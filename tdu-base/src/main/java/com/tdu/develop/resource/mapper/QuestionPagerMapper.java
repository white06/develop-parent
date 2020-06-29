package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Question;
import com.tdu.develop.resource.pojo.QuestionPagerContents;
import com.tdu.develop.resource.pojo.QuestionPagers;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-27-14:56
 */
@Repository
public interface QuestionPagerMapper {
    public List<QuestionPagers> GetQuestionPagers(/*@Param("page")int page,@Param("rowCount")int rowCount*/);

    public void insertQuestionPagers(QuestionPagers pt);

    public int insertQuestionPagerContents(QuestionPagerContents qpc);

    public Question selectAll(String id);

    public Boolean delPager(String id);

    public Boolean delPageCont(String PagerContentId);

    public int updateQuestionPagers(@Param("name") String name, @Param("id") String id);

    public int DelQuesPagerContent(@Param("questionPager_Id") String questionPager_Id, @Param("questionKey") String questionKey);

    public int DelPagerStoAndeAnswer(@Param("pagerKey") String pagerKey);

    public int DelPagerchengji(@Param("pagerKey") String pagerKey);
}
