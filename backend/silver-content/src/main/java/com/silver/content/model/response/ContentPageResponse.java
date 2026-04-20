package com.silver.content.model.response;

import com.silver.content.model.ContentItemEntity;
import java.util.List;

/**
 * 内容分页响应。
 */
public class ContentPageResponse {

    /** 总记录数。 */
    private long total;
    /** 当前页数据。 */
    private List<ContentItemEntity> list;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<ContentItemEntity> getList() {
        return list;
    }

    public void setList(List<ContentItemEntity> list) {
        this.list = list;
    }
}
