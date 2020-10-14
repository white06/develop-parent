package com.tdu.develop.user.controller;

import com.tdu.develop.resource.pojo.SubjectTrees;
import com.tdu.develop.resource.service.SubjectTreeService;
import com.tdu.develop.resource.service.impl.SubjectTreeServiceImpl;
import com.tdu.develop.user.pojo.*;
import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.user.service.DepartmentService;
import com.tdu.develop.user.service.impl.DepartmentServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 志阳
 * @create 2019-08-19-17:56
 */
@CrossOrigin
@Controller
@RequestMapping(value = "DepartmentController")
public class DepartmentController {
    @Autowired
    DepartmentService departmentService = new DepartmentServiceImpl();

    @Autowired
    SubjectTreeService subjectTreeService = new SubjectTreeServiceImpl();


    @RequestMapping(value = "GetDepartmentList.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Department> GetDepartmentList(HttpServletRequest request, HttpSession session) {
        String userId = session.getAttribute("ID").toString();

        List<Department> returnJson = departmentService.DepartmentList(userId);

        return returnJson;
    }

    @RequestMapping(value = "GetAllDepartmentList.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Department> getAllDepartmentList(HttpServletRequest request, HttpSession session) {
        List<Department> returnJson = departmentService.getAllDepartmentList();
        return returnJson;
    }

    @RequestMapping(value = "GetDepartmentListByid.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Department> GetDepartmentListByid(HttpServletRequest request) {

        List<Department> returnJson = departmentService.DepartmentListById(request.getParameter("id"));

        return returnJson;
    }


//    @RequestMapping(value="Updatedepartment.action",method={RequestMethod.POST})
//    @ResponseBody
//    public boolean Updatedepartment(HttpServletRequest request,HttpServletResponse response,@RequestParam("file") MultipartFile file){
//
//        Department department=new Department();
//        department.setId(request.getParameter("Id"));
//        department.setName(request.getParameter("Name"));
//        department.setAddress(request.getParameter("Address"));
//        department.setPhone(request.getParameter("Phone"));
//        department.setEmail(request.getParameter("Email"));
//        department.setWebsite(request.getParameter("Website"));
//        department.setBriefintroduction(request.getParameter("Briefintroduction"));
//
//
//        if (!file.isEmpty()) {
//
//
//            //数据库中保存的文件路径
//
//            // 文件保存路径
//            String fileURL=request.getParameter("filePath");
//            //得到文件名
//            String fileName=file.getOriginalFilename();
//            //得到文件后缀
//            String fileEnd = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
//            //使用UUID创建文件名
//            String uuid = UUID.randomUUID().toString();
//            //使用StringBuffer创建文件的存储路径
//            StringBuffer sbRealPath = new StringBuffer();
//            sbRealPath.append(fileURL).append(uuid).append(".").append(fileEnd);
////             StringBuffer sbfileway=new StringBuffer();
////             sbfileway.append(fileway).append(uuid).append(".").append(fileEnd);
//            StringBuffer sbname=new StringBuffer();
//            sbname.append(uuid).append(".").append(fileEnd);
//
//            // 转存文件
//            try {
//                file.transferTo(new File(sbRealPath.toString()));
//
//                department.setPhoto(("/Photo/"+uuid+"."+fileEnd));
//
//            } catch (IllegalStateException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        }else {
//            department.setPhoto(request.getParameter("photo"));
//        }
//
//
//
//
//        departmentService.Updatedepartment(department);
//        return true;
//    }


