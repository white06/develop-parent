package com.tdu.develop.user.mapper;

import com.tdu.develop.user.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-07-15:06
 */
@Repository
public interface LoginCountMapper {


    public void addCount(LoginCount loginCount);

    public int getCount();

}
