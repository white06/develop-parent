package com.tdu.develop.user.controller;

import com.tdu.develop.common.exception.JsonResult;
import com.tdu.develop.user.pojo.Informessages;
import com.tdu.develop.user.pojo.Reply;
import com.tdu.develop.user.service.InformessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author 志阳
 * @create 2019-08-14-15:29
 */
@CrossOrigin
@Controller
@RequestMapping(value = "InformessagesController")
public class InformessagesController {
    @Autowired
    InformessagesService informessagesService;


    //查询时间接近现在时间的十条公告
    @RequestMapping(value = "selSomeInf.action", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResult selSomeInf(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page"));
        int size = Integer.parseInt(request.getParameter("size"));
        Map<String, Object> list = informessagesService.selSomeInf(page, size);
        return new JsonResult(list);
    }

    //新增公告
    @RequestMapping(value = "insInfor.action", method = {})
    public void insInfor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Informessages informessages = new Informessages();
        informessages.setId(UUID.randomUUID().toString());
        Date date = new Date();
        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        informessages.setCreateDate(newDate.format(date));
        informessages.setTitle(java.net.URLDecoder.decode(request.getParameter("title"), "UTF-8"));
        informessages.setContent(java.net.URLDecoder.decode(request.getParameter("content"), "UTF-8"));
        informessages.setDepartmentId(java.net.URLDecoder.decode(request.getParameter("depertmentId"), "UTF-8"));
        if (informessagesService.insInfor(informessages)) {
            response.getWriter().print("ture");
        }
    }

    //编辑公告
    @RequestMapping(value = "editInfor.action", method = {})
    public void editInfor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        Informessages informessages = new Informessages();
        informessages.setId(id);
        Date date = new Date();
        SimpleDateFormat newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        informessages.setCreateDate(newDate.format(date));
        informessages.setTitle(java.net.URLDecoder.decode(request.getParameter("title"), "UTF-8"));
        informessages.setContent(java.net.URLDecoder.decode(request.getParameter("content"), "UTF-8"));
        if (informessagesService.editInfor(informessages)) {
            response.getWriter().print("ture");
        }
    }


    //查询全部公告
    @RequestMapping(value = "selAllInf.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Informessages> selAllInf(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String id = request.getParameter("id");
        String depertmentId = request.getParameter("depertmentId");
        String userId = "";
        if (session.getAttribute("ID") != null) {
            userId = session.getAttribute("ID").toString();
        }
        List<Informessages> list = null;
        if (id == "" || id == null) {
            list = informessagesService.selAllInf(userId);
        } else {
            int type = Integer.parseInt(id);
            if (type == 1) {
                list = informessagesService.selAllInf1(depertmentId);
            } else if (type == 2) {
                list = informessagesService.selAllInf2(depertmentId);
            } else if (type == 3) {
                list = informessagesService.selAllInf3(depertmentId);
            }
        }

        return list;
    }

    //删除公告
    @RequestMapping(value = "deleInforMessage.action")
    @ResponseBody
    public JsonResult deleInforMessage(HttpServletRequest request) {
        informessagesService.deleInforMessage(request.getParameter("id"));
        return new JsonResult();
    }

    /******************************************************************************************************************/


    //新增老师的回复
    @RequestMapping(value = "inTeaReply.action", method = {RequestMethod.POST})
    public void inTeaReply(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws IOException {
        String huifu = request.getParameter("huifu");
        String messageId = request.getParameter("messageId");
        String userKey = request.getParameter("userKey");
        Reply reply = new Reply();
        reply.setId(UUID.randomUUID().toString());
        reply.setTeaKey(session.getAttribute("ID").toString());
        reply.setUserKey(userKey);
        reply.setContent(huifu);
        reply.setMessageKey(messageId);
        Date date = new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String today = sdf.format(date);
        reply.setDate(today);
        if (informessagesService.teaHuifu(reply)) {
            if (informessagesService.upAnony1(messageId)) {
                response.getWriter().print("ture");
            }
            ;
        }
    }

    //查询单个公告
    @RequestMapping(value = "getInfo.action", method = {RequestMethod.POST})
    @ResponseBody
    public Informessages getInfo(HttpServletRequest request) {
        Informessages list = informessagesService.getInfo(request.getParameter("id"));
        return list;
    }

}
