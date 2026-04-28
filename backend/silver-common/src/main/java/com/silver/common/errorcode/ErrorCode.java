package com.silver.common.errorcode;

/**
 * 错误码抽象接口。
 * 定义错误码枚举的标准契约，包括错误码值和默认错误信息。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
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
