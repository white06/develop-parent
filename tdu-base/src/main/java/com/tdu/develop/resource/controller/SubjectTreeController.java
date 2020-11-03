package com.tdu.develop.resource.controller;

import com.tdu.develop.common.exception.JsonResult;
import com.tdu.develop.models.pojo.Models;
import com.tdu.develop.models.pojo.Scenes;
import com.tdu.develop.models.service.DevelopModelService;
import com.tdu.develop.models.service.DevelopSceneService;
import com.tdu.develop.models.service.impl.DevelopModelServiceImpl;
import com.tdu.develop.models.service.impl.DevelopSceneServiceImpl;
import com.tdu.develop.resource.pojo.*;
import com.tdu.develop.resource.service.SubjectTreeService;
import com.tdu.develop.resource.service.impl.SubjectTreeServiceImpl;
import com.tdu.develop.user.pojo.ZNodes;
import com.tdu.develop.user.service.UsersService;
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
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 志阳
 * @create 2019-08-14-14:32
 */
@CrossOrigin
@Controller
@RequestMapping(value = "SubjectTreeController")
public class SubjectTreeController {

    @Autowired
    SubjectTreeService subjectTreeService = new SubjectTreeServiceImpl();

    @Autowired
    DevelopModelService developModelService = new DevelopModelServiceImpl();

    @Autowired
    DevelopSceneService developSceneService = new DevelopSceneServiceImpl();

    @Autowired
    UsersService usersService = new UserServiceImpl();

