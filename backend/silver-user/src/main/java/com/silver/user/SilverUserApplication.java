package com.silver.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.silver")
@MapperScan("com.silver.user.mapper")
public class SilverUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(SilverUserApplication.class, args);
    }
}
