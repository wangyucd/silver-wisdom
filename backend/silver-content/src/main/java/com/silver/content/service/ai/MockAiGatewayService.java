package com.silver.content.service.ai;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * AI 网关服务模拟实现。
 * 用于本地开发环境，提供预设的模拟响应。
 * 仅在 dev 环境激活。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@Profile("dev")
@Service
public class MockAiGatewayService implements AiGatewayService {

    /**
     * 执行模拟 AI 任务。
     * 返回基于请求上下文的预设模拟结果。
     *
     * @param context AI 任务上下文
     * @return 模拟 AI 执行结果
     */
    @Override
    public AiGatewayResult execute(AiGatewayContext context) {
        AiGatewayResult result = new AiGatewayResult();
        result.setAnswer("基于平台内容与 " + context.getProvider() + " 模型，建议先从基础知识入门，再结合实际场景分步学习。问题重点是：" + context.getPrompt());
        result.setTitle(context.getScene() + "学习建议");
        result.setSummary("围绕“" + context.getPrompt() + "”整理的简明学习内容。");
        result.setOutline("一、基础认知；二、实操建议；三、持续学习");

        List<String> paragraphs = new ArrayList<>();
        paragraphs.add("第一部分：先理解概念，避免一开始就陷入复杂细节。");
        paragraphs.add("第二部分：结合平台精选内容逐步学习，并记录自己的问题。");
        if (context.getKnowledgeSnippets() != null && !context.getKnowledgeSnippets().isEmpty()) {
            paragraphs.add("平台参考内容：" + String.join("；", context.getKnowledgeSnippets()));
        }
        result.setParagraphs(paragraphs);
        return result;
    }
}
