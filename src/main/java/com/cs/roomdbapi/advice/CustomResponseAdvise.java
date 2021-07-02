package com.cs.roomdbapi.advice;

import com.cs.roomdbapi.annotation.IgnoreResponseBinding;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.exception.CustomException;
import com.cs.roomdbapi.response.ErrorResponse;
import com.cs.roomdbapi.response.SuccessResponse;
import com.cs.roomdbapi.utilities.AppUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class CustomResponseAdvise implements ResponseBodyAdvice<Object> {

    @Value("${springdoc.packagesToScan}")
    String webPackages;

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /*
     *
     * If the method is part of REST Controller, is not the instance of SuccessResponse or ErrorResponse
     * and also not annotated with IgnoreResponseBinding the magic happens.
     *
     */
    @Override
    public Object beforeBodyWrite(Object obj, MethodParameter methodParameter,
                                  @NonNull MediaType mediaType, @NonNull Class aClass,
                                  @NonNull ServerHttpRequest serverHttpRequest, @NonNull ServerHttpResponse serverHttpResponse) {
        if (methodParameter.getContainingClass().isAnnotationPresent(RestController.class)) {

            if (methodParameter.getContainingClass().getPackageName().equals(webPackages)) {
                if (!methodParameter.getMethod().isAnnotationPresent(IgnoreResponseBinding.class)) {
                    if ((!(obj instanceof ErrorResponse)) && (!(obj instanceof SuccessResponse))) {

                        String msg = AppUtils.RESPONSE_CODE_SUCCESS_MSG;
                        if (obj instanceof List) {
                            if (((List) obj).size() == 0) {
                                msg = AppUtils.RESPONSE_CODE_NO_DATA_MSG;
                            }
                        } else if (obj == null) {
                            msg = AppUtils.RESPONSE_CODE_NO_DATA_MSG;
                        }

                        return new SuccessResponse<>(obj, msg, AppUtils.SUCCESS);
                    }
                }
            }
        }
        return obj;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public SuccessResponse<Object> resolveException(BadRequestException exception) {
        return new SuccessResponse<>(exception.getObject(), exception.getMessage(), AppUtils.FAIL);
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        // Hide exception field in the return object
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
                errorAttributes.remove("exception");
                errorAttributes.remove("trace");
                return errorAttributes;
            }
        };
    }

    @ExceptionHandler(CustomException.class)
    public void handleCustomException(HttpServletResponse res, CustomException ex) throws IOException {
        res.sendError(ex.getHttpStatus().value(), ex.getMessage());
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<String> handleConflict(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(HttpServletResponse res) throws IOException {
        res.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
    }

    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletResponse res) throws IOException {
        res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
    }

}
