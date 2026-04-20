package com.silver.content.model;

/**
 * AI 生成结果。
 */
public class AiGenerateResult {

    /** 结果标题。 */
    private String title;
    /** 结果摘要。 */
    private String summary;
    /** 结果正文。 */
    private String body;
    /** 结果大纲。 */
    private String outline;

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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }
}
