package com.silver.user.model.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户分页响应。
 */
@Getter
@Setter
public class UserPageResponse {

    /** 总记录数。 */
    private long total;
    /** 当前页数据。 */
    private List<UserListItemResponse> list;

}
