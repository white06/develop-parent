package com.tdu.develop.user.service;

import com.tdu.develop.resource.pojo.ImgURL;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-30-14:16
 */
public interface SeduService {
    public Integer getCount();

    public void addCount(Integer count);
}
