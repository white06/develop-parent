package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.*;
import com.tdu.develop.user.pojo.Classes;
import com.tdu.develop.user.pojo.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-27-9:10
 */
@Repository
public interface ExamMapper {
    public String getPagerName2(String id);

    /*
     * 获取已组卷试卷的题目
     * */
    public List<Question> GetTimu(String pagerId);

    public List<Question> chajuan(@Param("knowledgeId") String knowledgeId, @Param("type") String type, @Param("nandu") String nandu, @Param("num") String num);

    public List<Question> selexam(@Param("knowledgeId") String knowledgeId, @Param("type") String type);

    public void inexam(Question question);

    public void updatExam(Question question);

    public void delExam(String id);

    public void inquestionPage(QuestionPagers questionPagers);

    public void inquertionPagerContent(QuestionPagerContents questionPagerContents);

    public void inExams(Exams exams);

    public Question chaxun(String id);

    public List<Exams> kaoshi(String classId);

    public List<QuestionPagerContents> timu(String id);

    public QuestionPagerContents trueDaan(@Param("quesId") String quesId, @Param("pageId") String pageId);

    public List<Exams> seleExamAll();

    /*public String seleClassName(String classkey);*/

    public String seleQuestionPagers(String pagekey);

    public void upAnony(Exams exams);

    public List<Exams> seleExamFabu();

    public Exams selClassKey(String id);

    public String getExamTime(String examPage);

    public List<QuestionPagers> selSubPage(String subId);

    public List<Exams> selSubPageNew(@Param("subId") String subId, @Param("classId") String classId);

    public Exams getExam(@Param("examPage") String examPage, @Param("classId") String classId);

    public List<SeekResult> seektestQuestion(@Param("questiondbs") Questiondbs questiondbs, @Param("number") int number);

    public String getContent(String knowledgeId);

    public List<Questiondbs> newtimu(String id);

    public Questiondbs selectAll(String id);

    public int insertPagertempContents(PagerTempContents ptc);

    public QuestionComprehensives selectQuestionComprehensives(String id);

    public Integer getMaxOrder();

    public String getUserName(String sessionId);

    public void insertPagerTemps(PagerTemps pt);

    public int insertPagerTempCompreContents(PagerTempCompreContents ptcc);

    public List<SeekResult> seekIntegratedQuestion(@Param("questiondbs") Questiondbs questiondbs, @Param("number") int number);

    public void updateQuestionKeyArray(@Param("spliceStr") String spliceStr, @Param("ptccId") String ptccId);

    public List<PagerTemps> selectExamsName();

    public List<Exams> selectExams(String parameter);

    public String getClassName(String classKey);

    public String getPagerName(String examPager);

    public String getRolesIdByTeacher();

    public List<String> getuserRolesId(String teachersId);

    public Users getUserInfoById(String id);

    public List<Classes> getClassesName();

    public int insertExams(Exams exams);

    public Exams getExamsById(String examId);

    public int updateExamsById(Exams exams);

    public List<Questiondbs> selectQuestionByOrder(String parameter);

    public List<Questiondbs> selectQuesbyid(String id);

    public List<PagerTempCompreContents> selectzongheQuesbyid(String id);

    public String seleCorrectAnswer(@Param("quesId") String quesId);

    public int seleScore(@Param("quesId") String quesId);

    public void insertStuQueInfors(StuQueInfors sqi);

    public String seleQuestionTypeOfpagertempcontents(@Param("quesId") String quesId);

    public String getExamPagerById(String pageId);

    public String seleQuestionKeyArray(@Param("pagetempId") String pagetempId, @Param("quesId") String quesId);

    public int updateStuquesinfors(StuQueInfors sqi);

    public Exams seleStartAndEndTime(String examPage);

    public String seleExamPagerById(String parameter);

    public ErrorQues seleErrorQues(@Param("questionKey") String questionKey, @Param("stuKey") String stuKey);

    public void updateErrorQues(StuQueInfors sqi);

    public void inseErrorQues(ErrorQues eq);

    public void delErrorQues(ErrorQues eq);

    public String getId(@Param("pageId") String pageId, @Param("quesId") String quesId);

    public String selePagertempId(String pagerKey);

    public Integer updwendaAnswer(@Param("answer") String answer, @Param("quesId") String quesId, @Param("pageId") String pageId, @Param("sessionId") String sessionId);

    /**
     * 获取已发布的考试
     *
     * @param subjectId
     * @param useAble
     * @return
     */
    public List<QuestionPagers> getPages(@Param("subjectId") String subjectId, @Param("useAble") String useAble);

    public void updateFabu(Exams exams);

    void delExamReally(String id);

    List<Question> getPageQue(@Param("examId") String examId);

    String getPageKey(String examId);

    boolean updatePage(QuestionPagers questionPagers);

    void delPageQuestions(String questionPage_id);
}
