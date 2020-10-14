package com.tdu.develop.resource.service.impl;

import com.tdu.develop.common.exception.ServiceException;
import com.tdu.develop.resource.mapper.*;
import com.tdu.develop.resource.pojo.*;
import com.tdu.develop.resource.service.ExamService;
import com.tdu.develop.user.mapper.ClassMapper;
import com.tdu.develop.user.pojo.Classes;
import com.tdu.develop.user.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 志阳
 * @create 2019-08-27-9:09
 */
@Service
public class ExamServiceImpl implements ExamService {
    /**
     * 组卷的默认状态
     */
    public final static String PagerTempStatus = "1";
    /**
     * 组卷的默认状态
     */
    public final static String useAble = "1";
    /**
     * 综合题的类型
     */
    public final static String QuestionType = "综合题";
    public final static String CompletionType = "填空题";
    public final static String ChoiceType = "单选题";
    public final static String MulitipleChoiceType = "多选题";
    public final static String CheckingType = "判断题";
    public final static String EssayQuestionType = "问答题";
    public final static String simulationsType = "3D仿真实验题";
    @Autowired
    ExercisesMapper exercisesMapper;
    @Autowired
    ExamMapper examMapper;

    @Autowired
    KnowledgesMapper knowledgesMapper;

    @Autowired
    StuQueInforsMapper stuQueInforsMapper;

    @Autowired
    PagerTempContentsMapper pagerTempContentsMapper;

    @Autowired
    PagerTempsMapper pagerTempsMapper;

    @Autowired
    ClassMapper classMapper;

    /**
     * 获取已组卷试卷的题目
     *
     * @param id
     * @return
     */
    public String GetPagerName2(String id) {


        return examMapper.getPagerName2(id);
    }

    /**
     * 获取已组卷试卷的题目
     *
     * @param id
     * @return
     */
    public List<Question> GetTimu(String id) {

        List<Question> list = examMapper.GetTimu(id);
        for (Question question : list) {
            question.setKnowledges(knowledgesMapper.getKnow(question.getKnowledgeId()));
        }
        return list;
    }

    public List<StutotalScores> GetUserExams() {

        return stuQueInforsMapper.GetUserExams();
    }

    public List<StutotalScores> GetUserExams2(String stuId) {

        return stuQueInforsMapper.GetUserExams2(stuId);
    }

    /**
     * 删除数据以及试卷内的试题
     *
     * @param id
     * @return
     */
    public Boolean delPager(String id) {

        Boolean flag = false;
        if (pagerTempsMapper.delPager(id) == true && pagerTempContentsMapper.delPageCont(id) == true) {
            flag = true;
        }
        return flag;
    }

    public Question chaxun(String id) {

        Question question = examMapper.chaxun(id);
        return question;
    }

    public List<Knowledges> seleAllKnow(String id) {

        List<Knowledges> list = knowledgesMapper.selectAll(id);
        return list;
    }

    public boolean delExam(String id) {

        examMapper.delExam(id);
        return true;
    }

    public void delExamReally(String id) {

        examMapper.delExamReally(id);
    }

    public boolean updatExam(Question question) {


        examMapper.updatExam(question);

        return true;
    }

    public List<Question> selexam(String knowledgeId, String type) {


        List<Question> list = new ArrayList<>();
        list = examMapper.selexam(knowledgeId, type);

        return list;
    }

    public boolean inexam(Question question) {

        examMapper.inexam(question);

        return true;
    }

    public List<Question> chajuan(String knowledgeId, String type, String nandu, String num) {

        List<Question> list = new ArrayList<>();
        list = examMapper.chajuan(knowledgeId, type, nandu, num);

        return list;
    }

    public boolean inquestionPage(QuestionPagers questionPagers) {

        examMapper.inquestionPage(questionPagers);
        return true;
    }

    public void inquertionPagerContent(QuestionPagerContents questionPagerContents) {

        examMapper.inquertionPagerContent(questionPagerContents);
        return;
    }

    public boolean inExams(Exams exams) {

        examMapper.inExams(exams);
        return true;
    }

    public List<Exams> kaoshi(String classId) {

        List<Exams> list = examMapper.kaoshi(classId);
        return list;
    }

    public List<QuestionPagerContents> timu(String id) {

        List<QuestionPagerContents> list = examMapper.timu(id);
        return list;
    }


