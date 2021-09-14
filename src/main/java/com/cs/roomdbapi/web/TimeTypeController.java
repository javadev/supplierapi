package com.cs.roomdbapi.web;

import com.cs.roomdbapi.model.TimeType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Tag(
        name = "Time Types",
        description = "API endpoints to access Time Types for the sellable units."
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/time-types", produces = MediaType.APPLICATION_JSON_VALUE)
public class TimeTypeController {

    @Operation(
            summary = "Get list of all time types.",
            description = "All fields of the time Type entity will be included in result."
    )
    @GetMapping
    public ResponseEntity<List<String>> getAllTimeTypes() {

        List<String> types = new ArrayList<>();
        for (TimeType type : TimeType.values()) {
            types.add(type.getCode());
        }

        return new ResponseEntity<>(types, HttpStatus.OK);
    }

}
