package com.tdu.develop.resource.pojo;


public class StuQueInfors {

    private String id;
    private String pagerKey;
    private double quesScore;
    private String questionAnswer;
    private String questionKey;
    private String stuKey;
    private String quesType;


    public String getQuesType() {
        return quesType;
    }

    public void setQuesType(String quesType) {
        this.quesType = quesType;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey;
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

    public double getQuesScore() {
        return quesScore;
    }

    public void setQuesScore(double quesScore) {
        this.quesScore = quesScore;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    public String getStuKey() {
        return stuKey;
    }

    public void setStuKey(String stuKey) {
        this.stuKey = stuKey;
    }

    @Override
    public String toString() {
        return "StuQueInfors [id=" + id + ", pagerKey=" + pagerKey + ", quesScore=" + quesScore + ", questionAnswer="
                + questionAnswer + ", questionKey=" + questionKey + ", stuKey=" + stuKey + "]";
    }

}
