package com.silver.user.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 微信登录请求。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@Getter
@Setter
public class WxLoginRequest {

    /**
     * 微信登录 code
     */
    private String code;

}
