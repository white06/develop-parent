package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Comments;
import com.tdu.develop.resource.pojo.Interpret;
import org.springframework.stereotype.Repository;

@Repository
public interface InterpretMapper {

    public  void addInterpret(Interpret interpret);
}
