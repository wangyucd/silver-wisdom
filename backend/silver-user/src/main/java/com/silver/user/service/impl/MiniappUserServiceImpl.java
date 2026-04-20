package com.silver.user.service.impl;

import com.silver.common.auth.StpAdminUtil;
import com.silver.common.auth.StpMiniappUtil;
import com.silver.common.exception.BusinessException;
import com.silver.user.errorcode.UserErrorCodes;
import com.silver.user.model.UserAccount;
import com.silver.user.model.UserInterestTag;
import com.silver.user.model.UserLearningSummary;
import com.silver.user.model.request.OnboardingRequest;
import com.silver.user.model.request.UpdateUserStatusRequest;
import com.silver.user.model.response.UserDetailResponse;
import com.silver.user.model.response.UserListItemResponse;
import com.silver.user.model.response.UserPageResponse;
import com.silver.user.model.response.UserTagResponse;
import com.silver.user.service.MiniappUserService;
import com.silver.user.service.UserDirectory;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 小程序用户服务实现。
 */
@Service
public class MiniappUserServiceImpl implements MiniappUserService {

    /**
     * 用户目录服务。
     */
    private final UserDirectory userDirectory;

    /**
     * 构造小程序用户服务。
     *
     * @param userDirectory 用户目录服务
     */
    public MiniappUserServiceImpl(UserDirectory userDirectory) {
        this.userDirectory = userDirectory;
    }

    /**
     * 保存新用户兴趣问卷。
     *
     * @param request 问卷请求
     * @return 用户标签响应
     */
    @Override
    public UserTagResponse saveOnboarding(OnboardingRequest request) {
        StpMiniappUtil.stpLogic().checkLogin();
        UserAccount userAccount = loadCurrentUser();
        if (request == null || CollectionUtils.isEmpty(request.getTags())) {
            throw new BusinessException(UserErrorCodes.USER_TAGS_REQUIRED);
        }
        userAccount.getTags().clear();
        for (String currentTag : request.getTags()) {
            if (StringUtils.hasText(currentTag)) {
                userAccount.getTags().add(new UserInterestTag(currentTag.trim(), 1.0));
            }
        }
        userAccount.setUpdatedAt(LocalDateTime.now());
        userDirectory.saveUser(userAccount);
        UserTagResponse response = new UserTagResponse();
        response.setTags(userAccount.getTags());
        return response;
    }

    /**
     * 查询当前用户标签。
     *
     * @return 用户标签响应
     */
    @Override
    public UserTagResponse currentUserTags() {
        StpMiniappUtil.stpLogic().checkLogin();
        UserAccount userAccount = loadCurrentUser();
        UserTagResponse response = new UserTagResponse();
        response.setTags(userAccount.getTags());
        return response;
    }

    /**
     * 管理端分页查询用户。
     *
     * @param pageNum 页码
     * @param pageSize 页大小
     * @param keyword 关键字
     * @param status 状态
     * @return 分页结果
     */
    @Override
    public UserPageResponse adminPageUsers(int pageNum, int pageSize, String keyword, String status) {
        StpAdminUtil.stpLogic().checkLogin();
        int normalizedPageNum = Math.max(pageNum, 1);
        int normalizedPageSize = Math.max(pageSize, 1);

        List<UserListItemResponse> items = userDirectory.listUsers().stream()
                .filter(user -> matchKeyword(user, keyword))
                .filter(user -> matchStatus(user, status))
                .sorted(Comparator.comparing(UserAccount::getCreatedAt).reversed())
                .map(this::toUserListItem)
                .toList();

        int fromIndex = Math.min((normalizedPageNum - 1) * normalizedPageSize, items.size());
        int toIndex = Math.min(fromIndex + normalizedPageSize, items.size());

        UserPageResponse response = new UserPageResponse();
        response.setTotal(items.size());
        response.setList(items.subList(fromIndex, toIndex));
        return response;
    }

