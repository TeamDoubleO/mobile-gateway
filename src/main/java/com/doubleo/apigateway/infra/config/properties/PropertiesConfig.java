package com.doubleo.apigateway.infra.config.properties;


import com.doubleo.apigateway.infra.config.gateway.GatewayPathProperties;
import com.doubleo.apigateway.infra.config.jwt.JwtProperties;
import com.doubleo.apigateway.infra.config.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({JwtProperties.class, RedisProperties.class, GatewayPathProperties.class})
@Configuration
public class PropertiesConfig {}
