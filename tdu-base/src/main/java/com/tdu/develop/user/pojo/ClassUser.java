package com.tdu.develop.user.pojo;

public class ClassUser {

    private String Id;
    private String ClassKey;
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

    public void setId(String id) {
        Id = id;
    }

    public String getClassKey() {
        return ClassKey;
    }

    public void setClassKey(String classKey) {
        ClassKey = classKey;
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
        if (checked.equals("0")) {
            checked = "";
        } else {
            checked = "Checked";
        }
		/*if(checked.equals("1")) {
			checked="Checked";
		}else {
			checked="";
		}*/
        this.checked = checked;
    }
}
