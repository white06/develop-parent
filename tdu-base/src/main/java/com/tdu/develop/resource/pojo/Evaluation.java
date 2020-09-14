package com.tdu.develop.resource.pojo;

public class Evaluation {

    private String  id;
    private String  clarity;  //语言清晰度
    private String  fullness;  //丰满度
    private String  articulation;    //音节清晰度
    private String  kindness;    //亲切感
    private String  creatData;    //创建时间
    private String  userKey;
    private String  sceneKey;

    /*
    查看评分
    * */
    private String username;
    private String  content;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClarity() {
        return clarity;
    }

    public void setClarity(String clarity) {
        this.clarity = clarity;
    }

    public String getFullness() {
        return fullness;
    }

    public void setFullness(String fullness) {
        this.fullness = fullness;
    }

    public String getArticulation() {
        return articulation;
    }

    public void setArticulation(String articulation) {
        this.articulation = articulation;
    }

    public String getKindness() {
        return kindness;
    }

    public void setKindness(String kindness) {
        this.kindness = kindness;
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

    public String getSceneKey() {
        return sceneKey;
    }

    public void setSceneKey(String sceneKey) {
        this.sceneKey = sceneKey;
    }
}
