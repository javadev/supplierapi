package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.manager.DescriptionManager;
import com.cs.roomdbapi.manager.PointOfInterestManager;
import com.cs.roomdbapi.manager.PropertyManager;
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
        name = "Point Of Interest",
        description = "API endpoints to access Points Of Interest and additional information about it."
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/poi", produces = MediaType.APPLICATION_JSON_VALUE)
public class PointOfInterestController {

    private final PointOfInterestManager poiManager;

    private final PropertyManager propertyManager;

    private final DescriptionManager descriptionManager;

    @Operation(
            summary = "Get list of all points of interest, by property id.",
            description = "All fields of the point of interest entity will be included in result."
    )
    @GetMapping({"/by-property/{propertyId}"})
    public ResponseEntity<List<PointOfInterest>> getAllPointOfInterest(
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1000000)
                    Integer propertyId,
            HttpServletRequest req
    ) {
        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        PropertyController.validatePropertyAccess(req, supplier, propertyId);

        List<PointOfInterest> all = poiManager.getAllPointOfInterestByPropertyId(propertyId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Get point of interest data by id."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<PointOfInterest> getPOI(
            @PathVariable
            @Parameter(description = "RoomDB internal point of interest Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        validatePOIAccess(id, req);

        return new ResponseEntity<>(poiManager.getPOIById(id), HttpStatus.OK);
    }

    private void validatePOIAccess(Integer poiId, HttpServletRequest req) {
        if (poiManager.poiNotExistsById(poiId)) {
            throw new BadRequestException("Point of interest with provided id does not exists in a system.");
        }

        Integer propertyId = poiManager.getPropertyIdByPOIId(poiId);

        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        PropertyController.validatePropertyAccess(req, supplier, propertyId);
    }

    @Operation(
            summary = "Get list of all category codes available for point of interest."
    )
    @GetMapping({"/category-codes"})
    public ResponseEntity<List<CategoryCode>> getAllCategoryCodes() {
        List<CategoryCode> all = poiManager.getAllCategoryCodes();

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete point of interest by id."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePOI(
            @PathVariable
            @Parameter(description = "RoomDB internal point of interest Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        log.info("API delete POI called with id: {}.", id);

        validatePOIAccess(id, req);

        poiManager.deletePOI(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(
            summary = "Add point of interest."
    )
    @PostMapping
    public ResponseEntity<PointOfInterest> addPointOfInterest(
            @Valid @RequestBody PointOfInterestSaveRequest poi,
            HttpServletRequest req
    ) {
        Integer propertyId = poi.getPropertyId();
        Supplier supplier = propertyManager.getSupplierByPropertyId(propertyId);
        PropertyController.validatePropertyAccess(req, supplier, propertyId);

        PointOfInterest newPOI = poiManager.addPOI(poi);

        return new ResponseEntity<>(newPOI, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update point of interest."
    )
    @PutMapping("/{id}")
    public ResponseEntity<PointOfInterest> updatePointOfInterest(
            @PathVariable("id")
            @Parameter(description = "RoomDB internal point of interest Id. Required.")
            @Min(1) Integer id,
            @Valid @RequestBody PointOfInterestSaveRequest poi,
            HttpServletRequest req
    ) {
        validatePOIAccess(id, req);

        PointOfInterest updated = poiManager.updatePOI(id, poi);

        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Add descriptions to point of interest."
    )
    @PostMapping({"/description/{poiId}"})
    public ResponseEntity<Description> addDescription(
            @PathVariable("poiId")
            @Parameter(description = "RoomDB internal point of interest Id. Required.")
            @Min(1)
                    Integer poiId,
            @Valid
            @RequestBody
                    DescriptionSave descriptionSave,
            HttpServletRequest req
    ) {
        validatePOIAccess(poiId, req);

        Description description = poiManager.addPOIDescription(poiId, descriptionSave);

        return new ResponseEntity<>(description, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update description for Point of interest."
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
        Integer poiId = poiManager.getPOIIdByDescriptionId(id);

        validatePOIAccess(poiId, req);

        Description description = descriptionManager.updateDescription(id, descriptionSave);

        return new ResponseEntity<>(description, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete description for Point of interest."
    )
    @DeleteMapping("/description/{id}")
    public ResponseEntity<Void> deleteDescription(
            @PathVariable
            @Parameter(description = "RoomDB internal description Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        log.info("API delete point of interests description called with id: {}.", id);

        Integer poiId = poiManager.getPOIIdByDescriptionId(id);
        validatePOIAccess(poiId, req);

        descriptionManager.deletePOIDescription(poiId, id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
