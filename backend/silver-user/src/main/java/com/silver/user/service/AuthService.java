package com.silver.user.service;

import com.silver.user.model.request.WxLoginRequest;
import com.silver.user.model.response.CurrentUserResponse;
import com.silver.user.model.response.LoginResponse;

/**
 * 小程序认证服务接口。
 * 提供微信小程序登录、登出及当前用户信息查询能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
public interface AuthService {

    /**
     * 执行微信小程序登录。
     * 根据 code 解析 openId，自动创建新用户或查询已有用户，更新最后登录时间并颁发登录令牌。
     *
     * @param request 微信登录请求
     * @return 登录响应，包含访问令牌和用户信息
     */
    LoginResponse wxLogin(WxLoginRequest request);

    /**
     * 执行小程序用户登出。
     * 清除当前登录态。
     */
    void logout();

    /**
     * 查询当前登录用户信息。
     *
     * @return 当前用户响应
     */
    CurrentUserResponse currentUser();
}
