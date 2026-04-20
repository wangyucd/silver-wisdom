package com.silver.user.model.request;

/**
 * 微信登录请求。
 */
public class WxLoginRequest {

    /** 微信登录 code。 */
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
