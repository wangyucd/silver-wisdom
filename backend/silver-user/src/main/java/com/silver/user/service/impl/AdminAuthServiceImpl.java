package com.silver.user.service.impl;

import com.silver.common.auth.StpAdminUtil;
import com.silver.common.exception.BusinessException;
import com.silver.user.converter.UserResponseConverter;
import com.silver.user.errorcode.UserErrorCodes;
import com.silver.user.enums.AccountStatusEnum;
import com.silver.user.model.AdminAccountEntity;
import com.silver.user.model.request.AdminLoginRequest;
import com.silver.user.model.response.AdminLoginResponse;
import com.silver.user.model.response.AdminProfileResponse;
import com.silver.user.service.AdminAuthService;
import com.silver.user.service.IAdminAccountInfraService;
import java.time.LocalDateTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 管理员认证服务实现。
 * 提供管理员登录、登出及当前管理员信息查询能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@Service
public class AdminAuthServiceImpl implements AdminAuthService {

    /**
     * 审计主体默认名称
     */
    private static final String ADMIN_AUDIT_NAME = "管理员";

    /**
     * 用户目录服务。
     */
    private final IAdminAccountInfraService adminAccountInfraService;
    /**
     * 密码编码器。
     */
    private final BCryptPasswordEncoder passwordEncoder;
    /**
     * 用户响应转换器。
     */
    private final UserResponseConverter userResponseConverter;

    /**
     * 构造管理员认证服务。
     *
     * @param adminAccountInfraService 管理员账号基础服务
     * @param passwordEncoder 密码编码器
     * @param userResponseConverter 用户响应转换器
     */
    public AdminAuthServiceImpl(IAdminAccountInfraService adminAccountInfraService,
                                BCryptPasswordEncoder passwordEncoder,
                                UserResponseConverter userResponseConverter) {
        this.adminAccountInfraService = adminAccountInfraService;
        this.passwordEncoder = passwordEncoder;
        this.userResponseConverter = userResponseConverter;
    }

    /**
     * 执行管理员登录。
     *
     * @param request 登录请求
     * @return 登录响应
     */
    @Override
    public AdminLoginResponse login(AdminLoginRequest request) {
        if (request == null || !StringUtils.hasText(request.getUsername()) || !StringUtils.hasText(request.getPassword())) {
            throw new BusinessException(UserErrorCodes.ADMIN_LOGIN_PARAM_INVALID);
        }

        AdminAccountEntity adminAccount = adminAccountInfraService.findByUsername(request.getUsername().trim())
                .orElseThrow(() -> new BusinessException(UserErrorCodes.ADMIN_LOGIN_FAIL));
        if (!AccountStatusEnum.ENABLED.matches(adminAccount.getStatus())) {
            throw new BusinessException(UserErrorCodes.ADMIN_ACCOUNT_DISABLED);
        }
        if (!passwordEncoder.matches(request.getPassword(), adminAccount.getPasswordHash())) {
            throw new BusinessException(UserErrorCodes.ADMIN_LOGIN_FAIL);
        }

        adminAccount.setLastLoginTime(LocalDateTime.now());
        adminAccount.setModifier(buildAuditActor(adminAccount.getId(), adminAccount.getName()));
        adminAccount.setModified(LocalDateTime.now());
        adminAccountInfraService.updateById(adminAccount);

        StpAdminUtil.stpLogic().login(adminAccount.getId());
        return userResponseConverter.toAdminLoginResponse(
                adminAccount,
                StpAdminUtil.stpLogic().getTokenValue(),
                StpAdminUtil.LOGIN_TYPE
        );
    }

    /**
     * 执行管理员登出。
     */
    @Override
    public void logout() {
        StpAdminUtil.stpLogic().checkLogin();
        StpAdminUtil.stpLogic().logout();
    }

    /**
     * 查询当前管理员信息。
     *
     * @return 当前管理员信息
     */
    @Override
    public AdminProfileResponse currentAdmin() {
        StpAdminUtil.stpLogic().checkLogin();
        long adminId = StpAdminUtil.stpLogic().getLoginIdAsLong();
        AdminAccountEntity adminAccount = adminAccountInfraService.findByAdminId(adminId)
                .orElseThrow(() -> new BusinessException(UserErrorCodes.ADMIN_NOT_FOUND));

        return userResponseConverter.toAdminProfileResponse(adminAccount, StpAdminUtil.LOGIN_TYPE);
    }

    /**
     * 构造审计主体
     *
     * @param adminId 管理员ID
     * @param displayName 管理员名称
     * @return 审计主体
     */
    private String buildAuditActor(Long adminId, String displayName) {
        String resolvedName = StringUtils.hasText(displayName) ? displayName.trim() : ADMIN_AUDIT_NAME;
        return adminId + "｜" + resolvedName;
    }
}
