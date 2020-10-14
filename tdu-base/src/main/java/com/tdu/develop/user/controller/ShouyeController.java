package com.tdu.develop.user.controller;

import com.tdu.develop.common.exception.JsonResult;
import com.tdu.develop.user.pojo.Major;
import com.tdu.develop.user.pojo.MenuTrees;
import com.tdu.develop.user.pojo.Subjects;
import com.tdu.develop.user.pojo.Users;
import com.tdu.develop.user.service.DepartmentService;
import com.tdu.develop.user.service.SubjectService;
import com.tdu.develop.user.service.UsersService;
import com.tdu.develop.user.service.impl.DepartmentServiceImpl;
import com.tdu.develop.user.service.impl.SubjectServiceImpl;
import com.tdu.develop.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-08-10:03
 */
@CrossOrigin
@Controller
@RequestMapping(value = "ShouyeController")
public class ShouyeController {

    @Autowired
    DepartmentService asi = new DepartmentServiceImpl();
    @Autowired
    SubjectService subjectService = new SubjectServiceImpl();

    @Autowired
    UsersService usersService = new UserServiceImpl();
    Users users = new Users();

    //显示用户名在右上角
    @RequestMapping(value = "sUserName.action")
    public void sUserName(HttpSession session, HttpServletResponse response) throws IOException {
        System.out.println("ShouyeController.sUserName-------------" + session.getMaxInactiveInterval());
        if (session.getAttribute("Name") != null) {
            String name = session.getAttribute("Name").toString();
            String id = session.getAttribute("ID").toString();

            System.out.println("ShouyeController.sUserName-------------");
            //session.setMaxInactiveInterval(1800);
            session.setAttribute("Name", name);
            session.setAttribute("ID", id);

            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().print(name);
        }
    }

    // 得到用户之下的专业的的科目
    @RequestMapping(value = "getSubjects.action")
    @ResponseBody
    public HashMap<Integer, HashMap<String, List<Subjects>>> getSubjects(HttpServletRequest request,
                                                                         HttpSession session) {
        if (session.getAttribute("ID").toString() != null) {
            String userId = session.getAttribute("ID").toString();
            users.setId(session.getAttribute("ID").toString());
            HashMap<Integer, HashMap<String, List<Subjects>>> majorMap = new HashMap<Integer, HashMap<String, List<Subjects>>>();
            HashMap<String, List<Subjects>> subjectMap = new HashMap<String, List<Subjects>>();
            List<Major> returnJson = new ArrayList<Major>();
            List<String> list = new ArrayList<String>();
            List<Subjects> list2 = subjectService.getSubjectIdList(users.getId());

            for (Subjects subjects : list2) {
                // mayorId去重
                boolean res = false;
                for (String s : list) {
                    if (s.equals(subjects.getMajor_Id())) {
                        res = true;
                    }
                }
                if (res == false) {
                    list.add(subjects.getMajor_Id());
                }
            }

            for (String string : list) {
                Major major = asi.getMajor(string);
                if (major != null) {
                    returnJson.add(major);
                }
            }

            for (int i = 0; i < returnJson.size(); i++) {
                List<Subjects> subList = new ArrayList<Subjects>();
                for (Subjects subjects : list2) {
                    if (subjects.getMajor_Id().equals(returnJson.get(i).getId())) {
                        subList.add(subjects);
                    }
                }
                for (int j = 0; j < subList.size(); j++) {
                    subjectMap.put(returnJson.get(i).getMajorName() + "," + returnJson.get(i).getId(), subList);
                }
                majorMap.put(i, subjectMap);

            }
            return majorMap;
        } else {
            return null;
        }
    }

    // 得到用户之下的专业的的科目(供开发者平台使用）
    @RequestMapping(value = "getSubjects_develop.action")
    @ResponseBody
    public HashMap<Integer, HashMap<String, List<Subjects>>> getSubjects_develop(HttpServletRequest request,
                                                                                 HttpSession session) {
        String userId = session.getAttribute("ID").toString();
        users.setId(session.getAttribute("ID").toString());
        HashMap<Integer, HashMap<String, List<Subjects>>> majorMap = new HashMap<Integer, HashMap<String, List<Subjects>>>();
        HashMap<String, List<Subjects>> subjectMap = new HashMap<String, List<Subjects>>();
        List<Major> returnJson = new ArrayList<Major>();
        List<String> list = new ArrayList<String>();
        List<Subjects> list2 = subjectService.getSubjectIdList_develop(users.getId());

        for (Subjects subjects : list2) {
            // mayorId去重
            boolean res = false;
            for (String s : list) {
                if (s.equals(subjects.getMajor_Id())) {
                    res = true;
                }
            }
            if (res == false) {
                list.add(subjects.getMajor_Id());
            }
        }

        for (String string : list) {
            Major major = asi.getMajor(string);
            if (major != null) {
                returnJson.add(major);
            }
        }

        for (int i = 0; i < returnJson.size(); i++) {
            List<Subjects> subList = new ArrayList<Subjects>();
            for (Subjects subjects : list2) {
                if (subjects.getMajor_Id().equals(returnJson.get(i).getId())) {
                    subList.add(subjects);
                }
            }
            for (int j = 0; j < subList.size(); j++) {
                subjectMap.put(returnJson.get(i).getMajorName() + "," + returnJson.get(i).getId(), subList);
            }
            majorMap.put(i, subjectMap);

        }
        return majorMap;
    }


