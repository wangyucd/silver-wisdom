package com.silver.content.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.silver.common.model.BaseEntity;

/**
 * 内容标签关联实体。
 */
@TableName("content_tag_rel")
public class ContentTagRelEntity extends BaseEntity {

    /** 主键ID。 */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 内容ID。 */
    private Long contentId;
    /** 标签名称。 */
    private String tag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
