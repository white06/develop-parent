package com.tdu.develop.resource.service;

import com.tdu.develop.resource.pojo.Comments;
import com.tdu.develop.resource.pojo.Evaluation;

import java.util.List;

public interface EvaluationService {

    /**
     *  新增评论
     * @param evaluation
     */
    public  void addEvaluation(Evaluation evaluation);

    Evaluation getOneByTimen(String userKey, String sceneKey);

    String getRanking(String userKey, String sceneKey);
}
