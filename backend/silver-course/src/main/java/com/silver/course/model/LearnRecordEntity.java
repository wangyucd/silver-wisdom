package com.silver.course.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

/**
 * 学习记录实体。
 */
@TableName("learn_record")
public class LearnRecordEntity {

    /** 主键ID。 */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 用户ID。 */
    private Long userId;
    /** 记录类型。 */
    private String recordType;
    /** 关联业务ID。 */
    private String bizId;
    /** 学习进度。 */
    private Integer progress;
    /** 最近查看时间。 */
    private LocalDateTime lastViewTime;
    /** 创建时间。 */
    private LocalDateTime createdAt;
    /** 更新时间。 */
    private LocalDateTime updatedAt;

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

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public LocalDateTime getLastViewTime() {
        return lastViewTime;
    }

    public void setLastViewTime(LocalDateTime lastViewTime) {
        this.lastViewTime = lastViewTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
