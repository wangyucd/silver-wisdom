package com.silver.user.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户学习概览。
 */
@Getter
@Setter
public class UserLearningSummary {

    /** 已生成内容数量。 */
    private int generatedCount;
    /** 内容查看次数。 */
    private int contentViewCount;
    /** 最近学习时间。 */
    private LocalDateTime lastLearnTime;

}
