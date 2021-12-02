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
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasketSellableUnitRequest {

    @JsonProperty
    @Min(value = 1, message = "Basket Id should be greater or equals to '1'")
    @Schema(description = "Room DB internal basket id.", example = "12332")
    private Integer basketId;

    @JsonProperty
    @Valid
    @Schema(description = "Sellable Units for basket.")
    private List<BasketSellableUnitSave> sellableUnits;

}
