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
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DerivedPricingModel {

    @JsonProperty
    @Min(value = 1, message = "Minimum occupancy is 1.")
    @Schema(description = "Standard occupancy.", example = "2")
    private Integer standardOccupancy;

    @JsonProperty
    @Schema(description = "Standard price", example = "30.55")
    private BigDecimal standardPrice;

    @JsonProperty
    @Min(value = 1, message = "Minimum value for minimum age is 1.")
    @Schema(description = "Minimum age.", example = "3")
    private Integer minAge;

    @JsonProperty
    @Min(value = 1, message = "Minimum value for maximum age is 1.")
    @Schema(description = "Maximum age.", example = "99")
    private Integer maxAge;

    @Valid
    @JsonProperty
    private List<DerivedPricingModelOptions> options;
}
