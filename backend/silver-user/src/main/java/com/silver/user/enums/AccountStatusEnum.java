package com.silver.user.enums;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;

/**
 * 账号状态枚举
 *
 * @author wangyu03
 * @since 2026/04/21 00:00
 */
@Getter
public enum AccountStatusEnum {

    /**
     * 启用
     */
    ENABLED("ENABLED", "启用"),
    /**
     * 禁用
     */
    DISABLED("DISABLED", "禁用");

    /**
     * 状态编码
     */
    private final String code;
    /**
     * 状态说明
     */
    private final String description;

    AccountStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 判断编码是否匹配当前枚举
     *
     * @param value 待比较编码
     * @return 是否匹配
     */
    public boolean matches(String value) {
        return normalize(value).map(code::equals).orElse(false);
    }

    /**
     * 按编码解析枚举
     *
     * @param value 状态编码
     * @return 状态枚举
     */
    public static Optional<AccountStatusEnum> fromCode(String value) {
        return normalize(value).flatMap(normalized ->
                Arrays.stream(values()).filter(item -> item.code.equals(normalized)).findFirst()
        );
    }

    /**
     * 规范化状态编码
     *
     * @param value 原始编码
     * @return 规范化后的编码
     */
    private static Optional<String> normalize(String value) {
        if (value == null || value.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(value.trim().toUpperCase());
    }
}
