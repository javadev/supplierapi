package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.*;
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
    public ResponseEntity<List<SUAvailabilityResult>> getSellableUnitAvailabilities(
            @PathVariable
            @Parameter(description = "RoomDB internal Sellable Unit Id. Required.")
            @Min(1)
                    Integer sellableUnitId,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(sellableUnitId, req);

        List<SUAvailabilityResult> all = sellableUnitManager.getAvailabilitiesBySellableUnitId(sellableUnitId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Set/add availabilities to sellable unit.",
            description = "If availability for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/availabilities/set"})
    public ResponseEntity<List<SUAvailabilityResult>> setAvailabilities(
            @Valid
            @RequestBody
                    SUAvailabilityRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUAvailabilityResult> all = sellableUnitManager.setAvailabilitiesToSellableUnit(request.getSellableUnitId(), request.getAvailabilities());

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Set/add availabilities to sellable unit. For date range",
            description = "Start and end date of the date range should be provided. Both start and end date will be **included**.<br/>" +
                    "It's possible to specify which week days from range should be included. " +
                    "If weekday is **not specified** or if it's set to **false** it will not be included. <br/><br/>" +
                    "If availability for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/availabilities/set/date-range"})
    public ResponseEntity<List<SUAvailabilityResult>> setAvailabilitiesDateRange(
            @Valid
            @RequestBody
                    SUAvailabilityDateRangeRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUAvailabilityResult> all = sellableUnitManager.setAvailabilitiesToSellableUnitForDateRange(request);

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get sellable unit prices."
    )
    @GetMapping({"/price/{sellableUnitId}"})
    public ResponseEntity<List<SUPriceResult>> getSellableUnitPrices(
            @PathVariable
            @Parameter(description = "RoomDB internal Sellable Unit Id. Required.")
            @Min(1)
                    Integer sellableUnitId,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(sellableUnitId, req);

        List<SUPriceResult> all = sellableUnitManager.getPricesBySellableUnitId(sellableUnitId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Set/add prices to sellable unit.",
            description = "If price for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/price/set"})
    public ResponseEntity<List<SUPriceResult>> setPrices(
            @Valid
            @RequestBody
                    SUPriceRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUPriceResult> all = sellableUnitManager.setPricesToSellableUnit(request.getSellableUnitId(), request.getPrices());

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Set/add prices to sellable unit. For date range.",
            description = "Start and end date of the date range should be provided. Both start and end date will be **included**.<br/>" +
                    "It's possible to specify which week days from range should be included. " +
                    "If weekday is **not specified** or if it's set to **false** it will not be included. <br/><br/>" +
                    "If price for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/price/set/date-range"})
    public ResponseEntity<List<SUPriceResult>> setPricesDateRange(
            @Valid
            @RequestBody
                    SUPriceDateRangeRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUPriceResult> all = sellableUnitManager.setPricesToSellableUnitForDateRange(request);

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get sellable unit commissions."
    )
    @GetMapping({"/commission/{sellableUnitId}"})
    public ResponseEntity<List<SUCommissionResult>> getSellableUnitCommissions(
            @PathVariable
            @Parameter(description = "RoomDB internal Sellable Unit Id. Required.")
            @Min(1)
                    Integer sellableUnitId,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(sellableUnitId, req);

        List<SUCommissionResult> all = sellableUnitManager.getCommissionsBySellableUnitId(sellableUnitId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Set/add commissions to sellable unit.",
            description = "If commission for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/commission/set"})
    public ResponseEntity<List<SUCommissionResult>> setCommissions(
            @Valid
            @RequestBody
                    SUCommissionRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUCommissionResult> all = sellableUnitManager.setCommissionsToSellableUnit(request.getSellableUnitId(), request.getCommissions());

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Set/add commissions to sellable unit. For date range.",
            description = "Start and end date of the date range should be provided. Both start and end date will be **included**.<br/>" +
                    "It's possible to specify which week days from range should be included. " +
                    "If weekday is **not specified** or if it's set to **false** it will not be included. <br/><br/>" +
                    "If commission for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/commission/set/date-range"})
    public ResponseEntity<List<SUCommissionResult>> setCommissionsDateRange(
            @Valid
            @RequestBody
                    SUCommissionDateRangeRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUCommissionResult> all = sellableUnitManager.setCommissionsToSellableUnitForDateRange(request);

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get sellable unit minimum length of stay records."
    )
    @GetMapping({"/minLOS/{sellableUnitId}"})
    public ResponseEntity<List<SUMinLOSResult>> getSellableUnitMinLOSRecords(
            @PathVariable
            @Parameter(description = "RoomDB internal Sellable Unit Id. Required.")
            @Min(1)
                    Integer sellableUnitId,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(sellableUnitId, req);

        List<SUMinLOSResult> all = sellableUnitManager.getMinLOSRecordsBySellableUnitId(sellableUnitId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Set/add minimum length of stay records to sellable unit.",
            description = "If minimum length of stay record for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/minLOS/set"})
    public ResponseEntity<List<SUMinLOSResult>> setMinLOSRecords(
            @Valid
            @RequestBody
                    SUMinLOSRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUMinLOSResult> all = sellableUnitManager.setMinLOSRecordsToSellableUnit(request.getSellableUnitId(), request.getMinLOSRecords());

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Set/add minimum length of stay records to sellable unit. For date range",
            description = "Start and end date of the date range should be provided. Both start and end date will be **included**.<br/>" +
                    "It's possible to specify which week days from range should be included. " +
                    "If weekday is **not specified** or if it's set to **false** it will not be included. <br/><br/>" +
                    "If minimum length of stay records for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/minLOS/set/date-range"})
    public ResponseEntity<List<SUMinLOSResult>> setMinLOSRecordsDateRange(
            @Valid
            @RequestBody
                    SUMinLOSDateRangeRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUMinLOSResult> all = sellableUnitManager.setMinLOSRecordsToSellableUnitForDateRange(request);

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get sellable unit maximum length of stay records."
    )
    @GetMapping({"/maxLOS/{sellableUnitId}"})
    public ResponseEntity<List<SUMaxLOSResult>> getSellableUnitMaxLOSRecords(
            @PathVariable
            @Parameter(description = "RoomDB internal Sellable Unit Id. Required.")
            @Min(1)
                    Integer sellableUnitId,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(sellableUnitId, req);

        List<SUMaxLOSResult> all = sellableUnitManager.getMaxLOSRecordsBySellableUnitId(sellableUnitId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Set/add maximum length of stay records to sellable unit.",
            description = "If maximum length of stay record for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/maxLOS/set"})
    public ResponseEntity<List<SUMaxLOSResult>> setMaxLOSRecords(
            @Valid
            @RequestBody
                    SUMaxLOSRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUMaxLOSResult> all = sellableUnitManager.setMaxLOSRecordsToSellableUnit(request.getSellableUnitId(), request.getMaxLOSRecords());

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Set/add maximum length of stay records to sellable unit. For date range",
            description = "Start and end date of the date range should be provided. Both start and end date will be **included**.<br/>" +
                    "It's possible to specify which week days from range should be included. " +
                    "If weekday is **not specified** or if it's set to **false** it will not be included. <br/><br/>" +
                    "If maximum length of stay records for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/maxLOS/set/date-range"})
    public ResponseEntity<List<SUMaxLOSResult>> setMaxLOSRecordsDateRange(
            @Valid
            @RequestBody
                    SUMaxLOSDateRangeRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUMaxLOSResult> all = sellableUnitManager.setMaxLOSRecordsToSellableUnitForDateRange(request);

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get sellable unit closed for sale records."
    )
    @GetMapping({"/closedForSale/{sellableUnitId}"})
    public ResponseEntity<List<SUClosedForSaleResult>> getSellableUnitClosedForSaleRecords(
            @PathVariable
            @Parameter(description = "RoomDB internal Sellable Unit Id. Required.")
            @Min(1)
                    Integer sellableUnitId,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(sellableUnitId, req);

        List<SUClosedForSaleResult> all = sellableUnitManager.getClosedForSaleRecordsBySellableUnitId(sellableUnitId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Set/add closed for sale records to sellable unit.",
            description = "If closed for sale record for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/closedForSale/set"})
    public ResponseEntity<List<SUClosedForSaleResult>> setClosedForSaleRecords(
            @Valid
            @RequestBody
                    SUClosedForSaleRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUClosedForSaleResult> all = sellableUnitManager.setClosedForSaleRecordsToSellableUnit(request.getSellableUnitId(), request.getClosedForSaleRecords());

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Set/add closed for sale records to sellable unit. For date range",
            description = "Start and end date of the date range should be provided. Both start and end date will be **included**.<br/>" +
                    "It's possible to specify which week days from range should be included. " +
                    "If weekday is **not specified** or if it's set to **false** it will not be included. <br/><br/>" +
                    "If closed for sale records for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/closedForSale/set/date-range"})
    public ResponseEntity<List<SUClosedForSaleResult>> setClosedForSaleRecordsDateRange(
            @Valid
            @RequestBody
                    SUClosedForSaleDateRangeRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUClosedForSaleResult> all = sellableUnitManager.setClosedForSaleRecordsToSellableUnitForDateRange(request);

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get sellable unit closed for arrival records."
    )
    @GetMapping({"/closedForArrival/{sellableUnitId}"})
    public ResponseEntity<List<SUClosedForArrivalResult>> getSellableUnitClosedForArrivalRecords(
            @PathVariable
            @Parameter(description = "RoomDB internal Sellable Unit Id. Required.")
            @Min(1)
                    Integer sellableUnitId,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(sellableUnitId, req);

        List<SUClosedForArrivalResult> all = sellableUnitManager.getClosedForArrivalRecordsBySellableUnitId(sellableUnitId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Set/add closed for arrival records to sellable unit.",
            description = "If closed for arrival record for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/closedForArrival/set"})
    public ResponseEntity<List<SUClosedForArrivalResult>> setClosedForArrivalRecords(
            @Valid
            @RequestBody
                    SUClosedForArrivalRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUClosedForArrivalResult> all = sellableUnitManager.setClosedForArrivalRecordsToSellableUnit(request.getSellableUnitId(), request.getClosedForArrivalRecords());

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Set/add closed for arrival records to sellable unit. For date range",
            description = "Start and end date of the date range should be provided. Both start and end date will be **included**.<br/>" +
                    "It's possible to specify which week days from range should be included. " +
                    "If weekday is **not specified** or if it's set to **false** it will not be included. <br/><br/>" +
                    "If closed for arrival records for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/closedForArrival/set/date-range"})
    public ResponseEntity<List<SUClosedForArrivalResult>> setClosedForArrivalRecordsDateRange(
            @Valid
            @RequestBody
                    SUClosedForArrivalDateRangeRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUClosedForArrivalResult> all = sellableUnitManager.setClosedForArrivalRecordsToSellableUnitForDateRange(request);

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get sellable unit closed for departure records."
    )
    @GetMapping({"/closedForDeparture/{sellableUnitId}"})
    public ResponseEntity<List<SUClosedForDepartureResult>> getSellableUnitClosedForDepartureRecords(
            @PathVariable
            @Parameter(description = "RoomDB internal Sellable Unit Id. Required.")
            @Min(1)
                    Integer sellableUnitId,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(sellableUnitId, req);

        List<SUClosedForDepartureResult> all = sellableUnitManager.getClosedForDepartureRecordsBySellableUnitId(sellableUnitId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Set/add closed for departure records to sellable unit.",
            description = "If closed for departure record for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/closedForDeparture/set"})
    public ResponseEntity<List<SUClosedForDepartureResult>> setClosedForDepartureRecords(
            @Valid
            @RequestBody
                    SUClosedForDepartureRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUClosedForDepartureResult> all = sellableUnitManager.setClosedForDepartureRecordsToSellableUnit(request.getSellableUnitId(), request.getClosedForDepartureRecords());

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Set/add closed for departure records to sellable unit. For date range",
            description = "Start and end date of the date range should be provided. Both start and end date will be **included**.<br/>" +
                    "It's possible to specify which week days from range should be included. " +
                    "If weekday is **not specified** or if it's set to **false** it will not be included. <br/><br/>" +
                    "If closed for departure records for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/closedForDeparture/set/date-range"})
    public ResponseEntity<List<SUClosedForDepartureResult>> setClosedForDepartureRecordsDateRange(
            @Valid
            @RequestBody
                    SUClosedForDepartureDateRangeRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUClosedForDepartureResult> all = sellableUnitManager.setClosedForDepartureRecordsToSellableUnitForDateRange(request);

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get sellable unit calendar entries.",
            description = "Result will include all date based fields ro the sellable unit."
    )
    @GetMapping({"/{sellableUnitId}"})
    public ResponseEntity<List<SUCalendar>> getSellableUnitCalendarEntries(
            @PathVariable
            @Parameter(description = "RoomDB internal Sellable Unit Id. Required.")
            @Min(1)
                    Integer sellableUnitId,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(sellableUnitId, req);

        List<SUCalendar> all = sellableUnitManager.getCalendarRowsBySellableUnitId(sellableUnitId);

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Set/add calendar entries to sellable unit.",
            description = "If calendar fields for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/"})
    public ResponseEntity<List<SUCalendar>> setSellableUnitCalendarEntries(
            @Valid
            @RequestBody
                    SUCalendarRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUCalendar> all = sellableUnitManager.setCalendarRowsToSellableUnit(request.getSellableUnitId(), request.getCalendars());

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Set/add calendar entries to sellable unit. For date range",
            description = "Start and end date of the date range should be provided. Both start and end date will be **included**.<br/>" +
                    "It's possible to specify which week days from range should be included. " +
                    "If weekday is **not specified** or if it's set to **false** it will not be included. <br/><br/>" +
                    "If calendar entries for specific date exists in RoomDB it will be overridden with provided data. <br/>" +
                    "Time segment is not required and will be empty if not provided. <br/>" +
                    "If time segment provided existing data will be overridden with provided data."
    )
    @PostMapping({"/date-range"})
    public ResponseEntity<List<SUCalendar>> setSellableUnitCalendarEntriesDateRange(
            @Valid
            @RequestBody
                    SUCalendarDateRangeRequest request,
            HttpServletRequest req
    ) {
        validationManager.validateSellableUnitAccess(request.getSellableUnitId(), req);

        List<SUCalendar> all = sellableUnitManager.setCalendarRowsToSellableUnitForDateRange(request);

        return new ResponseEntity<>(all, HttpStatus.CREATED);
    }

}
