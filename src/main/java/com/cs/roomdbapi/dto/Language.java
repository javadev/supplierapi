package com.cs.roomdbapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Language {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty
    @NotBlank(message = "Code should be provided.")
    @Size(min = 2, max = 2)
    @Schema(description = "ISO 639-1 two-letter code", example = "de")
    private String code;

    @JsonProperty
    @NotBlank(message = "Name should be provided.")
    @Size(min = 1, max = 255)
    @Schema(description = "ISO 639 Language name", example = "German")
    private String name;

    @JsonProperty
    @Size(max = 255)
    @Schema(description = "Native language name (endonym)", example = "Deutsch")
    private String nativeName;

    @JsonProperty
    @Size(max = 15)
    @Schema(description = "ISO 639-2/T three-letter code", example = "deu")
    private String code2T;

    @JsonProperty
    @Size(max = 15)
    @Schema(description = "ISO 639-2/B three-letter code", example = "ger")
    private String code2B;

    @JsonProperty
    @Size(max = 15)
    @Schema(description = "ISO 639-3 three-letter code", example = "deu")
    private String code3;

}
