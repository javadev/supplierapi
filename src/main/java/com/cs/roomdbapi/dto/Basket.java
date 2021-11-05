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
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Basket {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty
    @Size(min = 1, max = 255)
    @Schema(description = "Name of the Basket", example = "Double room with included massage")
    private String name;

    @JsonProperty
    @Min(1000000)
    @Schema(description = "Room DB internal property id.", example = "1000003")
    private Integer propertyId;

    @JsonProperty
    @Schema(description = "Is this basket visible.", example = "true")
    private Boolean isVisible;

    @JsonProperty
    @Schema(description = "Descriptions for the Media")
    private List<Description> descriptions;

}
