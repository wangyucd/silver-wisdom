package com.silver.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.silver")
public class SilverUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SilverUserApplication.class, args);
    }
}
