package com.silver.user.service.impl;

import com.silver.common.auth.StpAdminUtil;
import com.silver.common.exception.BusinessException;
import com.silver.user.errorcode.UserErrorCodes;
import com.silver.user.model.AdminAccount;
import com.silver.user.model.request.AdminLoginRequest;
import com.silver.user.model.response.AdminLoginResponse;
import com.silver.user.model.response.AdminProfileResponse;
import com.silver.user.service.AdminAuthService;
import com.silver.user.service.UserDirectory;
import java.time.LocalDateTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 管理员认证服务实现。
 */
@Service
public class AdminAuthServiceImpl implements AdminAuthService {

    /**
     * 用户目录服务。
     */
    private final UserDirectory userDirectory;
    /**
     * 密码编码器。
     */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 构造管理员认证服务。
     *
     * @param userDirectory 用户目录服务
     * @param passwordEncoder 密码编码器
     */
    public AdminAuthServiceImpl(UserDirectory userDirectory, BCryptPasswordEncoder passwordEncoder) {
        this.userDirectory = userDirectory;
        this.passwordEncoder = passwordEncoder;
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

        AdminAccount adminAccount = userDirectory.findAdminByUsername(request.getUsername().trim())
                .orElseThrow(() -> new BusinessException(UserErrorCodes.ADMIN_LOGIN_FAIL));
        if (!"ENABLED".equals(adminAccount.getStatus())) {
            throw new BusinessException(UserErrorCodes.ADMIN_ACCOUNT_DISABLED);
        }
        if (!passwordEncoder.matches(request.getPassword(), adminAccount.getPasswordHash())) {
            throw new BusinessException(UserErrorCodes.ADMIN_LOGIN_FAIL);
        }

        adminAccount.setLastLoginTime(LocalDateTime.now());
        adminAccount.setUpdatedAt(LocalDateTime.now());
        userDirectory.saveAdmin(adminAccount);

        StpAdminUtil.stpLogic().login(adminAccount.getId());
        AdminLoginResponse response = new AdminLoginResponse();
        response.setToken(StpAdminUtil.stpLogic().getTokenValue());
        response.setAdminId(adminAccount.getId());
        response.setName(adminAccount.getName());
        response.setLoginType(StpAdminUtil.LOGIN_TYPE);
        return response;
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
        AdminAccount adminAccount = userDirectory.getAdminById(adminId)
                .orElseThrow(() -> new BusinessException(UserErrorCodes.ADMIN_NOT_FOUND));

        AdminProfileResponse response = new AdminProfileResponse();
        response.setAdminId(adminAccount.getId());
        response.setUsername(adminAccount.getUsername());
        response.setName(adminAccount.getName());
        response.setStatus(adminAccount.getStatus());
        response.setLoginType(StpAdminUtil.LOGIN_TYPE);
        return response;
    }
}
