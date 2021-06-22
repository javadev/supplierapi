package com.cs.roomdbapi.advice;

import com.cs.roomdbapi.annotation.IgnoreResponseBinding;
import com.cs.roomdbapi.response.ErrorResponse;
import com.cs.roomdbapi.response.SuccessResponse;
import com.cs.roomdbapi.utilities.AppUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

/*
*
* If the method is part of REST Controller, is not the instance of SuccessResponse or ErrorResponse
* and also not annotated with IgnoreResponseBinding the magic happens.
*
*/

@ControllerAdvice
public class CustomResponseAdvise implements ResponseBodyAdvice<Object> {

    @Value("${springdoc.packagesToScan}")
    String webPackages;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object obj, MethodParameter methodParameter,
                                  MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
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

                        return new SuccessResponse<>(obj, msg);
                    }
                }
            }
        }
        return obj;
    }


}
