package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.InterpretResultMapper;
import com.tdu.develop.resource.pojo.InterpretResult;
import com.tdu.develop.resource.pojo.InterpretScore;
import com.tdu.develop.resource.service.InterpretResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterpretResultServiceImpl implements InterpretResultService {

    @Autowired
    InterpretResultMapper interpretResultMapper;

    @Override
    public void addInterpretResult(InterpretResult interpretResult) {
        interpretResultMapper.addInterpretResult(interpretResult);
    }

    @Override
    public void addInterpretScore(InterpretScore interpretScore) {
        interpretResultMapper.addInterpretScore(interpretScore);
    }

    @Override
    public InterpretScore getInterpretScore(String userKey, String nandu, String page,String pageKey) {
        return interpretResultMapper.getInterpretScore(userKey,  nandu,  page,pageKey);
    }
}
