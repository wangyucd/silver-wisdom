package com.silver.user.controller;

import com.silver.common.api.R;
import com.silver.user.model.request.AdminLoginRequest;
import com.silver.user.model.response.AdminLoginResponse;
import com.silver.user.model.response.AdminProfileResponse;
import com.silver.user.service.AdminAuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理端认证控制器。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@RestController
@RequestMapping("/user/admin/auth")
public class AdminAuthController {

    /**
     * 管理端认证服务。
     */
    private final AdminAuthService adminAuthService;

    /**
     * 构造管理端认证控制器。
     *
     * @param adminAuthService 管理端认证服务
     */
    public AdminAuthController(AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }

    /**
     * 管理员登录。
     *
     * @param request 登录请求
     * @return 登录响应
     */
    @PostMapping("/login")
    public R<AdminLoginResponse> login(@RequestBody AdminLoginRequest request) {
        return R.success(adminAuthService.login(request));
    }

    /**
     * 管理员登出。
     *
     * @return 登出结果
     */
    @PostMapping("/logout")
    public R<Boolean> logout() {
        adminAuthService.logout();
        return R.success(Boolean.TRUE);
    }

    /**
     * 获取当前管理员信息。
     *
     * @return 管理员信息
     */
    @GetMapping("/me")
    public R<AdminProfileResponse> currentAdmin() {
        return R.success(adminAuthService.currentAdmin());
    }
}
