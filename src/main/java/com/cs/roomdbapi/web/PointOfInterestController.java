package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.*;
import com.cs.roomdbapi.exception.BadRequestException;
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

    private final PointOfInterestManager pointOfInterestManager;

    private final PropertyManager propertyManager;

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
        Property prop = propertyManager.getPropertyById(propertyId);
        PropertyController.validatePropertyAccess(req, prop);

        List<PointOfInterest> all = pointOfInterestManager.getAllPointOfInterestByPropertyId(propertyId);

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

        return new ResponseEntity<>(pointOfInterestManager.getPOIById(id), HttpStatus.OK);
    }

    private void validatePOIAccess(Integer poiId, HttpServletRequest req) {
        if (pointOfInterestManager.poiNotExistsById(poiId)) {
            throw new BadRequestException("Point of interest with provided id does not exists in a system.");
        }

        Integer propertyId = pointOfInterestManager.getPropertyIdByPOIId(poiId);

        Property prop = propertyManager.getPropertyById(propertyId);
        PropertyController.validatePropertyAccess(req, prop);
    }

    @Operation(
            summary = "Get list of all category codes available for point of interest."
    )
    @GetMapping({"/category-codes"})
    public ResponseEntity<List<CategoryCode>> getAllCategoryCodes() {
        List<CategoryCode> all = pointOfInterestManager.getAllCategoryCodes();

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

        pointOfInterestManager.deletePOI(id);

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
        Property prop = propertyManager.getPropertyById(poi.getPropertyId());
        PropertyController.validatePropertyAccess(req, prop);

        PointOfInterest newPOI = pointOfInterestManager.addPOI(poi);

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

        Property prop = propertyManager.getPropertyById(poi.getPropertyId());
        PropertyController.validatePropertyAccess(req, prop);

        PointOfInterest updated = pointOfInterestManager.updatePOI(id, poi);

        return ResponseEntity.ok(updated);
    }

}
