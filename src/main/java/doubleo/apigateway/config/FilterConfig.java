package doubleo.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("member-service", r -> r.path("/member/**")
                        .uri("lb://member-service"))
                .route("did-service", r -> r.path("/did/**")
                        .uri("lb://did-service"))
                .route("admin-service", r -> r.path("/admin/**")
                        .uri("lb://admin-service"))
                .route("hospital-service", r -> r.path("/hospital/**")
                        .uri("lb://hospital-service"))
                .route("patient-service", r -> r.path("/patient/**")
                        .uri("lb://patient-service"))
                .route("pass-service", r -> r.path("/pass/**")
                        .uri("lb://pass-service"))
                //swagger routing
                .route("swagger_member", r -> r.path("/v3/api-docs/member")
                        .filters(f -> f.rewritePath("/v3/api-docs/member", "/v3/api-docs"))
                        .uri("lb://member-service"))
                .route("swagger_did", r -> r.path("/v3/api-docs/did")
                        .filters(f -> f.rewritePath("/v3/api-docs/did", "/v3/api-docs"))
                        .uri("lb://did-service"))
                .route("swagger_admin", r -> r.path("/v3/api-docs/admin")
                        .filters(f -> f.rewritePath("/v3/api-docs/admin", "/v3/api-docs"))
                        .uri("lb://admin-service"))
                .route("swagger_hospital", r -> r.path("/v3/api-docs/hospital")
                        .filters(f -> f.rewritePath("/v3/api-docs/hospital", "/v3/api-docs"))
                        .uri("lb://hospital-service"))
                .route("swagger_patient", r -> r.path("/v3/api-docs/log")
                        .filters(f -> f.rewritePath("/v3/api-docs/log", "/v3/api-docs"))
                        .uri("lb://patient-service"))
                .route("swagger_pass", r -> r.path("/v3/api-docs/pass")
                        .filters(f -> f.rewritePath("/v3/api-docs/pass", "/v3/api-docs"))
                        .uri("lb://pass-service"))
                .build();
    }
}