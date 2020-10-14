package com.tdu.develop.user.controller;

import com.tdu.develop.common.exception.JsonResult;
import com.tdu.develop.resource.service.KnowledgesService;
import com.tdu.develop.resource.service.impl.KnowledgesServiceImpl;
import com.tdu.develop.user.pojo.SubjectBackgrounds;
import com.tdu.develop.user.pojo.Subjects;
import com.tdu.develop.user.pojo.ZNodes;
import com.tdu.develop.user.service.SubjectService;
import com.tdu.develop.user.service.impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 志阳
 * @create 2019-08-13-14:53
 */
@CrossOrigin
@Controller
@RequestMapping(value = "SubjectController")
public class SubjectController {

    @Autowired
    SubjectService subjectService = new SubjectServiceImpl();
    @Autowired
    KnowledgesService knowledgesService = new KnowledgesServiceImpl();
    public static String Null = null;


    @RequestMapping(value = "getSubject.action", method = {RequestMethod.POST})
    @ResponseBody
    public Subjects getSubject(@RequestParam("id") String id) {
        return subjectService.getSubject(id);
    }

    @RequestMapping("loadgroundImg.action")
    @ResponseBody
    public JsonResult loadgroundImg(HttpServletRequest request) {
        //System.out.println(request.getSession().getServletContext().getRealPath("/"));
        String imgUrl = subjectService.loadgroundImg();
        return new JsonResult(imgUrl);
    }

    @RequestMapping("editSubjectBackground.action")
    @ResponseBody
    public JsonResult editSubjectBackground(@RequestParam("id") int id, @RequestParam("background") MultipartFile background, @RequestParam("logo") MultipartFile logo) {
        try {
            subjectService.editSubjectBackground(background, logo, id);
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonResult(e);
        }
        return new JsonResult();
    }

    /**
     * ��������ѯ��Ŀ�ı���ͼƬ��logo����Ϣ
     *
     * @return
     */
    @RequestMapping("querySubjectInfos.action")
    @ResponseBody
    public JsonResult querySubjectInfos(HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        List<SubjectBackgrounds> list = subjectService.querySubjectInfos();
        return new JsonResult(list);
    }
    //


    @RequestMapping(value = "subjectChange.action", method = {RequestMethod.POST})
    @ResponseBody
    public List TreeChange(HttpServletRequest request, HttpServletResponse response) {
        String SubjectKey = request.getParameter("Id");
        String treetype = request.getParameter("treetype");

        List<ZNodes> rList = new ArrayList<ZNodes>();
        rList = knowledgesService.seleknowledges(treetype);
        return rList;

    }

    /**
     * ��ѯ��̨����ͼƬ·��
     *
     * @return
     */
    @RequestMapping("showtopimg.action")
    @ResponseBody
    public JsonResult showtopimg() {
        String imgurl = subjectService.gettopimg();
        return new JsonResult(imgurl);
    }


    /**
     * 获取nmae
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "getKnowNmae.action", method = {RequestMethod.POST})
    @ResponseBody
    public void getKnowNmae(HttpServletRequest request, HttpServletResponse response) {
        String knowledgecontentId = request.getParameter("knowledgecontentId");
        String type = subjectService.getKnowNmae(knowledgecontentId);
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.getWriter().print(type);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取科目树第一个科目
     *
     * @param request
     * @param response
     * @param response
     */
    @RequestMapping(value = "getSubIdOne.action", method = {RequestMethod.POST})
    @ResponseBody
    public String getSubIdOne(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String userId = session.getAttribute("ID").toString();
        return subjectService.getSubIdOne(userId);
    }

    /**
     * 获取科目树第一个科目
     *
     * @param request
     * @param response
     * @param response
     */
    @RequestMapping(value = "getSubIdOne_develop.action", method = {RequestMethod.POST})
    @ResponseBody
    public String getSubIdOne_develop(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String userId = session.getAttribute("ID").toString();
        return subjectService.getSubIdOne_develop(userId);
    }

    @RequestMapping(value = "getMajors.action", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getMajors(HttpServletRequest request, HttpServletResponse response) {
        return subjectService.getMajors();
    }
}
