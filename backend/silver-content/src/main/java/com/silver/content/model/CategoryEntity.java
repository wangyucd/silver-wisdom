package com.silver.content.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.silver.common.model.BaseEntity;

/**
 * 内容分类实体。
 */
@TableName("category")
public class CategoryEntity extends BaseEntity {

    /** 分类ID。 */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 分类名称。 */
    private String name;
    /** 分类图标地址。 */
    private String iconUrl;
    /** 分类封面地址。 */
    private String coverUrl;
    /** 排序值。 */
    private int sort;
    /** 分类状态。 */
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
