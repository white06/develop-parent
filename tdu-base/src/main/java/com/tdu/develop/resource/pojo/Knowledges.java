package com.tdu.develop.resource.pojo;




public class Knowledges {
	
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String id;
	private String content;
	private String parentKnowledge;
	private String preKnowledge;
	private String subjectTree_Id;
	private String imageIcons;
	private String knowledgecontentId;
	private String beforCondition;
	
	
	
	private String CheckDel; 
	private String DelTime; 
	private String userKey;

	private String Type;
	private String Nmae;
	private String CustomName;
	private String CustomStyle;

	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getNmae() {
		return Nmae;
	}
	public void setNmae(String nmae) {
		Nmae = nmae;
	}
	public String getCustomName() {
		return CustomName;
	}
	public void setCustomName(String customName) {
		CustomName = customName;
	}
	public String getCustomStyle() {
		return CustomStyle;
	}
	public void setCustomStyle(String customStyle) {
		CustomStyle = customStyle;
	}

	public String getCheckDel() {
		return CheckDel;
	}
	public void setCheckDel(String checkDel) {
		CheckDel = checkDel;
	}
	public String getDelTime() {
		return DelTime;
	}
	public void setDelTime(String delTime) {
		DelTime = delTime;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getParentKnowledge() {
		return parentKnowledge;
	}
	public void setParentKnowledge(String parentKnowledge) {
		this.parentKnowledge = parentKnowledge;
	}
	public String getPreKnowledge() {
		return preKnowledge;
	}
	public void setPreKnowledge(String preKnowledge) {
		this.preKnowledge = preKnowledge;
	}
	public String getSubjectTree_Id() {
		return subjectTree_Id;
	}
	public void setSubjectTree_Id(String subjectTree_Id) {
		this.subjectTree_Id = subjectTree_Id;
	}
	public String getImageIcons() {
		return imageIcons;
	}
	public void setImageIcons(String imageIcons) {
		this.imageIcons = imageIcons;
	}
	public String getKnowledgecontentId() {
		return knowledgecontentId;
	}
	public void setKnowledgecontentId(String knowledgecontentId) {
		this.knowledgecontentId = knowledgecontentId;
	}
	public String getBeforCondition() {
		return beforCondition;
	}
	public void setBeforCondition(String beforCondition) {
		this.beforCondition = beforCondition;
	}
}
