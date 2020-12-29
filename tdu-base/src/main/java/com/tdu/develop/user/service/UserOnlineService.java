package com.tdu.develop.user.service;

import com.tdu.develop.user.pojo.Auth;
import com.tdu.develop.user.pojo.AuthDatas;

import java.util.List;
import java.util.Map;

/**
 * @author 志阳
 * @create 2020-10-26-15:37
 */
public interface UserOnlineService {
    public Map<String,Object> getseducount(String userId);
}
