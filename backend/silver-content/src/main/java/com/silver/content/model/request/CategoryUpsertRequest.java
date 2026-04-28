package com.silver.content.model.request;

/**
 * 分类新增或更新请求。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
public class CategoryUpsertRequest {

    /**
     * 分类名称
     */
    private String name;
    /**
     * 图标地址
     */
    private String iconUrl;
    /**
     * 封面地址
     */
    private String coverUrl;
    /**
     * 排序值
     */
    private int sort;

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
}
