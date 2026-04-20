package com.silver.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.silver.user.mapper.AdminAccountMapper;
import com.silver.user.model.AdminAccountEntity;
import com.silver.user.service.IAdminAccountInfraService;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 管理员账号基础数据访问实现。
 */
@Service
public class AdminAccountInfraServiceImpl extends ServiceImpl<AdminAccountMapper, AdminAccountEntity>
        implements IAdminAccountInfraService {

    /**
     * 密码编码器。
     */
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 构造管理员基础数据访问实现。
     *
     * @param passwordEncoder 密码编码器
     */
    public AdminAccountInfraServiceImpl(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 按账号查询管理员。
     *
     * @param username 登录账号
     * @return 管理员信息
     */
    @Override
    public Optional<AdminAccountEntity> findByUsername(String username) {
        return Optional.ofNullable(lambdaQuery().eq(AdminAccountEntity::getUsername, username).one());
    }

    /**
     * 按ID查询管理员。
     *
     * @param adminId 管理员ID
     * @return 管理员信息
     */
    @Override
    public Optional<AdminAccountEntity> findByAdminId(Long adminId) {
        return Optional.ofNullable(getById(adminId));
    }

    /**
     * 初始化管理员种子数据。
     */
    @PostConstruct
    public void initSeedData() {
        seedAdmin(passwordEncoder);
    }

    /**
     * 初始化管理员种子数据。
     *
     * @param passwordEncoder 密码编码器
     */
    private void seedAdmin(BCryptPasswordEncoder passwordEncoder) {
        if (lambdaQuery().eq(AdminAccountEntity::getUsername, "admin").count() > 0) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        AdminAccountEntity admin = new AdminAccountEntity();
        admin.setUsername("admin");
        admin.setPasswordHash(passwordEncoder.encode("Admin@123"));
        admin.setName("系统管理员");
        admin.setStatus("ENABLED");
        admin.setCreatedAt(now.minusDays(7));
        admin.setUpdatedAt(now.minusDays(7));
        admin.setLastLoginTime(now.minusHours(2));
        save(admin);
    }
}
