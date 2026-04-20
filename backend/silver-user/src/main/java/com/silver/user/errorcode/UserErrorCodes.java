package com.silver.user.errorcode;

import com.silver.common.errorcode.ErrorCode;

/**
 * 用户域错误码定义。
 */
public enum UserErrorCodes implements ErrorCode {

    /** 管理员登录参数缺失。 */
    ADMIN_LOGIN_PARAM_INVALID(110001, "管理员账号或密码不能为空"),
    /** 管理员账号或密码错误。 */
    ADMIN_LOGIN_FAIL(110002, "管理员账号或密码错误"),
    /** 管理员账号已禁用。 */
    ADMIN_ACCOUNT_DISABLED(110003, "管理员账号已禁用"),
    /** 管理员不存在。 */
    ADMIN_NOT_FOUND(110004, "管理员不存在"),
    /** 微信登录参数缺失。 */
    WX_LOGIN_CODE_REQUIRED(110101, "微信登录 code 不能为空"),
    /** 用户账号已禁用。 */
    USER_ACCOUNT_DISABLED(110102, "用户已被禁用"),
    /** 当前登录用户不存在。 */
    CURRENT_USER_NOT_FOUND(110103, "当前登录用户不存在"),
    /** 用户不存在。 */
    USER_NOT_FOUND(110104, "用户不存在"),
    /** 兴趣标签不能为空。 */
    USER_TAGS_REQUIRED(110105, "标签不能为空"),
    /** 用户状态不能为空。 */
    USER_STATUS_REQUIRED(110106, "状态不能为空"),
    /** 用户状态非法。 */
    USER_STATUS_INVALID(110107, "仅支持 ENABLED 或 DISABLED"),
    /** 未开启真实微信登录。 */
    WX_LOGIN_NOT_ENABLED(110108, "暂未接入真实微信登录，请先开启 silver.auth.wx-mini.mock-enabled"),
    /** 生成 openId 失败。 */
    WX_OPEN_ID_GENERATE_FAILED(110109, "生成 openId 失败");

    /** 错误码。 */
    private final int code;
    /** 默认错误信息。 */
    private final String message;

    /**
     * 构造用户域错误码。
     *
     * @param code 错误码
     * @param message 默认错误信息
     */
    UserErrorCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 返回错误码。
     *
     * @return 错误码
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * 返回默认错误信息。
     *
     * @return 默认错误信息
     */
    @Override
    public String getMessage() {
        return message;
    }
}
