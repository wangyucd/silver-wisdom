package com.silver.content.model.response;

import com.silver.content.model.ContentItem;
import java.util.List;

/**
 * 内容分页响应。
 */
public class ContentPageResponse {

    /** 总记录数。 */
    private long total;
    /** 当前页数据。 */
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
