package com.silver.content.model.request;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 内容新增或更新请求。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
public class ContentUpsertRequest {

    /**
     * 内容标题
     */
    private String title;
    /**
     * 内容摘要
     */
    private String summary;
    /**
     * 封面图地址
     */
    private String coverUrl;
    /**
     * 内容类型
     */
    private String type;
    /**
     * 图文正文
     */
    private String contentBody;
    /**
     * 视频地址
     */
    private String videoUrl;
    /**
     * 分类ID
     */
    private Long categoryId;
    /**
     * 热度分值
     */
    private int heatScore;
    /**
     * 标签列表
     */
    private List<String> tags;
    /**
     * 发布时间
     */
    private LocalDateTime publishTime;
    /**
     * 发布状态
     */
    private String publishStatus;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContentBody() {
        return contentBody;
    }

    public void setContentBody(String contentBody) {
        this.contentBody = contentBody;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Long getCategoryEntityId() {
        return categoryId;
    }

    public void setCategoryEntityId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public int getHeatScore() {
        return heatScore;
    }

    public void setHeatScore(int heatScore) {
        this.heatScore = heatScore;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(String publishStatus) {
        this.publishStatus = publishStatus;
    }
}
