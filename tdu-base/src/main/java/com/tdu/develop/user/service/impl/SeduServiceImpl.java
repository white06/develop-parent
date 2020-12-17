package com.tdu.develop.user.service.impl;

import com.tdu.develop.resource.pojo.ImgURL;
import com.tdu.develop.user.mapper.ImgUrlMapper;
import com.tdu.develop.user.mapper.SeduMapper;
import com.tdu.develop.user.service.ImgUrlService;
import com.tdu.develop.user.service.SeduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-30-14:16
 */
@Service
public class SeduServiceImpl implements SeduService {

    @Autowired
    SeduMapper seduMapper;
    public Integer getCount(){
        return seduMapper.getCount();
    }

    public void addCount(Integer count){
        Integer newcount=count++;
        seduMapper.addCount(newcount);
    }

}
