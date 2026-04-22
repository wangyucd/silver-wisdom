package com.silver.user.converter;

import com.silver.user.model.AdminAccountEntity;
import com.silver.user.model.UserAccountEntity;
import com.silver.user.model.UserInterestTagEntity;
import com.silver.user.model.UserLearningSummary;
import com.silver.user.model.response.AdminLoginResponse;
import com.silver.user.model.response.AdminProfileResponse;
import com.silver.user.model.response.CurrentUserResponse;
import com.silver.user.model.response.LoginResponse;
import com.silver.user.model.response.UserDetailResponse;
import com.silver.user.model.response.UserListItemResponse;
import com.silver.user.model.response.UserTagResponse;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * 用户域响应对象转换器。
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserResponseConverter {

    /**
     * 转换管理员登录响应。
     *
     * @param adminAccount 管理员信息
     * @param token 登录令牌
     * @param loginType 登录类型
     * @return 登录响应
     */
    @Mapping(target = "adminId", source = "adminAccount.id")
    @Mapping(target = "name", source = "adminAccount.name")
    @Mapping(target = "token", source = "token")
    @Mapping(target = "loginType", source = "loginType")
    AdminLoginResponse toAdminLoginResponse(AdminAccountEntity adminAccount, String token, String loginType);

    /**
     * 转换管理员资料响应。
     *
     * @param adminAccount 管理员信息
     * @param loginType 登录类型
     * @return 管理员资料响应
     */
    @Mapping(target = "adminId", source = "adminAccount.id")
    @Mapping(target = "username", source = "adminAccount.username")
    @Mapping(target = "name", source = "adminAccount.name")
    @Mapping(target = "status", source = "adminAccount.status")
    @Mapping(target = "loginType", source = "loginType")
    AdminProfileResponse toAdminProfileResponse(AdminAccountEntity adminAccount, String loginType);

    /**
     * 转换用户登录响应。
     *
     * @param userAccount 用户信息
     * @param token 登录令牌
     * @param newUser 是否新用户
     * @param loginType 登录类型
     * @return 用户登录响应
     */
    @Mapping(target = "userId", source = "userAccount.id")
    @Mapping(target = "token", source = "token")
    @Mapping(target = "newUser", source = "newUser")
    @Mapping(target = "loginType", source = "loginType")
    LoginResponse toLoginResponse(UserAccountEntity userAccount, String token, boolean newUser, String loginType);

    /**
     * 转换当前用户响应。
     *
     * @param userAccount 用户信息
     * @param newUser 是否新用户
     * @return 当前用户响应
     */
    @Mapping(target = "userId", source = "userAccount.id")
    @Mapping(target = "openId", source = "userAccount.openId")
    @Mapping(target = "nickname", source = "userAccount.nickname")
    @Mapping(target = "avatarUrl", source = "userAccount.avatarUrl")
    @Mapping(target = "status", source = "userAccount.status")
    @Mapping(target = "newUser", source = "newUser")
    CurrentUserResponse toCurrentUserResponse(UserAccountEntity userAccount, boolean newUser);

    /**
     * 转换用户详情响应。
     *
     * @param userAccount 用户信息
     * @param learningSummary 学习概览
     * @return 用户详情响应
     */
    @Mapping(target = "userId", source = "userAccount.id")
    @Mapping(target = "nickname", source = "userAccount.nickname")
    @Mapping(target = "avatarUrl", source = "userAccount.avatarUrl")
    @Mapping(target = "status", source = "userAccount.status")
    @Mapping(target = "tagList", source = "userAccount.tags")
    @Mapping(target = "learningSummary", source = "learningSummary")
    @Mapping(target = "createdAt", source = "userAccount.created")
    @Mapping(target = "lastLoginTime", source = "userAccount.lastLoginTime")
    UserDetailResponse toUserDetailResponse(UserAccountEntity userAccount, UserLearningSummary learningSummary);

    /**
     * 转换用户列表项响应。
     *
     * @param userAccount 用户信息
     * @param tagSummary 标签摘要
     * @return 用户列表项响应
     */
    @Mapping(target = "userId", source = "userAccount.id")
    @Mapping(target = "nickname", source = "userAccount.nickname")
    @Mapping(target = "status", source = "userAccount.status")
    @Mapping(target = "tagSummary", source = "tagSummary")
    @Mapping(target = "lastLoginTime", source = "userAccount.lastLoginTime")
    @Mapping(target = "createdAt", source = "userAccount.created")
    UserListItemResponse toUserListItemResponse(UserAccountEntity userAccount, List<String> tagSummary);

    /**
     * 转换用户标签响应。
     *
     * @param tags 标签列表
     * @return 用户标签响应
     */
    default UserTagResponse toUserTagResponse(List<UserInterestTagEntity> tags) {
        UserTagResponse response = new UserTagResponse();
        response.setTags(tags);
        return response;
    }
}
