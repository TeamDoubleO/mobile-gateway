package com.doubleo.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MobileGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MobileGatewayApplication.class, args);
    }

}