    public QuestionPagerContents daan(String quesId, String pageId) {

        QuestionPagerContents questionPagerContents = examMapper.trueDaan(quesId, pageId);
        return questionPagerContents;
    }

    public void insStuQueInfors(StuQueInfors stuQueInfors) {

        stuQueInforsMapper.insStuQueInfors(stuQueInfors);

    }

    public String selStuQueInfors(String quesId, String stuId, String pageId) {

        String id = stuQueInforsMapper.selStuQueInfors(quesId, stuId, pageId);

        return id;
    }

    public void upStuQueInfors(StuQueInfors stuQueInfors) {

        stuQueInforsMapper.upStuQueInfors(stuQueInfors);

    }

    public int seleChecked(String pagerKey, String stuKey) {

        Integer integer = stuQueInforsMapper.seleChecked(pagerKey, stuKey);
        if (integer == null || integer.intValue() == 0) {
            return 0;
        } else {
            int checked = integer.intValue();
            return checked;
        }
    }

    public int seleChecked2(String pagerKey, String stuKey, String KnowledgeId) {

        Integer integer = stuQueInforsMapper.seleChecked2(pagerKey, stuKey, KnowledgeId);
        if (integer == null || integer.intValue() == 0) {
            return 0;
        } else {
            int checked = integer.intValue();
            return checked;
        }
    }

    public int zongfen(String pagerKey, String stuKey) {

        int zongfen = 0;
        List<Double> list = stuQueInforsMapper.seleQuesScore(pagerKey, stuKey);
        if (list != null) {
            for (Double double1 : list) {
                double d = double1.doubleValue();
                zongfen += (int) d;
            }
        }
        return zongfen;
    }

    public boolean insTotalScore(StutotalScores stutotalScores) {

        stuQueInforsMapper.insTotalScore(stutotalScores);
        return true;
    }

    public boolean insTotalScore2(StutotalScores stutotalScores) {

        stuQueInforsMapper.insTotalScore2(stutotalScores);
        return true;
    }

    public List<FabuNeirong> seleExamAll() {

        List<Exams> examsList = examMapper.seleExamAll();
        List<FabuNeirong> list = new ArrayList<>();
        for (Exams exams : examsList) {

            String leixing = examMapper.seleQuestionPagers(exams.getExamPager());
            FabuNeirong fabuNeirong = new FabuNeirong();
            fabuNeirong.setExamId(exams.getId());
            fabuNeirong.setLeixing(leixing);
            fabuNeirong.setExamName(exams.getName());
            list.add(fabuNeirong);
        }
        return list;
    }

    public boolean upAnony(Exams exams) {

        examMapper.upAnony(exams);
        return true;
    }

    public List<FabuNeirong> seleExamFabu() {

        List<Exams> examsList = examMapper.seleExamFabu();
        List<FabuNeirong> list = new ArrayList<>();
        for (Exams exams : examsList) {

            String leixing = examMapper.seleQuestionPagers(exams.getExamPager());
            FabuNeirong fabuNeirong = new FabuNeirong();
            fabuNeirong.setExamId(exams.getId());
            fabuNeirong.setLeixing(leixing);
            fabuNeirong.setExamName(exams.getName());
            list.add(fabuNeirong);
        }
        return list;
    }

    public List<Classes> selClassKey(String id) {

        Exams exams = examMapper.selClassKey(id);
        String[] classId = exams.getClassKey().split("______");
        int idLength = classId.length;
        List<Classes> list = new ArrayList<>();
        for (int i = 0; i < idLength; i++) {

            Classes classes = new Classes();
            classes.setId(classId[i]);
            classes.setClassName(classMapper.seleClassName(classId[i]));
            //major_id是暂时没有用的列，在此次交互中暂时用来存放试卷id.此列全部都存放的同一个试卷id
            classes.setMajor_id(exams.getExamPager());
            list.add(classes);
        }
        return list;
    }

    public List<StutotalScores> seleStuScores(String pageId) {

        List<StutotalScores> list = stuQueInforsMapper.seleStuScores(pageId);
        return list;
    }