    /**
     * 管理端查询用户详情。
     *
     * @param userId 用户ID
     * @return 用户详情
     */
    @Override
    public UserDetailResponse adminUserDetail(Long userId) {
        StpAdminUtil.stpLogic().checkLogin();
        UserAccount userAccount = userDirectory.getUserById(userId)
                .orElseThrow(() -> new BusinessException(UserErrorCodes.USER_NOT_FOUND));
        UserDetailResponse response = new UserDetailResponse();
        response.setUserId(userAccount.getId());
        response.setNickname(userAccount.getNickname());
        response.setAvatarUrl(userAccount.getAvatarUrl());
        response.setStatus(userAccount.getStatus());
        response.setTagList(userAccount.getTags());
        response.setLearningSummary(buildLearningSummary(userAccount.getId()));
        response.setCreatedAt(userAccount.getCreatedAt());
        response.setLastLoginTime(userAccount.getLastLoginTime());
        return response;
    }

    /**
     * 管理端更新用户状态。
     *
     * @param userId 用户ID
     * @param request 状态更新请求
     */
    @Override
    public void updateUserStatus(Long userId, UpdateUserStatusRequest request) {
        StpAdminUtil.stpLogic().checkLogin();
        if (request == null || !StringUtils.hasText(request.getStatus())) {
            throw new BusinessException(UserErrorCodes.USER_STATUS_REQUIRED);
        }
        String status = request.getStatus().trim().toUpperCase(Locale.ROOT);
        if (!"ENABLED".equals(status) && !"DISABLED".equals(status)) {
            throw new BusinessException(UserErrorCodes.USER_STATUS_INVALID);
        }
        UserAccount userAccount = userDirectory.getUserById(userId)
                .orElseThrow(() -> new BusinessException(UserErrorCodes.USER_NOT_FOUND));
        userAccount.setStatus(status);
        userAccount.setUpdatedAt(LocalDateTime.now());
        userDirectory.saveUser(userAccount);
    }

    /**
     * 加载当前登录用户，并校验账号状态。
     *
     * @return 当前用户
     */
    private UserAccount loadCurrentUser() {
        long userId = StpMiniappUtil.stpLogic().getLoginIdAsLong();
        UserAccount userAccount = userDirectory.getUserById(userId)
                .orElseThrow(() -> new BusinessException(UserErrorCodes.USER_NOT_FOUND));
        if (!"ENABLED".equals(userAccount.getStatus())) {
            throw new BusinessException(UserErrorCodes.USER_ACCOUNT_DISABLED);
        }
        return userAccount;
    }

    /**
     * 判断用户是否命中关键字条件。
     *
     * @param userAccount 用户信息
     * @param keyword 关键字
     * @return 是否命中
     */
    private boolean matchKeyword(UserAccount userAccount, String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return true;
        }
        String normalizedKeyword = keyword.trim().toLowerCase(Locale.ROOT);
        return userAccount.getNickname().toLowerCase(Locale.ROOT).contains(normalizedKeyword)
                || String.valueOf(userAccount.getId()).contains(normalizedKeyword);
    }

    /**
     * 判断用户是否命中状态条件。
     *
     * @param userAccount 用户信息
     * @param status 状态
     * @return 是否命中
     */
    private boolean matchStatus(UserAccount userAccount, String status) {
        if (!StringUtils.hasText(status)) {
            return true;
        }
        return status.trim().equalsIgnoreCase(userAccount.getStatus());
    }

    /**
     * 转换用户列表项。
     *
     * @param userAccount 用户信息
     * @return 列表项响应
     */
    private UserListItemResponse toUserListItem(UserAccount userAccount) {
        UserListItemResponse item = new UserListItemResponse();
        item.setUserId(userAccount.getId());
        item.setNickname(userAccount.getNickname());
        item.setStatus(userAccount.getStatus());
        item.setTagSummary(userAccount.getTags().stream()
                .limit(3)
                .map(UserInterestTag::getTag)
                .collect(Collectors.toList()));
        item.setLastLoginTime(userAccount.getLastLoginTime());
        item.setCreatedAt(userAccount.getCreatedAt());
        return item;
    }

    /**
     * 构造学习概览演示数据。
     *
     * @param userId 用户ID
     * @return 学习概览
     */
    private UserLearningSummary buildLearningSummary(Long userId) {
        UserLearningSummary summary = new UserLearningSummary();
        summary.setGeneratedCount((int) (userId % 4 + 1));
        summary.setContentViewCount((int) (userId % 9 + 5));
        summary.setLastLearnTime(LocalDateTime.now().minusHours(userId % 6 + 1));
        return summary;
    }
}
