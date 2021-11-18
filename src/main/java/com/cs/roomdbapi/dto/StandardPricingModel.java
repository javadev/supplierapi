package com.cs.roomdbapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StandardPricingModel {

    @JsonProperty
    @Schema(description = "Price for a single person", example = "20.25")
    private BigDecimal singlePersonPrice;

    @JsonProperty
    @Schema(description = "Price for a maximum number of persons", example = "40.45")
    private BigDecimal maximumPersonsPrice;

}