    public int seleAllScore(String pageId, String stuId) {

        int score = 0;
        if (stuQueInforsMapper.seleAllScore(pageId, stuId) == null) {
            return score;
        } else {
            score = stuQueInforsMapper.seleAllScore(pageId, stuId).intValue();
            return score;
        }
    }

    public String getExamTime(String examPage) {

        String examTime = examMapper.getExamTime(examPage);
        return examTime;
    }

    public List<QuestionPagers> selSubPage(String subId) {

        List<QuestionPagers> list = examMapper.selSubPage(subId);
        return list;
    }

    public List<Exams> selSubPageNew(String subId, String classId) {

        List<Exams> list = examMapper.selSubPageNew(subId, classId);
        return list;
    }

    public Exams getExam(String examPage, String classId) {

        Exams exams = examMapper.getExam(examPage, classId);
        return exams;
    }

    public List<StuQueInfors> seleAnswer(String pagerKey, String stuKey) {

        List<StuQueInfors> list = stuQueInforsMapper.seleAnswer(pagerKey, stuKey);
        return list;
    }

    public void insQuertion(List<Question> list) {

        for (Question question : list) {
            examMapper.inexam(question);
        }
    }

    @Override
    public List<SeekResult> seektestQuestion(Questiondbs questiondbs, int number) {

        List<SeekResult> list = null;
        String knowledgePoint = examMapper.getContent(questiondbs.getKnowledgeId());
        if ("综合题".equals(questiondbs.getQuestionType())) {
            list = examMapper.seekIntegratedQuestion(questiondbs, number);
            for (SeekResult sr : list) {
                sr.setQuestionType(QuestionType);
                sr.setKnowledgePoint(knowledgePoint);
            }
        } else {
            list = examMapper.seektestQuestion(questiondbs, number);
            for (SeekResult sr : list) {
                sr.setDifficult(questiondbs.getDiffcult());
                sr.setKnowledgePoint(knowledgePoint);
            }
        }
        return list;
    }

    @Override
    public String submitTestPaper(String id, String testId, String questionType, int order, int score) {

        Questiondbs questiondbs = new Questiondbs();
        String result = null;
        int i = 0;
        if (!"综合题".equals(questionType)) {
            questiondbs.setScore(score);
            questiondbs = examMapper.selectAll(id);
            i = insertPagerTempContents(questiondbs, testId, order);
        } else {
            QuestionComprehensives qc = new QuestionComprehensives();
            PagerTempCompreContents ptcc = new PagerTempCompreContents();
            qc = examMapper.selectQuestionComprehensives(id);
            ptcc.setId(UUID.randomUUID().toString());
            ptcc.setPagerTempId(testId);
            ptcc.setQuestionKeyArray(qc.getQuestionKeyArray());
            ptcc.setQuestionStem(qc.getQuestionStem());
            ptcc.setCreatePerson(qc.getCreatePerson());
            ptcc.setCreateDate(qc.getCreateDate());
            ptcc.setQuestionOrder(order);
            ptcc.setScore(score);
            //插入综合题
            i = examMapper.insertPagerTempCompreContents(ptcc);
            //插入相应的子题
            insertChildQuestion(ptcc.getQuestionKeyArray(), testId, ptcc.getId());
        }
        if (i > 0) {
            result = "true";
        } else {
            result = "false";
        }
        return result;
    }

    @Override
    public String submitTest(String testName, String sessionId) {
        PagerTemps pt = new PagerTemps();
        pt.setId(UUID.randomUUID().toString());
        pt.setPagerName(testName);
        pt.setStatus(PagerTempStatus);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        pt.setCreateDate(sdf.format(date));

        String userName = examMapper.getUserName(sessionId);
        pt.setCreatePerson(userName);
        pt.setCiteTime(0);

        examMapper.insertPagerTemps(pt);
        return pt.getId();
    }

    /**
     * 插入相应的子题
     *
     * @param QuestionKeyArray
     * @param testId
     * @param ptccId
     */
    public void insertChildQuestion(String QuestionKeyArray, String testId, String ptccId) {
        String[] questiondbsId = QuestionKeyArray.split(",");
        String[] ptcId = new String[questiondbsId.length];
        for (int i = 0; i < questiondbsId.length; i++) {
            Questiondbs questiondbs = examMapper.selectAll(questiondbsId[i]);
            questiondbs.setKnowledgeId(ptccId);
            questiondbs.setScore(0);
            insertPagerTempContents(questiondbs, testId, (i + 1));
            PagerTempContents ptc = questiondbsSwitchPtc(questiondbs, testId, (i + 1));
            ptcId[i] = ptc.getId();
        }
        String spliceStr = arraySplice(ptcId);
        examMapper.updateQuestionKeyArray(spliceStr, ptccId);
    }

