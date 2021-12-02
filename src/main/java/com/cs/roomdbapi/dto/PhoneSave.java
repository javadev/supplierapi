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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PhoneSave {

    @JsonProperty
    @NotNull
    private Integer phoneTypeId;

    @JsonProperty
    @NotBlank(message = "Phone number should be provided.")
    @Size(min = 1, max = 50)
    @Schema(description = "Phone number", example = "123-23-23")
    private String phoneNumber;

    @JsonProperty
    @Size(min = 1, max = 25)
    @Schema(description = "Phone extension", example = "#43")
    private String extension;

    @JsonProperty
    @Schema(description = "Is provided phone validated.", example = "false")
    private Boolean isValidated;

}
