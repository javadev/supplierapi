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
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertyInfo {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty
    @Min(1000000)
    @Schema(description = "Room DB Property Id of the property", example = "1000040")
    private Integer propertyId;

    @JsonProperty
    @Size(min = 1, max = 255)
    @Schema(description = "Website of the property", example = "marriott.com")
    private String website;

    @JsonProperty
    private GeoCode geoCode;

    @JsonProperty
    private Brand brand;

    @JsonProperty
    @Size(min = 1, max = 255)
    @Schema(description = "Taxpayer Id of the property", example = "1232 1233")
    private String taxpayerId;

    @JsonProperty
    @Min(1)
    @Schema(description = "Capacity of the property", example = "40")
    private Integer capacity;

    @JsonProperty
    @Schema(description = "Capacity type that describes capacity unit of the property", example = "room")
    private String capacityType;

    @JsonProperty
    @Schema(description = "Exists flag for the property", example = "true")
    private Boolean isExist;

    @JsonProperty
    private PropertyType propertyType;

    @JsonProperty
    private List<PropertyInfoLanguage> languages;

    @JsonProperty
    private List<Address> addresses;

}
