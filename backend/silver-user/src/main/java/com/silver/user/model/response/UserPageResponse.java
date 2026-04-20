package com.silver.user.model.response;

import java.util.List;

/**
 * 用户分页响应。
 */
public class UserPageResponse {

    /** 总记录数。 */
    private long total;
    /** 当前页数据。 */
    private List<UserListItemResponse> list;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<UserListItemResponse> getList() {
        return list;
    }

    public void setList(List<UserListItemResponse> list) {
        this.list = list;
    }
}
