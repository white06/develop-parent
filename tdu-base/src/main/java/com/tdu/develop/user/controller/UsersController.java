package com.tdu.develop.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tdu.develop.redis.RedisUtil;
import com.tdu.develop.user.pojo.*;
import com.tdu.develop.user.service.DepartmentService;
import com.tdu.develop.user.service.MajorService;
import com.tdu.develop.user.service.UsersService;
import com.tdu.develop.user.service.impl.DepartmentServiceImpl;
import com.tdu.develop.user.service.impl.MajorServiceImpl;
import com.tdu.develop.user.service.impl.UserServiceImpl;
import com.tdu.develop.util.Config;
import com.tdu.develop.util.HttpUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 志阳
 * @create 2019-08-07-14:47
 */
@CrossOrigin
@Controller
@RequestMapping(value = "UsersController")
public class UsersController {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    UsersService usersService = new UserServiceImpl();

    @Autowired
    MajorService majorService = new MajorServiceImpl();

    @Autowired
    DepartmentService departmentService = new DepartmentServiceImpl();


    Users users = new Users();


    //查询所有的用户账号
    @RequestMapping(value = "selUsersByClass.action")
    @ResponseBody
    public List<Users> selUsersByClass() {
        List<Users> list = usersService.seleAllUsers();
        return list;
    }


