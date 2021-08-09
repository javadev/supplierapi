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
public class CategoryCode {

    @JsonProperty
    private Integer id;

    @JsonProperty
    @Schema(description = "Category Code value", example = "23")
    private String code;

    @JsonProperty
    @Schema(description = "Category Code name", example = "Historic building")
    private String name;

    @JsonProperty
    @Schema(description = "Source code describing the source of this category code", example = "ota")
    private String codeSource;

}
