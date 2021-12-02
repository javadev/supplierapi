package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.PhoneType;
import com.cs.roomdbapi.manager.PhoneTypeManager;
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
        name = "Phone Types",
        description = "API endpoints to access Phone Types."
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/phone-types", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class PhoneTypeController {

    private final PhoneTypeManager phoneTypeManager;

    @Operation(
            summary = "Get list of all phone types.",
            description = "All fields of the Phone Type entity will be included in result."
    )
    @GetMapping
    public ResponseEntity<List<PhoneType>> getAllPhoneType() {
        List<PhoneType> phoneTypes = phoneTypeManager.getPhoneType();

        return new ResponseEntity<>(phoneTypes, HttpStatus.OK);
    }

    @Operation(
            summary = "Get phone type data by id."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<PhoneType> getPhoneType(
            @PathVariable
            @Parameter(description = "RoomDB internal phone type Id. Required.")
            @Min(1)
                    Integer id
    ) {

        return new ResponseEntity<>(phoneTypeManager.getPhoneTypeById(id), HttpStatus.OK);
    }

}
