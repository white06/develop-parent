package com.tdu.develop.resource.pojo;
/**
 * @author 志阳
 * @create 2021-01-28-10:19
 */
public class BusinessUser {
    //id
    private String id;
    //用户id
    private String userId;
    //商机ID
    private String businessId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}
