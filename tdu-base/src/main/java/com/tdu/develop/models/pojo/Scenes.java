package com.tdu.develop.models.pojo;


/**
 * 场景表  前台页面
 *
 * @author TDU
 */
public class Scenes {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String Id;
    private String Content;
    private String ParentScene;
    private String PreScene;
    private String SubjectTree_Id;
    private String ImageIcons;
    private String SceneContentId;
    private String BeforCondition;

    private String photoName;
    private String userKey;


    private int CheckDel;
    private String DelTime;

    private String fileName;


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

    public String getParentScene() {
        return ParentScene;
    }

    public void setParentScene(String parentScene) {
        ParentScene = parentScene;
    }

    public String getPreScene() {
        return PreScene;
    }

    public void setPreScene(String preScene) {
        PreScene = preScene;
    }

    public String getSceneContentId() {
        return SceneContentId;
    }

    public void setSceneContentId(String sceneContentId) {
        SceneContentId = sceneContentId;
    }

}
