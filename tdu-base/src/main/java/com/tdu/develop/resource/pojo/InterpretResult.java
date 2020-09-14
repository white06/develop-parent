package com.tdu.develop.resource.pojo;

public class InterpretResult {
    private String Id;
    private String fluency;
    private String fidelity;
    private String coherence;
    private String accuracy;
    private String creatData;
    private String userKey;
    private String fileName;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFluency() {
        return fluency;
    }

    public void setFluency(String fluency) {
        this.fluency = fluency;
    }

    public String getFidelity() {
        return fidelity;
    }

    public void setFidelity(String fidelity) {
        this.fidelity = fidelity;
    }

    public String getCoherence() {
        return coherence;
    }

    public void setCoherence(String coherence) {
        this.coherence = coherence;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getCreatData() {
        return creatData;
    }

    public void setCreatData(String creatData) {
        this.creatData = creatData;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
