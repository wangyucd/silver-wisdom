package com.silver.user.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * 管理员资料响应。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@Getter
@Setter
public class AdminProfileResponse {

    /**
     * 管理员ID
     */
    private Long adminId;
    /**
     * 管理员账号
     */
    private String username;
    /**
     * 管理员名称
     */
    private String name;
    /**
     * 管理员状态
     */
    private String status;
    /**
     * 登录类型
     */
    private String loginType;

}
