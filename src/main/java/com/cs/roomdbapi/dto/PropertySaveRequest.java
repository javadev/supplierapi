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
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertySaveRequest {

    @JsonProperty
    @Size(min = 1, max = 255)
    @Schema(description = "Property id that is used in supplier system", example = "1232")
    private String supplierPropertyId;

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

    @JsonProperty
    @Schema(description = "Emails for property.")
    private List<EmailSave> emails;

    @JsonProperty
    @Schema(description = "Phones for property.")
    private List<PhoneSave> phones;

    @JsonProperty
    @Min(1)
    @Schema(description = "Room DB internal currency id.", example = "12")
    private Integer homeCurrencyId;

    @JsonProperty
    @Size(min = 3, max = 3)
    @Schema(description = "Currency 3 latter code (ISO 4217). Can be used instead homeCurrencyId.", example = "USD")
    private String homeCurrencyCode;

}
