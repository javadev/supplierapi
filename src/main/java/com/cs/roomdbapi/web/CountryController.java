package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.Country;
import com.cs.roomdbapi.manager.CountryManager;
import com.cs.roomdbapi.utilities.AppUtils;
import com.cs.roomdbapi.utilities.CountryCodeFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Tag(
        name = "Countries",
        description = "API endpoints to access country names and codes. <br/>" +
                "Each country has codes in different ISO formats: **Alpha-2**, **Alpha-3** and **Numeric**. <br/>" +
                "It's possible to pick format by using endpoint with *codeFormat* parameter. <br/><br/>" +

                "By default result include code in **Alpha-2** format only.<br/><br/>" +

                "**Name** and **full name** will be included in english (default) language.<br/>" +
                "Names on other languages can be retrieved by adding language code (*langCode*) parameter.<br/>" +
                "For *langCode* parameter ISO 639-1 two-letter codes are used.<br/>" +
                "If name for specified language does not exists in a system english version will be provided."
)
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/countries", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CountryController {

    private final CountryManager countryManager;

    @Operation(
            summary = "Get list of all countries with ISO 3166 Alpha-2 code.",
            description = "If you need other codes please use endpoint with **codeFormat** parameter.<br/>" +
                    "Language code parameter used for name and full name language."
    )
    @GetMapping
    public ResponseEntity<List<Country>> getAllCountries(
            @RequestParam(value = "langCode", required = false, defaultValue = AppUtils.DEFAULT_LANGUAGE_CODE)
            @Parameter(description = "Two letters language code (ISO 639-1). Optional. Default language is english.")
            @Size(min = 2, max = 2)
                    String langCode
    ) {
        List<Country> countries = countryManager.getCountriesByFormat(CountryCodeFormat.ALPHA2, langCode.toLowerCase());

        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @Operation(
            summary = "Get list of countries with specified code(s).",
            description = "Valid values for *codeFormat* are: <br/>" +
                    "- \"all\" to include ISO 3166 Alpha-2, Alpha-2 and Numeric code <br/>" +
                    "- \"alpha2\" to include ISO 3166 Alpha-2 code <br/>" +
                    "- \"alpha3\" to include ISO 3166 Alpha-3 code<br/>" +
                    "- \"numeric\" to include ISO 3166 Numeric code<br/><br/>" +
                    "Language code parameter used for name and full name language."
    )
    @GetMapping(value = {"/all/{codeFormat}"})
    public ResponseEntity<List<Country>> getAllCountriesForCode(
            @PathVariable
                    CountryCodeFormat codeFormat,
            @RequestParam(value = "langCode", required = false, defaultValue = AppUtils.DEFAULT_LANGUAGE_CODE)
            @Parameter(description = "Two letters language code (ISO 639-1). Optional. Default language is english.")
            @Size(min = 2, max = 2)
                    String langCode
    ) {
        List<Country> countries = countryManager.getCountriesByFormat(codeFormat, langCode.toLowerCase());

        return new ResponseEntity<>(countries, HttpStatus.OK);
    }

    @Operation(
            summary = "Get country data by id. Name, full name and codes will be included."
    )
    @GetMapping({"/{countryId}"})
    public ResponseEntity<Country> getCountry(
            @PathVariable
            @Parameter(description = "RoomDB internal country Id. Required.")
            @Min(1)
                    Integer countryId,
            @RequestParam(value = "langCode", required = false, defaultValue = AppUtils.DEFAULT_LANGUAGE_CODE)
            @Parameter(description = "Two letters language code (ISO 639-1). Optional. Default language is english.")
            @Size(min = 2, max = 2)
                    String langCode
    ) {
        return new ResponseEntity<>(countryManager.getCountryById(countryId, langCode.toLowerCase()), HttpStatus.OK);
    }

    @Operation(
            summary = "Get country data by Alpha-2 code. Name, full name and codes will be included."
    )
    @GetMapping({"/alpha-2-code/{code}"})
    public ResponseEntity<Country> getCountryByCode(
            @PathVariable
            @Parameter(description = "Two letters country code (ISO 3166 Alpha-2). Required.")
            @Size(min = 2, max = 2)
                    String code,
            @RequestParam(value = "langCode", required = false, defaultValue = AppUtils.DEFAULT_LANGUAGE_CODE)
            @Parameter(description = "Two letters language code (ISO 639-1). Optional. Default language is english.")
            @Size(min = 2, max = 2)
                    String langCode
    ) {
        return new ResponseEntity<>(countryManager.getCountryByCode(code, langCode.toLowerCase()), HttpStatus.OK);
    }

}
