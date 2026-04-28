package com.silver.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.silver.user.model.AdminAccountEntity;
import java.util.Optional;

/**
 * 管理员账号基础数据访问接口。
 * 提供管理员账号的基础 CRUD 及查询能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
public interface IAdminAccountInfraService extends IService<AdminAccountEntity> {

    /**
     * 按登录账号查询管理员。
     *
     * @param username 登录账号
     * @return 管理员信息
     */
    Optional<AdminAccountEntity> findByUsername(String username);

    /**
     * 按管理员ID查询管理员。
     *
     * @param adminId 管理员ID
     * @return 管理员信息
     */
    Optional<AdminAccountEntity> findByAdminId(Long adminId);
}
