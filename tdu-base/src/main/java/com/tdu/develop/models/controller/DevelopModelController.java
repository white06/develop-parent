package com.tdu.develop.models.controller;

import com.tdu.develop.common.support.CharsetKit;
import com.tdu.develop.models.pojo.Modelcontents;
import com.tdu.develop.models.pojo.Models;
import com.tdu.develop.models.service.DevelopModelService;
import com.tdu.develop.models.service.impl.DevelopModelServiceImpl;
import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.Knowlegcontent;
import com.tdu.develop.resource.pojo.ZNodes;
import com.tdu.develop.util.Base64Util;
import com.tdu.develop.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 志阳
 * @create 2019-08-15-11:34
 */
@CrossOrigin
@Controller
@RequestMapping(value = "DevelopModelController")
public class DevelopModelController {
    @Autowired
    DevelopModelService developModelService = new DevelopModelServiceImpl();

    @Autowired
    DevelopModelServiceImpl developModelServiceimp = new DevelopModelServiceImpl();


    /**
     * 得到数据首节点信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getLink.action", method = {RequestMethod.POST})
    @ResponseBody
    public String getLink(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String modelId = request.getParameter("knowledgeId");
        modelId = Base64Util.decode(modelId);
        //  1-VR：TDuVREngine    2-编辑器 : TDuVRDirector  3-WEBGl :TDuSimEngine 4-仿真 :TDuSimEngine
        String mark = request.getParameter("mark");
        mark = Base64Util.decode(mark);
        //  type=1 练习   type=2 考核
        String type = request.getParameter("type");
        type = Base64Util.decode(type);

        Models models = developModelService.getModel(modelId);
        Modelcontents modelcontents = developModelService.getModelcontentsInfos(modelId);

        String userId = (String) session.getAttribute("ID");

        String link = getLink(type, mark, models, modelcontents);
        //link = link + "KnowledgeID=" + knowledges.getKnowledgecontentId() + "&OperateID=" + userId + "&";
        return link;
    }

    public String getLink(String type, String mark, Models models, Modelcontents modelcontents) {
        /*String str="tduvr://command=open&App=TDuSimEngine&" +
                "Scene=79c6c061-baae-46c7-8de2-ee28d37d5613/c86b27a8-3a34-b198-ab93-01664af51d29/c86b27a8-3a34-b198-ab93-01664af51d29.EXM&" +
                "UserID=79c6c061-baae-46c7-8de2-ee28d37d5613&" +
                "SceneOrModelID=c86b27a8-3a34-b198-ab93-01664af51d29&OpMode=TDuPractice&";*/
        String str = "tduvr://command=open&App=";
        if (mark.equals("1")) {
            str = str + "TDuVREngine&";
        } else if (mark.equals("2")) {
            str = str + "TDuVRDirector&";
        } else if (mark.equals("3")) {
            str = str + "TDuSimEngine&";
        } else if (mark.equals("4")) {
            str = str + "TDuSimEngine&";
        }
        str = str + "Scene=" + "" + models.getUserKey() + "/" + "" + models.getModelContentId() + "" + "/" + modelcontents.getNmae() + "" + "&";
        str = str + "UserID=" + "" + models.getUserKey() + "&" + "SceneOrModelID=" + "" + models.getModelContentId() + "&OpMode=";
        if (type.equals("1")) {
            str = str + "TDuPractice&";
        } else if (type.equals("2")) {
            str = str + "TDuTest&";
        }
        return str;
    }


    /**
     * 获取rootIDgetKnowledgesFis
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getModelsparentId.action", method = {RequestMethod.POST})
    @ResponseBody
    public Models getModelsparentId(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        Models Id = developModelServiceimp.getModelsparentId(id);
        return Id;
    }

    /**
     * 获取rootID
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getFirstModelId.action", method = {RequestMethod.POST})
    @ResponseBody
    public String getFirstModelId(HttpServletRequest request, HttpServletResponse response) {
        String rootId = request.getParameter("rootId");
        String userId = request.getParameter("userId");
        String id = developModelServiceimp.getFirstModelId(rootId, userId);
        return id;
    }

    /**
     * 获取rootID
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getSubjectModelsRootId.action", method = {RequestMethod.POST})
    @ResponseBody
    public HashMap<String, String> getSubjectModelsRootId(HttpServletRequest request, HttpServletResponse response) {
        String treeId = request.getParameter("subId");
        HashMap<String, String> map = new HashMap<String, String>();
        String id = developModelService.getSubjectModelsRootId(treeId);
        map.put("id", id);
        return map;
    }

    /**
     * 获取rootID
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "gettModelsRootId.action", method = {RequestMethod.POST})
    @ResponseBody
    public HashMap<String, String> gettModelsRootId(HttpServletRequest request, HttpServletResponse response) {
        String treeId = request.getParameter("subId");
        HashMap<String, String> map = new HashMap<String, String>();
        String id = developModelService.gettModelsRootId(treeId);
        map.put("id", id);
        return map;
    }

    /**
     * 更新模型名字
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "updateModelName.action")
    @ResponseBody
    public boolean updateModelName(HttpServletRequest request, HttpServletResponse response) {
        String modelId = request.getParameter("subModel_Id");
        String modelName = request.getParameter("MadelName");
        boolean flag = developModelService.updateModelName(modelId, modelName);
        return flag;
    }

    /**
     * 获取模型库树形集合
     */
    @RequestMapping(value = "getModelTree.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Object> getModelTree(HttpServletRequest request, HttpSession session, HttpServletResponse response) {

        String userId = session.getAttribute("ID").toString();

        String subId = request.getParameter("subId");
        String treeName = request.getParameter("treeName");
        String rootId = request.getParameter("rootId");
        String subUpId = developModelService.getSubId(subId, treeName);
        List<Object> resultList = new ArrayList<Object>();
        List<Models> fList = developModelService.getFirstModel(subUpId, rootId, userId);
        int k = 0;
        for (int i = 0; i < fList.size(); i++) {
            if (fList.get(i).getCheckDel() == 0) {
                int allCount = 0;
                HashMap<String, Object> resultMap = new HashMap<String, Object>();
                String index_id = fList.get(i).getId();
                List<Models> sList = new ArrayList<Models>();
                sList = developModelService.getSubModel(index_id, subUpId, userId);
                for (int j = 0; j < sList.size(); j++) {
                    sList.get(j).setName(sList.get(j).getContent());
                    allCount = allCount + sList.size();
                }
                resultMap.put("id", index_id);
                //resultMap.put("name", fList.get(i).getContent()+"("+allCount+")");
                resultMap.put("name", fList.get(i).getContent());
                resultMap.put("isParent", true);
                resultMap.put("children", sList);
                resultList.add(k, resultMap);
                k++;
            }

        }
        System.out.println("resultList  :" + resultList);
        return resultList;
    }

    /**
     * 添加次节点信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "addSubModelTree.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean addSubModelTree(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        String subId = request.getParameter("subId");
        String treeName = request.getParameter("treeName");
        String subUpId = developModelService.getSubId(subId, treeName);
        String fId = request.getParameter("fId");
        String subName = request.getParameter("subName");
        subName = java.net.URLDecoder.decode(subName, "utf-8");
        String userId = session.getAttribute("ID").toString();
        developModelService.addSubModels(fId, subName, subUpId, userId);
        return true;
    }

    /**
     * 更改首节点信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "upFirModelTree.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean upFirModelTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String ufId = request.getParameter("fId");
        String ufName = request.getParameter("subName");
        String subId = request.getParameter("subId");
        String treeName = request.getParameter("treeName");
        String subUpId = developModelService.getSubId(subId, treeName);
        developModelService.upFirModelTree(ufId, ufName);
        return true;
    }

    /**
     * 获取subid
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getSubId.action", method = {RequestMethod.POST})
    @ResponseBody
    public String getSubId(HttpServletRequest request, HttpServletResponse response) {
        String subId = request.getParameter("subId");
        String treeName = request.getParameter("treeName");
        return developModelService.getSubId(subId, treeName);
    }

    /**
     * 删除首节点
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "delFirModelTree.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean delFirModelTree(HttpServletRequest request, HttpSession session, HttpServletResponse response) {

        String fId = request.getParameter("fId");

        String userId = session.getAttribute("ID").toString();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String timeFormat = sdf.format(date);
        /* 1: 查询是否有子节点
         * 2：子节点是文件夹还是模型/场景
         * 3：   是模型就更改模型和contengt表;是文件夹就查找文件夹下的所有模型/场景并更改
         * */
        List<Models> list = developModelServiceimp.getModelscontents(fId, userId);

        Modelcontents contents = developModelServiceimp.getModelcontentsInfos(fId);
        if (list.size() == 0) {
            developModelServiceimp.delmodels(fId, timeFormat);

            //developModelServiceimp.delmodelContets(contents.getId(),timeFormat);
        } else {

            developModelServiceimp.delmodels(fId, timeFormat);

            //developModelServiceimp.delmodelContets(contents.getId(),timeFormat);

            for (Models models : list) {
                if (models.getModelContentId().equals("00000000-0000-0000-0000-000000000000")) {
                    List<Models> list2 = developModelServiceimp.getModelscontents(models.getId(), userId);
                    if (list2.size() == 0) {
                        developModelServiceimp.delmodels(models.getId(), timeFormat);
                        //developModelServiceimp.delmodelContets(models.getModelContentId(),timeFormat);
                    } else {
                        developModelServiceimp.delmodels(models.getId(), timeFormat);
                        //developModelServiceimp.delmodelContets(models.getModelContentId(),timeFormat);
                        for (Models models2 : list2) {
                            developModelServiceimp.delmodels(models2.getId(), timeFormat);
                            developModelServiceimp.delmodelContets(models2.getModelContentId(), timeFormat);
                        }
                    }
                } else {
                    developModelServiceimp.delmodels(models.getId(), timeFormat);
                    developModelServiceimp.delmodelContets(models.getModelContentId(), timeFormat);
                }
            }
        }


        //developModelService.deleModel(fId);
        return true;
    }

    /**
     * 添加首节点信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "addFirModelTree", method = {RequestMethod.POST})
    @ResponseBody
    public boolean addFirModelTree(HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        String firName = request.getParameter("firName");
        String subId = request.getParameter("subId");
        String treeName = request.getParameter("treeName");
        String subUpId = developModelService.getSubId(subId, treeName);
        String Id = UUID.randomUUID().toString();
        String userId = session.getAttribute("ID").toString();
        developModelService.addFisModel(Id, subUpId, firName, userId);
        return true;
    }

    @RequestMapping(value = "getModelInfos.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Modelcontents> getModelInfos(HttpServletRequest request, HttpSession session, HttpServletResponse response) {

        String userId = session.getAttribute("ID").toString();
        List<Modelcontents> modelInfosList = developModelService.getModelcontents(request.getParameter("subModelId"), userId);

        return modelInfosList;
    }

    /*
     * 新增模型
     */
    @RequestMapping(value = "AddModelsContent.action", method = {RequestMethod.POST})
    @ResponseBody
    public String AddModelsContent(HttpServletRequest request, HttpSession session,
                                   @RequestParam("treeId1") String treeId1, @RequestParam("KnowledgeId") String KnowledgeId,
                                   @RequestParam("name") String name, @RequestParam("cusstyle") String cusstyle) {
        String knowSubId = treeId1;
        treeId1 = developModelService.getSubId1(knowSubId);
        treeId1 = treeId1;
        Modelcontents modelcontents = new Modelcontents();

        modelcontents.setCustomName(name);
        modelcontents.setCustomStyle(cusstyle);
        String nodeId = KnowledgeId;

        Models model = new Models();
        // String id=request.getParameter("Id2");
        String userId = session.getAttribute("ID").toString();
        model.setUserKey(userId);
        modelcontents.setUserKey(userId);
        String knowledgesId = developModelService.AddModelsContent(modelcontents, treeId1, nodeId, model);
        return knowledgesId;
    }

    @RequestMapping(value = "test.action", method = {RequestMethod.POST})
    @ResponseBody
    public void test() {
        System.out.println(System.getProperty("file.encoding"));
        Charset cs = Charset.forName("utf-8");
        System.out.println(cs.defaultCharset());
        System.out.println(Charset.defaultCharset());
    }

    /*
     * 新增模型
     */
    @RequestMapping(value = "AddModelsContentFile.action", method = {RequestMethod.POST})
    @ResponseBody
    public String AddModelsContentFile(HttpServletRequest request, HttpSession session, @RequestParam("file") MultipartFile[] file,
                                       @RequestParam("treeId1") String treeId1, @RequestParam("KnowledgeId") String KnowledgeId,
                                       @RequestParam("name") String name, @RequestParam("cusstyle") String cusstyle) {
        String knowSubId = treeId1;
        //treeId1 = developModelService.getSubId1(knowSubId);
        treeId1 = treeId1;

        Modelcontents modelcontents = new Modelcontents();

        modelcontents.setCustomName(name);
        modelcontents.setCustomStyle(cusstyle);
        String nodeId = KnowledgeId;

        Models model = new Models();
        // String id=request.getParameter("Id2");
        String userId = session.getAttribute("ID").toString();
        model.setUserKey(userId);
        modelcontents.setUserKey(userId);

        System.out.println(Charset.defaultCharset());

        String knowledgesId = developModelService.AddModelsContentFile(modelcontents, treeId1, nodeId, model, file);
        return knowledgesId;
    }


    /*
     * 新增模型
     */
    @RequestMapping(value = "AddModelsContentZong.action", method = {RequestMethod.POST})
    @ResponseBody
    public String AddModelsContentZong(HttpServletRequest request, HttpSession session) {
        String knowSubId = request.getParameter("treeId");
        String treeId = developModelService.getSubId1(knowSubId);

        Modelcontents modelcontents = new Modelcontents();

        modelcontents.setCustomName(request.getParameter("name"));
        modelcontents.setCustomStyle(request.getParameter("cusstyle"));
        String nodeId = request.getParameter("treeId");

        Models model = new Models();
        // String id=request.getParameter("Id2");
        String userId = session.getAttribute("ID").toString();
        model.setUserKey(userId);
        modelcontents.setUserKey(userId);
        String knowledgesId = developModelService.AddModelsContent(modelcontents, treeId, nodeId, model);
        return knowledgesId;
    }


    /**
     * 删除模型
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "RemoveModel .action", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, String> RemoveModel(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        String treeNodeId = null;
        try {
            treeNodeId = request.getParameter("Id");
            String subjectId = request.getParameter("subjectId");
            developModelService.removeModels(treeNodeId, subjectId);

            /*删除模型分类*/
            //developModelService.delModelContact(treeNodeId);
            /*end*/
        } catch (Exception e) {
            e.printStackTrace();
            map.put("Value", "删除成功");
            return map;
        }
        /*删除模型*/
        developModelService.delModels2(treeNodeId);
        /*end*/
        map.put("Key", "删除成功");
        return map;
    }


    /**
     * 获取首页树数据
     *
     * @param request
     * @param response
     * @param session
     * @return
     */
    @RequestMapping(value = "getTreeInfos.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Object> getTreeInfos(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String subId = request.getParameter("id");
        String content = request.getParameter("content");
        String userId = session.getAttribute("ID").toString();
        List<Object> list = developModelService.getTreeInfos(subId, content, userId);
        //System.out.println("list  :"+list);
        return list;
    }

    /**
     * 得到Models数据首节点信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getModelsFis.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Models> getModelsFis(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String subTreeId = request.getParameter("subTreeId");
        //String userId=(String) session.getAttribute("ID");
        List<Models> fList = developModelService.getModelsFis(subTreeId);
        return fList;
    }

    /**
     * 得到第二节点信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getModelsSecondAll.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Models> getModelsSecondAll(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String fId = request.getParameter("KnowledgesFisId");
        String subjectId = request.getParameter("subjectId");
        List<Models> sList = developModelService.getModelsSecondAll(fId, subjectId);
        return sList;
    }

    /*
     * 获取模型内容表
     * */
    @RequestMapping("getModelsContentName.action")
    @ResponseBody
    public Modelcontents getModelsContentName(HttpServletRequest request) {
        String id = request.getParameter("id");
        Modelcontents modelcontents = developModelService.getModelContentName(id);
        return modelcontents;
    }

    /**
     * 获取模型内容表的TYPE
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getModelsType.action", method = {RequestMethod.POST})
    @ResponseBody
    public void getModelsType(HttpServletRequest request, HttpServletResponse response) {
        String knowledgecontentId = request.getParameter("knowledgecontentId");
        String type = developModelService.getModelsType(knowledgecontentId);
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.getWriter().print(type);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 得到第二节点信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getModelsSecond2.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Models> getModelsSecond2(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String fId = request.getParameter("KnowledgesFisId");
        String userId = request.getParameter("userId");
        List<Models> sList = developModelService.getModelsSecond(fId, userId);
        return sList;
    }

    @RequestMapping(value = "getModelByAllUsers.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Models> getModelByAllUsers(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String name = request.getParameter("name");
        String rootId = request.getParameter("rootId");

        List<Models> sList = developModelService.getModelByAllUsers(name, rootId);
        return sList;
    }

    /**
     * 判断是否是管理员
     *
     * @param request
     * @param response
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "panduanAdmin.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean panduanAdmin(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        boolean panduan = false;
        String userId = (String) session.getAttribute("ID");
        if (userId.equals("4d272f66-9dac-4b87-a2a1-22b6e5910779")) {
            panduan = true;
        }
        return panduan;
    }

    /*
     * 刪除模型 更改 checkdel  deltime 字段
     * */
    @RequestMapping(value = "delModelcontents.action", method = {RequestMethod.POST})
    @ResponseBody
    public int delModelcontents(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        String modelID = request.getParameter("Id");//session.getAttribute("ID").toString();
        Modelcontents modelcontents = new Modelcontents();
        modelcontents.setModel_Id(modelID);
        modelcontents.setCheckDel(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String timeFormat = sdf.format(date);
        modelcontents.setDelTime(timeFormat);
        Models model = new Models();
        model.setId(modelID);
        model.setCheckDel(1);
        model.setDelTime(timeFormat);
        int count = developModelService.delModelcontents(modelcontents);
        int count2 = developModelService.delModels(model);
        return (count + count2);
    }

    /**
     * 得到第二节点信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getModelsSecond.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Models> getModelsSecond(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String fId = request.getParameter("KnowledgesFisId");
        String userId = (String) session.getAttribute("ID");
        if (userId == null) {
            userId = request.getParameter("userId");
        }
        List<Models> sList = developModelService.getModelsSecond(fId, userId);
        return sList;
    }

    /**
     * getModelByTeamModels
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getModelByUsers.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Models> getModelByUsers(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String name = request.getParameter("name");
        String rootId = request.getParameter("rootId");
        String userId = (String) session.getAttribute("ID");

        List<Models> sList = developModelService.getModelByUsers(name, rootId, userId);
        return sList;
    }

    /*
     * 获取模型内容表value="getParentModels.action",method={RequestMethod.POST}
     * */
    @RequestMapping(value = "getParentModels.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Models> getParentModels(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        List<Models> list = developModelService.getParentModels(id);
        return list;
    }

//    /**
//     * 获取View導航
//     * @param request
//     * @param response
//     * @return
//     */
//    @RequestMapping(value="getModelContacts2.action",method={RequestMethod.POST})
//    @ResponseBody
//    public List<Modelcontents> getModelContacts2(HttpServletRequest request,HttpServletResponse response){
//        List<Modelcontents> List=developModelService.getModelContacts2();
//        System.out.println(List+"----");
//        return List;
//    }

//    /**
//     * 获取View list
//     * @param request
//     * @param response
//     * @return
//     */
//    @RequestMapping(value="getModelListMapper2.action",method={RequestMethod.POST})
//    @ResponseBody
//    public List<Models> getModelListMapper2(HttpSession session,HttpServletRequest request,HttpServletResponse response,String Id){
//        String userId=request.getParameter("userId");//session.getAttribute("ID").toString();
//        List<Models> List=developModelService.getModelListMapper2(Id,userId);
//        return List;
//    }

    /**
     * 获取模型list
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getModelsList.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Models> getModelsList(HttpSession session, HttpServletRequest request, HttpServletResponse response, String Id) {
        String userId = request.getParameter("userId");//session.getAttribute("ID").toString();
        List<Models> List = developModelService.getModelsList(Id, userId);
        for (int i = 0; i < List.size(); i++) {
            System.out.println("list  :" + List.get(i).getFileName());
        }
        return List;
    }

    /**
     * 获取模型list
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getModelListMapper.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Models> getModelListMapper(HttpSession session, HttpServletRequest request, HttpServletResponse response, String Id) {
        String userId = request.getParameter("userId");//session.getAttribute("ID").toString();
        List<Models> List = developModelService.getModelListMapper(Id, userId);
        return List;
    }


//    /**
//     * 编辑模型Or场景
//     * @param request
//     * @param response
//     * @return
//     */
//    @RequestMapping(value="updateModelOrView.action",method={RequestMethod.POST})
//    @ResponseBody
//    public String updateModel(HttpServletRequest request,HttpServletResponse response,HttpSession session){
//        Models model = new Models();
//        //String id=UUID.randomUUID().toString();
//        String userId=request.getParameter("userId");//session.getAttribute("ID").toString();
//        String Name=request.getParameter("Name");
//        String ContactKey=request.getParameter("modelOrView");
//        String type=request.getParameter("type");
//        model.setContactKey(ContactKey);
//        model.setId(request.getParameter("Id"));
//        model.setName(Name);
//        model.setType(type);
//        model.setUserKey(userId);
//        int count = developModelService.updateModel(model);
//        String str = "";
//        if(count==1) {
//            str="成功";
//        }else {
//            str="失败";
//        }
//        try {
//            System.out.println(type);
//            if(type.equals("模型")){
//                response.sendRedirect(request.getContextPath()+"/QZ/model/kaifazhe2.html?contactKey="+ContactKey+"&type=1"+"&userID="+userId);
//            }else if(type.equals("场景")){
//                response.sendRedirect(request.getContextPath()+"/QZ/model/kaifazhe2.html?contactKey="+ContactKey+"&type=0"+"&userID="+userId);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return str;
//    }

    /**
     * 获取单个模型Or场景
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getModel.action", method = {RequestMethod.POST})
    @ResponseBody
    public Models getModel(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String id = request.getParameter("id");
        Models model = developModelService.getModel(id);
        return model;
    }

    //主页模型目录的新增
    @RequestMapping(value = "AllinsModels.action", method = {RequestMethod.POST})
    @ResponseBody
    public ZNodes AllinsModels(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Models models = new Models();
        String userId = session.getAttribute("ID").toString();
        models.setUserKey(userId);
        String id = UUID.randomUUID().toString();
        String content = request.getParameter("Content");
        if (content == null || content == "") {
            content = "新建目录";
        }
        //科目树id
        String subjectTree_Id = request.getParameter("subid");
        String imageIcons = "../../../Source/imgicon/tag_orange.png";
        String knowledgecontentId = "00000000-0000-0000-0000-000000000000";
        String beforCondition = "<root><beforesee></beforesee><userkey></userkey><grades></grades></root>";
        //上一节点id
        //String preknowledge=request.getParameter("lei");
        String preknowledge = developModelService.lastModelsNodeIdInAll(subjectTree_Id, "", userId);
				/*if(preknowledge==null||"".equals(preknowledge))
					throw new ServiceException("新增失败");*/
        //添加子科目目录
        if (request.getParameter("id") != "") {
            //父节点id
            String parentKnowledge = request.getParameter("id");

            models.setId(id);
            models.setContent(content);
            models.setParentModel(parentKnowledge);
            if (!preknowledge.isEmpty()) {
                models.setPreModel(preknowledge);
            }
            models.setSubjectTree_Id(subjectTree_Id);
            models.setImageIcons(imageIcons);
            models.setModelContentId(knowledgecontentId);
            models.setBeforCondition(beforCondition);
            //向knowledge表新增数据
            Boolean i = developModelService.inknowModels(models);
            if (i == true) {
                ZNodes zNodes = new ZNodes();
                zNodes.setId(models.getId());
                zNodes.setpId(models.getParentModel());
                zNodes.setName(models.getContent());
                zNodes.setKnowledgecontentId(models.getModelContentId());
                return zNodes;
            }
        }
        //添加科目目录
        else if (request.getParameter("id") == "") {
            //获取ROOT的ID
            String parentKnowledge = developModelService.slRootModels(subjectTree_Id);
            models.setId(id);
            models.setContent(content);
            models.setParentModel(parentKnowledge);
            if (StringUtils.isNotEmpty(preknowledge) /*!preknowledge.isEmpty()*/) {
                models.setPreModel(preknowledge);
            }
            models.setSubjectTree_Id(subjectTree_Id);
            models.setImageIcons(imageIcons);
            models.setModelContentId(knowledgecontentId);
            models.setBeforCondition(beforCondition);


            Boolean i = developModelService.inknowModels(models);

            /*增加模型分类*/
					/*ModelContact modelContact =new ModelContact();
					modelContact.setId(id);
					modelContact.setName(content);
					modelContact.setParentId("0aec2588-4f01-4a94-b27a-fb2e20c8c85b");
					mlsi.addModelContact(modelContact);*/
            /*end*/

            if (i == true) {
                ZNodes zNodes = new ZNodes();
                zNodes.setId(models.getId());
                zNodes.setpId("0");
                zNodes.setName(models.getContent());
                zNodes.setKnowledgecontentId(models.getModelContentId());
                return zNodes;
            }
        }

        return null;
    }


    //主页模型目录的新增
    @RequestMapping(value = "insModels.action", method = {RequestMethod.POST})
    @ResponseBody
    public ZNodes insModels(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Models models = new Models();
        String userId = session.getAttribute("ID").toString();
        models.setUserKey(userId);
        String id = UUID.randomUUID().toString();
        String content = request.getParameter("Content");
        if (content == null || content == "") {
            content = "新建目录";
        }
        //科目树id
        String subjectTree_Id = request.getParameter("subid");
        String imageIcons = "../../../Source/imgicon/tag_orange.png";
        String knowledgecontentId = "00000000-0000-0000-0000-000000000000";
        String beforCondition = "<root><beforesee></beforesee><userkey></userkey><grades></grades></root>";
        //上一节点id
        String preknowledge = request.getParameter("lei");
			/*if(preknowledge==null||"".equals(preknowledge))
				throw new ServiceException("新增失败");*/
        //添加子科目目录
        if (request.getParameter("id") != "") {
            //父节点id
            String parentKnowledge = request.getParameter("id");

            models.setId(id);
            models.setContent(content);
            models.setParentModel(parentKnowledge);
            if (!preknowledge.isEmpty()) {
                models.setPreModel(preknowledge);
            }
            models.setSubjectTree_Id(subjectTree_Id);
            models.setImageIcons(imageIcons);
            models.setModelContentId(knowledgecontentId);
            models.setBeforCondition(beforCondition);
            //向knowledge表新增数据
            Boolean i = developModelService.inknowModels(models);
            if (i == true) {
                ZNodes zNodes = new ZNodes();
                zNodes.setId(models.getId());
                zNodes.setpId(models.getParentModel());
                zNodes.setName(models.getContent());
                zNodes.setKnowledgecontentId(models.getModelContentId());
                return zNodes;
            }
        }
        //添加科目目录
        else if (request.getParameter("id") == "") {
            //获取ROOT的ID
            String parentKnowledge = developModelService.slRootModels(subjectTree_Id);
            models.setId(id);
            models.setContent(content);
            models.setParentModel(parentKnowledge);
            if (!preknowledge.isEmpty()) {
                models.setPreModel(preknowledge);
            }
            models.setSubjectTree_Id(subjectTree_Id);
            models.setImageIcons(imageIcons);
            models.setModelContentId(knowledgecontentId);
            models.setBeforCondition(beforCondition);

            Boolean i = developModelService.inknowModels(models);

            if (i == true) {
                ZNodes zNodes = new ZNodes();
                zNodes.setId(models.getId());
                zNodes.setpId("0");
                zNodes.setName(models.getContent());
                zNodes.setKnowledgecontentId(models.getModelContentId());
                return zNodes;
            }
        }

        return null;
    }

}
