package com.tdu.develop.user.service.impl;

import com.tdu.develop.resource.mapper.KnowOnlineMapper;
import com.tdu.develop.user.mapper.*;
import com.tdu.develop.user.pojo.*;
import com.tdu.develop.user.service.UsersService;
import com.tdu.develop.util.MonthInYearUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 志阳
 * @create 2019-08-07-15:28
 */
@Service
public class UserServiceImpl implements UsersService {

    @Autowired
    UsersMapper usersMapper;
    @Autowired
    ClassMapper classMapper;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    AuthMapper authMapper;
    @Autowired
    UseronlineMapper useronlineMapper;
    @Autowired
    KnowOnlineMapper knowOnlineMapper;


    public String GetUidByuserName(String userName) {
        return usersMapper.GetUidByuserName(userName);
    }

    @Override
    public int seleUsersByPageDepertmentLikeNameCount(String userName) {
        return usersMapper.seleUsersByPageDepertmentLikeNameCount(userName);
    }

    @Override
    public List<MajorUser> seleAllUsersByPagemajorLikeName(int m, int n, String majorId, String collegeId, String userName) {
        return usersMapper.seleAllUsersByPagemajorLikeName(m, n, majorId, collegeId, userName);
    }

    @Override
    public int seleAllUsersByPagemajorLikeNameCount(String collegeId, String userName) {
        return usersMapper.seleAllUsersByPagemajorLikeNameCount(collegeId, userName);
    }

    @Override
    public List<ClassUser> seleAllUsersByPageclassLikeName(int m, int n, String majorId, String classId, String userName) {
        return usersMapper.seleAllUsersByPageclassLikeName(m, n, majorId, classId, userName);
    }

    @Override
    public int seleAllUsersByPageclassLileNameCount(String majorId, String classId, String userName) {
        return usersMapper.seleAllUsersByPageclassLileNameCount(majorId, classId, userName);
    }

    @Override
    public List<SubjectUser> seleAllUsersByPageSubjcetLikeName(int m, int n, String majorId, String subjectId, String userName) {
        return usersMapper.seleAllUsersByPageSubjcetLikeName(m, n, majorId, subjectId, userName);
    }

    @Override
    public int seleAllUsersByPageSubjcetLikeNameCount(String majorId, String subjectId, String userName) {
        return usersMapper.seleAllUsersByPageSubjcetLikeNameCount(majorId, subjectId, userName);
    }

    @Override
    public List<Users> getAllUsers(int m, int n) {
        return usersMapper.getAllUsers(m, n);
    }

    @Override
    public int getAllUsersCount() {
        return usersMapper.getAllUsersCount();
    }

    @Override
    public List<Users> getAllUsersLikeName(int m, int n, String userName) {
        return usersMapper.getAllUsersLikeName(m, n, userName);
    }

    @Override
    public int getAllUsersLikeNameCount(String userName) {
        return usersMapper.getAllUsersLikeNameCount(userName);
    }

    @Override
    public void updateEndDay(String endDay, String userId) {
        usersMapper.updateEndDay(endDay, userId);
    }

    public List<Users> seleStuBySearch(String content) {
        List<Users> list = usersMapper.seleStuBySearch(content);
        return list;
    }

    public String getUserName(String userId) {
        return usersMapper.getUserName(userId);
    }

    @Override
    public Users getUserByUserNameWithPermission(String userName) {
        return usersMapper.getUserByUserNameWithPermission(userName);
    }

    @Override
    public Users longin(Users users) {
        Users flag = new Users();
        try {
            flag = usersMapper.userssele(users);
        } catch (Exception e) {
            flag = usersMapper.usersseleAgain(users);
        }
        return flag;
    }

    public Users GetNowUser(String userid) {
        Users users = new Users();
        users = usersMapper.GetNowUser(userid);
        return users;
    }

    public void UpdateUserInfo(Users users) {
        usersMapper.UpdateUserInfo(users);

    }

    @Override
    public void addUserInfo(Users users) {
        usersMapper.addUserInfo(users);
    }

    /**
     * 读取用户的权限
     *
     * @return
     */
    public List<Integer> seleNum(String id) {
        List<String> ssele = new ArrayList<String>();
        List<Integer> ilist = new ArrayList<Integer>();
        ssele = usersMapper.seledid(id);
        for (String ssi : ssele) {

            ilist.add(usersMapper.seleNum(ssi));
        }

        return ilist;
    }

