package com.silver.user.service.impl;

import com.silver.common.exception.BusinessException;
import com.silver.user.config.WxMiniAuthProperties;
import com.silver.user.errorcode.UserErrorCodes;
import com.silver.user.service.WxOpenIdResolver;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 微信 openId 模拟解析器。
 * 在 mock 模式下基于 code 生成模拟 openId，用于本地开发测试。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@Component
public class MockWxOpenIdResolver implements WxOpenIdResolver {

    /**
     * 微信认证配置
     */
    private final WxMiniAuthProperties properties;

    /**
     * 构造模拟解析器。
     *
     * @param properties 微信认证配置
     */
    public MockWxOpenIdResolver(WxMiniAuthProperties properties) {
        this.properties = properties;
    }

    /**
     * 解析微信 openId。
     * mock 模式下生成基于 code 的哈希值作为模拟 openId。
     *
     * @param code 微信登录 code
     * @return openId
     */
    @Override
    public String resolveOpenId(String code) {
        if (!StringUtils.hasText(code)) {
            throw new BusinessException(UserErrorCodes.WX_LOGIN_CODE_REQUIRED);
        }
        if (properties.isMockEnabled()) {
            return "mock_" + sha256(code).substring(0, 24);
        }
        throw new BusinessException(UserErrorCodes.WX_LOGIN_NOT_ENABLED);
    }

    /**
     * 计算字符串 SHA-256 摘要。
     *
     * @param input 输入字符串
     * @return 十六进制摘要
     */
    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder(bytes.length * 2);
            for (byte current : bytes) {
                builder.append(String.format("%02x", current));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException(UserErrorCodes.WX_OPEN_ID_GENERATE_FAILED);
        }
    }
}
