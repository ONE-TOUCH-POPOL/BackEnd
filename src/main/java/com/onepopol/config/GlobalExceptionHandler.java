package com.onepopol.config;

import com.onepopol.utils.ApiResult;
import com.onepopol.utils.Apiutils;
import org.springframework.validation.FieldError;
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
        for (FieldError fieldError : ex.getFieldErrors()) {
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            Object rejectedValue = fieldError.getRejectedValue();
            return Apiutils.error(errorMessage, 1000);
        }
        return Apiutils.error("Valid 에러", 1000);
    }
}
