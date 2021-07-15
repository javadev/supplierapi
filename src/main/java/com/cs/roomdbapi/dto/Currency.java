package com.cs.roomdbapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty
    @NotBlank(message = "Code should be provided.")
    @Size(min = 3, max = 3)
    @Schema(description = "Currency 3 latter code (ISO 4217)", example = "USD")
    private String code;

    @JsonProperty
    @NotBlank(message = "Name should be provided.")
    @Size(min = 1, max = 255)
    @Schema(description = "Currency name.", example = "United States dollar")
    private String name;

    @JsonProperty
    @NotNull(message = "Numeric code should be provided.")
    @Min(1)
    @Max(999)
    @Schema(description = "Numeric code of the currency (ISO 4217)", example = "840")
    private Integer numericCode;

    @JsonProperty
    @Min(1)
    @Max(4)
    @Schema(description = "The number of digits after the decimal separator", example = "2")
    private Short minorUnit;

}
