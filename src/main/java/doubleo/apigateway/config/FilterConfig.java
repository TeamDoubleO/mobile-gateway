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
                .route("employee-service", r -> r.path("/employee/**")
                        .uri("lb://employee-service"))
                .route("area-service", r -> r.path("/area/**")
                        .uri("lb://area-service"))
                .route("access-service", r -> r.path("/access/**")
                        .uri("lb://access-service"))
                .route("admin-service", r -> r.path("/admin/**")
                        .uri("lb://admin-service"))
                .route("log-service", r -> r.path("/log/**")
                        .uri("lb://log-service"))
                //swagger routing
                .route("swagger_employee", r -> r.path("/v3/api-docs/employee")
                        .filters(f -> f.rewritePath("/v3/api-docs/employee", "/v3/api-docs"))
                        .uri("lb://employee-service"))
                .route("swagger_area", r -> r.path("/v3/api-docs/area")
                        .filters(f -> f.rewritePath("/v3/api-docs/area", "/v3/api-docs"))
                        .uri("lb://area-service"))
                .route("swagger_access", r -> r.path("/v3/api-docs/access")
                        .filters(f -> f.rewritePath("/v3/api-docs/access", "/v3/api-docs"))
                        .uri("lb://access-service"))
                .route("swagger_admin", r -> r.path("/v3/api-docs/admin")
                        .filters(f -> f.rewritePath("/v3/api-docs/admin", "/v3/api-docs"))
                        .uri("lb://admin-service"))
                .route("swagger_log", r -> r.path("/v3/api-docs/log")
                        .filters(f -> f.rewritePath("/v3/api-docs/log", "/v3/api-docs"))
                        .uri("lb://log-service"))
                .build();
    }
}