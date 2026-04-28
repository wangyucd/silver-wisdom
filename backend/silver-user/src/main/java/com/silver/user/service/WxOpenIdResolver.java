package com.silver.user.service;

/**
 * 微信 openId 解析器接口。
 * 负责将微信登录 code 解析为用户唯一标识 openId。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
public interface WxOpenIdResolver {

    /**
     * 根据微信登录 code 解析 openId。
     *
     * @param code 微信登录 code
     * @return 微信 openId
     */
    String resolveOpenId(String code);
}
