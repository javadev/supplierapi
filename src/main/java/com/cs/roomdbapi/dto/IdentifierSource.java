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
public class IdentifierSource {

    @JsonProperty
    private Integer id;

    @JsonProperty
    @Schema(description = "Abbreviation of the identifier source", example = "exp")
    private String abbreviation;

    @JsonProperty
    @Schema(description = "Name of the identifier source", example = "Expedia")
    private String name;

}
