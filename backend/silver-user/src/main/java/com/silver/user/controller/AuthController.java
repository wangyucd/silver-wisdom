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

@RestController
@RequestMapping("/user/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/wx-login")
    public R<LoginResponse> wxLogin(@RequestBody WxLoginRequest request) {
        return R.success(authService.wxLogin(request));
    }

    @PostMapping("/logout")
    public R<Boolean> logout() {
        authService.logout();
        return R.success(Boolean.TRUE);
    }

    @GetMapping("/me")
    public R<CurrentUserResponse> currentUser() {
        return R.success(authService.currentUser());
    }
}
