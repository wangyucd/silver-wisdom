package com.silver.content.service.ai;

import java.util.List;

public class AiGatewayContext {

    private String scene;
    private String prompt;
    private String provider;
    private List<String> knowledgeSnippets;

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<String> getKnowledgeSnippets() {
        return knowledgeSnippets;
    }

    public void setKnowledgeSnippets(List<String> knowledgeSnippets) {
        this.knowledgeSnippets = knowledgeSnippets;
    }
}
