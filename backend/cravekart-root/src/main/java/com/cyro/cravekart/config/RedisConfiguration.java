package com.cyro.cravekart.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import java.time.Duration;

@Configuration
public class RedisConfiguration {

  @Bean
  public ObjectMapper objectMapper() {
    com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    mapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
    mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, false);
    return  mapper;

  }

  @Bean
  public RedisCacheManager redisCacheManager(
      RedisConnectionFactory redisConnectionFactory
  ) {

    RedisCacheConfiguration cacheConfig =
        RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))
            .enableTimeToIdle();

    return RedisCacheManager.builder(redisConnectionFactory)
        .cacheDefaults(cacheConfig)
        .build();
  }

  // Redis configuration

//  @Bean
//  public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//    RedisTemplate redisTemplate = new RedisTemplate<>();
//    redisTemplate.setConnectionFactory(redisConnectionFactory);
//
//    redisTemplate.setKeySerializer(new StringRedisSerializer());
//    redisTemplate.setValueSerializer(new StringRedisSerializer());
//
//    return redisTemplate;
//  }


}
