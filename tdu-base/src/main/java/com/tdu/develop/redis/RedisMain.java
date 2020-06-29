package com.tdu.develop.redis;

import com.tdu.develop.user.service.UsersService;
import com.tdu.develop.user.service.impl.UserServiceImpl;
import com.tdu.develop.util.MonthInYearUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author 志阳
 * @create 2020-04-16-11:17
 */
@Component
public class RedisMain {
    @Autowired
    UsersService usersService=new UserServiceImpl();

    @PostConstruct // 构造函数之后执行
    public void init(){
        System.out.println("容器启动后执行");
        final long timeInterval = 86400000;//一天
        Runnable runnable = new Runnable() {
            public void run() {
                while (true) {
                    startJob();
                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();




    }

    public void startJob(){

        //饼图数据获取存入redis
        HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
        String dayCount =  usersService.getUserOnLineDay()+"";
        String monthCount =  usersService.getUserOnLineMonth()+"";
        String preMonthCount =  usersService.getUserOnLinePreMonth()+"";
        boolean flag1 = RedisUtil.set("dayCount", dayCount,Long.parseLong("86400"));
        boolean flag2 =RedisUtil.set("monthCount", monthCount,Long.parseLong("86400"));
        boolean flag3 = RedisUtil.set("preMonthCount", preMonthCount,Long.parseLong("86400"));

        //曲线图数据获取
        List<Object> monthInYearUtil =  usersService.getMonthInYear();
        /*List<Object> monthInYearCount = usersService.getMonthInYearCount();*/

        List<Object> halfYearUtil =  usersService.getHalfYear();

        List<Object> halfYearUserUtil =  usersService.getHalfYearUser();

        boolean flag4 = RedisUtil.setList("getMonthInYear", monthInYearUtil,Long.parseLong("86400"));
        boolean flag5 = RedisUtil.setList("getHalfYear", halfYearUtil,Long.parseLong("86400"));
        boolean flag6 = RedisUtil.setList("getHalfYearUser", halfYearUserUtil,Long.parseLong("86400"));
       /* boolean flag5 = RedisUtil.setList("getMonthInYearCount", monthInYearCount,Long.parseLong("86400"));*/
        if (flag1&&flag2&flag3) {
            // 读取缓存
            System.out.println("成功写入缓存");
        } else {
            System.out.println("写入缓存失败");
        }

        System.out.println(String.valueOf(RedisUtil.get("dayCount")));
        System.out.println(String.valueOf(RedisUtil.get("monthCount")));
        System.out.println(String.valueOf(RedisUtil.get("preMonthCount")));
        System.out.println(RedisUtil.getList("getMonthInYear"));
        System.out.println(RedisUtil.getList("getHalfYear"));
        System.out.println(RedisUtil.getList("getHalfYearUser"));
        List<?> list = RedisUtil.getList("getMonthInYear");
       /* System.out.println(RedisUtil.getList("getMonthInYearCount"));*/
        /**
         * 测试模板
         */

//        // 写入一个缓存
//        boolean flag = RedisUtil.set("x", "轩");
//        if (flag) {
//            // 读取缓存
//            System.out.println("成功写入缓存");
//            System.out.println("正在读取缓存......");
//            String xuan = String.valueOf(RedisUtil.get("x"));
//            System.out.println("你读取的缓存为:" + xuan);
//        } else {
//            System.out.println("写入缓存失败");
//        }
//
//        //写入一个带时间的缓存 30秒消失
//        //可以自己去验证是否正确
//        boolean flag1 = RedisUtil.set("xuan", "关注我博客~",Long.parseLong("30"));
//        if (flag){
//            System.out.println("写入成功");
//        }
    }
}
