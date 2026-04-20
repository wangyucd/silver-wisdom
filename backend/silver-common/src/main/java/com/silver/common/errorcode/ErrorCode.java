package com.silver.common.errorcode;

/**
 * 错误码抽象。
 */
public interface ErrorCode {

    /**
     * 返回业务错误码。
     *
     * @return 业务错误码
     */
    int getCode();

    /**
     * 返回默认错误信息。
     *
     * @return 默认错误信息
     */
    String getMessage();
}
