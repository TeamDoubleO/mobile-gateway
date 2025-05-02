package com.doubleo.mobilegateway.config.security;

import static org.springframework.http.HttpHeaders.SET_COOKIE;

import com.doubleo.mobilegateway.infra.config.gateway.GatewayPathProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class GatewaySecurityConfig {

    private final GatewayPathProperties paths;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeExchange(
                        ex -> {
                            paths.publicEndpoints()
                                    .forEach(
                                            ep -> {
                                                if (ep.methods().contains("*")) {
                                                    ex.pathMatchers(ep.path()).permitAll();
                                                } else {
                                                    ep.methods().stream()
                                                            .map(String::toUpperCase)
                                                            .map(HttpMethod::valueOf)
                                                            .forEach(
                                                                    m ->
                                                                            ex.pathMatchers(
                                                                                            m,
                                                                                            ep
                                                                                                    .path())
                                                                                    .permitAll());
                                                }
                                            });

                            paths.protectedEndpoints()
                                    .forEach(
                                            ep -> {
                                                if (ep.methods().contains("*")) {
                                                    ex.pathMatchers(ep.path()).authenticated();
                                                } else {
                                                    ep.methods().stream()
                                                            .map(String::toUpperCase)
                                                            .map(HttpMethod::valueOf)
                                                            .forEach(
                                                                    m ->
                                                                            ex.pathMatchers(
                                                                                            m,
                                                                                            ep
                                                                                                    .path())
                                                                                    .authenticated());
                                                }
                                            });

                            //                            ex.anyExchange().authenticated();
                            ex.anyExchange().permitAll();
                        });

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.addAllowedHeader("*");
        cfg.addAllowedMethod("*");
        cfg.setAllowCredentials(true);
        cfg.addAllowedOriginPattern("http://localhost:5173");
        cfg.addExposedHeader(SET_COOKIE);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
