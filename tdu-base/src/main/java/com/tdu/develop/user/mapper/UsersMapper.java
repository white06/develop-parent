package com.tdu.develop.user.mapper;

import com.tdu.develop.user.pojo.*;
import com.tdu.develop.util.MonthInYearUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-07-15:06
 */
@Repository
public interface UsersMapper {


    /**
     * 11.28 南靖專用
     * 获取所有科目
     *
     * @return
     */
    public List<Subjects> getAllSub();


    public List<Users> seleStuBySearch(@Param("content") String content);


    public String getUserName(@Param("Id") String id);

    /**
     * 获取所有科目Id
     *
     * @return
     */
    public List<String> getSubjectId();

    public int addMajorUsers(@Param("Id") String id, @Param("UserKey") String userKey, @Param("MajorKey") String majorKey);


    public Users userssele(Users users) throws Exception;

    public Users GetNowUser(@Param("userid") String userid);

    public void UpdateUserInfo(Users user);


    public Users usersseleAgain(Users users);

    public List<String> seledid(String id);

    public int seleNum(String id);

    public void insUser(Users users);

    public void insrole(@Param("id") String id, @Param("roles") String roles);

    public void inssub(@Param("uid") String uid, @Param("id") String id, @Param("subjectkey") String subjectkey, @Param("data") String data);

    public String seleClass(String className);

    public String seleClassId(String userId);

    public List<String> seleClassIdList(String userId);


    public List<String> seleStu(String classId);

    public String seleStuName(String id);

    public List<Classes> seleClassList();

    public Classes getClass(String classKey);

    public void addClass(Classes classes);

    public List<String> seleStuId();

    public void upClassId(@Param("classid") String classid, @Param("userid") String userid);

    public void addClassUsers(@Param("id") String id, @Param("classid") String classid, @Param("userid") String userid);

    public void delClassUsers(String userid);

    public Integer sel1(String userName);

    public String selRole(String keyName);

    public void insUserList(CellObject cellObject);

    public void insClassList(CellObject cellList);

    public void insRoleList(CellObject cellList);

    public List<String> GetAuthListByUserName(String userName);

    public List<Auth> GetAuthParent();

    public List<Auth> GetAuthById(String authid);

    public String GetUidByuserName(String userName);

    public List<Users> seleAllUsers();

    public void upUser(Users users);

    public void upUsers(Users users);

    public void deleUser(String id);


    /******************************************************************************************************/

    public List<Roles> seleRoles();

    public Roles getRoles(String id);

    public void addRole(Roles roles);

    public void editRole(Roles roles);

    public void uprole(@Param("id") String id, @Param("role") String role);

    public void deleRole(String id);

    public void updateRole(@Param("roleName") String roleName, @Param("id") String id);

    /*****************************************************************************************************/

    public List<TrialUsers> seleTrialUsers();

    public Integer seleUserId(String id);

    public void upTrial(TrialUsers trialUsers);

    public void inTrial(TrialUsers trialUsers);

    public void delTrial(String id);

    public String getuNameById(String createPerson);

    /**
     * 根据用户id获取他的权限值id，userroles表
     *
     * @param userId 用户id
     * @return 权限值id
     */
    public String getroleIdByUserId(String userId);

    /**
     * 根据权限值id获取他的权限,roles表
     *
     * @param roleId 权限值id
     * @return roleName 权限
     */
    public String getroleNameByRoleId(String roleId);

    /**
     * 插入用户信息
     *
     * @param member
     */
    public void creUser(Member member);

    /**
     * 插入用户与班级的关联关系
     *
     * @param cu 表classusers
     */
    public void creClassUser(ClassUsers cu);

    /**
     * 根据班级id查询班级内所有的学生
     *
     * @param classId
     * @param pageObject
     * @return
     */
    public List<Users> selUsersForClassId(@Param("classId") String classId, @Param("pageObject") PageObject pageObject);

    /**
     * 查询班级内的学生数
     * users 、classes表
     *
     * @param classId
     * @return
     */
    public int selusersOfAll(String classId);

    /**
     * 根据id查询班级名称
     *
     * @param teamInfos
     * @return
     */
    public String selClassNameById(TeamInfos teamInfos);

    /**
     * 查询小组成员名
     *
     * @param teamInfos
     * @return
     */
    public List<String> queryteamUsersName(TeamInfos teamInfos);

    /**
     * 注册用户
     *
     * @param userId
     * @param rePassWord
     * @param name
     * @param passWord
     * @param sex
     * @param userName
     */
    public void insUserPersonl(@Param("userId") String userId, @Param("rePassWord") String rePassWord, @Param("name") String name,
                               @Param("passWord") String passWord, @Param("sex") String sex, @Param("userName") String userName, @Param("mobilePhoneNum") String telphone, @Param("idol") String endDay);

    /**
     * 获取用户是否存在
     *
     * @param userName
     * @return
     */
    public Users getTemp(@Param("userName") String userName);

    /**
     * 获取用户
     *
     * @return
     */
    public List<Users> selUsers();

    /**
     * 获取登录电脑信息
     *
     * @param userName
     * @return
     */
    public Userstool loginTool(@Param("userName") String userName);

    /**
     * 添加用户工具信息
     *
     * @param userstool
     */
    public void setLoginTool(@Param("Userstool") Userstool userstool);

    /**
     * 修改登录电脑信息
     *
     * @param userstool
     */
    public void upLoginTool(@Param("Userstool") Userstool userstool);

    public String getClassName(@Param("userId") String userId);

    /**
     * 查询用户的角色
     *
     * @param userId
     * @return
     */
    public String queryUserRoles(String userId);

