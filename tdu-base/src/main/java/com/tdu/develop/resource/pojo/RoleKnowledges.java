package com.tdu.develop.resource.pojo;


public class RoleKnowledges {

    private String id;
    private String content;
    private String parentKnowledge;
    private String preKnowledge;
    private String subjectTree_Id;
    private String imageIcons;
    private String knowledgecontentId;
    private String beforCondition;
    private String userid;

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
