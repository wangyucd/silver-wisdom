package com.silver.content.model.response;

/**
 * AI 问答引用响应。
 */
public class ReferenceResponse {

    /** 内容ID。 */
    private Long contentId;
    /** 内容标题。 */
    private String title;
    /** 引用片段。 */
    private String snippet;

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }
}
