package com.silver.content.model.response;

import com.silver.content.model.AiGenerateResultEntity;

/**
 * AI 生成任务响应。
 */
public class AiGenerateTaskResponse {

    /** 任务号。 */
    private String taskId;
    /** 任务状态。 */
    private String status;
    /** 生成结果。 */
    private AiGenerateResultEntity result;
    /** 失败原因。 */
    private String failReason;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AiGenerateResultEntity getResult() {
        return result;
    }

    public void setResult(AiGenerateResultEntity result) {
        this.result = result;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}
