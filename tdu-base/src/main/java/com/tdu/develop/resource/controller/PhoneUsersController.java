package com.tdu.develop.resource.controller;

import com.tdu.develop.user.pojo.Users;
import com.tdu.develop.user.service.UsersService;
import com.tdu.develop.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 志阳
 * @create 2019-11-12-16:30
 */
@CrossOrigin
@Controller
@RequestMapping(value = "PhoneUsersController")
public class PhoneUsersController {
    @Autowired
    UsersService us = new UserServiceImpl();

    /**
     * 根据用户id获取用户信息
     *
     * @param request
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "getUser.action", method = {RequestMethod.GET})
    @ResponseBody
    public Users getUser(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String userId = session.getAttribute("ID").toString();
        return us.getUser(userId);
    }

    /**
     * 根据用户id修改用户名
     *
     * @param request
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "setUserName.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean setUserName(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String userId = session.getAttribute("ID").toString();
        String userName = request.getParameter("UserName");
        return us.setUsername(userId, userName);
    }

    /**
     * 根据用户id修改用户性别
     *
     * @param request
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "setUserSex.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean setUserSex(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String userId = session.getAttribute("ID").toString();
        String userSex = request.getParameter("UserSex");
        return us.setUserSex(userId, userSex);
    }

    /**
     * 根据用户id修改用户生日
     *
     * @param request
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "setUserBirthday.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean setUserBirthday(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String userId = session.getAttribute("ID").toString();
        String userBirthday = request.getParameter("UserBirthday");
        return us.setUserBirthdate(userId, userBirthday);
    }

    /**
     * 根据用户id修改用户id
     *
     * @param request
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "setUserQQ.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean setUserQQ(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String userId = session.getAttribute("ID").toString();
        String userQQ = request.getParameter("UserQQ");
        return us.setUserQQ(userId, userQQ);
    }

    /**
     * 根据用户id修改用户微信
     *
     * @param request
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "setUserChat.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean setUserChat(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String userId = session.getAttribute("ID").toString();
        String userWeixin = request.getParameter("UserChat");
        return us.setUserWeixin(userId, userWeixin);
    }

    /**
     * 根据用户id修改用户手机
     *
     * @param request
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "setUserPhone.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean setUserPhone(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String userId = session.getAttribute("ID").toString();
        String userPhone = request.getParameter("UserPhone");
        return us.setUserPhone(userId, userPhone);
    }

    /**
     * 根据用户id修改用户邮箱
     *
     * @param request
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "setUserEmail.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean setUserEmail(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String userId = session.getAttribute("ID").toString();
        String userEmail = request.getParameter("UserEmail");
        return us.setUserMail(userId, userEmail);
    }
}
