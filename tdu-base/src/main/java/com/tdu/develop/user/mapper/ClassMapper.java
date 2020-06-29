package com.tdu.develop.user.mapper;

import com.tdu.develop.user.pojo.ClassUsers;
import com.tdu.develop.user.pojo.Classes;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-07-15:06
 */
@Repository
public interface ClassMapper {

    //11.30
    public String seleClassName(String classkey);

    /**
     * 查询表中所有的Id和班级名
     * @return
     */
    List<Classes> selClassesOfAll();
    /**
     * 根据用户id查询出用户所在班级
     * @param userId
     * @return
     */
    public String selUserClassId(@Param("userId") String userId);
    /**
     * 根据班级id获取该班级用户id
     * @param classId
     * @return
     */
    public List<String> selClassUsers(@Param("classId") String classId);


    List<ClassUsers> getClassUsersList(@Param("userKey") String userKey);

}
