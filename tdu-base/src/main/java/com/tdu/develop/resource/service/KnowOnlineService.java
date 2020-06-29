package com.tdu.develop.resource.service;

import com.tdu.develop.resource.pojo.*;
import com.tdu.develop.user.pojo.Classes;
import com.tdu.develop.user.pojo.Users;
import com.tdu.develop.util.OnlineUtil;
import com.tdu.develop.util.knowOnlineUtil;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author 志阳
 * @create 2020-04-13-17:21
 */
public interface KnowOnlineService {

    /**
     * 添加知识点打开时间
     * @param knowOnline
     */
    public void  insertKnowOnline(KnowOnline knowOnline);

    /**
     * 获取登录信息
     * @param id
     * @return
     */
    public String getKnowOnlinepaly(String id);

    /**
     * 添加离线时间，计算总时间
     * @param loginId
     * @param date
     * @throws Exception
     */
    public void insertKnowOutline(String  loginId, Date date) throws Exception;

    knowOnlineUtil getKnoUserOnLine(String knowContId, String userId);

    knowOnlineUtil getKnoOnLine(String knowContId);

    List<knowOnlineUtil> getKnoLineGroupUser(String knowContId);
}
