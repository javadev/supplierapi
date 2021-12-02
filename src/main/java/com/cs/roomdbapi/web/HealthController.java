package com.cs.roomdbapi.web;

import com.cs.roomdbapi.response.SuccessResponse;
import com.cs.roomdbapi.utilities.AppUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(
        name = "Health",
        description = "API endpoints to health check."
)
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/health", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class HealthController {

    @Operation(
            summary = "Helper method to check application availability."
    )
    @GetMapping("/test")
    public SuccessResponse<String> test() {

        return new SuccessResponse<>(null, "Application is up and running.", AppUtils.SUCCESS);
    }

}
