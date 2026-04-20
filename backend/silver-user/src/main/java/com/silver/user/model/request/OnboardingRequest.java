package com.silver.user.model.request;

import java.util.List;

public class OnboardingRequest {

    private List<String> tags;
    private String source;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
