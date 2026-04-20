package com.silver.user.model.response;

/**
 * 管理员登录响应。
 */
public class AdminLoginResponse {

    /** 访问令牌。 */
    private String token;
    /** 管理员ID。 */
    private Long adminId;
    /** 管理员名称。 */
    private String name;
    /** 登录类型。 */
    private String loginType;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
