package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.PrevKnowledgeContentInfor;
import com.tdu.develop.resource.pojo.Question;
import com.tdu.develop.resource.pojo.QuestionComprehensives;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-26-17:56
 */
@Repository
public interface QuestionMapper {
    public List<Question> GetFillblankQuestionJson(String knowledgeid);
    public List<Question> GetSingleChoiceQuestionJson(String knowledgeid);
    public List<Question> GetMulChoiceQuestionJson(String knowledgeid);
    public List<Question> GetIsTureQuestionJson(String knowledgeid);;
    public List<Question> GetTextAreaQuestionJson(String knowledgeid);
    public List<Question> Get3DQuestionJson(String knowledgeid);
    public List<QuestionComprehensives> GetcompreQuestionJson(String knowledgeid);
    public void addKnowledge(Knowledges knownew) ;
    public List<PrevKnowledgeContentInfor> GetPrevSourceList(String knowledge_Id);


    public List<Question> seektestQuestion(@Param("question") Question question, @Param("number") int number);
}
