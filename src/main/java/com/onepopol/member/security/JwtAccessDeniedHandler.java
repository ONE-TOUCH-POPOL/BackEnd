package com.onepopol.member.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onepopol.utils.Apiutils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.onepopol.member.error.MemberErrorCode.NO_ACCESS_RIGHTS;

@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        log.error("no access rights");

        // 권한이 없는 경우
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ObjectMapper objectMapper = new ObjectMapper();
        String responseBody = objectMapper.writeValueAsString(Apiutils.error(NO_ACCESS_RIGHTS.getMessage(),NO_ACCESS_RIGHTS.getCode()));

        response.getWriter().write(responseBody);
        response.getWriter().flush();
    }
}