    /**
     * 每当添加一个用户，则为他添加5个科目的阅览权限
     *
     * @param users
     * @param role
     */
    public void insUser(Users users, String role) {
        String id = users.getId();
        System.out.println(users.getMobilePhoneNum().length());
        if (users.getMobilePhoneNum().length() == 0) {
            users.setMobilePhoneNum("13859980221");
        }
        usersMapper.insUser(users);
        String uuId = UUID.randomUUID().toString();
        usersMapper.addMajorUsers(uuId, id, "d44c8d04-39c0-45b2-87b8-c68943f14d36");
        usersMapper.insrole(id, role);
        List<String> subList = usersMapper.getSubjectId();
        List<String> list = new ArrayList<>();
        //学生
        if (role.equals("0c494961-fc3c-41b3-992a-4f9b0d0f57eb")) {
            list.add("ccabdcd3-9754-4426-9078-53ee4bd9d09c");
            //老师
        } else if (role.equals("0f7b183d-4606-4874-8f36-566d8792403d")) {
            for (String string : subList) {
                list.add(string);
            }
            //admin
        } else if (role.equals("9c8c0815-3968-45d0-9fae-0d42885973fc")) {
            for (String string : subList) {
                list.add(string);
            }
        }

        for (String subjectkey : list) {
            String uid = UUID.randomUUID().toString();
            usersMapper.inssub(uid, id, subjectkey, users.getCreateDate());
        }
    }

    /**
     * 每当添加一个用户，则为他添加5个科目的阅览权限
     *
     * @param users
     * @param role
     */
    public void upUser(Users users, String role) {
        String id = users.getId();
        //修改用户信息
        usersMapper.upUsers(users);
        String uuId = UUID.randomUUID().toString();
        //修改roleId
        usersMapper.uprole(id, role);
    }

    public String seleClass(String className) {

        String id = usersMapper.seleClass(className);

        return id;
    }

    public List<String> seleClassIdList(String userId) {

        List<String> classId = usersMapper.seleClassIdList(userId);

        return classId;
    }

    public String seleClassId(String userId) {

        String classId = usersMapper.seleClassId(userId);

        return classId;
    }

    public List<Users> seleStu(String classId) {
        List<String> list = usersMapper.seleStu(classId);
        List<Users> usersList = new ArrayList<>();
        for (String string : list) {
            Users users = new Users();
            users.setId(string);
            users.setName(usersMapper.seleStuName(string));
            usersList.add(users);
        }
        return usersList;
    }

    public List<Classes> seleClassList() {
        List<Classes> list = usersMapper.seleClassList();
        return list;
    }

    public boolean addClass(Classes classes) {
        usersMapper.addClass(classes);
        return true;
    }

    //查询所有的学生
    public List<Users> seleAllStu() {
        List<String> stuList = usersMapper.seleStuId();
        List<Users> usersList = new ArrayList<>();
        for (String string : stuList) {
            Users users = new Users();
            users.setId(string);
            users.setName(usersMapper.seleStuName(string));
            usersList.add(users);
        }
        return usersList;
    }

    public boolean upClassId(String classid, String userid) {
        usersMapper.upClassId(classid, userid);
        return true;
    }

    public boolean addClassUsers(String id, String classid, String userid) {
        usersMapper.addClassUsers(id, classid, userid);
        return true;
    }

    public boolean delClassUsers(String userid) {
        usersMapper.delClassUsers(userid);
        return true;
    }

    public boolean sel1(String userName) {
        int num = usersMapper.sel1(userName).intValue();
        if (num == 1) {
            return false;
        } else {
            return true;
        }
    }

    public String seleExcelClassId(String ExcelClassName) {
        String id = usersMapper.seleClass(ExcelClassName);
        if (id == null) {
            Classes classes = new Classes();
            id = UUID.randomUUID().toString();
            classes.setId(id);
            classes.setClassName(ExcelClassName);
            usersMapper.addClass(classes);
        }
        return id;
    }

    public String selRole() {
        String id = usersMapper.selRole("student");
        return id;
    }

