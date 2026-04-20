package com.silver.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.silver.user.model.AdminAccountEntity;
import java.util.Optional;

/**
 * 管理员账号基础数据访问接口。
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
