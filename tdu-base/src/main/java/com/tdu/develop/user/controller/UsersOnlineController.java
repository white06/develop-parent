package com.tdu.develop.user.controller;


import com.tdu.develop.user.pojo.Users;
import com.tdu.develop.user.service.UserOnlineService;
import com.tdu.develop.user.service.UsersService;

import com.tdu.develop.user.service.impl.UserOnlineServiceImpl;
import com.tdu.develop.user.service.impl.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author 志阳
 * @create 2019-08-07-14:47
 */
@CrossOrigin
@Controller
@RequestMapping(value = "UsersOnlineController")
public class UsersOnlineController {

    private static final Logger logger = LoggerFactory.getLogger(UsersOnlineController.class);

    @Autowired
    UsersService usersService = new UserServiceImpl();

    @Autowired
    UserOnlineService userOnlineService = new UserOnlineServiceImpl();

    @RequestMapping(value = "getseducount.action")
    @ResponseBody
    public Map<String,Object> getseducount(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String userId=(String) session.getAttribute("ID");

        return userOnlineService.getseducount(userId);
    }

    // @RequestMapping(value = "getCode.action")
    // @ResponseBody
    // public String getCode(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
    //     String userId=(String) session.getAttribute("ID");
    //
    //     return userOnlineService.getseducount(userId);
    // }
}
