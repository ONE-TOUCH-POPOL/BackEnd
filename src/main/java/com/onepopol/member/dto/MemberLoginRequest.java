package com.onepopol.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLoginRequest {
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Pattern(regexp = "^[A-Z0-9.%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d).{6,}$", message = "비밀번호 형식이 올바르지 않습니다.")
    private String password;

    @Builder
    public MemberLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
