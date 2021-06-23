package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.PropertyType;
import com.cs.roomdbapi.manager.PropertyTypeManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Tag(
        name = "Property types",
        description = "API endpoints to access Property Types."
)
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/property-types", produces = MediaType.APPLICATION_JSON_VALUE)
public class PropertyTypeController {

    private final PropertyTypeManager propertyTypeManager;

    @Operation(
            summary = "Get list of all property types.",
            description = "All fields of the Property Type entity will be included in result."
    )
    @GetMapping
    public ResponseEntity<List<PropertyType>> getAllPropertyTypes() {
        List<PropertyType> propertyTypes = propertyTypeManager.getPropertyTypes();

        return new ResponseEntity<>(propertyTypes, HttpStatus.OK);
    }

    @Operation(
            summary = "Get property type data by id."
    )
    @GetMapping({"/{propertyTypeId}"})
    public ResponseEntity<PropertyType> getPropertyType(
            @PathVariable
            @Parameter(description = "RoomDB internal property type Id. Required.")
            @Min(1)
                    Integer propertyTypeId
    ) {

        return new ResponseEntity<>(propertyTypeManager.getPropertyTypeById(propertyTypeId), HttpStatus.OK);
    }

    @Operation(
            summary = "Get property type data by code value."
    )
    @GetMapping({"/code/{code}"})
    public ResponseEntity<PropertyType> getPropertyTypeByCode(
            @PathVariable
            @Parameter(description = "Code (OTA, Booking, CultSwitch) for specific Property Type. Required.")
            @Size(min = 1, max = 4)
                    String code
    ) {

        return new ResponseEntity<>(propertyTypeManager.getPropertyTypeByCode(code), HttpStatus.OK);
    }

}
