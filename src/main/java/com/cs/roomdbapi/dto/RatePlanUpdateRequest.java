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
public class RatePlanUpdateRequest {

    @JsonProperty
    @NotBlank(message = "Name should be provided.")
    @Size(min = 1, max = 255)
    @Schema(description = "Name for the Rate Plan", example = "Rate Plan for single rooms.")
    private String name;

    @JsonProperty
    @Schema(description = "Parent Rate Plan id.", example = "21")
    private Integer parentId;

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
    @Schema(description = "Payment policy id for Rate Plan.")
    private Integer paymentPolicyId;

    @JsonProperty
    @Schema(description = "Cancellation policy id for Rate Plan.")
    private Integer cancellationPolicyId;

    @JsonProperty
    @Schema(description = "Booking policy id for Rate Plan.")
    private Integer bookingPolicyId;

    @JsonProperty
    @Schema(description = "Policy Info id for Rate Plan.")
    private Integer policyInfoId;

    @JsonProperty
    @Schema(description = "Pet policy id for Rate Plan.")
    private Integer petPolicyId;

    @JsonProperty
    @Min(0)
    @Schema(description = "What commission assume this Rate Plan?", example = "55.90")
    private BigDecimal commission;

}
