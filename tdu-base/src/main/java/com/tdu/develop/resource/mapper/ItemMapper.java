package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Item;
import com.tdu.develop.resource.pojo.Link;
import com.tdu.develop.resource.pojo.blue;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-27-17:37
 */
@Repository
public interface ItemMapper {
    /**
     * 福建农林大学-永恒之蓝部分
     * @param Blue
     */
    void yuxi(blue Blue);

    blue get(String userId);

    String getyuxi(String userId);

    blue getblue(String userId);

    void tijiaocaozuo(blue Blue);

    void tijiaobaogao(blue Blue);

    /**
     * crm部分
     */
    List<Item> getItemInfo(@Param("customerId")String customerId);


}
