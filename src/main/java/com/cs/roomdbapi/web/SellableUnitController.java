package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.manager.PropertyManager;
import com.cs.roomdbapi.manager.SellableUnitManager;
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
        PropertyController.validatePropertyAccess(req, supplier, propertyId);

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

        validateSellableUnitAccess(id, req);

        sellableUnitManager.deleteSellableUnit(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void validateSellableUnitAccess(Integer sellableUnitId, HttpServletRequest req) {
        if (sellableUnitManager.sellableUnitNotExistsById(sellableUnitId)) {
            throw new BadRequestException("Sellable Unit with provided id does not exists in a system.");
        }

        Integer propertyId = sellableUnitManager.getPropertyIdBySellableUnitId(sellableUnitId);

        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        PropertyController.validatePropertyAccess(req, supplier, propertyId);
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
        validateSellableUnitAccess(id, req);

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
        PropertyController.validatePropertyAccess(req, supplier, propertyId);

        List<SellableUnit> all = sellableUnitManager.getAllSellableUnitsByPropertyId(propertyId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

}
