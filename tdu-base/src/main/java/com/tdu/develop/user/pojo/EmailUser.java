package com.tdu.develop.user.pojo;

/**
 * 邮件注册实体类
 * @author 志阳
 * @create 2020-02-26-15:01
 */
public class EmailUser {
    private String id;
    private String userName;
    private String email;
    private String password;
    private String code;
    private String state;

    private String telephone;
    private String ziyuan;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getZiyuan() {
        return ziyuan;
    }

    public void setZiyuan(String ziyuan) {
        this.ziyuan = ziyuan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
