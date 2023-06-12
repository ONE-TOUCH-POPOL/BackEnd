package com.onepopol.member.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onepopol.utils.Apiutils;
import io.jsonwebtoken.IncorrectClaimException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Access Token 추출
        String accessToken = resolveToken(request);

        try { // 정상 토큰인지 검사
            if (accessToken != null && jwtTokenProvider.validateAccessToken(accessToken)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("Save authentication in SecurityContextHolder.");
            }else if(accessToken != null && jwtTokenProvider.validateAccessTokenOnlyExpired(accessToken)){ // 유효하지만 만료된 경우
                response.setStatus(HttpStatus.OK.value());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                ObjectMapper objectMapper = new ObjectMapper();
                String responseBody = objectMapper.writeValueAsString(Apiutils.error("access token expire",1003)); // ApiResult 객체를 JSON 문자열로 변환

                response.getWriter().write(responseBody);
                response.getWriter().flush();
                return;
            }
        } catch (IncorrectClaimException e) { // 잘못된 토큰일 경우
            SecurityContextHolder.clearContext();
            log.debug("Invalid JWT token.");

            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ObjectMapper objectMapper = new ObjectMapper();
            String responseBody = objectMapper.writeValueAsString(Apiutils.error("잘못된 Access Token 입니다.",1004));

            response.getWriter().write(responseBody);
            response.getWriter().flush();
        } catch (UsernameNotFoundException e) { // 회원을 찾을 수 없을 경우
            SecurityContextHolder.clearContext();
            log.debug("Can't find user.");

            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            ObjectMapper objectMapper = new ObjectMapper();
            String responseBody = objectMapper.writeValueAsString(Apiutils.error("사용자를 찾을 수 없습니다.",1005));

            response.getWriter().write(responseBody);
            response.getWriter().flush();
        }

        filterChain.doFilter(request, response);
    }

    // HTTP Request 헤더로부터 토큰 추출
    public String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}