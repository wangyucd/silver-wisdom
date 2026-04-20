package com.silver.user.model;

/**
 * 用户兴趣标签。
 */
public class UserInterestTag {

    /** 标签名称。 */
    private String tag;
    /** 标签权重。 */
    private double weight;

    public UserInterestTag() {
    }

    public UserInterestTag(String tag, double weight) {
        this.tag = tag;
        this.weight = weight;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
