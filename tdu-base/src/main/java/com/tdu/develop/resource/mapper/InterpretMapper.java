package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Interpret;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterpretMapper {

    public  void addInterpret(Interpret interpret);

    public List<Interpret> getInterResultList(@Param("nandu") String nandu, @Param("page") String page,
                                              @Param("userKey") String userKey, @Param("pageKey") String pageKey);
}
