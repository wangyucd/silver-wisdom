package com.silver.user.controller;

import com.silver.common.api.R;
import com.silver.user.model.request.UpdateUserStatusRequest;
import com.silver.user.model.response.UserDetailResponse;
import com.silver.user.model.response.UserPageResponse;
import com.silver.user.service.MiniappUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/admin/users")
public class AdminUserController {

    private final MiniappUserService miniappUserService;

    public AdminUserController(MiniappUserService miniappUserService) {
        this.miniappUserService = miniappUserService;
    }

    @GetMapping
    public R<UserPageResponse> pageUsers(@RequestParam(defaultValue = "1") int pageNum,
                                         @RequestParam(defaultValue = "10") int pageSize,
                                         @RequestParam(required = false) String keyword,
                                         @RequestParam(required = false) String status) {
        return R.success(miniappUserService.adminPageUsers(pageNum, pageSize, keyword, status));
    }

    @GetMapping("/{userId}")
    public R<UserDetailResponse> userDetail(@PathVariable Long userId) {
        return R.success(miniappUserService.adminUserDetail(userId));
    }

    @PostMapping("/{userId}/status")
    public R<Boolean> updateStatus(@PathVariable Long userId, @RequestBody UpdateUserStatusRequest request) {
        miniappUserService.updateUserStatus(userId, request);
        return R.success(Boolean.TRUE);
    }
}
