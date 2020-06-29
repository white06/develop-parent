package com.tdu.develop.user.pojo;

import java.sql.Date;

public class Member {
	private String id;
	private String name;
	private String sex;
	private String className;
	private Date birthDate;
	private String familyAddress;
	private String mobilePhoneNum;
	private String familyPhoneNum;
	private String email;
	private String qqNum;
	private String msn;
	private String OfficePhoneNum;
	private String Constellation;
	private Date createDate;
	private String userName;
	private String password;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getFamilyAddress() {
		return familyAddress;
	}
	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}
	public String getMobilePhoneNum() {
		return mobilePhoneNum;
	}
	public void setMobilePhoneNum(String mobilePhoneNum) {
		this.mobilePhoneNum = mobilePhoneNum;
	}
	public String getFamilyPhoneNum() {
		return familyPhoneNum;
	}
	public void setFamilyPhoneNum(String familyPhoneNum) {
		this.familyPhoneNum = familyPhoneNum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQqNum() {
		return qqNum;
	}
	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String getOfficePhoneNum() {
		return OfficePhoneNum;
	}
	public void setOfficePhoneNum(String officePhoneNum) {
		OfficePhoneNum = officePhoneNum;
	}
	public String getConstellation() {
		return Constellation;
	}
	public void setConstellation(String constellation) {
		Constellation = constellation;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}