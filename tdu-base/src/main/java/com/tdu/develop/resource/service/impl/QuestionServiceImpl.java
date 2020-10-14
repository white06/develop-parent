package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.QuestionMapper;
import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.PrevKnowledgeContentInfor;
import com.tdu.develop.resource.pojo.Question;
import com.tdu.develop.resource.pojo.QuestionComprehensives;
import com.tdu.develop.resource.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-26-17:55
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    QuestionMapper questionmapper;


    public List<Question> seektestQuestion(Question question, int number) {
        return questionmapper.seektestQuestion(question, number);
    }

    public List<Question> GetFillblankQuestionJson(String knowledgeid) {
        return questionmapper.GetFillblankQuestionJson(knowledgeid);
    }

    public List<Question> GetSingleChoiceQuestionJson(String knowledgeid) {

        return questionmapper.GetSingleChoiceQuestionJson(knowledgeid);
    }

    public List<Question> GetMulChoiceQuestionJson(String knowledgeid) {

        return questionmapper.GetMulChoiceQuestionJson(knowledgeid);
    }

    public List<Question> GetIsTureQuestionJson(String knowledgeid) {
        return questionmapper.GetIsTureQuestionJson(knowledgeid);
    }

    public List<Question> GetTextAreaQuestionJson(String knowledgeid) {
        return questionmapper.GetTextAreaQuestionJson(knowledgeid);
    }

    public List<Question> Get3DQuestionJson(String knowledgeid) {
        return questionmapper.Get3DQuestionJson(knowledgeid);
    }

    public List<QuestionComprehensives> GetcompreQuestionJson(String knowledgeid) {
        return questionmapper.GetcompreQuestionJson(knowledgeid);
    }

    public void addKnowledge(Knowledges knownew) {
        questionmapper.addKnowledge(knownew);
    }

    public List<PrevKnowledgeContentInfor> GetPrevSourceList(String selesubjectid) {
        return questionmapper.GetPrevSourceList(selesubjectid);
    }
}
