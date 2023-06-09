package com.onepopol.member.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onepopol.utils.Apiutils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.onepopol.member.error.MemberErrorCode.INVALID_ACCESS;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.error("invalid access");

        // 잘못된 접근
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(Apiutils.error(INVALID_ACCESS.getMessage(),INVALID_ACCESS.getCode()));

        response.getWriter().write(responseBody);
        response.getWriter().flush();
    }
}