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
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DerivedPricingModelOptions {

    @JsonProperty
    @Min(value = 1, message = "Minimum value is 1.")
    @Schema(description = "Number of persons.", example = "4")
    private Integer numberOfPersons;

    @JsonProperty
    @Schema(description = "Standard price", example = "3.11")
    private BigDecimal derivePercentage;

    @JsonProperty
    @Schema(description = "Standard price", example = "27.75")
    private BigDecimal derivePrice;

}
