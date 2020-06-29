package com.tdu.develop.resource.service;

import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.PrevKnowledgeContentInfor;
import com.tdu.develop.resource.pojo.Question;
import com.tdu.develop.resource.pojo.QuestionComprehensives;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-26-17:54
 */
public interface QuestionService {
    public List<Question> seektestQuestion(Question question, int number);

    public List<Question> GetFillblankQuestionJson(String knowledgeid);
    public List<Question> GetSingleChoiceQuestionJson(String knowledgeid);
    public List<Question> GetMulChoiceQuestionJson(String knowledgeid);
    public List<Question> GetIsTureQuestionJson(String knowledgeid);
    public List<Question> GetTextAreaQuestionJson(String knowledgeid);
    public List<Question> Get3DQuestionJson(String knowledgeid);
    public List<QuestionComprehensives> GetcompreQuestionJson(String knowledgeid);

    public void addKnowledge(Knowledges knownew) ;
    public List<PrevKnowledgeContentInfor> GetPrevSourceList(String selesubjectid);
}
