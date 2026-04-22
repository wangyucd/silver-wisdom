package com.silver.user.model.response;

import com.silver.user.model.UserInterestTagEntity;
import com.silver.user.model.UserLearningSummary;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户详情响应。
 */
@Getter
@Setter
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
    private List<UserInterestTagEntity> tagList;
    /** 学习概览。 */
    private UserLearningSummary learningSummary;
    /** 注册时间。 */
    private LocalDateTime createdAt;
    /** 最近登录时间。 */
    private LocalDateTime lastLoginTime;

}
