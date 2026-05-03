package com.silver.content.service.ai.config;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AI 配置类。
 * 配置 Spring AI Alibaba 的 ChatClient 实例，支持对话记忆。
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
     * 使用 DashScope 模型，支持对话记忆和日志记录。
     *
     * @param chatModel  Spring AI 自动装配的 ChatModel
     * @param chatMemory 对话记忆实例
     * @return ChatClient 实例
     */
    @Bean
    public ChatClient chatClient(ChatModel chatModel, ChatMemory chatMemory) {
        DashScopeChatOptions defaultOptions = DashScopeChatOptions.builder()
                .withTopP(0.9)
                .build();

        return ChatClient.builder(chatModel)
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
                .defaultOptions(defaultOptions)
                .build();
    }
}
