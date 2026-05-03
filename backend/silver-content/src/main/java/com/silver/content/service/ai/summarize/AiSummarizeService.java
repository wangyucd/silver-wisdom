package com.silver.content.service.ai.summarize;

import com.silver.content.service.ai.dashscope.DashScopeAiService;
import com.silver.content.service.ai.prompt.AiPromptTemplates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * AI 内容总结服务。
 * 基于搜索结果，使用 Spring AI Alibaba 生成高质量内容摘要。
 *
 * @author wangyu03
 * @since 2026/04/29
 */
@Slf4j
@Service
public class AiSummarizeService {

    private final DashScopeAiService dashScopeAiService;
    private final String modelName;

    public AiSummarizeService(
            DashScopeAiService dashScopeAiService,
            @Value("${spring.ai.dashscope.chat.options.model:qwen-plus}") String modelName) {
        this.dashScopeAiService = dashScopeAiService;
        this.modelName = modelName;
    }

    /**
     * 根据搜索结果生成内容。
     *
     * @param keyword       内容关键词
     * @param searchResults 搜索引擎返回的原始结果
     * @return 生成的高质量内容
     */
    public String generateContent(String keyword, String searchResults) {
        log.info("开始 AI 内容生成，关键词: {}", keyword);

        String promptText = String.format(AiPromptTemplates.CONTENT_GENERATE_TEMPLATE, keyword, searchResults);

        try {
            String result = dashScopeAiService.chat(promptText, modelName, 0.7);
            log.info("AI 内容生成完成，长度: {} 字符", result.length());
            return result;
        } catch (Exception e) {
            log.error("AI 内容生成失败: {}", keyword, e);
            throw new RuntimeException("AI 服务暂时不可用，请稍后重试", e);
        }
    }

    /**
     * 生成内容标题。
     *
     * @param keyword       内容关键词
     * @param searchResults 搜索引擎返回的原始结果
     * @return 生成的标题
     */
    public String generateTitle(String keyword, String searchResults) {
        log.info("开始 AI 标题生成，关键词: {}", keyword);

        String promptText = String.format(AiPromptTemplates.TITLE_GENERATE_TEMPLATE, keyword, searchResults);

        try {
            String result = dashScopeAiService.chat(promptText, modelName, 0.5).trim();
            log.info("AI 标题生成完成: {}", result);
            return result;
        } catch (Exception e) {
            log.error("AI 标题生成失败: {}", keyword, e);
            return keyword + " - 精选内容"; // 返回默认标题
        }
    }
}
