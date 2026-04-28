package com.silver.content.errorcode;

import com.silver.common.errorcode.ErrorCode;

/**
 * 内容域错误码定义。
 * 统一维护内容模块相关的业务异常码与默认错误信息。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
public enum ContentErrorCodes implements ErrorCode {

    /**
     * 热点标题不能为空
     */
    HOTSPOT_TITLE_REQUIRED(120001, "热点标题不能为空"),
    /**
     * 热点不存在
     */
    HOTSPOT_NOT_FOUND(120002, "热点不存在"),
    /**
     * 分类名称不能为空
     */
    CATEGORY_NAME_REQUIRED(120003, "分类名称不能为空"),
    /**
     * 分类不存在
     */
    CATEGORY_NOT_FOUND(120004, "分类不存在"),
    /**
     * 内容标题不能为空
     */
    CONTENT_TITLE_REQUIRED(120005, "内容标题不能为空"),
    /**
     * 内容不存在
     */
    CONTENT_NOT_FOUND(120006, "内容不存在"),
    /**
     * AI 问答问题不能为空
     */
    AI_CHAT_QUESTION_REQUIRED(120007, "问题不能为空"),
    /**
     * 生成提示词不能为空
     */
    AI_GENERATE_PROMPT_REQUIRED(120008, "生成提示词不能为空"),
    /**
     * 生成任务不存在
     */
    AI_GENERATE_TASK_NOT_FOUND(120009, "生成任务不存在");

    /**
     * 错误码。
     */
    private final int code;
    /**
     * 默认错误信息。
     */
    private final String message;

    /**
     * 构造内容域错误码。
     *
     * @param code 错误码
     * @param message 默认错误信息
     */
    ContentErrorCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 返回错误码。
     *
     * @return 错误码
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * 返回默认错误信息。
     *
     * @return 默认错误信息
     */
    @Override
    public String getMessage() {
        return message;
    }
}
