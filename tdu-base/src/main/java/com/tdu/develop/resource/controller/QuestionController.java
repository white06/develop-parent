package com.tdu.develop.resource.controller;

import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.PrevKnowledgeContentInfor;
import com.tdu.develop.resource.pojo.Question;
import com.tdu.develop.resource.pojo.QuestionComprehensives;
import com.tdu.develop.resource.service.QuestionService;
import com.tdu.develop.resource.service.impl.QuestionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 志阳
 * @create 2019-08-26-17:52
 */
@CrossOrigin
@Controller
@RequestMapping(value="QuestionController")
public class QuestionController {
    @Autowired
    QuestionService questionService=new QuestionServiceImpl();
    
    public static String Null=null;




    @RequestMapping("seektestQuetions.action")
    @ResponseBody
    public List<Question> seektestQuetions(HttpServletRequest request){
        List<Question> list=null;
        try {
            request.setCharacterEncoding("utf-8");
            String knowledgeId = request.getParameter("knowledgeId");
            String diffcult =java.net.URLDecoder.decode(request.getParameter("difficult"),"UTF-8");
            String type = java.net.URLDecoder.decode(request.getParameter("type"),"UTF-8");
            int number = Integer.parseInt(request.getParameter("number"));
            //System.out.println("knowledgeId:"+knowledgeId+"-diffcult:"+diffcult+"-type:"+type+"-number:"+number);
            Question question = new Question();
            question.setKnowledgeId(knowledgeId);
            question.setQuestionImg(diffcult);
            question.setType(type);
            list =  questionService.seektestQuestion(question,number);
            //System.out.println(list);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping(value="GetFillblankQuestionJson.action",method={RequestMethod.POST})
    @ResponseBody
    public List GetFillblankQuestionJson(HttpServletRequest request,HttpServletResponse response){

        String knowledgeid=request.getParameter("knowledgeid");
        List<Question> rList=new ArrayList<Question>();
        rList= questionService.GetFillblankQuestionJson(knowledgeid);
        return rList;

    }

    @RequestMapping(value="GetSingleChoiceQuestionJson.action",method={RequestMethod.POST})
    @ResponseBody
    public List GetSingleChoiceQuestionJson(HttpServletRequest request,HttpServletResponse response){

        String knowledgeid=request.getParameter("knowledgeid");
        List<Question> rList=new ArrayList<Question>();
        rList= questionService.GetSingleChoiceQuestionJson(knowledgeid);
        return rList;

    }
    @RequestMapping(value="GetMulChoiceQuestionJson.action",method={RequestMethod.POST})
    @ResponseBody
    public List GetMulChoiceQuestionJson(HttpServletRequest request,HttpServletResponse response){

        String knowledgeid=request.getParameter("knowledgeid");
        List<Question> rList=new ArrayList<Question>();
        rList= questionService.GetMulChoiceQuestionJson(knowledgeid);
        return rList;

    }

    @RequestMapping(value="GetIsTureQuestionJson.action",method={RequestMethod.POST})
    @ResponseBody
    public List GetIsTureQuestionJson(HttpServletRequest request,HttpServletResponse response){

        String knowledgeid=request.getParameter("knowledgeid");
        List<Question> rList=new ArrayList<Question>();
        rList= questionService.GetIsTureQuestionJson(knowledgeid);
        return rList;

    }

    @RequestMapping(value="GetTextAreaQuestionJson.action",method={RequestMethod.POST})
    @ResponseBody
    public List GetTextAreaQuestionJson(HttpServletRequest request,HttpServletResponse response){

        String knowledgeid=request.getParameter("knowledgeid");
        List<Question> rList=new ArrayList<Question>();
        rList= questionService.GetTextAreaQuestionJson(knowledgeid);
        return rList;

    }


    @RequestMapping(value="Get3DQuestionJson.action",method={RequestMethod.POST})
    @ResponseBody
    public List Get3DQuestionJson(HttpServletRequest request,HttpServletResponse response){

        String knowledgeid=request.getParameter("knowledgeid");
        List<Question> rList=new ArrayList<Question>();
        rList= questionService.Get3DQuestionJson(knowledgeid);
        return rList;

    }

    @RequestMapping(value="GetcompreQuestionJson.action",method={RequestMethod.POST})
    @ResponseBody
    public List GetcompreQuestionJson(HttpServletRequest request,HttpServletResponse response){

        String knowledgeid=request.getParameter("knowledgeid");
        List<QuestionComprehensives> rList=new ArrayList<QuestionComprehensives>();
        rList= questionService.GetcompreQuestionJson(knowledgeid);
        return rList;

    }

    @RequestMapping(value="addKnowledge.action",method={RequestMethod.POST})
    @ResponseBody
    public void addKnowledge(HttpServletRequest request,HttpServletResponse response){



        String Content=request.getParameter("Content");


        try {
            Content = java.net.URLDecoder.decode(Content,"UTF-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String ParentKnowledge=request.getParameter("ParentKnowledge");
        String PreKnowledge=request.getParameter("PreKnowledge");
        String treeId=request.getParameter("treeId");
        if(PreKnowledge.length()==0) {
            PreKnowledge=Null;
        }
        Knowledges knownew=new Knowledges();
        knownew.setContent(Content);
        knownew.setParentKnowledge(ParentKnowledge);
        knownew.setPreKnowledge(PreKnowledge);
        knownew.setSubjectTree_Id(treeId);
        knownew.setBeforCondition("<root><beforesee></beforesee><userkey></userkey><grades></grades></root>");
        knownew.setId(UUID.randomUUID().toString());
        knownew.setImageIcons("../../../Source/imgicon/tag_orange.png");
        knownew.setKnowledgecontentId("00000000-0000-0000-0000-000000000000");
         questionService.addKnowledge(knownew);

        try {
            response.getWriter().print("{\"Key\":\"true\",\"Value\":\""+knownew.getId()+"\"}");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    @RequestMapping(value="GetPrevSourceList.action",method={RequestMethod.POST})
    @ResponseBody
    public List<PrevKnowledgeContentInfor> GetPrevSourceList(HttpServletRequest request, HttpServletResponse response){

        String selesubjectid=request.getParameter("selesubjectid");

        List<PrevKnowledgeContentInfor> item= questionService.GetPrevSourceList(selesubjectid);

        return item;
    }

}
