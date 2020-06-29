package com.tdu.develop.resource.service;

import com.tdu.develop.resource.pojo.*;
import com.tdu.develop.user.pojo.Subjects;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-27-17:35
 */
public interface ExercisesService {
    /**
     * 根据知识点id获取全部题目
     */
    public List<Question> getQuestion(String knowId) ;

    /**
     * 根据id获取内容id
     */
     
    public String getKnowContentId(String knowId) ;

    /**
     * 根据内容id获取知识点内容
     */
     
    public Knowlegcontent getKnowContent(String contId) ;

    /**
     * 根据知识点内容对象获取随机题目
     */
     
    public List<Question> getRandomQuestion(Knowlegcontent knowlegcontent) ;

    /**
     * 根据知识点内容对象获取随机题目
     */
     
    public List<QuestionPersonal> perQuestion(Knowlegcontent knowlegcontent, String type) ;
    /**
     * 获取所有科目
     */
     
    public List<Subjects> getAllSub() ;
    /**
     * 获取知识点
     */
     
    public List<Knowledges> getKnowledge(String subId) ;
    /**
     * 根据知识点获取全部内容
     */
     
    public List<Question> getAllQuestion(Knowlegcontent knowlegcontent,String chooseId) ;

    /**
     * 根据subId获取首目录
     */
     
    public List<Knowledges> getFirstRoot(String subId) ;

    /**
     * 根据parentId获取知识点
     */
     
    public List<Knowledges> getKnowledgeBy(String parentId) ;

    /**
     * 获取知识点
     */
     
    public List<Object> getKnowledgeZtree(String subId) ;

    /**
     * 根据题目id以及类型添加个人题目
     */
     
    public boolean addPersonal(String timuId, String useType, String userId) ;
    public boolean deleteShoucang(String timuId);

    public boolean submit(String userId, String allscore, String examId,String id) ;

    /**
     * 根据题目id以及类型添加个人错题
     */
    public boolean addError(String timuId, String useType,String userId) ;
    public List<Question> getAllQuestion(String knowId) ;
}
