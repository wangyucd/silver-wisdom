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

/**
 * 管理端用户管理控制器。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@RestController
@RequestMapping("/user/admin/users")
public class AdminUserController {

    /**
     * 小程序用户服务。
     */
    private final MiniappUserService miniappUserService;

    /**
     * 构造管理端用户控制器。
     *
     * @param miniappUserService 小程序用户服务
     */
    public AdminUserController(MiniappUserService miniappUserService) {
        this.miniappUserService = miniappUserService;
    }

    /**
     * 分页查询用户列表。
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param keyword 搜索关键词
     * @param status 用户状态
     * @return 用户分页数据
     */
    @GetMapping
    public R<UserPageResponse> pageUsers(@RequestParam(defaultValue = "1") int pageNum,
                                         @RequestParam(defaultValue = "10") int pageSize,
                                         @RequestParam(required = false) String keyword,
                                         @RequestParam(required = false) String status) {
        return R.success(miniappUserService.adminPageUsers(pageNum, pageSize, keyword, status));
    }

    /**
     * 查询用户详情。
     *
     * @param userId 用户ID
     * @return 用户详情
     */
    @GetMapping("/{userId}")
    public R<UserDetailResponse> userDetail(@PathVariable Long userId) {
        return R.success(miniappUserService.adminUserDetail(userId));
    }

    /**
     * 更新用户状态。
     *
     * @param userId 用户ID
     * @param request 状态更新请求
     * @return 操作结果
     */
    @PostMapping("/{userId}/status")
    public R<Boolean> updateStatus(@PathVariable Long userId, @RequestBody UpdateUserStatusRequest request) {
        miniappUserService.updateUserStatus(userId, request);
        return R.success(Boolean.TRUE);
    }
}
