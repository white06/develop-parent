package com.tdu.develop.resource.pojo;


public class QuestionPagerContents {
	
	private String id;
	private String questionKey;//绑定的题目序号
	private String questionContent;//绑定的题目
	private int questionIndex;//题目顺序
	private int questionScore;//题目的分数
	private String questionType;//题目类型
	private String questionPager_Id;//绑定的试卷序号
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQuestionKey() {
		return questionKey;
	}
	public void setQuestionKey(String questionKey) {
		this.questionKey = questionKey;
	}
	public String getQuestionContent() {
		return questionContent;
	}
	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}
	public int getQuestionIndex() {
		return questionIndex;
	}
	public void setQuestionIndex(int questionIndex) {
		this.questionIndex = questionIndex;
	}
	public int getQuestionScore() {
		return questionScore;
	}
	public void setQuestionScore(int questionScore) {
		this.questionScore = questionScore;
	}
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	public String getQuestionPager_Id() {
		return questionPager_Id;
	}
	public void setQuestionPager_Id(String questionPager_Id) {
		this.questionPager_Id = questionPager_Id;
	}
}
