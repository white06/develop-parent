package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.*;
import com.tdu.develop.util.knowOnlineUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-27-9:10
 */
@Repository
public interface KnowOnlineMapper {
    public void insetKnowOnline(KnowOnline knowOnline);

    public Date getKnowOnlineTime(@Param("loginId")String loginId);

    public void setKnowOnlineTime(@Param("onlineTime")int onlineTime,@Param("outTime")Date outTime,@Param("loginId")String loginId);

    List<Object> getHalfYear();

    knowOnlineUtil getKnoUserOnLine(@Param("knowContId")String knowContId, @Param("userId")String userId);

    knowOnlineUtil getKnoOnLine(@Param("knowContId")String knowContId);

    List<knowOnlineUtil> getKnoLineGroupUser(@Param("knowContId")String knowContId);
}
