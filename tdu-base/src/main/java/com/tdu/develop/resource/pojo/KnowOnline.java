package com.tdu.develop.resource.pojo;

import java.util.Date;

/**
 * @author 志阳
 * @create 2020-04-03-16:58
 */
public class KnowOnline {
    //主键id
    private String id;
    //用户id
    private String userId;

    //登录时间
    private Date playTime;
    //退出时间
    private Date outTime;
    //在线时间
    private int onlineTime;

    private String knowId;

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

    public Date getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Date playTime) {
        this.playTime = playTime;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    public int getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(int onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getKnowId() {
        return knowId;
    }

    public void setKnowId(String knowId) {
        this.knowId = knowId;
    }
}
