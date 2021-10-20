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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SUCalendar {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Min(1)
    @Schema(description = "Room DB internal sellable unit id.", example = "12")
    private Integer sellableUnitId;

    @JsonProperty
    @NotNull
    @Schema(description = "Data for the calendar data.", example = "2021-09-16")
    private LocalDate date;

    @JsonProperty
    @Min(0)
    @Schema(description = "Count of the available sellable units.", example = "30")
    private Integer countAvailable;

    @JsonProperty
    @Min(0)
    @Schema(description = "Price of the sellable unit for this date", example = "22.50")
    private BigDecimal price;

    @JsonProperty
    @Min(0)
    @Schema(description = "Minimum length of stay for sellable unit for this date.", example = "1")
    private Integer minLOS;

    @JsonProperty
    @Min(0)
    @Schema(description = "Maximum length of stay for sellable unit for this date.", example = "7")
    private Integer maxLOS;

    @JsonProperty
    @Schema(description = "Is sellable units closed for sale for this date", example = "false")
    private Boolean closedForSale;

    @JsonProperty
    @Schema(description = "Is sellable units closed for arrival for this date", example = "true")
    private Boolean closedForArrival;

    @JsonProperty
    @Schema(description = "Is sellable units closed for departure for this date", example = "true")
    private Boolean closedForDeparture;

    @JsonProperty
    @Schema(description = "Time segment of the calendar data.", example = "07:00")
    private LocalTime timeSegment;

}
