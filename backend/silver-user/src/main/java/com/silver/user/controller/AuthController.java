package com.silver.user.controller;

import com.silver.common.api.R;
import com.silver.user.model.request.WxLoginRequest;
import com.silver.user.model.response.CurrentUserResponse;
import com.silver.user.model.response.LoginResponse;
import com.silver.user.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 小程序端认证控制器。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@RestController
@RequestMapping("/user/auth")
public class AuthController {

    /**
     * 认证服务。
     */
    private final AuthService authService;

    /**
     * 构造认证控制器。
     *
     * @param authService 认证服务
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 微信小程序登录。
     *
     * @param request 微信登录请求
     * @return 登录响应
     */
    @PostMapping("/wx-login")
    public R<LoginResponse> wxLogin(@RequestBody WxLoginRequest request) {
        return R.success(authService.wxLogin(request));
    }

    /**
     * 用户登出。
     *
     * @return 登出结果
     */
    @PostMapping("/logout")
    public R<Boolean> logout() {
        authService.logout();
        return R.success(Boolean.TRUE);
    }

    /**
     * 获取当前用户信息。
     *
     * @return 当前用户信息
     */
    @GetMapping("/me")
    public R<CurrentUserResponse> currentUser() {
        return R.success(authService.currentUser());
    }
}
