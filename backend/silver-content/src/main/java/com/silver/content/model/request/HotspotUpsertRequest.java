package com.silver.content.model.request;

/**
 * 热点新增或更新请求。
 */
public class HotspotUpsertRequest {

    /** 热点标题。 */
    private String title;
    /** 热点摘要。 */
    private String summary;
    /** 封面图地址。 */
    private String coverUrl;
    /** 热点权重。 */
    private int weight;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
