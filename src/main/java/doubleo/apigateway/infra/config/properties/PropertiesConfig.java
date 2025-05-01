package com.doubleo.memberservice.infra.config.properties;

import com.doubleo.memberservice.infra.config.jwt.JwtProperties;
import com.doubleo.memberservice.infra.config.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({JwtProperties.class, RedisProperties.class})
@Configuration
public class PropertiesConfig {}
