package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.AvailabilityResult;
import com.cs.roomdbapi.dto.Calendar;
import com.cs.roomdbapi.dto.SellableUnitAvailabilityRequest;
import com.cs.roomdbapi.dto.SellableUnitCalendarRequest;
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
import java.util.List;

@Slf4j
@Tag(
        name = "Sellable Unit Calendar",
        description = "API endpoints to access Sellable Unit Calendar based information."
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/sellable-unit/calendar", produces = MediaType.APPLICATION_JSON_VALUE)
public class SellableUnitCalendarController {

    private final SellableUnitManager sellableUnitManager;

    private final ValidationManager validationManager;

    @Operation(
            summary = "Get sellable unit availabilities."
    )
    @GetMapping({"/availabilities/{sellableUnitId}"})
    public ResponseEntity<List<AvailabilityResult>> getSellableUnitAvailabilities(
            @PathVariable
            @Parameter(description = "RoomDB internal Sellable Unit Id. Required.")
            @Min(1)
                    Integer sellableUnitId,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(sellableUnitId, req);

        List<AvailabilityResult> all = sellableUnitManager.getAvailabilitiesBySellableUnitId(sellableUnitId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Set/add availabilities to sellable unit.",
            description = "If availability for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/availabilities/set"})
    public ResponseEntity<List<AvailabilityResult>> setAvailabilities(
            @Valid
            @RequestBody
                    SellableUnitAvailabilityRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<AvailabilityResult> all = sellableUnitManager.setAvailabilitiesToSellableUnit(request.getSellableUnitId(), request.getAvailabilities());

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get sellable unit calendar entries.",
            description = "Result will include all date based fields ro the sellable unit."
    )
    @GetMapping({"/{sellableUnitId}"})
    public ResponseEntity<List<Calendar>> getSellableUnitCalendarEntries(
            @PathVariable
            @Parameter(description = "RoomDB internal Sellable Unit Id. Required.")
            @Min(1)
                    Integer sellableUnitId,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(sellableUnitId, req);

        List<Calendar> all = sellableUnitManager.getCalendarRowsBySellableUnitId(sellableUnitId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Set/add calendar entries to sellable unit.",
            description = "If calendar fields for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/"})
    public ResponseEntity<List<Calendar>> setSellableUnitCalendarEntries(
            @Valid
            @RequestBody
                    SellableUnitCalendarRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<Calendar> all = sellableUnitManager.setCalendarRowsToSellableUnit(request.getSellableUnitId(), request.getCalendars());

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

}
