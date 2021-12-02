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
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SUMinLOSRequest {

    @JsonProperty
    @Min(1)
    @NotNull
    @Schema(description = "Room DB internal sellable unit id.", example = "12")
    private Integer sellableUnitId;

    @JsonProperty
    @Schema(description = "Minimum length of stay records for sellable unit.")
    private List<SUMinLOSSave> minLOSRecords;

}
