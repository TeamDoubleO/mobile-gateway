package com.doubleo.mobilegateway.infra.config.gateway;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("security.endpoints")
public record GatewayPathProperties(
        List<Endpoint> publicEndpoints, List<Endpoint> protectedEndpoints) {
    public record Endpoint(String path, List<String> methods) {}
}
