package com.silver.content.service.ai.dashscope;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

/**
 * DashScope AI 服务。
 * 基于 Spring AI Alibaba 的 ChatClient 封装，提供统一的 AI 调用能力。
 * 支持对话记忆、流式输出、结构化输出等高级能力。
 *
 * @author wangyu03
 * @since 2026/04/30 10:00
 */
@Slf4j
@Service
public class DashScopeAiService {

    /**
     * 默认对话记忆检索大小。
     */
    private static final int DEFAULT_MEMORY_RETRIEVE_SIZE = 10;

    private final ChatClient chatClient;

    public DashScopeAiService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * 同步调用 AI 生成内容（无对话记忆）。
     *
     * @param prompt      提示词
     * @param model       模型名称，为 null 时使用默认模型
     * @param temperature 温度参数
     * @return 生成的文本内容
     */
    public String chat(String prompt, String model, double temperature) {
        log.debug("调用 DashScope AI，模型: {}, 温度: {}", model, temperature);

        try {
            ChatClient.ChatClientRequestSpec requestSpec = chatClient.prompt(prompt);

            // 动态设置 Options
            if (model != null || temperature > 0) {
                DashScopeChatOptions options = DashScopeChatOptions.builder()
                        .withModel(model)
                        .withTemperature(temperature)
                        .build();
                requestSpec = requestSpec.options(options);
            }

            String result = requestSpec.call().content();
            log.debug("AI 调用完成，结果长度: {}", result != null ? result.length() : 0);
            return result;
        } catch (Exception e) {
            log.error("DashScope AI 调用失败", e);
            throw new RuntimeException("AI 服务暂时不可用，请稍后重试", e);
        }
    }

    /**
     * 使用默认模型和温度调用 AI。
     *
     * @param prompt 提示词
     * @return 生成的文本内容
     */
    public String chat(String prompt) {
        return chat(prompt, null, 0.7);
    }

    /**
     * 使用指定温度调用 AI。
     *
     * @param prompt      提示词
     * @param temperature 温度参数
     * @return 生成的文本内容
     */
    public String chat(String prompt, double temperature) {
        return chat(prompt, null, temperature);
    }

    /**
     * 带对话记忆的 AI 调用。
     * 适用于多轮对话场景，自动维护上下文。
     *
     * @param prompt       提示词
     * @param conversationId 对话 ID，用于隔离不同对话的记忆
     * @param model        模型名称，为 null 时使用默认模型
     * @param temperature  温度参数
     * @return 生成的文本内容
     */
    public String chatWithMemory(String prompt, String conversationId, String model, double temperature) {
        log.debug("调用 DashScope AI（带记忆），对话ID: {}, 模型: {}, 温度: {}", conversationId, model, temperature);

        try {
            ChatClient.ChatClientRequestSpec requestSpec = chatClient.prompt(prompt)
                    .advisors(spec -> spec
                            .param(CHAT_MEMORY_CONVERSATION_ID_KEY, conversationId)
                            .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, DEFAULT_MEMORY_RETRIEVE_SIZE));

            // 动态设置 Options
            if (model != null || temperature > 0) {
                DashScopeChatOptions options = DashScopeChatOptions.builder()
                        .withModel(model)
                        .withTemperature(temperature)
                        .build();
                requestSpec = requestSpec.options(options);
            }

            String result = requestSpec.call().content();
            log.debug("AI 调用完成（带记忆），结果长度: {}", result != null ? result.length() : 0);
            return result;
        } catch (Exception e) {
            log.error("DashScope AI 调用失败（带记忆）", e);
            throw new RuntimeException("AI 服务暂时不可用，请稍后重试", e);
        }
    }

    /**
     * 带对话记忆的 AI 调用（使用默认模型和温度）。
     *
     * @param prompt       提示词
     * @param conversationId 对话 ID
     * @return 生成的文本内容
     */
    public String chatWithMemory(String prompt, String conversationId) {
        return chatWithMemory(prompt, conversationId, null, 0.7);
    }

    /**
     * 流式调用 AI 生成内容。
     * 适用于需要实时输出的场景，如打字机效果。
     *
     * @param prompt      提示词
     * @param model       模型名称，为 null 时使用默认模型
     * @param temperature 温度参数
     * @return 流式文本内容
     */
    public Flux<String> chatStream(String prompt, String model, double temperature) {
        log.debug("流式调用 DashScope AI，模型: {}, 温度: {}", model, temperature);

        try {
            ChatClient.ChatClientRequestSpec requestSpec = chatClient.prompt(prompt);

            // 动态设置 Options
            if (model != null || temperature > 0) {
                DashScopeChatOptions options = DashScopeChatOptions.builder()
                        .withModel(model)
                        .withTemperature(temperature)
                        .build();
                requestSpec = requestSpec.options(options);
            }

            return requestSpec.stream().chatResponse()
                    .map(response -> response.getResult().getOutput().getText())
                    .doOnError(e -> log.error("流式 AI 调用失败", e));
        } catch (Exception e) {
            log.error("DashScope AI 流式调用失败", e);
            throw new RuntimeException("AI 服务暂时不可用，请稍后重试", e);
        }
    }

    /**
     * 获取完整的 ChatResponse。
     * 适用于需要访问元数据（如 token 使用量）的场景。
     *
     * @param prompt      提示词
     * @param model       模型名称
     * @param temperature 温度参数
     * @return ChatResponse 对象
     */
    public ChatResponse chatWithResponse(String prompt, String model, double temperature) {
        log.debug("调用 DashScope AI 获取完整响应，模型: {}, 温度: {}", model, temperature);

        try {
            ChatClient.ChatClientRequestSpec requestSpec = chatClient.prompt(prompt);

            if (model != null || temperature > 0) {
                DashScopeChatOptions options = DashScopeChatOptions.builder()
                        .withModel(model)
                        .withTemperature(temperature)
                        .build();
                requestSpec = requestSpec.options(options);
            }

            return requestSpec.call().chatResponse();
        } catch (Exception e) {
            log.error("DashScope AI 调用失败", e);
            throw new RuntimeException("AI 服务暂时不可用，请稍后重试", e);
        }
    }
}
