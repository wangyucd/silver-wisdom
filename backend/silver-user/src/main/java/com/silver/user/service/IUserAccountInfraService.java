package com.silver.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.silver.user.model.UserAccountEntity;
import java.util.List;
import java.util.Optional;

/**
 * 用户账号基础数据访问接口。
 */
public interface IUserAccountInfraService extends IService<UserAccountEntity> {

    /**
     * 按 openId 查询用户。
     *
     * @param openId 微信 openId
     * @return 用户信息
     */
    Optional<UserAccountEntity> findByOpenId(String openId);

    /**
     * 按用户ID查询用户。
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    Optional<UserAccountEntity> findByUserId(Long userId);

    /**
     * 创建小程序用户。
     *
     * @param openId 微信 openId
     * @return 新建用户
     */
    UserAccountEntity createMiniappUser(String openId);

    /**
     * 查询全部用户并携带标签。
     *
     * @return 用户列表
     */
    List<UserAccountEntity> listUsersWithTags();
}
