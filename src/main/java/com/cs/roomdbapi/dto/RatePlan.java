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
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatePlan {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @JsonProperty
    @NotBlank(message = "Name should be provided.")
    @Size(min = 1, max = 255)
    @Schema(description = "Name for the Rate Plan", example = "Rate Plan for single rooms.")
    private String name;

    @JsonProperty
    @Min(1000000)
    @Schema(description = "Room DB internal property id.", example = "1000003")
    private Integer propertyId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Parent Rate Plan.")
    private RatePlan parent;

    @JsonProperty
    @Schema(description = "Is Rate Plan on stop selling?", example = "false")
    private Boolean stopSell;

    @JsonProperty
    @Schema(description = "Is Rate Plan assume closed to arrival?", example = "true")
    private Boolean closedToArrival;

    @JsonProperty
    @Schema(description = "Is Rate Plan assume closed to departure?", example = "true")
    private Boolean closedToDeparture;

    @JsonProperty
    @Schema(description = "Minimum length of stay within Rate Plan.", example = "1")
    private Integer minLengthOfStay;

    @JsonProperty
    @Schema(description = "Maximum length of stay within Rate Plan.", example = "20")
    private Integer maxLengthOfStay;

    @JsonProperty
    @Schema(description = "Payment policy for Rate Plan.")
    private Integer paymentPolicy;

    @JsonProperty
    @Schema(description = "Cancellation policy for Rate Plan.")
    private Integer cancellationPolicy;

    @JsonProperty
    @Schema(description = "Booking policy for Rate Plan.")
    private Integer bookingPolicy;

    @JsonProperty
    @Schema(description = "Policy Info for Rate Plan.")
    private Integer policyInfo;

    @JsonProperty
    @Schema(description = "Pet policy for Rate Plan.")
    private Integer petPolicy;

    @JsonProperty
    @Min(0)
    @Schema(description = "What commission assume this Rate Plan?", example = "55.90")
    private BigDecimal commission;

}