    /**
     * 根据questiondbs对象和组卷id在PagerTempContents插入数据
     *
     * @param questiondbs
     * @param testId      组卷id
     * @param order
     * @return 执行出入操作对表产生影响的行数，一般为一行
     */
    public int insertPagerTempContents(Questiondbs questiondbs, String testId, int order) {
        PagerTempContents ptc = questiondbsSwitchPtc(questiondbs, testId, order);
        int i = examMapper.insertPagertempContents(ptc);
        return i;
    }

    /**
     * 根据questiondbs对象和组卷id转换为PagerTempContents对象
     *
     * @param questiondbs
     * @param testId
     * @param order
     * @return PagerTempContents对象
     */
    public PagerTempContents questiondbsSwitchPtc(Questiondbs questiondbs, String testId, int order) {
        PagerTempContents ptc = new PagerTempContents();
        ptc.setId(UUID.randomUUID().toString());
        ptc.setPagerTempId(testId);
        ptc.setScore(questiondbs.getScore());
        ptc.setQuestionStem(questiondbs.getQuestionStem());
        ptc.setQuestionType(questiondbs.getQuestionType());
        ptc.setKnowledgeId(questiondbs.getKnowledgeId());
        ptc.setDiffcult(questiondbs.getDiffcult());
        ptc.setAnswer(questiondbs.getAnswer());
        ptc.setOptionA(questiondbs.getOptionA());
        ptc.setOptionB(questiondbs.getOptionB());
        ptc.setOptionC(questiondbs.getOptionC());
        ptc.setOptionD(questiondbs.getOptionD());
        ptc.setOptionE(questiondbs.getOptionE());
        ptc.setOptionF(questiondbs.getOptionF());
        ptc.setOptionG(questiondbs.getOptionG());
        ptc.setOptionH(questiondbs.getOptionH());
        ptc.setAnalysis(questiondbs.getAnalysis());
        ptc.setCreatePerson(questiondbs.getCreatePerson());
        ptc.setCreateDate(questiondbs.getCreateDate());
        ptc.setQuestionOrder(order);
        return ptc;
    }

    /**
     * 将数据拼接成字符串，用逗号隔开
     *
     * @param array
     * @return String 拼接后的字符串
     */
    public String arraySplice(String[] array) {
        String spliceStr = "";
        for (int i = 0; i < array.length; i++) {
            spliceStr += array[i] + ",";
        }
        if (spliceStr.lastIndexOf(",") == spliceStr.length() - 1) {
            spliceStr = spliceStr.substring(0, spliceStr.length() - 1);
        }
        return spliceStr;
    }

    @Override
    public List<PagerTemps> selectExamsName() {

        List<PagerTemps> list = examMapper.selectExamsName();
        return list;
    }

    @Override
    public List<TestList> selectExams(String parameter, String sessionId) {

        List<Exams> list = examMapper.selectExams(parameter);
        List<TestList> testList = new ArrayList<TestList>();
        for (int i = 0; i < list.size(); i++) {
            Exams exams = new Exams();
            TestList tl = new TestList();
            exams = list.get(i);
            tl.setId(exams.getId());
            tl.setExamType(exams.getExamType());
            tl.setName(exams.getName());
            tl.setStartTime(exams.getStartTime());
            tl.setEndTime(exams.getEndTime());

            tl.setClassName(examMapper.getClassName(exams.getClassKey()));
            tl.setCorrectPrenson(examMapper.getUserName(sessionId));

            tl.setPagerName(examMapper.getPagerName(exams.getExamPager()));
            testList.add(tl);
        }
        return testList;
    }

    @Override
    public List<Users> getTeacherUsers() {

        String teachersId = examMapper.getRolesIdByTeacher();
        List<String> userRolesId = examMapper.getuserRolesId(teachersId);
        List<Users> list = new ArrayList<Users>();
        for (int i = 0; i < userRolesId.size(); i++) {
            Users users = examMapper.getUserInfoById(userRolesId.get(i));
            users.setId(userRolesId.get(i));
            list.add(users);
        }
        return list;
    }

