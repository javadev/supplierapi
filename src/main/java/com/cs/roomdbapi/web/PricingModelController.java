package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.manager.PricingModelManager;
import com.cs.roomdbapi.manager.PropertyManager;
import com.cs.roomdbapi.manager.ValidationManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Tag(
        name = "Pricing Models",
        description = "API endpoints to access and manipulate with Pricing Models."
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/pricing-models", produces = MediaType.APPLICATION_JSON_VALUE)
public class PricingModelController {

    private final PricingModelManager pricingModelManager;

    private final PropertyManager propertyManager;

    private final ValidationManager validationManager;

    @Operation(
            summary = "Get list of all pricing model types.",
            description = "All fields of the Brand entity will be included in result."
    )
    @GetMapping("/types")
    public ResponseEntity<List<PricingModelType>> getAllBrands() {
        List<PricingModelType> pricingModelTypes = pricingModelManager.getPricingModelTypes();

        return new ResponseEntity<>(pricingModelTypes, HttpStatus.OK);
    }

    @Operation(
            summary = "Get pricing model type by id."
    )
    @GetMapping({"/types/{id}"})
    public ResponseEntity<PricingModelType> getBrandType(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal pricing model type Id. Required.")
            @Min(1)
                    Integer id
    ) {

        return new ResponseEntity<>(pricingModelManager.getPricingModelTypeById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Add pricing model."
    )
    @PostMapping({"/"})
    public ResponseEntity<PricingModel> addPricingModel(
            @Valid
            @RequestBody
                    PricingModelSaveRequest request,
            HttpServletRequest req
    ) {
        Integer propertyId = request.getPropertyId();
        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        validationManager.validatePropertyAccess(req, supplier, propertyId);

        PricingModel pricingModel = pricingModelManager.addPricingModel(request);

        return new ResponseEntity<>(pricingModel, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get list of all pricing models, by property id.",
            description = "All fields of the pricing model will be included in result."
    )
    @GetMapping({"/by-property/{propertyId}"})
    public ResponseEntity<List<PricingModel>> getAllPricingModels(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1000000)
                    Integer propertyId,
            HttpServletRequest req
    ) {
        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        validationManager.validatePropertyAccess(req, supplier, propertyId);

        List<PricingModel> all = pricingModelManager.getAllPricingModelsByPropertyId(propertyId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Get pricing model data by id."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<PricingModel> getPricingModel(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal Pricing Model Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        validationManager.validatePricingModelAccess(id, req);

        return new ResponseEntity<>(pricingModelManager.getPricingModelById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete pricing model by id."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePricingModel(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal pricing model Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        validationManager.validatePricingModelAccess(id, req);

        pricingModelManager.deletePricingModel(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
