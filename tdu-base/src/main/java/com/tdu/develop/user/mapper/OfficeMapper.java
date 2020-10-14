package com.tdu.develop.user.mapper;

import java.util.List;

import com.tdu.develop.resource.pojo.StuQueInfors;
import com.tdu.develop.user.pojo.Classes;
import com.tdu.develop.user.pojo.Member;
import com.tdu.develop.user.pojo.Subjects;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeMapper {

    List<Classes> getclasses();

    List<String> getUsersId(String classId);

    List<String> getUsersIdByCollege(String departmentKey);

    Member getUsersInfos(@Param("str") String str);

    List<String> getAllStudents();

    List<String> getAllTeachers();

    @Select("SELECT User_Id FROM userroles WHERE Role_Id='9c8c0815-3968-45d0-9fae-0d42885973fc'")
    List<String> getAllAdmins();

    List<String> getclassesOfClassName();

    void createClass(Classes cl);


    String getKnowledgeContentId(String SubjectTree_Id);

    String getSubTreeId(String SubjectKey);

    String getchengji(StuQueInfors stuQueInfors);

    List<Subjects> getSubject();

    List<Subjects> getNurseSubject();


    List<Classes> getclasses2();

    List<String> getUsersId2(String classId);


    Member getUsersInfos2(String str);


    List<String> getAllStudents2();


    List<String> getAllNurseStudents();

    String getNurseScore(StuQueInfors stuQueInfors);

    List<Classes> getNurseClasses();
}
