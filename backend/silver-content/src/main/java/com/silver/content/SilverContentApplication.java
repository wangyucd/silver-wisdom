package com.silver.content;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.silver")
public class SilverContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SilverContentApplication.class, args);
    }
}
