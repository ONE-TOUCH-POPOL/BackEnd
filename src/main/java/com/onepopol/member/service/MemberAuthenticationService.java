package com.onepopol.member.service;

import com.onepopol.config.BaseException;
import com.onepopol.member.dto.MemberLoginRequest;
import com.onepopol.member.dto.TokenDto;
import com.onepopol.member.security.JwtTokenProvider;
import com.onepopol.utils.ApiError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.stream.Collectors;

import static com.onepopol.member.error.MemberErrorCode.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberAuthenticationService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRedisService memberRedisService;

    private final String SERVER = "Server";

    // 로그인: 인증 정보 저장 및 비어 토큰 발급
    @Transactional
    public TokenDto login(MemberLoginRequest memberLoginRequest) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(memberLoginRequest.getEmail(), memberLoginRequest.getPassword());

            Authentication authentication = authenticationManagerBuilder.getObject()
                    .authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return generateToken(SERVER, authentication.getName(), getAuthorities(authentication));
    }

    // 토큰 재발급: validate 메서드가 true 반환할 때만 사용 -> AT, RT 재발급
    @Transactional
    public TokenDto reissue(String requestAccessTokenInHeader, String requestRefreshToken) {
        String requestAccessToken = resolveToken(requestAccessTokenInHeader);

        Authentication authentication = jwtTokenProvider.getAuthentication(requestAccessToken);
        String principal = getPrincipal(requestAccessToken);

        String refreshTokenInRedis = getRefreshTokenInRedis(principal); // Redis에 RT 저장 되어있는지 && 값 확인

        // 요청된 RT의 유효성 검사 & Redis에 저장되어 있는 RT와 같은지 비교
        if (jwtTokenProvider.validateRefreshToken(requestRefreshToken) && refreshTokenInRedis.equals(requestRefreshToken)) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String authorities = getAuthorities(authentication);

            // 토큰 재발급 및 Redis 업데이트
            memberRedisService.deleteValues("RT(" + SERVER + "):" + principal); // 기존 RT 삭제
            TokenDto tokenDto = jwtTokenProvider.createToken(principal, authorities);
            saveRefreshToken(SERVER, principal, tokenDto.getRefreshToken());
            return tokenDto;
        }
        memberRedisService.deleteValues("RT(" + SERVER + "):" + principal); // 탈취 가능성 -> 삭제
        throw new BaseException(new ApiError(INVALID_REFRESH_TOKEN.getMessage(), INVALID_REFRESH_TOKEN.getCode())); // 재로그인 요청
    }

    // 토큰 발급
    @Transactional
    private TokenDto generateToken(String provider, String email, String authorities) {
        // RT가 이미 있을 경우
        if(memberRedisService.getValues("RT(" + provider + "):" + email) != null) {
            memberRedisService.deleteValues("RT(" + provider + "):" + email); // 삭제
        }

        // AT, RT 생성 및 Redis에 RT 저장
        TokenDto tokenDto = jwtTokenProvider.createToken(email, authorities);
        saveRefreshToken(provider, email, tokenDto.getRefreshToken());
        return tokenDto;
    }

    // RT를 Redis에 저장
    @Transactional
    private void saveRefreshToken(String provider, String principal, String refreshToken) {
        memberRedisService.setValuesWithTimeout("RT(" + provider + "):" + principal, // key
                refreshToken, // value
                jwtTokenProvider.getTokenExpirationTime(refreshToken)); // timeout(milliseconds)
    }

    // 권한 이름 가져오기
    public String getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    // AT로부터 principal 추출
    public String getPrincipal(String requestAccessToken) {
        return jwtTokenProvider.getAuthentication(requestAccessToken).getName();
    }

    private String getRefreshTokenInRedis(String principal){
        String refreshTokenInRedis = memberRedisService.getValues("RT(" + SERVER + "):" + principal);
        if (refreshTokenInRedis == null) {
            throw new BaseException(new ApiError(NON_EXISTENT_REFRESH_TOKEN.getMessage(), NON_EXISTENT_REFRESH_TOKEN.getCode()));
        }
        return refreshTokenInRedis;
    }

    // "Bearer {AT}"에서 {AT} 추출
    private String resolveToken(String requestAccessTokenInHeader) {
        if (requestAccessTokenInHeader == null) {
            throw new BaseException(new ApiError(ACCESS_TOKEN_NOT_FOUND.getMessage(), ACCESS_TOKEN_NOT_FOUND.getCode()));
        }
        if(requestAccessTokenInHeader.startsWith("Bearer ")){
            return requestAccessTokenInHeader.substring(7);
        }
        throw new BaseException(new ApiError(INVALID_ACCESS_TOKEN.getMessage(), INVALID_ACCESS_TOKEN.getCode()));
    }

    // 로그아웃
    @Transactional
    public void logout(String requestAccessTokenInHeader) {
        String requestAccessToken = resolveToken(requestAccessTokenInHeader);
        String principal = getPrincipal(requestAccessToken);

        // Redis에 저장되어 있는 RT 삭제
        getRefreshTokenInRedis(principal); // Redis에 RT가 저장되어있는지 확인
        memberRedisService.deleteValues("RT(" + SERVER + "):" + principal);

        // Redis에 로그아웃 처리한 AT 저장
        long expiration = jwtTokenProvider.getTokenExpirationTime(requestAccessToken) - new Date().getTime();
        memberRedisService.setValuesWithTimeout(requestAccessToken,
                "logout",
                expiration);
    }
}
