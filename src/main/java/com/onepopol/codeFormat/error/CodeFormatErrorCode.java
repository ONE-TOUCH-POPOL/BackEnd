package com.onepopol.codeFormat.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CodeFormatErrorCode {
    UNSUPPORTED_LANGUAGE(3000, "포매팅을 지원하지 않는 언어입니다."),
    FAIL_FORMATTING(3001, "JAVA 코드 포매팅에 실패하였습니다.");

    private final int code;
    private final String message;
}
