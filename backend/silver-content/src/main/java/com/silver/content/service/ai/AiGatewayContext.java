package com.silver.content.service.ai;

import java.util.List;

/**
 * AI 网关任务上下文。
 * 封装 AI 任务所需的场景、提示词、知识片段等信息。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
public class AiGatewayContext {

    /**
     * 任务场景（chat/general）
     */
    private String scene;
    /**
     * 用户提示词/问题
     */
    private String prompt;
    /**
     * AI 服务提供商
     */
    private String provider;
    /**
     * 知识片段列表
     */
    private List<String> knowledgeSnippets;
    /**
     * 对话 ID，用于多轮对话记忆隔离
     */
    private String conversationId;

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<String> getKnowledgeSnippets() {
        return knowledgeSnippets;
    }

    public void setKnowledgeSnippets(List<String> knowledgeSnippets) {
        this.knowledgeSnippets = knowledgeSnippets;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
