package com.silver.course;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 课程服务启动类。
 * 提供课程信息、章节结构、学习记录管理能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@SpringBootApplication(scanBasePackages = "com.silver")
@MapperScan("com.silver.course.mapper")
public class SilverCourseApplication {

    /**
     * 课程服务入口。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(SilverCourseApplication.class, args);
    }
}
