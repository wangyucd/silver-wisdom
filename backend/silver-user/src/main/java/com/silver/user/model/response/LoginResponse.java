package com.silver.user.model.response;

/**
 * 小程序登录响应。
 */
public class LoginResponse {

    /** 访问令牌。 */
    private String token;
    /** 用户ID。 */
    private Long userId;
    /** 是否新用户。 */
    private boolean newUser;
    /** 登录类型。 */
    private String loginType;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
