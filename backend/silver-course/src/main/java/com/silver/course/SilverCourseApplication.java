package com.silver.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.silver")
public class SilverCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SilverCourseApplication.class, args);
    }
}
