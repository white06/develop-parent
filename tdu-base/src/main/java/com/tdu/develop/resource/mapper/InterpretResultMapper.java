package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.InterpretResult;
import com.tdu.develop.resource.pojo.InterpretScore;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InterpretResultMapper {

    public  void addInterpretResult(InterpretResult interpretResult);

    public void addInterpretScore(InterpretScore interpretScore);

    InterpretScore getInterpretScore(@Param("userKey") String userKey,@Param("nandu") String nandu, @Param("page") String page,@Param("pageKey") String pageKey);
}
