package com.silver.user.model.response;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户列表项响应。
 */
public class UserListItemResponse {

    /** 用户ID。 */
    private Long userId;
    /** 用户昵称。 */
    private String nickname;
    /** 用户状态。 */
    private String status;
    /** 标签摘要。 */
    private List<String> tagSummary;
    /** 最近登录时间。 */
    private LocalDateTime lastLoginTime;
    /** 注册时间。 */
    private LocalDateTime createdAt;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getTagSummary() {
        return tagSummary;
    }

    public void setTagSummary(List<String> tagSummary) {
        this.tagSummary = tagSummary;
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
}
