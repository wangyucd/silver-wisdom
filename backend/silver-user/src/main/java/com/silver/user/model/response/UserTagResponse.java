package com.silver.user.model.response;

import com.silver.user.model.UserInterestTag;
import java.util.List;

/**
 * 用户标签响应。
 */
public class UserTagResponse {

    /** 标签列表。 */
    private List<UserInterestTag> tags;

    public List<UserInterestTag> getTags() {
        return tags;
    }

    public void setTags(List<UserInterestTag> tags) {
        this.tags = tags;
    }
}
