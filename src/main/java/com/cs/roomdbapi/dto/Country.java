package com.cs.roomdbapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Country {

	@JsonProperty
	private Integer id;
	
	@JsonProperty
	@Schema(description = "Country name", example = "Germany")
	private String name;

	@JsonProperty
	@Schema(description = "Country full name", example = "the Federal Republic of Germany")
	private String fullName;

	@JsonProperty
	@Schema(description = "ISO 3166 Alpha-2 code", example = "de")
	private String code;

	@JsonProperty
	@Schema(description = "ISO 3166 Alpha-3 code", example = "deu")
	private String codeA3;

	@JsonProperty
	@Schema(description = "ISO 3166 Numeric code", example = "276")
	private String codeNumeric;

}
