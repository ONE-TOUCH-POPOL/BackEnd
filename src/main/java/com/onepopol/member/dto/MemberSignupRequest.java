package com.onepopol.member.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignupRequest {
    private String email;
    private String password;

    @Builder
    public MemberSignupRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static MemberSignupRequest encodePassword(MemberSignupRequest memberSignupRequest, String encodedPassword) {
        MemberSignupRequest newMemberSignupRequest = new MemberSignupRequest();
        newMemberSignupRequest.email = memberSignupRequest.getEmail();
        newMemberSignupRequest.password = encodedPassword;
        return newMemberSignupRequest;
    }
}