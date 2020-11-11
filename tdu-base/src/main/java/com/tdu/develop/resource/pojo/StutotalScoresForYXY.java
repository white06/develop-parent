package com.tdu.develop.resource.pojo;


public class StutotalScoresForYXY {

    private String id;
    private String pagerKey;
    private int quesScore;
    private int checked;
    private String stuKey;
    private int allowExam;
    private String KnowledgeId;
    private String CustomName;

    private String endDate;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCustomName() {
        return CustomName;
    }

    public void setCustomName(String customName) {
        CustomName = customName;
    }

    public String getKnowledgeId() {
        return KnowledgeId;
    }

    public void setKnowledgeId(String knowledgeId) {
        KnowledgeId = knowledgeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPagerKey() {
        return pagerKey;
    }

    public void setPagerKey(String pagerKey) {
        this.pagerKey = pagerKey;
    }

    public int getQuesScore() {
        return quesScore;
    }

    public void setQuesScore(int quesScore) {
        this.quesScore = quesScore;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public String getStuKey() {
        return stuKey;
    }

    public void setStuKey(String stuKey) {
        this.stuKey = stuKey;
    }

    public int getAllowExam() {
        return allowExam;
    }

    public void setAllowExam(int allowExam) {
        this.allowExam = allowExam;
    }
}
