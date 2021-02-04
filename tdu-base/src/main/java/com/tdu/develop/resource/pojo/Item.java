package com.tdu.develop.resource.pojo;

/**
 * @author 志阳
 * @create 2020-09-10-10:19
 */
public class Item {
    private String id;
    private String itemName;
    private String subjectId;
    private String cunstomerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getCunstomerId() {
        return cunstomerId;
    }

    public void setCunstomerId(String cunstomerId) {
        this.cunstomerId = cunstomerId;
    }
}
