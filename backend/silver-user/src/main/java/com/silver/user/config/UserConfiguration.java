package com.silver.user.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 用户模块配置类。
 * 提供用户相关 Bean 配置。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@Configuration
@EnableConfigurationProperties(WxMiniAuthProperties.class)
public class UserConfiguration {

    /**
     * 密码编码器。
     * 用于管理员密码的加密与校验。
     *
     * @return BCrypt 密码编码器
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
