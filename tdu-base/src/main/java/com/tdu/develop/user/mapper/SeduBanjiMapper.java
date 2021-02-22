package com.tdu.develop.user.mapper;

import com.tdu.develop.user.pojo.SeduBanji;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeduBanjiMapper {
    public void inssuduinfo(SeduBanji seduBanji);

   public SeduBanji getsuduinfo(@Param("userId") String userId);
}
