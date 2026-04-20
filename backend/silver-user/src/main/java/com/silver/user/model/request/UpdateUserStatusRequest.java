package com.silver.user.model.request;

/**
 * 用户状态更新请求。
 */
public class UpdateUserStatusRequest {

    /** 目标状态。 */
    private String status;
    /** 状态变更原因。 */
    private String reason;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
