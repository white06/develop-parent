package com.tdu.develop.user.mapper;

import com.tdu.develop.user.pojo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-12-11:53
 */
@Repository
public interface DepartmentMapper {


    //College
    public String getCollege_Id(String id);

    public void Adddepartment(Department department);
    public void Adddepartment2(Department department);
    public Boolean Deletedepartment(String departmentid);
    public List<Department> DepartmentList(@Param("userId") String userId);
    public List<Department> DepartmentListById(String departmentid);
    public List<Department> DepartmentListAll();
    public void Updatedepartment(Department department);
    public void Updatedepartment2(Department department);
    //	Major
    public void AddMajor(Major major);
    public Boolean DeleteMajor(String majorid);
    public List<Major> MajorListById(String majorid);
    public List<Major> MajorListByCollege_Id(String College_Id);
    public void UpdateMajor(Major major);
    public int getMajorCount();
    public List<MajorUser> getAllMajorUser(String majorid);

    public Major getMajor(String majorid);

    /**
     * 通过userId获取SubjectIdList
     * @param id
     * @return
     */
    public List<String> getMajorIdList(String id);

    //Classes
    public List<Classes> ClassListById(String Id);
    public List<Classes> ClassListByMajor_Id(String Major_Id);
    public void AddClass(Classes classes);
    public void DeleteClass(String classid);
    public void DeleteClassUser(String classid);
    public void UpdateClass(Classes classes);
    public List<ClassUser> getAllUser(String classid);
    public void ClassAddUser(@Param("Id") String Id, @Param("ClassId") String ClassId, @Param("UserId") String UserId);
    public void ClassRemoveUser(@Param("ClassId") String ClassId, @Param("UserId") String UserId);
    public int SelectClassUser(@Param("ClassId") String ClassId, @Param("UserId") String UserId);

    public Boolean DelClassByMajor_Id(String Major_Id);


    //subject

    public void UpdateSubject(Subjects subjects);

    public Boolean DelSubjectByMajor_Id(String Major_Id);

    public List<Subjects> SubjectListById(String majorid);

    public List<Subjects> getMajorSubjects(String Major_Id);
    public void AddSubject(Subjects subjects);
    public int subjectCount();
    public void DeleteSubjectUser(String subjectid);
    public void DeleteSubject(String subjectid);
    public void SubjectAddUser(@Param("Id") String Id, @Param("UserKey") String UserKey, @Param("SubjectKey") String SubjectKey, @Param("EndDate") String EndDate);
    public void SubjectRemoveUser(@Param("UserKey") String UserKey, @Param("SubjectKey") String SubjectKey);
    public int SelectSubjectUser(@Param("Userkey") String Userkey, @Param("SubjectKey") String SubjectKey);
    public List<SubjectUser> getAllSubjectUser(String subjectid);
    public List<String> AllUserInfo();
    public List<UserJson> GetUserJson();
    public void SaveSubject(@Param("subjectId") String subjectId, @Param("SubjectName") String SubjectName, @Param("Money") String Money);
    public List<Roles> GetAllRoles();
    public List<Roles> GetUserRolesbyId(String userid);
    public void AddUserRole(@Param("userid") String userid, @Param("roleid") String roleid);
    public void DeleteUserRole(String userid);
    public void AddNewKnowledge(Knowledges knowledges);
    /**
     * 获取科目名
     * @param majorId
     * @return
     */
    public String seleMajorName(@Param("majorId") String majorId);

    public void SaveClass(@Param("id") String id, @Param("className") String className);

    public String getCollegeName(@Param("userId") String userId);

    public String getSubjectName(@Param("subjectId") String subjectId);

    public String getCollerId(@Param("subjectId") String subjectId);

    public int SelectDepertmentUser(@Param("departmentId") String departmentId, @Param("userId") String userId);

    public void DepartmentUser(@Param("Id") String Id, @Param("departmentId") String departmentId, @Param("userId") String userId);

    public void DepertmentRemoveUser(@Param("departmentId") String departmentId, @Param("userkey") String userkey);

    public int SelectMajorUser(@Param("majorId") String majorId, @Param("userId") String userId);

    public void MajorAddUser(@Param("Id") String Id, @Param("majorId") String majorId, @Param("userId") String userId);

    public void MajorRemoveUser(@Param("majorId") String majorId, @Param("userkey") String userkey);

    List<Major> MajorListByUserId(@Param("college_id") String college_id, @Param("userId") String userId);

    List<Subjects> getMajorSubjectsByUserId(@Param("majorId") String majorId, @Param("userId") String userId);

    List<Classes> GetClasslistBydepartmentid(@Param("departmentId") String departmentId);
}
