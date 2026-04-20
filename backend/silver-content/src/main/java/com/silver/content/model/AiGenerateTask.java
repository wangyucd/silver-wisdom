package com.silver.content.model;

import java.time.LocalDateTime;

/**
 * AI 生成任务。
 */
public class AiGenerateTask {

    /** 任务号。 */
    private String taskId;
    /** 生成提示词。 */
    private String prompt;
    /** 生成场景。 */
    private String scene;
    /** 任务状态。 */
    private String status;
    /** 失败原因。 */
    private String failReason;
    /** 生成结果。 */
    private AiGenerateResult result;
    /** 创建时间。 */
    private LocalDateTime createdAt;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public AiGenerateResult getResult() {
        return result;
    }

    public void setResult(AiGenerateResult result) {
        this.result = result;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
