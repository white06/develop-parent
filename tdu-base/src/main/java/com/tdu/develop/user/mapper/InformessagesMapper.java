package com.tdu.develop.user.mapper;

import com.tdu.develop.user.pojo.Informessages;
import com.tdu.develop.user.pojo.Reply;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-14-15:39
 */
@Repository
public interface InformessagesMapper {
    public List<Informessages> selSomeInf(@Param("startNum") int startNum, @Param("endNum") int endNum);

    public void insInfor(Informessages informessages);

    public void editInfor(Informessages informessages);

    public List<Informessages> selAllInf();

    public void deleInforMessage(String id);

    /******************************************************************************************************/


    public Integer selStuMessageSize();


    public void teaHuifu(Reply reply);

    public void upAnony1(String id);

    public Integer selCount();


    public Informessages selInfo(String id);


    public List<Informessages> selAllInf1(String departmentId);

    public List<Informessages> selAllInf2(String departmentId);

    public List<Informessages> selAllInf3(String departmentId);

    List<Informessages> selAllInfByRole(@Param("userId") String userId);
}
