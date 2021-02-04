package com.tdu.develop.resource.pojo;

/**
 * @author 志阳
 * @create 2021-01-22-10:19
 */
public class CustomerInformation {
    private String id;
    private String name;
    private String address;
    private String roleName;
    private String roleJob;
    private String rolePhone;
    private String itemCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleJob() {
        return roleJob;
    }

    public void setRoleJob(String roleJob) {
        this.roleJob = roleJob;
    }

    public String getRolePhone() {
        return rolePhone;
    }

    public void setRolePhone(String rolePhone) {
        this.rolePhone = rolePhone;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }
}
