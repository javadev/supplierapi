package com.cs.roomdbapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Supplier {

    @JsonProperty
    private Integer id;

    @JsonProperty
    @Schema(description = "Supplier name", example = "Extranet application")
    private String name;

    @JsonProperty
    @Schema(description = "WebHook url to send notifications to", example = "http://supplier.domain.com/notifiactions")
    private String webhook;

    @JsonProperty
    @Schema(description = "Is supplier account is active or not", example = "true")
    private Boolean isActive;

}
