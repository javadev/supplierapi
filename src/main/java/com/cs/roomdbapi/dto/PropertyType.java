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
public class PropertyType {

    @JsonProperty
    private Integer id;

    @JsonProperty
    @Schema(description = "Code of the property type", example = "16")
    private String code;

    @JsonProperty
    @Schema(description = "Name (appellation) of the property type", example = "Guest house limited service")
    private String name;

    @JsonProperty
    @Schema(description = "Source code describing the source of this type", example = "ota")
    private String codeSource;

    @JsonProperty
    @Schema(description = "Alternative name that might be used by other system", example = "Guest House")
    private String alternativeName;

}
