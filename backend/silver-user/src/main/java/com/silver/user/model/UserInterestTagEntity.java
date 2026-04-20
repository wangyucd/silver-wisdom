package com.silver.user.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 用户兴趣标签。
 */
@TableName("user_interest_tag")
public class UserInterestTagEntity {

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

    public UserInterestTagEntity() {
    }

    public UserInterestTagEntity(String tag, double weight) {
        this.tag = tag;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
