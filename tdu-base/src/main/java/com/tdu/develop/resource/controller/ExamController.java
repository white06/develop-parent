package com.tdu.develop.resource.controller;

import com.tdu.develop.resource.pojo.*;
import com.tdu.develop.resource.service.ExamService;
import com.tdu.develop.resource.service.impl.ExamServiceImpl;
import com.tdu.develop.user.pojo.Classes;
import com.tdu.develop.user.pojo.Users;
import com.tdu.develop.user.service.UsersService;
import com.tdu.develop.user.service.impl.UserServiceImpl;
import net.sf.json.JSONArray;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 志阳
 * @create 2019-08-27-9:07
 */
@CrossOrigin
@Controller
@RequestMapping(value = "ExamController")
public class ExamController {
    @Autowired
    ExamService examService = new ExamServiceImpl();
    @Autowired
    UsersService usersService = new UserServiceImpl();


    //组卷
    @RequestMapping(value = "updateExam.action", method = {RequestMethod.POST})
    public void updateExam(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //转换时间
        Date date = new Date();//获得系统时间.
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);//将时间格式转换成符合Timestamp要求的格式.

        String allJSON = request.getParameter("allJSON");
        String juanming = request.getParameter("juanming");//试卷名
        String examId = request.getParameter("examId");// ExamId
        String questionPage_id = examService.getPageKey(examId);
        //第一步，updage试卷
        QuestionPagers questionPagers = new QuestionPagers();
        questionPagers.setId(questionPage_id);
        questionPagers.setName(juanming);
        questionPagers.setUseAble(0);
        questionPagers.setCreateData(nowTime);
        if (examService.updatePage(questionPagers)) {
            examService.delPageQuestions(questionPage_id);
            //第二步，将试题加载入试卷
            //将字符串转换为JSONArray
            JSONArray array = JSONArray.fromObject(allJSON);
            //循环获取JSON数组之中的每一个对象
            for (int i = 0; i < array.size(); i++) {
                Question question = examService.chaxun(array.get(i).toString());
                QuestionPagerContents questionPagerContents = new QuestionPagerContents();
                questionPagerContents.setId(UUID.randomUUID().toString());
                questionPagerContents.setQuestionKey(question.getId());
                questionPagerContents.setQuestionContent(question.getContent());
                questionPagerContents.setQuestionIndex(i);
                questionPagerContents.setQuestionScore(question.getFenshu());
                questionPagerContents.setQuestionType(question.getType());
                questionPagerContents.setQuestionPager_Id(questionPage_id);
                examService.inquertionPagerContent(questionPagerContents);
            }
            //第三步，把试卷添加进考试
            /*Exams exams=new Exams();
            exams.setId(UUID.randomUUID().toString());
            exams.setName(juanming);
            exams.setExamPager(questionPage_id);
            exams.setAnony(0);
            if(examService.inExams(exams)){
                response.getWriter().print("success");
                return;
            }*/

        }
    }


    @RequestMapping(value = "getPageQue.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Question> getPageQue(HttpServletRequest request) {

        List<Question> list = new ArrayList<>();

        String examId = request.getParameter("examId");
        list = examService.getPageQue(examId);
        return list;
    }


    @RequestMapping(value = "GetPagerName.action", method = {RequestMethod.GET}, produces = {"application/json;", "text/html;charset=UTF-8;"})
    @ResponseBody
    public HashMap<String, String> GetPagerName(String id, HttpServletResponse response) {
        String name = examService.GetPagerName2(id);
        HashMap<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("result", name);
        //System.out.println(name);
        response.setContentType("text/html;charset=UTF-8");
        return resultMap;
    }

    @RequestMapping(value = "GetTimu.action", method = {RequestMethod.GET})
    @ResponseBody
    public List<Question> GetTimu(String id) {

        List<Question> list = examService.GetTimu(id);
        return list;
    }

    @RequestMapping(value = "GetUserExams.action", method = {RequestMethod.GET})
    @ResponseBody
    public List<StutotalScores> GetUserExams() {

        return examService.GetUserExams();
    }

    @RequestMapping(value = "GetUserExams2.action", method = {RequestMethod.GET})
    @ResponseBody
    public List<StutotalScores> GetUserExams2(HttpServletRequest request) {
        String sessionId = (String) request.getSession().getAttribute("ID");
        return examService.GetUserExams2(sessionId);
    }


    @RequestMapping(value = "delPager.action", method = {RequestMethod.GET})
    @ResponseBody
    public Boolean delPager(String id) {
        Boolean flag = false;
        if (examService.delPager(id)) {
            flag = true;
            return flag;
        }
        return flag;
    }

    //不需要递归地查询出科目之下的所有子目录
    @RequestMapping(value = "seleAllKnow.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Knowledges> seleAllKnow(HttpServletRequest request) {
        List<Knowledges> list = new ArrayList<>();
        list = examService.seleAllKnow(request.getParameter("knowledgeId"));
        return list;
    }

    //按条件查询出随机的试题
    @RequestMapping(value = "chajuan.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Question> chajuan(HttpServletRequest request) throws UnsupportedEncodingException {
        List<Question> list = new ArrayList<>();

        String knowledgeId = request.getParameter("knowledgeId");
        String type = URLDecoder.decode(request.getParameter("type"), "UTF-8");
        String nandu = URLDecoder.decode(request.getParameter("nandu"), "UTF-8");
        String fenye = request.getParameter("fenye");
        list = examService.chajuan(knowledgeId, type, nandu, fenye);
        return list;
    }

    //删除试题
    @RequestMapping(value = "delExam.action", method = {RequestMethod.POST})
    public void delExam(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");

        boolean flag = examService.delExam(id);
        if (flag) {
            try {
                response.getWriter().print(flag);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //修改试题
    @RequestMapping(value = "updatExam.action", method = {RequestMethod.POST})
    @ResponseBody
    public String updatExam(HttpServletRequest request, HttpServletResponse response, @Param("content") String content, @Param("id") String id, @Param("title") String title, @Param("nandu") String nandu, @Param("fenshu") int fenshu) {
        Question question = new Question();
        //System.out.println(nandu+fenshu+"修改question");
        question.setContent(content);
        question.setTitle(title);
        question.setId(id);

        question.setQuestionImg(nandu);
        question.setFenshu(fenshu);
        //转换时间
        Date date = new Date();//获得系统时间.
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);//将时间格式转换成符合Timestamp要求的格式.
        question.setTime(nowTime);

        Boolean flag = examService.updatExam(question);
        String succe = "ture";
        if (flag) {
            return succe;
        }
        return null;
    }

    //查询试题
    @RequestMapping(value = "selexam.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Question> selexam(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        List<Question> list = new ArrayList<>();

        String knowledgeId = request.getParameter("knowledgeId");
        String type = request.getParameter("type");
        String type1 = URLDecoder.decode(type, "UTF-8");

        list = examService.selexam(knowledgeId, type1);

        return list;
    }

    //偷偷把图片存起来然后把图片地址还给前台
    @RequestMapping(value = "examup.action", method = {RequestMethod.POST})
    @ResponseBody
    public String examup(@Param("fileup") MultipartFile fileup, @Param("filePath") String filePath, HttpServletResponse response) throws IllegalStateException, IOException {
        if (!fileup.isEmpty()) {
            //对存储文件的路径预处理
            String fileWay = "QZ/examFile/";
            StringBuffer stringBuffer = new StringBuffer();
            StringBuffer stringBuffer2 = new StringBuffer();
            String fileName = fileup.getOriginalFilename();
            String fileEnd = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            String uuid = UUID.randomUUID().toString();
            stringBuffer.append(filePath).append(fileWay).append(uuid).append(".").append(fileEnd);
            File file = new File(stringBuffer.toString());
            //转存
            fileup.transferTo(file);
            //返回存储的图片的名字
            stringBuffer2.append(uuid).append(".").append(fileEnd);
            return stringBuffer2.toString();
        }
        return null;
    }

    //新增试题
    @RequestMapping(value = "zuhe.action", method = {RequestMethod.POST})
    @ResponseBody
    public String zuhe(@Param("content") String content, @Param("knowledgeId") String knowledgeId, @Param("title") String title, @Param("type") String type, @Param("nandu") String nandu, @Param("fenshu") int fenshu) {
        Question question = new Question();

        String uuid = UUID.randomUUID().toString();

        question.setId(uuid);
        question.setContent(content);
        question.setKnowledgeId(knowledgeId);
        question.setTitle(title);
        question.setType(type);
        question.setQuestionImg(nandu);
        question.setFenshu(fenshu);
        //转换时间
        Date date = new Date();//获得系统时间.
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);//将时间格式转换成符合Timestamp要求的格式.
        question.setTime(nowTime);

        //执行添加题目
        Boolean flag = examService.inexam(question);
        String succ = null;
        if (flag == true) {
            //执行成功返回
            succ = "ture";
        }

        return succ;
    }

    //组卷
    @RequestMapping(value = "zujuan.action", method = {RequestMethod.POST})
    public void zujuan(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //转换时间
        Date date = new Date();//获得系统时间.
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);//将时间格式转换成符合Timestamp要求的格式.

        String allJSON = request.getParameter("allJSON");
        String juanming = request.getParameter("juanming");//试卷名
        String juanleixing = request.getParameter("juanleixing");//试卷类型
        String subject = request.getParameter("subject");//试卷所属科目
        String questionPage_id = UUID.randomUUID().toString();
        //第一步，先添加一张试卷
        QuestionPagers questionPagers = new QuestionPagers();
        questionPagers.setId(questionPage_id);
        questionPagers.setName(juanming);
        questionPagers.setUseAble(0);
        questionPagers.setCreateData(nowTime);
        questionPagers.setSubjectKey(subject);
        questionPagers.setLeixing(juanleixing);
        if (examService.inquestionPage(questionPagers)) {
            //第二步，将试题加载入试卷
            //将字符串转换为JSONArray
            JSONArray array = JSONArray.fromObject(allJSON);
            //循环获取JSON数组之中的每一个对象
            for (int i = 0; i < array.size(); i++) {
                Question question = examService.chaxun(array.get(i).toString());
                QuestionPagerContents questionPagerContents = new QuestionPagerContents();
                questionPagerContents.setId(UUID.randomUUID().toString());
                questionPagerContents.setQuestionKey(question.getId());
                questionPagerContents.setQuestionContent(question.getContent());
                questionPagerContents.setQuestionIndex(i);
                questionPagerContents.setQuestionScore(question.getFenshu());
                questionPagerContents.setQuestionType(question.getType());
                questionPagerContents.setQuestionPager_Id(questionPage_id);
                examService.inquertionPagerContent(questionPagerContents);
            }
            //第三步，把试卷添加进考试
            Exams exams = new Exams();
            exams.setId(UUID.randomUUID().toString());
            exams.setName(juanming);
            exams.setExamPager(questionPage_id);
            exams.setAnony(0);
            if (examService.inExams(exams)) {
                response.getWriter().print("success");
                return;
            }

        }
    }

    //查询单条题目,点击修改按钮时就执行
    @RequestMapping(value = "chaxun.action", method = {RequestMethod.POST})
    @ResponseBody
    public Question chaxun(HttpServletResponse response, HttpServletRequest request) {
        String id = request.getParameter("id");

        Question question = examService.chaxun(id);

        return question;
    }

    @RequestMapping("seektestQuetions.action")
    @ResponseBody
    public List<SeekResult> seektestQuetions(HttpServletRequest request) {
        List<SeekResult> list = null;
        try {
            request.setCharacterEncoding("utf-8");
            String knowledgeId = request.getParameter("knowledgeId");
            String diffcult = URLDecoder.decode(request.getParameter("difficult"), "UTF-8");
            String type = URLDecoder.decode(request.getParameter("type"), "UTF-8");
            int number = Integer.parseInt(request.getParameter("number"));
            //System.out.println("knowledgeId:"+knowledgeId+"-diffcult:"+diffcult+"-type:"+type+"-number:"+number);
            Questiondbs questiondbs = new Questiondbs();
            questiondbs.setKnowledgeId(knowledgeId);
            questiondbs.setDiffcult(diffcult);
            questiondbs.setQuestionType(type);
            list = examService.seektestQuestion(questiondbs, number);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return list;
    }

    @RequestMapping("submitTestPaper.action")
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
            result = examService.submitTestPaper(id, testId, questionType, order, score);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("submitTest.action")
    @ResponseBody
    public String submitTest(HttpServletRequest request) {
        String testName = request.getParameter("testName");
        String sessionId = (String) request.getSession().getAttribute("ID");
        String result = examService.submitTest(testName, sessionId);
        return result;
    }

    @RequestMapping("GetPagers.action")
    @ResponseBody
    public List<PagerTemps> GetPagers(HttpServletRequest request) {
        List<PagerTemps> list = examService.selectExamsName();
        return list;
    }

    @RequestMapping("getExamsBysubjectId.action")
    @ResponseBody
    public List<TestList> getExamsBysubjectId(HttpServletRequest request) {
        String sessionId = (String) request.getSession().getAttribute("ID");
        List<TestList> list = examService.selectExams(request.getParameter("subjectId"), sessionId);
        return list;
    }

    @RequestMapping("GetTeacherUsers.action")
    @ResponseBody
    public List<Users> getTeacherUsers() {
        List<Users> list = examService.getTeacherUsers();
        return list;
    }

    @RequestMapping("GetClasses.action")
    @ResponseBody
    public List<Classes> getClasses() {
        List<Classes> list = examService.getClasses();
        return list;
    }

    @RequestMapping("submitPagerTempTest.action")
    @ResponseBody
    public String submitPagerTempTest(String testtype, String id, String subjectid, String testname, String testpagerid, String teststarttime, String testendtime, String invigilateid, String correctingid, String testclassid) {
        Exams exams = new Exams();
        exams.setName(testname);
        exams.setExamPager(testpagerid);
        exams.setExamType(testtype);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String result = "faild";
        try {
            exams.setStartTime(sdf.format(sdf2.parse(teststarttime)));
            exams.setEndTime(sdf.format(sdf2.parse(testendtime)));
            exams.setInvigilateKey(invigilateid);
            exams.setCheckKey(correctingid);
            exams.setClassKey(testclassid);
            exams.setSubjectkey(subjectid);
            result = examService.insertExams(exams, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("getExamByExamId.action")
    @ResponseBody
    public Exams getExamByExamId(String examId) {
        Exams exams = null;
        try {
            exams = examService.getExamsByExamsId(examId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exams;
    }

    /**
     * 获取发布考试信息
     *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "getPages.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<QuestionPagers> getPages(HttpServletRequest request) throws UnsupportedEncodingException {

        String subjectId = request.getParameter("subjectid");
        return examService.getPages(subjectId);
    }
}