    @RequestMapping(value = "Updatedepartment.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean Updatedepartment(HttpServletRequest request, HttpServletResponse response) {

        Department department = new Department();
        department.setId(request.getParameter("Id"));
        department.setName(request.getParameter("Name"));
        department.setAddress(request.getParameter("Address"));
        department.setPhone(request.getParameter("Phone"));
        department.setEmail(request.getParameter("Email"));
        department.setBriefintroduction(request.getParameter("Briefintroduction"));


//                   if (!file.isEmpty()) {
//
//
//            //数据库中保存的文件路径
//
//            // 文件保存路径
//            String fileURL=request.getParameter("filePath");
//            //得到文件名
//            String fileName=file.getOriginalFilename();
//            //得到文件后缀
//            String fileEnd = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
//            //使用UUID创建文件名
//            String uuid = UUID.randomUUID().toString();
//            //使用StringBuffer创建文件的存储路径
//            StringBuffer sbRealPath = new StringBuffer();
//            sbRealPath.append(fileURL).append(uuid).append(".").append(fileEnd);
////             StringBuffer sbfileway=new StringBuffer();
////             sbfileway.append(fileway).append(uuid).append(".").append(fileEnd);
//            StringBuffer sbname=new StringBuffer();
//            sbname.append(uuid).append(".").append(fileEnd);
//
//            // 转存文件
//            try {
//                file.transferTo(new File(sbRealPath.toString()));
//
//                department.setPhoto(("/Photo/"+uuid+"."+fileEnd));
//
//            } catch (IllegalStateException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        }else {
//            department.setPhoto(request.getParameter("photo"));
//        }
//
//


        departmentService.Updatedepartment2(department);
        return true;
    }

    @RequestMapping(value = "Createdepartment.action", method = {RequestMethod.POST})
    @ResponseBody
//    public boolean Createdepartment(HttpServletRequest request,HttpServletResponse response,@RequestParam("file") MultipartFile file){
    public boolean Createdepartment(HttpServletRequest request, HttpServletResponse response) {

        Department department = new Department();
        department.setId(UUID.randomUUID().toString());
        department.setName(request.getParameter("Name"));
        department.setAddress(request.getParameter("Address"));
        department.setPhone(request.getParameter("Phone"));
        department.setEmail(request.getParameter("Email"));
        department.setWebsite(request.getParameter("Website"));
        department.setBriefintroduction(request.getParameter("Briefintroduction"));

//
//        if (!file.isEmpty()) {
//
//            String path1 = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//            //数据库中保存的文件路径
//
//            // 文件保存路径
//            String fileURL=request.getParameter("filePath");
//            //得到文件名
//            String fileName=file.getOriginalFilename();
//            //得到文件后缀
//            String fileEnd = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
//            //使用UUID创建文件名
//            String uuid = UUID.randomUUID().toString();
//            //使用StringBuffer创建文件的存储路径
//            StringBuffer sbRealPath = new StringBuffer();
//            sbRealPath.append(fileURL).append(uuid).append(".").append(fileEnd);
//            StringBuffer sbname=new StringBuffer();
//            sbname.append(uuid).append(".").append(fileEnd);
//
//            // 转存文件
//            try {
//                file.transferTo(new File(sbRealPath.toString()));
//
//                department.setPhoto(("/Photo/"+uuid+"."+fileEnd));
//
//            } catch (IllegalStateException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        }else {
//            department.setPhoto(request.getParameter("photo"));
//        }


        departmentService.Adddepartment2(department);

        return true;

    }

    @RequestMapping(value = "DeleteDepartment.action", method = {RequestMethod.POST})
    @ResponseBody
    public Boolean DeleteDepartment(HttpServletRequest request, HttpServletResponse response) {
        Boolean flag = false;
        //System.out.println(request.getParameter("id")+"--------");
        List<Major> majorList = departmentService.MajorListByCollege_Id(request.getParameter("id"));
        for (Major major : majorList) {
            //System.out.println(major.getId());
            departmentService.DeleteClassBymajorid(major.getId());
            departmentService.DeleteSubjectBymajorid(major.getId());
            departmentService.DeleteMajor(major.getId());
            //System.out.println(true);

        }
        Boolean tt = departmentService.Deletedepartment(request.getParameter("id"));
        return tt;
    }


    //Major

