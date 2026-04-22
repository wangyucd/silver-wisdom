package com.silver.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.silver.common.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 用户兴趣标签。
 */
@TableName("user_interest_tag")
@Getter
@Setter
@NoArgsConstructor
public class UserInterestTagEntity extends BaseEntity {

    /** 主键ID。 */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 用户ID。 */
    private Long userId;
    /** 标签名称。 */
    private String tag;
    /** 标签权重。 */
    private double weight;
    /** 标签来源。 */
    private String source;

    public UserInterestTagEntity(String tag, double weight) {
        this.tag = tag;
        this.weight = weight;
    }
}
