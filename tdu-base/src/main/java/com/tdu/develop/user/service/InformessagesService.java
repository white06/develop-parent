package com.tdu.develop.user.service;

import com.tdu.develop.user.pojo.Informessages;
import com.tdu.develop.user.pojo.Reply;

import java.util.List;
import java.util.Map;

/**
 * @author 志阳
 * @create 2019-08-14-15:30
 */
public interface InformessagesService {


    public Map<String, Object> selSomeInf(int page, int size);

    public boolean insInfor(Informessages informessages);

    public boolean editInfor(Informessages informessages);

    public List<Informessages> selAllInf(String userId);

    public boolean deleInforMessage(String id);





    //保存教师的回复
    public boolean teaHuifu(Reply reply);

    //修改回复状态，改为已回复
    public boolean upAnony1(String id);

    public Informessages getInfo(String Id);


    public List<Informessages> selAllInf1(String depertmentId);

    public List<Informessages> selAllInf2(String depertmentId);

    public List<Informessages> selAllInf3(String depertmentId);

}
