package com.tdu.develop.resource.controller;

import cn.hutool.system.UserInfo;
/*import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;*/
import com.tdu.develop.common.exception.JsonResult;
import com.tdu.develop.resource.pojo.*;
import com.tdu.develop.resource.service.ExamService;
import com.tdu.develop.resource.service.impl.ExamServiceImpl;
import com.tdu.develop.user.pojo.Classes;
import com.tdu.develop.user.pojo.Users;
import com.tdu.develop.user.service.UsersService;
import com.tdu.develop.user.service.impl.UserServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
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
 * @create 2019-08-27-15:31
 */
@CrossOrigin
@Controller
@RequestMapping(value = "KaoshiController")
public class KaoshiController {

    @Autowired
    ExamService examService = new ExamServiceImpl();
    @Autowired
    UsersService usersService = new UserServiceImpl();
    @Resource
    ExamService es;


    @RequestMapping(value = "getfenshu.action", method = {RequestMethod.POST})
    @ResponseBody
    public HashMap<String, String> getZongfen(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws IOException {

        HashMap<String, String> resultMap = new HashMap<String, String>();
        String pageId = request.getParameter("pageId");
        String stuId = session.getAttribute("ID").toString();
        //String KnowledgeId=request.getParameter("KnowledgeId");
        int zongfen = examService.zongfen(pageId, stuId);
        resultMap.put("fenshu", String.valueOf(zongfen));
        //System.out.println("zongfen:"+zongfen);
        return resultMap;
    }

    //查询出当前用户对应的班级，然后将班级对应去查寻所有的考试
    @RequestMapping(value = "kaoshi.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Exams> kaoshi(HttpSession session, HttpServletRequest request) throws ParseException {
        String subId = request.getParameter("subId");
        List<QuestionPagers> pagersList = examService.selSubPage(subId);
        if (pagersList.size() != 0) {
            String userId = session.getAttribute("ID").toString();
            List<String> classIdList = usersService.seleClassIdList(userId);
            List<Exams> examsList = new ArrayList<>();
            for (QuestionPagers questionPagers : pagersList) {
                //过滤所有未发布的以及超过试卷答题日期的考试以及未到达开始答题日期的试卷
                for (String classId : classIdList) {
                    Exams exams = examService.getExam(questionPagers.getId(), classId);
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


    //给试卷ID查询试卷中的所有题目，然后包装4层，返回jsonArray，方便前台解析
    @RequestMapping(value = "timu.action", method = {RequestMethod.POST})
    @ResponseBody
    public void timu(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JSONArray jsArray = new JSONArray();
        JSONArray danxuanti = new JSONArray();
        JSONArray duoxuanti = new JSONArray();
        JSONArray tiankongti = new JSONArray();
        JSONArray panduanti = new JSONArray();
        JSONArray wendati = new JSONArray();

        List<QuestionPagerContents> list = examService.timu(request.getParameter("id"));
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

    //将questionPagerContents转化为JSONObject，方便储存
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

    @RequestMapping("wenda.action")
    @ResponseBody
    public JsonResult wenda(HttpSession session, String answer, String pageId, String quesId) {
        String sessionId = session.getAttribute("ID").toString();
        examService.wendaDispose(answer, quesId, pageId, sessionId);
        return new JsonResult();
    }

    //传回答案，进行比对得出分数，然后再把分数和作答答案存进数据库
    @RequestMapping(value = "daan.action", method = {RequestMethod.POST})
    public void daan(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws DocumentException, IOException {
        String daan = request.getParameter("value");
        //System.out.println("daan:"+daan);
        String pageId = request.getParameter("pageId");
        String quesId = request.getParameter("quesId");
        QuestionPagerContents questionPagerContents = examService.daan(quesId, pageId);

        //将xml字符串转换成xml格式
        Document doc = DocumentHelper.parseText(questionPagerContents.getQuestionContent());
        //搜寻根节点
        Element rootElt = doc.getRootElement();
        //elementText指定获取根节点下面子节点中的内容
        String trueDaan = rootElt.elementText("答案");
        //System.out.println(trueDaan.contains("______"));
        //trueDaan.split("______");
        //System.out.println("答案："+trueDaan+"daan:"+daan);
        double trueFenshu = 0;//用来存得分
        int fenshu = questionPagerContents.getQuestionScore();

        if (questionPagerContents.getQuestionType().equals("填空题")) {//只允许填空题按空得分，其他题都要求必须全对
            String[] daanList = daan.split("______");
            String[] trueDaanList = trueDaan.split("______");
            int daanListL = daanList.length;
            int trueDaanListL = trueDaanList.length;
            double pingjunfen = (double) fenshu / (double) trueDaanListL;
            //计算分数
            for (int i = 0; i < daanListL; i++) {
                if (daanList[i].equals(trueDaanList[i])) {
                    trueFenshu += pingjunfen;
                    continue;
                }
            }
        } else {
            if (daan.equals(trueDaan)) {
                trueFenshu = fenshu;
            }
        }

        String stuId = session.getAttribute("ID").toString();
        String id = examService.selStuQueInfors(quesId, stuId, pageId);

        if (id == null) {
            StuQueInfors stuQueInfors = new StuQueInfors();
            stuQueInfors.setId(UUID.randomUUID().toString());
            stuQueInfors.setPagerKey(pageId);
            stuQueInfors.setQuesScore(trueFenshu);
            stuQueInfors.setQuestionAnswer(daan);
            stuQueInfors.setQuestionKey(quesId);
            stuQueInfors.setStuKey(stuId);
            examService.insStuQueInfors(stuQueInfors);

        } else {
            StuQueInfors stuQueInfors = new StuQueInfors();
            stuQueInfors.setId(id);
            stuQueInfors.setQuesScore(trueFenshu);
            stuQueInfors.setQuestionAnswer(daan);
            examService.upStuQueInfors(stuQueInfors);

        }

    }

    //交卷，然后锁定试卷
    @RequestMapping(value = "jiaojuan.action", method = {RequestMethod.POST})
    public void jiaojuan(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws IOException {
        String pageId = request.getParameter("pageId");
        String stuId = session.getAttribute("ID").toString();
        int zongfen = examService.zongfen(pageId, stuId);

        StutotalScores scores = new StutotalScores();
        scores.setId(UUID.randomUUID().toString());
        scores.setPagerKey(pageId);
        scores.setChecked(1);
        scores.setQuesScore(zongfen);
        scores.setStuKey(stuId);
        scores.setAllowExam(0);
        if (examService.insTotalScore(scores)) {
            response.getWriter().print("ture");
        }
    }


    //交卷，然后锁定试卷
    @RequestMapping(value = "jiaojuan2.action", method = {RequestMethod.POST})
    public void jiaojuan2(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws IOException {
        String pageId = request.getParameter("pageId");
        String stuId = session.getAttribute("ID").toString();
        String KnowledgeId = request.getParameter("KnowledgeId");
        int zongfen = examService.zongfen(pageId, stuId);

        StutotalScores scores = new StutotalScores();
        scores.setId(UUID.randomUUID().toString());
        scores.setPagerKey(pageId);
        scores.setChecked(1);
        scores.setQuesScore(zongfen);
        scores.setStuKey(stuId);
        scores.setAllowExam(0);
        scores.setKnowledgeId(KnowledgeId);
        if (examService.insTotalScore2(scores)) {
            response.getWriter().print("ture");
        }
    }

    //判断试卷是否做过，返回值
    @RequestMapping(value = "checked.action", method = {RequestMethod.POST})
    public void checked(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String stuKey = session.getAttribute("ID").toString();
        String pagerKey = request.getParameter("pageId");
        int checked = examService.seleChecked(pagerKey, stuKey);
        if (checked == 0) {
            response.getWriter().print("ture");
        } else {
            response.getWriter().print("error");
        }
    }

    //判断试卷是否做过，返回值
    @RequestMapping(value = "checked2.action", method = {RequestMethod.POST})
    public void checked2(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String stuKey = session.getAttribute("ID").toString();
        String pagerKey = request.getParameter("pageId");
        String KnowledgeId = request.getParameter("KnowledgeId");
        int checked = examService.seleChecked2(pagerKey, stuKey, KnowledgeId);
        if (checked == 0) {
            response.getWriter().print("ture");
        } else {
            response.getWriter().print("error");
        }
    }

    //查询所有未发布的试卷，然会给前台进行显示
    @RequestMapping(value = "seleExamAll.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<FabuNeirong> seleExamAll() {
        List<FabuNeirong> list = examService.seleExamAll();
        return list;
    }

    //发布试卷，将试卷的Anony状态改为1，加上截至日期
    @RequestMapping(value = "delExam.action", method = {RequestMethod.POST})
    public void delExam(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        String examId = request.getParameter("examId");
        examService.delExamReally(examId);
    }

    //发布试卷，将试卷的Anony状态改为1，加上截至日期
    @RequestMapping(value = "updateFabu.action", method = {RequestMethod.POST})
    public void updateFabu(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        String examId = request.getParameter("examId");
        Exams exams = new Exams();
        exams.setId(examId);
        exams.setAnony(0);
        examService.updateFabu(exams);
    }

    //发布试卷，将试卷的Anony状态改为1，加上截至日期
    @RequestMapping(value = "fabu.action", method = {RequestMethod.POST})
    public void fabu(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        String endDate = request.getParameter("endDate");
        String startDate = request.getParameter("startDate");
        String examId = request.getParameter("examId");
        String classKey = request.getParameter("classKey");
        String examTime = request.getParameter("examTime");
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(df.parse(startDate));
        String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(df.parse(endDate));
        Exams exams = new Exams();
        exams.setId(examId);
        exams.setStartTime(startTime);
        exams.setEndTime(endTime);
        exams.setClassKey(classKey);
        exams.setInvigilateKey(examTime);
        exams.setAnony(1);
        if (examService.upAnony(exams)) {
            response.getWriter().print("ture");
        }
        ;
    }

    //查询所有已发布的考试
    @RequestMapping(value = "seleExamFabu.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<FabuNeirong> seleExamFabu() {
        List<FabuNeirong> list = examService.seleExamFabu();
        return list;
    }

    //使用examId查询班级
    @RequestMapping(value = "getClassList.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Classes> getClassList(HttpServletRequest request) {
        List<Classes> list = examService.selClassKey(request.getParameter("examId"));
        return list;
    }

    //前台给classId和examPage，得到所有学生的成绩
    @RequestMapping(value = "getStuScores.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<StuScore> getStuScores(HttpServletRequest request) {
        String classId = request.getParameter("classId");
        List<Users> usersList = usersService.seleStu(classId);
        String pageId = request.getParameter("pageId");
        List<StutotalScores> scoresList = examService.seleStuScores(pageId);
        List<StuScore> list = new ArrayList<>();
        for (StutotalScores stutotalScores : scoresList) {
            for (Users users : usersList) {
                if (stutotalScores.getStuKey().equals(users.getId())) {
                    StuScore score = new StuScore();
                    score.setPagerKey(stutotalScores.getPagerKey());
                    score.setQuesScore(stutotalScores.getQuesScore());
                    score.setStuKey(users.getId());
                    score.setStuName(users.getName());
                    list.add(score);
                }
            }
        }
        return list;
    }

    //教师查看成长曲线
    @RequestMapping(value = "quxian.action", method = {RequestMethod.POST})
    public void quxian(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String classId = usersService.seleClassId(request.getParameter("stuId"));
        List<Exams> list = examService.kaoshi(classId);
        JSONArray jsArray = new JSONArray();
        for (Exams exams : list) {
            JSONObject obj = new JSONObject();
            obj.put("examName", exams.getName());
            obj.put("score", examService.seleAllScore(exams.getExamPager(), request.getParameter("stuId")));
            jsArray.add(obj);
        }
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(jsArray);
    }

    //教师查看成长曲线
    @RequestMapping(value = "quxian2.action", method = {RequestMethod.POST})
    public void quxian2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String stuId = request.getParameter("stuId");
        //String classId=usersService.seleClassId();
        //List<Exams> list=examService.kaoshi(classId);
        List<StutotalScores> list = examService.GetUserExams2(stuId);
        JSONArray jsArray = new JSONArray();
        for (StutotalScores exams : list) {
            JSONObject obj = new JSONObject();
            obj.put("examName", exams.getCustomName());
            obj.put("score", exams.getQuesScore());
            jsArray.add(obj);
        }
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(jsArray);
    }

    //学生查看成长曲线
    @RequestMapping(value = "stuSeequxian.action", method = {RequestMethod.POST})
    public void stuSeequxian(HttpServletResponse response, HttpSession session) throws IOException {
        String classId = usersService.seleClassId(session.getAttribute("ID").toString());
        List<Exams> list = examService.kaoshi(classId);
        JSONArray jsArray = new JSONArray();
        for (Exams exams : list) {
            JSONObject obj = new JSONObject();
            obj.put("examName", exams.getName());
            obj.put("score", examService.seleAllScore(exams.getExamPager(), session.getAttribute("ID").toString()));
            jsArray.add(obj);
        }
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(jsArray);
    }

    //查询试卷的考试时间
    @RequestMapping(value = "getExamTime.action", method = {RequestMethod.POST})
    @ResponseBody
    public String getExamTime(HttpServletRequest request) {
        String examPage = request.getParameter("examPage");
        String examTime = examService.getExamTime(examPage);
        return examTime;
    }

    //学生查询试卷时查出答案
    @RequestMapping(value = "seleAnswer.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<StuQueInfors> seleAnswer(HttpServletRequest request, HttpSession session) {
        String pagerKey = request.getParameter("pageId");
        String stuKey = session.getAttribute("ID").toString();
        List<StuQueInfors> list = examService.seleAnswer(pagerKey, stuKey);
        return list;
    }

    //老师查询试卷时查出答案
    @RequestMapping(value = "seleTeaAnswer.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<StuQueInfors> seleTeaAnswer(HttpServletRequest request) {
        String pagerKey = request.getParameter("pageId");
        String stuKey = request.getParameter("stuId");
        List<StuQueInfors> list = examService.seleAnswer(pagerKey, stuKey);
        return list;
    }


    //查询出当前用户对应的班级，然后将班级对应去查寻所有的考试--New
    @RequestMapping(value = "Newkaoshi.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Exams> Newkaoshi(HttpSession session, HttpServletRequest request) throws ParseException {
        String subId = request.getParameter("subId");
        String userId = session.getAttribute("ID").toString();
        String classId = usersService.seleClassId(userId);

        List<Exams> newExam = examService.selSubPageNew(subId, classId);

        if (newExam.size() != 0) {

            List<Exams> examsList = new ArrayList<>();

            for (Exams examsabc : newExam) {
                //过滤所有未发布的以及超过试卷答题日期的考试以及未到达开始答题日期的试卷


                if (examsabc != null) {
                    Date taday = new Date();
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date endDate = sdf.parse(examsabc.getEndTime());
                    Date startDate = sdf.parse(examsabc.getStartTime());
                    if (startDate.before(taday)) {
                        if (taday.before(endDate)) {
                            examsList.add(examsabc);
                        }
                    }
                }
            }
            return examsList;
        } else {
            return null;
        }
    }


    @RequestMapping(value = "Newtimu.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Questiondbs> Newtimu(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Questiondbs> result = new ArrayList<Questiondbs>();
        String pageId = examService.seleExamPagerById(request.getParameter("id"));
        List<Questiondbs> list = examService.selectQuesidByOrder(pageId);
        for (Questiondbs questiondbs : list) {
            List<Questiondbs> list2 = examService.selectQuesbyid(questiondbs.getId());
            if (list2.size() < 1) {
                List<PagerTempCompreContents> list3 = examService.selectzongheQuesbyid(questiondbs.getId());
                Questiondbs questiondbs2 = new Questiondbs();
                questiondbs2.setId(list3.get(0).getId());
                questiondbs2.setAnswer(list3.get(0).getQuestionKeyArray());
                questiondbs2.setQuestionType("综合题");
                questiondbs2.setQuestionStem(list3.get(0).getQuestionStem());
                questiondbs2.setScore(list3.get(0).getScore());
                result.add(questiondbs2);

            } else {
                result.addAll(list2);
            }

        }
        return result;
    }

    @RequestMapping(value = "getchildrentimu.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Questiondbs> getchildrentimu(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Questiondbs> list = examService.selectQuesbyid(request.getParameter("id"));
        return list;
    }

    /**
     * 普通题的处理
     *
     * @param quesId
     * @param pageId
     * @param value
     */
    @RequestMapping("answerDispose.action")
    @ResponseBody
    public String answerDispose(HttpServletRequest request, String quesId, String pageId, String value) {
        String sessionId = (String) request.getSession().getAttribute("ID");
        es.answerDispose(quesId, pageId, value, sessionId);
        String result = "true";
        return result;
    }

    @RequestMapping("getStartAndEndTime.action")
    @ResponseBody
    public List<String> getStartAndEndTime(String pageId) {
        List<String> result = es.getStartAndEndTime(pageId);
        return result;
    }

    @RequestMapping("getStuId.action")
    @ResponseBody
    public JsonResult getStuId(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("ID");
        return new JsonResult(userId);
    }
}
