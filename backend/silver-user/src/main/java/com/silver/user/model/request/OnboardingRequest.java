package com.silver.user.model.request;

import java.util.List;

/**
 * 新用户兴趣问卷请求。
 */
public class OnboardingRequest {

    /** 用户选择的标签列表。 */
    private List<String> tags;
    /** 标签来源。 */
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