    public List<String> insUserList(List<CellObject> cellList) {
        List<String> errList = new ArrayList<>();
        for (CellObject cellObject : cellList) {
            if (usersMapper.sel1(cellObject.getUserName()) == 1) {
                errList.add(cellObject.getUserName());
                continue;
            } else {
                usersMapper.insUserList(cellObject);

                String uuId = UUID.randomUUID().toString();
                usersMapper.addMajorUsers(uuId, cellObject.getId(), "d44c8d04-39c0-45b2-87b8-c68943f14d36");

                usersMapper.insClassList(cellObject);
                usersMapper.insRoleList(cellObject);
                List<String> list = new ArrayList<>();
                list.add("ccabdcd3-9754-4426-9078-53ee4bd9d09c");
                for (String subjectkey : list) {
                    String uid = UUID.randomUUID().toString();
                    usersMapper.inssub(uid, cellObject.getId(), subjectkey, cellObject.getCreateDate());
                }
            }
        }
        return errList;
    }

    public String GetUserRoleListByUserName(String userId, String subjectid) {


        List<String> AuthList = new ArrayList<>();
        List<Auth> AuthParent = new ArrayList<Auth>();
        AuthList = usersMapper.GetAuthListByUserName(userId);

        AuthParent = usersMapper.GetAuthParent();

        String returnJsonhead = "({'menus':[ ";
        String returnJson = "";

        for (Auth parentauth : AuthParent) {

            returnJson = returnJson + "{'menuid':'" + parentauth.getNum() + "','icon':'icon-sys','menuname':'" + parentauth.getPageName() + "','menus':[";
            int myauth = 0;
            int mychildren = 0;

            String[] ChildrenList = parentauth.getChildren().split(";");

            for (String Children : ChildrenList) {

                if (AuthList.contains(Children)) {

                    List<Auth> AuthChildren = new ArrayList<Auth>();
                    AuthChildren = usersMapper.GetAuthById(Children);
                    for (Auth auth : AuthChildren) {
                        switch (auth.getParametersType()) {
                            case 0:
                                returnJson = returnJson + "{'menuname':'" + auth.getPageName() + "','icon':'icon-nav','url':PoolName+'" + auth.getLink() + "'},";
                                mychildren = 1;
                                break;
                            case 1:
                                returnJson = returnJson + "{'menuname':'" + auth.getPageName() + "','icon':'icon-nav','url':PoolName+'" + auth.getLink() + "?userId=" + userId + "'},";
                                mychildren = 2;
                                break;
                            case 2:
                                returnJson = returnJson + "{'menuname':'" + auth.getPageName() + "','icon':'icon-nav','url':PoolName+'" + auth.getLink() + "?subjectid=" + subjectid + "'},";
                                mychildren = 3;
                                break;


                        }

                    }

                }

            }

            if (myauth == 1) {
                myauth = 0;
            } else if (mychildren != 0) {
                returnJson = returnJson.substring(0, returnJson.length() - 1) + "]},";
            } else {
                returnJson = returnJson + "]},";
            }

        }


        returnJson = returnJsonhead + returnJson.substring(0, returnJson.length() - 1) + "]})";
        return returnJson;
    }

    public List<Users> seleAllUsers() {
        List<Users> list = usersMapper.seleAllUsers();
        return list;
    }

    public boolean upUser(Users users) {
        usersMapper.upUser(users);
        return true;
    }

    public boolean deleUser(String id) {
        usersMapper.deleUser(id);
        return true;
    }


    /********************************************************************************************************/

    public List<Roles> seleRoles() {
        return usersMapper.seleRoles();
    }

    public Roles getRoles(String id) {
        return usersMapper.getRoles(id);
    }

    public boolean addRole(Roles roles) {
        usersMapper.addRole(roles);
        return true;
    }

    public boolean editRole(Roles roles) {
        usersMapper.editRole(roles);
        return true;
    }

    public boolean deleRole(String id) {
        usersMapper.deleRole(id);
        return true;
    }

    public void updateRole(String roleName, String id) {
        usersMapper.updateRole(roleName, id);
    }

    /********************************************************************************************************/

    public List<TrialUsers> seleTrialUsers() {
        List<TrialUsers> list = new ArrayList<TrialUsers>();
        list = usersMapper.seleTrialUsers();
        return list;
    }

