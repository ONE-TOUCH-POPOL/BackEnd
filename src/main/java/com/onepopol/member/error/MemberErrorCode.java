package com.onepopol.member.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode {
    INVALID_ACCESS(1001, "잘못된 접근입니다."),
    NO_ACCESS_RIGHTS(1002, "권한이 없습니다."),
    ACCESS_TOKEN_EXPIRED(1003, "Access Token이 만료되었습니다."),
    INVALID_ACCESS_TOKEN(1004, "잘못된 Access Token 입니다."),
    USER_NOT_FOUND(1005, "사용자를 찾을 수 없습니다."),
    INVALID_REFRESH_TOKEN(1006, "Refresh Token이 일치하지 않습니다."),
    NON_EXISTENT_REFRESH_TOKEN(1007, "존재하지 않는 Refresh Token 입니다."),
    ACCESS_TOKEN_NOT_FOUND(1008, "Access Token이 존재하지 않습니다.");

    private final int code;
    private final String message;
}
