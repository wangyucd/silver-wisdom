package com.silver.content.model.request;

/**
 * AI 生成请求。
 */
public class AiGenerateRequest {

    /** 生成提示词。 */
    private String prompt;
    /** 生成场景。 */
    private String scene;

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }
}