    public boolean seleUserId(String id) {
        if (usersMapper.seleUserId(id) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean upTrial(TrialUsers trialUsers) {
        usersMapper.upTrial(trialUsers);
        return true;
    }

    public boolean inTrial(TrialUsers trialUsers) {
        usersMapper.inTrial(trialUsers);
        return true;
    }

    public boolean delTrial(String id) {
        usersMapper.delTrial(id);
        return true;
    }

    /**
     * 移动端，根据用户id获取班级id
     */
    @Override
    public String getUserClass(String userId) {
        String classId = classMapper.selUserClassId(userId);

        return classId;
    }

    @Override
    public boolean insUserPersonl(String userId, String rePassWord, String name, String passWord, String sex,
                                  String userName, String telphone) {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String getDate = simpleDateFormat.format(date);

        //获取系统时间，并且转码成数据库可读
        Date d = new Date();
        Date endDay = new Date();
        //创建Calendar实例
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDay);   //设置当前时间
        cal.add(Calendar.MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        //同理增加一个月和一天的方法：
        String dateEndStr = sdf.format(cal.getTime());

        String studentId = selRole();
        CellObject cellObject = new CellObject();

        cellObject.setName(name);
        cellObject.setUserName(userName);
        cellObject.setPassWord(passWord);
        cellObject.setSex(sex);
        cellObject.setId(userId);
        cellObject.setRoleId(studentId);
        //cellObject.setClassId(classId);
        cellObject.setCreateDate(getDate);

        usersMapper.insUserPersonl(userId, rePassWord, name, passWord, sex, userName, telphone, dateEndStr);
        String uuId = UUID.randomUUID().toString();
        usersMapper.addMajorUsers(uuId, userId, "d44c8d04-39c0-45b2-87b8-c68943f14d36");
        usersMapper.insRoleList(cellObject);
        List<String> list = new ArrayList<>();
		/*list.add("2c941e2c-e2ae-48a6-b0b3-a42e95e9dbc4");
		list.add("332405da-16ba-4a84-9689-d7904f8bf7bd");
		list.add("3c75eda0-e494-43f4-9d66-933033d32542");
		list.add("b4400061-5891-48c5-8186-ca89c4eb1c7d");
		list.add("bf99191c-a6eb-41c8-8db8-ffdcddfa71b2");
		list.add("ff75ffcf-8aca-4e0f-874c-70d20f21ade0");*/
        list.add("ccabdcd3-9754-4426-9078-53ee4bd9d09c");
        for (String subjectkey : list) {
            String uid = UUID.randomUUID().toString();
            usersMapper.inssub(uid, cellObject.getId(), subjectkey, cellObject.getCreateDate());
        }

        return true;
    }

    @Override
    public boolean ziyuaninsUserPersonl(String userId, String rePassWord, String name, String passWord, String sex,
                                        String userName, String telphone) {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String getDate = simpleDateFormat.format(date);

        //获取系统时间，并且转码成数据库可读
        Date d = new Date();
        Date endDay = new Date();
        //创建Calendar实例
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDay);   //设置当前时间
        cal.add(Calendar.MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = sdf.format(d);
        //同理增加一个月和一天的方法：
        String dateEndStr = sdf.format(cal.getTime());

        String studentId = selRole();
        CellObject cellObject = new CellObject();

        cellObject.setName(name);
        cellObject.setUserName(userName);
        cellObject.setPassWord(passWord);
        cellObject.setSex(sex);
        cellObject.setId(userId);
        cellObject.setRoleId(studentId);
        //cellObject.setClassId(classId);
        cellObject.setCreateDate(getDate);
        cellObject.setMobilePhoneNum(telphone);

        usersMapper.insUserPersonl(userId, rePassWord, name, passWord, sex, userName, telphone, dateEndStr);
        String uuId = UUID.randomUUID().toString();
        usersMapper.addMajorUsers(uuId, userId, "bc16e9fd-fbf7-45d5-9f7d-3d3f2ada842b");
        usersMapper.insRoleList(cellObject);
        List<String> list = new ArrayList<>();
		/*list.add("2c941e2c-e2ae-48a6-b0b3-a42e95e9dbc4");
		list.add("332405da-16ba-4a84-9689-d7904f8bf7bd");
		list.add("3c75eda0-e494-43f4-9d66-933033d32542");
		list.add("b4400061-5891-48c5-8186-ca89c4eb1c7d");
		list.add("bf99191c-a6eb-41c8-8db8-ffdcddfa71b2");
		list.add("ff75ffcf-8aca-4e0f-874c-70d20f21ade0");*/
        list.add("7dec7125-008b-44a4-9001-1dc90dee5468");
        list.add("11b0d4ea-4464-4926-868e-feca0b1b4c45");
        list.add("defe2778-9ede-4ba8-8a89-bc8df1742783");
        list.add("f3e0abe5-6cee-4474-933f-89d1ec00af0d");
        list.add("dec75c41-fb73-4eb5-bd69-4ea979e501e5");
        for (String subjectkey : list) {
            String uid = UUID.randomUUID().toString();
            usersMapper.inssub(uid, cellObject.getId(), subjectkey, cellObject.getCreateDate());
        }

        return true;
    }


    @Override
    public Users getTemp(String userName) {
        return usersMapper.getTemp(userName);
    }

    public List<Users> selUsers() {
        return usersMapper.selUsers();
    }

    /**
     * 获取 用户登录电脑信息
     *
     * @param userName
     * @return
     */
    public Userstool loginTool(String userName) {
        return usersMapper.loginTool(userName);
    }

    /**
     * 添加用户工具登录信息（电脑）
     *
     * @param userstool
     */
    public void setLoginTool(Userstool userstool) {
        usersMapper.setLoginTool(userstool);
    }

    /**
     * 添加用户工具登录信息（电脑）
     *
     * @param userstool
     */
    public void upLoginTool(Userstool userstool) {
        usersMapper.upLoginTool(userstool);
    }

    public String getClassName(String userId) {
        return usersMapper.getClassName(userId);
    }

    @Override
    public Users getUser(String userId) {
        return usersMapper.GetNowUser(userId);
    }

    @Override
    public boolean setUsername(String userId, String userName) {
        if (userId != null) {
            usersMapper.updateUsername(userId, userName);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean setUserSex(String userId, String userSex) {
        if (userId != null) {
            usersMapper.updateUsersex(userId, userSex);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean setUserBirthdate(String userId, String birthdate) {
        if (userId != null) {
            usersMapper.updateUserBirthdate(userId, birthdate);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean setUserQQ(String userId, String userQQ) {
        if (userId != null) {
            usersMapper.updateUserQQ(userId, userQQ);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean setUserWeixin(String userId, String userWeixin) {
        if (userId != null) {
            usersMapper.updateUserChat(userId, userWeixin);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean setUserPhone(String userId, String userPhone) {
        if (userId != null) {
            usersMapper.updateUserPhone(userId, userPhone);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean setUserMail(String userId, String userMail) {
        if (userId != null) {
            usersMapper.updateUserMail(userId, userMail);
            return true;
        } else {
            return false;
        }
    }

    public List<Auth> getAuths(String userId) {
        String roleId = usersMapper.getroleIdByUserId(userId);
        List<Auth> authList = usersMapper.getauthId(roleId);

        return authList;
    }

    public String getroleIdByUserId(String userId) {

        return usersMapper.getroleIdByUserId(userId);
    }

    public List<Auth> getAuthsByRoleId(String roleId) {
        List<Auth> authList = usersMapper.getauthId(roleId);

        return authList;
    }

    public List<Auth> getAllAuth() {
        List<Auth> authList = usersMapper.getAllAuth();

        return authList;
    }

    public void addRoleAuth(String authId, String roldId) {
        authMapper.addRoleAuth(authId, roldId);
    }

    public int queryCount(String authId, String roldId) {
        return authMapper.queryCount(authId, roldId);
    }

    public void delRoleAuth(String authId, String roldId) {
        authMapper.delRoleAuth(authId, roldId);
    }

    /**
     * 修改密码
     *
     * @param password
     * @param userid
     */
    public void updatePassword(String password, String userid) {
        usersMapper.updatePassword(userid, password);
    }

    public List<Users> seleAllUsersByPage(int page, int limit) {
        // TODO Auto-generated method stub
        return usersMapper.seleAllUsersByPage(page, limit);
    }

    public List<ClassUser> seleAllUsersByPageclass(int page, int limit, String majorId, String classId) {
        // TODO Auto-generated method stub
        //   String collerId=departmentMapper.getCollerId(subjectId);
        return usersMapper.seleAllUsersByPageclass(page, limit, majorId, classId);
    }

    public int seleAllUsersByPagemajorCount(String collegeId) {
        return usersMapper.seleAllUsersByPagemajorCount(collegeId);
    }

    public int seleAllUsersByPagedepertmentCount() {
        return usersMapper.seleAllUsersByPagedepertmentCount();
    }

    public List<MajorUser> seleAllUsersByPagemajor(int page, int limit, String majorId, String collegeId) {
        // TODO Auto-generated method stub
        //   String collerId=departmentMapper.getCollerId(subjectId);
        return usersMapper.seleAllUsersByPagemajor(page, limit, majorId, collegeId);
    }

    public List<DepartmentUser> seleAllUsersByPagedepertment(int page, int limit, String depertmentId) {
        // TODO Auto-generated method stub
        //   String collerId=departmentMapper.getCollerId(subjectId);
        return usersMapper.seleAllUsersByPagedepertment(page, limit, depertmentId);
    }

    @Override
    public List<DepartmentUser> seleUsersByPageDepertmentLikeName(int m, int n, String depertmentId, String userName) {
        return usersMapper.seleUsersByPageDepertmentLikeName(m, n, depertmentId, userName);
    }


    public List<SubjectUser> seleAllUsersByPageSubjcet(int page, int limit, String majorId, String subjectId) {
        // TODO Auto-generated method stub
        //   String collerId=departmentMapper.getCollerId(subjectId);
        return usersMapper.seleAllUsersByPageSubjcet(page, limit, majorId, subjectId);
    }

    public int seleAllUsersByPageSubjcetCount(String majorId, String subjectId) {
        // TODO Auto-generated method stub
        //   String collerId=departmentMapper.getCollerId(subjectId);
        return usersMapper.seleAllUsersByPageSubjcetCount(majorId, subjectId);
    }

    public int seleAllUsersByPageclassCount(String majorId, String classId) {
        // TODO Auto-generated method stub
        //   String collerId=departmentMapper.getCollerId(subjectId);
        return usersMapper.seleAllUsersByPageclassCount(majorId, classId);
    }

    public List<Users> seleAllUsersByPageCollege(int page, int limit, String CollegeId) {
        // TODO Auto-generated method stub
        //   String collerId=departmentMapper.getCollerId(subjectId);
        return usersMapper.seleAllUsersByPageCollege(page, limit, CollegeId);
    }

    public void insetUserLogin(UserOnline userOnline) {
        useronlineMapper.insetUserLogin(userOnline);
    }

    public void createLogin(Date date, String loginId) throws Exception {
        String loginTime = useronlineMapper.getLoginTime(loginId);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = df.format(date);
        Date sd2 = df.parse(loginTime);
        long fen = (date.getTime() - sd2.getTime()) / (1000 * 60);
        int onlinetime = (int) fen;
        useronlineMapper.setOnlineTime(onlinetime, createTime, loginId);

    }

    @Override
    public Integer getUserOnLineDay() {
        return usersMapper.getUserOnLineDay();
    }

    @Override
    public Integer getUserOnLineMonth() {
        return usersMapper.getUserOnLineMonth();
    }

    @Override
    public Integer getUserOnLinePreMonth() {
        return usersMapper.getUserOnLinePreMonth();
    }

    @Override
    public List<Object> getMonthInYear() {
        return usersMapper.getMonthInYear();
    }

    @Override
    public List<Object> getHalfYear() {
        return knowOnlineMapper.getHalfYear();
    }

    @Override
    public List<Object> getHalfYearUser() {
        return usersMapper.getHalfYearUser();
    }

    @Override
    public String getPersonPhoto(String userKey) {
        return usersMapper.getPersonPhoto(userKey);
    }

    @Override
    public Users getUserByTel(String telphone) {
        return usersMapper.getUserByTel(telphone);
    }

    @Override
    public List<Users> getUsersByTel(String telphone) {
        return usersMapper.getUsersByTel(telphone);
    }
    /*@Override
    public List<Object> getMonthInYearCount() {
        return usersMapper.getMonthInYearCount();
    }*/
}
