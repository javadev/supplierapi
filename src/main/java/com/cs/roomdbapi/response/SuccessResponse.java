package com.cs.roomdbapi.response;

import com.cs.roomdbapi.utilities.AppUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {

    @JsonProperty
    private Object result;

    @JsonProperty
    private final String status = AppUtils.SUCCESS;

    @JsonProperty
    private String message;

    public SuccessResponse(T object) {
        this.result = object;
    }

    public SuccessResponse(T object, String message) {
        this.result = object;
        this.message = message;
    }
}
