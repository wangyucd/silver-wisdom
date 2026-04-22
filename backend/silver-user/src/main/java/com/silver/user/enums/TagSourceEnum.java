package com.silver.user.enums;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

/**
 * 用户标签来源枚举
 *
 * @author wangyu03
 * @since 2026/04/21 00:00
 */
@Getter
public enum TagSourceEnum {

    /**
     * 问卷采集
     */
    QUESTIONNAIRE("QUESTIONNAIRE", "问卷采集");

    /**
     * 来源编码
     */
    private final String code;
    /**
     * 来源说明
     */
    private final String description;

    TagSourceEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 按编码解析枚举
     *
     * @param value 来源编码
     * @return 来源枚举
     */
    public static Optional<TagSourceEnum> fromCode(String value) {
        if (value == null || value.isBlank()) {
            return Optional.empty();
        }
        String normalized = value.trim().toUpperCase();
        return Arrays.stream(values()).filter(item -> item.code.equals(normalized)).findFirst();
    }
}
