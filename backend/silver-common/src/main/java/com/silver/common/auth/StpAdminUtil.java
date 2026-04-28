package com.silver.common.auth;

import cn.dev33.satoken.stp.StpLogic;

/**
 * Sa-Token 管理员认证工具类。
 * 提供管理员登录态校验、登录登出等能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
public final class StpAdminUtil {

    /**
     * 管理员登录类型标识
     */
    public static final String LOGIN_TYPE = "admin";

    /**
     * Sa-Token 逻辑实例
     */
    private static final StpLogic STP_LOGIC = new StpLogic(LOGIN_TYPE);

    private StpAdminUtil() {
    }

    /**
     * 获取 Sa-Token 逻辑实例。
     *
     * @return StpLogic 实例
     */
    public static StpLogic stpLogic() {
        return STP_LOGIC;
    }
}
