package com.silver.content.model.response;

import com.silver.content.model.AiGenerateResult;

public class AiGenerateTaskResponse {

    private String taskId;
    private String status;
    private AiGenerateResult result;
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

    public AiGenerateResult getResult() {
        return result;
    }

    public void setResult(AiGenerateResult result) {
        this.result = result;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }
}
