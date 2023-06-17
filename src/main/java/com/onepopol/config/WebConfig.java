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
//                .allowedOriginPatterns("*")  // 허용되는 도메인. "*"은 모든 도메인 허용을 의미합니다.
                .allowedOrigins("http://127.0.0.1:3000")
                .allowedMethods("*")  // 허용되는 HTTP 메소드. "*"은 모든 메소드를 허용합니다.
                .allowedHeaders("*")  // 허용되는 HTTP 헤더. "*"은 모든 헤더를 허용합니다.
                .allowCredentials(true) // 쿠키, 세션 정보도 허용
                .exposedHeaders("Authorization");  // 클라이언트가 접근할 수 있는 헤더. 여기에서 "Authorization" 헤더를 허용하도록 설정하였습니다.
    }
}