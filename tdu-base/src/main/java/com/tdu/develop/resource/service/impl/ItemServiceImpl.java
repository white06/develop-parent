package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.ItemMapper;
import com.tdu.develop.resource.pojo.blue;
import com.tdu.develop.resource.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author 志阳
 * @create 2020-06-10-14:41
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemMapper itemMapper;

    public void yuxi(String yuxi,String userId){
        if(userId!=null){
            blue Blue=itemMapper.get(userId);
            Blue.setYuxi(yuxi);
            itemMapper.yuxi(Blue);
        }
    }


    public String getyuxi(String userId){
        return itemMapper.getyuxi(userId);
    }

    public blue getblue(String userId){
        return itemMapper.getblue(userId);
    }


    public void tijiaocaozuo(String userId,String caozuo) {
        if (userId != null) {
            blue Blue = itemMapper.get(userId);
            Blue.setCaozuo(caozuo);
            itemMapper.tijiaocaozuo(Blue);
        }
    }

    public void tijiaobaogao(String userId,String baogao) {
        if (userId != null) {
            blue Blue = itemMapper.get(userId);
            Blue.setTi11(baogao);
            itemMapper.tijiaobaogao(Blue);
        }
    }

    public blue get(String userId) {
       return itemMapper.get(userId);
    }
}
