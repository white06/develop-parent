package com.tdu.develop.user.pojo;




public class Informessages {
	

	private String id;
	private String title;
	private String content;
	private String createDate;

	private  int type;
	private String departmentId;

	private String depertmentName;

	public String getDepertmentName() {
		return depertmentName;
	}

	public void setDepertmentName(String depertmentName) {
		this.depertmentName = depertmentName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
