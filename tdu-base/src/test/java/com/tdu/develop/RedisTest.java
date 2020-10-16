package com.tdu.develop;

import com.tdu.develop.redis.RedisUtil;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author 志阳
 * @create 2020-04-15-17:56
 */
public class RedisTest {

    public static void main(String[] args) {
        //连接本地的 Redis 服务
//        Jedis jedis = new Jedis("localhost");
//        System.out.println("连接成功");
//        //查看服务是否运行
//        System.out.println("服务正在运行: "+jedis.ping());
//        //存储数据到列表中
//        jedis.lpush("site-list", "Runoob");
//        jedis.lpush("site-list", "Google");
//        jedis.lpush("site-list", "Taobao");
//        // 获取存储的数据并输出
//        List<String> list = jedis.lrange("site-list", 0 ,0);
//        for(int i=0; i<list.size(); i++) {
//            System.out.println("列表项为: "+list.get(i));
//        }
//        // 获取数据并输出
//        Set<String> keys = jedis.keys("*");
//        Iterator<String> it=keys.iterator() ;
//        while(it.hasNext()){
//            String key = it.next();
//            System.out.println(key);
//        }


        // 写入一个缓存
        boolean flag = RedisUtil.set("x", "轩");
        if (flag) {
            // 读取缓存
            System.out.println("成功写入缓存");
            System.out.println("正在读取缓存......");
            String xuan = String.valueOf(RedisUtil.get("x"));
            System.out.println("你读取的缓存为:" + xuan);
        } else {
            System.out.println("写入缓存失败");
        }

        //写入一个带时间的缓存 30秒消失
        //可以自己去验证是否正确
        boolean flag1 = RedisUtil.set("xuan", "关注我博客~", Long.parseLong("30"));
        if (flag) {
            System.out.println("写入成功");
        }
    }
}
