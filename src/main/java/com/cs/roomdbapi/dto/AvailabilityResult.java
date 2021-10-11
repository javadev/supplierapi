package com.cs.roomdbapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailabilityResult {

    @Min(1)
    @Schema(description = "Room DB internal sellable unit id.", example = "12")
    private Integer sellableUnitId;

    @JsonProperty
    @Schema(description = "Data for the calendar data.", example = "2021-09-16")
    private LocalDate date;

    @JsonProperty
    @Min(0)
    @Schema(description = "Count of the available sellable units.", example = "30")
    private Integer countAvailable;

    @JsonProperty
    @Schema(description = "Time segment of the calendar data.", example = "07:00")
    private LocalTime timeSegment;

}
