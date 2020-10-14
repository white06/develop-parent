package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Comments;
import com.tdu.develop.resource.pojo.Evaluation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationMapper {

    /**
     * 新增评论
     *
     * @param evaluation
     */
    public void addEvaluation(Evaluation evaluation);

    /*
     * 获取最近一次评价
     * */
    Evaluation getOneByTimen(@Param("userKey") String userKey, @Param("sceneKey") String sceneKey);

    /*
     * 获取音质试听排名
     * */
    String getRanking(@Param("userKey") String userKey, @Param("sceneKey") String sceneKey);
}
