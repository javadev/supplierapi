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

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasketSellableUnitSaveOne {

    @JsonProperty
    @Min(value = 1, message = "Basket Id should be greater or equals to '1'")
    @Schema(description = "Room DB internal basket id.", example = "12332")
    private Integer basketId;

    @JsonProperty
    @Min(value = 1, message = "Sellable Unit Id should be greater or equals to '1'")
    private Integer sellableUnitId;

    @JsonProperty
    @Min(value = 1, message = "Minimum quantity is 1.")
    @Schema(description = "Quantity of sellable units within basket.", example = "5")
    private Integer quantity;

    @JsonProperty
    @Schema(description = "Is sellable units are consecutive over the time", example = "true")
    private Boolean consecutiveOverTime;

}
