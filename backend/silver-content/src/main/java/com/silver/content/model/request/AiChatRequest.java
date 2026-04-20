package com.silver.content.model.request;

import java.util.List;

public class AiChatRequest {

    private String question;
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
