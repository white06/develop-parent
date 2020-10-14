package com.tdu.develop.resource.controller;

import com.tdu.develop.resource.pojo.*;
import com.tdu.develop.resource.service.ExamService;
import com.tdu.develop.resource.service.ExercisesService;
import com.tdu.develop.resource.service.impl.ExamServiceImpl;
import com.tdu.develop.resource.service.impl.ExercisesServiceImpl;
import com.tdu.develop.user.service.SubjectService;
import com.tdu.develop.user.service.UsersService;
import com.tdu.develop.user.service.impl.SubjectServiceImpl;
import com.tdu.develop.user.service.impl.UserServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 志阳
 * @create 2019-11-12-15:03
 */
@CrossOrigin
@Controller
@RequestMapping(value = "PhoneExamController")
public class PhoneExamController {
    @Autowired
    private ExercisesService ecs = new ExercisesServiceImpl();
    @Autowired
    ExamService es = new ExamServiceImpl();
    @Autowired
    ExamService examServiceImp = new ExamServiceImpl();
    @Autowired
    UsersService usersServiceImp = new UserServiceImpl();
    @Autowired
    SubjectService ss = new SubjectServiceImpl();

    /**
     * 针对单一科目实现。移动端
     *
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "seleExams.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Exams> seleExams(HttpSession session, HttpServletRequest request) throws ParseException {
        String userId = session.getAttribute("ID").toString();

        //南靖项目写死
        //String subId=ss.getSubId(userId);
        String subId = "086e9c77-8f33-4b0a-b3a6-39f138f512a0";
        List<QuestionPagers> pagersList = examServiceImp.selSubPage(subId);

        if (pagersList.size() != 0) {

            List<String> classIdList = usersServiceImp.seleClassIdList(userId);
            List<Exams> examsList = new ArrayList<>();

            for (QuestionPagers questionPagers : pagersList) {
                //过滤所有未发布的以及超过试卷答题日期的考试以及未到达开始答题日期的试卷
                for (String classId : classIdList) {
                    Exams exams = examServiceImp.getExam(questionPagers.getId(), classId);

                    if (exams != null) {
                        Date taday = new Date();
                        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date endDate = sdf.parse(exams.getEndTime());
                        Date startDate = sdf.parse(exams.getStartTime());
                        if (startDate.before(taday)) {
                            if (taday.before(endDate)) {
                                examsList.add(exams);
                            }
                        }
                    }
                }


            }
            examsList = removeDupliById(examsList);
            return examsList;
        } else {
            return null;
        }
    }


    /**
     * 根据对象属性去重  属性：userId
     *
     * @param persons
     * @return
     */
    public static List<Exams> removeDupliById(List<Exams> persons) {
        Set<Exams> personSet = new TreeSet<>((o1, o2) -> o1.getId().compareTo(o2.getId()));
        personSet.addAll(persons);
        return new ArrayList<>(personSet);
    }

    /**
     * 移动端，获取考试题目，返回值json格式。给试卷ID查询试卷中的所有题目，然后包装4层，返回jsonArray，方便前台解析
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "timu.action", method = {RequestMethod.POST})
    @ResponseBody
    public void timu(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONArray jsArray = new JSONArray();
        JSONArray danxuanti = new JSONArray();
        JSONArray duoxuanti = new JSONArray();
        JSONArray tiankongti = new JSONArray();
        JSONArray panduanti = new JSONArray();
        JSONArray wendati = new JSONArray();

        List<QuestionPagerContents> list = examServiceImp.timu(request.getParameter("id"));
        for (QuestionPagerContents questionPagerContents : list) {
            if (questionPagerContents.getQuestionType().equals("单选题")) {
                danxuanti.add(getQuestionPagerContents(questionPagerContents));
            } else if (questionPagerContents.getQuestionType().equals("多选题")) {
                duoxuanti.add(getQuestionPagerContents(questionPagerContents));
            } else if (questionPagerContents.getQuestionType().equals("填空题")) {
                tiankongti.add(getQuestionPagerContents(questionPagerContents));
            } else if (questionPagerContents.getQuestionType().equals("判断题")) {
                panduanti.add(getQuestionPagerContents(questionPagerContents));
            } else if (questionPagerContents.getQuestionType().equals("问答题")) {
                wendati.add(getQuestionPagerContents(questionPagerContents));
            }
        }

        if (danxuanti.size() != 0) {
            JSONObject o = new JSONObject();
            o.put("type", "单选题");
            o.put("neirong", danxuanti.toString());
            o.put("size", danxuanti.size());
            jsArray.add(o);
        }
        if (duoxuanti.size() != 0) {
            JSONObject o = new JSONObject();
            o.put("type", "多选题");
            o.put("neirong", duoxuanti.toString());
            o.put("size", duoxuanti.size());
            jsArray.add(o);
        }
        if (tiankongti.size() != 0) {
            JSONObject o = new JSONObject();
            o.put("type", "填空题");
            o.put("neirong", tiankongti.toString());
            o.put("size", tiankongti.size());
            jsArray.add(o);
        }
        if (panduanti.size() != 0) {
            JSONObject o = new JSONObject();
            o.put("type", "判断题");
            o.put("neirong", panduanti.toString());
            o.put("size", panduanti.size());
            jsArray.add(o);
        }
        if (wendati.size() != 0) {
            JSONObject o = new JSONObject();
            o.put("type", "问答题");
            o.put("neirong", wendati.toString());
            o.put("size", wendati.size());
            jsArray.add(o);
        }
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(jsArray);
    }

    /**
     * 移动端，将questionPagerContents转化为JSONObject，方便储存
     *
     * @param questionPagerContents
     * @return
     */
    public JSONObject getQuestionPagerContents(QuestionPagerContents questionPagerContents) {
        JSONObject o = new JSONObject();
        o.put("id", questionPagerContents.getId());
        o.put("questionKey", questionPagerContents.getQuestionKey());
        o.put("questionContent", questionPagerContents.getQuestionContent());
        o.put("questionIndex", questionPagerContents.getQuestionIndex());
        o.put("questionScore", questionPagerContents.getQuestionScore());
        o.put("questionType", questionPagerContents.getQuestionType());
        o.put("questionPager_Id", questionPagerContents.getQuestionPager_Id());
        return o;
    }

