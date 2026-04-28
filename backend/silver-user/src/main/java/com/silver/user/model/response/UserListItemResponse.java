package com.silver.user.model.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户列表项响应。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@Getter
@Setter
public class UserListItemResponse {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户状态
     */
    private String status;
    /**
     * 标签摘要
     */
    private List<String> tagSummary;
    /**
     * 最近登录时间
     */
    private LocalDateTime lastLoginTime;
    /**
     * 注册时间
     */
    private LocalDateTime createdAt;

}
