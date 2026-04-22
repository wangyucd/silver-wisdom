package com.silver.user.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 管理员登录响应。
 */
@Getter
@Setter
public class AdminLoginResponse {

    /** 访问令牌。 */
    private String token;
    /** 管理员ID。 */
    private Long adminId;
    /** 管理员名称。 */
    private String name;
    /** 登录类型。 */
    private String loginType;

}
