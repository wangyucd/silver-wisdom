package com.silver.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.silver.common.model.BaseEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * 小程序用户实体。
 */
@TableName("user_account")
@Getter
@Setter
public class UserAccountEntity extends BaseEntity {

    /** 用户主键。 */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 微信 openId。 */
    @TableField("wx_openid")
    private String openId;
    /** 用户昵称。 */
    private String nickname;
    /** 用户头像地址。 */
    private String avatarUrl;
    /** 用户状态。 */
    private String status;
    /** 最近登录时间。 */
    private LocalDateTime lastLoginTime;
    /** 兴趣标签列表。 */
    @TableField(exist = false)
    @Setter(AccessLevel.NONE)
    private final List<UserInterestTagEntity> tags = new ArrayList<>();
}
