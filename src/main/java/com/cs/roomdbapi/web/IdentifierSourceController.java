package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.IdentifierSource;
import com.cs.roomdbapi.manager.IdentifierSourceManager;
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

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Tag(
        name = "Identifier Sources",
        description = "API endpoints to access Identifier Sources."
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/identifier-source", produces = MediaType.APPLICATION_JSON_VALUE)
public class IdentifierSourceController {

    private final IdentifierSourceManager identifierSourceManager;

    @Operation(
            summary = "Get list of all identifier sources.",
            description = "All fields of the Identifier Source entity will be included in result."
    )
    @GetMapping
    public ResponseEntity<List<IdentifierSource>> getAll() {
        List<IdentifierSource> all = identifierSourceManager.getAll();

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @Operation(
            summary = "Get identifier source data by id."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<IdentifierSource> getIdentifierSource(
            @PathVariable
            @Parameter(description = "RoomDB internal identifier source Id. Required.")
            @Min(1)
                    Integer id
    ) {

        return new ResponseEntity<>(identifierSourceManager.getById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Get identifier source data by abbreviation value."
    )
    @GetMapping({"/abbreviation/{abbreviation}"})
    public ResponseEntity<IdentifierSource> getIdentifierSourceByAbbreviation(
            @PathVariable
            @Parameter(description = "Abbreviation (bkg, exp, cs etc.) for specific Identifier Source. Required.")
            @Size(min = 1, max = 4)
                    String abbreviation
    ) {

        return new ResponseEntity<>(identifierSourceManager.getByAbbreviation(abbreviation), HttpStatus.OK);
    }

}
