package com.silver.content;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 内容服务启动类。
 * 提供内容发布、管理、查询以及 AI 生成能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@SpringBootApplication(scanBasePackages = "com.silver")
@MapperScan("com.silver.content.mapper")
public class SilverContentApplication {

    /**
     * 内容服务入口。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(SilverContentApplication.class, args);
    }
}
