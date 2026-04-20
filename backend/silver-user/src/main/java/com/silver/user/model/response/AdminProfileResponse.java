package com.silver.user.model.response;

/**
 * 管理员资料响应。
 */
public class AdminProfileResponse {

    /** 管理员ID。 */
    private Long adminId;
    /** 管理员账号。 */
    private String username;
    /** 管理员名称。 */
    private String name;
    /** 管理员状态。 */
    private String status;
    /** 登录类型。 */
    private String loginType;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
