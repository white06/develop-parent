package com.tdu.develop.user.mapper;

import com.tdu.develop.user.pojo.EmailUser;
import com.tdu.develop.user.pojo.UserOnline;
import com.tdu.develop.user.pojo.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.List;

/**
 * @author 志阳
 * @create 2020-02-26-16:13
 */
@Repository
public interface UseronlineMapper {
    /**
     * 邮件
     */
    public void insetUserLogin(UserOnline userOnline);

    public String getLoginTime(@Param("loginId") String loginId);

    public void setOnlineTime(@Param("onlineTime") int onlineTime, @Param("outTime") String outTime, @Param("loginId") String loginId);

    public List<UserOnline> getOnline(@Param("userId") String userId);
}
