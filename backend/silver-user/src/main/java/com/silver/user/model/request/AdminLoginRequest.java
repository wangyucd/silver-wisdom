package com.silver.user.model.request;

/**
 * 管理员登录请求。
 */
public class AdminLoginRequest {

    /** 管理员账号。 */
    private String username;
    /** 管理员密码。 */
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
