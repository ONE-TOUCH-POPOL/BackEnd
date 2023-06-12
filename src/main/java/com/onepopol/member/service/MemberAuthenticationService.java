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

        String refreshTokenInRedis = memberRedisService.getValues("RT(" + SERVER + "):" + principal);
        if (refreshTokenInRedis == null) { // Redis에 저장되어 있는 RT가 없을 경우
            throw new BaseException(new ApiError("존재하지 않는 RT입니다.", 1002)); // 재로그인 요청
        }

        // 요청된 RT의 유효성 검사 & Redis에 저장되어 있는 RT와 같은지 비교
        if(!jwtTokenProvider.validateRefreshToken(requestRefreshToken) || !refreshTokenInRedis.equals(requestRefreshToken)) {
            memberRedisService.deleteValues("RT(" + SERVER + "):" + principal); // 탈취 가능성 -> 삭제
            throw new BaseException(new ApiError("RT가 유효하지 않습니다.", 1003)); // 재로그인 요청
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String authorities = getAuthorities(authentication);

        // 토큰 재발급 및 Redis 업데이트
        memberRedisService.deleteValues("RT(" + SERVER + "):" + principal); // 기존 RT 삭제
        TokenDto tokenDto = jwtTokenProvider.createToken(principal, authorities);
        saveRefreshToken(SERVER, principal, tokenDto.getRefreshToken());
        return tokenDto;
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

    // "Bearer {AT}"에서 {AT} 추출
    private String resolveToken(String requestAccessTokenInHeader) {
        if (requestAccessTokenInHeader != null && requestAccessTokenInHeader.startsWith("Bearer ")) {
            return requestAccessTokenInHeader.substring(7);
        }
        return null;
    }

    // 로그아웃
    @Transactional
    public void logout(String requestAccessTokenInHeader) {
        String requestAccessToken = resolveToken(requestAccessTokenInHeader);
        String principal = getPrincipal(requestAccessToken);

        // Redis에 저장되어 있는 RT 삭제
        String refreshTokenInRedis = memberRedisService.getValues("RT(" + SERVER + "):" + principal);
        if (refreshTokenInRedis != null) {
            memberRedisService.deleteValues("RT(" + SERVER + "):" + principal);
        }

        // Redis에 로그아웃 처리한 AT 저장
        long expiration = jwtTokenProvider.getTokenExpirationTime(requestAccessToken) - new Date().getTime();
        memberRedisService.setValuesWithTimeout(requestAccessToken,
                "logout",
                expiration);
    }
}
