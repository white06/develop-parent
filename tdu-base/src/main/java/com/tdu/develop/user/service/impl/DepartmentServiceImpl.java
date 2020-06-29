package com.tdu.develop.user.service.impl;

import com.tdu.develop.resource.mapper.AddKnowledgeMapper;
import com.tdu.develop.user.mapper.DepartmentMapper;
import com.tdu.develop.user.mapper.UsersMapper;
import com.tdu.develop.user.pojo.*;
import com.tdu.develop.user.service.DepartmentService;
import com.tdu.develop.resource.pojo.Knowledges;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 志阳
 * @create 2019-08-13-11:09
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    UsersMapper usersMapper;

    @Autowired
    AddKnowledgeMapper addKnowledgeMapper;
    
    
    public String getCollege_Id(String userkey) {
       
        return departmentMapper.getCollege_Id(userkey);
    }
    public void Adddepartment(Department department) {
       
        departmentMapper.Adddepartment(department);
    }
    public void Adddepartment2(Department department) {

        departmentMapper.Adddepartment2(department);
    }
    public Boolean Deletedepartment(String departmentid) {
       
        return departmentMapper.Deletedepartment(departmentid);
    }
    public List<Department> DepartmentList(String userId) {
        String roleID= usersMapper.getroleIdByUserId(userId);
        if(roleID.equals("9c8c0815-3968-45d0-9fae-0d42885973fc")){
            return departmentMapper.DepartmentListAll();
        }else{
            return departmentMapper.DepartmentList(userId);
        }
       

    }

    public List<Department> getAllDepartmentList() {
            return departmentMapper.DepartmentListAll();
    }

    public List<Department> DepartmentListById(String departmentid) {
       
        return departmentMapper.DepartmentListById(departmentid);
    }
    public void Updatedepartment(Department department) {
       
        departmentMapper.Updatedepartment(department);
    }

    public void Updatedepartment2(Department department) {

        departmentMapper.Updatedepartment2(department);
    }
    public void AddMajor(Major major) {
       
        int order=departmentMapper.getMajorCount();
        major.setMajorOrder(order+1);
        departmentMapper.AddMajor(major);
    }
    public Boolean DeleteMajor(String majorid) {
       

        return departmentMapper.DeleteMajor(majorid);
    }
    public Boolean DeleteClassBymajorid(String majorid) {
       
        return departmentMapper.DelClassByMajor_Id(majorid);
    }

    public Boolean DeleteSubjectBymajorid(String majorid) {
       
        return departmentMapper.DelSubjectByMajor_Id(majorid);
    }


    public Major getMajor(String majorid) {
       
        return departmentMapper.getMajor(majorid);
    }

    public List<String> getMajorIdList(String userId){
       
        return departmentMapper.getMajorIdList(userId);
    }
    public List<Major> MajorListById(String majorid){
       
        return departmentMapper.MajorListById(majorid);
    }
    public List<Major> MajorListByCollege_Id(String College_Id){
       
        return departmentMapper.MajorListByCollege_Id(College_Id);
    }
    public List<Major> MajorListByCollegeId(String College_Id,String userId){
        String roleID= usersMapper.getroleIdByUserId(userId);
        if(roleID.equals("9c8c0815-3968-45d0-9fae-0d42885973fc")){
            return departmentMapper.MajorListByCollege_Id(College_Id);
        }else{
            return departmentMapper.MajorListByUserId(College_Id,userId);
        }
    }

    @Override
    public List<Classes> GetClasslistBydepartmentid(String id) {
        return departmentMapper.GetClasslistBydepartmentid(id);
    }

    public void UpdateMajor(Major major) {
       
        List<Major> lMajors=new ArrayList<Major>();
        lMajors=departmentMapper.MajorListById(major.Id);
        for (Major major2 : lMajors) {
            major.setCollege_Id(major2.College_Id);
            major.setMajorOrder(major2.MajorOrder);
        }

        departmentMapper.UpdateMajor(major);
    }
    public List<MajorUser> getAllMajorUser(String majorid){
       
        return	departmentMapper.getAllMajorUser(majorid);
    }
    //-classes

    public List<Classes> ClassListById(String Id){
       
        return departmentMapper.ClassListById(Id);
    }
    public List<Classes> ClassListByMajor_Id(String Major_Id){
       
        return departmentMapper.ClassListByMajor_Id(Major_Id);
    }
    public void AddClass(Classes classes) {
       
        departmentMapper.AddClass(classes);
    }
    public void DeleteClass(String classid) {
       
        departmentMapper.DeleteClass(classid);
    }
    public void DeleteClassUser(String classid) {
       
        departmentMapper.DeleteClassUser(classid);
    }
    public void UpdateClass(Classes classes) {
       
        departmentMapper.UpdateClass(classes);
    }
    public List<ClassUser> getAllUser(String classid){
       
        return	departmentMapper.getAllUser(classid);
    }
    public void ClassAddUser(String classid,String UserId){
       
        int rs=departmentMapper.SelectClassUser(classid, UserId);
        if(rs>0) {

        }else {
            String idString= UUID.randomUUID().toString();
            departmentMapper.ClassAddUser(idString,classid,UserId);
        }
    }

    public void DepartmentUser(String departmentId,String userId){

        int rs=departmentMapper.SelectDepertmentUser(departmentId, userId);
        if(rs>0) {

        }else {
            String idString= UUID.randomUUID().toString();
            departmentMapper.DepartmentUser(idString,departmentId,userId);
        }
    }
    public void MajorAddUser(String majorId,String userId){

        int rs=departmentMapper.SelectMajorUser(majorId, userId);
        if(rs>0) {

        }else {
            String idString= UUID.randomUUID().toString();
            departmentMapper.MajorAddUser(idString,majorId,userId);
        }
    }

    public void ClassRemoveUser(String classid,String UserId){

        int rs=departmentMapper.SelectClassUser(classid, UserId);
        if(rs>0) {
            departmentMapper.ClassRemoveUser(classid,UserId);
        }

    }

    public void DepartmentRemoveUser(String departmentId,String userkey){

        int rs=departmentMapper.SelectDepertmentUser(departmentId, userkey);
        if(rs>0) {
            departmentMapper.DepertmentRemoveUser(departmentId,userkey);
        }

    }
    public void MajorRemoveUser(String majorId,String userkey){

        int rs=departmentMapper.SelectMajorUser(majorId, userkey);
        if(rs>0) {
            departmentMapper.MajorRemoveUser(majorId,userkey);
        }

    }

    //subject


    public void UpdateSubject(Subjects subjects) {

        departmentMapper.UpdateSubject(subjects);
    }

    public List<Subjects> SubjectListById(String majorid){
       
        return departmentMapper.SubjectListById(majorid);
    }

    public List<Subjects> getMajorSubjects(String Major_Id,String userId){
        String roleID= usersMapper.getroleIdByUserId(userId);
        if(roleID.equals("9c8c0815-3968-45d0-9fae-0d42885973fc")){
            return departmentMapper.getMajorSubjects(Major_Id);
        }else{
            return departmentMapper.getMajorSubjectsByUserId(Major_Id,userId);
        }
    }
    public void AddSubject(Subjects subjects) {
       
        int rs=departmentMapper.subjectCount()+1;
        subjects.setSubjectOrder(String.valueOf(rs));
        departmentMapper.AddSubject(subjects);
    }
