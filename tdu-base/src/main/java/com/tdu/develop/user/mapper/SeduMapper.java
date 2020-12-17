package com.tdu.develop.user.mapper;

import com.tdu.develop.resource.pojo.StuQueInfors;
import com.tdu.develop.user.pojo.Classes;
import com.tdu.develop.user.pojo.Member;
import com.tdu.develop.user.pojo.Subjects;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeduMapper {

    public Integer getCount();

    public void addCount(@Param("count") Integer count);
}
