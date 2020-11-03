package com.tdu.develop.resource.controller;

import com.tdu.develop.common.exception.JsonResult;
import com.tdu.develop.resource.pojo.Exams;
import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.Knowlegcontent;
import com.tdu.develop.resource.pojo.QuestionPagers;
import com.tdu.develop.resource.service.ExamService;
import com.tdu.develop.resource.service.KnowledgesService;
import com.tdu.develop.resource.service.SubjectTreeService;
import com.tdu.develop.resource.service.OfficeService;
import com.tdu.develop.resource.service.impl.ExamServiceImpl;
import com.tdu.develop.resource.service.impl.KnowledgesServiceImpl;
import com.tdu.develop.resource.service.impl.OfficeServiceImpl;
import com.tdu.develop.resource.service.impl.SubjectTreeServiceImpl;
import com.tdu.develop.user.pojo.ZNodes;
import com.tdu.develop.user.service.UsersService;
import com.tdu.develop.user.service.impl.UserServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 志阳
 * @create 2019-11-28-19:14
 */
@CrossOrigin
@Controller
@RequestMapping(value = "nanjingController")
public class nanjingController {

    @Autowired
    KnowledgesService knowledgesService = new KnowledgesServiceImpl();

    @Autowired
    SubjectTreeService subjectTreeService = new SubjectTreeServiceImpl();

    @Autowired
    OfficeService officeService = new OfficeServiceImpl();


    @Autowired
    ExamService examService = new ExamServiceImpl();
    @Autowired
    UsersService usersService = new UserServiceImpl();

