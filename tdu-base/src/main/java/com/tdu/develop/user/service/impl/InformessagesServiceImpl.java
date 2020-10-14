package com.tdu.develop.user.service.impl;

import com.tdu.develop.user.mapper.InformessagesMapper;
import com.tdu.develop.user.mapper.UsersMapper;
import com.tdu.develop.user.pojo.Informessages;
import com.tdu.develop.user.pojo.Reply;
import com.tdu.develop.user.service.InformessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 志阳
 * @create 2019-08-14-15:30
 */
@Service
public class InformessagesServiceImpl implements InformessagesService {
    @Autowired
    InformessagesMapper informessagesMapper;

    @Autowired
    UsersMapper usersMapper;

    public Map<String, Object> selSomeInf(int page, int size) {

        int count = informessagesMapper.selCount();
        int startNum = size * (page - 1);
        int endNum = size;
        List<Informessages> list = informessagesMapper.selSomeInf(startNum, endNum);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", count);
        map.put("data", list);
        return map;
    }

    public boolean insInfor(Informessages informessages) {

        informessagesMapper.insInfor(informessages);
        return true;
    }

    public boolean editInfor(Informessages informessages) {

        informessagesMapper.editInfor(informessages);
        return true;
    }

    public List<Informessages> selAllInf(String userId) {
        String roleID = usersMapper.getroleIdByUserId(userId);
        if (roleID.equals("9c8c0815-3968-45d0-9fae-0d42885973fc")) {
            return informessagesMapper.selAllInf();
        } else {
            return informessagesMapper.selAllInfByRole(userId);
        }
    }

    public boolean deleInforMessage(String id) {

        informessagesMapper.deleInforMessage(id);
        return true;
    }


    //保存教师的回复
    public boolean teaHuifu(Reply reply) {
        informessagesMapper.teaHuifu(reply);
        return true;
    }

    //修改回复状态，改为已回复
    public boolean upAnony1(String id) {
        informessagesMapper.upAnony1(id);
        return true;
    }

    public Informessages getInfo(String Id) {
        Informessages Info = informessagesMapper.selInfo(Id);
        return Info;
    }

    public List<Informessages> selAllInf1(String depertmentId) {

        List<Informessages> list = informessagesMapper.selAllInf1(depertmentId);
        return list;
    }

    public List<Informessages> selAllInf2(String depertmentId) {

        List<Informessages> list = informessagesMapper.selAllInf2(depertmentId);
        return list;
    }

    public List<Informessages> selAllInf3(String depertmentId) {

        List<Informessages> list = informessagesMapper.selAllInf3(depertmentId);
        return list;
    }
}
