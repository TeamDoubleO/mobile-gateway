package com.doubleo.apigateway.infra.config.gateway;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;



import java.util.List;


@ConfigurationProperties("security.endpoints")
public record GatewayPathProperties(
        List<Endpoint> publicEndpoints,
        List<Endpoint> protectedEndpoints
) {
    public record Endpoint(String path, List<String> methods) {}
}