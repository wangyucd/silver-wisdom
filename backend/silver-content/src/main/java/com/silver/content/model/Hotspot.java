package com.silver.content.model;

import java.time.LocalDateTime;

/**
 * 热点实体。
 */
public class Hotspot {

    /** 热点ID。 */
    private Long id;
    /** 热点标题。 */
    private String title;
    /** 热点摘要。 */
    private String summary;
    /** 热点封面。 */
    private String coverUrl;
    /** 热点权重。 */
    private int weight;
    /** 上线时间。 */
    private LocalDateTime startTime;
    /** 下线时间。 */
    private LocalDateTime endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
