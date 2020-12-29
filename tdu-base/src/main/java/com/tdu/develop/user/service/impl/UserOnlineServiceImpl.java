package com.tdu.develop.user.service.impl;

import com.tdu.develop.resource.pojo.ImgURL;
import com.tdu.develop.user.mapper.ImgUrlMapper;
import com.tdu.develop.user.mapper.UseronlineMapper;
import com.tdu.develop.user.pojo.UserOnline;
import com.tdu.develop.user.service.ImgUrlService;
import com.tdu.develop.user.service.UserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 志阳
 * @create 2019-08-30-14:16
 */
@Service
public class UserOnlineServiceImpl implements UserOnlineService {
    @Autowired
    UseronlineMapper useronlineMapper;
    public Map<String,Object> getseducount(String userId){
        List<UserOnline> onlineList=useronlineMapper.getOnline(userId);
        Map<String,Object> onlineMap=new HashMap<String,Object>();
        onlineMap.put("count",onlineList.size());
        Integer count=0;
        for(int i=0;i<onlineList.size();i++){
            count+=onlineList.get(i).getOnlineTime();
        }
        onlineMap.put("time",count);
        return onlineMap;
    }
}
