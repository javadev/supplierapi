package com.cs.roomdbapi.advice;

import com.cs.roomdbapi.annotation.IgnoreResponseBinding;
import com.cs.roomdbapi.exception.BadRequestException;
import com.cs.roomdbapi.exception.CustomException;
import com.cs.roomdbapi.exception.InvalidTokenException;
import com.cs.roomdbapi.exception.ResourceNotFoundException;
import com.cs.roomdbapi.response.ErrorResponse;
import com.cs.roomdbapi.response.SuccessResponse;
import com.cs.roomdbapi.utilities.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.error.ErrorAttributeOptions;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
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
    public ResponseEntity<SuccessResponse<Object>> resolveException(BadRequestException ex) {
        SuccessResponse<Object> response = new SuccessResponse<>(ex.getObject(), ex.getMessage(), AppUtils.FAIL);
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseBody
    public ResponseEntity<SuccessResponse<Object>> resolveException(ResourceNotFoundException ex) {
        SuccessResponse<Object> response = new SuccessResponse<>(null, ex.getMessage(), AppUtils.FAIL);

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        // Hide exception field in the return object
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, options);
                errorAttributes.remove("exception");
                errorAttributes.remove("trace");
                return errorAttributes;
            }
        };
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<SuccessResponse<Object>> handleCustomException(HttpServletResponse res, CustomException ex) throws IOException {
        SuccessResponse<Object> response = new SuccessResponse<>(null, ex.getMessage(), AppUtils.FAIL);

        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<SuccessResponse<Object>> handleConflict(RuntimeException ex) {
        SuccessResponse<Object> response = new SuccessResponse<>(null, ex.getMessage(), AppUtils.FAIL);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<SuccessResponse<Object>> handleAccessDeniedException(HttpServletResponse res) {
        SuccessResponse<Object> response = new SuccessResponse<>(null, "Access denied", AppUtils.FAIL);

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<SuccessResponse<Object>> handleInvalidTokenException(HttpServletResponse res, InvalidTokenException ex) {
        SuccessResponse<Object> response = new SuccessResponse<>(null, ex.getMessage(), AppUtils.FAIL);

        return new ResponseEntity<>(response, ex.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public void handleException(HttpServletResponse res, Exception ex) throws IOException {
        log.error("ExceptionHandled: ", ex);
        res.sendError(HttpStatus.BAD_REQUEST.value(), "Something went wrong");
    }

    @ExceptionHandler({
            ConstraintViolationException.class,
            MethodArgumentTypeMismatchException.class
    })
    public void handleConstraintViolationException(HttpServletResponse res, Exception ex) throws IOException {
        if (ex.getMessage().contains("must be greater")) {
            log.error("ConstraintViolationException: {}", ex.getMessage());
        } else {
            log.error("ConstraintViolationException: ", ex);
        }

        res.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

}
