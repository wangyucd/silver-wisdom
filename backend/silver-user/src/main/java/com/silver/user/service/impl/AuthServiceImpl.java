package com.silver.user.service.impl;

import com.silver.common.auth.StpMiniappUtil;
import com.silver.common.exception.BusinessException;
import com.silver.user.converter.UserResponseConverter;
import com.silver.user.errorcode.UserErrorCodes;
import com.silver.user.model.UserAccount;
import com.silver.user.model.request.WxLoginRequest;
import com.silver.user.model.response.CurrentUserResponse;
import com.silver.user.model.response.LoginResponse;
import com.silver.user.service.AuthService;
import com.silver.user.service.UserDirectory;
import com.silver.user.service.WxOpenIdResolver;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 小程序认证服务实现。
 */
@Service
public class AuthServiceImpl implements AuthService {

    /**
     * 微信 openId 解析器。
     */
    private final WxOpenIdResolver wxOpenIdResolver;
    /**
     * 用户目录服务。
     */
    private final UserDirectory userDirectory;
    /**
     * 用户响应转换器。
     */
    private final UserResponseConverter userResponseConverter;

    /**
     * 构造小程序认证服务。
     *
     * @param wxOpenIdResolver 微信 openId 解析器
     * @param userDirectory 用户目录服务
     */
    public AuthServiceImpl(WxOpenIdResolver wxOpenIdResolver,
                           UserDirectory userDirectory,
                           UserResponseConverter userResponseConverter) {
        this.wxOpenIdResolver = wxOpenIdResolver;
        this.userDirectory = userDirectory;
        this.userResponseConverter = userResponseConverter;
    }

    /**
     * 执行微信登录。
     *
     * @param request 微信登录请求
     * @return 登录响应
     */
    @Override
    public LoginResponse wxLogin(WxLoginRequest request) {
        if (request == null || !StringUtils.hasText(request.getCode())) {
            throw new BusinessException(UserErrorCodes.WX_LOGIN_CODE_REQUIRED);
        }

        String openId = wxOpenIdResolver.resolveOpenId(request.getCode().trim());
        UserAccount userAccount = userDirectory.findUserByOpenId(openId).orElse(null);
        boolean newUser = false;
        if (userAccount == null) {
            userAccount = userDirectory.createMiniappUser(openId);
            newUser = true;
        }
        if (!"ENABLED".equals(userAccount.getStatus())) {
            throw new BusinessException(UserErrorCodes.USER_ACCOUNT_DISABLED);
        }

        userAccount.setLastLoginTime(LocalDateTime.now());
        userAccount.setUpdatedAt(LocalDateTime.now());
        userDirectory.saveUser(userAccount);

        StpMiniappUtil.stpLogic().login(userAccount.getId());
        return userResponseConverter.toLoginResponse(
                userAccount,
                StpMiniappUtil.stpLogic().getTokenValue(),
                newUser,
                StpMiniappUtil.LOGIN_TYPE
        );
    }

    /**
     * 执行小程序登出。
     */
    @Override
    public void logout() {
        StpMiniappUtil.stpLogic().checkLogin();
        StpMiniappUtil.stpLogic().logout();
    }

    /**
     * 查询当前登录用户。
     *
     * @return 当前登录用户
     */
    @Override
    public CurrentUserResponse currentUser() {
        StpMiniappUtil.stpLogic().checkLogin();

        long userId = StpMiniappUtil.stpLogic().getLoginIdAsLong();
        UserAccount userAccount = userDirectory.getUserById(userId)
                .orElseThrow(() -> new BusinessException(UserErrorCodes.CURRENT_USER_NOT_FOUND));
        if (!"ENABLED".equals(userAccount.getStatus())) {
            throw new BusinessException(UserErrorCodes.USER_ACCOUNT_DISABLED);
        }

        return userResponseConverter.toCurrentUserResponse(
                userAccount,
                userAccount.getCreatedAt() != null && userAccount.getCreatedAt().plusMinutes(5).isAfter(LocalDateTime.now())
        );
    }
}
