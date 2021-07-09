package com.cs.roomdbapi.dto;

import com.cs.roomdbapi.utilities.AppUtils;
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
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {

    @JsonProperty
    @Min(0)
    @Schema(description = "Id of the notification event", example = "476")
    private Integer id;

    @JsonProperty
    @NotBlank(message = "Entity name should be provided.")
    @Size(min = 1, max = 255)
    @Schema(description = "Name of the updated entity", example = AppUtils.LANGUAGE)
    private String entityName;

    @JsonProperty
    @Min(0)
    @Schema(description = "Id of the updated entity", example = "3")
    private Integer entityId;

    @JsonProperty
    @Schema(description = "Creation time of the notification")
    private Timestamp updateTime;
}
