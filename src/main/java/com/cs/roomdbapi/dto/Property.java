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
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Property {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty
    @Size(min = 1, max = 255)
    @Schema(description = "CultSwitch id - property id that is used in CultSwitch. Also known as ObjectId.", example = "1232")
    private String cultSwitchId;

    @JsonProperty
    @Size(min = 1, max = 255)
    @Schema(description = "Code of the property", example = "GT-01")
    private String code;

    @JsonProperty
    @Size(min = 1, max = 255)
    @Schema(description = "Name of the property", example = "Golden Tulip")
    private String name;

    @JsonProperty
    @Size(min = 1, max = 255)
    @Schema(description = "Alternative name of the property", example = "Local name for Golden Tulip")
    private String alternativeName;

    @JsonProperty
    @Size(min = 1, max = 255)
    @Schema(description = "Status of the property", example = "active")
    private String status;              // TODO define possible statuses, in enum with converter

    @JsonProperty
    @Schema(description = "Is this property for testing purpose", example = "false")
    private Boolean forTesting;

    @JsonProperty
    @Schema(description = "Is data from Room DB should be used as master for this property.", example = "false")
    private Boolean isMaster;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Property home currency")
    private Currency homeCurrency;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Phones of the Property")
    private List<Phone> phones;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Emails of the Property")
    private List<Email> emails;

    @JsonProperty
    @Schema(description = "Descriptions of the Property")
    private List<Description> descriptions;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Supplier supplier;

}
