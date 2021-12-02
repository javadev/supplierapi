package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.EmailType;
import com.cs.roomdbapi.manager.EmailTypeManager;
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

import javax.validation.constraints.Min;
import java.util.List;

@Tag(
        name = "Email Types",
        description = "API endpoints to access Email Types."
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/email-types", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class EmailTypeController {

    private final EmailTypeManager emailTypeManager;

    @Operation(
            summary = "Get list of all email types.",
            description = "All fields of the Brand entity will be included in result."
    )
    @GetMapping
    public ResponseEntity<List<EmailType>> getAllBrands() {
        List<EmailType> emailTypes = emailTypeManager.getEmailTypes();

        return new ResponseEntity<>(emailTypes, HttpStatus.OK);
    }

    @Operation(
            summary = "Get email type data by id."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<EmailType> getBrandType(
            @PathVariable
            @Parameter(description = "RoomDB internal email type Id. Required.")
            @Min(1)
                    Integer id
    ) {

        return new ResponseEntity<>(emailTypeManager.getEmailTypeById(id), HttpStatus.OK);
    }

}
