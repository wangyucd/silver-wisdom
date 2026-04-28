package com.silver.content.service.ai;

/**
 * AI 网关服务接口。
 * 负责与外部 AI 大模型交互，执行问答和内容生成任务。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
public interface AiGatewayService {

    /**
     * 执行 AI 任务。
     * 根据上下文执行问答或内容生成，返回结构化结果。
     *
     * @param context AI 任务上下文
     * @return AI 执行结果
     */
    AiGatewayResult execute(AiGatewayContext context);
}
