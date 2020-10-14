package com.tdu.develop.resource.controller;

import com.tdu.develop.resource.pojo.QuestionPagers;
import com.tdu.develop.resource.service.QuestionPagerService;
import com.tdu.develop.resource.service.impl.QuestionPagerServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-27-14:54
 */
@CrossOrigin
@Controller
@RequestMapping(value = "QuestionPagerController")
public class QuestionPagerController {
    @Resource
    QuestionPagerService questionPagerService = new QuestionPagerServiceImpl();
    public static String Null = null;

    @RequestMapping("delchengjiandanswer.action")
    @ResponseBody
    public Boolean DelQuesPagerstoAndAnswer(HttpServletRequest request) {
        //String qusetionkey = request.getParameter("qusetionkey");
        //String sessionId = (String) request.getSession().getAttribute("ID");
        String pageId = request.getParameter("id");
        //System.out.println("pageId:-----"+pageId);
        Boolean result = questionPagerService.DelPagerStoAndeAnswer(pageId);
        return result;
    }

    @RequestMapping("DelQuesPagerContent.action")
    @ResponseBody
    public Boolean DelQuesPagerContent(HttpServletRequest request) {
        String qusetionkey = request.getParameter("qusetionkey");
        //String sessionId = (String) request.getSession().getAttribute("ID");
        String pageId = request.getParameter("pageId");
        //System.out.println("qusetionkey:----"+qusetionkey+"pageId:-----"+pageId);
        Boolean result = questionPagerService.DelQuesPagerContent(pageId, qusetionkey);
        return result;
    }

    @RequestMapping(value = "delPager.action", method = {RequestMethod.GET})
    @ResponseBody
    public Boolean delPager(String id) {
        Boolean flag = false;
        if (questionPagerService.delPager(id)) {
            flag = true;
            return flag;
        }
        return flag;
    }

    @RequestMapping(value = "GetPagers.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<QuestionPagers> GetQuestionPagers(/*int page,int rowCount*/) {
        List<QuestionPagers> rList = new ArrayList<QuestionPagers>();
        rList = questionPagerService.GetQuestionPagers(/*page,rowCount*/);
        return rList;

    }

    @RequestMapping("submitPaperQuest.action")
    @ResponseBody
    public String submitTestPaper(HttpServletRequest request) {
        String questionType;
        String result = "false";
        try {
            questionType = URLDecoder.decode(request.getParameter("questionType"), "utf-8");
            int score = Integer.parseInt(request.getParameter("qScore"));
            int order = Integer.parseInt(request.getParameter("qOrder"));
            String id = request.getParameter("datainputId");
            String testId = request.getParameter("pagerTempId");
            //System.out.println("id:    "+id+"  testId    :"+testId+" questionType    :"+questionType+"  order    :"+order+"  score    :"+score);
            result = questionPagerService.submitTestPaper(id, testId, questionType, order, score);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("submitPager.action")
    @ResponseBody
    public String submitTest(HttpServletRequest request) {
        String testName = request.getParameter("testName");
        String sessionId = (String) request.getSession().getAttribute("ID");
        String subjectKey = request.getParameter("subjectKey");
        String result = questionPagerService.submitTest(testName, sessionId, subjectKey);
        return result;
    }

    @RequestMapping("updatePager.action")
    @ResponseBody
    public int submitUpdateTest(HttpServletRequest request) {
        String testName = request.getParameter("testName");
        //String sessionId = (String) request.getSession().getAttribute("ID");
        String pagerId = request.getParameter("id");
        int result = questionPagerService.updatePager(testName, pagerId);
        return result;
    }

    //创建卷子（灵活组卷与固定题目组卷 二种类型），灵活组卷又分为包含错题库，不包含错题库，灵活组卷情况下每个人的试题题目不一样（每次进去也不一样，因此这种类型的试题，不会断电保护上次作答，此种方式可以防止学生相互抄袭）

    //添加试题到试卷中（分数与试题顺序，保存试题ID ，并复制试题题干等内容到试卷内容表）

    //考试、练习 与试卷一一绑定，记录试卷ID 适用班级，考试时间，练习时间等；

    //返回给前端试卷试题JSON，用户前端还原试题界面

    //根据试题ID 判断答案，并将结果记录到操作记录表（用于统计分数，还原上次作答使用），如果试题作答错误，记录到错题表中，用于后面的错题练习库

    //提交练习试卷（记录多次作答分数），考试（只记录最后一次作答分数）

    //根据分数生成练习的成长曲线，考试的成长曲线

    //随机返回一定数量的错题，随机试题提交就产生成绩，新加表格记录所有的随机类型试题成绩，并有平时练习的成长曲线

    //成长曲线的分析方式需要在以前只有成绩分数曲线基础上，根据知识点的错误情况，分班等多种情况统计多种曲线。
}
