package com.tdu.develop.user.mapper;

import com.tdu.develop.resource.pojo.StuQueInfors;
import com.tdu.develop.user.pojo.Classes;
import com.tdu.develop.user.pojo.Member;
import com.tdu.develop.user.pojo.Sedubaogao;
import com.tdu.develop.user.pojo.Subjects;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeduMapper {

    public String getCount();

    public void addCount1(@Param("count") String count);

    public void addCount(@Param("count") Integer count);

    public void addbaogao( Sedubaogao sedubaogao);

    public Sedubaogao getbaogao(@Param("userId")String userId);
}
