package com.cs.roomdbapi.advice;

import com.cs.roomdbapi.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionAdvice {

    @Value("${trees.stacktrace}")
    boolean toStackTrace;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse<List<String>> processAllError(Exception ex) {
        List<String> stackTrace = null;

        if (toStackTrace) {
            List<StackTraceElement> elements = Arrays.asList(ex.getStackTrace());
            stackTrace = elements.stream().map(StackTraceElement::toString).collect(Collectors.toList());
        }

        return new ErrorResponse<>(stackTrace, ex.getMessage());
    }
}
