package com.silver.user.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 小程序用户实体。
 */
public class UserAccount {

    /** 用户主键。 */
    private Long id;
    /** 微信 openId。 */
    private String openId;
    /** 用户昵称。 */
    private String nickname;
    /** 用户头像地址。 */
    private String avatarUrl;
    /** 用户状态。 */
    private String status;
    /** 最近登录时间。 */
    private LocalDateTime lastLoginTime;
    /** 创建时间。 */
    private LocalDateTime createdAt;
    /** 更新时间。 */
    private LocalDateTime updatedAt;
    /** 兴趣标签列表。 */
    private final List<UserInterestTag> tags = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<UserInterestTag> getTags() {
        return tags;
    }
}