    //查询所有的学生
    @RequestMapping(value = "getTelphone.action", method = {RequestMethod.POST})
    @ResponseBody
    public String getTelphone(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnsupportedEncodingException {

        String telphone = request.getParameter("telphone");
        List<Users> getuser = usersService.getUsersByTel(telphone);
        HashMap<String, String> resultMap = new HashMap<String, String>();

        //  001 表示该手机号未被注册； 002表示该手机号已经被注册； 003表示该手机号已经被多人注册
        if (getuser.size() == 0) {
            resultMap.put("code", "001");
        } else if (getuser.size() == 1) {
            resultMap.put("code", "002");
        } else if (getuser.size() > 1) {
            resultMap.put("code", "003");
        }
        return JSONArray.toJSONString(resultMap);
    }

    //登录，核实用户以及姓名，返回判断
    @RequestMapping(value = "loginByTel.action", method = {RequestMethod.POST})
    public void loginByTel(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        String telphone = request.getParameter("telphone");

        Users getuser = usersService.getUserByTel(telphone);

        if (getuser == null) {
            try {
                response.getWriter().print("{\"no\":\"no\"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            String userName = getuser.getUserName();
            String passWord = getuser.getPassWord();
            users.setUserName(userName);
            users.setPassWord(passWord);
            // 想要得到 SecurityUtils.getSubject() 的对象．．访问地址必须跟 shiro 的拦截地址内．不然后会报空指针
            Subject sub = SecurityUtils.getSubject();
            // 用户输入的账号和密码,,存到UsernamePasswordToken对象中..然后由shiro内部认证对比,
            // 认证执行者交由 com.battcn.config.AuthRealm 中 doGetAuthenticationInfo 处理
            // 当以上认证成功后会向下执行,认证失败会抛出异常
            UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
            try {
                sub.login(token);
            } catch (UnknownAccountException e) {
                logger.error("对用户[{}]进行登录验证,验证未通过,用户不存在", userName);
                token.clear();
            } catch (LockedAccountException lae) {
                logger.error("对用户[{}]进行登录验证,验证未通过,账户已锁定", userName);
                token.clear();
            } catch (ExcessiveAttemptsException e) {
                logger.error("对用户[{}]进行登录验证,验证未通过,错误次数过多", userName);
                token.clear();
            } catch (AuthenticationException e) {
                logger.error("对用户[{}]进行登录验证,验证未通过,堆栈轨迹如下", userName, e);
                token.clear();
            }

            Users users2 = usersService.longin(users);

            Users user = (Users) session.getAttribute("USER_SESSION");

            try {
                if (null == users2) {
                    response.getWriter().print("{\"false\":\"false\"}");
                } else {
                    //获取系统时间，并且转码成数据库可读
                    Date d = new Date();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateNowStr = df.format(d);
                    Date sd1 = df.parse(dateNowStr);
                    Date sd2 = df.parse(users2.getIdol());
                    System.out.println(sd1.before(sd2));
                    String loginId = UUID.randomUUID().toString();
                    UserOnline userOnline = new UserOnline();
                    if (sd1.before(sd2)) {
                        session.setAttribute("Name", users2.getName());
                        session.setAttribute("ID", users2.getId());
                        userOnline.setId(loginId);
                        userOnline.setUserId(users2.getId());
                        userOnline.setLoginTime(sd1);
                        userOnline.setOnlineTime(30);
                        session.setAttribute("loginid", loginId);
                        usersService.insetUserLogin(userOnline);
                        response.getWriter().print("{\"success\":\"ture\",\"cookie\":\"" + request.getSession().getId() + "\"}");
                    } else {
                        response.getWriter().print("{\"error\":\"error\"}");
                        //   session.setMaxInactiveInterval(0);
                    }
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    }


    //查询所有的学生
    @RequestMapping(value = "validation.action", method = {RequestMethod.POST})
    @ResponseBody
    public String validation(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws UnsupportedEncodingException {

        String tel = request.getParameter("phone");
        StringBuilder sb = new StringBuilder();
        sb.append("accountSid").append("=").append(Config.ACCOUNT_SID);
        sb.append("&to").append("=").append(tel);
        sb.append("&templateid").append("=").append("249459");
        //验证码
        String rod = smsCode();
        sb.append("&param").append("=").append(URLEncoder.encode(rod, "UTF-8"));
        //sb.append("&smsContent").append("=").append( URLEncoder.encode("【秒嘀科技】您的验证码为123456，该验证码5分钟内有效。请勿泄漏于他人。","UTF-8"));
        String body = sb.toString() + HttpUtil.createCommonParam(Config.ACCOUNT_SID, Config.AUTH_TOKEN);
        String result = HttpUtil.post(Config.BASE_URL, body);
        System.out.println(result);
        // 字符串转换为JSON
        JSONObject jsonObject = JSON.parseObject(result);    // result数据源：JSON格式字符串
        // 获取值
        String s = jsonObject.getString("respCode");
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (s.equals("0000")) {
            resultMap.put("code", "0000");
        }
        byte[] bytes = rod.getBytes();
        String encoded = Base64.getEncoder().encodeToString(bytes);
        resultMap.put("data", encoded);
        return JSONArray.toJSONString(resultMap);
    }

    // 创建验证码
    public static String smsCode() {
        String random = (int) ((Math.random() * 9 + 1) * 100000) + "";
        return random;
    }

    //更新当前用户信息
    @RequestMapping(value = "getHalfYearUser.action")
    @ResponseBody
    public List<?> getHalfYearUser(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
//        HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
//        List<MonthInYearUtil> monthInYearUtil =  usersService.getMonthInYear();
        List<?> halfYearUserUtil = RedisUtil.getList("getHalfYearUser");
        return halfYearUserUtil;
    }

    //更新当前用户信息
    @RequestMapping(value = "getHalfYear.action")
    @ResponseBody
    public List<?> getHalfYear(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
//        HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
//        List<MonthInYearUtil> monthInYearUtil =  usersService.getMonthInYear();
        List<?> halfYearUtil = RedisUtil.getList("getHalfYear");
        return halfYearUtil;
    }

    //更新当前用户信息
    @RequestMapping(value = "getMonthInYear.action")
    @ResponseBody
    public List<?> getMonthInYear(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
//        HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
//        List<MonthInYearUtil> monthInYearUtil =  usersService.getMonthInYear();
        List<?> monthInYearUtil = RedisUtil.getList("getMonthInYear");
        return monthInYearUtil;
    }

    //更新当前用户信息
    @RequestMapping(value = "getUserOnLine.action")
    @ResponseBody
    public HashMap<String, Object> getUserOnLine(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        Integer dayCount = usersService.getUserOnLineDay();
        Integer monthCount = usersService.getUserOnLineMonth();
        Integer preMonthCount = usersService.getUserOnLinePreMonth();
        resultMap.put("dayCount", String.valueOf(RedisUtil.get("dayCount")));
        resultMap.put("monthCount", String.valueOf(RedisUtil.get("monthCount")));
        resultMap.put("preMonthCount", String.valueOf(RedisUtil.get("preMonthCount")));
        return resultMap;
    }

    //查询当前用户信息
    @RequestMapping(value = "CompareUserInfo.action")
    @ResponseBody
    public int CompareUserInfo(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String id = (String) session.getAttribute("ID");
        Users us = new Users();
        us = usersService.GetNowUser(id);
        int count = 0;
        if (us.getBirthdate() == "" || us.getBirthdate() == null) {
            count = count + 1;
        }
        if (us.getBooldType() == "" || us.getBooldType() == null) {
            count = count + 1;
        }
        if (us.getConstellation() == "" || us.getConstellation() == null) {
            count = count + 1;
        }
        if (us.getEmail() == "" || us.getEmail() == null) {
            count = count + 1;
        }
        if (us.getFamilyAddress() == "" || us.getFamilyAddress() == null) {
            count = count + 1;
        }
        if (us.getFamilyPhoneNum() == "" || us.getFamilyPhoneNum() == null) {
            count = count + 1;
        }
        if (us.getIdNum() == "" || us.getIdNum() == null) {
            count = count + 1;
        }
        if (us.getIdol() == "" || us.getIdol() == null) {
            count = count + 1;
        }
        if (us.getMobilePhoneNum() == "" || us.getMobilePhoneNum() == null) {
            count = count + 1;
        }
        if (us.getMotto() == "" || us.getMotto() == null) {
            count = count + 1;
        }
        if (us.getMsn() == "" || us.getMsn() == null) {
            count = count + 1;
        }
        if (us.getNative() == "" || us.getNative() == null) {
            count = count + 1;
        }
        if (us.getOffice() == "" || us.getOffice() == null) {
            count = count + 1;
        }
        if (us.getPresonalPhoto() == "" || us.getPresonalPhoto() == null) {
            count = count + 1;
        }
        if (us.getQqNum() == "" || us.getQqNum() == null) {
            count = count + 1;
        }
        if (us.getSchoolName() == "" || us.getSchoolName() == null) {
            count = count + 1;
        }
        if (us.getSex() == "" || us.getSex() == null) {
            count = count + 1;
        }
        if (us.getWx() == "" || us.getWx() == null) {
            count = count + 1;
        }
        System.out.println(count);
        System.out.println(count / 24);
        return count;
    }

    //更新当前用户信息
    @RequestMapping(value = "addUserInfo.action")
    @ResponseBody
    public boolean addUserInfo(HttpServletRequest request, HttpSession session, HttpServletResponse response) {

        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Users user = new Users();
        user.setId((String) session.getAttribute("ID"));
        user.setSex(request.getParameter("Sex"));
        user.setFamilyAddress(request.getParameter("address"));
        user.setEmail(request.getParameter("email"));
        user.setMobilePhoneNum(request.getParameter("phone"));
        user.setIdNum(request.getParameter("idNumber"));
        user.setNative(request.getParameter("nativePlace"));
        user.setQqNum(request.getParameter("qq"));
        user.setWx(request.getParameter("wx"));
        user.setSchoolName(request.getParameter("school"));
        usersService.addUserInfo(user);
        return true;
    }


    @RequestMapping(value = "updateEndDay.action", method = {RequestMethod.POST})
    @ResponseBody
    public void updateEndDay(HttpServletRequest request, @RequestParam("userList") List<String> userList,
                             @RequestParam("endDay") String endDay) {
        String regEx = "[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：\"\"’。， 、？]";
//可以在中括号内加上任何想要替换的字符，实际上是一个正则表达式
        String aa = "";//这里是将特殊字符换为aa字符串," "代表直接去掉
        System.out.println("  endDay  ---: " + endDay);
        SimpleDateFormat sdf = new SimpleDateFormat(("yyyy-MM-dd"));
        Date date = null;
        try {
            date = sdf.parse(endDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = df.format(date);


        for (String userId : userList) {
            userId = userId.replaceAll(regEx, aa);
            usersService.updateEndDay(dateNowStr, userId);
        }
    }


    //查询所有的学生
    @RequestMapping(value = "seleStuBySearch.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Users> seleStuBySearch(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String content = request.getParameter("content");
        List<Users> usersList = usersService.seleStuBySearch(content);
        return usersList;
    }


    //用于主页获取用户Name字段并展示
    @RequestMapping(value = "getUserId.action", method = {RequestMethod.POST})
    @ResponseBody
    public void getUserId(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        String userId = session.getAttribute("ID").toString();
        response.getWriter().print("" + userId + "");
    }


    //用于主页获取用户Name字段并展示
    @RequestMapping(value = "getUserName.action", method = {RequestMethod.POST})
    @ResponseBody
    public void getUserName(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        String userId = session.getAttribute("ID").toString();
        String Name = usersService.getUserName(userId);
        response.getWriter().print("{\"success\":\"ture\",\"Name\":\"" + Name + "\"}");
    }

    @RequestMapping(value = "getroleIdByUserId.action", method = {RequestMethod.POST})
    @ResponseBody
    public void getroleIdByUserId(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        String userId = session.getAttribute("ID").toString();
        String roleId = usersService.getroleIdByUserId(userId);
        response.getWriter().print("" + roleId + "");
    }

    //登录，核实用户以及姓名，返回判断
    @RequestMapping(value = "login.action", method = {RequestMethod.POST})
    public void login(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");

        users.setUserName(userName);
        users.setPassWord(passWord);
        // 想要得到 SecurityUtils.getSubject() 的对象．．访问地址必须跟 shiro 的拦截地址内．不然后会报空指针
        Subject sub = SecurityUtils.getSubject();
        // 用户输入的账号和密码,,存到UsernamePasswordToken对象中..然后由shiro内部认证对比,
        // 认证执行者交由 com.battcnyuyin.config.AuthRealm 中 doGetAuthenticationInfo 处理
        // 当以上认证成功后会向下执行,认证失败会抛出异常
        UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
        try {
            sub.login(token);
        } catch (UnknownAccountException e) {
            logger.error("对用户[{}]进行登录验证,验证未通过,用户不存在", userName);
            token.clear();
        } catch (LockedAccountException lae) {
            logger.error("对用户[{}]进行登录验证,验证未通过,账户已锁定", userName);
            token.clear();
        } catch (ExcessiveAttemptsException e) {
            logger.error("对用户[{}]进行登录验证,验证未通过,错误次数过多", userName);
            token.clear();
        } catch (AuthenticationException e) {
            logger.error("对用户[{}]进行登录验证,验证未通过,堆栈轨迹如下", userName, e);
            token.clear();
        }

        Users users2 = usersService.longin(users);

        Users user = (Users) session.getAttribute("USER_SESSION");

        try {
            if (null == users2) {
                response.getWriter().print("{\"false\":\"false\"}");
            } else {
                //获取系统时间，并且转码成数据库可读
                Date d = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateNowStr = df.format(d);
                Date sd1 = df.parse(dateNowStr);
                Date sd2 = df.parse(users2.getIdol());
                System.out.println(sd1.before(sd2));
                String loginId = UUID.randomUUID().toString();
                UserOnline userOnline = new UserOnline();
                if (sd1.before(sd2)) {
                    session.setAttribute("Name", users2.getName());
                    session.setAttribute("ID", users2.getId());
                    userOnline.setId(loginId);
                    userOnline.setUserId(users2.getId());
                    userOnline.setLoginTime(sd1);
                    userOnline.setOnlineTime(30);
                    session.setAttribute("loginid", loginId);
                    usersService.insetUserLogin(userOnline);
                    response.getWriter().print("{\"success\":\"ture\",\"cookie\":\"" + request.getSession().getId() + "\"}");
                } else {
                    response.getWriter().print("{\"error\":\"error\"}");
                    //   session.setMaxInactiveInterval(0);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }


    //登录，核实用户以及姓名，返回判断
    @RequestMapping(value = "loginForWebGL.action", method = {RequestMethod.POST})
    @ResponseBody
    public String loginForWebGL(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model model) {
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        users.setUserName(userName);
        users.setPassWord(passWord);
        UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
        Users users2 = usersService.longin(users);
        if (users2 != null) {
            String userKey = users2.getId();
            System.out.println("users2.getId(  : " + users2.getId());
            return userKey;
        }
        return null;
    }

    //登录，核实用户以及姓名，返回判断（用于编辑工具的）
 /*   @RequestMapping(value="loginTool.action",method={RequestMethod.POST})
    @ResponseBody
    public String loginTool(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
        String panduan="";
        String data=request.getParameter("IdentityKey");
        byte[] bt = (new BASE64Decoder()).decodeBuffer(data);
        String data1=new String(bt);
        String[] loginDat=data1.split("&");
        List<String> setInfos=new ArrayList<String>();
        for(int i=loginDat.length-6;i<loginDat.length;i++){
            setInfos.add(loginDat[i]);
        }
        String mac=setInfos.get(0).split("=")[1];
        String cpu=setInfos.get(1).split("=")[1];
        String sysId=setInfos.get(2).split("=")[1];
        String ip=setInfos.get(3).split("=")[1];
        String name=setInfos.get(4).split("=")[1];
        String password=setInfos.get(5).split("=")[1];
        users.setUserName(name);
        users.setPassWord(password);
        Users users2=usersService.longin(users);
        if(null==users2){
            //3表示用户不存在
            panduan="6";
        }else{
            Userstool userstool=usersService.loginTool(users2.getUserName());
            Userstool userstool2=new Userstool();
            if(userstool==null){
                userstool2.setName(name);
                userstool2.setMac(mac);
                userstool2.setCpu(cpu);
                userstool2.setSysId(sysId);
                userstool2.setIp(ip);
                usersService.setLoginTool(userstool2);
                panduan="1";
            }else if(userstool.getMac().equals(mac)&&userstool.getCpu().equals(cpu)&&userstool.getSysId().equals(sysId)
                    &&userstool.getIp().equals(ip)){
                panduan="1";
            }else{
                panduan="7";
                usersService.upLoginTool(userstool2);
            }
//					session.setAttribute("Name",users2.getName());
//					session.setAttribute("ID", users2.getId());
//					response.getWriter().print("{\"success\":\"ture\",\"cookie\":\""+request.getSession().getId()+"\"}");
        }
//			}catch (IOException e) {
//				e.printStackTrace();
//			}
        return panduan;
    }
*/

    //登录，核实用户以及姓名，返回判断（用于编辑工具的）
    @RequestMapping(value = "loginExport.action", method = {RequestMethod.POST})
    @ResponseBody
    public String loginExport(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        users.setUserName(request.getParameter("userName"));
        String userId = "";
        users.setPassWord(request.getParameter("passWord"));
        Users users2 = usersService.longin(users);
        if (null == users2) {
            userId = "false";
        } else {
            session.setAttribute("Name", users2.getName());
            session.setAttribute("ID", users2.getId());
            userId = users2.getId();
        }
        return userId;
    }

    //查询用户权限
    @RequestMapping(value = "seleNum.action", method = {RequestMethod.POST})
    public void seleNum(HttpSession session, HttpServletResponse response) throws IOException {

        String id = (String) session.getAttribute("ID");
        List<Integer> iList = new ArrayList<Integer>();
        iList = usersService.seleNum(id);
        int adminNum = 3;
        //有的人可能是管理员也是老师，所有有两个角色权限可能，所以用list放权限内容，然后循环判断
        for (Integer integer : iList) {
            if (integer < adminNum) {
                adminNum = integer;
            }
        }
        response.getWriter().print(adminNum);
        response.getWriter().print(iList);
        return;
    }

    //查询用户权限
    @RequestMapping(value = "seleNum2.action", method = {RequestMethod.POST})
    public void seleNum2(HttpSession session, HttpServletResponse response) throws IOException {

        String id = (String) session.getAttribute("ID");
        List<Integer> iList = new ArrayList<Integer>();
        iList = usersService.seleNum(id);
        int adminNum = 3;
        //有的人可能是管理员也是老师，所有有两个角色权限可能，所以用list放权限内容，然后循环判断
        for (Integer integer : iList) {
            if (integer < adminNum) {
                adminNum = integer;
            }
        }
        //response.getWriter().print(adminNum);
        response.getWriter().print(iList);
        return;
    }

    //新增账户
    @RequestMapping(value = "insUser.action", method = {RequestMethod.POST})
    public void insUser(HttpSession session, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Users users = new Users();
        String username = request.getParameter("username");
        if (usersService.sel1(username)) {
            String id = UUID.randomUUID().toString();
            String name = request.getParameter("name");
            String sex = request.getParameter("sex");
            String password = request.getParameter("password");
            int role = Integer.valueOf(request.getParameter("role"));
            String roles = null;
            users.setId(id);
            users.setUserName(username);
            users.setName(name);
            users.setSex(sex);
            users.setPassWord(password);
            users.setMobilePhoneNum("13859980221");

            //判断权限，并赋予后台权限ID
            if (role == 1) {
                roles = "9c8c0815-3968-45d0-9fae-0d42885973fc";
            } else if (role == 2) {
                roles = "0f7b183d-4606-4874-8f36-566d8792403d";
            } else if (role == 3) {
                roles = "0c494961-fc3c-41b3-992a-4f9b0d0f57eb";
            }
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

            System.out.println("  dateEndStr   :" + dateEndStr);
            users.setIdol(dateEndStr);

            //注册时间
            users.setCreateDate(dateNowStr);
            users.setBirthdate(dateNowStr);
            usersService.insUser(users, roles);

            response.getWriter().print("ture");
            return;
        } else {
            response.getWriter().print("err");
            return;
        }
    }

    //注销
    @ResponseBody
    @RequestMapping(value = "zhuxiao.action", method = {RequestMethod.POST})
    public boolean zhuxiao(HttpSession session, HttpServletResponse response) throws Exception {
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = df.format(d);
        Date sd1 = df.parse(dateNowStr);
        String loginId = (String) session.getAttribute("loginid");
        usersService.createLogin(sd1, loginId);
        session.invalidate();
        return true;
    }

    //查询所有的班级
    @RequestMapping(value = "seleClassList.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Classes> seleClassList() {
        List<Classes> classes = usersService.seleClassList();
        return classes;
    }

    //新增班级
    @RequestMapping(value = "addClass.action", method = {RequestMethod.POST})
    public void addClass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        Classes classes = new Classes();
        classes.setId(UUID.randomUUID().toString());
        String className = java.net.URLDecoder.decode(request.getParameter("className"), "utf-8");
        //System.out.println("className:"+className);
        classes.setClassName(className);
        if (usersService.addClass(classes)) {
            response.getWriter().print("ture");
        } else {
            response.getWriter().print("err");
        }
    }

    //查询所有的学生
    @RequestMapping(value = "seleAllStu.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Users> seleAllStu() {
        List<Users> usersList = usersService.seleAllStu();
        return usersList;
    }

    //每次都对学生的班级进行判断，如果有这个字段就修改，不然就添加
    @RequestMapping(value = "insOrUp.action", method = {RequestMethod.POST})
    public void insOrUp(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (null != usersService.seleClassId(request.getParameter("stuId"))) {
            usersService.upClassId(request.getParameter("classId"), request.getParameter("stuId"));
        } else {
            usersService.addClassUsers(UUID.randomUUID().toString(), request.getParameter("classId"), request.getParameter("stuId"));
        }
        response.getWriter().print("true");
    }

    //删除不需要的ClassUsers
    @RequestMapping(value = "delClassUsers.action", method = {RequestMethod.POST})
    public void delClassUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        usersService.delClassUsers(request.getParameter("stuId"));
        response.getWriter().print("true");
    }

    //查询当前用户信息
    @RequestMapping(value = "GetUserInfo.action")
    @ResponseBody
    public Users GetUserInfo(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String id = (String) session.getAttribute("ID");
        Users us = new Users();
        us = usersService.GetNowUser(id);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format1.parse(us.getBirthdate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        us.setBirthdate(format1.format(date));
        return us;
    }

    //更新当前用户信息
    @RequestMapping(value = "UpdateUserInfo.action")
    @ResponseBody
    public boolean UpdateUserInfo(HttpServletRequest request, HttpSession session, HttpServletResponse response) {

        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String name = request.getParameter("Name");
        String sex = request.getParameter("Sex");
        Users user = new Users();
        user.setId((String) session.getAttribute("ID"));
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(request.getParameter("Brithdate"));
		} catch (ParseException e2) {
			e2.printStackTrace();
		}*/
        ////System.out.println(request.getParameter("ConfirmPassword")+"--------"+request.getParameter("Password"));
        if (request.getParameter("Brithdate") != "") {
            user.setBirthdate(request.getParameter("Brithdate"));
        }
        user.setPassWord(request.getParameter("Password"));
        user.setConfirmPassword(request.getParameter("ConfirmPassword"));
        user.setName(request.getParameter("Name"));
        user.setEmail(request.getParameter("Email"));
        user.setMsn(request.getParameter("MSN"));
        user.setMobilePhoneNum(request.getParameter("MobilePhoneNum"));
        user.setOffice(request.getParameter("OfficePhoneNum"));
        user.setIdNum(request.getParameter("IDNum"));
        user.setIdol(request.getParameter("Idol"));
        user.setMotto(request.getParameter("Motto"));
        user.setNative(request.getParameter("NativePlace"));
        user.setQqNum(request.getParameter("QQNum"));
        user.setBooldType(request.getParameter("BooldType"));
        user.setSex(request.getParameter("Sex"));
        //System.out.println(user.getBirthdate());
        usersService.UpdateUserInfo(user);
        return true;
    }


    //更新当前用户信息
    @RequestMapping(value = "UpdateUserFile.action")
    @ResponseBody
    public boolean UpdateUserFile(@RequestParam("file") MultipartFile file, @RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("sex") String sex, @RequestParam("email") String email,
                                  @RequestParam("msn") String msn, @RequestParam("phone") String phone, @RequestParam("qq") String qq, @RequestParam("birthdate") String birthdate, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws IOException {

        String fileName = file.getOriginalFilename();
        //截取最后一个.结尾的后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Users user = new Users();
        String userId = (String) session.getAttribute("ID");
        user.setId(userId);
        if (!birthdate.equals("undefined")) {
            user.setBirthdate(birthdate);
        }
        user.setPassWord(password);
        user.setConfirmPassword(password);
        user.setName(name);
        user.setEmail(email);
        user.setMsn(msn);
        user.setMobilePhoneNum(phone);
        /*user.setOffice(request.getParameter("OfficePhoneNum"));
        user.setIdNum(request.getParameter("IDNum"));
        user.setMotto(request.getParameter("Motto"));
        user.setNative(request.getParameter("NativePlace"));
        user.setBooldType(request.getParameter("BooldType"));*/
        user.setQqNum(qq);
        user.setSex(sex);

        user.setPresonalPhoto(userId + suffix);

        //File file = new File(filePath+File.separator+knowlegcontent.getType());
        InputStream is = file.getInputStream();

        //在指定目录下添加文件
        File writeFile = new File("");
        //本机路径  wamp  www
        //writeFile = new File("D:/wamp/www/newTdu/57/"+userId+suffix);
        //服务器路径
        writeFile = new File("/www/wwwroot/file.tduvr.club/pp/" + userId + suffix);

        if (!writeFile.exists()) {
            //创建指定文件
            writeFile.createNewFile();
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(writeFile));
            byte[] flash = new byte[1024];
            int len = 0;
            while (-1 != (len = bis.read(flash))) {
                bos.write(flash, 0, len);
            }
            bos.flush();
            bis.close();
            bos.close();
        }

        usersService.UpdateUserInfo(user);
        return true;
    }


    //查询所有的用户账号
    @RequestMapping(value = "seleAllUsers.action")
    @ResponseBody
    public List<Users> seleAllUsers() {
        List<Users> list = usersService.seleAllUsers();
        return list;
    }

    //编辑用户信息
    @RequestMapping(value = "upUser.action")
    public void upUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Users users = new Users();
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String password = request.getParameter("password");
        int role = Integer.valueOf(request.getParameter("role"));
        String roles = null;
        users.setId(id);
        users.setName(name);
        users.setSex(sex);
        users.setPassWord(password);
        users.setConfirmPassword(password);
        //判断权限，并赋予后台权限ID
        if (role == 1) {
            roles = "9c8c0815-3968-45d0-9fae-0d42885973fc";
        } else if (role == 2) {
            roles = "0f7b183d-4606-4874-8f36-566d8792403d";
        } else if (role == 3) {
            roles = "0c494961-fc3c-41b3-992a-4f9b0d0f57eb";
        }

        usersService.upUser(users, roles);

        response.getWriter().print("ture");
        return;
    }

    public void sel1(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (usersService.sel1(request.getParameter("userName"))) {
                response.getWriter().print("ture");
            } else {
                response.getWriter().print("ergetTeamNamer");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "deleUser.action")
    @ResponseBody
    public void deleUser(HttpServletRequest request) {
        usersService.deleUser(request.getParameter("id"));
    }


    /************************************************************************************************************/

    //查询所有的角色
    @RequestMapping(value = "seleRoles.action")
    @ResponseBody
    public List<Roles> seleRoles() {
        //String userId =session.getAttribute("ID").toString();
        return usersService.seleRoles();
    }

    //获取角色
    @RequestMapping(value = "getRoles.action")
    @ResponseBody
    public Roles getRoles(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        return usersService.getRoles(id);
    }

    //编辑角色名
    @RequestMapping(value = "updateRole.action")
    @ResponseBody
    public void updateRole(HttpServletRequest request, HttpServletResponse response) {

        String roleName = request.getParameter("roleName");
        String id = request.getParameter("id");
        usersService.updateRole(roleName, id);
    }

    //新增角色权限
    @RequestMapping(value = "addRole.action")
    public void addRole(HttpServletRequest request, HttpServletResponse response) {
        Roles roles = new Roles();
        roles.setId(UUID.randomUUID().toString());
        List<Roles> list = usersService.seleRoles();
        roles.setNum(list.size() + 1);
        roles.setRoleName(request.getParameter("roleName"));
        //Integer.parseInt(request.getParameter("enable"))
        roles.setEnable(1);
        try {
            if (usersService.addRole(roles)) {
                response.getWriter().print("success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //编辑角色权限
    @RequestMapping(value = "editRole.action")
    public void editRole(HttpServletRequest request, HttpServletResponse response) {
        Roles roles = new Roles();
        roles.setId(UUID.randomUUID().toString());
        roles.setNum(Integer.parseInt(request.getParameter("num")));
        roles.setRoleName(request.getParameter("roleName"));
        roles.setEnable(Integer.parseInt(request.getParameter("enable")));
        try {
            if (usersService.editRole(roles)) {
                response.getWriter().print("success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除角色
    @RequestMapping(value = "deleRole.action")
    public void deleRole(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (usersService.deleRole(request.getParameter("id"))) {
                response.getWriter().print("success");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /************************************************************************************************************/

    @RequestMapping(value = "seleTrialUsers.action")
    @ResponseBody
    public List<TrialUsers> seleTrialUsers() {
        List<TrialUsers> list = new ArrayList<TrialUsers>();
        list = usersService.seleTrialUsers();
        return list;
    }

    @RequestMapping(value = "addTrialUser.action")
    public void addTrialUser(HttpServletRequest request, HttpServletResponse response) {
        TrialUsers trialUsers = new TrialUsers();
        trialUsers.setBeginTrialDate(request.getParameter("beginTrialDate"));
        trialUsers.setEndTrialDate(request.getParameter("endTrialDate"));
        trialUsers.setUserId(request.getParameter("userId"));
        if (usersService.seleUserId(request.getParameter("userId"))) {
            usersService.upTrial(trialUsers);
        } else {
            trialUsers.setId(UUID.randomUUID().toString());
            trialUsers.setEnableTrial(true);
            usersService.inTrial(trialUsers);
        }
        try {
            response.getWriter().print("ture");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "deleTrialUser.action")
    public void deleTrialUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (usersService.seleUserId(request.getParameter("id"))) {
                usersService.delTrial(request.getParameter("id"));
                response.getWriter().print("ture");

            } else {
                response.getWriter().print("err");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "zhuce.action", method = {RequestMethod.POST})
    @ResponseBody
    public String zhuce(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String userName = request.getParameter("UserName");
        //String name="xx";
        String name = userName;
        String passWord = request.getParameter("PassWord");

        String telphone = request.getParameter("telphone");

        String ConfirmPassword = passWord;
        String userId = UUID.randomUUID().toString();
        String sex = "男";
        String panduan = "1";
        Users temp = usersService.getTemp(userName);
        if (temp == null) {
            usersService.insUserPersonl(userId, ConfirmPassword, name, passWord, sex, userName, telphone);
            panduan = "1";
        } else {
            panduan = "2";
        }
        return panduan;
    }

    /**
     * 注册
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "ziyuanzhuce.action", method = {RequestMethod.POST})
    @ResponseBody
    public String ziyuanzhuce(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String userName = request.getParameter("UserName");
        //String name="xx";
        String name = userName;
        String passWord = request.getParameter("PassWord");
        String telphone = request.getParameter("telphone");
        String ConfirmPassword = passWord;
        String userId = UUID.randomUUID().toString();
        String sex = "男";
        String panduan = "1";
        Users temp = usersService.getTemp(userName);
        if (temp == null) {
            usersService.ziyuaninsUserPersonl(userId, ConfirmPassword, name, passWord, sex, userName, telphone);
            panduan = "1";
        } else {
            panduan = "2";
        }
        return panduan;
    }

    //查询当前用户信息
    @RequestMapping(value = "getPersonPhoto.action")
    @ResponseBody
    public HashMap<String, String> getPersonPhoto(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String userKey = (String) session.getAttribute("ID");
        String result = usersService.getPersonPhoto(userKey);
        HashMap<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("result", result);
        return resultMap;
    }


    //查询当前用户信息
    @RequestMapping(value = "getUserId.action")
    @ResponseBody
    public HashMap<String, String> getUserId(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String id = (String) session.getAttribute("ID");
        HashMap<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("id", id);
        return resultMap;
    }

    //用于主页获取用户Name字段并展示
    @RequestMapping(value = "getTeamUserName.action", method = {RequestMethod.POST})
    @ResponseBody
    public HashMap<String, String> getTeamUserName(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        String userId = request.getParameter("userId");
        String Name = usersService.getUserName(userId);
        HashMap<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("name", Name);
        return resultMap;
    }

    //用于主页获取用户Name字段并展示
    @RequestMapping(value = "getInformation.action", method = {RequestMethod.POST})
    @ResponseBody
    public HashMap<String, String> getInformation(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        String userId = session.getAttribute("ID").toString();
        String className = usersService.getClassName(userId);
        List<String> majorNamelist = majorService.getMajorName(userId);
        String majorName = majorNamelist.get(0);
        if (majorNamelist.size() > 1) {
            majorName = majorName + "...";
        }
        String collegeName = departmentService.getCollegeName(userId);
        HashMap<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("className", className);
        resultMap.put("majorName", majorName);
        resultMap.put("collegeName", collegeName);
        return resultMap;

    }

    @RequestMapping(value = "GetSysAdministrateTree.action", method = {RequestMethod.POST})
    @ResponseBody
    public void GetSysAdministrateTree(HttpServletRequest request, HttpSession session, HttpServletResponse response) {

        String User_Id = session.getAttribute("ID").toString();

        String subjectid = request.getParameter("subjectid");

        String returnJson = usersService.GetUserRoleListByUserName(User_Id, subjectid);


        try {
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(returnJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "GetSysAdministrateTree_develop.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Auth> GetSysAdministrateTree_develop(HttpServletRequest request, HttpSession session, HttpServletResponse response) {

        String User_Id = session.getAttribute("ID").toString();


        return usersService.getAuths(User_Id);
    }

    @RequestMapping(value = "getAuthByRoleId.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Auth> getAuthByRoleId(HttpServletRequest request, HttpSession session, HttpServletResponse response) {

        String roleId = request.getParameter("roleId");
        return usersService.getAuthsByRoleId(roleId);
    }

    @RequestMapping(value = "getAllAuth.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Auth> getAllAuth(HttpServletRequest request, HttpSession session, HttpServletResponse response) {

        return usersService.getAllAuth();
    }

    @RequestMapping(value = "addRoleAuth.action", method = {RequestMethod.POST})
    @ResponseBody
    public String addRoleAuth(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String roleId = request.getParameter("role");
        String authId = request.getParameter("auth");
        System.out.println(roleId);
        System.out.println(authId);
        int count = usersService.queryCount(authId, roleId);
        if (count < 1) {
            usersService.addRoleAuth(authId, roleId);
        }
        //usersService.addRoleAuth
        return "";
    }

    @RequestMapping(value = "delRoleAuth.action", method = {RequestMethod.POST})
    @ResponseBody
    public String delRoleAuth(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String roleId = request.getParameter("role");
        String authId = request.getParameter("auth");
        System.out.println(roleId);
        System.out.println(authId);
        usersService.delRoleAuth(authId, roleId);

        //usersService.addRoleAuth
        return "";
    }


    //    //用于主页获取用户Name字段并展示
//    @RequestMapping(value="getTeamName.action",method={RequestMethod.POST})
//    @ResponseBody
//    public HashMap<String, String> getTeamName(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception{
//        request.setCharacterEncoding("utf-8");
//        response.setCharacterEncoding("UTF-8");
//        String userId =session.getAttribute("ID").toString();
//        List<Team>  list= usersServiceImp.getTeamName(userId);
//        Team team1 = new Team();
//        for (Team team : list) {
//            team1=team;
//        }
//        HashMap<String, String> resultMap = new HashMap<String, String>();
//        resultMap.put("teamName", team1.getTeamName());
//        return resultMap;
//
//    }
    @RequestMapping(value = "updatePassword.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean updatePassword(HttpServletRequest request, HttpSession session, HttpServletResponse response) {

        String User_Id = session.getAttribute("ID").toString();
        String password = request.getParameter("Password");
        usersService.updatePassword(password, User_Id);
        return true;
    }

    @RequestMapping(value = "seleAllUsersByPage.action")
    @ResponseBody
    public List<Users> seleAllUsersByPage(HttpServletRequest request, HttpServletResponse response) {
        int page = Integer.parseInt(request.getParameter("page"));
        ;
        int limit = Integer.parseInt(request.getParameter("limit"));
        int m = (page - 1) * limit;
        int n = limit;
        List<Users> list = usersService.seleAllUsersByPage(m, n);
        return list;
    }

    @RequestMapping(value = "seleAllUsersByPageclass.action")
    @ResponseBody
    public HashMap<String, Object> seleAllUsersByPageclass(HttpServletRequest request, HttpServletResponse response) {
        String majorId = request.getParameter("majorId");
        String classId = request.getParameter("classId");
        int page = Integer.parseInt(request.getParameter("page"));
        ;
        int limit = Integer.parseInt(request.getParameter("limit"));
        int m = (page - 1) * limit;
        int n = limit;
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        List<ClassUser> list = usersService.seleAllUsersByPageclass(m, n, majorId, classId);
        int count = usersService.seleAllUsersByPageclassCount(majorId, classId);//list.size();
        resultMap.put("list", list);
        resultMap.put("count", count);
        return resultMap;
    }

    @RequestMapping(value = "seleAllUsersByPageclassLikeName.action")
    @ResponseBody
    public HashMap<String, Object> seleAllUsersByPageclassLikeName(HttpServletRequest request, HttpServletResponse response) {
        String majorId = request.getParameter("majorId");
        String classId = request.getParameter("classId");
        String userName = request.getParameter("userName");
        int page = Integer.parseInt(request.getParameter("page"));
        ;
        int limit = Integer.parseInt(request.getParameter("limit"));
        int m = (page - 1) * limit;
        int n = limit;
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        List<ClassUser> list = usersService.seleAllUsersByPageclassLikeName(m, n, majorId, classId, userName);
        int count = usersService.seleAllUsersByPageclassLileNameCount(majorId, classId, userName);//list.size();
        resultMap.put("list", list);
        resultMap.put("count", count);
        return resultMap;
    }

    @RequestMapping(value = "seleAllUsersByPagemajor.action")
    @ResponseBody
    public HashMap<String, Object> seleAllUsersByPagemajor(HttpServletRequest request, HttpServletResponse response) {
        String majorId = request.getParameter("majorId");
        String collegeId = request.getParameter("collegeId");
        int page = Integer.parseInt(request.getParameter("page"));
        ;
        int limit = Integer.parseInt(request.getParameter("limit"));
        int m = (page - 1) * limit;
        int n = limit;
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        List<MajorUser> list = usersService.seleAllUsersByPagemajor(m, n, majorId, collegeId);
        int count = usersService.seleAllUsersByPagemajorCount(collegeId);
        resultMap.put("list", list);
        resultMap.put("count", count);
        return resultMap;
    }

    @RequestMapping(value = "seleAllUsersByPagemajorLikeName.action")
    @ResponseBody
    public HashMap<String, Object> seleAllUsersByPagemajorLikeName(HttpServletRequest request, HttpServletResponse response) {
        String majorId = request.getParameter("majorId");
        String collegeId = request.getParameter("collegeId");
        String userName = request.getParameter("userName");
        int page = Integer.parseInt(request.getParameter("page"));
        ;
        int limit = Integer.parseInt(request.getParameter("limit"));
        int m = (page - 1) * limit;
        int n = limit;
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        List<MajorUser> list = usersService.seleAllUsersByPagemajorLikeName(m, n, majorId, collegeId, userName);
        int count = usersService.seleAllUsersByPagemajorLikeNameCount(collegeId, userName);
        resultMap.put("list", list);
        resultMap.put("count", count);
        return resultMap;
    }

    @RequestMapping(value = "getAllUsersLikeName.action")
    @ResponseBody
    public HashMap<String, Object> getAllUsersLikeName(HttpServletRequest request, HttpServletResponse response) {
        String userName = request.getParameter("userName");
        int page = Integer.parseInt(request.getParameter("page"));
        ;
        int limit = Integer.parseInt(request.getParameter("limit"));
        int m = (page - 1) * limit;
        int n = limit;
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        List<Users> list = usersService.getAllUsersLikeName(m, n, userName);
        int count = usersService.getAllUsersLikeNameCount(userName);
        resultMap.put("list", list);
        resultMap.put("count", count);
        return resultMap;
    }

    @RequestMapping(value = "getAllUsers.action")
    @ResponseBody
    public HashMap<String, Object> getAllUsers(HttpServletRequest request, HttpServletResponse response) {
        int page = Integer.parseInt(request.getParameter("page"));
        ;
        int limit = Integer.parseInt(request.getParameter("limit"));
        int m = (page - 1) * limit;
        int n = limit;
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        List<Users> list = usersService.getAllUsers(m, n);
        int count = usersService.getAllUsersCount();
        resultMap.put("list", list);
        resultMap.put("count", count);
        return resultMap;
    }


    @RequestMapping(value = "seleAllUsersByPagedepertment.action")
    @ResponseBody
    public HashMap<String, Object> seleAllUsersByPagedepertment(HttpServletRequest request, HttpServletResponse response) {
        String depertmentId = request.getParameter("depertmentId");
        int page = Integer.parseInt(request.getParameter("page"));
        ;
        int limit = Integer.parseInt(request.getParameter("limit"));
        int m = (page - 1) * limit;
        int n = limit;
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        List<DepartmentUser> list = usersService.seleAllUsersByPagedepertment(m, n, depertmentId);
        int count = usersService.seleAllUsersByPagedepertmentCount();
        resultMap.put("list", list);
        resultMap.put("count", count);
        return resultMap;
    }


    @RequestMapping(value = "seleUsersByPageDepertmentLikeName.action")
    @ResponseBody
    public HashMap<String, Object> seleUsersByPageDepertmentLikeName(HttpServletRequest request, HttpServletResponse response) {
        String depertmentId = request.getParameter("depertmentId");
        String userName = request.getParameter("userName");
        int page = Integer.parseInt(request.getParameter("page"));
        ;
        int limit = Integer.parseInt(request.getParameter("limit"));
        int m = (page - 1) * limit;
        int n = limit;
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        List<DepartmentUser> list = usersService.seleUsersByPageDepertmentLikeName(m, n, depertmentId, userName);
        int count = usersService.seleUsersByPageDepertmentLikeNameCount(userName);
        resultMap.put("list", list);
        resultMap.put("count", count);
        return resultMap;
    }


    @RequestMapping(value = "seleAllUsersByPageSubjcet.action")
    @ResponseBody
    public HashMap<String, Object> seleAllUsersByPageSubjcet(HttpServletRequest request, HttpServletResponse response) {
        String majorId = request.getParameter("majorId");
        String subjectId = request.getParameter("subjectId");
        int page = Integer.parseInt(request.getParameter("page"));
        ;
        int limit = Integer.parseInt(request.getParameter("limit"));
        int m = (page - 1) * limit;
        int n = limit;
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        List<SubjectUser> list = usersService.seleAllUsersByPageSubjcet(m, n, majorId, subjectId);
        int count = usersService.seleAllUsersByPageSubjcetCount(majorId, subjectId);
        ;//list.size();
        resultMap.put("list", list);
        resultMap.put("count", count);
        return resultMap;
    }

    @RequestMapping(value = "seleAllUsersByPageSubjcetLikeName.action")
    @ResponseBody
    public HashMap<String, Object> seleAllUsersByPageSubjcetLikeName(HttpServletRequest request, HttpServletResponse response) {
        String majorId = request.getParameter("majorId");
        String subjectId = request.getParameter("subjectId");
        String userName = request.getParameter("userName");
        int page = Integer.parseInt(request.getParameter("page"));
        ;
        int limit = Integer.parseInt(request.getParameter("limit"));
        int m = (page - 1) * limit;
        int n = limit;
        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        List<SubjectUser> list = usersService.seleAllUsersByPageSubjcetLikeName(m, n, majorId, subjectId, userName);
        int count = usersService.seleAllUsersByPageSubjcetLikeNameCount(majorId, subjectId, userName);
        ;//list.size();
        resultMap.put("list", list);
        resultMap.put("count", count);
        return resultMap;
    }


    @RequestMapping(value = "seleAllUsersByPageCollege.action")
    @ResponseBody
    public List<Users> seleAllUsersByPageCollege(HttpServletRequest request, HttpServletResponse response) {
        String CollegeId = request.getParameter("CollegeId");
        int page = Integer.parseInt(request.getParameter("page"));
        ;
        int limit = Integer.parseInt(request.getParameter("limit"));
        int m = (page - 1) * limit;
        int n = limit;
        List<Users> list = usersService.seleAllUsersByPageCollege(m, n, CollegeId);
        return list;
    }

    @RequestMapping(value = "getuserforid.action")
    @ResponseBody
    public Users getuserforid(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        return usersService.getUser(id);
    }

    /**
     * 获取session用户信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getuser.action")
    @ResponseBody
    public Users getuser(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String userId=(String) session.getAttribute("ID");
        return usersService.getUser(userId);
    }
}
