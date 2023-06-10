package com.onepopol.config;

public class ValidationException extends RuntimeException {
    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message); // RuntimeException 클래스의 생성자를 호출합니다.
    }


}