    /**
     * 根据用户id修改用户名
     *
     * @param userId
     * @param userName
     */
    public void updateUsername(@Param("userId") String userId, @Param("userName") String userName);

    /**
     * 根据用户id修改用户性别
     *
     * @param userId
     */
    public void updateUsersex(@Param("userId") String userId, @Param("userSex") String userSex);

    /**
     * 根据用户id修改用户生日
     *
     * @param userId
     */
    public void updateUserBirthdate(@Param("userId") String userId, @Param("userBirthdate") String userBirthdate);

    /**
     * 根据用户id修改用户QQ
     *
     * @param userId
     */
    public void updateUserQQ(@Param("userId") String userId, @Param("userQQ") String userQQ);

    /**
     * 根据用户id修改用户微信
     *
     * @param userId
     */
    public void updateUserChat(@Param("userId") String userId, @Param("userChat") String userChat);

    /**
     * 根据用户id修改用户手机
     *
     * @param userId
     */
    public void updateUserPhone(@Param("userId") String userId, @Param("userPhone") String userPhone);

    /**
     * 根据用户id修改用户邮箱
     *
     * @param userId
     */
    public void updateUserMail(@Param("userId") String userId, @Param("userEmail") String userEmail);

    /**
     * 获取权限根据角色
     *
     * @param authId
     * @return
     */
    public List<Auth> getauthId(@Param("authId") String authId);

    /**
     * 获取所有权限
     *
     * @return
     */
    public List<Auth> getAllAuth();

    /**
     * 修改密码
     *
     * @param userid
     * @param password
     */
    public void updatePassword(@Param("userid") String userid, @Param("password") String password);

    /**
     * 分页查询账号
     *
     * @param page
     * @param limit
     * @return
     */
    public List<Users> seleAllUsersByPage(@Param("page") int page, @Param("limit") int limit);

    public List<ClassUser> seleAllUsersByPageclass(@Param("page") int page, @Param("limit") int limit, @Param("majorId") String majorId, @Param("classid") String classid);

    public int seleAllUsersByPageclassCount(@Param("majorId") String majorId, @Param("classid") String classid);

    public List<Users> seleAllUsersByPageCollege(@Param("page") int page, @Param("limit") int limit, @Param("departmentKey") String departmentKey);

    public List<SubjectUser> seleAllUsersByPageSubjcet(@Param("page") int page, @Param("limit") int limit, @Param("majorId") String majorId, @Param("subjectId") String subjectId);

    public int seleAllUsersByPageSubjcetCount(@Param("majorId") String majorId, @Param("subjectId") String subjectId);

    public List<MajorUser> seleAllUsersByPagemajor(@Param("page") int page, @Param("limit") int limit, @Param("majorId") String majorId, @Param("collegeId") String collegeId);

    public List<DepartmentUser> seleAllUsersByPagedepertment(@Param("page") int page, @Param("limit") int limit, @Param("depertmentId") String depertmentId);

    public int seleAllUsersByPagemajorCount(@Param("collegeId") String collegeId);

    public int seleAllUsersByPagedepertmentCount();

    List<Classes> getClassList(@Param("ids") List<String> ids);

    List<String> getMajorId(@Param("depertId") String depertId);

    List<DepartmentUser> seleUsersByPageDepertmentLikeName(@Param("page") int page, @Param("limit") int limit, @Param("depertmentId") String depertmentId, @Param("userName") String userName);

    int seleUsersByPageDepertmentLikeNameCount(@Param("userName") String userName);

    List<MajorUser> seleAllUsersByPagemajorLikeName(@Param("page") int page, @Param("limit") int limit, @Param("majorId") String majorId, @Param("collegeId") String collegeId, @Param("userName") String userName);

    int seleAllUsersByPagemajorLikeNameCount(@Param("collegeId") String collegeId, @Param("userName") String userName);

    List<ClassUser> seleAllUsersByPageclassLikeName(@Param("page") int page, @Param("limit") int limit, @Param("majorId") String majorId, @Param("classid") String classid, @Param("userName") String userName);

    int seleAllUsersByPageclassLileNameCount(@Param("majorId") String majorId, @Param("classid") String classid, @Param("userName") String userName);

    List<SubjectUser> seleAllUsersByPageSubjcetLikeName(@Param("page") int page, @Param("limit") int limit, @Param("majorId") String majorId, @Param("subjectId") String subjectId, @Param("userName") String userName);

    int seleAllUsersByPageSubjcetLikeNameCount(@Param("majorId") String majorId, @Param("subjectId") String subjectId, @Param("userName") String userName);

    Users getUserByUserNameWithPermission(@Param("userName") String userName);

    List<Users> getAllUsers(@Param("page") int page, @Param("limit") int limit);

    int getAllUsersCount();

    List<Users> getAllUsersLikeName(@Param("page") int page, @Param("limit") int limit, @Param("userName") String userName);

    int getAllUsersLikeNameCount(@Param("userName") String userName);

    void updateEndDay(@Param("endDay") String endDay, @Param("userId") String userId);

    public void addUserInfo(Users user);

    public Integer getUserOnLineDay();

    public Integer getUserOnLineMonth();

    public Integer getUserOnLinePreMonth();

    public List<Object> getMonthInYear();

    Users getUserByTel(@Param("mobilePhoneNum") String telphone);

    List<Users> getUsersByTel(@Param("mobilePhoneNum") String telphone);

    List<Object> getHalfYearUser();

    String getPersonPhoto(@Param("userKey") String userKey);
    /* public List<Object> getMonthInYearCount();*/
}
