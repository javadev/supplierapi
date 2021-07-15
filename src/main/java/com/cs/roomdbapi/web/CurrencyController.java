package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.Currency;
import com.cs.roomdbapi.manager.CurrencyManager;
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

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Tag(
        name = "Currencies",
        description = "API endpoints to access Currency information."
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/currencies", produces = MediaType.APPLICATION_JSON_VALUE)
public class CurrencyController {

    private final CurrencyManager currencyManager;

    @Operation(
            summary = "Get list of all currencies.",
            description = "All fields of the Currency entity will be included in result."
    )
    @GetMapping
    public ResponseEntity<List<Currency>> getAllCurrencies() {
        List<Currency> currencies = currencyManager.getCurrencies();

        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @Operation(
            summary = "Get currency data by id."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<Currency> getCurrency(
            @PathVariable
            @Parameter(description = "RoomDB internal currency Id. Required.")
            @Min(1)
                    Integer id
    ) {

        return new ResponseEntity<>(currencyManager.getCurrencyById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Get currency data by ISO 4217 three-letter code."
    )
    @GetMapping({"/letters-code/{code}"})
    public ResponseEntity<Currency> getCurrencyByLettersCode(
            @PathVariable
            @Parameter(description = "Three letters currency code (ISO 4217). Required.")
            @Size(min = 3, max = 3)
                    String code
    ) {

        return new ResponseEntity<>(currencyManager.getCurrencyByLettersCode(code.toUpperCase()), HttpStatus.OK);
    }

    @Operation(
            summary = "Get currency data by ISO 4217 numeric code."
    )
    @GetMapping({"/numeric-code/{code}"})
    public ResponseEntity<Currency> getCurrencyByNumericCode(
            @PathVariable
            @Parameter(description = "Numeric currency code (ISO 4217). Required.")
            @Min(1)
            @Max(999)
                    Integer code
    ) {

        return new ResponseEntity<>(currencyManager.getCurrencyByNumericCode(code), HttpStatus.OK);
    }

    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN)")
    @Operation(
            summary = "Add currency."
    )
    @PostMapping
    public ResponseEntity<Currency> addCurrency(
            @Valid @RequestBody Currency currency
    ) {
        Currency newCurrency = currencyManager.addCurrency(currency);

        return new ResponseEntity<>(newCurrency, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN)")
    @Operation(
            summary = "Update currency."
    )
    @PutMapping("/{id}")
    public ResponseEntity<Currency> updateCurrency(
            @PathVariable("id")
            @Parameter(description = "RoomDB internal currency Id. Required.")
            @Min(1) Integer id,
            @Valid @RequestBody Currency currency
    ) {
        Currency updatedCurrency = currencyManager.updateCurrency(id, currency);

        return ResponseEntity.ok(updatedCurrency);
    }

}
