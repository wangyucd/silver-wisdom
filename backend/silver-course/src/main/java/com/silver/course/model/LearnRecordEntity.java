package com.silver.course.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.silver.common.model.BaseEntity;
import java.time.LocalDateTime;

/**
 * 学习记录实体。
 * 记录用户的学习行为和进度。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@TableName("learn_record")
public class LearnRecordEntity extends BaseEntity {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 记录类型
     */
    private String recordType;
    /**
     * 关联业务ID
     */
    private String bizId;
    /**
     * 学习进度
     */
    private Integer progress;
    /**
     * 最近查看时间
     */
    private LocalDateTime lastViewTime;

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

}
