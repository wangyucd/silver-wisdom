package com.silver.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.silver.user.enums.AccountStatusEnum;
import com.silver.user.enums.TagSourceEnum;
import com.silver.user.mapper.UserAccountMapper;
import com.silver.user.mapper.UserInterestTagMapper;
import com.silver.user.model.UserAccountEntity;
import com.silver.user.model.UserInterestTagEntity;
import com.silver.user.service.IUserAccountInfraService;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * 用户账号基础数据访问实现。
 * 提供用户账号的 CRUD 操作、标签关联查询及种子数据初始化。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@Service
public class UserAccountInfraServiceImpl extends ServiceImpl<UserAccountMapper, UserAccountEntity>
        implements IUserAccountInfraService {

    /**
     * 系统审计主体
     */
    private static final String SYSTEM_AUDIT_ACTOR = "0｜系统";

    /**
     * 用户兴趣标签 Mapper
     */
    private final UserInterestTagMapper userInterestTagMapper;

    /**
     * 构造用户账号基础服务。
     *
     * @param userInterestTagMapper 用户兴趣标签 Mapper
     */
    public UserAccountInfraServiceImpl(UserInterestTagMapper userInterestTagMapper) {
        this.userInterestTagMapper = userInterestTagMapper;
    }

    /**
     * 按 openId 查询用户并关联标签。
     *
     * @param openId 微信 openId
     * @return 用户信息
     */
    @Override
    public Optional<UserAccountEntity> findByOpenId(String openId) {
        return Optional.ofNullable(lambdaQuery().eq(UserAccountEntity::getOpenId, openId).one()).map(this::attachTags);
    }

    /**
     * 按用户ID查询用户并关联标签。
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public Optional<UserAccountEntity> findByUserId(Long userId) {
        return Optional.ofNullable(getById(userId)).map(this::attachTags);
    }

    /**
     * 创建新的小程序用户。
     * 自动分配默认昵称和状态。
     *
     * @param openId 微信 openId
     * @return 新建用户
     */
    @Override
    public UserAccountEntity createMiniappUser(String openId) {
        LocalDateTime now = LocalDateTime.now();
        UserAccountEntity userAccount = new UserAccountEntity();
        userAccount.setOpenId(openId);
        userAccount.setNickname("银发用户");
        userAccount.setAvatarUrl("");
        userAccount.setStatus(AccountStatusEnum.ENABLED.getCode());
        userAccount.setCreator(SYSTEM_AUDIT_ACTOR);
        userAccount.setModifier(SYSTEM_AUDIT_ACTOR);
        userAccount.setCreated(now);
        userAccount.setModified(now);
        userAccount.setLastLoginTime(now);
        save(userAccount);
        userAccount.setNickname("银发用户" + userAccount.getId());
        updateById(userAccount);
        return userAccount;
    }

    /**
     * 查询全部用户并关联标签。
     *
     * @return 用户列表
     */
    @Override
    public List<UserAccountEntity> listUsersWithTags() {
        return list().stream().map(this::attachTags).collect(Collectors.toList());
    }

    /**
     * 初始化用户种子数据。
     * 仅在数据库为空时执行。
     */
    @PostConstruct
    public void initSeedData() {
        seedUsers();
    }

    /**
     * 为用户附加标签信息。
     *
     * @param userAccount 用户信息
     * @return 附加标签后的用户信息
     */
    private UserAccountEntity attachTags(UserAccountEntity userAccount) {
        userAccount.getTags().clear();
        userAccount.getTags().addAll(userInterestTagMapper.selectList(
                Wrappers.<UserInterestTagEntity>lambdaQuery().eq(UserInterestTagEntity::getUserId, userAccount.getId())
        ));
        return userAccount;
    }

    /**
     * 初始化种子用户数据。
     */
    private void seedUsers() {
        if (count() > 0) {
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        saveSeedUser("mock_seed_pet", "银发用户10001", now.minusDays(2), AccountStatusEnum.ENABLED,
                List.of(new UserInterestTagEntity("宠物", 1.0), new UserInterestTagEntity("健康", 0.8)));
        saveSeedUser("mock_seed_novel", "银发用户10002", now.minusDays(1), AccountStatusEnum.ENABLED,
                List.of(new UserInterestTagEntity("小说", 1.0), new UserInterestTagEntity("二次元", 0.6)));
        saveSeedUser("mock_seed_disabled", "银发用户10003", now.minusDays(3), AccountStatusEnum.DISABLED,
                List.of(new UserInterestTagEntity("养生", 1.0)));
    }

    /**
     * 保存种子用户及标签。
     *
     * @param openId 微信 openId
     * @param nickname 用户昵称
     * @param created 创建时间
     * @param status 用户状态
     * @param tags 标签列表
     */
    private void saveSeedUser(String openId,
                              String nickname,
                              LocalDateTime created,
                              AccountStatusEnum status,
                              List<UserInterestTagEntity> tags) {
        UserAccountEntity userAccount = new UserAccountEntity();
        userAccount.setOpenId(openId);
        userAccount.setNickname(nickname);
        userAccount.setAvatarUrl("");
        userAccount.setStatus(status.getCode());
        userAccount.setCreator(SYSTEM_AUDIT_ACTOR);
        userAccount.setModifier(SYSTEM_AUDIT_ACTOR);
        userAccount.setCreated(created);
        userAccount.setModified(created);
        userAccount.setLastLoginTime(created.plusHours(6));
        save(userAccount);
        for (UserInterestTagEntity tag : tags) {
            tag.setUserId(userAccount.getId());
            tag.setSource(TagSourceEnum.QUESTIONNAIRE.getCode());
            tag.setCreator(SYSTEM_AUDIT_ACTOR);
            tag.setModifier(SYSTEM_AUDIT_ACTOR);
            tag.setCreated(created);
            tag.setModified(created);
            userInterestTagMapper.insert(tag);
        }
    }
}
