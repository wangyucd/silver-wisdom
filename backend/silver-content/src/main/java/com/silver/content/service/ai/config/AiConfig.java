package com.silver.content.service.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * AI 配置类。
 * 配置 Spring AI 的 ChatClient 实例，支持对话记忆。
 * 优先使用 OpenAI 兼容接口（火山引擎方舟平台）。
 *
 * @author wangyu03
 * @since 2026/04/30 10:00
 */
@Configuration
public class AiConfig {

    /**
     * 配置基于内存的对话记忆。
     *
     * @return ChatMemory 实例
     */
    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }

    /**
     * 配置默认 ChatClient 实例。
     * 使用 OpenAI 兼容接口（火山引擎方舟平台），支持对话记忆。
     *
     * @param openAiChatModel OpenAI ChatModel
     * @param chatMemory      对话记忆实例
     * @return ChatClient 实例
     */
    @Bean
    @Primary
    public ChatClient chatClient(ChatModel openAiChatModel, ChatMemory chatMemory) {
        return ChatClient.builder(openAiChatModel)
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
                .build();
    }

    /**
     * 配置 DashScope ChatClient 实例。
     * 保留 DashScope 模型作为备选。
     *
     * @param dashScopeChatModel DashScope ChatModel
     * @param chatMemory         对话记忆实例
     * @return ChatClient 实例
     */
    @Bean
    public ChatClient dashScopeChatClient(
            @Qualifier("dashScopeChatModel") ChatModel dashScopeChatModel,
            ChatMemory chatMemory) {
        return ChatClient.builder(dashScopeChatModel)
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
                .build();
    }
}
