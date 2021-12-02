package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.DescriptionType;
import com.cs.roomdbapi.manager.DescriptionManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Tag(
        name = "Description",
        description = "API endpoints to access Descriptions and additional information about them."
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/description", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class DescriptionController {

    private final DescriptionManager descriptionManager;

    @Operation(
            summary = "Get list of all description types available for descriptions."
    )
    @GetMapping({"/description-types"})
    public ResponseEntity<List<DescriptionType>> getAllDescriptionTypes() {
        List<DescriptionType> all = descriptionManager.getAllDescriptionTypes();

        return new ResponseEntity<>(all, HttpStatus.OK);
    }

}
