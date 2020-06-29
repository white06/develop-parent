package com.tdu.develop.user.service;

import com.tdu.develop.user.pojo.*;
import com.tdu.develop.util.OnlineUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 志阳
 * @create 2019-08-13-11:28
 */
public interface SubjectService {

    public List<Subjects> getSubjectIdList(String id);
    public List<Subjects> getSubjectIdList_develop(String id);

    public List<Subjects> getSubjectIdList_resource(String id);

    public Subjects getSubject(String id);

    public List<Subjects> seleSub(Users users) ;


    /**
     * 查询所有的科目背景图片和logo数据
     * @return
     */
    public List<SubjectBackgrounds> querySubjectInfos() ;
    /**
     * 保存上传的图片，并将图片名保存到数据库中
     * @param background	背景图片文件
     * @param logo	logo图片文件
     * @param id	sujectbackgrounds主键（注意此主键并非java生成，由数据库自动生成，类型为int）
     * @throws IOException
     */
    public void editSubjectBackground(MultipartFile background, MultipartFile logo, int id) throws IOException ;
    /**
     * 将原图片名换成新的图片名
     * 新图片名为当前时间的毫秒值
     * @param imgname
     * @return
     */
    public  String imgRename(String imgname) ;
    /**
     * 查询后台顶部图片路径,
     * @return
     */
    public String gettopimg() ;
    /**
     * 查询背景图片
     * @return
     */
    public String loadgroundImg() ;
  
    public void changeSelectedSubject(String subjectId, String userId) ;

    /**
     * 移动端，根据用户Id获取科目
     */
  
    public String getSubId(String userId) ;

    /**
     * 获取知识点nmae
     * @param knowledgecontentId
     * @return
     */
    public String getKnowNmae(String knowledgecontentId);

    public String getSubIdOne(String userId);
    public String getSubIdOne_develop(String userId);

    public Map<String,Object> getMajors();

    public List<Classes> getClasses();

    public List<Users> getClassUsers(String classId);

    /**
     * 加载首页菜单栏
     * @param userId
     * @return
     */
    public List<MenuTrees> loadMenus(String userId) ;

    List<Subjects> getSubjectIdList2(String id);

    List<OnlineUtil> getClassUsersOnLine(String classId);

    List<ClassUsers> getClassUsersList(String userKey);
}
