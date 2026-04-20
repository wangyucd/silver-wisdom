package com.silver.content.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

/**
 * AI 生成任务。
 */
@TableName("ai_generate_task")
public class AiGenerateTaskEntity {

    /** 主键ID。 */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 任务号。 */
    private String taskId;
    /** 用户ID。 */
    private Long userId;
    /** 生成提示词。 */
    private String prompt;
    /** 生成场景。 */
    private String scene;
    /** 任务状态。 */
    private String status;
    /** 失败原因。 */
    private String failReason;
    /** 生成结果。 */
    @TableField(exist = false)
    private AiGenerateResultEntity result;
    /** 创建时间。 */
    private LocalDateTime createdAt;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public AiGenerateResultEntity getResult() {
        return result;
    }

    public void setResult(AiGenerateResultEntity result) {
        this.result = result;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
