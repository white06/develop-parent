package com.tdu.develop.resource.controller;

import com.tdu.develop.resource.pojo.Knowlegcontent;
import com.tdu.develop.resource.service.AddKnowledgeService;
import com.tdu.develop.resource.service.impl.AddKnowledgeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * @author 志阳
 * @create 2019-08-27-17:18
 */
@CrossOrigin
@Controller
@RequestMapping(value="AddKnowledgeController")
public class AddKnowledgeController {

    @Autowired
    public AddKnowledgeService addKnowledgeService=new AddKnowledgeServiceImpl();




    @RequestMapping("addUploading.action")
    @ResponseBody
    public String addUploading(@RequestParam("file") MultipartFile file, @RequestParam("customname") String customName, @RequestParam("introduce") String introduce, @RequestParam("customstyle") String customStyle, @RequestParam("subjectTreeId") String treeId,
                               @RequestParam("brosweFile") String fileName, @RequestParam("type") String contentType, @RequestParam("treeNodeId") String clickNodeId, HttpSession session){
        String string;
        String userId=session.getAttribute("ID").toString();
        try{
            Knowlegcontent knowlegcontent = new Knowlegcontent();
            knowlegcontent.setCustomname(customName);
            knowlegcontent.setIntroduce(introduce);
            knowlegcontent.setCustomstyle(customStyle);
            knowlegcontent.setUserKey(userId);
            string = addKnowledgeService.addUploading(knowlegcontent,contentType,fileName,treeId,clickNodeId,file);
            return string;
        }catch(Exception e){
            e.printStackTrace();
            String result = "fail";
            return result;
        }
    }
    @RequestMapping("editorLoading.action")
    @ResponseBody
    public String editorLoading(@RequestParam("file") MultipartFile file, @RequestParam("customname") String customName, @RequestParam("introduce") String introduce, @RequestParam("customstyle") String customStyle, @RequestParam("subjectTreeId") String treeId,
                                @RequestParam("brosweFile") String fileName, @RequestParam("type") String contentType, @RequestParam("treeNodeId") String clickNodeId){
        String string;
        try{
            Knowlegcontent knowlegcontent = new Knowlegcontent();
            knowlegcontent.setCustomname(customName);
            knowlegcontent.setIntroduce(introduce);
            knowlegcontent.setCustomstyle(customStyle);
            string = addKnowledgeService.editorLoading(knowlegcontent,contentType,fileName,treeId,clickNodeId,file);
            return string;
        }catch(Exception e){
            e.printStackTrace();
            String result = "fail";
            return result;
        }
    }

    @RequestMapping("IsKnowledgeContent.action")
    @ResponseBody
    public String isKnowledgeContent(HttpServletRequest request){
        String nodeId = request.getParameter("id");
        String result = addKnowledgeService.isKnowledgeContent(nodeId);
        return result;
    }
    @RequestMapping("AddSimulateModel.action")
    @ResponseBody
    public String addSimulateModel(HttpServletRequest request,HttpSession session){
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String userId=session.getAttribute("ID").toString();

        Knowlegcontent knowledgecontent = new Knowlegcontent();
        knowledgecontent.setNmae(request.getParameter("simulate"));
        knowledgecontent.setCustomname(request.getParameter("name"));
        knowledgecontent.setCustomstyle("default");//
        knowledgecontent.setType(request.getParameter("simulType"));
        knowledgecontent.setUserKey(userId);
        String nodeId = request.getParameter("KnowledgeId");
        String treeId = request.getParameter("treeId");
        String knowledgesId = addKnowledgeService.addSimulateModel(knowledgecontent,treeId,nodeId);
        return knowledgesId;
    }
    @RequestMapping("AddQuesModel.action")
    @ResponseBody
    public String addQuesModel(HttpServletRequest request,HttpSession session){
        String userId=session.getAttribute("ID").toString();
        Knowlegcontent knowlegcontent = new Knowlegcontent();
        knowlegcontent.setNmae(request.getParameter("errorques"));
        knowlegcontent.setCustomname(request.getParameter("name"));
        knowlegcontent.setCustomstyle(request.getParameter("cusstyle"));
        knowlegcontent.setUserKey(userId);
        String nodeId = request.getParameter("KnowledgeId");
        String treeId = request.getParameter("treeId");
        String knowledgesId = addKnowledgeService.addQuesModel(knowlegcontent,treeId,nodeId);
        return knowledgesId;
    }
    /**
     * 添加考试页面
     * @param request
     * @return
     */
    @RequestMapping("addExamModel.action")
    @ResponseBody
    public String addExamModel(HttpServletRequest request,HttpSession session){
        String userId=session.getAttribute("ID").toString();
        Knowlegcontent knowlegcontent = new Knowlegcontent();
        knowlegcontent.setNmae(request.getParameter("examId"));
        knowlegcontent.setCustomname(request.getParameter("name"));
        knowlegcontent.setCustomstyle(request.getParameter("cusstyle"));
        String nodeId = request.getParameter("KnowledgeId");
        String treeId = request.getParameter("treeId");
        knowlegcontent.setUserKey(userId);
        String knowledgesId = addKnowledgeService.addExamModel(knowlegcontent,treeId,nodeId);
        return knowledgesId;
    }
    @RequestMapping("AddCustomModel.action")
    @ResponseBody
    public String addCustomModel(HttpServletRequest request,HttpSession session){
        String userId=session.getAttribute("ID").toString();
        Knowlegcontent knowlegcontent = new Knowlegcontent();
        knowlegcontent.setNmae(request.getParameter("errorques"));
        knowlegcontent.setCustomname(request.getParameter("name"));
        knowlegcontent.setCustomstyle(request.getParameter("cusstyle"));
        knowlegcontent.setUserKey(userId);
        String nodeId = request.getParameter("KnowledgeId");
        String treeId = request.getParameter("treeId");
        String knowledgesId = addKnowledgeService.addCustomModel(knowlegcontent,treeId,nodeId);
        return knowledgesId;
    }
    @RequestMapping("AddHtmlEditorContent.action")
    @ResponseBody
    public String addHtmlEditorContent(HttpServletRequest request){
        Knowlegcontent knowlegcontent = new Knowlegcontent();
        knowlegcontent.setIntroduce(request.getParameter("introduce"));
        knowlegcontent.setCustomname(request.getParameter("name"));
        knowlegcontent.setCustomstyle(request.getParameter("cusstyle"));
        String nodeId = request.getParameter("KnowledgeId");
        String treeId = request.getParameter("treeId");
        String htmlcontent = request.getParameter("htmlcontent");
        String knowledgesId = addKnowledgeService.addHtmlEditorContent(knowlegcontent,htmlcontent,treeId,nodeId);
        return knowledgesId;
    }
    @RequestMapping("getknowidbycontentid.action")
    @ResponseBody
    public String getknowidbycontentid(HttpServletRequest request){
        String contentId = request.getParameter("id");
        String knowId = addKnowledgeService.getKnowIdbycontentId(contentId);
        return knowId;
    }
    /**
     * 查询考试名称
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value="getExamName.action",method={RequestMethod.POST})
    @ResponseBody
    public Knowlegcontent getExamName(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String examId = request.getParameter("examId");
        return addKnowledgeService.getExamName(examId);
    }


}
