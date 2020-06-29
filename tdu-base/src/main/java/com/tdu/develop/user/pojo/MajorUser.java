package com.tdu.develop.user.pojo;

public class MajorUser {
	private String Id;
	private String MajorKey;
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
	public String getMajorKey() {
		return MajorKey;
	}
	public void setMajorKey(String majorKey) {
		MajorKey = majorKey;
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