//    public void AddNewSubjectTree(SubjectTrees subjectTrees) {
//
//        departmentMapper.AddNewSubjectTree(subjectTrees);
//    }
    public void AddNewKnowledge(Knowledges knowledges) {

        addKnowledgeMapper.AddNewKnowledge(knowledges);
    }

    public void DeleteSubjectUser(String subjectid) {
       
        departmentMapper.DeleteSubjectUser(subjectid);
    }
    public void DeleteSubject(String subjectid) {
       
        departmentMapper.DeleteSubject(subjectid);
    }


    public void SubjectAddUser(String UserKey, String SubjectKey) {
       
        int rs=departmentMapper.SelectSubjectUser(UserKey, SubjectKey);
        if(rs>0) {

        }else{
            java.util.Date now=new java.util.Date();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");

            String idString=UUID.randomUUID().toString();
            departmentMapper.SubjectAddUser(idString,UserKey,SubjectKey,simpleDateFormat.format(now));
        }


    }
    public void SubjectRemoveUser(String UserKey,String SubjectKey) {
       
        int rs=departmentMapper.SelectSubjectUser(UserKey, SubjectKey);
        if(rs>0) {
            departmentMapper.SubjectRemoveUser(UserKey,SubjectKey);
        }

    }
    public List<SubjectUser> getAllSubjectUser(String subjectid){
       
        return	departmentMapper.getAllSubjectUser(subjectid);
    }
    public void CreateAllSubjectUser(String subjectid){
       
        List<String> uArray=departmentMapper.AllUserInfo();
        for (String object : uArray) {
            this.SubjectAddUser(object,subjectid);
        }
    }

    public void SaveSubject(String subjectId,String SubjectName,String Money){
       
        departmentMapper.SaveSubject(subjectId,SubjectName,Money);
    }
    public List<UserJson> GetUserJson(){
       
        return departmentMapper.GetUserJson();
    }
    public List<Roles> GetAllRoles(){
       
        return departmentMapper.GetAllRoles();
    }
    public List<Roles> GetUserRolesbyId(String userid){
       
        return departmentMapper.GetUserRolesbyId(userid);
    }

    public void SaveUserRoles(String userid,String[] roleidarray) {
       
        departmentMapper.DeleteUserRole(userid);
        for (String roleid : roleidarray) {
            departmentMapper.AddUserRole(userid, roleid);
        }

    }
    /**
     * 获取科目名字
     * @param majorId
     * @return
     */
    public String seleMajorName(String majorId){
       
        return departmentMapper.seleMajorName(majorId);
    }

    public void SaveClass(String classId,String className){
       
        departmentMapper.SaveClass(classId,className);
    }

    public String getCollegeName(String userId) {
        // TODO Auto-generated method stub
        return departmentMapper.getCollegeName(userId);
    }

}
