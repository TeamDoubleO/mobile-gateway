package com.doubleo.mobilegateway.filter;

import com.doubleo.mobilegateway.infra.config.gateway.GatewayPathProperties;
import com.doubleo.mobilegateway.infra.config.jwt.JwtProperties;
import com.doubleo.mobilegateway.infra.config.redis.BlackListTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthHeaderFilter implements GlobalFilter, Ordered {

    private final GatewayPathProperties paths;
    private final BlackListTokenService blackListTokenService;
    private final JwtProperties jwtProperties;
    private final AntPathMatcher matcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = exchange.getRequest().getPath().value();
        String method = exchange.getRequest().getMethod().name();

        // public endpoints 등록된 url -> 다음 체인으로 넘기지 x
        for (var ep : paths.publicEndpoints()) {
            if (matcher.match(ep.path(), path)
                    && (ep.methods().contains("*") || ep.methods().contains(method))) {
                return chain.filter(exchange);
            }
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("No or invalid Authorization header");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        if (blackListTokenService.existsByToken(token)) {
            log.warn("Token is blacklisted: {}", token);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        try {
            SecretKey key =
                    Keys.hmacShaKeyFor(
                            jwtProperties.accessTokenSecret().getBytes(StandardCharsets.UTF_8));
            Claims claims =
                    Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

            String memberId = claims.getSubject();

            ServerHttpRequest mutatedRequest =
                    request.mutate().header("X-Member-Id", memberId).build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());

        } catch (JwtException e) {
            log.warn("Invalid JWT: {}", e.getMessage());
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