    @RequestMapping(value = "GetMajorListByUser.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Major> GetMajorListByUser(HttpServletRequest request, HttpSession session) {
        String userId = request.getParameter("userId");
        String collegeId = request.getParameter("id");
        List<Major> returnJson = departmentService.MajorListByCollegeId(collegeId, userId);
        return returnJson;
    }

    @RequestMapping(value = "GetMajorListByid.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Major> GetMajorListByid(HttpServletRequest request, HttpSession session) {

        String userId = session.getAttribute("ID").toString();
        String collegeId = request.getParameter("id");

        List<Major> returnJson = departmentService.MajorListByCollegeId(collegeId, userId);
        return returnJson;
    }

    @RequestMapping(value = "GetMajorByid.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Major> GetMajorByid(HttpServletRequest request) {
        List<Major> returnJson = departmentService.MajorListById(request.getParameter("id"));
        return returnJson;
    }

    @RequestMapping(value = "UpdateMajor.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean UpdateMajor(HttpServletRequest request, HttpServletResponse response) {

        Major major = new Major();
        major.setId(request.getParameter("id"));
        major.setMajorName(request.getParameter("MajorName"));
        departmentService.UpdateMajor(major);
        return true;
    }

    @RequestMapping(value = "CreateMajor.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean CreateMajor(HttpServletRequest request, HttpServletResponse response) {
        Major major = new Major();
        major.setId(UUID.randomUUID().toString());
        major.setMajorName(request.getParameter("MajorName"));
        major.setCollege_Id(request.getParameter("College_Id"));

        departmentService.AddMajor(major);
        return true;
    }

    @RequestMapping(value = "DeleteMajor.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean DeleteMajor(HttpServletRequest request, HttpServletResponse response) {
        if (departmentService.DeleteClassBymajorid(request.getParameter("id")) && departmentService.DeleteSubjectBymajorid(request.getParameter("id"))) {
            departmentService.DeleteMajor(request.getParameter("id"));
        } else {
            departmentService.DeleteMajor(request.getParameter("id"));
        }
        return true;
    }

    //Class
    @RequestMapping(value = "GetClasslistBydepartmentid.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Classes> GetClasslistBydepartmentid(HttpServletRequest request) {
        List<Classes> returnJson = departmentService.GetClasslistBydepartmentid(request.getParameter("id"));
        return returnJson;
    }

    //Class
    @RequestMapping(value = "GetClasslistBymajorid.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Classes> ClassListByMajor_Id(HttpServletRequest request) {
        List<Classes> returnJson = departmentService.ClassListByMajor_Id(request.getParameter("id"));
        return returnJson;
    }

    @RequestMapping(value = "GetClassByid.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Classes> ClassListById(HttpServletRequest request) {
        List<Classes> returnJson = departmentService.ClassListById(request.getParameter("id"));
        return returnJson;
    }

    @RequestMapping(value = "CreateClass.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean CreateClass(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        Classes classes = new Classes();
        classes.setId(UUID.randomUUID().toString());
        String className = request.getParameter("className");
        //System.out.println(className);
        classes.setClassName(className);
        classes.setMajor_id(request.getParameter("Majorid"));

        departmentService.AddClass(classes);
        return true;
    }

    @RequestMapping(value = "DeleteClass.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean DeleteClass(HttpServletRequest request, HttpServletResponse response) {

        departmentService.DeleteClass(request.getParameter("id"));
        departmentService.DeleteClassUser(request.getParameter("id"));
        //此处是删除班级的时候要做的另外的操作删除班级用户
        return true;
    }

    @RequestMapping(value = "getAllUser.action", method = {RequestMethod.POST})
    @ResponseBody
    public List getAllUser(HttpServletRequest request) {
        List returnJson = new ArrayList();
        if (!request.getParameter("classId").equals("00000000-0000-0000-0000-000000000000")) {
            returnJson = departmentService.getAllUser(request.getParameter("classId"));

        } else if (!request.getParameter("subjectId").equals("00000000-0000-0000-0000-000000000000")) {
            returnJson = departmentService.getAllSubjectUser(request.getParameter("subjectId"));

        } else if (!request.getParameter("majorId").equals("00000000-0000-0000-0000-000000000000")) {
            returnJson = departmentService.getAllMajorUser(request.getParameter("majorId"));

        }

        return returnJson;


    }


