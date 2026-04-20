package com.silver.user.model;

public class UserInterestTag {

    private String tag;
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
