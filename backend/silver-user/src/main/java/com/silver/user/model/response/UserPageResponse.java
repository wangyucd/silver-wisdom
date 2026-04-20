package com.silver.user.model.response;

import java.util.List;

public class UserPageResponse {

    private long total;
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
