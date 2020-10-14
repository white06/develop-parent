package com.tdu.develop.user.service;

import com.tdu.develop.user.pojo.*;
import com.tdu.develop.util.MonthInYearUtil;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-07-15:25
 */
public interface UsersService {

    public List<Users> seleStuBySearch(String content);


    //用于主页获取用户Name字段并展示
    public String getUserName(String userId);

    public Users getUserByUserNameWithPermission(String userName);

    public Users longin(Users users);

    public Users GetNowUser(String userid);

    public void UpdateUserInfo(Users Users);

    public void addUserInfo(Users Users);

    /**
     * 读取用户的权限
     *
     * @return
     */
    public List<Integer> seleNum(String id);

    /**
     * 每当添加一个用户，则为他添加5个科目的阅览权限
     *
     * @param users
     * @param role
     */
    public void insUser(Users users, String role);

    public void upUser(Users users, String role);

    public String seleClass(String className);

    public String seleClassId(String userId);

    public List<String> seleClassIdList(String userId);


    public List<Users> seleStu(String classId);

    public List<Classes> seleClassList();

    public boolean addClass(Classes classes);

    //查询所有的学生
    public List<Users> seleAllStu();

    public boolean upClassId(String classid, String userid);

    public boolean addClassUsers(String id, String classid, String userid);

    public boolean delClassUsers(String userid);

    public boolean sel1(String userName);

    public String seleExcelClassId(String ExcelClassName);

    public String selRole();

    public List<String> insUserList(List<CellObject> cellList);


    public String GetUserRoleListByUserName(String userId, String subjectid);

    public List<Users> seleAllUsers();

    public boolean upUser(Users users);

    public boolean deleUser(String id);


    /********************************************************************************************************/

    public String getroleIdByUserId(String userId);

    public List<Roles> seleRoles();

    public Roles getRoles(String id);

    public boolean addRole(Roles roles);

    public boolean editRole(Roles roles);

    public boolean deleRole(String id);

    public void updateRole(String roleName, String id);

    /********************************************************************************************************/

    public List<TrialUsers> seleTrialUsers();

    public boolean seleUserId(String id);

    public boolean upTrial(TrialUsers trialUsers);

    public boolean inTrial(TrialUsers trialUsers);

    public boolean delTrial(String id);

    /**
     * 移动端，根据用户id获取班级id
     */
    public String getUserClass(String userId);

    public boolean insUserPersonl(String userId, String rePassWord, String name, String passWord, String sex,
                                  String userName, String telphone);

    public boolean ziyuaninsUserPersonl(String userId, String rePassWord, String name, String passWord, String sex,
                                        String userName, String telphone);

    public Users getTemp(String userName);

    public List<Users> selUsers();

    /**
     * 获取 用户登录电脑信息
     *
     * @param userName
     * @return
     */
    public Userstool loginTool(String userName);

    /**
     * 添加用户工具登录信息（电脑）
     *
     * @param userstool
     */
    public void setLoginTool(Userstool userstool);

    /**
     * 添加用户工具登录信息（电脑）
     *
     * @param userstool
     */
    public void upLoginTool(Userstool userstool);

    public String getClassName(String userId);


    public Users getUser(String userId);

    public boolean setUsername(String userId, String userName);

    public boolean setUserSex(String userId, String userSex);

    public boolean setUserBirthdate(String userId, String birthdate);

    public boolean setUserQQ(String userId, String userQQ);

    public boolean setUserWeixin(String userId, String userWeixin);

    public boolean setUserPhone(String userId, String userPhone);

    public boolean setUserMail(String userId, String userMail);

    public List<Auth> getAuths(String userId);

    public List<Auth> getAuthsByRoleId(String roleId);

    public List<Auth> getAllAuth();

    public void updatePassword(String password, String userid);

    public List<Users> seleAllUsersByPage(int page, int limit);

    public List<ClassUser> seleAllUsersByPageclass(int page, int limit, String majorId, String classId);

    public int seleAllUsersByPageclassCount(String majorId, String classId);

    public List<Users> seleAllUsersByPageCollege(int page, int limit, String CollegeId);

    public List<SubjectUser> seleAllUsersByPageSubjcet(int page, int limit, String majorId, String subjectId);

    public int seleAllUsersByPageSubjcetCount(String majorId, String subjectId);

    public List<MajorUser> seleAllUsersByPagemajor(int page, int limit, String majorId, String collegeId);

    public List<DepartmentUser> seleAllUsersByPagedepertment(int m, int n, String depertmentId);

    public List<DepartmentUser> seleUsersByPageDepertmentLikeName(int m, int n, String depertmentId, String userName);


    public int seleAllUsersByPagemajorCount(String collegeId);

    public int seleAllUsersByPagedepertmentCount();

    public void addRoleAuth(String authId, String roldId);

    public int queryCount(String authId, String roldId);

    public void delRoleAuth(String authId, String roldId);

    public String GetUidByuserName(String userName);

    int seleUsersByPageDepertmentLikeNameCount(String userName);

    List<MajorUser> seleAllUsersByPagemajorLikeName(int m, int n, String majorId, String collegeId, String userName);

    int seleAllUsersByPagemajorLikeNameCount(String collegeId, String userName);

    List<ClassUser> seleAllUsersByPageclassLikeName(int m, int n, String majorId, String classId, String userName);

    int seleAllUsersByPageclassLileNameCount(String majorId, String classId, String userName);

    List<SubjectUser> seleAllUsersByPageSubjcetLikeName(int m, int n, String majorId, String subjectId, String userName);

    int seleAllUsersByPageSubjcetLikeNameCount(String majorId, String subjectId, String userName);

    List<Users> getAllUsers(int m, int n);

    int getAllUsersCount();

    List<Users> getAllUsersLikeName(int m, int n, String userName);

    int getAllUsersLikeNameCount(String userName);

    void updateEndDay(String endDay, String userId);

    /**
     * 用户登录信息记录
     */
    public void insetUserLogin(UserOnline userOnline);

    /**
     * 记录登出时间，以及统计登录时长
     *
     * @param date
     */
    public void createLogin(Date date, String loginId) throws Exception;

    public Integer getUserOnLineDay();

    public Integer getUserOnLineMonth();

    public Integer getUserOnLinePreMonth();

    List<Object> getMonthInYear();

    Users getUserByTel(String telphone);

    List<Users> getUsersByTel(String telphone);

    List<Object> getHalfYear();

    List<Object> getHalfYearUser();

    String getPersonPhoto(String userKey);

    /* List<Object> getMonthInYearCount();*/
}
