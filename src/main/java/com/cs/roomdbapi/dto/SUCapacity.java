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
public class SUCapacity {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Min(1)
    @Schema(description = "Room DB internal sellable unit id.", example = "12")
    private Integer sellableUnitId;

    @JsonProperty
    @Min(0)
    @Schema(description = "Capacity for specified period.", example = "30")
    private Integer capacity;

    @JsonProperty
    @Schema(description = "Is this period blockout period.", example = "false")
    private Boolean isBlockout;

    @JsonProperty
    @Schema(description = "Time range for this capacity entry.")
    private TimeRange timeRange;

}
