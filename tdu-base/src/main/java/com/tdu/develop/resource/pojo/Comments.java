package com.tdu.develop.resource.pojo;

public class Comments {

    private String  id;
    private String  integrity;  //完整性
    private String  standard;  //标准性
    private String  grammar;    //语法
    private String  fluency;    //流利度
    private String  intonation;    //语调
    private String  reaction;  //反应速度
    private String  overall;    //整体评分
    private String  type;    // 0-->自评     1-->他评
    private String  stuKey;    //学生ID

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntegrity() {
        return integrity;
    }

    public void setIntegrity(String integrity) {
        this.integrity = integrity;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getGrammar() {
        return grammar;
    }

    public void setGrammar(String grammar) {
        this.grammar = grammar;
    }

    public String getFluency() {
        return fluency;
    }

    public void setFluency(String fluency) {
        this.fluency = fluency;
    }

    public String getIntonation() {
        return intonation;
    }

    public void setIntonation(String intonation) {
        this.intonation = intonation;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public String getOverall() {
        return overall;
    }

    public void setOverall(String overall) {
        this.overall = overall;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStuKey() {
        return stuKey;
    }

    public void setStuKey(String stuKey) {
        this.stuKey = stuKey;
    }
}
