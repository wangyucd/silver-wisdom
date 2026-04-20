package com.silver.user.model;

import java.time.LocalDateTime;

public class UserLearningSummary {

    private int generatedCount;
    private int contentViewCount;
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
