package com.tdu.develop.user.pojo;


import java.util.List;

public class TeamInfos {
	//С��id
	private String teamId;
	//С������
	private String teamName;
	//�༶id
	private String classId;
	//�༶��
	private String className;
	private List<String> teamUsers;
	
	public List<String> getTeamUsers() {
		return teamUsers;
	}
	public void setTeamUsers(List<String> teamUsers) {
		this.teamUsers = teamUsers;
	}
	public String getTeamId() {
		return teamId;
	}
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	
	
}
