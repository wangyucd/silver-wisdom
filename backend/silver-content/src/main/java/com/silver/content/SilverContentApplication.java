package com.silver.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.silver")
@MapperScan("com.silver.content.mapper")
public class SilverContentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SilverContentApplication.class, args);
    }
}
