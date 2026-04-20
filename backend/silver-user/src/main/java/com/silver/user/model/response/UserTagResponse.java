package com.silver.user.model.response;

import com.silver.user.model.UserInterestTag;
import java.util.List;

public class UserTagResponse {

    private List<UserInterestTag> tags;

    public List<UserInterestTag> getTags() {
        return tags;
    }

    public void setTags(List<UserInterestTag> tags) {
        this.tags = tags;
    }
}
