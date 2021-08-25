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
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointOfInterestSaveRequest {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty
    @Min(1000000)
    @Schema(description = "Room DB internal property id.", example = "1000003")
    private Integer propertyId;

    @JsonProperty
    @Min(1)
    @Schema(description = "Category code id for the point of interest.")
    private Integer categoryCodeId;

    @JsonProperty
    @NotBlank(message = "Name should be provided.")
    @Schema(description = "Name of the point of interest", example = "mt. Elbrus")
    private String name;

    @JsonProperty
    @Schema(description = "Distance to the point of interest", example = "42.195")
    private BigDecimal distance;

    @JsonProperty
    @Schema(description = "Distance unit in which distance is measured", example = "kilometer")
    private String distanceUnit;

    @JsonProperty
    @Min(1)
    @Schema(description = "Language id of language that is primary at the point of interest")
    private Integer languageId;

}
