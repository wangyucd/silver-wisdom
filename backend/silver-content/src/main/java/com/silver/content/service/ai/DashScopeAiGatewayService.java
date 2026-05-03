package com.silver.content.service.ai;

import com.silver.content.service.ai.dashscope.DashScopeAiService;
import com.silver.content.service.ai.prompt.AiPromptTemplates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * DashScope AI 网关服务实现。
 * 基于 Spring AI Alibaba 的真实 AI 实现，替换 Mock 实现。
 *
 * @author wangyu03
 * @since 2026/04/30 10:00
 */
@Slf4j
@Primary
@Service
public class DashScopeAiGatewayService implements AiGatewayService {

    private final DashScopeAiService dashScopeAiService;

    public DashScopeAiGatewayService(DashScopeAiService dashScopeAiService) {
        this.dashScopeAiService = dashScopeAiService;
    }

    /**
     * 执行 AI 任务。
     * 根据场景（chat/general）调用不同的 Prompt 模板。
     *
     * @param context AI 任务上下文
     * @return AI 执行结果
     */
    @Override
    public AiGatewayResult execute(AiGatewayContext context) {
        String scene = context.getScene();
        log.info("执行 AI 任务，场景: {}, 提示词长度: {}", scene, context.getPrompt().length());

        if ("chat".equals(scene)) {
            return executeChat(context);
        } else {
            return executeGenerate(context);
        }
    }

    /**
     * 执行对话场景。
     * 基于知识片段进行问答，支持多轮对话记忆。
     *
     * @param context AI 任务上下文
     * @return AI 执行结果
     */
    private AiGatewayResult executeChat(AiGatewayContext context) {
        String knowledgeText = buildKnowledgeText(context.getKnowledgeSnippets());
        String promptText = String.format(AiPromptTemplates.CHAT_TEMPLATE, knowledgeText, context.getPrompt());

        try {
            // 使用对话记忆（如果有 conversationId）
            String answer;
            if (context.getConversationId() != null && !context.getConversationId().isEmpty()) {
                answer = dashScopeAiService.chatWithMemory(promptText, context.getConversationId());
            } else {
                answer = dashScopeAiService.chat(promptText, null, 0.7);
            }

            AiGatewayResult result = new AiGatewayResult();
            result.setAnswer(answer);
            result.setTitle("智能问答");
            result.setSummary(context.getPrompt());
            result.setOutline("基于平台内容的智能问答");
            result.setParagraphs(List.of(answer));

            log.info("对话任务完成，回答长度: {}", answer.length());
            return result;
        } catch (Exception e) {
            log.error("对话任务失败", e);
            throw new RuntimeException("AI 问答服务暂时不可用", e);
        }
    }

    /**
     * 执行内容生成场景。
     * 根据 Prompt 生成结构化学习内容。
     *
     * @param context AI 任务上下文
     * @return AI 执行结果
     */
    private AiGatewayResult executeGenerate(AiGatewayContext context) {
        String knowledgeText = buildKnowledgeText(context.getKnowledgeSnippets());
        String promptText = String.format(AiPromptTemplates.GENERATE_TEMPLATE, context.getPrompt(), knowledgeText);

        try {
            String response = dashScopeAiService.chat(promptText, null, 0.7);

            // 解析结构化响应
            AiGatewayResult result = parseStructuredResponse(response, context.getPrompt());

            log.info("内容生成任务完成，标题: {}", result.getTitle());
            return result;
        } catch (Exception e) {
            log.error("内容生成任务失败", e);
            throw new RuntimeException("AI 内容生成服务暂时不可用", e);
        }
    }

    /**
     * 构建知识片段文本。
     *
     * @param knowledgeSnippets 知识片段列表
     * @return 格式化的知识文本
     */
    private String buildKnowledgeText(List<String> knowledgeSnippets) {
        if (knowledgeSnippets == null || knowledgeSnippets.isEmpty()) {
            return "暂无参考内容";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < knowledgeSnippets.size(); i++) {
            sb.append(i + 1).append(". ").append(knowledgeSnippets.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * 解析结构化响应。
     * 从 AI 返回的文本中提取标题、摘要、大纲和正文。
     *
     * @param response AI 返回的文本
     * @param prompt   原始提示词
     * @return 解析后的结果
     */
    private AiGatewayResult parseStructuredResponse(String response, String prompt) {
        AiGatewayResult result = new AiGatewayResult();

        // 默认值
        result.setTitle("学习内容");
        result.setSummary("围绕" + prompt + "整理的学习内容。");
        result.setOutline("一、基础认知；二、实操建议；三、持续学习");

        // 尝试解析结构化内容
        String[] lines = response.split("\n");
        StringBuilder currentSection = new StringBuilder();
        String currentSectionName = null;

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("【标题】")) {
                result.setTitle(line.substring(4).trim());
            } else if (line.startsWith("【摘要】")) {
                result.setSummary(line.substring(4).trim());
            } else if (line.startsWith("【大纲】")) {
                result.setOutline(line.substring(4).trim());
            } else if (line.startsWith("【正文】")) {
                currentSectionName = "正文";
                currentSection = new StringBuilder();
            } else if (currentSectionName != null) {
                currentSection.append(line).append("\n");
            }
        }

        // 设置正文
        String body = currentSection.toString().trim();
        if (!body.isEmpty()) {
            result.setAnswer(body);
            result.setParagraphs(List.of(body));
        } else {
            // 如果没有解析到正文，使用整个响应
            result.setAnswer(response);
            result.setParagraphs(List.of(response));
        }

        return result;
    }
}
