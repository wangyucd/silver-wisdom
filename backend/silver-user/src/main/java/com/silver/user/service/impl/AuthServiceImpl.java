package com.silver.user.service.impl;

import com.silver.common.auth.StpMiniappUtil;
import com.silver.common.exception.BusinessException;
import com.silver.user.converter.UserResponseConverter;
import com.silver.user.errorcode.UserErrorCodes;
import com.silver.user.enums.AccountStatusEnum;
import com.silver.user.model.UserAccountEntity;
import com.silver.user.model.request.WxLoginRequest;
import com.silver.user.model.response.CurrentUserResponse;
import com.silver.user.model.response.LoginResponse;
import com.silver.user.service.AuthService;
import com.silver.user.service.IUserAccountInfraService;
import com.silver.user.service.WxOpenIdResolver;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 小程序认证服务实现。
 * 提供微信小程序登录、登出及当前用户信息查询能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@Service
public class AuthServiceImpl implements AuthService {

    /**
     * 审计主体名称
     */
    private static final String MINIAPP_USER_AUDIT_NAME = "小程序用户";

    /**
     * 微信 openId 解析器。
     */
    private final WxOpenIdResolver wxOpenIdResolver;
    /**
     * 用户目录服务。
     */
    private final IUserAccountInfraService userAccountInfraService;
    /**
     * 用户响应转换器。
     */
    private final UserResponseConverter userResponseConverter;

    /**
     * 构造小程序认证服务。
     *
     * @param wxOpenIdResolver 微信 openId 解析器
     * @param userAccountInfraService 用户账号基础服务
     * @param userResponseConverter 用户响应转换器
     */
    public AuthServiceImpl(WxOpenIdResolver wxOpenIdResolver,
                           IUserAccountInfraService userAccountInfraService,
                           UserResponseConverter userResponseConverter) {
        this.wxOpenIdResolver = wxOpenIdResolver;
        this.userAccountInfraService = userAccountInfraService;
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
        UserAccountEntity userAccount = userAccountInfraService.findByOpenId(openId).orElse(null);
        boolean newUser = false;
        if (userAccount == null) {
            userAccount = userAccountInfraService.createMiniappUser(openId);
            newUser = true;
        }
        if (!AccountStatusEnum.ENABLED.matches(userAccount.getStatus())) {
            throw new BusinessException(UserErrorCodes.USER_ACCOUNT_DISABLED);
        }

        userAccount.setLastLoginTime(LocalDateTime.now());
        userAccount.setModifier(buildAuditActor(userAccount.getId(), userAccount.getNickname(), MINIAPP_USER_AUDIT_NAME));
        userAccount.setModified(LocalDateTime.now());
        userAccountInfraService.updateById(userAccount);

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
        UserAccountEntity userAccount = userAccountInfraService.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(UserErrorCodes.CURRENT_USER_NOT_FOUND));
        if (!AccountStatusEnum.ENABLED.matches(userAccount.getStatus())) {
            throw new BusinessException(UserErrorCodes.USER_ACCOUNT_DISABLED);
        }

        return userResponseConverter.toCurrentUserResponse(
                userAccount,
                userAccount.getCreated() != null && userAccount.getCreated().plusMinutes(5).isAfter(LocalDateTime.now())
        );
    }

    /**
     * 构造审计主体
     *
     * @param id 主体ID
     * @param displayName 展示名称
     * @param defaultName 默认名称
     * @return 审计主体
     */
    private String buildAuditActor(Long id, String displayName, String defaultName) {
        String resolvedName = StringUtils.hasText(displayName) ? displayName.trim() : defaultName;
        return id + "｜" + resolvedName;
    }
}
