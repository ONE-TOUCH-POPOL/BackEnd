package com.onepopol.member.configuration;

import com.onepopol.config.BaseException;
import com.onepopol.utils.ApiError;
import io.lettuce.core.RedisConnectionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import static com.onepopol.member.error.MemberErrorCode.SERVER_ERROR;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableRedisRepositories
public class RedisRepositoryConfig {

    private final RedisProperties redisProperties;

    // lettuce
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        try {
            return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
        } catch (RedisConnectionException e) {
            log.error("Failed to connect to Redis server: " + e.getMessage());
        } catch (RedisConnectionFailureException e) {
            log.error("Failed to establish Redis connection: " + e.getMessage());
        }
        throw new BaseException(new ApiError(SERVER_ERROR.getMessage(), SERVER_ERROR.getCode()));
    }

    // redis-cli 사용을 위한 설정
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}