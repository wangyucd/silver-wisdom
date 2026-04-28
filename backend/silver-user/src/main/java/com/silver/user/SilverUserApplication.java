package com.silver.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 用户服务启动类。
 * 提供用户账号、登录态、权限管理能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@SpringBootApplication(scanBasePackages = "com.silver")
@MapperScan("com.silver.user.mapper")
public class SilverUserApplication {

    /**
     * 用户服务入口。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(SilverUserApplication.class, args);
    }
}
