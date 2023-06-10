package com.onepopol.member;

import com.onepopol.member.dto.MemberLoginRequest;
import com.onepopol.member.dto.MemberSignupRequest;
import com.onepopol.member.dto.TokenDto;
import com.onepopol.member.service.MemberAuthenticationService;
import com.onepopol.member.service.MemberManagementService;
import com.onepopol.utils.ApiResult;
import com.onepopol.utils.Apiutils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberController {

    private final MemberAuthenticationService memberAuthenticationService;
    private final MemberManagementService memberManagementService;
    private final BCryptPasswordEncoder encoder;

    private final long COOKIE_EXPIRATION = 7776000; // 90일

    // 회원가입
    @PostMapping("/signup")
    public ApiResult<?> signup(@RequestBody @Valid MemberSignupRequest memberSignupRequest) {
        String encodedPassword = encoder.encode(memberSignupRequest.getPassword());
        MemberSignupRequest newMemberSignupRequest = MemberSignupRequest.encodePassword(memberSignupRequest, encodedPassword);

        memberManagementService.registerUser(newMemberSignupRequest);
        return Apiutils.success("회원가입 성공");
    }

    // 로그인 -> 토큰 발급
    @PostMapping("/login")
    public ApiResult<?> login(@RequestBody @Valid MemberLoginRequest memberLoginRequest, HttpServletResponse response) {
        // User 등록 및 Refresh Token 저장
        TokenDto tokenDto = memberAuthenticationService.login(memberLoginRequest);

        // RT 저장
        HttpCookie httpCookie = ResponseCookie.from("refresh-token", tokenDto.getRefreshToken())
                .maxAge(COOKIE_EXPIRATION)
                .httpOnly(true)
                .secure(true)
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, httpCookie.toString());
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + tokenDto.getAccessToken());

        return Apiutils.success("로그인 성공");
    }

    // 유효하지 않은 token이나 blank 값을 보낼시 HttpStatus.OK 리턴
    @PostMapping("/validate")
    public ApiResult<?> validate(@RequestHeader("Authorization") String requestAccessToken) {
        if (!memberAuthenticationService.validate(requestAccessToken)) {
            return Apiutils.success("Valid"); // 재발급 필요X
        } else {
            return Apiutils.success("unValid"); // 재발급 필요
        }
    }
    // 토큰 재발급
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@CookieValue(name = "refresh-token") String requestRefreshToken,
                                     @RequestHeader("Authorization") String requestAccessToken) {
        TokenDto reissuedTokenDto = memberAuthenticationService.reissue(requestAccessToken, requestRefreshToken);

        if (reissuedTokenDto != null) { // 토큰 재발급 성공
            // RT 저장
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", reissuedTokenDto.getRefreshToken())
                    .maxAge(COOKIE_EXPIRATION)
                    .httpOnly(true)
                    .secure(true)
                    .build();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    // AT 저장
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + reissuedTokenDto.getAccessToken())
                    .build();

        } else { // Refresh Token 탈취 가능성
            // Cookie 삭제 후 재로그인 유도
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
                    .maxAge(0)
                    .path("/")
                    .build();
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                    .build();
        }
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String requestAccessToken) {
        memberAuthenticationService.logout(requestAccessToken);
        ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
                .maxAge(0)
                .path("/")
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }
}