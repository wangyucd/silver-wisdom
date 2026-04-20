package com.silver.course;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.silver")
@MapperScan("com.silver.course.mapper")
public class SilverCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SilverCourseApplication.class, args);
    }
}