    @RequestMapping(value = "ClassAddUser.action", method = {RequestMethod.POST})
    @ResponseBody
    public void ClassAddUser(HttpServletRequest request) {
        String ClassId = request.getParameter("ClassId");
        String UserId = request.getParameter("UserId");
        departmentService.ClassAddUser(ClassId, UserId);

    }

    @RequestMapping(value = "SubjectAddUserNew.action", method = {RequestMethod.POST})
    @ResponseBody
    public void SubjectAddUserNew(HttpServletRequest request, @RequestParam("userList") List<String> userList,
                                  @RequestParam("reList") List<String> reList,
                                  @RequestParam("subjectId") String subjectId,
                                  @RequestParam("subjectName") String subjectName,
                                  @RequestParam("subjectType") String subjectType) {


        Subjects subjects = new Subjects();
        subjects.setId(subjectId);
        subjects.setSubjectName(subjectName);
        subjects.setMoney(subjectType);
        departmentService.UpdateSubject(subjects);
        String regEx = "[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：\"\"’。， 、？]";
//可以在中括号内加上任何想要替换的字符，实际上是一个正则表达式
        String aa = "";//这里是将特殊字符换为aa字符串," "代表直接去掉
        for (String userId : userList) {
            userId = userId.replaceAll(regEx, aa);
            departmentService.SubjectAddUser(userId, subjectId);
        }
        if (reList.size() > 0) {
            for (String userkey : reList) {
                userkey = userkey.replaceAll(regEx, aa);
                departmentService.SubjectRemoveUser(userkey, subjectId);
            }
        }

    }

    @RequestMapping(value = "DepartmentAddUserNew.action", method = {RequestMethod.POST})
    @ResponseBody
    public void DepartmentAddUserNew(HttpServletRequest request, @RequestParam("userList") List<String> userList,
                                     @RequestParam("reList") List<String> reList,
                                     @RequestParam("departmentId") String departmentId) {
        String regEx = "[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：\"\"’。， 、？]";
//可以在中括号内加上任何想要替换的字符，实际上是一个正则表达式
        String aa = "";//这里是将特殊字符换为aa字符串," "代表直接去掉
        for (String userId : userList) {
            userId = userId.replaceAll(regEx, aa);
            departmentService.DepartmentUser(departmentId, userId);
        }
        if (reList.size() > 0) {
            for (String userkey : reList) {
                userkey = userkey.replaceAll(regEx, aa);
                departmentService.DepartmentRemoveUser(departmentId, userkey);
            }
        }
    }

    @RequestMapping(value = "MajprAddUserNew.action", method = {RequestMethod.POST})
    @ResponseBody
    public void MajprAddUserNew(HttpServletRequest request, @RequestParam("userList") List<String> userList,
                                @RequestParam("reList") List<String> reList,
                                @RequestParam("majorId") String majorId) {
        String regEx = "[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：\"\"’。， 、？]";
//可以在中括号内加上任何想要替换的字符，实际上是一个正则表达式
        String aa = "";//这里是将特殊字符换为aa字符串," "代表直接去掉
        for (String userId : userList) {
            userId = userId.replaceAll(regEx, aa);
            departmentService.MajorAddUser(majorId, userId);
        }
        if (reList.size() > 0) {
            for (String userkey : reList) {
                userkey = userkey.replaceAll(regEx, aa);
                departmentService.MajorRemoveUser(majorId, userkey);
            }
        }
    }


