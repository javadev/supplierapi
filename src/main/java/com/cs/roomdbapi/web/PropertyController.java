package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.Property;
import com.cs.roomdbapi.dto.PropertySaveRequest;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.manager.PropertyManager;
import com.cs.roomdbapi.model.RoleName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {

        Property property = propertyManager.getPropertyById(id);
        validatePropertyAccess(req, property);

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
        validatePropertyAccess(req, property);

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
            @Min(1) Integer id,
            @Valid @RequestBody PropertySaveRequest property,
            HttpServletRequest req
    ) {
        Property prop = propertyManager.getPropertyById(id);
        validatePropertyAccess(req, prop);

        Property updatedProperty = propertyManager.updateProperty(id, property, req.getRemoteUser());

        return ResponseEntity.ok(updatedProperty);
    }

    protected static void validatePropertyAccess(HttpServletRequest req, Property property) {

        String propertyName = property.getSupplier().getName();
        String supplierName = req.getRemoteUser();

        if (!isHasAllPropertiesPermission() && !supplierName.equals(propertyName)) {
            throw new BadRequestException(String.format("Property with id '%s' does not belong to supplier", property.getId()));
        }
    }

    private static boolean isHasAllPropertiesPermission() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().stream().anyMatch(
                a -> a.getAuthority().equals(RoleName.ROLE_SUPPLIER_ALL_PROPERTIES.getAuthority())
        );
    }

}
