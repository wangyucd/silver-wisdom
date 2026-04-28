package com.silver.user.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 微信小程序认证配置属性。
 * 从配置文件读取微信认证相关参数。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@ConfigurationProperties(prefix = "silver.auth.wx-mini")
public class WxMiniAuthProperties {

    /**
     * 是否启用 mock 模式
     */
    private boolean mockEnabled = true;
    /**
     * 微信小程序 appId
     */
    private String appId = "";
    /**
     * 微信小程序 appSecret
     */
    private String appSecret = "";

    public boolean isMockEnabled() {
        return mockEnabled;
    }

    public void setMockEnabled(boolean mockEnabled) {
        this.mockEnabled = mockEnabled;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
