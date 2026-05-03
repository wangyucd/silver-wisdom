package com.silver.common.api;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一响应体。
 * 封装所有 API 接口的统一返回格式。
 *
 * @param <T> 数据类型
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
public class R<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应码。
     */
    private int code;
    /**
     * 响应消息。
     */
    private String message;
    /**
     * 响应数据。
     */
    private T data;

    /**
     * 构造成功响应。
     *
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 成功响应
     */
    public static <T> R<T> success(T data) {
        R<T> r = new R<>();
        r.setCode(200);
        r.setMessage("success");
        r.setData(data);
        return r;
    }

    /**
     * 构造失败响应。
     *
     * @param code 业务错误码
     * @param message 错误信息
     * @param <T> 数据类型
     * @return 失败响应
     */
    public static <T> R<T> fail(int code, String message) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    /**
     * 构造失败响应（默认错误码 500）。
     *
     * @param message 错误信息
     * @param <T> 数据类型
     * @return 失败响应
     */
    public static <T> R<T> fail(String message) {
        return fail(500, message);
    }

    /**
     * 返回响应码。
     *
     * @return 响应码
     */
    public int getCode() {
        return code;
    }

    /**
     * 设置响应码。
     *
     * @param code 响应码
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 返回响应消息。
     *
     * @return 响应消息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置响应消息。
     *
     * @param message 响应消息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 返回响应数据。
     *
     * @return 响应数据
     */
    public T getData() {
        return data;
    }

    /**
     * 设置响应数据。
     *
     * @param data 响应数据
     */
    public void setData(T data) {
        this.data = data;
    }
}
