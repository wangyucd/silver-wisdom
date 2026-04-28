package com.silver.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 网关服务启动类。
 * 提供统一路由、鉴权入口、限流和跨域能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@SpringBootApplication(scanBasePackages = "com.silver")
public class SilverGatewayApplication {

    /**
     * 网关服务入口。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(SilverGatewayApplication.class, args);
    }
}
