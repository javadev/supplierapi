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
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SUDateRangeRequest {

    @JsonProperty
    @Min(1)
    @NotNull
    @Schema(description = "Room DB internal sellable unit id.", example = "12")
    private Integer sellableUnitId;

    @JsonProperty
    @NotNull
    @Schema(description = "Start data for the availability data. This date included.", example = "2021-09-02")
    private LocalDate startDate;

    @JsonProperty
    @NotNull
    @Schema(description = "End data for the availability data. This date included.", example = "2021-09-10")
    private LocalDate endDate;

    @JsonProperty
    @Schema(description = "Include all Mondays from date range, by default false.", example = "true")
    private Boolean monday;

    @JsonProperty
    @Schema(description = "Include all Tuesdays from date range, by default false.", example = "false")
    private Boolean tuesday;

    @JsonProperty
    @Schema(description = "Include all Wednesdays from date range, by default false.", example = "false")
    private Boolean wednesday;

    @JsonProperty
    @Schema(description = "Include all Thursdays from date range, by default false.", example = "true")
    private Boolean thursday;

    @JsonProperty
    @Schema(description = "Include all Fridays from date range, by default false.", example = "false")
    private Boolean friday;

    @JsonProperty
    @Schema(description = "Include all Saturdays from date range, by default false.", example = "true")
    private Boolean saturday;

    @JsonProperty
    @Schema(description = "Include all Sundays from date range, by default false.", example = "true")
    private Boolean sunday;

    @JsonProperty
    @Schema(description = "Time segment of the availability data for date range.", example = "07:00")
    private LocalTime timeSegment;

}
