package com.silver.user.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户状态更新请求。
 */
@Getter
@Setter
public class UpdateUserStatusRequest {

    /** 目标状态。 */
    private String status;
    /** 状态变更原因。 */
    private String reason;

}
