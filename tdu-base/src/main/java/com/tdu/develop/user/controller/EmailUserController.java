package com.tdu.develop.user.controller;

import cn.hutool.core.util.RandomUtil;
import com.tdu.develop.resource.pojo.MailOperation;
import com.tdu.develop.user.pojo.EmailUser;
import com.tdu.develop.user.pojo.Users;
import com.tdu.develop.user.service.EmailService;
import com.tdu.develop.user.service.UsersService;
import com.tdu.develop.user.service.impl.EmailServiceImpl;
import com.tdu.develop.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 志阳
 * @create 2020-02-26-16:11
 */
@CrossOrigin
@Controller
@RequestMapping(value = "EmailUserController")
public class EmailUserController {

    @Autowired
    private EmailService emailService = new EmailServiceImpl();
    @Autowired
    UsersService usersService = new UserServiceImpl();

    private String useremail = "45187137@qq.com";
    private String auth = "cmvtitknkrhkbjec";

    /**
     * 获取properties值
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        Properties prop = new Properties();
        try {
            //装载配置文件
            prop.load(new FileInputStream(new File("src//email.properties")));

        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回获取的值
        return prop.getProperty(key);
    }

    //往数据库里添加email邮件信息
    @RequestMapping(value = "emailUser.action", method = {RequestMethod.POST})
    @ResponseBody
    public String emailUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        //1表示成功
        //2表示账号已存在
        String id = UUID.randomUUID().toString();
        String userName = request.getParameter("UserName");
        String email = request.getParameter("Email");
        String password = request.getParameter("PassWord");
        String telephone = request.getParameter("telphone");
        String ziyuan = request.getParameter("ziyuan");
        String state = "0";
        if (emailService.seleUsers(userName) != null) {
            return "2";
        } else {
            emailService.addEmail(id, userName, email, password, state, auth, telephone, ziyuan);
            MailOperation operation = new MailOperation();
            String host = "smtp.qq.com";
            String from = "45187137@qq.com";
            String to = "2791906134@qq.com";// 收件人
            String subject = "天度科技";
            //邮箱内容
            StringBuffer sb = new StringBuffer();
            String yzm = "http://www.tduvr.club/";
            sb.append("<!DOCTYPE>" + "<div bgcolor='#f1fcfa'   style='border:1px solid #d9f4ee; font-size:14px; line-height:22px; color:#005aa0;padding-left:1px;padding-top:5px;   padding-bottom:5px;'><span style='font-weight:bold;'>温馨提示：</span>"
                    + "<div style='width:950px;font-family:arial;'>欢迎使用TDuVR产品，请您点击下面链接确认确认注册信息：<br/><a  href='https://www.tduvr.club/tdu-base/EmailUserController/addUser.action?id=" + id + "' style='color:green'>" + yzm + "</a><br/>本邮件由系统自动发出，请勿回复。<br/>感谢您的使用。<br/>天度（厦门）科技股份有限公司</div>"
                    + "</div>");
//            sb.append("<!DOCTYPE>" + "<div bgcolor='#f1fcfa'   style='border:1px solid #d9f4ee; font-size:14px; line-height:22px; color:#005aa0;padding-left:1px;padding-top:5px;   padding-bottom:5px;'><span style='font-weight:bold;'>温馨提示：</span>"
//                    + "<div style='width:950px;font-family:arial;'>欢迎使用TDuVR产品，请您点击下面链接确认确认注册信息：<br/><a  href='http://192.168.0.55/tdu-base/EmailUserController/addUser.action?id=" + id + "' style='color:green'>" + yzm + "</a><br/>本邮件由系统自动发出，请勿回复。<br/>感谢您的使用。<br/>天度（厦门）科技股份有限公司</div>"
//                    + "</div>");
            try {
                String res = operation.sendMail(useremail, auth, host, from, email,
                        subject, sb.toString());
                System.out.println(res);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return "1";
        }


    }


    //查询所有的学生
    @RequestMapping(value = "seleStuBySearch.action", method = {RequestMethod.POST})
    @ResponseBody
    public void insEmail(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String id = UUID.randomUUID().toString();
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String telephone = request.getParameter("telphone");
        String ziyuan = request.getParameter("ziyuan");
        String state = "0";
        MailOperation operation = new MailOperation();
        String host = "smtp.qq.com";
        String from = "45187137@qq.com";
        String to = "2791906134@qq.com";// 收件人
        String subject = "测试发送功能";
        //邮箱内容
        StringBuffer sb = new StringBuffer();
        String yzm = RandomUtil.randomString(6);
        sb.append("<!DOCTYPE>" + "<div bgcolor='#f1fcfa'   style='border:1px solid #d9f4ee; font-size:14px; line-height:22px; color:#005aa0;padding-left:1px;padding-top:5px;   padding-bottom:5px;'><span style='font-weight:bold;'>温馨提示：</span>"
                + "<div style='width:950px;font-family:arial;'>欢迎使用NET微活动，您的注册码为：<br/><h2 style='color:green'>" + yzm + "</h2><br/>本邮件由系统自动发出，请勿回复。<br/>感谢您的使用。<br/>杭州恩意替电子商务有限公司</div>"
                + "</div>");
        emailService.addEmail(id, userName, email, password, state, auth, telephone, ziyuan);
        try {
            String res = operation.sendMail(email, auth, host, from, to,
                    subject, sb.toString());
            System.out.println(res);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //添加账号，邮件认证
    @RequestMapping(value = "insUser.action", method = {RequestMethod.POST})
    @ResponseBody
    public String insUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String emailId = request.getParameter("id");
        if (emailService.getState(emailId) == "0") {
            Users users = emailService.getUser(emailId);
            String roleId = "0c494961-fc3c-41b3-992a-4f9b0d0f57eb";
            usersService.insUser(users, roleId);
            return "1";
        } else {
            return "2";
        }
    }

    @RequestMapping(value = "addUser.action", method = {RequestMethod.GET})
    @ResponseBody
    public String addUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String emailId = request.getParameter("id");
        if (emailService.getState(emailId).equals("0")) {
            EmailUser users = emailService.getEmailUser(emailId);
            Users temp = usersService.getTemp(users.getUserName());
            if (users.getZiyuan().equals("false")) {
                System.out.println("false");
                ;
                Users user = emailService.getUser(emailId);

                //获取系统时间，并且转码成数据库可读
                Date d = new Date();
                Date endDay = new Date();
                //创建Calendar实例
                Calendar cal = Calendar.getInstance();
                cal.setTime(endDay);   //设置当前时间
                cal.add(Calendar.MONTH, 1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateNowStr = sdf.format(d);
                //同理增加一个月和一天的方法：
                String dateEndStr = sdf.format(cal.getTime());
                user.setIdol(dateEndStr);
                user.setCreateDate(dateNowStr);
                user.setBirthdate(dateNowStr);
                user.setEmail(users.getEmail());

                user.setId(emailId);

                String roleId = "0c494961-fc3c-41b3-992a-4f9b0d0f57eb";
                usersService.insUser(user, roleId);
            }
            if (users.getZiyuan().equals("true")) {
                if (temp == null) {
                    usersService.ziyuaninsUserPersonl(users.getId(), users.getPassword(), users.getUserName(), users.getPassword(), "男", users.getUserName(), users.getTelephone());
                }
            }
            return "注册成功！请登录！";
        } else {
            return "2";
        }
    }

}
