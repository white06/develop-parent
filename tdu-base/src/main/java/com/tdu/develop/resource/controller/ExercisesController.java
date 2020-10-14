package com.tdu.develop.resource.controller;

import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.Knowlegcontent;
import com.tdu.develop.resource.pojo.Question;
import com.tdu.develop.resource.pojo.QuestionPersonal;
import com.tdu.develop.resource.service.ExercisesService;
import com.tdu.develop.resource.service.impl.ExercisesServiceImpl;
import com.tdu.develop.user.pojo.Subjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 志阳
 * @create 2019-08-27-17:34
 */
@CrossOrigin
@Controller
@RequestMapping(value = "ExercisesController")
public class ExercisesController {
    @Autowired
    private ExercisesService exercisesService = new ExercisesServiceImpl();

    /*
     * 根据知识点获取所有题目
     */
    @RequestMapping(value = "getQuestion.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Question> getQuestion(HttpServletRequest request, HttpSession session) {
        String knowId = request.getParameter("knowId");
        return exercisesService.getQuestion(knowId);
    }

    /*
     * 获取随机题的题目
     */
    @RequestMapping(value = "getRandomQuestion.action", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getRandomQuestion(HttpServletRequest request, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String knowId = request.getParameter("knowId");
        String contId = exercisesService.getKnowContentId(knowId);
        Knowlegcontent knowlegcontent = exercisesService.getKnowContent(contId);
        String nmae = knowlegcontent.getNmae();
        String type = "";
        if (nmae.indexOf("userShoucangId") != -1) {
            type = "收藏";
            List<QuestionPersonal> shoucangList = exercisesService.perQuestion(knowlegcontent, type);
            resultMap.put("1", shoucangList);
        } else if (nmae.indexOf("userErrorId") != -1) {
            type = "错题";
            List<QuestionPersonal> errorList = exercisesService.perQuestion(knowlegcontent, type);
            resultMap.put("1", errorList);
        } else {
            List<Question> randomList = exercisesService.getRandomQuestion(knowlegcontent);
            resultMap.put("1", randomList);
        }
        return resultMap;
    }

    /**
     * 获取所有科目
     *
     * @return
     */
    @RequestMapping(value = "getAllSub.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Subjects> getAllSub() {

        return exercisesService.getAllSub();
    }

    /**
     * 根据subId获取对应的知识点
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "getKnowledge.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Knowledges> getKnowledge(HttpServletRequest request, HttpSession session) {
        String subId = request.getParameter("subid");
        return exercisesService.getKnowledge(subId);
    }

    /**
     * 根据parentId获取对应的知识点
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "getKnowledgeBy.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Knowledges> getKnowledgeBy(HttpServletRequest request, HttpSession session) {
        String parentId = request.getParameter("parentId");
        return exercisesService.getKnowledgeBy(parentId);
    }

    /**
     * 获取知识点的全部题
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "getAllQuestion.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Question> getAllQuestion(HttpServletRequest request, HttpSession session) {
        String knowId = request.getParameter("knowId");
        List<Question> allList = exercisesService.getAllQuestion(knowId);
        return allList;
    }

    /**
     * 根据科目id获取首节点信息
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "getFirstRoot.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Knowledges> getFirstRoot(HttpServletRequest request, HttpSession session) {
        String subId = request.getParameter("subId");
        return exercisesService.getFirstRoot(subId);
    }

    /**
     * 根据subId获取对应的知识点(资源管理指定知识点)
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "getKnowledgeZtree.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Object> getKnowledgeZtree(HttpServletRequest request, HttpSession session) {
        String subId = request.getParameter("subid");
        return exercisesService.getKnowledgeZtree(subId);
    }

    /**
     * 添加收藏题目
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "addPersonal.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean addPersonal(HttpServletRequest request, HttpSession session) {
        String userId = (String) session.getAttribute("ID");
        String timuId = request.getParameter("timuId");
        String useType = request.getParameter("useType");
        return exercisesService.addPersonal(timuId, useType, userId);
    }

    /**
     * 获取用户id
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "getUserId.action", method = {RequestMethod.POST})
    @ResponseBody
    public String getUserId(HttpServletRequest request, HttpSession session) {
        return (String) session.getAttribute("ID");
    }

    /**
     * 根据个人题目id从个人题目表中删除
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "deleteShoucang.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean deleteShoucang(HttpServletRequest request, HttpSession session) {
        String timuId = request.getParameter("timuId");
        return exercisesService.deleteShoucang(timuId);
    }

}
