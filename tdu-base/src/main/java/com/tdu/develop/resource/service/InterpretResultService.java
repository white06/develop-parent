package com.tdu.develop.resource.service;

import com.tdu.develop.resource.pojo.InterpretResult;
import com.tdu.develop.resource.pojo.InterpretScore;


public interface InterpretResultService {

    public void addInterpretResult(InterpretResult interpretResult);

    public void addInterpretScore(InterpretScore interpretScore);

    public InterpretScore getInterpretScore(String userKey, String nandu, String page, String pageKey);
}
