package com.silver.content.service.ai.dashscope;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * DashScope API HTTP 客户端服务。
 * 直接通过 HTTP 调用阿里云 DashScope 大模型 API（OpenAI 兼容接口）。
 *
 * @author wangyu03
 * @since 2026/04/29
 */
@Slf4j
@Service
public class DashScopeApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.ai.dashscope.base-url:https://dashscope.aliyuncs.com/compatible-mode/v1}")
    private String baseUrl;

    @Value("${spring.ai.dashscope.api-key:}")
    private String apiKey;

    @Value("${spring.ai.dashscope.model:qwen-plus}")
    private String defaultModel;

    public DashScopeApiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 调用 DashScope API 生成内容。
     *
     * @param prompt   提示词
     * @param model    模型名称
     * @param temperature 温度参数
     * @return 生成的文本内容
     */
    public String chat(String prompt, String model, double temperature) {
        String url = baseUrl + "/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model != null ? model : defaultModel);

        List<Map<String, String>> messages = new ArrayList<>();
        messages.add(Map.of("role", "user", "content", prompt));
        requestBody.put("messages", messages);

        Map<String, Object> options = new HashMap<>();
        options.put("temperature", temperature);
        requestBody.put("stream", false);
        // 将 options 移到顶层
        requestBody.putAll(options);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            log.debug("调用 DashScope API: {}", url);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode choices = root.path("choices");
                if (choices.isArray() && choices.size() > 0) {
                    return choices.get(0).path("message").path("content").asText();
                }
            }

            log.error("DashScope API 返回异常: {}", response.getBody());
            throw new RuntimeException("AI 服务返回异常");

        } catch (JsonProcessingException e) {
            log.error("解析 DashScope API 响应失败", e);
            throw new RuntimeException("AI 响应解析失败", e);
        }
    }

    /**
     * 使用默认模型生成内容。
     */
    public String chat(String prompt) {
        return chat(prompt, null, 0.7);
    }

    /**
     * 使用指定模型和温度生成内容。
     */
    public String chat(String prompt, double temperature) {
        return chat(prompt, null, temperature);
    }
}
