package com.silver.user.model.response;

import com.silver.user.model.UserInterestTagEntity;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户标签响应。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@Getter
@Setter
public class UserTagResponse {

    /**
     * 用户标签列表
     */
    private List<UserInterestTagEntity> tags;

}
