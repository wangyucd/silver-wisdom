package com.silver.content.model.response;

import java.util.List;

/**
 * AI 问答响应。
 */
public class AiChatResponse {

    /** 回答内容。 */
    private String answer;
    /** 引用列表。 */
    private List<ReferenceResponse> references;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<ReferenceResponse> getReferences() {
        return references;
    }

    public void setReferences(List<ReferenceResponse> references) {
        this.references = references;
    }
}
