package com.cs.roomdbapi.response;

import com.cs.roomdbapi.utilities.AppUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T trace;

    @JsonProperty
    private final String status = AppUtils.FAIL;

    private String message;

    public ErrorResponse(T object, String message) {
        this.trace = object;
        this.message = message;
    }
}
