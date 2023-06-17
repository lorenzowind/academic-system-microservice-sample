package com.academicsys.gatewayservice;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {
@Bean
public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
    return builder.routes()
    .route(p -> p.path("/academic-service/**")
    .uri("lb://academic-service"))
    .route(p -> p.path("/student-service/**")
    .uri("lb://student-service"))
    .build();
    }
}
