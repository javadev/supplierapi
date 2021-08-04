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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaAttributeSaveOne {

    @JsonProperty
    @Min(1)
    @Schema(description = "Room DB internal media id.", example = "3")
    private Integer mediaId;

    @JsonProperty
    private Integer mediaAttributeTypeId;

    @JsonProperty
    @NotBlank(message = "Value should be provided.")
    @Size(min = 1, max = 255)
    @Schema(description = "Value of the media attribute", example = "720")
    private String value;

    @JsonProperty
    @Size(min = 1, max = 255)
    @Schema(description = "Dimension of the media attribute", example = "pixel")
    private String dimension;

}
