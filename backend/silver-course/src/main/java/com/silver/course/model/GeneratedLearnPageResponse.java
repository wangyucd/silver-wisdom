package com.silver.course.model;

import java.util.List;

/**
 * 已生成学习内容分页响应。
 */
public class GeneratedLearnPageResponse {

    /** 总记录数。 */
    private long total;
    /** 当前页数据。 */
    private List<GeneratedLearnItem> list;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<GeneratedLearnItem> getList() {
        return list;
    }

    public void setList(List<GeneratedLearnItem> list) {
        this.list = list;
    }
}
