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

@RestController
@RequestMapping("/user/admin/auth")
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    public AdminAuthController(AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }

    @PostMapping("/login")
    public R<AdminLoginResponse> login(@RequestBody AdminLoginRequest request) {
        return R.success(adminAuthService.login(request));
    }

    @PostMapping("/logout")
    public R<Boolean> logout() {
        adminAuthService.logout();
        return R.success(Boolean.TRUE);
    }

    @GetMapping("/me")
    public R<AdminProfileResponse> currentAdmin() {
        return R.success(adminAuthService.currentAdmin());
    }
}
