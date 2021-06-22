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
public class Language {

	@JsonProperty
	private Integer id;
	
	@JsonProperty
	@Schema(description = "ISO 639-1 two-letter code", example = "de")
	private String code;

	@JsonProperty
	@Schema(description = "ISO 639 Language name", example = "German")
	private String name;

	@JsonProperty
	@Schema(description = "Native language name (endonym)", example = "Deutsch")
	private String nativeName;

	@JsonProperty
	@Schema(description = "ISO 639-2/T three-letter code", example = "deu")
	private String code2T;

	@JsonProperty
	@Schema(description = "ISO 639-2/B three-letter code", example = "ger")
	private String code2B;

	@JsonProperty
	@Schema(description = "ISO 639-3 three-letter code", example = "deu")
	private String code3;

}
