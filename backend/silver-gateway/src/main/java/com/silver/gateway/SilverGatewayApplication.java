package com.silver.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.silver")
public class SilverGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SilverGatewayApplication.class, args);
    }
}
