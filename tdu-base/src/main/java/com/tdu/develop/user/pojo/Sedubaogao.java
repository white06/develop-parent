package com.tdu.develop.user.pojo;

import java.util.Date;

public class Sedubaogao {
    private String id;
    private Date riqi;
    private String wenti;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getRiqi() {
        return riqi;
    }

    public void setRiqi(Date riqi) {
        this.riqi = riqi;
    }

    public String getWenti() {
        return wenti;
    }

    public void setWenti(String wenti) {
        this.wenti = wenti;
    }
}
