package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.RatePlan;
import com.cs.roomdbapi.dto.RatePlanSaveRequest;
import com.cs.roomdbapi.dto.RatePlanUpdateRequest;
import com.cs.roomdbapi.manager.RatePlanManager;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Tag(
        name = "Rate Plans",
        description = "API endpoints to access Rate Plans for the product."
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/rate-plans", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class RatePlanController {

    private final RatePlanManager ratePlanManager;

    private final ValidationManager validationManager;

    @Operation(
            summary = "Get list of all rate plans, by property id.",
            description = "All fields of the Rate Plan entity will be included in result."
    )
    @GetMapping({"/by-property/{propertyId}"})
    public ResponseEntity<List<RatePlan>> getAllRatePlansForProperty(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal property Id. Required.")
            @Min(1000000)
                    Integer propertyId,
            HttpServletRequest req
    ) {
        validationManager.validatePropertyAccess(req, propertyId);

        List<RatePlan> all = ratePlanManager.getAllRatePlansForProperty(propertyId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Get rate plan data by id."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<RatePlan> getRatePlan(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal rate plan Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        validationManager.validateRatePlanAccess(id, req);

        return new ResponseEntity<>(ratePlanManager.getRatePlanById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Add Rate Plan."
    )
    @PostMapping
    public ResponseEntity<RatePlan> addRatePlan(
            @Valid
            @RequestBody
                    RatePlanSaveRequest request,
            HttpServletRequest req
    ) {
        validationManager.validatePropertyAccess(req, request.getPropertyId());

        RatePlan ratePlan = ratePlanManager.addRatePlan(request);

        return new ResponseEntity<>(ratePlan, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update Rate Plan."
    )
    @PutMapping("/{id}")
    public ResponseEntity<RatePlan> updateRatePlan(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal Rate Plan Id. Required.")
            @Min(1)
                    Integer id,
            @Valid
            @RequestBody
                    RatePlanUpdateRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateRatePlanAccess(id, req);

        RatePlan ratePlan = ratePlanManager.updateRatePlan(id, request);

        return ResponseEntity.ok(ratePlan);
    }

    @Operation(
            summary = "Delete Rate Plan by id."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRatePlan(
            @Valid
            @PathVariable
            @Parameter(description = "RoomDB internal rate plan Id. Required.")
            @Min(1)
                    Integer id,
            HttpServletRequest req
    ) {
        validationManager.validateRatePlanAccess(id, req);

        ratePlanManager.deleteRatePlan(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