    @RequestMapping(value = "GetSubjectRootId.action", method = {RequestMethod.POST})
    @ResponseBody
    public void GetSubjectRootId(HttpServletRequest request, HttpServletResponse response) {
        String treetype = request.getParameter("treetype");
        String rString = subjectTreeService.GetSubjectRootId(treetype);

        try {
            response.getWriter().print("{\"Key\":\"true\",\"Value\":\"" + rString + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("SaveSimulateModel.action")
    @ResponseBody
    public Map<String, String> saveSimulateModel(HttpServletRequest request) {
        Knowlegcontent kt = new Knowlegcontent();
        kt.setNmae(request.getParameter("simulate"));
        kt.setId(request.getParameter("sourceid"));
        kt.setCustomname(request.getParameter("name"));
        kt.setCustomstyle("default");
        return subjectTreeService.saveSimulateModel(kt);
    }


    @RequestMapping("Remove.action")
    @ResponseBody
    public Map<String, String> remove(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        String treeNodeId = null;
        try {
            treeNodeId = request.getParameter("Id");
            //System.out.println("1:"+treeNodeId);
            String subjectId = request.getParameter("subjectId");
            subjectTreeService.remove(treeNodeId, subjectId);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("Value", "删除成功");
            return map;
        }
        /*删除模型*//*
        mlsi.delModel2(treeNodeId);
        *//*end*/
        map.put("Key", "删除成功");
        return map;
    }

    /**
     * 老师获取学生考核成绩
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getScoreTeacherNan.action", method = {RequestMethod.POST})
    @ResponseBody
    public StutotalScores getScoreTeacherNan(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String rId = request.getParameter("rId");
        String userId = request.getParameter("userId");
        return subjectTreeService.getStudentScoreNan(rId, userId);
    }


    /**
     * 获取列表展示
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getUsersSub.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<SubjectTrees> getUsersSub(HttpServletRequest request, HttpServletResponse response) {
        String majorId = request.getParameter("majorId");
        return subjectTreeService.getUsersSub(majorId);

    }

    @RequestMapping(value = "getSubSearch.action", method = {RequestMethod.POST})
    @ResponseBody
    public HashMap<String, List> getSubSearch(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        request.setCharacterEncoding("utf-8");

        HashMap<String, List> resultMap = new HashMap<String, List>();


        List<Models> MoList = new ArrayList<>();

        List<Scenes> SeList = new ArrayList<>();

        String subjectId = request.getParameter("subjectId");

        String sarchStr = request.getParameter("sarchStr");

        List<SubjectTrees> list = subjectTreeService.getUsersSub(subjectId);
        System.out.println("subjectId :" + subjectId + " sarchStr :" + sarchStr);


        for (int i = 0; i < list.size(); i++) {
            /*
             * 0-模型库 1-场景库 2-题库 3-考试 4-资源类
             * */
            if (list.get(i).getStyle().equals("0")) {
                MoList = developModelService.getContentModels(list.get(i).getId(), sarchStr);
                resultMap.put("moList", MoList);
            } else if (list.get(i).getStyle().equals("1")) {
                SeList = developSceneService.getContentScenes(list.get(i).getId(), sarchStr);
                resultMap.put("seList", SeList);
            } else if (list.get(i).getStyle().equals("3")) {
                List<Knowledges> kcList = subjectTreeService.getContentKnowledges(list.get(i).getId(), sarchStr);
                resultMap.put("tiList", kcList);
            } else if (list.get(i).getStyle().equals("4")) {
                List<Knowledges> kcList = subjectTreeService.getContentKnowledges(list.get(i).getId(), sarchStr);
                resultMap.put("ziList" + i + "", kcList);
            }
        }

        for (SubjectTrees sub : list) {

        }
        return resultMap;
    }

    @RequestMapping(value = "getKnowledgesUrl.action", method = {RequestMethod.POST})
    @ResponseBody
    public String getKnowledgesUrl(HttpServletRequest request, HttpSession session) {
        String st = request.getParameter("Id");
        String str = subjectTreeService.getKnowledgesUrl(st);
        return str;
    }

    @RequestMapping(value = "GetSubjectTree.action", method = {RequestMethod.POST})
    @ResponseBody
    public List GetSubjectTree(HttpServletRequest request, HttpServletResponse response) {
        String SubjectKey = request.getParameter("SubjectKey");

        List rList = new ArrayList();
        rList = subjectTreeService.GetSubjectTree(SubjectKey);
        return rList;

    }

    @RequestMapping(value = "GetSubjectTreePage.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Knowledges> GetSubjectTreePage(HttpServletRequest request, HttpServletResponse response) {
        String SubjectKey = request.getParameter("SubjectKey");
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

    /**
     * 资源树文件树结构展示
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "seleKnowledges.action", method = {RequestMethod.GET})
    @ResponseBody
    public List<ZNodes> seleKnowledges(HttpServletRequest request, HttpSession session) {
        String userId = (String) session.getAttribute("ID");
        String id = request.getParameter("treetype");
        List<ZNodes> list = subjectTreeService.seleknowledges(id, userId);
        return list;
    }


    @RequestMapping(value = "getSourceList.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<SubjectTrees> getSourceList(HttpServletRequest request) {
        List<SubjectTrees> list = subjectTreeService.getSourceList(request.getParameter("subjectid"));
        return list;
    }

    @RequestMapping(value = "GetSubjectRootId.action", method = {RequestMethod.GET})
    @ResponseBody
    public String getSubjectRootId(HttpServletRequest request, HttpSession session) {
        String st = request.getParameter("treetype");
        String str = subjectTreeService.getSubjectRootId(st);
        return str;
    }

    @RequestMapping(value = "subjectChange.action", method = {RequestMethod.GET})
    @ResponseBody
    public List<Knowledges> subjectChange(HttpServletRequest request) {
        List<Knowledges> list = subjectTreeService.subjectChange(request.getParameter("treetype"));
        return list;
    }

    @RequestMapping(value = "treeChange.action", method = {RequestMethod.GET})
    @ResponseBody
    public List<SubjectTrees> treeChange(HttpServletRequest request) {
        List<SubjectTrees> list = subjectTreeService.treeChange(request.getParameter("id"));
        return list;
    }

    @RequestMapping(value = "getType.action", method = {RequestMethod.POST})
    @ResponseBody
    public JsonResult getType(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String contentid = request.getParameter("contentid");
        String type = subjectTreeService.getType(contentid);
        //System.out.println(type);
        return new JsonResult(type);
    }

    @RequestMapping("GetSimulateParams.action")
    @ResponseBody
    public Knowlegcontent getSimulateParams(HttpServletRequest request) {
        String id = request.getParameter("id");
        Knowlegcontent kt = subjectTreeService.getSimulateParams(id);
        return kt;
    }

    @RequestMapping("GetParams.action")
    @ResponseBody
    public Knowlegcontent getParams(HttpServletRequest request) {
        Knowlegcontent knowlegcontent = subjectTreeService.getParams(request.getParameter("id"));
        return knowlegcontent;
    }

    @RequestMapping("SaveCustomModel.action")
    @ResponseBody
    public Map<String, String> saveCustomModel(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            request.setCharacterEncoding("utf-8");
            String nmae = request.getParameter("errorques");
            Knowlegcontent knowlegcontent = new Knowlegcontent();
            String id = request.getParameter("sourceid");
            String customName = request.getParameter("name");
            //System.out.println("customName:"+customName);
            knowlegcontent.setId(id);
            knowlegcontent.setNmae(nmae);
            knowlegcontent.setCustomname(customName);
            map = subjectTreeService.saveCustomModel(knowlegcontent);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取nmae
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "getKnowNmae.action", method = {RequestMethod.POST})
    @ResponseBody
    public void getKnowNmae(HttpServletRequest request, HttpServletResponse response) {
        String knowledgecontentId = request.getParameter("knowledgecontentId");
        String type = subjectTreeService.getKnowNmae(knowledgecontentId);
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.getWriter().print(type);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 根据subId获取跟我练资源信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getPractice.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Knowledges> getPractice(HttpServletRequest request, HttpServletResponse response) {
        String subId = request.getParameter("subId");
        return subjectTreeService.getPractice(subId);
    }

    /**
     * 根据subId获取来闯关资源信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getGo.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Knowledges> getGo(HttpServletRequest request, HttpServletResponse response) {
        String subId = request.getParameter("subId");
        return subjectTreeService.getGo(subId);
    }

    /**
     * 根据subId获取跟我做资源信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getDo.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Knowledges> getDo(HttpServletRequest request, HttpServletResponse response) {
        String subId = request.getParameter("subId");
        return subjectTreeService.getDo(subId);
    }

    /**
     * 根据subId获取Office资源信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getOffice.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Knowledges> getOffice(HttpServletRequest request, HttpServletResponse response) {
        String subId = request.getParameter("subId");
        return subjectTreeService.getOffice(subId);
    }

    /**
     * 获取知识点内容
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getKc.action", method = {RequestMethod.POST})
    @ResponseBody
    public Knowlegcontent getKc(HttpServletRequest request, HttpServletResponse response) {
        String oId = request.getParameter("oId");
        return subjectTreeService.getKc(oId);
    }

    /**
     * 根据dId获取考试信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getKcId.action", method = {RequestMethod.POST})
    @ResponseBody
    public String getKcId(HttpServletRequest request, HttpServletResponse response) {
        String dId = request.getParameter("dId");
        return subjectTreeService.getKcId(dId);
    }

    /**
     * 根据dId获取考试信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getExam.action", method = {RequestMethod.POST})
    @ResponseBody
    public Exams getExam(HttpServletRequest request, HttpServletResponse response) {
        String dId = request.getParameter("dId");
        System.out.println("  getExam.action   request = [" + request + "], response = [" + response + "]");
        return subjectTreeService.getExam(dId);
    }

    /**
     * 根据dId获取考试信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getQuestion.action", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> getQuestion(HttpServletRequest request, HttpServletResponse response) {
        String examId = request.getParameter("examId");
        return subjectTreeService.getQuestion(examId);
    }

    /**
     * 获取考核成绩
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getQueKey.action", method = {RequestMethod.POST})
    @ResponseBody
    public String getQueKey(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String examId = request.getParameter("examId");
        return subjectTreeService.getQueKey(examId);
    }

    /**
     * 获取考核成绩
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getKaohe.action", method = {RequestMethod.POST})
    @ResponseBody
    public StuQueInfors getKaohe(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String examId = request.getParameter("examId");
        String userId = session.getAttribute("ID").toString();
        return subjectTreeService.getkaohe(examId, userId);
    }

    /**
     * 老师获取学生考核成绩
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getScoreTeacher.action", method = {RequestMethod.POST})
    @ResponseBody
    public StuQueInfors getScoreTeacher(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String rId = request.getParameter("rId");
        String userId = request.getParameter("userId");
        return subjectTreeService.getStudentScore(rId, userId);
    }

    @RequestMapping("queryKnowledgeContents.action")
    @ResponseBody
    public JsonResult queryKnowledgeContents(String subjecttreeId) {

        List<Knowledges> list = subjectTreeService.queryKnowledgeContents(subjecttreeId);
        return new JsonResult(list);
    }

    @RequestMapping("loadAllKnowledges.action")
    @ResponseBody
    public JsonResult loadAllKnowledges(String treeId) {
        List<Knowledges> list = subjectTreeService.queryAllKnowledges(treeId);
        return new JsonResult(list);
    }


    /**
     * 润尼尔对接
     * 获取考核成绩
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getScoresRun.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean getScoresRun(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            request.setCharacterEncoding("utf-8");//设置post请求的编码问题.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //System.out.println(" request :"+request);//postData
        String score = request.getParameter("ScoreInfo");
        System.out.println(" score : " + score);
        String userId = "";
        String dId = "";
        int getscroe = 0;
        String totalscroe = "100";
        String reHtml = "";
        String[] arr = score.split("\\*\\$\\*");
        for (int i = 0; i < arr.length; i++) {
            if ((i + 1) % 5 == 1) {
                reHtml += "<tr>";
            }
            String[] str = arr[i].split(":");
            System.out.println(str[0]);
            System.out.println(str[1]);
            if (str[0].equals("UserID")) {
                userId = str[1];
            }
            if (str[0].equals("KnowledgeID")) {
                dId = str[1];
            }
            System.out.println(" userId :" + userId + " dId :" + dId);
            if (i < arr.length - 2) {
                reHtml += "<td>";
                reHtml += arr[i];
                reHtml += "</td>";
                String[] str3 = str[1].split("分");
                str3[0].trim();
                System.out.println(str3.length + " i :" + i + " str3[0] :" + str3[0]);
                getscroe = getscroe + Integer.valueOf(str3[0]);
            }
            if ((i + 1) % 5 == 1) {
                reHtml += "</tr>";
            }
        }
        String html = "<div style='width:100%;text-align:center;color:blue'>";
        html += "操作完成率：";
        html += getscroe;
        html += "</div><table style='border-left: 1px solid #DDDDDD;border-top: 1px solid #DDDDDD;'>";
        html += reHtml;
        html += "</table>";
        score = html;
        System.out.println(" getscroe :" + getscroe + " dId :" + dId + " userId :" + userId);
        userId = usersService.GetUidByuserName(userId);
        Integer getStuScore = subjectTreeService.getStuScore(dId, userId, dId);
        return subjectTreeService.getScores(score, getscroe, totalscroe, dId, dId, userId, getStuScore);
    }


    /**
     * 老师获取学生考核成绩
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getScoresForYXYStu.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<StutotalScoresForYXY> getScoresForYXYStu(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String rId = request.getParameter("rId");
        String userId = request.getParameter("userId");
        List<StutotalScoresForYXY> list =  subjectTreeService.getScoresForYXYStu(rId, userId);
        if(list!=null){
            return list;
        }
        return null ;
    }

    /**
     * 获取考核成绩
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getScoresForYXYByByDate.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<StutotalScoresForYXY> getScoresForYXYByByDate(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            request.setCharacterEncoding("utf-8");//设置post请求的编码问题.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String classKey = request.getParameter("classKey");
        String knoConentId = request.getParameter("knoConentId");
        String startDate = request.getParameter("startDate");
        String enddatetime = request.getParameter("enddatetime");
        List<StutotalScoresForYXY> list =  subjectTreeService.getScoresForYXYByByDate(classKey, knoConentId,startDate,enddatetime);
        if(list!=null){
            return list;
        }
        return null ;
    }
    /**
     * 获取考核成绩
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getScoresForYXYByStu.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<StutotalScoresForYXY> getScoresForYXYByStu(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            request.setCharacterEncoding("utf-8");//设置post请求的编码问题.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String userKey = request.getParameter("userKey");
        String subjectKey = request.getParameter("subjectKey");
        List<StutotalScoresForYXY> list =  subjectTreeService.getScoresForYXYByStu(userKey, subjectKey);
        if(list!=null){
            return list;
        }
        return null ;
    }

    /**
     * 获取考核成绩
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getScores.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean getScores(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        try {
            request.setCharacterEncoding("utf-8");//设置post请求的编码问题.
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String score = request.getParameter("ScoreInfo");
        System.out.println(" score : " + score);
        String userId = "";
        String dId = "";
        int getscroe = 0;
        String totalscroe = "100";

        String reHtml = "";

        String[] arr = score.split("\\*\\$\\*");
        for (int i = 0; i < arr.length; i++) {

            if ((i + 1) % 5 == 1) {
                reHtml += "<tr>";
            }

            String[] str = arr[i].split(":");
            System.out.println(str[0]);
            System.out.println(str[1]);
            if (str[0].equals("UserID")) {
                userId = str[1];
            }
            if (str[0].equals("KnowledgeID")) {
                dId = str[1];
            }
            System.out.println(" userId :" + userId + " dId :" + dId);
            if (i < arr.length - 2) {

                reHtml += "<td>";
                reHtml += arr[i];
                reHtml += "</td>";

                String[] str3 = str[1].split("分");
                str3[0].trim();
                System.out.println(str3.length + " i :" + i + " str3[0] :" + str3[0]);

                getscroe = getscroe + Integer.valueOf(str3[0]);


            }
            if ((i + 1) % 5 == 1) {
                reHtml += "</tr>";
            }
        }
        String html = "<div style='width:100%;text-align:center;color:blue'>";
        html += "操作完成率：";
        html += getscroe;
        html += "</div><table style='border-left: 1px solid #DDDDDD;border-top: 1px solid #DDDDDD;'>";
        html += reHtml;
        html += "</table>";
        score = html;
        System.out.println(" getscroe :" + getscroe + " dId :" + dId + " userId :" + userId);

        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endDate = df.format(d);

        return subjectTreeService.getScoresForYXY(score, getscroe, totalscroe, dId, dId, userId, endDate);
    }
//    /**
//     * 获取考核成绩
//     *
//     * @param request
//     * @param response
//     * @return
//     */
//    @RequestMapping(value = "getScores.action", method = {RequestMethod.POST})
//    @ResponseBody
//    public boolean getScores(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//        /*String score=request.getParameter("score");
//        System.out.println(" score : "+score);
//        int getscroe=Integer.parseInt(request.getParameter("getscroe"));
//        System.out.println(" getscroe : "+getscroe);
//        String totalscroe=request.getParameter("totalscroe");
//        System.out.println(" totalscroe : "+totalscroe);
//        String dId=request.getParameter("dId");
//        System.out.println(" dId : "+dId);
//        String questionKey=request.getParameter("questionKey");
//        System.out.println(" questionKey : "+questionKey);
//        String userId=session.getAttribute("ID").toString();
//        System.out.println(" userId : "+userId);*/
//
//        try {
//            request.setCharacterEncoding("utf-8");//设置post请求的编码问题.
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        //System.out.println(" request :"+request);//postData
//        String score = request.getParameter("ScoreInfo");
//        System.out.println(" score : " + score);
//        String userId = "";
//        String dId = "";
//        int getscroe = 0;
//        String totalscroe = "100";
//
//        String reHtml = "";
//
//        String[] arr = score.split("\\*\\$\\*");
//        for (int i = 0; i < arr.length; i++) {
//
//            if ((i + 1) % 5 == 1) {
//                reHtml += "<tr>";
//            }
//
//            String[] str = arr[i].split(":");
//            System.out.println(str[0]);
//            System.out.println(str[1]);
//            if (str[0].equals("UserID")) {
//                userId = str[1];
//            }
//            if (str[0].equals("KnowledgeID")) {
//                dId = str[1];
//            }
//            System.out.println(" userId :" + userId + " dId :" + dId);
//            if (i < arr.length - 2) {
//
//                reHtml += "<td>";
//                reHtml += arr[i];
//                reHtml += "</td>";
//
//                String[] str3 = str[1].split("分");
//                str3[0].trim();
//                System.out.println(str3.length + " i :" + i + " str3[0] :" + str3[0]);
//
//                getscroe = getscroe + Integer.valueOf(str3[0]);
//
//
//            }
//            if ((i + 1) % 5 == 1) {
//                reHtml += "</tr>";
//            }
//        }
//        String html = "<div style='width:100%;text-align:center;color:blue'>";
//        html += "操作完成率：";
//        html += getscroe;
//        html += "</div><table style='border-left: 1px solid #DDDDDD;border-top: 1px solid #DDDDDD;'>";
//        html += reHtml;
//        html += "</table>";
//        score = html;
//        System.out.println(" getscroe :" + getscroe + " dId :" + dId + " userId :" + userId);
//
//
//        //获取之前作答分数
//
//        /*
//         *   score--  QuestionAnswer
//         * getscroe-- QuesScore
//         * totalscroe-- ?
//         * dId-- PagerKey
//         * questionKey-- questionKey
//         * userId--  session
//         * */
//
//        /*
//         * select QuesScore from stuqueinfors where
//         * PagerKey=#{dId} and QuestionKey=#{questionKey}
//         * and StuKey=#{userId}
//         * */
//        //Integer getStuScore=subjectTreeService.getStuScore(dId,userId,questionKey);
//        Integer getStuScore = subjectTreeService.getStuScore(dId, userId, dId);
//        /*
//         * select * from stutotalscores where PagerKey=#{dId} and StuKey=#{userId}
//         * select * from stuqueinfors where PagerKey=#{dId} and QuestionKey=#{questionKey} and StuKey=#{userId}
//         *
//         * update stutotalscores set QuesScore=#{getscroe} where id=#{id}
//         *
//         * INSERT INTO stutotalscores(Id,PagerKey,QuesScore,Checked,StuKey,AllowExam)
//         * values(#{stuId},#{dId},#{getscroe},'0',#{userId},'1')
//         *
//         * update stuqueinfors set QuesScore=#{getscroe},QuestionAnswer=#{scroe} where id=#{id}
//         *
//         * INSERT into stuqueinfors(Id,PagerKey,QuesScore,QuestionAnswer,QuestionKey,StuKey) values
//         * (#{infosId},#{dId},#{getscroe},#{scroe},#{questionKey},#{userId})
//         *
//         * */
//        //return subjectTreeService.getScores(score, getscroe, totalscroe, dId, questionKey, userId,getStuScore);
//        return subjectTreeService.getScores(score, getscroe, totalscroe, dId, dId, userId, getStuScore);
//    }

}
