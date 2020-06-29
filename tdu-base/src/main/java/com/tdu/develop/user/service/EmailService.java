package com.tdu.develop.user.service;

import com.tdu.develop.user.pojo.*;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-07-15:25
 */
public interface EmailService {
    public void addEmail(String id, String username, String email, String password, String state, String code,String telephone,String ziyuan);

    public Users seleUsers(String username);

    public Users getUser(String emailId);
    public String getState(String emailId);

    public EmailUser getEmailUser(String emailId);
}
