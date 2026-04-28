package com.silver.user.service;

import com.silver.user.model.request.AdminLoginRequest;
import com.silver.user.model.response.AdminLoginResponse;
import com.silver.user.model.response.AdminProfileResponse;

/**
 * 管理员认证服务接口。
 * 提供管理员登录、登出及当前管理员信息查询能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
public interface AdminAuthService {

    /**
     * 执行管理员登录。
     * 校验账号密码，验证账号状态，更新最后登录时间并颁发登录令牌。
     *
     * @param request 登录请求
     * @return 登录响应，包含访问令牌和管理员信息
     */
    AdminLoginResponse login(AdminLoginRequest request);

    /**
     * 执行管理员登出。
     * 清除当前登录态。
     */
    void logout();

    /**
     * 查询当前登录管理员信息。
     *
     * @return 当前管理员信息
     */
    AdminProfileResponse currentAdmin();
}
