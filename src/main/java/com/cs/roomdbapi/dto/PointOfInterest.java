package com.cs.roomdbapi.dto;

import com.cs.roomdbapi.model.DescriptionEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointOfInterest {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty
    @Min(1000000)
    @Schema(description = "Room DB internal property id.", example = "1000003")
    private Integer propertyId;

    @JsonProperty
    @Schema(description = "Category code for the point of interest.")
    private CategoryCode categoryCode;

    @JsonProperty
    @Schema(description = "Name of the point of interest", example = "mt. Elbrus")
    private String name;

    @JsonProperty
    @Schema(description = "Distance to the point of interest", example = "42.195")
    private BigDecimal distance;

    @JsonProperty
    @Schema(description = "Distance unit in which distance is measured", example = "kilometer")
    private String distanceUnit;

    @JsonProperty
    @Schema(description = "Language that is primary at the point of interest")
    private Language language;

    @JsonProperty
    @Schema(description = "Descriptions of the Point Of Interest")
    private Set<Description> descriptions;

}
