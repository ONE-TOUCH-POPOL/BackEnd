package com.onepopol.config;

import com.onepopol.utils.ApiResult;
import com.onepopol.utils.Apiutils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ApiResult<?> handleAllExceptions(BaseException ex) {
        return Apiutils.error(ex.getApiError());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ApiResult<?> handleAllExceptions(ValidationException ex) {
        return Apiutils.error(ex.getMessage(), 1000);
    }
}
