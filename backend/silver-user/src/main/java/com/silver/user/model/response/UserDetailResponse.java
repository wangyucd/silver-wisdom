package com.silver.user.model.response;

import com.silver.user.model.UserInterestTag;
import com.silver.user.model.UserLearningSummary;
import java.time.LocalDateTime;
import java.util.List;

public class UserDetailResponse {

    private Long userId;
    private String nickname;
    private String avatarUrl;
    private String status;
    private List<UserInterestTag> tagList;
    private UserLearningSummary learningSummary;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginTime;

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

    public List<UserInterestTag> getTagList() {
        return tagList;
    }

    public void setTagList(List<UserInterestTag> tagList) {
        this.tagList = tagList;
    }

    public UserLearningSummary getLearningSummary() {
        return learningSummary;
    }

    public void setLearningSummary(UserLearningSummary learningSummary) {
        this.learningSummary = learningSummary;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
