package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.CommentsMapper;
import com.tdu.develop.resource.mapper.EvaluationMapper;
import com.tdu.develop.resource.pojo.Comments;
import com.tdu.develop.resource.pojo.Evaluation;
import com.tdu.develop.resource.service.CommentsService;
import com.tdu.develop.resource.service.EvaluationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EvaluationServiceImpl implements EvaluationService {


    @Resource
    EvaluationMapper evaluationMapper;

    @Override
    public  void addEvaluation(Evaluation evaluation) {
        System.out.println(evaluation);
        if(evaluation != null) {
            evaluationMapper.addEvaluation(evaluation);
        }
    }

    @Override
    public Evaluation getOneByTimen(String userKey, String sceneKey) {
        Evaluation  evaluation= evaluationMapper.getOneByTimen(userKey,sceneKey);
        return evaluation;
    }

    @Override
    public String getRanking(String userKey, String sceneKey) {
        String count= evaluationMapper.getRanking(userKey,sceneKey);
        return count;
    }
}
