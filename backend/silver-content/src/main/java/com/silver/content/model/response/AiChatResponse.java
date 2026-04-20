package com.silver.content.model.response;

import java.util.List;

public class AiChatResponse {

    private String answer;
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
