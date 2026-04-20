package com.silver.content.model.response;

import com.silver.content.model.ContentItem;
import java.util.List;

public class ContentPageResponse {

    private long total;
    private List<ContentItem> list;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<ContentItem> getList() {
        return list;
    }

    public void setList(List<ContentItem> list) {
        this.list = list;
    }
}
