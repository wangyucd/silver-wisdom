package com.silver.user.model;

import java.time.LocalDateTime;

/**
 * 用户学习概览。
 */
public class UserLearningSummary {

    /** 已生成内容数量。 */
    private int generatedCount;
    /** 内容查看次数。 */
    private int contentViewCount;
    /** 最近学习时间。 */
    private LocalDateTime lastLearnTime;

    public int getGeneratedCount() {
        return generatedCount;
    }

    public void setGeneratedCount(int generatedCount) {
        this.generatedCount = generatedCount;
    }

    public int getContentViewCount() {
        return contentViewCount;
    }

    public void setContentViewCount(int contentViewCount) {
        this.contentViewCount = contentViewCount;
    }

    public LocalDateTime getLastLearnTime() {
        return lastLearnTime;
    }

    public void setLastLearnTime(LocalDateTime lastLearnTime) {
        this.lastLearnTime = lastLearnTime;
    }
}
