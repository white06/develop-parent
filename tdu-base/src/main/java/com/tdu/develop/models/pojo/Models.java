package com.tdu.develop.models.pojo;



/**
 * 模型表 前台页面用
 * @author TDU
 *
 */
public class Models {
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String Id;
	private String Content;
	private String ParentModel;
	private String PreModel;
	private String SubjectTree_Id;
	private String ImageIcons;
	private String ModelContentId;
	private String BeforCondition;

	private String photoName;
	private String userKey;

//	private String ContactKey;
	private int CheckDel;
	private String DelTime;

	private String fileName;

	private String Type;


	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
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
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
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
	public String getBeforCondition() {
		return BeforCondition;
	}
	public void setBeforCondition(String beforCondition) {
		BeforCondition = beforCondition;
	}
	public String getParentModel() {
		return ParentModel;
	}
	public void setParentModel(String parentModel) {
		ParentModel = parentModel;
	}
	public String getPreModel() {
		return PreModel;
	}
	public void setPreModel(String preModel) {
		PreModel = preModel;
	}
	public String getModelContentId() {
		return ModelContentId;
	}
	public void setModelContentId(String modelContentId) {
		ModelContentId = modelContentId;
	}
//	public String getContactKey() {
//		return ContactKey;
//	}
//	public void setContactKey(String contactKey) {
//		ContactKey = contactKey;
//	}
}
