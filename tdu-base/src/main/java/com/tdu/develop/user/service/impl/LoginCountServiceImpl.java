package com.tdu.develop.user.service.impl;

import com.tdu.develop.resource.mapper.KnowOnlineMapper;
import com.tdu.develop.user.mapper.*;
import com.tdu.develop.user.pojo.*;
import com.tdu.develop.user.service.LoginCountService;
import com.tdu.develop.user.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 志阳
 * @create 2019-08-07-15:28
 */
@Service
public class LoginCountServiceImpl implements LoginCountService {

    @Autowired
    LoginCountMapper loginCountMapper;


    @Override
    public void addCount(LoginCount loginCount) {
        loginCountMapper.addCount(loginCount);
    }

    @Override
    public int getCount() {
        int count = loginCountMapper.getCount();
        return count;
    }
}
