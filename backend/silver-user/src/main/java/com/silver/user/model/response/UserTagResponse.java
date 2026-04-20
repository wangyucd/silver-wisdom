package com.silver.user.model.response;

import com.silver.user.model.UserInterestTagEntity;
import java.util.List;

/**
 * 用户标签响应。
 */
public class UserTagResponse {

    /** 标签列表。 */
    private List<UserInterestTagEntity> tags;

    public List<UserInterestTagEntity> getTags() {
        return tags;
    }

    public void setTags(List<UserInterestTagEntity> tags) {
        this.tags = tags;
    }
}
