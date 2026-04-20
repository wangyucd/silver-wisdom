package com.silver.user.model.response;

import java.time.LocalDateTime;
import java.util.List;

public class UserListItemResponse {

    private Long userId;
    private String nickname;
    private String status;
    private List<String> tagSummary;
    private LocalDateTime lastLoginTime;
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
