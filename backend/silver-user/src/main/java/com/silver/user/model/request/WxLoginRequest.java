package com.silver.user.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信登录请求。
 */
@Getter
@Setter
public class WxLoginRequest {

    /** 微信登录 code。 */
    private String code;

}
