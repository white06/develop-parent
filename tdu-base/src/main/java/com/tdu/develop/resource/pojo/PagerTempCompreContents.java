package com.tdu.develop.resource.pojo;


public class PagerTempCompreContents {
	
	private String Id;
	private String PagerTempId;
	private String QuestionStem;
	private String QuestionKeyArray;
	private String CreatePerson;
	private String CreateDate;
	private int QuestionOrder;
	private int Score;
	public int getScore() {
		return Score;
	}
	public void setScore(int score) {
		this.Score = score;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getPagerTempId() {
		return PagerTempId;
	}
	public void setPagerTempId(String pagerTempId) {
		PagerTempId = pagerTempId;
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
	public int getQuestionOrder() {
		return QuestionOrder;
	}
	public void setQuestionOrder(int questionOrder) {
		QuestionOrder = questionOrder;
	}
}
