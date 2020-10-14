package com.tdu.develop.resource.pojo;


public class Question {

    private String id;
    private String content;
    private String knowledgeId;
    private String time;
    private String title;
    private String type;
    private String questionImg;
    private int fenshu;
    /*试卷Id*/
    private String pagerId;
    /*知识点对象*/
    private Knowledges knowledges;

    private String jiexi;

    public String getJiexi() {
        return jiexi;
    }

    public void setJiexi(String jiexi) {
        this.jiexi = jiexi;
    }

    public String getPagerId() {
        return pagerId;
    }

    public void setPagerId(String pagerId) {
        this.pagerId = pagerId;
    }

    public Knowledges getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(Knowledges knowledges) {
        this.knowledges = knowledges;
    }

    public int getFenshu() {
        return fenshu;
    }

    public void setFenshu(int fenshu) {
        this.fenshu = fenshu;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuestionImg() {
        return questionImg;
    }

    public void setQuestionImg(String questionImg) {
        this.questionImg = questionImg;
    }

}
