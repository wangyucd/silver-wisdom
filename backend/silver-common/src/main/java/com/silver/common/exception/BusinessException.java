package com.silver.common.exception;

import com.silver.common.errorcode.ErrorCode;

/**
 * 业务异常。
 */
public class BusinessException extends RuntimeException {

    /**
     * 业务错误码。
     */
    private final int code;

    /**
     * 基于默认失败信息构造异常。
     *
     * @param message 异常信息
     */
    public BusinessException(String message) {
        this(500, message);
    }

    /**
     * 基于错误码与异常信息构造异常。
     *
     * @param code 业务错误码
     * @param message 异常信息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 基于统一错误码定义构造异常。
     *
     * @param errorCode 统一错误码
     */
    public BusinessException(ErrorCode errorCode) {
        this(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 返回业务错误码。
     *
     * @return 业务错误码
     */
    public int getCode() {
        return code;
    }
}
