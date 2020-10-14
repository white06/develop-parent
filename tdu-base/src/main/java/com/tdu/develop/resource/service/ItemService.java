package com.tdu.develop.resource.service;


import com.tdu.develop.resource.pojo.Link;
import com.tdu.develop.resource.pojo.Linshi;
import com.tdu.develop.resource.pojo.blue;

import java.util.List;
import java.util.Map;

/**
 * @author 志阳
 * @create 2020-06-10-14:39
 */
public interface ItemService {

    void yuxi(String yuxi, String userId);

    String getyuxi(String userId);

    blue getblue(String userId);

    void tijiaocaozuo(String userId, String caozuo);

    void tijiaobaogao(String userId, String baogao);

    blue get(String userId);
}
