package com.silver.content.service.ai;

import java.util.List;

/**
 * AI 网关执行结果。
 * 封装 AI 任务返回的结构化内容，包括回答、标题、摘要、大纲和段落。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
public class AiGatewayResult {

    /**
     * 回答内容
     */
    private String answer;
    /**
     * 生成内容标题
     */
    private String title;
    /**
     * 生成内容摘要
     */
    private String summary;
    /**
     * 内容大纲
     */
    private String outline;
    /**
     * 内容段落列表
     */
    private List<String> paragraphs;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public List<String> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<String> paragraphs) {
        this.paragraphs = paragraphs;
    }
}
