package com.tdu.develop.user.controller;

import com.tdu.develop.user.pojo.ClassUsers;
import com.tdu.develop.user.pojo.Classes;
import com.tdu.develop.user.pojo.Users;
import com.tdu.develop.user.service.SubjectService;
import com.tdu.develop.user.service.impl.SubjectServiceImpl;
import com.tdu.develop.util.OnlineUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@CrossOrigin
@Controller
@RequestMapping(value="ClassController")
public class ClassController {

    @Autowired
    SubjectService subjectService=new SubjectServiceImpl();


   //得到选修班级
    @RequestMapping(value="getClasseUserByUserKey.action")
    @ResponseBody
    public List<Users> getClasseUserByUserKey(HttpServletRequest request, HttpSession session, HttpServletResponse response){

        String userKey = request.getParameter("userKey");
        List<ClassUsers> classUsersList =  subjectService.getClassUsersList(userKey);
        List<Users>   list=null;
        if(classUsersList.size()>0){
           list = subjectService.getClassUsers(classUsersList.get(0).getClassId());
        }
        return list;
    }


    //得到选修班级
    @RequestMapping(value="getClasses.action")
    @ResponseBody
    public List<Classes> getClasses(HttpServletRequest request, HttpSession session, HttpServletResponse response){
        return subjectService.getClasses();
    }

    /**
     * 获取属于班级的学生
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(value="getClassUsers.action",method={RequestMethod.POST})
    @ResponseBody
    public List<Users> getClassUsers(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        String classId=request.getParameter("classId");
        System.out.println("classId   :"+classId);
        return subjectService.getClassUsers(classId);
    }

    /**
     * 获取属于班级的学生
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(value="getClassUsersOnLine.action",method={RequestMethod.POST})
    @ResponseBody
    public List<OnlineUtil> getClassUsersOnLine(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String classId=request.getParameter("classId");
        System.out.println("classId   :"+classId);
        return subjectService.getClassUsersOnLine(classId);
    }

}
