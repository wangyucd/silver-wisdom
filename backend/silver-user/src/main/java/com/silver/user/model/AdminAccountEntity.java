package com.silver.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.silver.common.model.BaseEntity;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * 管理员账号实体。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@TableName("admin_account")
@Getter
@Setter
public class AdminAccountEntity extends BaseEntity {

    /**
     * 管理员主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 登录账号
     */
    private String username;
    /**
     * 密码哈希
     */
    private String passwordHash;
    /**
     * 展示名称
     */
    private String name;
    /**
     * 账号状态
     */
    private String status;
    /**
     * 最近登录时间
     */
    private LocalDateTime lastLoginTime;
}
