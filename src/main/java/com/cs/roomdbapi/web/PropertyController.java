package com.cs.roomdbapi.web;

import com.cs.roomdbapi.annotation.IgnoreResponseBinding;
import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.manager.DescriptionManager;
import com.cs.roomdbapi.manager.PropertyManager;
import com.cs.roomdbapi.manager.ValidationManager;
import com.cs.roomdbapi.model.PropertyEntity;
import com.cs.roomdbapi.response.SuccessResponse;
import com.cs.roomdbapi.utilities.AppUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class PropertyController {

    private final PropertyManager propertyManager;

    private final DescriptionManager descriptionManager;

    private final ValidationManager validationManager;

    @Operation(
            summary = "Get list of all properties.",
            description = "All fields of the Property entity will be included in result. <br/>" +
                    "If supplier has role to **read all properties** than this endpoint will return **all** properties in a system. <br/>" +
                    "If supplier has **no role** to read all properties, result will include **only** properties which **belongs to supplier**."
    )
    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties(HttpServletRequest req) {
        List<Property> properties;

        if (validationManager.isHasAllPropertiesPermission()) {
            // all properties
            properties = propertyManager.getProperties();
        } else {
            // only properties that are belong to supplier
            properties = propertyManager.getPropertiesBySupplier(req.getRemoteUser());
        }

        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @IgnoreResponseBinding
    @Operation(
            summary = "Get list of all properties.",
            description = "All fields of the Property entity will be included in result. <br/>" +
                    "If supplier has role to **read all properties** than this endpoint will return **all** properties in a system. <br/>" +
                    "If supplier has **no role** to read all properties, result will include **only** properties which **belongs to supplier**."
    )
    @GetMapping({"/paging"})
    public SuccessResponse<List<Property>> getAllPropertiesWithPagination(
            @Valid
            @Parameter(description = "Page number in paginated request. Default is 0.")
            @Min(0)
            @RequestParam(defaultValue = "0")
                    Integer page,
            @Valid
            @Parameter(description = "Size of the page in paginated request. Default is 10.")
            @Min(1)
            @RequestParam(defaultValue = "10")
                    Integer size,
            @Valid
            @Parameter(description = "Field name that should be used for sorting in paginated request. Default is 'name'.")
            @RequestParam(defaultValue = "name")
                    String sortBy,
            @Valid
            @Parameter(description = "Is descending sort should be applied in sorting in paginated request. Default is 'true'.")
            @RequestParam(defaultValue = "true")
                    boolean sortDesc,
            HttpServletRequest req
    ) {
        List<Property> properties;

        Page<PropertyEntity> pageResult;
        if (validationManager.isHasAllPropertiesPermission()) {
            // all properties
            pageResult = propertyManager.getPropertiesPagination(page, size, sortBy, sortDesc);
        } else {
            // only properties that are belong to supplier
            pageResult = propertyManager.getPropertiesBySupplierPagination(req.getRemoteUser(), page, size, sortBy, sortDesc);
        }
        properties = propertyManager.toListDTO(pageResult.getContent());

        return new SuccessResponse<>(properties, AppUtils.RESPONSE_CODE_SUCCESS_MSG, AppUtils.SUCCESS,
                pageResult.getTotalElements(), pageResult.getTotalPages());
    }

    @Operation(
            summary = "Get list of properties that are isMaster.",
            description = "All fields of the Property entity will be included in result. <br/>" +
                    "If supplier has role to **read all properties** than this endpoint will return **all** properties in a system. <br/>" +
                    "If supplier has **no role** to read all properties, result will include **only** properties which **belongs to supplier**."
    )
    @GetMapping({"/is-master"})
    public ResponseEntity<List<Property>> getAllIsMasterProperties(HttpServletRequest req) {
        List<Property> properties;

        if (validationManager.isHasAllPropertiesPermission()) {
            // all properties
            properties = propertyManager.getIsMasterProperties();
        } else {
            // only properties that are belong to supplier
            properties = propertyManager.getIsMasterPropertiesBySupplier(req.getRemoteUser());
        }

        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @Operation(
            summary = "Get property data by id.",
            description = "If supplier has role to **read all properties** than this endpoint will return **any** property in a system. <br/>" +
                    "If supplier has **no role** to read all properties, result will return property **only** if it **belongs to supplier**."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<Property> getProperty(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1000000)
                    Integer id,
            HttpServletRequest req
    ) {
        Property property = propertyManager.getPropertyById(id);
        validationManager.validatePropertyAccess(req, property.getSupplier(), id);

        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @Operation(
            summary = "Get property data by CultSwitch id.",
            description = "If supplier has role to **read all properties** than this endpoint will return **any** property in a system. <br/>" +
                    "If supplier has **no role** to read all properties, result will return property **only** if it **belongs to supplier**."
    )
    @GetMapping({"/supplier-property-id/{id}"})
    public ResponseEntity<Property> getPropertyByCultSwitchId(
            @Valid
            @PathVariable
            @Parameter(description = "CultSwitch id - property id that is used in CultSwitch. Also known as ObjectId. Required.")
            @Size(min = 1, max = 255)
                    String id,
            HttpServletRequest req
    ) {
        Property property = propertyManager.getPropertyByCultSwitchId(id);
        validationManager.validatePropertyAccess(req, property.getSupplier(), property.getId());

        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @Operation(
            summary = "Get multiply properties by CultSwitch ids.",
            description = "If supplier has role to **read all properties** than this endpoint will return **any** property in a system. <br/>" +
                    "If supplier has **no role** to read all properties, result will return property **only** if it **belongs to supplier**."
    )
    @GetMapping({"/by-cultswitch-ids/{ids}"})
    public ResponseEntity<List<Property>> getPropertiesByCultSwitchIds(
            @Valid
            @PathVariable
            @Parameter(description = "CultSwitch ids - property ids that is used in CultSwitch. Also known as ObjectIds. Required.")
            @Size(min = 1, max = 255)
                    List<String> ids,
            HttpServletRequest req
    ) {
        List<Property> properties = propertyManager.getPropertiesByCultSwitchIds(ids);
        validationManager.validatePropertiesList(req, properties);

        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @Operation(
            summary = "Get property isMaster data by id.",
            description = "If supplier has role to **read all properties** than this endpoint will return **any** property in a system. <br/>" +
                    "If supplier has **no role** to read all properties, result will return property **only** if it **belongs to supplier**."
    )
    @GetMapping({"/is-master/{id}"})
    public ResponseEntity<Property> isMasterProperty(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1000000)
                    Integer id,
            HttpServletRequest req
    ) {
        Property property = propertyManager.getPropertyIsMasterById(id);
        validationManager.validatePropertyAccess(req, property.getSupplier(), id);

        property.setSupplier(null); // unset, is was needed only for validation

        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @Operation(
            summary = "Get property isMaster data by CultSwitch id.",
            description = "If supplier has role to **read all properties** than this endpoint will return **any** property in a system. <br/>" +
                    "If supplier has **no role** to read all properties, result will return property **only** if it **belongs to supplier**."
    )
    @GetMapping({"/is-master/supplier-property-id/{id}"})
    public ResponseEntity<Property> isMasterPropertyByCultSwitchId(
            @Valid
            @PathVariable
            @Parameter(description = "CultSwitch id - property id that is used in CultSwitch. Also known as ObjectId. Required.")
            @Size(min = 1, max = 255)
                    String id,
            HttpServletRequest req
    ) {
        Property property = propertyManager.getPropertyIsMasterByCultSwitchId(id);
        validationManager.validatePropertyAccess(req, property.getSupplier(), property.getId());

        property.setSupplier(null); // unset, is was needed only for validation

        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @Operation(
            summary = "Get properties data by code.",
            description = "This endpoint could return more than one entry because code is not validate to be unique accross all properties. <br/>" +
                    "If supplier has role to **read all properties** than this endpoint will return **any** property in a system. <br/>" +
                    "If supplier has **no role** to read all properties, result will return property **only** if it **belongs to supplier**."
    )
    @GetMapping({"/code/{code}"})
    public ResponseEntity<List<Property>> getPropertyByCode(
            @Valid
            @PathVariable
            @Parameter(description = "Properties code. Required.")
            @Size(min = 1, max = 255)
                    String code,
            HttpServletRequest req
    ) {
        List<Property> properties = propertyManager.getPropertiesByCode(code);
        properties.removeIf(property -> !validationManager.hasAccessToProperty(req, property.getSupplier(), property.getId()));

        return new ResponseEntity<>(properties, HttpStatus.OK);
    }

    @Operation(
            summary = "Get or create property by CultSwitch id.",
            description = "If supplier has role to **read all properties** than this endpoint will return **any** property in a system. <br/>" +
                    "If supplier has **no role** to read all properties, result will return property **only** if it **belongs to supplier**."
    )
    @GetMapping({"/supplier-property-id/get-or-create/{id}"})
    public ResponseEntity<Property> getOrCreatePropertyByCultSwitchId(
            @Valid
            @PathVariable
            @Parameter(description = "CultSwitch id - property id that is used in CultSwitch. Also known as ObjectId. Required.")
            @Size(min = 1, max = 255)
                    String id,
            HttpServletRequest req
    ) {
        Property property = propertyManager.getOrCreatePropertyByCultSwitchId(id, req.getRemoteUser());
        validationManager.validatePropertyAccess(req, property.getSupplier(), property.getId());

        return new ResponseEntity<>(property, HttpStatus.OK);
    }

    @Operation(
            summary = "Add property."
    )
    @PostMapping
    public ResponseEntity<Property> addProperty(
            @Valid
            @RequestBody
                    PropertySaveRequest property,
            HttpServletRequest req
    ) {
        Property newProperty = propertyManager.addProperty(property, req.getRemoteUser());

        return new ResponseEntity<>(newProperty, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update property."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(
            @Valid
            @PathVariable("id")
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1000000)
                    Integer id,
            @Valid
            @RequestBody
                    PropertySaveRequest property,
            HttpServletRequest req
    ) {
        validationManager.validatePropertyAccess(req, id);

        Property updatedProperty = propertyManager.updateProperty(id, property, req.getRemoteUser());

        return ResponseEntity.ok(updatedProperty);
    }

    @Operation(
            summary = "Get property info by property id.",
            description = "If supplier has role to **read all properties** than this endpoint will return **any** property in a system. <br/>" +
                    "If supplier has **no role** to read all properties, result will return property **only** if it **belongs to supplier**."
    )
    @GetMapping({"/info/{id}"})
    public ResponseEntity<PropertyInfo> getPropertyInfo(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1000000)
                    Integer id,
            HttpServletRequest req
    ) {
        validationManager.validatePropertyAccess(req, id);

        PropertyInfo propertyInfo = propertyManager.getPropertyInfoByPropertyId(id);

        return new ResponseEntity<>(propertyInfo, HttpStatus.OK);
    }

    @Operation(
            summary = "Add property info."
    )
    @PostMapping({"/info"})
    public ResponseEntity<PropertyInfo> addPropertyInfo(
            @Valid
            @RequestBody
                    PropertyInfoSaveRequest info,
            HttpServletRequest req
    ) {
        validationManager.validatePropertyAccess(req, info.getPropertyId());

        PropertyInfo propertyInfo = propertyManager.addPropertyInfo(info);

        return new ResponseEntity<>(propertyInfo, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update property info. Request field PropertyId will be used to find Property Info that need to be updated."
    )
    @PutMapping("/info")
    public ResponseEntity<PropertyInfo> updatePropertyInfo(
            @Valid
            @RequestBody
                    PropertyInfoSaveRequest info,
            HttpServletRequest req
    ) {
        validationManager.validatePropertyAccess(req, info.getPropertyId());

        PropertyInfo updated = propertyManager.updatePropertyInfo(info);

        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Delete property info by property id."
    )
    @DeleteMapping("/info/{id}")
    public ResponseEntity<Void> deletePropertyInfo(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1000000)
                    Integer id,
            HttpServletRequest req
    ) {
        validationManager.validatePropertyAccess(req, id);

        propertyManager.deletePropertyInfoByPropertyId(id);

        return new ResponseEntity<>(HttpStatus.OK);
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
        validationManager.validatePropertyAccess(req, propertyEmailRequest.getPropertyId());

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
        validationManager.validatePropertyAccess(req, propertyPhoneRequest.getPropertyId());

        List<Phone> phones = propertyManager.setPropertyPhones(propertyPhoneRequest.getPropertyId(), propertyPhoneRequest.getPhones());

        return new ResponseEntity<>(phones, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Add description to property.",
            description = "Description type is not required and will be set to 'Point Of Interest' if not provided."
    )
    @PostMapping({"/description/{propertyId}"})
    public ResponseEntity<Description> addDescription(
            @Valid
            @PathVariable("propertyId")
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1000000)
                    Integer propertyId,
            @Valid
            @RequestBody
                    DescriptionSave descriptionSave,
            HttpServletRequest req
    ) {
        validationManager.validatePropertyAccess(req, propertyId);

        Description description = propertyManager.addPropertyDescription(propertyId, descriptionSave);

        return new ResponseEntity<>(description, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update description for property.",
            description = "Description type is not required and will be set to 'Point Of Interest' if not provided."
    )
    @PatchMapping("/description/{id}")
    public ResponseEntity<Description> updateDescription(
            @Valid
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
        if (propertyId == null) {
            throw new BadRequestException(String.format("Description with id '%s' does not belong to Property.", id));
        }
        validationManager.validatePropertyAccess(req, propertyId);

        Description description = descriptionManager.updateDescription(id, descriptionSave);

        return new ResponseEntity<>(description, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete description for property."
    )
    @DeleteMapping("/description/{id}")
    public ResponseEntity<Void> deleteDescription(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal description Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        Integer propertyId = propertyManager.getPropertyIdByDescriptionId(id);
        if (propertyId == null) {
            throw new BadRequestException(String.format("Description with id '%s' does not belong to Property.", id));
        }
        validationManager.validatePropertyAccess(req, propertyId);

        descriptionManager.deletePropertyDescription(propertyId, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Get property identifiers by property id.",
            description = "It will return array of the identifiers if supplier has access to the property"
    )
    @GetMapping({"/identifier/{propertyId}"})
    public ResponseEntity<List<PropertyIdentifier>> getPropertyIdentifiers(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1000000)
                    Integer propertyId,
            HttpServletRequest req
    ) {
        validationManager.validatePropertyAccess(req, propertyId);

        List<PropertyIdentifier> all = propertyManager.getPropertyIdentifiersByPropertyId(propertyId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Add/Update property identifiers for specific source to property.",
            description = "If identifier for source already exists, it will be overridden. <br/>" +
                    "If identifier for source is new it will be created. <br/>" +
                    "If identifier is not provided in request for source it will remain as is."
    )
    @PostMapping({"/identifier"})
    public ResponseEntity<List<PropertyIdentifier>> addPropertyIdentifier(
            @Valid
            @RequestBody
                    PropertyIdentifierRequest propertyIdentifierRequest,
            HttpServletRequest req
    ) {
        Integer propertyId = propertyIdentifierRequest.getPropertyId();
        validationManager.validatePropertyAccess(req, propertyId);

        List<PropertyIdentifier> list = propertyManager.addPropertyIdentifiers(propertyId, propertyIdentifierRequest.getIdentifiers());

        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete property identifier for specific source for property."
    )
    @DeleteMapping("/identifier/{propertyId}/{sourceId}")
    public ResponseEntity<Void> deleteIdentifier(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1000000)
                    Integer propertyId,
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal identifier source Id. Required.")
            @Min(1)
                    Integer sourceId,
            HttpServletRequest req
    ) {
        validationManager.validatePropertyAccess(req, propertyId);

        propertyManager.deletePropertyIdentifier(propertyId, sourceId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Set/Update home currency by currency id.",
            description = "Existing value will be updated."
    )
    @PatchMapping({"/home-currency/by-id"})
    public ResponseEntity<Currency> setHomeCurrencyById(
            @Valid
            @RequestBody
                    HomeCurrencyById request,
            HttpServletRequest req
    ) {
        Integer propertyId = request.getPropertyId();
        validationManager.validatePropertyAccess(req, propertyId);

        Currency currency = propertyManager.setPropertyHomeCurrencyById(propertyId, request.getHomeCurrencyId());

        return new ResponseEntity<>(currency, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Set/Update home currency by currency 3 latter code (ISO 4217).",
            description = "Existing value will be updated."
    )
    @PatchMapping({"/home-currency/by-code"})
    public ResponseEntity<Currency> setHomeCurrencyByCode(
            @Valid
            @RequestBody
                    HomeCurrencyByCode request,
            HttpServletRequest req
    ) {
        Integer propertyId = request.getPropertyId();
        validationManager.validatePropertyAccess(req, propertyId);

        Currency currency = propertyManager.setPropertyHomeCurrencyByCode(propertyId, request.getHomeCurrencyCode());

        return new ResponseEntity<>(currency, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get property data by External System Id.",
            description = "Two params are needed: External System Abbreviation and External System Id."
    )
    @GetMapping({"/supplier-property-id/{source}/{id}"})
    public ResponseEntity<Property> getPropertyByExternalSystemId(
            @Valid
            @PathVariable
            @Parameter(description = "External System Abbreviation - abbreviation that is used in RoomDb to identify External system. " +
                    "In models known as IdentifierSource - abbreviation. Required.")
            @Size(min = 1, max = 255)
                    String source,
            @Valid
            @PathVariable
            @Parameter(description = "External System Id - identifier that is used in External system. Required.")
            @Size(min = 1, max = 255)
                    String id,
            HttpServletRequest req
    ) {
        Property property = propertyManager.getPropertyByExternalSystemId(id, source);
        validationManager.validatePropertyAccess(req, property.getSupplier(), property.getId());

        return new ResponseEntity<>(property, HttpStatus.OK);
    }

}
