package com.tdu.develop.models.pojo;



/**
 * 声音表  前台页面
 * @author TDU
 *
 */
public class Voices {
	
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private String Id;
	private String Content;
	private String ParentVoice;
	private String PreVoice;
	private String SubjectTree_Id;
	private String ImageIcons;
	private String VoiceContentId;
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
	public String getParentVoice() {
		return ParentVoice;
	}
	public void setParentVoice(String parentVoice) {
		ParentVoice = parentVoice;
	}
	public String getPreVoice() {
		return PreVoice;
	}
	public void setPreVoice(String preVoice) {
		PreVoice = preVoice;
	}
	public String getVoiceContentId() {
		return VoiceContentId;
	}
	public void setVoiceContentId(String voiceContentId) {
		VoiceContentId = voiceContentId;
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
	
}