    // 得到用户之下的专业的的科目(供开发者平台使用）
    @RequestMapping(value = "getSubjects_resource.action")
    @ResponseBody
    public HashMap<Integer, HashMap<String, List<Subjects>>> getSubjects_resource(HttpServletRequest request,
                                                                                  HttpSession session) {
        String userId = session.getAttribute("ID").toString();
        users.setId(session.getAttribute("ID").toString());
        HashMap<Integer, HashMap<String, List<Subjects>>> majorMap = new HashMap<Integer, HashMap<String, List<Subjects>>>();
        HashMap<String, List<Subjects>> subjectMap = new HashMap<String, List<Subjects>>();
        List<Major> returnJson = new ArrayList<Major>();
        List<String> list = new ArrayList<String>();
        List<Subjects> list2 = subjectService.getSubjectIdList_resource(users.getId());

        for (Subjects subjects : list2) {
            // mayorId去重
            boolean res = false;
            for (String s : list) {
                if (s.equals(subjects.getMajor_Id())) {
                    res = true;
                }
            }
            if (res == false) {
                list.add(subjects.getMajor_Id());
            }
        }

        for (String string : list) {
            Major major = asi.getMajor(string);
            if (major != null) {
                returnJson.add(major);
            }
        }

        for (int i = 0; i < returnJson.size(); i++) {
            List<Subjects> subList = new ArrayList<Subjects>();
            for (Subjects subjects : list2) {
                if (subjects.getMajor_Id().equals(returnJson.get(i).getId())) {
                    subList.add(subjects);
                }
            }
            for (int j = 0; j < subList.size(); j++) {
                subjectMap.put(returnJson.get(i).getMajorName() + "," + returnJson.get(i).getId(), subList);
            }
            majorMap.put(i, subjectMap);

        }
        return majorMap;
    }


    // 得到用户之下的专业的的科目 开发者单独页面使用
    @RequestMapping(value = "getSubjects2.action", method = {RequestMethod.POST})
    @ResponseBody
    public HashMap<Integer, HashMap<String, List<Subjects>>> getSubjects2(HttpServletRequest request,
                                                                          HttpSession session, HttpServletResponse response) {
        users.setId(request.getParameter("userId"));
        HashMap<Integer, HashMap<String, List<Subjects>>> majorMap = new HashMap<Integer, HashMap<String, List<Subjects>>>();
        HashMap<String, List<Subjects>> subjectMap = new HashMap<String, List<Subjects>>();
        List<Major> returnJson = new ArrayList<Major>();
        List<String> list = new ArrayList<String>();
        List<Subjects> list2 = subjectService.getSubjectIdList2(users.getId());

        for (Subjects subjects : list2) {
            // mayorId去重
            boolean res = false;
            for (String s : list) {
                if (s.equals(subjects.getMajor_Id())) {
                    res = true;
                }
            }
            if (res == false) {
                list.add(subjects.getMajor_Id());
            }
        }

        for (String string : list) {
            Major major = asi.getMajor(string);
            if (major != null) {
                returnJson.add(major);
            }
        }

        for (int i = 0; i < returnJson.size(); i++) {
            List<Subjects> subList = new ArrayList<Subjects>();
            for (Subjects subjects : list2) {
                if (subjects.getMajor_Id().equals(returnJson.get(i).getId())) {
                    subList.add(subjects);
                }
            }
            for (int j = 0; j < subList.size(); j++) {
                subjectMap.put(returnJson.get(i).getMajorName() + "," + returnJson.get(i).getId(), subList);
            }
            majorMap.put(i, subjectMap);

        }
        return majorMap;
    }

    /**
     * 加载左侧菜单栏(南靖项目接口）
     *
     * @param response
     * @param session
     * @return
     */
    @RequestMapping("loadMenus.action")
    @ResponseBody
    public JsonResult loadMenus(HttpServletResponse response, HttpSession session) {
        String userId = "4d272f66-9dac-4b87-a2a1-22b6e5910779";
        List<MenuTrees> list = subjectService.loadMenus(userId);
        return new JsonResult(list);
    }
}
