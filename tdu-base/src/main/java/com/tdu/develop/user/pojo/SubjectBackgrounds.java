package com.tdu.develop.user.pojo;


public class SubjectBackgrounds {
	//主键id
	private int id;
	//科目id
	private String subjectkey;
	//科目名称
	private String subjectName;
	//科目背景图片路径
	private String backgrouppic;
	//科目logo
	private String subjectlogo;
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	private String skin;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubjectkey() {
		return subjectkey;
	}
	public void setSubjectkey(String subjectkey) {
		this.subjectkey = subjectkey;
	}
	public String getBackgrouppic() {
		return backgrouppic;
	}
	public void setBackgrouppic(String backgrounppic) {
		this.backgrouppic = backgrounppic;
	}
	public String getSubjectlogo() {
		return subjectlogo;
	}
	public void setSubjectlogo(String subjectlogo) {
		this.subjectlogo = subjectlogo;
	}
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	
}
