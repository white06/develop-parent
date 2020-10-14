package com.tdu.develop.user.pojo;

public class TrialUsers {
    private String id;
    private String userId;
    private String name;
    private String userName;
    private boolean enableTrial;
    private String beginTrialDate;
    private String endTrialDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean getEnableTrial() {
        return enableTrial;
    }

    public void setEnableTrial(boolean enableTrial) {
        this.enableTrial = enableTrial;
    }

    public String getBeginTrialDate() {
        return beginTrialDate;
    }

    public void setBeginTrialDate(String beginTrialDate) {
        this.beginTrialDate = beginTrialDate;
    }

    public String getEndTrialDate() {
        return endTrialDate;
    }

    public void setEndTrialDate(String endTrialDate) {
        this.endTrialDate = endTrialDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
