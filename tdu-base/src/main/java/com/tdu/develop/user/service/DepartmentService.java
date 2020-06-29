package com.tdu.develop.user.service;

import com.tdu.develop.user.pojo.*;
import com.tdu.develop.resource.pojo.Knowledges;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-12-11:23
 */
public interface DepartmentService {

    public String getCollege_Id(String userkey) ;
    public void Adddepartment(Department department) ;
    public void Adddepartment2(Department department) ;
    public Boolean Deletedepartment(String departmentid) ;
    public List<Department> DepartmentList(String userId) ;
    public List<Department> DepartmentListById(String departmentid) ;
    public void Updatedepartment(Department department) ;
    public void Updatedepartment2(Department department) ;

    public void AddMajor(Major major) ;
    public Boolean DeleteMajor(String majorid) ;
    public Boolean DeleteClassBymajorid(String majorid) ;

    public Boolean DeleteSubjectBymajorid(String majorid) ;


    public Major getMajor(String majorid) ;
    public List<String> getMajorIdList(String userId);
    public List<Major> MajorListById(String majorid);
    public List<Major> MajorListByCollege_Id(String College_Id);
    public void UpdateMajor(Major major) ;
    public List<MajorUser> getAllMajorUser(String majorid);

    public List<Classes> ClassListById(String Id);
    public List<Classes> ClassListByMajor_Id(String Major_Id);
    public void AddClass(Classes classes) ;
    public void DeleteClass(String classid) ;
    public void DeleteClassUser(String classid) ;
    public void UpdateClass(Classes classes) ;
    public List<ClassUser> getAllUser(String classid);
    public void ClassAddUser(String classid, String UserId);

    public void ClassRemoveUser(String classid, String UserId);

    //subject

    public void UpdateSubject(Subjects subjects) ;


    public List<Subjects> SubjectListById(String majorid);

    public List<Subjects> getMajorSubjects(String Major_Id, String userId);
    public void AddSubject(Subjects subjects) ;
    public void AddNewKnowledge(Knowledges knowledges) ;

    public void DeleteSubjectUser(String subjectid) ;
    public void DeleteSubject(String subjectid) ;


    public void SubjectAddUser(String UserKey, String SubjectKey) ;
    public void SubjectRemoveUser(String UserKey, String SubjectKey) ;
    public List<SubjectUser> getAllSubjectUser(String subjectid);
    public void CreateAllSubjectUser(String subjectid);

    public void SaveSubject(String subjectId, String SubjectName, String Money);
    public List<UserJson> GetUserJson();
    public List<Roles> GetAllRoles();
    public List<Roles> GetUserRolesbyId(String userid);

    public void SaveUserRoles(String userid, String[] roleidarray) ;
    /**
     * 获取科目名字
     * @param majorId
     * @return
     */
    public String seleMajorName(String majorId);

    public String getCollegeName(String userId);

    public void SaveClass(String classId, String className);

    void DepartmentUser(String departmentId, String userId);

    void DepartmentRemoveUser(String departmentId, String userkey);

    void MajorAddUser(String majorId, String userId);

    void MajorRemoveUser(String majorId, String userkey);

    public List<Department> getAllDepartmentList();

    List<Major> MajorListByCollegeId(String collegeId, String userId);

    List<Classes> GetClasslistBydepartmentid(String id);
}
