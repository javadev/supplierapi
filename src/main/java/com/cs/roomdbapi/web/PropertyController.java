package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.manager.DescriptionManager;
import com.cs.roomdbapi.manager.PropertyManager;
import com.cs.roomdbapi.model.RoleName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Slf4j
@Tag(
        name = "Properties",
        description = "API endpoints to access Property basic information."
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/properties", produces = MediaType.APPLICATION_JSON_VALUE)
public class PropertyController {

    private final PropertyManager propertyManager;

    private final DescriptionManager descriptionManager;

    @Operation(
            summary = "Get list of all properties.",
            description = "All fields of the Property entity will be included in result. <br/>" +
                    "If supplier has role to **read all properties** than this endpoint will return **all** properties in a system. <br/>" +
                    "If supplier has **no role** to read all properties, result will include **only** properties which **belongs to supplier**."
    )
    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
            "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties(HttpServletRequest req) {

        List<Property> properties;

        if (isHasAllPropertiesPermission()) {
            // all properties
            properties = propertyManager.getProperties();
        } else {
            // only properties that are belong to supplier
            properties = propertyManager.getPropertiesBySupplier(req.getRemoteUser());
        }

        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @Operation(
            summary = "Get property data by id.",
            description = "If supplier has role to **read all properties** than this endpoint will return **any** property in a system. <br/>" +
                    "If supplier has **no role** to read all properties, result will return property **only** if it **belongs to supplier**."
    )
    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
            "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
    @GetMapping({"/{id}"})
    public ResponseEntity<Property> getProperty(
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(100000)
                    Integer id,
            HttpServletRequest req
    ) {

        Property property = propertyManager.getPropertyById(id);
        validatePropertyAccess(req, property.getSupplier(), id);

        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @Operation(
            summary = "Get property data by supplier property id.",
            description = "If supplier has role to **read all properties** than this endpoint will return **any** property in a system. <br/>" +
                    "If supplier has **no role** to read all properties, result will return property **only** if it **belongs to supplier**."
    )
    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
            "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
    @GetMapping({"/supplier-property-id/{id}"})
    public ResponseEntity<Property> getPropertyBySupplierPropertyId(
            @PathVariable
            @Parameter(description = "Supplier property id - property id that is used on supplier side. Required.")
            @Size(min = 1, max = 255)
                    String id,
            HttpServletRequest req
    ) {

        Property property = propertyManager.getPropertyBySupplierPropertyId(id);
        validatePropertyAccess(req, property.getSupplier(), property.getId());

        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @Operation(
            summary = "Get or create property by supplier property id.",
            description = "If supplier has role to **read all properties** than this endpoint will return **any** property in a system. <br/>" +
                    "If supplier has **no role** to read all properties, result will return property **only** if it **belongs to supplier**."
    )
    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
            "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
    @GetMapping({"/supplier-property-id/get-or-create/{id}"})
    public ResponseEntity<Property> getOrCreatePropertyBySupplierPropertyId(
            @PathVariable
            @Parameter(description = "Supplier property id - property id that is used on supplier side. Required.")
            @Size(min = 1, max = 255)
                    String id,
            HttpServletRequest req
    ) {
        Property property = propertyManager.getOrCreatePropertyBySupplierPropertyId(id, req.getRemoteUser());
        validatePropertyAccess(req, property.getSupplier(), property.getId());

        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
            "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
    @Operation(
            summary = "Add property."
    )
    @PostMapping
    public ResponseEntity<Property> addProperty(
            @Valid @RequestBody PropertySaveRequest property,
            HttpServletRequest req
    ) {
        Property newProperty = propertyManager.addProperty(property, req.getRemoteUser());

        return new ResponseEntity<>(newProperty, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
            "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
    @Operation(
            summary = "Update property."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(
            @PathVariable("id")
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(100000) Integer id,
            @Valid @RequestBody PropertySaveRequest property,
            HttpServletRequest req
    ) {
        Supplier supplier = propertyManager.getSupplierByPropertyId(id);
        validatePropertyAccess(req, supplier, id);

        Property updatedProperty = propertyManager.updateProperty(id, property, req.getRemoteUser());

        return ResponseEntity.ok(updatedProperty);
    }

    @Operation(
            summary = "Get property info by property id.",
            description = "If supplier has role to **read all properties** than this endpoint will return **any** property in a system. <br/>" +
                    "If supplier has **no role** to read all properties, result will return property **only** if it **belongs to supplier**."
    )
    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
            "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
    @GetMapping({"/info/{id}"})
    public ResponseEntity<PropertyInfo> getPropertyInfo(
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(100000)
                    Integer id,
            HttpServletRequest req
    ) {
        Supplier supplier = propertyManager.getSupplierByPropertyId(id);
        validatePropertyAccess(req, supplier, id);

        PropertyInfo propertyInfo = propertyManager.getPropertyInfoByPropertyId(id);

        return new ResponseEntity<>(propertyInfo, HttpStatus.OK);
    }

    @Operation(
            summary = "Add property info."
    )
    @PostMapping({"/info"})
    public ResponseEntity<PropertyInfo> addPropertyInfo(
            @Valid @RequestBody PropertyInfoSaveRequest info,
            HttpServletRequest req
    ) {
        Integer propertyId = info.getPropertyId();
        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        validatePropertyAccess(req, supplier, propertyId);

        PropertyInfo propertyInfo = propertyManager.addPropertyInfo(info);

        return new ResponseEntity<>(propertyInfo, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update property info. Request field PropertyId will be used to find Property Info that need to be updated."
    )
    @PutMapping("/info")
    public ResponseEntity<PropertyInfo> updatePropertyInfo(
            @Valid @RequestBody PropertyInfoSaveRequest info,
            HttpServletRequest req
    ) {
        Integer propertyId = info.getPropertyId();
        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        validatePropertyAccess(req, supplier, propertyId);

        PropertyInfo updated = propertyManager.updatePropertyInfo(info);

        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Delete property info by property id."
    )
    @DeleteMapping("/info/{id}")
    public ResponseEntity<Void> deletePropertyInfo(
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(100000)
                    Integer id,
            HttpServletRequest req
    ) {
        log.info("API delete Property Info called with propertyId: {}.", id);

        Supplier supplier = propertyManager.getSupplierByPropertyId(id);
        validatePropertyAccess(req, supplier, id);

        propertyManager.deletePropertyInfoByPropertyId(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected static void validatePropertyAccess(HttpServletRequest req, Supplier supplier, Integer propertyId) {

        String propertyName = supplier.getName();
        String supplierName = req.getRemoteUser();

        if (!isHasAllPropertiesPermission() && !supplierName.equals(propertyName)) {
            throw new BadRequestException(String.format("Property with id '%s' does not belong to supplier", propertyId));
        }
    }

    private static boolean isHasAllPropertiesPermission() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream().anyMatch(
                a -> a.getAuthority().equals(RoleName.ROLE_SUPPLIER_ALL_PROPERTIES.getAuthority())
        );
    }

    @Operation(
            summary = "Set emails to property.",
            description = "Previous emails will be removed and only new emails will be added to property. <br/>" +
                    "If provided emails array empty all existing emails will be removed."
    )
    @PostMapping({"/set-emails"})
    public ResponseEntity<List<Email>> setEmails(
            @Valid
            @RequestBody
                    PropertyEmailRequest propertyEmailRequest,
            HttpServletRequest req
    ) {
        Integer propertyId = propertyEmailRequest.getPropertyId();
        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        validatePropertyAccess(req, supplier, propertyId);

        List<Email> emails = propertyManager.setPropertyEmails(propertyEmailRequest.getPropertyId(), propertyEmailRequest.getEmails());

        return new ResponseEntity<>(emails, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Set phones to property.",
            description = "Previous phones will be removed and only new phones will be added to property. <br/>" +
                    "If provided phones array empty all existing phones will be removed."
    )
    @PostMapping({"/set-phones"})
    public ResponseEntity<List<Phone>> setPhones(
            @Valid
            @RequestBody
                    PropertyPhoneRequest propertyPhoneRequest,
            HttpServletRequest req
    ) {
        Integer propertyId = propertyPhoneRequest.getPropertyId();
        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        validatePropertyAccess(req, supplier, propertyId);

        List<Phone> phones = propertyManager.setPropertyPhones(propertyPhoneRequest.getPropertyId(), propertyPhoneRequest.getPhones());

        return new ResponseEntity<>(phones, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Add description to property."
    )
    @PostMapping({"/description/{propertyId}"})
    public ResponseEntity<Description> addDescription(
            @PathVariable("propertyId")
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1)
                    Integer propertyId,
            @Valid
            @RequestBody
                    DescriptionSave descriptionSave,
            HttpServletRequest req
    ) {
        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        validatePropertyAccess(req, supplier, propertyId);

        Description description = propertyManager.addPropertyDescription(propertyId, descriptionSave);

        return new ResponseEntity<>(description, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update description for property."
    )
    @PatchMapping("/description/{id}")
    public ResponseEntity<Description> updateDescription(
            @PathVariable("id")
            @Parameter(description = "RoomDB internal description Id. Required.")
            @Min(1)
                    Integer id,
            @Valid
            @RequestBody
                    DescriptionSave descriptionSave,
            HttpServletRequest req
    ) {
        Integer propertyId = propertyManager.getPropertyIdByDescriptionId(id);
        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        validatePropertyAccess(req, supplier, propertyId);

        Description description = descriptionManager.updateDescription(id, descriptionSave);

        return new ResponseEntity<>(description, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete description for property."
    )
    @DeleteMapping("/description/{id}")
    public ResponseEntity<Void> deleteDescription(
            @PathVariable
            @Parameter(description = "RoomDB internal description Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        log.info("API delete property description called with id: {}.", id);

        Integer propertyId = propertyManager.getPropertyIdByDescriptionId(id);
        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        validatePropertyAccess(req, supplier, propertyId);

        descriptionManager.deletePropertyDescription(propertyId, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