    @Override
    public List<Classes> getClasses() {
        List<Classes> list = examMapper.getClassesName();
        return list;
    }

    @Override
    public String insertExams(Exams exams, String id) {

        int i = 0;
        if (id == null) {
            exams.setId(UUID.randomUUID().toString());
            exams.setAnony(1);
            i = examMapper.insertExams(exams);
        } else {
            exams.setId(id);
            i = examMapper.updateExamsById(exams);
        }
        String result = "faild";
        if (i > 0) {
            result = "succeed";
        }
        return result;
    }

    @Override
    public Exams getExamsByExamsId(String examId) throws ParseException {

        Exams exams = examMapper.getExamsById(examId);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        exams.setStartTime(sdf.format(sdf2.parse(exams.getStartTime())));
        exams.setEndTime(sdf.format(sdf2.parse(exams.getEndTime())));
        return exams;
    }

    public List<Questiondbs> selectQuesidByOrder(String parameter) {

        List<Questiondbs> list = examMapper.selectQuestionByOrder(parameter);
        return list;
    }

    public List<Questiondbs> selectQuesbyid(String id) {

        List<Questiondbs> list = examMapper.selectQuesbyid(id);
        return list;
    }

    public List<PagerTempCompreContents> selectzongheQuesbyid(String id) {

        List<PagerTempCompreContents> list = examMapper.selectzongheQuesbyid(id);
        return list;
    }

    /**
     * 对普通题的处理
     */
    @Override
    public void answerDispose(String quesId, String pageId, String value, String sessionId) {
        //System.out.println("value2:"+value);

        //获取题型
        String questionType = examMapper.seleQuestionTypeOfpagertempcontents(quesId);
        if (CompletionType.equals(questionType)) {
            //填空题的处理
            completion(value, quesId, pageId, sessionId);
        } else if (ChoiceType.equals(questionType)) {
            //单选题的处理
            completion(value, quesId, pageId, sessionId);
        } else if (MulitipleChoiceType.equals(questionType)) {
            //多选题的处理
            mulitipleChoice(value, quesId, pageId, sessionId);
        } else if (CheckingType.equals(questionType)) {
            //判断题的处理
            checking(value, quesId, pageId, sessionId);
        } else if (EssayQuestionType.equals(questionType)) {
            //问答题的处理
            essayQuestion(value, quesId, pageId, sessionId);
        } else if (simulationsType.equals(questionType)) {
            //3D仿真题的处理
            simulations(value, quesId, pageId, sessionId);
        }
    }

    /**
     * 对3D仿真题的处理
     *
     * @param value     用户填写的答案
     * @param quesId    题目id
     * @param pageId    试卷id
     * @param sessionId 用户id
     */
    public void simulations(String value, String quesId, String pageId, String sessionId) {
        double score = 0;
        updateOrInsert(value, quesId, pageId, sessionId, score);
    }

    /**
     * 对问答题的处理
     *
     * @param value     用户填写的答案
     * @param quesId    题目id
     * @param pageId    试卷id
     * @param sessionId 用户id
     */
    public void essayQuestion(String value, String quesId, String pageId, String sessionId) {
        double score = 0;
        updateOrInsert(value, quesId, pageId, sessionId, score);
    }

    /**
     * 对判断题的处理
     *
     * @param value     用户填写的答案
     * @param quesId    题目id
     * @param pageId    试卷id
     * @param sessionId 用户id
     */
    public void checking(String value, String quesId, String pageId, String sessionId) {
        //获取正确答案
        String correctAnswer = examMapper.seleCorrectAnswer(quesId);
        double score = 0;
        if ("A".equals(value)) {
            value = "正确";
        } else if ("B".equals(value)) {
            value = "错误";
        } else {
            throw new ServiceException("判断题答案转换异常");
        }
        if (correctAnswer.equals(value)) {
            score = examMapper.seleScore(quesId);
        }
        updateOrInsert(value, quesId, pageId, sessionId, score);
    }

