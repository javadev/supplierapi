package com.cs.roomdbapi.response;

import com.cs.roomdbapi.utilities.AppUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenResponse {

    @JsonProperty
    private final String tokenType = AppUtils.BEARER;

    @JsonProperty
    private long expiresIn;

    @JsonProperty
    private String accessToken;

    public TokenResponse(String accessToken, long expiresIn) {
        this.expiresIn = expiresIn;
        this.accessToken = accessToken;
    }
}