    @RequestMapping(value = "ClassAddUserNew.action", method = {RequestMethod.POST})
    @ResponseBody
    public void ClassAddUserNew(HttpServletRequest request, @RequestParam("userList") List<String> userList,
                                @RequestParam("reList") List<String> reList,
                                @RequestParam("classId") String classId,
                                @RequestParam("className") String className) {

        Classes classes = new Classes();
        classes.setId(classId);
        classes.setClassName(className);
        departmentService.UpdateClass(classes);
        String regEx = "[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：\"\"’。， 、？]";
//可以在中括号内加上任何想要替换的字符，实际上是一个正则表达式
        String aa = "";//这里是将特殊字符换为aa字符串," "代表直接去掉
        for (String userId : userList) {
            userId = userId.replaceAll(regEx, aa);
            departmentService.ClassAddUser(classId, userId);
        }
        if (reList.size() > 0) {
            for (String userkey : reList) {
                userkey = userkey.replaceAll(regEx, aa);
                departmentService.ClassRemoveUser(classId, userkey);
            }
        }
    }

    @RequestMapping(value = "ClassRemoveUser.action", method = {RequestMethod.POST})
    @ResponseBody
    public void ClassRemoveUser(HttpServletRequest request) {
        String ClassId = request.getParameter("ClassId");
        String UserId = request.getParameter("UserId");
        departmentService.ClassRemoveUser(ClassId, UserId);

    }


    //subject

    @RequestMapping(value = "GetSubjectByid.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Subjects> GetSubjectByid(HttpServletRequest request) {
        List<Subjects> returnJson = departmentService.SubjectListById(request.getParameter("id"));
        return returnJson;
    }

    @RequestMapping(value = "getMajorSubjectsByUser.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Subjects> getMajorSubjectsByUser(HttpServletRequest request, HttpSession session) {
        String userId = request.getParameter("userId");
        String majorId = request.getParameter("id");
        List<Subjects> returnJson = departmentService.getMajorSubjects(majorId, userId);
        return returnJson;
    }

    @RequestMapping(value = "getMajorSubjects.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Subjects> getMajorSubjects(HttpServletRequest request, HttpSession session) {
        String userId = session.getAttribute("ID").toString();
        String majorId = request.getParameter("id");
        List<Subjects> returnJson = departmentService.getMajorSubjects(majorId, userId);
        return returnJson;
    }

