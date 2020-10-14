package com.tdu.develop.resource.controller;

import com.tdu.develop.resource.pojo.KnowOnline;
import com.tdu.develop.resource.service.KnowOnlineService;
import com.tdu.develop.resource.service.impl.KnowOnlineServiceImpl;
import com.tdu.develop.util.OnlineUtil;
import com.tdu.develop.util.knowOnlineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 志阳
 * @create 2020-04-10-17:21
 */
@CrossOrigin
@Controller
@RequestMapping(value = "KnowOnlineController")
public class KnowOnlineController {
    @Autowired
    private KnowOnlineService knowOnlineService = new KnowOnlineServiceImpl();


    @RequestMapping(value = "getClassUsersOnLine.action", method = {RequestMethod.POST})
    @ResponseBody
    public knowOnlineUtil getKnoUserOnLine(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String knowContId = request.getParameter("knowContId");
        String userId = request.getParameter("userId");
        knowOnlineUtil knowOnline = knowOnlineService.getKnoUserOnLine(knowContId, userId);
        System.out.println(knowOnline);
        return knowOnline;
    }

    @RequestMapping(value = "getKnoOnLine.action", method = {RequestMethod.POST})
    @ResponseBody
    public knowOnlineUtil getKnoOnLine(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String knowContId = request.getParameter("knowContId");
        knowOnlineUtil knowOnline = knowOnlineService.getKnoOnLine(knowContId);
        System.out.println(knowOnline);
        return knowOnline;
    }

    @RequestMapping(value = "getKnoLineGroupUser.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<knowOnlineUtil> getKnoLineGroupUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String knowContId = request.getParameter("knowContId");
        List<knowOnlineUtil> knowOnline = knowOnlineService.getKnoLineGroupUser(knowContId);
        System.out.println(knowOnline);
        return knowOnline;
    }

    /**
     * 触发打开时间
     */
    @RequestMapping("insertKnowOnline.action")
    @ResponseBody
    public String insertKnowOnline(HttpServletRequest request, HttpSession session) throws Exception {
        String userId = session.getAttribute("ID").toString();
        String loginId = UUID.randomUUID().toString();
        String knowId = request.getParameter("knowContentId");
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        Date sd1 = sdf.parse(dateNowStr);
        KnowOnline knowOnline = new KnowOnline();
        knowOnline.setId(loginId);
        knowOnline.setKnowId(knowId);
        knowOnline.setPlayTime(sd1);
        knowOnline.setUserId(userId);
        knowOnline.setOnlineTime(30);
        if (knowId != null) {
            knowOnlineService.insertKnowOnline(knowOnline);
        }
        return loginId;
    }


    @RequestMapping("insertKnowOutline.action")
    @ResponseBody
    public void insertKnowOutline(HttpServletRequest request, HttpSession session) throws Exception {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = df.format(d);
        Date sd1 = df.parse(dateNowStr);
        String loginId = request.getParameter("loginId");
        if (loginId != null) {
            knowOnlineService.insertKnowOutline(loginId, sd1);
        }
    }
}
