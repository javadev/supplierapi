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
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HomeCurrencyByCode {

    @JsonProperty
    @Min(1000000)
    @NotNull
    @Schema(description = "Room DB internal property id.", example = "1000010")
    private Integer propertyId;

    @JsonProperty
    @NotNull
    @Size(min = 3, max = 3)
    @Schema(description = "Currency 3 latter code (ISO 4217). Can be used instead homeCurrencyId.", example = "USD")
    private String homeCurrencyCode;

}
