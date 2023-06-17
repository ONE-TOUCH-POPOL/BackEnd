package com.onepopol.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedOrigins("*")  // 허용되는 도메인. "*"은 모든 도메인 허용을 의미합니다.
                .allowedMethods("*")  // 허용되는 HTTP 메소드. "*"은 모든 메소드를 허용합니다.
                .allowedHeaders("*")  // 허용되는 HTTP 헤더. "*"은 모든 헤더를 허용합니다.
                .allowCredentials(true); // 쿠키, 세션 정보도 허용
    }
}