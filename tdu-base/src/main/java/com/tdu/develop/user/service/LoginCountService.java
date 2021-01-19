package com.tdu.develop.user.service;

import com.tdu.develop.user.pojo.*;

import java.util.Date;
import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-07-15:25
 */
public interface LoginCountService {

    public void addCount(LoginCount loginCount);

    public int getCount();

}
