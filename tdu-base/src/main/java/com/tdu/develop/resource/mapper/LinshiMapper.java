package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Link;
import com.tdu.develop.resource.pojo.Linshi;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-27-17:37
 */
@Repository
public interface LinshiMapper {

    void insert(Linshi Linshi);

    Linshi get(@Param("userId") String userId);
}
