package com.tdu.develop.resource.pojo;

public class QuestionComprehensives {
	private String Id;
	private String QuestionStem;
	private String QuestionKeyArray;
	private String KnowledgeId;
	private String Type;
	private String CreatePerson;
	private String CreateDate;
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getKnowledgeId() {
		return KnowledgeId;
	}
	public void setKnowledgeId(String knowledgeId) {
		KnowledgeId = knowledgeId;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getQuestionStem() {
		return QuestionStem;
	}
	public void setQuestionStem(String questionStem) {
		QuestionStem = questionStem;
	}
	public String getQuestionKeyArray() {
		return QuestionKeyArray;
	}
	public void setQuestionKeyArray(String questionKeyArray) {
		QuestionKeyArray = questionKeyArray;
	}
	public String getCreatePerson() {
		return CreatePerson;
	}
	public void setCreatePerson(String createPerson) {
		CreatePerson = createPerson;
	}
	public String getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}
}
