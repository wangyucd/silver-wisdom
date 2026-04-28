package com.silver.user.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 当前登录用户响应。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@Getter
@Setter
public class CurrentUserResponse {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 微信 openId
     */
    private String openId;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户头像地址
     */
    private String avatarUrl;
    /**
     * 用户状态
     */
    private String status;
    /**
     * 是否新用户
     */
    private boolean newUser;

}