    @RequestMapping("daoFangzhenByClass.action")
    @ResponseBody
    public JsonResult daoFangzhenByClass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = "导出文件";
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment; filename=\""
                + new String((fileName).getBytes("GB2312"), "iso8859-1") + ".xls" + "\"");
        //response.addHeader("Content-Length", "C://导出文件.xls");
        String subjectId = request.getParameter("subjectId");
        String depertId = request.getParameter("depertId");
        String majorId = request.getParameter("majorId");
        OutputStream outputStream = response.getOutputStream();

        List<Knowledges> knoList = getFangzhen(subjectId);

        List<HSSFWorkbook> list = officeService.daoFangzhen(depertId, knoList);
        for (HSSFWorkbook hwb : list) {
            hwb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }
        return new JsonResult();
    }

    @RequestMapping("daoFangzhenByKnowledges.action")
    @ResponseBody
    public JsonResult daoFangzhenByKnowledges(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        String fileName = "导出文件";
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment; filename=\""
                + new String((fileName).getBytes("GB2312"), "iso8859-1") + ".xls" + "\"");
        //response.addHeader("Content-Length", "C://导出文件.xls");
        String subjectId = request.getParameter("subjectId");
        String startDate = request.getParameter("startDate");
        String enddatetime = request.getParameter("enddatetime");
        String classId = request.getParameter("classId");
        OutputStream outputStream = response.getOutputStream();

        List<Knowledges> knoList = getFangzhen(subjectId);

        List<HSSFWorkbook> list = officeService.daoFangzhenByKnowledges(classId, knoList,startDate,enddatetime);
        for (HSSFWorkbook hwb : list) {
            hwb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }
        return new JsonResult();
    }

    public List<Knowledges> getFangzhen(String SubjectKey) {
        List<Knowledges> list = new ArrayList<Knowledges>();
        List<Knowledges> rList = new ArrayList<Knowledges>();
        list = subjectTreeService.GetSubjectTreePage(SubjectKey);
        Knowlegcontent knowlegcontent = null;
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).getKnowledgecontentId().equals("00000000-0000-0000-0000-000000000000")) {
                knowlegcontent = subjectTreeService.getSimulateParams(list.get(i).getKnowledgecontentId());
                if (knowlegcontent.getType().equals("仿真考核")) {
                    rList.add(list.get(i));
                }
            }
        }
        return rList;
    }


    @RequestMapping("exportExcelInfo.action")
    @ResponseBody
    public JsonResult exportExcelInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = "导出文件";
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment; filename=\""
                + new String((fileName).getBytes("GB2312"), "iso8859-1") + ".xls" + "\"");
        //response.addHeader("Content-Length", "C://导出文件.xls");
        String classes = request.getParameter("classes");
        String students = request.getParameter("students");
        String teachers = request.getParameter("teacheres");
        String admins = request.getParameter("admines");
        System.out.println(classes + ":" + students + ":" + teachers + ":" + admins);
        OutputStream outputStream = response.getOutputStream();
        List<HSSFWorkbook> list = officeService.exportInfos(classes, students, teachers, admins);
        for (HSSFWorkbook hwb : list) {
            hwb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }
        return new JsonResult();
    }


    @RequestMapping("daoByClass.action")
    @ResponseBody
    public JsonResult daoByClass(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        String fileName = "导出文件";
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment; filename=\""
                + new String((fileName).getBytes("GB2312"), "iso8859-1") + ".xls" + "\"");
        //response.addHeader("Content-Length", "C://导出文件.xls");

        String userId = session.getAttribute("ID").toString();

        String subjectId = request.getParameter("subjectId");

        List<Exams> examList = kaoshi(userId, subjectId);

        OutputStream outputStream = response.getOutputStream();
        List<HSSFWorkbook> list = officeService.daoByClass(examList);
        for (HSSFWorkbook hwb : list) {
            hwb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }
        return new JsonResult();
    }

    public List<Exams> kaoshi(String userId, String subId) {
        List<QuestionPagers> pagersList = examService.selSubPage(subId);
        if (pagersList.size() != 0) {
            List<String> classIdList = usersService.seleClassIdList(userId);
            List<Exams> examsList = new ArrayList<>();
            for (QuestionPagers questionPagers : pagersList) {
                //过滤所有未发布的以及超过试卷答题日期的考试以及未到达开始答题日期的试卷
                for (String classId : classIdList) {
                    Exams exams = examService.getExam(questionPagers.getId(), classId);
                    if (exams != null) {
                        Date taday = new Date();
                        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date endDate = null;
                        try {
                            endDate = sdf.parse(exams.getEndTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date startDate = null;
                        try {
                            startDate = sdf.parse(exams.getStartTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (startDate.before(taday)) {
                            if (taday.before(endDate)) {
                                examsList.add(exams);
                            }
                        }
                    }
                }
            }
            examsList = removeDupliById(examsList);
            return examsList;
        } else {
            return null;
        }
    }

    /**
     * 根据对象属性去重  属性：userId
     *
     * @param persons
     * @return
     */
    public static List<Exams> removeDupliById(List<Exams> persons) {
        Set<Exams> personSet = new TreeSet<>((o1, o2) -> o1.getId().compareTo(o2.getId()));
        personSet.addAll(persons);
        return new ArrayList<>(personSet);
    }


    @RequestMapping("exportExcelByCollege.action")
    @ResponseBody
    public JsonResult exportExcelByCollege(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = "导出文件";
        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment; filename=\""
                + new String((fileName).getBytes("GB2312"), "iso8859-1") + ".xls" + "\"");
        //response.addHeader("Content-Length", "C://导出文件.xls");
        String college = request.getParameter("rolename");
        OutputStream outputStream = response.getOutputStream();
        List<HSSFWorkbook> list = officeService.exportInfoByCollege(college);
        for (HSSFWorkbook hwb : list) {
            hwb.write(outputStream);
            outputStream.flush();
            outputStream.close();
        }
        return new JsonResult();
    }


    /**
     * 导入excel文件
     *
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("importExcelInfos.action")
    @ResponseBody
    public JsonResult importExcelInfos(@RequestParam("excelFile") MultipartFile file, HttpServletRequest request) {
        String filePath = request.getSession().getServletContext().getRealPath("/") + file.getOriginalFilename();
        officeService.importExcelInfos(file);
        return new JsonResult();
    }


    //使用科目树id查询整个科目树的子树
    @RequestMapping(value = "selTheKnow.action")
    @ResponseBody
    public List<ZNodes> selTheKnow(HttpServletRequest request, HttpServletResponse response) {
        List<ZNodes> list = knowledgesService.seleknowledges(request.getParameter("ChooseSubject"));
        return list;
    }

    @RequestMapping(value = "GetSubjectRootId2.action", method = {RequestMethod.GET})
    @ResponseBody
    public HashMap<String, String> getSubjectRootId2(HttpServletRequest request, HttpSession session) {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        String st = request.getParameter("treetype");
        System.out.println("treetype  :" + st);
        String str = subjectTreeService.getSubjectRootId(st);
        System.out.println("str  :" + str);
        resultMap.put("subjectId", str);
        return resultMap;
    }

    /**
     * 资源树文件树结构展示
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "seleKnowledgesNan.action", method = {RequestMethod.GET})
    @ResponseBody
    public List<ZNodes> seleKnowledgesNan(HttpServletRequest request, HttpSession session) {
        String userId = (String) session.getAttribute("ID");
        String id = request.getParameter("treetype");
        List<ZNodes> list = subjectTreeService.seleKnowledgesNan(id);
        return list;
    }
}
