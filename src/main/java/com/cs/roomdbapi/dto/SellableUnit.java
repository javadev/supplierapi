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
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SellableUnit {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty
    @Min(1000000)
    @Schema(description = "Room DB Property Id of the property", example = "1000040")
    private Integer propertyId;

    // TODO review logic that this id could be duplicated if provided from different systems
    // make check that it's unique within supplier or within property? discuss.
    @JsonProperty
    @Schema(description = "Unit (room, meal, etc.) id that is used in supplier system.")
    private String supplierUnitId;

    @JsonProperty
    @Schema(description = "Names for property.")
    private List<Name> names;

    @JsonProperty
    @Schema(description = "Is number of such sellable units limited or not", example = "false")
    private Boolean limited;

    @JsonProperty
    @Schema(description = "Is sellable units are sold over the time", example = "true")
    private Boolean soldOverTime;

    @JsonProperty
    @Min(0)
    @Schema(description = "Base price of the sellable unit", example = "55.90")
    private BigDecimal basePrice;

    @JsonProperty
    @Min(1)
    @Schema(description = "Sellable unit type.")
    private SellableUnitType sellableUnitType;

    @JsonProperty
    @Schema(description = "Descriptions of the sellable unit")
    private List<Description> descriptions;

    @JsonProperty
    @Schema(description = "Time type that describes time period applicable for sellable unit", example = "day")
    private String timeType;

}
