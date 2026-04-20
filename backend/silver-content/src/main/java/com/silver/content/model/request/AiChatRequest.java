package com.silver.content.model.request;

import java.util.List;

/**
 * AI 问答请求。
 */
public class AiChatRequest {

    /** 用户问题。 */
    private String question;
    /** 上下文内容ID列表。 */
    private List<Long> contextItemIds;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Long> getContextItemIds() {
        return contextItemIds;
    }

    public void setContextItemIds(List<Long> contextItemIds) {
        this.contextItemIds = contextItemIds;
    }
}
