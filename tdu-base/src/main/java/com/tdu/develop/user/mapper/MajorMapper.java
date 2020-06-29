package com.tdu.develop.user.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-19-9:46
 */
@Repository
public interface MajorMapper {
    public List<String> getMajorName(@Param("userId") String userId);
}
