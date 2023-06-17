package com.onepopol.member;

import com.onepopol.config.BaseException;
import com.onepopol.config.ValidationException;
import com.onepopol.member.dto.MemberLoginRequest;
import com.onepopol.member.dto.MemberSignupRequest;
import com.onepopol.member.dto.TokenDto;
import com.onepopol.member.service.MemberAuthenticationService;
import com.onepopol.member.service.MemberManagementService;
import com.onepopol.utils.ApiResult;
import com.onepopol.utils.Apiutils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin
public class MemberController {

    private final MemberAuthenticationService memberAuthenticationService;
    private final MemberManagementService memberManagementService;
    private final BCryptPasswordEncoder encoder;

    private final long COOKIE_EXPIRATION = 7776000; // 90일

    // 회원가입
    @PostMapping("/signup")
    public ApiResult<?> signup(@RequestBody @Valid MemberSignupRequest memberSignupRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            throw new ValidationException(fieldErrors);
        }

        try {
            memberManagementService.registerUser(memberSignupRequest);
            return Apiutils.success("회원가입 성공");
        } catch (BaseException e) {
            throw new BaseException(e.getApiError());
        }
    }

    // 로그인 -> 토큰 발급
    @PostMapping("/login")
    public ApiResult<?> login(@RequestBody @Valid MemberLoginRequest memberLoginRequest, BindingResult bindingResult, HttpServletResponse response) {
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

    // 토큰 재발급
    @PostMapping("/reissue")
    public ApiResult<?> reissue(@CookieValue(name = "refresh-token") String requestRefreshToken,
                                @RequestHeader("Authorization") String requestAccessToken,
                                HttpServletResponse response) {
        try {
            TokenDto reissuedTokenDto = memberAuthenticationService.reissue(requestAccessToken, requestRefreshToken);

            // RT 저장
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", reissuedTokenDto.getRefreshToken())
                    .maxAge(COOKIE_EXPIRATION)
                    .httpOnly(true)
                    .secure(true)
                    .path("/")
                    .build();
            response.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
            response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + reissuedTokenDto.getAccessToken());

            return Apiutils.success("Access Token 재발급 성공");
        } catch (BaseException e) {
            // Cookie 삭제 후 재로그인 유도
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
                    .maxAge(0)
                    .path("/")
                    .build();
            response.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
            throw new BaseException(e.getApiError());
        }
    }

    @PostMapping("/test")
    public ApiResult<?> test(@CookieValue(name = "refresh-token") String requestRefreshToken) {
        System.out.println(requestRefreshToken);
        return Apiutils.success("Access Token 재발급 성공");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ApiResult<?> logout(@RequestHeader("Authorization") String requestAccessToken, HttpServletResponse response) {
        try {
            ResponseCookie responseCookie = ResponseCookie.from("refresh-token", "")
                    .maxAge(0)
                    .path("/")
                    .build();

            response.setHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

            memberAuthenticationService.logout(requestAccessToken);

            return Apiutils.success("로그아웃 성공");
        } catch (BaseException e) {
            throw new BaseException(e.getApiError());
        }
    }

    @PostMapping("/signup/checkDuplicateEmail")
    public ApiResult<?> checkDuplicateEmail(@RequestBody Map<String, String> requestBody) {
        try {
            memberManagementService.checkDuplicateEmail(requestBody.get("email"));

            return Apiutils.success("valid"); // 중복 아님
        } catch (BaseException e) {
            throw new BaseException(e.getApiError());
        }
    }
}