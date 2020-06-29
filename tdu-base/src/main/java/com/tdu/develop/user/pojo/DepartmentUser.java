package com.tdu.develop.user.pojo;

public class DepartmentUser {
    private String Id;
    private String UserKey;
    private String DepartmentKey;
    private String UserName;
    private String Name;
    private String Sex;
    private String createdateyear;
    private String createdatemonth;
    private String createdateday;
    private String checked;

    public String getId() {
        return Id;
    }

    public String getUserKey() {
        return UserKey;
    }

    public String getDepartmentKey() {
        return DepartmentKey;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public void setDepartmentKey(String departmentKey) {
        DepartmentKey = departmentKey;
    }

    public String getUserName() {
        return UserName;
    }
    public void setUserName(String userName) {
        UserName = userName;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getSex() {
        return Sex;
    }
    public void setSex(String sex) {
        Sex = sex;
    }
    public String getCreatedateyear() {
        return createdateyear;
    }
    public void setCreatedateyear(String createdateyear) {
        this.createdateyear = createdateyear;
    }
    public String getCreatedatemonth() {
        return createdatemonth;
    }
    public void setCreatedatemonth(String createdatemonth) {
        this.createdatemonth = createdatemonth;
    }
    public String getCreatedateday() {
        return createdateday;
    }
    public void setCreatedateday(String createdateday) {
        this.createdateday = createdateday;
    }
    public String getChecked() {
        return checked;
    }
    public void setChecked(String checked) {
        this.checked = checked;
    }
}
