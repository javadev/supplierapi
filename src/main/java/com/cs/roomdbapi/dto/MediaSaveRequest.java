package com.cs.roomdbapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaSaveRequest {

    @JsonProperty
    @Min(1000000)
    @NotNull(message = "Property id should be provided.")
    @Schema(description = "Room DB internal property id.", example = "1000003")
    private Integer propertyId;

    @JsonProperty
    @Min(1)
    @Schema(description = "Room DB internal media type id.", example = "3")
    private Integer mediaTypeId;

    @JsonProperty
    @Min(1)
    @Schema(description = "Room DB internal license type id.", example = "5")
    private Integer licenseTypeId;

    @JsonProperty
    @Schema(description = "Specifies whether the image should be displayed as the main photo for the property",
            example = "false")
    private Boolean isMain;

    @JsonProperty
    @Schema(description = "Is the image a logo for the property", example = "false")
    private Boolean isLogo;

    @JsonProperty
    @Min(1)
    @Max(1000)
    @Schema(description = "Numeric value that should be used to order media. Max value is 1000", example = "3")
    private Integer sortOrder;

    @JsonProperty
    @NotBlank(message = "Media url should be provided.")
    @URL(message = "Media url should be in proper url format")
    private String url;

}
