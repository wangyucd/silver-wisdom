package com.silver.course.model;

import java.time.LocalDateTime;

/**
 * 已生成学习内容列表项。
 */
public class GeneratedLearnItem {

    /** 任务号。 */
    private String taskId;
    /** 标题。 */
    private String title;
    /** 摘要。 */
    private String summary;
    /** 创建时间。 */
    private LocalDateTime createdAt;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
