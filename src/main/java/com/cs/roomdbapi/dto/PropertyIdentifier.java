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
public class PropertyIdentifier {

    @JsonProperty
    @Min(1000000)
    @Schema(description = "Room DB Property Id of the property", example = "1000040")
    private Integer propertyId;

    @JsonProperty
    @Min(1)
    @Schema(description = "Identifier for the property, for specified source", example = "40")
    private String identifier;

    @JsonProperty
    private IdentifierSource source;

}
