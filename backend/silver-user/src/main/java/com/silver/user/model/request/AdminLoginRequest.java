package com.silver.user.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 管理员登录请求。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@Getter
@Setter
public class AdminLoginRequest {

    /**
     * 管理员账号
     */
    private String username;
    /**
     * 管理员密码
     */
    private String password;

}
