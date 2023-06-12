package com.onepopol.config;

import com.onepopol.utils.ApiError;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    private ApiError apiError;

    public BaseException() {

    }

    public BaseException(String message) {
        super(message); // RuntimeException 클래스의 생성자를 호출합니다.
    }

    public BaseException(ApiError apiError) {
        this.apiError = apiError;
    }

}
