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
public class PricingModel {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty
    @Min(1000000)
    @Schema(description = "Room DB Property Id of the property", example = "1000040")
    private Integer propertyId;

    @JsonProperty
    @Schema(description = "Name of the pricing model", example = "Standard price for single room.")
    private String name;

    @Schema(description = "Pricing model type.")
    private PricingModelType pricingModelType;

    @JsonProperty
    private StandardPricingModel standardPricingModel;

    @JsonProperty
    private DerivedPricingModel derivedPricingModel;

    @JsonProperty
    private OccupancyBasedPricingModel occupancyBasedPricingModel;

    @JsonProperty
    private LengthOfStayPricingModel lengthOfStayPricingModel;

}
