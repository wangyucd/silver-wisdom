package com.silver.user.controller;

import com.silver.common.api.R;
import com.silver.user.model.request.OnboardingRequest;
import com.silver.user.model.response.UserTagResponse;
import com.silver.user.service.MiniappUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/profile")
public class UserProfileController {

    private final MiniappUserService miniappUserService;

    public UserProfileController(MiniappUserService miniappUserService) {
        this.miniappUserService = miniappUserService;
    }

    @PostMapping("/onboarding")
    public R<UserTagResponse> saveOnboarding(@RequestBody OnboardingRequest request) {
        return R.success(miniappUserService.saveOnboarding(request));
    }

    @GetMapping("/tags")
    public R<UserTagResponse> currentUserTags() {
        return R.success(miniappUserService.currentUserTags());
    }
}