    @RequestMapping(value = "CreateSubject.action", method = {RequestMethod.POST})
    @ResponseBody
    public void CreateSubject(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        String userId = session.getAttribute("ID").toString();

        java.util.Date now = new java.util.Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String subjectid = UUID.randomUUID().toString();
        Subjects subjects = new Subjects();
        subjects.setId(subjectid);
        subjects.setSubjectName(request.getParameter("SubjectName"));
        subjects.setMajor_Id(request.getParameter("Majorid"));
        String subjectType = request.getParameter("subjectType");
        subjects.setMoney(subjectType);
        subjects.setSubjectIntro(request.getParameter("SubjectName"));
        subjects.setData(simpleDateFormat.format(now));

        /*if (!file.isEmpty()) {
            // 文件保存路径  
            String fileURL=request.getParameter("filePath");
            //得到文件名
            String fileName=file.getOriginalFilename();
            //得到文件后缀
            String fileEnd = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
            //使用UUID创建文件名
            String uuid = UUID.randomUUID().toString();
            //使用StringBuffer创建文件的存储路径
            StringBuffer sbRealPath = new StringBuffer();
            sbRealPath.append(fileURL).append(uuid).append(".").append(fileEnd);
            StringBuffer sbname=new StringBuffer();
            sbname.append(uuid).append(".").append(fileEnd);
            // 转存文件  
            try {
                file.transferTo(new File(sbRealPath.toString()));
                subjects.setSubjectIcon(("/img/"+uuid+"."+fileEnd));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {*/
        subjects.setSubjectIcon((""));
        //}
        departmentService.AddSubject(subjects);

        if (subjectType.equals("1")) {  //开发者平台
            String[] treeArray = {"资源库", "素材库", "文本库", "数据库"};
            int nums = 0;
            String columnUpper = null;
            for (String treeName : treeArray) {
                SubjectTrees sTrees = new SubjectTrees();
                String subjecttreeid = UUID.randomUUID().toString();
                columnUpper = subjecttreeid;
                sTrees.setId(subjecttreeid);
                if (nums == 1 || nums == 2 || nums == 3 || nums == 4) {
                    sTrees.setStatus("1");
                    sTrees.setStyle("4");
                } else {
                    sTrees.setStatus((nums + 1) + "");
                    sTrees.setStyle("4");
                }
                sTrees.setSubjectKey(subjectid);
                sTrees.setTreeName(treeName);
                sTrees.setTreeNum((nums + 1));
                if (nums == 0) {
                    sTrees.setIcon("img/ico/zhanshi01.png");
                } else if (nums == 1) {
                    sTrees.setIcon("img/ico/zhanshi02.png");
                } else if (nums == 2) {
                    sTrees.setIcon("img/ico/zhanshi03.png");
                } else if (nums == 3) {
                    sTrees.setIcon("img/ico/zhanshi04.png");
                }
                if (nums == 1) {
                    sTrees.setColumnUpper(null);
                } else {
                    sTrees.setColumnUpper(columnUpper);
                }
                subjectTreeService.AddNewSubjectTree(sTrees);
                Knowledges kledges = new Knowledges();
                String parentId = UUID.randomUUID().toString();
                kledges.setId(parentId);
                kledges.setContent("Root");
                kledges.setBeforCondition("<root><beforesee></beforesee><userkey></userkey><grades></grades></root>");
                kledges.setImageIcons("../../../Source/imgicon/tag_orange.png");
                kledges.setKnowledgecontentId("00000000-0000-0000-0000-000000000000");
                kledges.setParentKnowledge(null);
                kledges.setPreKnowledge(null);
                kledges.setSubjectTree_Id(subjecttreeid);
                departmentService.AddNewKnowledge(kledges);

                Knowledges knowledges1 = new Knowledges();
                String knowledges1Id = UUID.randomUUID().toString();
                knowledges1.setId(knowledges1Id);
                if (nums == 0) {
                    knowledges1.setContent("模型库");
                } else if (nums == 1) {
                    knowledges1.setContent("音频库");
                } else if (nums == 2) {
                    knowledges1.setContent("WORD");
                } else if (nums == 3) {
                    knowledges1.setContent("数据库1");
                }
                knowledges1.setBeforCondition("<root><beforesee></beforesee><userkey></userkey><grades></grades></root>");
                knowledges1.setImageIcons("../../../Source/imgicon/tag_orange.png");
                knowledges1.setKnowledgecontentId("00000000-0000-0000-0000-000000000000");
                knowledges1.setParentKnowledge(parentId);
                knowledges1.setPreKnowledge(null);
                knowledges1.setSubjectTree_Id(subjecttreeid);
                knowledges1.setCheckDel("0");
                knowledges1.setDelTime(null);
                knowledges1.setUserKey(userId);
                departmentService.AddNewKnowledge(knowledges1);

                Knowledges knowledges2 = new Knowledges();
                String knowledges2Id = UUID.randomUUID().toString();
                knowledges2.setId(knowledges2Id);
                if (nums == 0) {
                    knowledges2.setContent("场景库");
                } else if (nums == 1) {
                    knowledges2.setContent("视频库");
                } else if (nums == 2) {
                    knowledges2.setContent("EXCEL");
                } else if (nums == 3) {
                    knowledges2.setContent("数据库2");
                }
                knowledges2.setBeforCondition("<root><beforesee></beforesee><userkey></userkey><grades></grades></root>");
                knowledges2.setImageIcons("../../../Source/imgicon/tag_orange.png");
                knowledges2.setKnowledgecontentId("00000000-0000-0000-0000-000000000000");
                knowledges2.setParentKnowledge(parentId);
                knowledges2.setPreKnowledge(knowledges1Id);
                knowledges2.setSubjectTree_Id(subjecttreeid);
                knowledges2.setCheckDel("0");
                knowledges2.setDelTime(null);
                knowledges2.setUserKey(userId);
                departmentService.AddNewKnowledge(knowledges2);


                Knowledges knowledges3 = new Knowledges();
                String knowledges3Id = UUID.randomUUID().toString();
                knowledges3.setId(knowledges3Id);
                if (nums == 0) {
                    knowledges3.setContent("课程库");
                } else if (nums == 1) {
                    knowledges3.setContent("图片库");
                } else if (nums == 2) {
                    knowledges3.setContent("PPT");
                } else if (nums == 3) {
                    knowledges3.setContent("数据库3");
                }
                knowledges3.setBeforCondition("<root><beforesee></beforesee><userkey></userkey><grades></grades></root>");
                knowledges3.setImageIcons("../../../Source/imgicon/tag_orange.png");
                knowledges3.setKnowledgecontentId("00000000-0000-0000-0000-000000000000");
                knowledges3.setParentKnowledge(parentId);
                knowledges3.setPreKnowledge(knowledges2Id);
                knowledges3.setSubjectTree_Id(subjecttreeid);
                knowledges3.setCheckDel("0");
                knowledges3.setDelTime(null);
                knowledges3.setUserKey(userId);
                departmentService.AddNewKnowledge(knowledges3);
                nums++;
            }
        } else if (subjectType.equals("0")) { //资源平台  课程资源 微课资源 课件资源 题库资源 考核资源
            String[] treeArray = {"课程资源", "微课资源", "课件资源", "题库资源", "考核资源"};
            int nums = 0;
            String columnUpper = null;
            for (String treeName : treeArray) {
                SubjectTrees sTrees = new SubjectTrees();
                String subjecttreeid = UUID.randomUUID().toString();
                columnUpper = subjecttreeid;
                sTrees.setId(subjecttreeid);
                if (nums == 1 || nums == 2 || nums == 3 || nums == 4) {
                    sTrees.setStatus("1");
                    sTrees.setStyle("4");
                } else {
                    sTrees.setStatus((nums + 1) + "");
                    sTrees.setStyle("4");
                }
                sTrees.setSubjectKey(subjectid);
                sTrees.setTreeName(treeName);
                sTrees.setTreeNum((nums + 1));
                if (nums == 0) {
                    sTrees.setIcon("img/ico/zhanshi01.png");
                } else if (nums == 1) {
                    sTrees.setIcon("img/ico/zhanshi02.png");
                } else if (nums == 2) {
                    sTrees.setIcon("img/ico/zhanshi03.png");
                } else if (nums == 3) {
                    sTrees.setIcon("img/ico/zhanshi04.png");
                } else if (nums == 4) {
                    sTrees.setIcon("img/ico/zhanshi05.png");
                }
                if (nums == 1) {
                    sTrees.setColumnUpper(null);
                } else {
                    sTrees.setColumnUpper(columnUpper);
                }
                subjectTreeService.AddNewSubjectTree(sTrees);
                Knowledges kledges = new Knowledges();
                String parentId = UUID.randomUUID().toString();
                kledges.setId(parentId);
                kledges.setContent("Root");
                kledges.setBeforCondition("<root><beforesee></beforesee><userkey></userkey><grades></grades></root>");
                kledges.setImageIcons("../../../Source/imgicon/tag_orange.png");
                kledges.setKnowledgecontentId("00000000-0000-0000-0000-000000000000");
                kledges.setParentKnowledge(null);
                kledges.setPreKnowledge(null);
                kledges.setSubjectTree_Id(subjecttreeid);
                departmentService.AddNewKnowledge(kledges);
                nums++;
            }
        }

    }

