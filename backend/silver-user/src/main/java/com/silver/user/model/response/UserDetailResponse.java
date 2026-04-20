package com.silver.user.model.response;

import com.silver.user.model.UserInterestTag;
import com.silver.user.model.UserLearningSummary;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户详情响应。
 */
public class UserDetailResponse {

    /** 用户ID。 */
    private Long userId;
    /** 用户昵称。 */
    private String nickname;
    /** 用户头像。 */
    private String avatarUrl;
    /** 用户状态。 */
    private String status;
    /** 用户标签列表。 */
    private List<UserInterestTag> tagList;
    /** 学习概览。 */
    private UserLearningSummary learningSummary;
    /** 注册时间。 */
    private LocalDateTime createdAt;
    /** 最近登录时间。 */
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
