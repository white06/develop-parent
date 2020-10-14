package com.tdu.develop.user.pojo;


public class Message {

    private String id;
    private String content;
    private String userKey;
    private String date;
    private String title;
    private int anony = 0;

    public int getAnony() {
        return anony;
    }

    public void setAnony(int anony) {
        this.anony = anony;
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

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