    /**
     * 对填空题和单选题的处理
     *
     * @param value     用户填写的答案
     * @param quesId    题目id
     * @param pageId    试卷id
     * @param sessionId 用户id
     */
    public void completion(String value, String quesId, String pageId, String sessionId) {

        //获取正确答案
        String correctAnswer = examMapper.seleCorrectAnswer(quesId);
        //对答案进行对比
        double score = 0;
        if (correctAnswer.equals(value)) {
            //获取得分
            score = examMapper.seleScore(quesId);
        } else {
            String[] ca = correctAnswer.split("______");
            String[] vl = value.split("______");
            int count = 0;
            for (int i = 0; i < ca.length; i++) {
                if (ca[i].equals(vl[i])) {
                    count++;
                }
            }
            score = examMapper.seleScore(quesId) / ca.length * count;
        }
        updateOrInsert(value, quesId, pageId, sessionId, score);
        //根据得分情况判断是否更新错题库
    }

    /**
     * 对用户答题的数据进行插入，
     * 插入在StuQueInfors表中,如果回答错误
     * 将此题插入或更新在错题库中
     *
     * @param value     用户回答的答案
     * @param quesId    题目id
     * @param pageId    试卷id
     * @param sessionId 用户id
     * @param score     得分数
     */
    public void insertStuQueInfor(String value, String quesId, String pageId, String sessionId, double score) {

        StuQueInfors sqi = new StuQueInfors();
        sqi.setQuesScore(score);
        sqi.setQuestionAnswer(value);
        sqi.setPagerKey(pageId);
        sqi.setId(UUID.randomUUID().toString());
        sqi.setQuestionKey(quesId);
        sqi.setStuKey(sessionId);
        //对错题库的插入或者更新
        insertOrUpdateErrorQues(sqi);
        //将学生答题的数据插入表中
        examMapper.insertStuQueInfors(sqi);
    }

    /**
     * 根据得分情况判断是否对错题库进行插入或更新数据
     *
     * @param sqi 用户回答的该题的数据
     */

    public void insertOrUpdateErrorQues(StuQueInfors sqi) {
        String pagerTempId = examMapper.selePagertempId(sqi.getPagerKey());
        int score = examMapper.seleScore(pagerTempId);

        //根据题目id和用户id在错题库查找是否有插入此题的数据
        ErrorQues eq = examMapper.seleErrorQues(sqi.getQuestionKey(), sqi.getStuKey());
        //根据得分情况对错题库进行操作
        if (score == sqi.getQuesScore()) {
            if (eq != null) {
                //对错题进行删除
                examMapper.delErrorQues(eq);
            }
            return;
        } else {
            if (eq != null) {
                //对错题进行更新操作
                System.err.println(sqi.getQuestionAnswer());
                eq.setErrorAns(sqi.getQuestionAnswer());
                examMapper.updateErrorQues(sqi);
            } else {
                //对错题进行插入操作
                ErrorQues eqi = new ErrorQues();
                eqi.setId(UUID.randomUUID().toString());
                eqi.setErrorAns(sqi.getQuestionAnswer());
                eqi.setQuestionKey(sqi.getQuestionKey());
                eqi.setStuKey(sqi.getStuKey());
                examMapper.inseErrorQues(eqi);
            }
        }
    }

    /**
     * 对多选题的判断
     *
     * @param value     用户填写的答案
     * @param quesId    题目id
     * @param pageId    试卷id
     * @param sessionId 用户id
     */
    public void mulitipleChoice(String value, String quesId, String pageId, String sessionId) {
        double score = 0;
        //获取正确答案
        String correctAnswer = examMapper.seleCorrectAnswer(quesId);
        String[] ca = correctAnswer.split(";");
        String[] valueStr = value.split("______");
        if (ca.length >= valueStr.length) {
            if (Arrays.equals(ca, valueStr)) {
                score = examMapper.seleScore(quesId);
            } else if (containArray(ca, valueStr)) {
                //多选对两个以上且没错选项，得一半分
                score = examMapper.seleScore(quesId) / 2;
            }
        }
        updateOrInsert(value, quesId, pageId, sessionId, score);
    }

