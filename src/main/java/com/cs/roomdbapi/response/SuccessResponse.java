package com.cs.roomdbapi.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {

    @JsonProperty
    private Object result;

    @JsonProperty
    private String message;

    @JsonProperty
    private final String status;

    @JsonProperty("total")
    private Long total;

    @JsonProperty("totalPages")
    private Integer totalPages;

    public SuccessResponse(T object, String status) {
        this.result = object;
        this.status = status;
    }

    public SuccessResponse(T object, String msg, String status) {
        this.result = object;
        this.message = msg;
        this.status = status;
    }

}
