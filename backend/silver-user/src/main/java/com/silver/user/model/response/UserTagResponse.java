package com.silver.user.model.response;

import com.silver.user.model.UserInterestTagEntity;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户标签响应。
 */
@Getter
@Setter
public class UserTagResponse {

    /** 标签列表。 */
    private List<UserInterestTagEntity> tags;

}
