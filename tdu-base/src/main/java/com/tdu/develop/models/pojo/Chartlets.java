package com.tdu.develop.models.pojo;



/**
 * 贴图表  前台页面
 * @author TDU
 *
 */
public class Chartlets {
	
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String Id;
	private String Content;
	private String ParentChartlet;
	private String PreChartlet;
	private String SubjectTree_Id;
	private String ImageIcons;
	private String ChartletContentId;
	private String BeforCondition;
	
	private String photoName;
	private String userKey;
	
	
	private int CheckDel;
	private String DelTime;
	
	
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public int getCheckDel() {
		return CheckDel;
	}
	public void setCheckDel(int checkDel) {
		CheckDel = checkDel;
	}
	public String getDelTime() {
		return DelTime;
	}
	public void setDelTime(String delTime) {
		DelTime = delTime;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getParentChartlet() {
		return ParentChartlet;
	}
	public void setParentChartlet(String parentChartlet) {
		ParentChartlet = parentChartlet;
	}
	public String getPreChartlet() {
		return PreChartlet;
	}
	public void setPreChartlet(String preChartlet) {
		PreChartlet = preChartlet;
	}
	public String getSubjectTree_Id() {
		return SubjectTree_Id;
	}
	public void setSubjectTree_Id(String subjectTree_Id) {
		SubjectTree_Id = subjectTree_Id;
	}
	public String getImageIcons() {
		return ImageIcons;
	}
	public void setImageIcons(String imageIcons) {
		ImageIcons = imageIcons;
	}
	public String getChartletContentId() {
		return ChartletContentId;
	}
	public void setChartletContentId(String chartletContentId) {
		ChartletContentId = chartletContentId;
	}
	public String getBeforCondition() {
		return BeforCondition;
	}
	public void setBeforCondition(String beforCondition) {
		BeforCondition = beforCondition;
	}
	
}
