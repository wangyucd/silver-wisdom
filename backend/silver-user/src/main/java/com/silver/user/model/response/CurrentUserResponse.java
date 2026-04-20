package com.silver.user.model.response;

/**
 * 当前登录用户响应。
 */
public class CurrentUserResponse {

    /** 用户ID。 */
    private Long userId;
    /** 微信 openId。 */
    private String openId;
    /** 用户昵称。 */
    private String nickname;
    /** 用户头像地址。 */
    private String avatarUrl;
    /** 用户状态。 */
    private String status;
    /** 是否新用户。 */
    private boolean newUser;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }
}
