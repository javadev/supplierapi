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
@JsonIgnoreProperties(ignoreUnknown=true)
public class State {

	@JsonProperty
	private Integer id;

	@JsonProperty
	@Schema(description = "Country code (ISO 3166 Alpha-2)", example = "us")
	private String countryCode;

	@JsonProperty
	@Schema(description = "State code (ISO 3166-2 code)", example = "US-CA")
	private String code;

	@JsonProperty
	@Schema(description = "State (subdivision) name", example = "California")
	private String name;

	@JsonProperty
	@Schema(description = "State local name variant", example = "Local name for California")
	private String localName;

	@JsonProperty
	@Schema(description = "Language code (ISO 639-1 two-letter)", example = "en")
	private String languageCode;

	@JsonProperty
	@Schema(description = "State (subdivision) category", example = "state")
	private String subdivisionCategory;

}
