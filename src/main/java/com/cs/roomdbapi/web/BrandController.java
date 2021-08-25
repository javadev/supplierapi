package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.Brand;
import com.cs.roomdbapi.manager.BrandManager;
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
import java.util.List;

@Tag(
        name = "Brands",
        description = "API endpoints to access Brands for the properties."
)
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_ADMIN) " +
        "or hasRole(T(com.cs.roomdbapi.model.RoleName).ROLE_SUPPLIER_COMMON)")
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/brands", produces = MediaType.APPLICATION_JSON_VALUE)
public class BrandController {

    private final BrandManager brandManager;

    @Operation(
            summary = "Get list of all brands.",
            description = "All fields of the Brand entity will be included in result."
    )
    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        List<Brand> brands = brandManager.getBrands();

        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @Operation(
            summary = "Get brand data by id."
    )
    @GetMapping({"/{id}"})
    public ResponseEntity<Brand> getBrandType(
            @PathVariable
            @Parameter(description = "RoomDB internal brand Id. Required.")
            @Min(1)
                    Integer id
    ) {

        return new ResponseEntity<>(brandManager.getBrandById(id), HttpStatus.OK);
    }

}
