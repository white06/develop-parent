package com.tdu.develop.resource.service.impl;

import com.tdu.develop.common.exception.ServiceException;
import com.tdu.develop.resource.mapper.*;
import com.tdu.develop.resource.pojo.*;
import com.tdu.develop.resource.service.ExamService;
import com.tdu.develop.resource.service.KnowOnlineService;
import com.tdu.develop.user.mapper.ClassMapper;
import com.tdu.develop.user.pojo.Classes;
import com.tdu.develop.user.pojo.Users;
import com.tdu.develop.util.knowOnlineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 志阳
 * @create 2019-08-27-9:09
 */
@Service
public class KnowOnlineServiceImpl implements KnowOnlineService {

    @Autowired
    private KnowOnlineMapper knowOnlineMapper;

    @Override
    public void insertKnowOnline(KnowOnline knowOnline) {
        if(knowOnline!=null){
            knowOnlineMapper.insetKnowOnline(knowOnline);
        }
    }

    @Override
    public String getKnowOnlinepaly(String id) {
        return null;
    }

    @Override
    public void insertKnowOutline(String loginId,Date date) throws Exception{
        Date date1=knowOnlineMapper.getKnowOnlineTime(loginId);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long  fen=(date.getTime()-date1.getTime())/(1000*60);
        int onlinetime=(int)fen;
        knowOnlineMapper.setKnowOnlineTime(onlinetime,date,loginId);
    }

    @Override
    public knowOnlineUtil getKnoUserOnLine(String knowContId, String userId) {
        return knowOnlineMapper.getKnoUserOnLine(knowContId,userId);
    }

    @Override
    public knowOnlineUtil getKnoOnLine(String knowContId) {
        return knowOnlineMapper.getKnoOnLine(knowContId);
    }

    @Override
    public List<knowOnlineUtil> getKnoLineGroupUser(String knowContId) {
        return knowOnlineMapper.getKnoLineGroupUser(knowContId);
    }
}
