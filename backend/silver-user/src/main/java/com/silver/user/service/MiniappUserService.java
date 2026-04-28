package com.silver.user.service;

import com.silver.user.model.request.OnboardingRequest;
import com.silver.user.model.request.UpdateUserStatusRequest;
import com.silver.user.model.response.UserDetailResponse;
import com.silver.user.model.response.UserPageResponse;
import com.silver.user.model.response.UserTagResponse;

/**
 * 小程序用户服务接口。
 * 提供用户画像管理、管理端用户查询与状态变更等能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
public interface MiniappUserService {

    /**
     * 保存新用户兴趣问卷。
     * 替换用户现有标签为问卷中选中的标签。
     *
     * @param request 问卷请求
     * @return 用户标签响应
     */
    UserTagResponse saveOnboarding(OnboardingRequest request);

    /**
     * 查询当前登录用户标签列表。
     *
     * @return 用户标签响应
     */
    UserTagResponse currentUserTags();

    /**
     * 管理端分页查询用户列表。
     * 支持按关键词和状态筛选，按注册时间倒序排列。
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param keyword 搜索关键词（昵称或ID）
     * @param status 用户状态
     * @return 用户分页数据
     */
    UserPageResponse adminPageUsers(int pageNum, int pageSize, String keyword, String status);

    /**
     * 管理端查询用户详情。
     * 包含用户基本信息、标签和学习概览。
     *
     * @param userId 用户ID
     * @return 用户详情
     */
    UserDetailResponse adminUserDetail(Long userId);

    /**
     * 管理端更新用户状态。
     * 支持启用或禁用用户账号。
     *
     * @param userId 用户ID
     * @param request 状态更新请求
     */
    void updateUserStatus(Long userId, UpdateUserStatusRequest request);
}
