package com.tdu.develop.user.service.impl;

import com.tdu.develop.resource.pojo.ImgURL;
import com.tdu.develop.user.mapper.ImgUrlMapper;
import com.tdu.develop.user.mapper.SeduBanjiMapper;
import com.tdu.develop.user.mapper.SeduMapper;
import com.tdu.develop.user.pojo.SeduBanji;
import com.tdu.develop.user.pojo.Sedubaogao;
import com.tdu.develop.user.service.ImgUrlService;
import com.tdu.develop.user.service.SeduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-30-14:16
 */
@Service
public class SeduServiceImpl implements SeduService {

    @Autowired
    SeduMapper seduMapper;
    @Autowired
    SeduBanjiMapper seduBanjiMapper;

    public void inssuduinfo(SeduBanji seduBanji){
        seduBanjiMapper.inssuduinfo(seduBanji);
    }


    public SeduBanji getsuduinfo(String userId){
        if(seduBanjiMapper.getsuduinfo(userId)==null){
            return null;
        }else{
            return seduBanjiMapper.getsuduinfo(userId);
        }

    }

    public String getCount(){
        String count=seduMapper.getCount();
        int count1=Integer.parseInt(count);
        count1++;
        String countAdd=""+count1;
        seduMapper.addCount1(countAdd);
        return countAdd;
    }

    public void addCount(Integer count){
        Integer newcount=count++;
        seduMapper.addCount(newcount);
    }

    public boolean addbaogao(String id,String riqi,String wenti,String userId){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] parts = riqi.split("/");
        String newDate=parts[0]+"-"+parts[1]+"-"+parts[2];
        Date date=new Date();
        try {
           date = simpleDateFormat.parse(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Sedubaogao sedubaogao=new Sedubaogao();
        sedubaogao.setId(id);
        sedubaogao.setWenti(wenti);
        sedubaogao.setRiqi(date);
        sedubaogao.setUserId(userId);
        seduMapper.addbaogao(sedubaogao);
        return true;
    }

    public Sedubaogao getbaogao(String userId){
        Sedubaogao sedubaogao=new Sedubaogao();
        if(seduMapper.getbaogao(userId)!=null){
            sedubaogao=seduMapper.getbaogao(userId);
        }
        return sedubaogao;
    }
}
