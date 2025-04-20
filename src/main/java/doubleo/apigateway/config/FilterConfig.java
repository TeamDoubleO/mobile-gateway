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
                        .uri("lb://employee-service"))
                .route("admin-service", r -> r.path("/admin/**")
                        .uri("lb://admin-service"))
                .route("log-service", r -> r.path("/log/**")
                        .uri("lb://log-service"))
                .build();
    }
}