package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.manager.DescriptionManager;
import com.cs.roomdbapi.manager.PropertyManager;
import com.cs.roomdbapi.manager.SellableUnitManager;
import com.cs.roomdbapi.manager.ValidationManager;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Slf4j
@Tag(
        name = "Sellable Unit",
        description = "API endpoints to access Sellable Unit and additional information about it."
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/sellable-unit", produces = MediaType.APPLICATION_JSON_VALUE)
public class SellableUnitController {

    private final SellableUnitManager sellableUnitManager;

    private final PropertyManager propertyManager;

    private final DescriptionManager descriptionManager;

    private final ValidationManager validationManager;

    @Operation(
            summary = "Get list of all sellable unit types."
    )
    @GetMapping({"/sellable-unit-types"})
    public ResponseEntity<List<SellableUnitType>> getAllSellableUnitTypes() {
        List<SellableUnitType> all = sellableUnitManager.getAllSellableUnitTypes();

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Add sellable unit."
    )
    @PostMapping({"/"})
    public ResponseEntity<SellableUnit> addSellableUnit(
            @Valid @RequestBody SellableUnitSaveRequest request,
            HttpServletRequest req
    ) {
        Integer propertyId = request.getPropertyId();
        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        validationManager.validatePropertyAccess(req, supplier, propertyId);

        SellableUnit sellableUnit = sellableUnitManager.addSellableUnit(request);

        return new ResponseEntity<>(sellableUnit, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete sellable unit by id."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSellableUnit(
            @PathVariable
            @Parameter(description = "RoomDB internal sellable unit Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        log.info("API delete sellable unit called with id: {}.", id);

        validationManager.validateSellableUnitAccess(id, req);

        sellableUnitManager.deleteSellableUnit(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Get sellable unit data by id."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<SellableUnit> getSellableUnit(
            @PathVariable
            @Parameter(description = "RoomDB internal Sellable Unit Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(id, req);

        return new ResponseEntity<>(sellableUnitManager.getSellableUnitById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Get list of all sellable units, by property id.",
            description = "All fields of the sellable unit entity will be included in result."
    )
    @GetMapping({"/by-property/{propertyId}"})
    public ResponseEntity<List<SellableUnit>> getAllSellableUnits(
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1000000)
                    Integer propertyId,
            HttpServletRequest req
    ) {
        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        validationManager.validatePropertyAccess(req, supplier, propertyId);

        List<SellableUnit> all = sellableUnitManager.getAllSellableUnitsByPropertyId(propertyId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Get sellable unit by supplier unit id."
    )
    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
            "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
    @GetMapping({"/supplier-unit-id/{id}"})
    public ResponseEntity<SellableUnit> getSellableUnitBySupplierUnitId(
            @PathVariable
            @Parameter(description = "Supplier unit id - unit (room, meal, etc.) id that is used on supplier side. Required.")
            @Size(min = 1, max = 255)
                    String id,
            HttpServletRequest req
    ) {

        SellableUnit sellableUnit = sellableUnitManager.getSellableUnitBySupplierUnitId(id);

        Integer propertyId = sellableUnit.getPropertyId();

        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        validationManager.validatePropertyAccess(req, supplier, propertyId);

        return new ResponseEntity<>(sellableUnit, HttpStatus.OK);
    }

    @Operation(
            summary = "Get or create sellable unit by supplier unit id."
    )
    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
            "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
    @GetMapping({"/supplier-unit-id/get-or-create/{propertyId}/{sellableUnitId}"})
    public ResponseEntity<SellableUnit> getOrCreateSellableUnitBySupplierUnitId(
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required. Will be used to create sellable unit in case it doesn't exists.")
            @Min(100000)
                    Integer propertyId,
            @PathVariable
            @Parameter(description = "Supplier unit id - unit (room, meal, etc.) id that is used on supplier side. Required.")
            @Size(min = 1, max = 255)
                    String sellableUnitId,
            HttpServletRequest req
    ) {
        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        validationManager.validatePropertyAccess(req, supplier, propertyId);

        SellableUnit supplierUnit = sellableUnitManager.getOrCreateSellableUnitBySupplierUnitId(sellableUnitId, propertyId);

        return new ResponseEntity<>(supplierUnit, HttpStatus.OK);
    }

    @Operation(
            summary = "Add description to sellable unit."
    )
    @PostMapping({"/description/{sellableUnitId}"})
    public ResponseEntity<Description> addDescription(
            @PathVariable("sellableUnitId")
            @Parameter(description = "RoomDB internal sellable unit Id. Required.")
            @Min(1)
                    Integer sellableUnitId,
            @Valid
            @RequestBody
                    DescriptionSave descriptionSave,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(sellableUnitId, req);

        Description description = sellableUnitManager.addSellableUnitDescription(sellableUnitId, descriptionSave);

        return new ResponseEntity<>(description, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update description for sellable unit."
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
        Integer sellableUnitId = sellableUnitManager.getSellableUnitIdByDescriptionId(id);
        validationManager.validateSellableUnitAccess(sellableUnitId, req);

        Description description = descriptionManager.updateDescription(id, descriptionSave);

        return new ResponseEntity<>(description, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete description for sellable unit."
    )
    @DeleteMapping("/description/{id}")
    public ResponseEntity<Void> deleteDescription(
            @PathVariable
            @Parameter(description = "RoomDB internal description Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        log.info("API delete sellable unit description called with id: {}.", id);

        Integer sellableUnitId = sellableUnitManager.getSellableUnitIdByDescriptionId(id);
        validationManager.validateSellableUnitAccess(sellableUnitId, req);

        descriptionManager.deleteSellableUnitDescription(sellableUnitId, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Get sellable unit capacity data."
    )
    @GetMapping({"/capacity/{sellableUnitId}"})
    public ResponseEntity<List<SUCapacity>> getSellableUnitSUCapacity(
            @PathVariable
            @Parameter(description = "RoomDB internal Sellable Unit Id. Required.")
            @Min(1)
                    Integer sellableUnitId,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(sellableUnitId, req);

        List<SUCapacity> all = sellableUnitManager.getSUCapacityBySellableUnitId(sellableUnitId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Set capacity data to sellable unit.",
            description = "All capacity entries will be **overridden** with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided."
    )
    @PostMapping({"/set-capacities"})
    public ResponseEntity<List<SUCapacity>> setCapacity(
            @Valid
            @RequestBody
                    SUCapacityRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUCapacity> capacities = sellableUnitManager.setSellableUnitCapacities(request.getSellableUnitId(), request.getCapacities());

        return new ResponseEntity<>(capacities, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Add capacity data to sellable unit.",
            description = "New capacity entries will be **created** no effect for existing entries. <br/>" +
            "Time segment is not required and will be empty if not provided. <br/>" +
            "Only created records will be returned as result."
    )
    @PostMapping({"/add-capacities"})
    public ResponseEntity<List<SUCapacity>> addCapacity(
            @Valid
            @RequestBody
                    SUCapacityRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUCapacity> capacities = sellableUnitManager.addSellableUnitCapacities(request.getSellableUnitId(), request.getCapacities());

        return new ResponseEntity<>(capacities, HttpStatus.CREATED);
    }

}
