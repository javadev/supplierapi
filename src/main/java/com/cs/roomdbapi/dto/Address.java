package com.cs.roomdbapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Address {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty
    @Schema(description = "Address line", example = "221b Baker St")
    private String addressLine;

    @JsonProperty
    @Schema(description = "City", example = "London")
    private String cityName;

    @JsonProperty
    @Schema(description = "Postal code, also known as zip code", example = "NW1 6XE")
    private String postalCode;

    @JsonProperty
    @Schema(description = "Language used for address")
    private Language language;

    @JsonProperty
    @Schema(description = "Country")
    private Country country;

    @JsonProperty
    @Schema(description = "State or province")
    private State state;

}