    /**
     * 移动端，提交考卷成绩存储后台
     *
     * @param request
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping(value = "getScores.action", method = {RequestMethod.POST})
    @ResponseBody
    public void getScores(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        String userId = session.getAttribute("ID").toString();
        String pageId = request.getParameter("pageId");
        int scores = Integer.parseInt(request.getAttribute("scores").toString());
        StutotalScores userScores = new StutotalScores();
        userScores.setId(UUID.randomUUID().toString());
        userScores.setPagerKey(pageId);
        userScores.setChecked(1);
        userScores.setQuesScore(scores);
        userScores.setStuKey(userId);
        userScores.setAllowExam(0);
        if (examServiceImp.insTotalScore(userScores)) {
            response.getWriter().print("ture");
        }
    }

    /**
     * 移动端随机题(全部章节）
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "getRandomQuestion.action", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getRandomQuestion(HttpServletRequest request, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String knowId = request.getParameter("knowId");
        String contId = ecs.getKnowContentId(knowId);
        Knowlegcontent knowlegcontent = ecs.getKnowContent(contId);
        String nmae = knowlegcontent.getNmae();
        String type = "";
        if (nmae.indexOf("userShoucangId") != -1) {
            type = "收藏";
            List<QuestionPersonal> shoucangList = ecs.perQuestion(knowlegcontent, type);
            resultMap.put("1", shoucangList);
        } else if (nmae.indexOf("userErrorId") != -1) {
            type = "错题";
            List<QuestionPersonal> errorList = ecs.perQuestion(knowlegcontent, type);
            resultMap.put("1", errorList);
        } else {
            List<Question> randomList = ecs.getRandomQuestion(knowlegcontent);
            resultMap.put("1", randomList);
        }
        return resultMap;
    }

    /**
     * 移动端随机题（全部章节）
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "getAllQuestion.action", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getAllQuestion(HttpServletRequest request, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String knowId = request.getParameter("knowId");
        String chooseId = request.getParameter("chooseId");
        String contId = ecs.getKnowContentId(knowId);
        Knowlegcontent knowlegcontent = ecs.getKnowContent(contId);
        String nmae = knowlegcontent.getNmae();
        String type = "";
        if (nmae.indexOf("userShoucangId") != -1) {
            type = "收藏";
            List<QuestionPersonal> shoucangList = ecs.perQuestion(knowlegcontent, type);
            resultMap.put("1", shoucangList);
        } else if (nmae.indexOf("userErrorId") != -1) {
            type = "错题";
            List<QuestionPersonal> errorList = ecs.perQuestion(knowlegcontent, type);
            resultMap.put("1", errorList);
        } else {
            List<Question> randomList = ecs.getAllQuestion(knowlegcontent, chooseId);
            resultMap.put("1", randomList);
        }
        return resultMap;
    }

    /**
     * 提交成绩
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "submit.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean submit(HttpServletRequest request, HttpSession session) {
        String examId = request.getParameter("examId");
        String allscore = request.getParameter("allscore");
        String userId = session.getAttribute("ID").toString();
        String id = UUID.randomUUID().toString();
        return ecs.submit(userId, allscore, examId, id);
    }

    /**
     * 添加错题库
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "addError.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean addError(HttpServletRequest request, HttpSession session) {
        String userId = (String) session.getAttribute("ID");
        String timuId = request.getParameter("timuId");
        String useType = request.getParameter("useType");
        return ecs.addError(timuId, useType, userId);
    }

    /**
     * 移动端随机题（全部章节）
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "getQuestionAll.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Question> getQuestionAll(HttpServletRequest request, HttpSession session) {
        String knowId = request.getParameter("chooseId");
        List<Question> allList = ecs.getAllQuestion(knowId);
        return allList;
    }
}
