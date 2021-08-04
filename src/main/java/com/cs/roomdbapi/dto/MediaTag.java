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

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaTag {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Min(1)
    @Schema(description = "Room DB internal media id.", example = "12")
    private Integer mediaId;

    @JsonProperty
    @Schema(description = "Tag text", example = "Landscape")
    private String text;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Predefined Tag id", example = "23")
    private Integer predefinedTagId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Predefined Tag")
    private PredefinedTag predefinedTag;

}
