package com.tdu.develop.user.mapper;

import com.tdu.develop.user.pojo.*;
import com.tdu.develop.util.OnlineUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-13-11:47
 */
@Repository
public interface SubjectMapper {
    public List<Subjects> getSubjectIdList2(String UserKey);

    public List<Subjects> getSubjectIdList(String UserKey);

    public List<Subjects> getSubjectIdList_develop(String UserKey);

    public List<Subjects> getSubjectIdList_resource(String UserKey);


    public Subjects getSubject(String id);

    public List<Subjects> seleSub(Users users);

    /**
     * 将科目状态为1的改为0
     * subjectusers表
     * @param userId
     * @return
     */
    public int alterstatus(String userId);
    /**
     * 根据科目id修改状态为1
     * subjectusers表
     * @param subjectId
     * @param userId
     */
    public int updateStatus(@Param("subjectId") String subjectId, @Param("userId") String userId);
    /**
     * 根据用户id获取科目id
     * subjectusers表
     * @param userId
     * @return
     */
    public String getSubjectKey(String userId);
    /**
     * 根据用户id获取初始科目id
     *
     * @param userId
     * @return
     */
    public String seleinitSubject(String userId);
    /**
     * 查询背景图片
     * subjectbackgrounds表
     * @return
     */
    public String queryBackground();
    /**
     * 查询logo
     * subjectbackgrounds表
     * @return
     */
    public String queryLogo();
    /**
     * 更新图片名
     * subjectbackgrounds表
     * @param sb
     */
    public void updateSubjectBackgrounds(SubjectBackgrounds sb);
    /**
     * 查询所有的科目的背景图片和logo等数据
     * subjectbackgrounds表
     * @return
     */
    public List<SubjectBackgrounds> querySubjectBackgrounds();
    /**
     * 查询用户所属科目，针对单一科目
     * @param userId
     * @return
     */
    public String getSubId(String userId);
    /**
     * 获取专业名字
     * @param majorId
     * @return
     */
    public String getMajorName(String majorId);
    /**
     * 获取所有科目包含专业
     * @param majorId
     * @return
     */
    public List<Subjects> getMajorSub(String majorId);
    /**
     * 获取知识点nmae
     * @param knowledgecontentId
     * @return
     */
    public String getKnowNmae(@Param("knowledgecontentId") String knowledgecontentId);
    /**
     * 获取科目树id第一个
     * @param userId
     * @return
     */
    public List<String> getSubIdOne(@Param("userId") String userId);

    /**
     * 获取科目树id第一个(开发者平台使用）
     * @param userId
     * @return
     */
    public List<String> getSubIdOne_develop(@Param("userId") String userId);


    public List<String> getUserIdForAll(@Param("SubjectKey") String SubjectKey);

    /**
     * 获取所有的科目信息
     * @return
     */
    public List<Major> getAllMajor();
    /**
     * 根据majorid获取subject
     *
     * @param mid
     * @return
     */
    public List<Subjects> getSubjects(@Param("mid") String mid);

    /**
     * 获取选修班级（固定后台写死）
     * @return
     */
    public List<Classes> getClasses();

    public List<String> getUserId(@Param("classId") String classId);
    /**
     * 获取班级学生
     * @param userId
     * @return
     */
    public Users getClassUsers(@Param("userId") String userId);


    OnlineUtil getClassUsersOnLine(@Param("userId") String userId);
}
