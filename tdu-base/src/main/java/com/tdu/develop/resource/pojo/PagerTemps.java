package com.tdu.develop.resource.pojo;

public class PagerTemps {
    private String Id;
    private String PagerName;
    private String Status;
    private String CreateDate;
    private String CreatePerson;
    private int CiteTime;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPagerName() {
        return PagerName;
    }

    public void setPagerName(String pagerName) {
        PagerName = pagerName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getCreatePerson() {
        return CreatePerson;
    }

    public void setCreatePerson(String createPerson) {
        CreatePerson = createPerson;
    }

    public int getCiteTime() {
        return CiteTime;
    }

    public void setCiteTime(int citeTime) {
        CiteTime = citeTime;
    }
}
