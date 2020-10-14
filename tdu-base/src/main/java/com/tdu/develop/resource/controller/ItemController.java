package com.tdu.develop.resource.controller;

import com.tdu.develop.resource.pojo.Linshi;
import com.tdu.develop.resource.pojo.blue;
import com.tdu.develop.resource.service.ItemService;
import com.tdu.develop.resource.service.impl.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author 志阳
 * @create 2020-09-10-10:24
 */
@CrossOrigin
@Controller
@RequestMapping(value = "ItemController")
public class ItemController {
    @Autowired
    ItemService itemService = new ItemServiceImpl();


    @RequestMapping(value = "yuxi.action", method = {RequestMethod.POST})
    @ResponseBody
    public void yuxi(HttpServletRequest request, HttpSession session) throws Exception {
        String userId = request.getParameter("userId");
        String yuxi = request.getParameter("yuxi");

        itemService.yuxi(yuxi, userId);

    }

    @RequestMapping(value = "getyuxi.action", method = {RequestMethod.POST})
    @ResponseBody
    public String getyuxi(HttpServletRequest request, HttpSession session) throws Exception {
        String userId = request.getParameter("userId");

        return itemService.getyuxi(userId);

    }

    @RequestMapping(value = "getblue.action", method = {RequestMethod.POST})
    @ResponseBody
    public blue getblue(HttpServletRequest request, HttpSession session) throws Exception {
        String userId = request.getParameter("userId");

        return itemService.getblue(userId);

    }

    @RequestMapping(value = "tijiao.action", method = {RequestMethod.POST})
    @ResponseBody
    public void tijiao(HttpServletRequest request, HttpSession session) throws Exception {
        String userId = request.getParameter("userId");
        String caozuo = request.getParameter("caozuo");
        itemService.tijiaocaozuo(userId, caozuo);

    }

    @RequestMapping(value = "tijiaobaogao.action", method = {RequestMethod.POST})
    @ResponseBody
    public void tijiaobaogao(HttpServletRequest request, HttpSession session) throws Exception {
        String userId = request.getParameter("userId");
        String baogao = request.getParameter("baogao");
        itemService.tijiaobaogao(userId, baogao);

    }

    @RequestMapping(value = "get.action", method = {RequestMethod.POST})
    @ResponseBody
    public blue get(HttpServletRequest request, HttpSession session) throws Exception {
        String userId = request.getParameter("userId");

        return itemService.get(userId);

    }
}
