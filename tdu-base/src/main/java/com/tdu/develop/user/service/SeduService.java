package com.tdu.develop.user.service;

import com.tdu.develop.resource.pojo.ImgURL;
import com.tdu.develop.user.pojo.SeduBanji;
import com.tdu.develop.user.pojo.Sedubaogao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-30-14:16
 */
public interface SeduService {


    public void inssuduinfo(SeduBanji seduBanji);
    public SeduBanji getsuduinfo(String userId);
    public String getCount();

    public void addCount(Integer count);

    public boolean addbaogao(String id,String riqi,String wenti,String userId);

    public Sedubaogao getbaogao(String userId);
}
