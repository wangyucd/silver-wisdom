package com.silver.user.model.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 新用户兴趣问卷请求。
 */
@Getter
@Setter
public class OnboardingRequest {

    /** 用户选择的标签列表。 */
    private List<String> tags;
    /** 标签来源。 */
    private String source;

}
