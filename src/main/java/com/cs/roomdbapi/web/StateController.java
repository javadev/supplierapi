package com.cs.roomdbapi.web;

import com.cs.roomdbapi.dto.State;
import com.cs.roomdbapi.manager.StateManager;
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
        name = "States",
        description = "API endpoints to access States, districts or any other country subdivisions."
)
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/states", produces = MediaType.APPLICATION_JSON_VALUE)
public class StateController {

    private final StateManager stateManager;

    @Operation(
            summary = "Get list of all States.",
            description = "All fields of the State entity will be included in result."
    )
    @GetMapping
    public ResponseEntity<List<State>> getAllPropertyTypes() {
        List<State> propertyTypes = stateManager.getAll();

        return new ResponseEntity<>(propertyTypes, HttpStatus.OK);
    }

    @Operation(
            summary = "Get state data by id."
    )
    @GetMapping({"/{stateId}"})
    public ResponseEntity<State> getState(
            @PathVariable
            @Parameter(description = "RoomDB internal state Id. Required.")
            @Min(1)
                    Integer stateId
    ) {

        return new ResponseEntity<>(stateManager.getById(stateId), HttpStatus.OK);
    }

    @Operation(
            summary = "Get state data by its code (ISO 3166-2)."
    )
    @GetMapping({"/by-code/{code}"})
    public ResponseEntity<State> getStateByCode(
            @PathVariable
            @Parameter(description = "State code (ISO 3166-2). Required.")
            @Size(min = 2, max = 8)
                    String code
    ) {

        return new ResponseEntity<>(stateManager.getStateByCode(code.toUpperCase()), HttpStatus.OK);
    }

    @Operation(
            summary = "Get list of all states for country. Country code should be in ISO 3166 Alpha-2 format."
    )
    @GetMapping({"/by-country-code/{countryCode}"})
    public ResponseEntity<List<State>> getStatesByCountryCode(
            @PathVariable
            @Parameter(description = "Two letters country code (ISO 3166 Alpha-2). Required.")
            @Size(min = 2, max = 2)
                    String countryCode
    ) {

        return new ResponseEntity<>(stateManager.getStatesByCountryCode(countryCode.toUpperCase()), HttpStatus.OK);
    }
}
