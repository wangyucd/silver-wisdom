package com.silver.user.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 小程序登录响应。
 */
@Getter
@Setter
public class LoginResponse {

    /** 访问令牌。 */
    private String token;
    /** 用户ID。 */
    private Long userId;
    /** 是否新用户。 */
    private boolean newUser;
    /** 登录类型。 */
    private String loginType;

}
