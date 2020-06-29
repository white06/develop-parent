package com.tdu.develop.resource.service;

import com.tdu.develop.resource.pojo.QuestionPagers;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-27-14:55
 */
public interface QuestionPagerService {
    public Boolean DelPagerStoAndeAnswer(String questionPager_Id);

    public Boolean DelQuesPagerContent(String questionPager_Id, String questionKey);
    public int updatePager(String testName, String id) ;

    public List<QuestionPagers> GetQuestionPagers(/*int page,int rowCount*/) ;

    public String submitTest(String testName, String sessionId, String subjectKey) ;

    public String submitTestPaper(String id, String testId, String questionType, int order, int score) ;

    /**
     * 删除数据以及试卷内的试题
     * @param id
     * @return
     */
    public Boolean delPager(String id);
}