    @RequestMapping(value = "DeleteSubject.action", method = {RequestMethod.POST})
    @ResponseBody
    public void DeleteSubject(HttpServletRequest request, HttpServletResponse response) {

        departmentService.DeleteSubject(request.getParameter("id"));
        departmentService.DeleteSubjectUser(request.getParameter("id"));

        /*try {
          //  response.sendRedirect(request.getContextPath()+"/SysAdministrate/Subject/index.php");
            response.sendRedirect("/tdu/SysAdministrate/Subject/index.html");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/

    }

    @RequestMapping(value = "SubjectAddUser.action", method = {RequestMethod.POST})
    @ResponseBody
    public void SubjectAddUser(HttpServletRequest request) {
        String subjectId = request.getParameter("subjectId");
        String UserId = request.getParameter("UserId");
        departmentService.SubjectAddUser(UserId, subjectId);

    }

    @RequestMapping(value = "SubjectRemoveUser.action", method = {RequestMethod.POST})
    @ResponseBody
    public void SubjectRemoveUser(HttpServletRequest request) {
        String subjectId = request.getParameter("subjectId");
        String UserId = request.getParameter("UserId");
        departmentService.SubjectRemoveUser(UserId, subjectId);

    }

    @RequestMapping(value = "CreateAllSubjectUser.action", method = {RequestMethod.POST})
    @ResponseBody
    public void CreateAllSubjectUser(HttpServletRequest request, HttpServletResponse response) {
        String subjectId = request.getParameter("subjectId");

        departmentService.CreateAllSubjectUser(subjectId);

        try {
            response.getWriter().print("ok");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "SaveSubject.action", method = {RequestMethod.POST})
    @ResponseBody
    public void SaveSubject(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String subjectId = request.getParameter("subjectId");
        String SubjectName = request.getParameter("SubjectName");
        String Money = request.getParameter("Money");
        departmentService.SaveSubject(subjectId, SubjectName, Money);
        try {
            //  response.sendRedirect(request.getContextPath()+"/SysAdministrate/Subject/index.php");
            response.sendRedirect("/tdu/SysAdministrate/Subject/index.html");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "GetUserJson.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<UserJson> GetUserJson(HttpServletRequest request) {

        return departmentService.GetUserJson();

    }

    @RequestMapping(value = "GetAllRoles.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Roles> GetAllRoles(HttpServletRequest request) {

        return departmentService.GetAllRoles();

    }

    @RequestMapping(value = "GetUserRolesbyId.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Roles> GetUserRolesbyId(HttpServletRequest request) {

        return departmentService.GetUserRolesbyId(request.getParameter("userid"));

    }

    @RequestMapping(value = "SaveUserRoles.action", method = {RequestMethod.POST})
    @ResponseBody
    public void SaveUserRoles(HttpServletRequest request, HttpServletResponse response) {
        String[] roleidarray = request.getParameter("roleidarray").split(",");
        departmentService.SaveUserRoles(request.getParameter("userid"), roleidarray);

        try {
            response.getWriter().print("ok");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "seleMajorName.action", method = {RequestMethod.POST})
    @ResponseBody
    public void seleMajorName(HttpServletRequest request, HttpServletResponse response) {
        String majorId = request.getParameter("majorId");
        String name = departmentService.seleMajorName(majorId);
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.getWriter().print(name);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "SaveClass.action", method = {RequestMethod.POST})
    @ResponseBody
    public void SaveClass(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        response.setCharacterEncoding("UTF-8");

        String ClassId = request.getParameter("ClassId");
        String[] array = request.getParameterValues("id[]");
        for (String string : array) {
            departmentService.ClassAddUser(ClassId, string);
        }
        //String SubjectName=request.getParameter("SubjectName");
        String classname = null;
        try {
            classname = new String(request.getParameter("classname").getBytes("ISO8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        departmentService.SaveClass(ClassId, classname);
        try {
            //  response.sendRedirect(request.getContextPath()+"/QZ/SysAdministrate/Subject/index.php");
            response.sendRedirect("/tdu/QZ/SysAdministrate/Subject/index.html");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