    /**
     * 用于多选题判断，true为对了两个以上，
     * false为对了一个或有错误答案
     *
     * @param ca    正确答案的字符数组
     * @param value 用户填写答案的字符数组
     * @return
     */
    public boolean containArray(String[] ca, String[] value) {
        //用于统计对了几个
        double count = 0;
        for (int i = 0; i < value.length; i++) {
            String ccv = value[i];
            for (int j = 0; j < ca.length; j++) {
                String cca = ca[j];
                if (cca.equals(ccv)) {
                    count++;
                } else {
                    return false;
                }
            }
        }
        if (count > 1) {
            return true;
        } else
            return false;
    }

    /**
     * 对修改答案的题目进行更新操作，
     * 如果回答错误将此题插入或更新在错题库中
     *
     * @param value     用户修改后的答案
     * @param quesId    题目id
     * @param pageId    试卷id
     * @param sessionId 用户id
     * @param score     得分情况
     * @return result    更新的行数
     */
    public int updateStuqueinfors(String value, String quesId, String pageId, String sessionId, double score) {

        StuQueInfors sqi = new StuQueInfors();
        sqi.setQuesScore(score);
        sqi.setQuestionAnswer(value);
        sqi.setPagerKey(pageId);
        sqi.setQuestionKey(quesId);
        sqi.setStuKey(sessionId);
        //对stuquesinfors进行更新操作
        int result = examMapper.updateStuquesinfors(sqi);
        sqi.setId(examMapper.getId(pageId, quesId));
        //根据得分情况对错题库进行操作
        insertOrUpdateErrorQues(sqi);
        return result;
    }

    /**
     * 判断对stuqueinfors表进行插入或更新操作
     *
     * @param value     用户填入的答案
     * @param quesId    题目id
     * @param pageId    试卷id
     * @param sessionId 用户id
     * @param score     得分情况
     */
    public void updateOrInsert(String value, String quesId, String pageId, String sessionId, double score) {
        //对stuqueinfors表进行更新操作，返回更新受影响的行数
        int result = updateStuqueinfors(value, quesId, pageId, sessionId, score);
        if (result > 0) {
            //更新成功
            return;
        }
        insertStuQueInfor(value, quesId, pageId, sessionId, score);
    }

    @Override
    public List<String> getStartAndEndTime(String examPage) {

        Exams exams = examMapper.seleStartAndEndTime(examPage);
        String startTime = exams.getStartTime().substring(0, exams.getStartTime().lastIndexOf("."));
        String endTime = exams.getEndTime().substring(0, exams.getEndTime().lastIndexOf("."));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String nowDate = sdf.format(date);
        List<String> list = new ArrayList<String>();
        list.add(startTime);
        list.add(endTime);
        list.add(nowDate);
        return list;
    }

    public String seleExamPagerById(String parameter) {

        String pageId = examMapper.seleExamPagerById(parameter);
        return pageId;
    }

    public void wendaDispose(String answer, String quesId, String pageId, String sessionId) {
        int i = examMapper.updwendaAnswer(answer, quesId, pageId, sessionId);
    }

    @Override
    public List<QuestionPagers> getPages(String subjectId) {

        return examMapper.getPages(subjectId, useAble);
    }

    public boolean submit(String userId, String allscore, String examId, String id) {
        if (allscore != null) {
            exercisesMapper.submit(userId, allscore, examId, id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据题目id以及类型添加个人错题
     */
    public boolean addError(String timuId, String useType, String userId) {
        Question question = exercisesMapper.getSingleQuestion(timuId);
        QuestionPersonal personal = new QuestionPersonal();
        personal.setId(question.getId());
        personal.setContent(question.getContent());
        personal.setFenshu(question.getFenshu());
        personal.setPersonalId(userId);
        personal.setQuestionId(question.getKnowledgeId());
        personal.setQuestionImg(question.getQuestionImg());
        personal.setTime(question.getTime());
        personal.setTitle(question.getTitle());
        personal.setType(question.getType());
        personal.setUseType(useType);
        exercisesMapper.addPersonal(personal);
        return true;
    }

    public void updateFabu(Exams exams) {
        examMapper.updateFabu(exams);
    }

    public List<Question> getPageQue(String examId) {

        return examMapper.getPageQue(examId);
    }

    public String getPageKey(String examId) {
        return examMapper.getPageKey(examId);
    }

    public boolean updatePage(QuestionPagers questionPagers) {
        return examMapper.updatePage(questionPagers);
    }

    public void delPageQuestions(String questionPage_id) {
        examMapper.delPageQuestions(questionPage_id);
    }
}
