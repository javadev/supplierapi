package com.cs.roomdbapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingModelUpdateRequest {

    @JsonProperty
    @Size(min = 1)
    @Schema(description = "Name of the pricing model", example = "Standard price for single room.")
    private String name;

    @JsonProperty
    @Min(1)
    @Schema(description = "Room DB internal pricing model type id.", example = "3")
    private Integer pricingModelTypeId;

    @Valid
    @JsonProperty
    private StandardPricingModel standardPricingModel;

    @Valid
    @JsonProperty
    private DerivedPricingModel derivedPricingModel;

    @Valid
    @JsonProperty
    private OccupancyBasedPricingModel occupancyBasedPricingModel;

    @Valid
    @JsonProperty
    private LengthOfStayPricingModel lengthOfStayPricingModel;

}
