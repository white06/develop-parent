package com.tdu.develop.resource.service;

import com.tdu.develop.resource.pojo.*;
import com.tdu.develop.user.pojo.Classes;
import com.tdu.develop.user.pojo.Users;

import java.text.ParseException;
import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-27-9:09
 */
public interface ExamService {
    public String GetPagerName2(String id);

    /**
     * 获取已组卷试卷的题目
     *
     * @param id
     * @return
     */
    public List<Question> GetTimu(String id);

    public List<StutotalScores> GetUserExams();

    public List<StutotalScores> GetUserExams2(String stuId);

    /**
     * 删除数据以及试卷内的试题
     *
     * @param id
     * @return
     */
    public Boolean delPager(String id);

    public Question chaxun(String id);

    public List<Knowledges> seleAllKnow(String id);

    public boolean delExam(String id);

    public boolean updatExam(Question question);

    public List<Question> selexam(String knowledgeId, String type);

    public boolean inexam(Question question);

    public List<Question> chajuan(String knowledgeId, String type, String nandu, String num);

    public boolean inquestionPage(QuestionPagers questionPagers);

    public void inquertionPagerContent(QuestionPagerContents questionPagerContents);

    public boolean inExams(Exams exams);

    public List<Exams> kaoshi(String classId);

    public List<QuestionPagerContents> timu(String id);


    public QuestionPagerContents daan(String quesId, String pageId);

    public void insStuQueInfors(StuQueInfors stuQueInfors);

    public String selStuQueInfors(String quesId, String stuId, String pageId);

    public void upStuQueInfors(StuQueInfors stuQueInfors);

    public int seleChecked(String pagerKey, String stuKey);

    public int seleChecked2(String pagerKey, String stuKey, String KnowledgeId);

    public int zongfen(String pagerKey, String stuKey);

    public boolean insTotalScore(StutotalScores stutotalScores);

    public boolean insTotalScore2(StutotalScores stutotalScores);

    public List<FabuNeirong> seleExamAll();

    public boolean upAnony(Exams exams);

    public List<FabuNeirong> seleExamFabu();

    public List<Classes> selClassKey(String id);

    public List<StutotalScores> seleStuScores(String pageId);

    public int seleAllScore(String pageId, String stuId);

    public String getExamTime(String examPage);

    public List<QuestionPagers> selSubPage(String subId);

    public List<Exams> selSubPageNew(String subId, String classId);

    public Exams getExam(String examPage, String classId);

    public List<StuQueInfors> seleAnswer(String pagerKey, String stuKey);

    public void insQuertion(List<Question> list);

    public List<SeekResult> seektestQuestion(Questiondbs questiondbs, int number);

    public String submitTestPaper(String id, String testId, String questionType, int order, int score);

    public String submitTest(String testName, String sessionId);

    /**
     * 插入相应的子题
     *
     * @param QuestionKeyArray
     * @param testId
     * @param ptccId
     */
    public void insertChildQuestion(String QuestionKeyArray, String testId, String ptccId);

    /**
     * 根据questiondbs对象和组卷id在PagerTempContents插入数据
     *
     * @param questiondbs
     * @param testId      组卷id
     * @param order
     * @param score
     * @return 执行出入操作对表产生影响的行数，一般为一行
     */
    public int insertPagerTempContents(Questiondbs questiondbs, String testId, int order);

    /**
     * 根据questiondbs对象和组卷id转换为PagerTempContents对象
     *
     * @param questiondbs
     * @param testId
     * @param order
     * @param score
     * @return PagerTempContents对象
     */
    public PagerTempContents questiondbsSwitchPtc(Questiondbs questiondbs, String testId, int order);

    /**
     * 将数据拼接成字符串，用逗号隔开
     *
     * @param array
     * @return String 拼接后的字符串
     */
    public String arraySplice(String[] array);

    public List<PagerTemps> selectExamsName();

    public List<TestList> selectExams(String parameter, String sessionId);

    public List<Users> getTeacherUsers();

    public List<Classes> getClasses();

    public String insertExams(Exams exams, String id);

    public Exams getExamsByExamsId(String examId) throws ParseException;

    public List<Questiondbs> selectQuesidByOrder(String parameter);

    public List<Questiondbs> selectQuesbyid(String id);

    public List<PagerTempCompreContents> selectzongheQuesbyid(String id);

    /**
     * 对普通题的处理
     */
    public void answerDispose(String quesId, String pageId, String value, String sessionId);

    /**
     * 对3D仿真题的处理
     *
     * @param value     用户填写的答案
     * @param quesId    题目id
     * @param pageId    试卷id
     * @param sessionId 用户id
     */
    public void simulations(String value, String quesId, String pageId, String sessionId);

    /**
     * 对问答题的处理
     *
     * @param value     用户填写的答案
     * @param quesId    题目id
     * @param pageId    试卷id
     * @param sessionId 用户id
     */
    public void essayQuestion(String value, String quesId, String pageId, String sessionId);

    /**
     * 对判断题的处理
     *
     * @param value     用户填写的答案
     * @param quesId    题目id
     * @param pageId    试卷id
     * @param sessionId 用户id
     */
    public void checking(String value, String quesId, String pageId, String sessionId);

    /**
     * 对填空题和单选题的处理
     *
     * @param value     用户填写的答案
     * @param quesId    题目id
     * @param pageId    试卷id
     * @param sessionId 用户id
     */
    public void completion(String value, String quesId, String pageId, String sessionId);

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
    public void insertStuQueInfor(String value, String quesId, String pageId, String sessionId, double score);

    /**
     * 根据得分情况判断是否对错题库进行插入或更新数据
     *
     * @param sqi 用户回答的该题的数据
     */

    public void insertOrUpdateErrorQues(StuQueInfors sqi);

    /**
     * 对多选题的判断
     *
     * @param value     用户填写的答案
     * @param quesId    题目id
     * @param pageId    试卷id
     * @param sessionId 用户id
     */
    public void mulitipleChoice(String value, String quesId, String pageId, String sessionId);

    /**
     * 用于多选题判断，true为对了两个以上，
     * false为对了一个或有错误答案
     *
     * @param ca    正确答案的字符数组
     * @param value 用户填写答案的字符数组
     * @return
     */
    public boolean containArray(String[] ca, String[] value);

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
    public int updateStuqueinfors(String value, String quesId, String pageId, String sessionId, double score);

    /**
     * 判断对stuqueinfors表进行插入或更新操作
     *
     * @param value     用户填入的答案
     * @param quesId    题目id
     * @param pageId    试卷id
     * @param sessionId 用户id
     * @param score     得分情况
     */
    public void updateOrInsert(String value, String quesId, String pageId, String sessionId, double score);

    public List<String> getStartAndEndTime(String examPage);

    public String seleExamPagerById(String parameter);

    public void wendaDispose(String answer, String quesId, String pageId, String sessionId);

    public List<QuestionPagers> getPages(String subjectId);

    public void updateFabu(Exams exams);

    void delExamReally(String examId);

    List<Question> getPageQue(String examId);

    String getPageKey(String examId);

    boolean updatePage(QuestionPagers questionPagers);

    void delPageQuestions(String questionPage_id);
}
