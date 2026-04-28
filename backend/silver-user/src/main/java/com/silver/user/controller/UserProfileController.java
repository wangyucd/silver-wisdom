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

/**
 * 用户画像控制器。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@RestController
@RequestMapping("/user/profile")
public class UserProfileController {

    /**
     * 小程序用户服务。
     */
    private final MiniappUserService miniappUserService;

    /**
     * 构造用户画像控制器。
     *
     * @param miniappUserService 小程序用户服务
     */
    public UserProfileController(MiniappUserService miniappUserService) {
        this.miniappUserService = miniappUserService;
    }

    /**
     * 保存新用户兴趣问卷。
     *
     * @param request 问卷请求
     * @return 用户标签响应
     */
    @PostMapping("/onboarding")
    public R<UserTagResponse> saveOnboarding(@RequestBody OnboardingRequest request) {
        return R.success(miniappUserService.saveOnboarding(request));
    }

    /**
     * 查询当前用户标签。
     *
     * @return 用户标签响应
     */
    @GetMapping("/tags")
    public R<UserTagResponse> currentUserTags() {
        return R.success(miniappUserService.currentUserTags());
    }
}
