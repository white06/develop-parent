package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.Knowlegcontent;
import com.tdu.develop.resource.pojo.Question;
import com.tdu.develop.resource.pojo.QuestionPersonal;
import com.tdu.develop.user.pojo.Subjects;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-27-17:37
 */
@Repository
public interface ExercisesMapper {
    /**
     * 根据知识点获取所有题目
     *
     * @param knowId
     * @return
     */
    public List<Question> getQuestion(@Param("knowId") String knowId);

    /**
     * 根据随机题节点获取内容id
     *
     * @param knowId
     * @return
     */
    public String getKnowContentId(@Param("knowId") String knowId);

    /**
     * 根据内容id获取内容
     *
     * @param contId
     * @return
     */
    public Knowlegcontent getKnowContent(@Param("contId") String contId);

    /**
     * 根据科目id获取知识点id
     *
     * @param subId
     * @return
     */
    public String getSubjectTreesId(@Param("subId") String subId);

    /**
     * 根据知识树ID获取所有包含的知识点
     *
     * @param subTreesId
     * @return
     */
    public List<Knowledges> getKnowAll(@Param("subTreesId") String subTreesId);

    /**
     * 根据知识树ID获取所有包含的知识点
     *
     * @param subTreesId
     * @return
     */
    public List<Knowledges> getKnow(@Param("subTreesId") String subTreesId);

    /**
     * 获取所有科目的随机题
     *
     * @return
     */
    public List<Question> getAllSubQue(@Param("count") int count);

    /**
     * 获取所有科目
     *
     * @return
     */
    public List<Subjects> getAllSub();

    /**
     * 获取所有科目的随机题
     *
     * @return
     */
    public List<Question> getAllKnowQue(@Param("count") int count, @Param("knowId") String knowId);

    /**
     * 根据知识点获取所有科目的题目
     *
     * @param knowId
     * @return
     */
    public List<Question> getAllQuestion(@Param("knowId") String knowId);

    /**
     * 根据subId获取首知识点
     *
     * @param subId
     * @return
     */
    public List<Knowledges> getFirstRoot(@Param("subId") String subId);

    /**
     * 根据parentId获取知识点
     *
     * @param parentId
     * @return
     */
    public List<Knowledges> getKnowledgeBy(@Param("parentId") String parentId);

    /**
     * 根据题目Id获取该题目
     *
     * @param timuId
     * @return
     */
    public Question getSingleQuestion(@Param("timuId") String timuId);

    /**
     * 添加个人题目
     *
     * @param questionPersonal
     */
    public void addPersonal(@Param("questionPersonal") QuestionPersonal questionPersonal);

    /**
     * 根据个人id以及个人题库的类型的出题
     *
     * @param questionNum
     * @param subId
     * @return
     */
    public List<QuestionPersonal> shoucangQuestion(@Param("questionNum") String questionNum, @Param("subId") String subId);

    /**
     * 根据个人题目id从个人题目表中删除
     *
     * @param timuId
     */
    public void deleteShoucang(@Param("timuId") String timuId);

    /**
     * 提交考试成绩
     *
     * @param userId
     * @param allscore
     * @param examId
     * @param id
     */
    public void submit(@Param("userId") String userId, @Param("allscore") String allscore, @Param("examId") String examId, @Param("id") String id);
}
