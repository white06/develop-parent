package com.tdu.develop.user.service.impl;

import com.tdu.develop.resource.pojo.ImgURL;
import com.tdu.develop.user.mapper.EmailUserMapper;
import com.tdu.develop.user.mapper.ImgUrlMapper;
import com.tdu.develop.user.mapper.InformessagesMapper;
import com.tdu.develop.user.pojo.EmailUser;
import com.tdu.develop.user.pojo.Users;
import com.tdu.develop.user.service.EmailService;
import com.tdu.develop.user.service.ImgUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author 志阳
 * @create 2019-08-30-14:16
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    EmailUserMapper emailUserMapper;
    public void addEmail(String id,String username,String email,String password,String state,String code,String telephone,String ziyuan){
        emailUserMapper.addEmail(id,username,email,password,state,code,telephone,ziyuan);
    }

    public Users seleUsers(String username){
        return emailUserMapper.seleUser(username);

    }
    public Users getUser(String emailId){
        EmailUser emailUser=emailUserMapper.getEmailUser(emailId);
        Users users=new Users();
        String id= UUID.randomUUID().toString();
        String sex="男";
        users.setId(id);
        users.setUserName(emailUser.getUserName());
        users.setName(emailUser.getUserName());
        users.setSex(sex);
        users.setPassWord(emailUser.getPassword());
        users.setMobilePhoneNum(emailUser.getTelephone());
        users.setConfirmPassword(emailUser.getPassword());
        return users;
    }
    public EmailUser getEmailUser(String emailId){
        EmailUser emailUser=emailUserMapper.getEmailUser(emailId);
        return emailUser;
    }

    public String getState(String emailId){

        return emailUserMapper.getEmailUser(emailId).getState();
    }
}
