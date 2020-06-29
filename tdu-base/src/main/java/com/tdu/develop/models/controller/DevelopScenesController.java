package com.tdu.develop.models.controller;

import com.alibaba.fastjson.JSONArray;
import com.tdu.develop.models.pojo.Modelcontents;
import com.tdu.develop.models.pojo.Models;
import com.tdu.develop.models.pojo.Scenecontents;
import com.tdu.develop.models.pojo.Scenes;
import com.tdu.develop.models.service.DevelopSceneService;
import com.tdu.develop.models.service.impl.DevelopSceneServiceImpl;
import com.tdu.develop.resource.pojo.ZNodes;
import com.tdu.develop.util.Base64Util;
import com.tdu.develop.util.FilModle;
import com.tdu.develop.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 志阳
 * @create 2019-08-16-14:12
 */
@CrossOrigin
@Controller
@RequestMapping(value="DevelopScenesController")
public class DevelopScenesController {

    @Autowired
    DevelopSceneService developSceneService=new DevelopSceneServiceImpl();


    /**
     * 得到数据首节点信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getLink.action", method={RequestMethod.POST})
    @ResponseBody
    public String getLink(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String sceneId=request.getParameter("knowledgeId");
        sceneId= Base64Util.decode(sceneId);
        //  1-VR：TDuVREngine    2-编辑器 : TDuVRDirector  3-WEBGl :TDuSimEngine 4-仿真 :TDuSimEngine
        String mark=request.getParameter("mark");
        mark= Base64Util.decode(mark);
        //  type=1 练习   type=2 考核
        String type=request.getParameter("type");
        type= Base64Util.decode(type);

        Scenes scenes = developSceneService.getScenes(sceneId);
        Scenecontents scenecontents= developSceneService.getScenecontentsInfos(sceneId);

        String userId=(String) session.getAttribute("ID");

        String link = getLink(type,mark,scenes,scenecontents);
        //link = link + "KnowledgeID=" + knowledges.getKnowledgecontentId() + "&OperateID=" + userId + "&";
        return link;
    }

    public  String getLink(String type,String mark, Scenes scenes,Scenecontents  scenecontents){
        /*String str="tduvr://command=open&App=TDuSimEngine&" +
                "Scene=79c6c061-baae-46c7-8de2-ee28d37d5613/c86b27a8-3a34-b198-ab93-01664af51d29/c86b27a8-3a34-b198-ab93-01664af51d29.EXM&" +
                "UserID=79c6c061-baae-46c7-8de2-ee28d37d5613&" +
                "SceneOrModelID=c86b27a8-3a34-b198-ab93-01664af51d29&OpMode=TDuPractice&";*/
        String str="tduvr://command=open&App=";
        if(mark.equals("1")){
            str=str+"TDuVREngine&";
        }else if(mark.equals("2")){
            str=str+"TDuVRDirector&";
        }else if(mark.equals("3")){
            str=str+"TDuSimEngine&";
        }else if(mark.equals("4")){
            str=str+"TDuSimEngine&";
        }
        str=str+"Scene="+""+scenes.getUserKey()+"/"+""+scenes.getSceneContentId()+""+"/"+scenecontents.getNmae()+""+"&";
        str=str+"UserID="+""+scenes.getUserKey()+"&"+"SceneOrModelID="+""+scenes.getSceneContentId()+"&OpMode=";
        if(type.equals("1")){
            str=str+"TDuPractice&";
        }else if(type.equals("2")){
            str=str+"TDuTest&";
        }
        if(mark.equals("3")){
            str="https://tdu.tduvr.club/TDuWebEngine/index.html?"+str;
        }
        return str;
    }


    @RequestMapping(value = "updateScenesContentFile.action", method = { RequestMethod.POST })
    @ResponseBody
    public void updateScenesContentFile(HttpServletRequest request, HttpSession session,@RequestParam("file") MultipartFile[] file,
                                       @RequestParam("userkey")String userkey,@RequestParam("knoContentId")String knoContentId) {

       developSceneService.updateScenesContentFile(userkey, knoContentId,file);
    }


    /**
     *getModelByTeamModels
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getSceneByUsers.action",method={RequestMethod.POST})
    @ResponseBody
    public List<Scenes> getSceneByUsers(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String name=request.getParameter("name");
        String rootId=request.getParameter("rootId");
        String userId=(String) session.getAttribute("ID");

        List<Scenes> sList=developSceneService.getSceneByUsers(name,rootId,userId);
        return sList;
    }

    @RequestMapping(value="getSceneByAllUsers.action",method={RequestMethod.POST})
    @ResponseBody
    public List<Scenes> getSceneByAllUsers(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String name=request.getParameter("name");
        String rootId=request.getParameter("rootId");

        List<Scenes> sList=developSceneService.getSceneByAllUsers(name,rootId);
        return sList;
    }

    /**
     * 获取rootID
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getSubjectScenesRootId.action",method={RequestMethod.POST})
    @ResponseBody
    public HashMap<String,String> getSubjectScenesRootId(HttpServletRequest request, HttpServletResponse response){
        String treeId=request.getParameter("subId");
        HashMap<String,String> map = new HashMap<String,String>();
        String id = developSceneService.getSubjectScenesRootId(treeId);
        map.put("id",id);
        return map;
    }
    /*
     * 获取场景内容表
     * */
    @RequestMapping("getScenesContentName.action")
    @ResponseBody
    public Scenecontents getScenesContentName(HttpServletRequest request){
        String id = request.getParameter("id");
        Scenecontents scenecontents = developSceneService.getSceneContentName(id);
        return scenecontents;
    }

    /**
     * 获取rootID
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="gettScenesRootId.action",method={RequestMethod.POST})
    @ResponseBody
    public HashMap<String,String> gettScenesRootId(HttpServletRequest request,HttpServletResponse response){
        String treeId=request.getParameter("subId");
        HashMap<String,String> map = new HashMap<String,String>();
        String id = developSceneService.gettScenesRootId(treeId);
        map.put("id",id);
        return map;
    }

    /**
     * 获取getFirstSceneId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getFirstSceneId.action",method={RequestMethod.POST})
    @ResponseBody
    public String getFirstSceneId(HttpServletRequest request,HttpServletResponse response){
        String rootId=request.getParameter("rootId");
        String userId=request.getParameter("userId");
        String id = developSceneService.getFirstSceneId(rootId,userId);
        return id;
    }

    /**
     * 得到Scenes数据首节点信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getScenesFis.action", method={RequestMethod.POST})
    @ResponseBody
    public List<Scenes> getScenesFis(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String subTreeId=request.getParameter("subTreeId");
        /*String userId=(String) session.getAttribute("ID");*/
        List<Scenes> fList=developSceneService.getScenesFis(subTreeId);
        return fList;
    }

    /**
     * 得到第二节点信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getScenesSecondAll.action",method={RequestMethod.POST})
    @ResponseBody
    public List<Scenes> getScenesSecondAll(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String fId=request.getParameter("KnowledgesFisId");
        String subjectId=request.getParameter("subjectId");
        List<Scenes> sList=developSceneService.getScenesSecondAll(fId,subjectId);
        return sList;
    }
    /**
     * 得到第二节点信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getScenesSecond.action",method={RequestMethod.POST})
    @ResponseBody
    public List<Scenes> getScenesSecond(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String fId=request.getParameter("KnowledgesFisId");
        String userId=(String) session.getAttribute("ID");
        if(userId==null){
            userId=request.getParameter("userId");
        }
        List<Scenes> sList=developSceneService.getScenesSecond(fId,userId);
        return sList;
    }

    @RequestMapping(value="getScenesType.action",method={RequestMethod.POST})
    @ResponseBody
    public void getScenesType(HttpServletRequest request,HttpServletResponse response){
        String knowledgecontentId=request.getParameter("knowledgecontentId");
        String type=developSceneService.getScenesType(knowledgecontentId);
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
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getScenesSecond2.action",method={RequestMethod.POST})
    @ResponseBody
    public List<Scenes> getScenesSecond2(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String fId=request.getParameter("KnowledgesFisId");
        String userId=request.getParameter("userId");//(String) session.getAttribute("ID");
        List<Scenes> sList=developSceneService.getScenesSecond(fId,userId);
        return sList;
    }

    @RequestMapping(value="delScenecontents.action",method={RequestMethod.POST})
    @ResponseBody
    public int delScenecontents(HttpSession session,HttpServletRequest request,HttpServletResponse response){
        String modelID=request.getParameter("Id");//session.getAttribute("ID").toString();
        Scenecontents scenecontents = new Scenecontents();
        scenecontents.setScene_Id(modelID);
        scenecontents.setCheckDel(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String timeFormat = sdf.format(date);
        scenecontents.setDelTime(timeFormat);
        Scenes scenes = new Scenes();
        scenes.setId(modelID);
        scenes.setCheckDel(1);
        scenes.setDelTime(timeFormat);

        int count =developSceneService.delScenecontents(scenecontents);
        int count2 =developSceneService.delScenes(scenes);
        return (count+count2);
    }
    @RequestMapping(value="getScenesparentId.action",method={RequestMethod.POST})
    @ResponseBody
    public Scenes getScenesparentId(HttpServletRequest request, HttpServletResponse response){
        String id=request.getParameter("id");
        Scenes Id = developSceneService.getScenesparentId(id);
        return Id;
    }

    /**
     * 更新模型名字
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="updateSceneName.action")
    @ResponseBody
    public boolean updateSceneName(HttpServletRequest request,HttpServletResponse response){
        String SceneId=request.getParameter("subModel_Id");
        String SceneName=request.getParameter("MadelName");
        boolean flag = developSceneService.updateSceneName(SceneId,SceneName);
        return flag;
    }
    /**
     * 获取模型库树形集合
     */
    @RequestMapping(value="getSceneTree.action",method={RequestMethod.POST})
    @ResponseBody
    public List<Object> getSceneTree(HttpServletRequest request, HttpSession session,HttpServletResponse response){

        String userId = session.getAttribute("ID").toString();

        String subId=request.getParameter("subId");
        String treeName=request.getParameter("treeName");
        String rootId=request.getParameter("rootId");
        String subUpId=developSceneService.getSubId(subId, treeName);
        List<Object> resultList=new ArrayList<Object>();
        List<Scenes> fList=developSceneService.getFirstScene(subUpId,rootId,userId);
        for(int i=0;i<fList.size();i++){
            int allCount=0;
            HashMap<String, Object> resultMap= new HashMap<String, Object>();
            String index_id=fList.get(i).getId();
            List<Scenes> sList=new ArrayList<Scenes>();
            sList=developSceneService.getSubScene(index_id,subUpId,userId);
            for(int j=0;j<sList.size();j++){
                sList.get(j).setName(sList.get(j).getContent());
                allCount=allCount+sList.size();
            }
            resultMap.put("id", index_id);
            //resultMap.put("name", fList.get(i).getContent()+"("+allCount+")");
            resultMap.put("name", fList.get(i).getContent());
            resultMap.put("isParent", true);
            resultMap.put("children", sList);
            resultList.add(i, resultMap);
        }
        System.out.println("resultList  :"+resultList);
        return resultList;
    }

    /**
     * 添加次节点信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="addSubSceneTree.action",method={RequestMethod.POST})
    @ResponseBody
    public boolean addSubSceneTree(HttpServletRequest request, HttpSession session,HttpServletResponse response) throws Exception{
        String subId=request.getParameter("subId");
        String treeName=request.getParameter("treeName");
        String subUpId=developSceneService.getSubId(subId, treeName);
        String fId=request.getParameter("fId");
        String subName=request.getParameter("subName");
        subName=java.net.URLDecoder.decode(subName,"utf-8");
        String userId = session.getAttribute("ID").toString();
        developSceneService.addSubScenes(fId, subName,subUpId,userId);
        return true;
    }

    /**
     * 更改首节点信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="upFirSceneTree.action",method={RequestMethod.POST})
    @ResponseBody
    public boolean upFirSceneTree(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String ufId=request.getParameter("fId");
        String ufName=request.getParameter("subName");
        String subId=request.getParameter("subId");
        String treeName=request.getParameter("treeName");
        String subUpId=developSceneService.getSubId(subId, treeName);
        developSceneService.upFirSceneTree(ufId, ufName);
        return true;
    }
    /**
     * 获取subid
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getSubId.action",method={RequestMethod.POST})
    @ResponseBody
    public String getSubId(HttpServletRequest request,HttpServletResponse response){
        String subId=request.getParameter("subId");
        String treeName=request.getParameter("treeName");
        return developSceneService.getSubId(subId, treeName);
    }

    /**
     * 删除首节点
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "delFirSceneTree.action", method = { RequestMethod.POST })
    @ResponseBody
    public boolean delFirSceneTree(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        String fId = request.getParameter("fId");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String timeFormat = sdf.format(date);

        String userId = session.getAttribute("ID").toString();

        /*
         * 1: 查询是否有子节点 2：子节点是文件夹还是模型/场景 3： 是模型就更改模型和contengt表;是文件夹就查找文件夹下的所有模型/场景并更改
         */
        List<Scenes> list = developSceneService.getScenesscontents(fId,userId);

        Scenecontents contents = developSceneService.getScenecontentsInfos(fId);
        if (list.size() == 0) {
            developSceneService.delscenes(fId, timeFormat);

            // developModelServiceimp.delmodelContets(contents.getId(),timeFormat);
        } else {

            developSceneService.delscenes(fId, timeFormat);

            // developModelServiceimp.delmodelContets(contents.getId(),timeFormat);

            for (Scenes models : list) {
                if (models.getSceneContentId().equals("00000000-0000-0000-0000-000000000000")) {
                    List<Scenes> list2 = developSceneService.getScenesscontents(models.getId(),userId);
                    if (list2.size() == 0) {
                        developSceneService.delscenes(models.getId(), timeFormat);
                        // developModelServiceimp.delmodelContets(models.getModelContentId(),timeFormat);
                    } else {
                        developSceneService.delscenes(models.getId(), timeFormat);
                        // developModelServiceimp.delmodelContets(models.getModelContentId(),timeFormat);
                        for (Scenes models2 : list2) {
                            developSceneService.delscenes(models2.getId(), timeFormat);
                            developSceneService.delsceneContets(models2.getSceneContentId(), timeFormat);
                        }
                    }
                } else {
                    developSceneService.delscenes(models.getId(), timeFormat);
                    developSceneService.delsceneContets(models.getSceneContentId(), timeFormat);
                }
            }
        }

        // developSceneService.deleScene(fId);
        return true;
    }

    /**
     * 添加首节点信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="addFirSceneTree",method={RequestMethod.POST})
    @ResponseBody
    public boolean addFirSceneTree(HttpServletRequest request, HttpSession session,HttpServletResponse response) throws Exception{
        String firName=request.getParameter("firName");
        String subId=request.getParameter("subId");
        String treeName=request.getParameter("treeName");
        String subUpId=developSceneService.getSubId(subId, treeName);
        String Id=UUID.randomUUID().toString();
        String userId = session.getAttribute("ID").toString();
        developSceneService.addFisScene(Id, subUpId, firName,userId);
        return true;
    }

    @RequestMapping(value="getSceneInfos.action",method={RequestMethod.POST})
    @ResponseBody
    public List<Scenecontents> getSceneInfos(HttpServletRequest request, HttpSession session, HttpServletResponse response){

        String userId = session.getAttribute("ID").toString();

        List<Scenecontents> SceneInfosList=developSceneService.getScenecontents(request.getParameter("subSceneId"),userId);

        return SceneInfosList;
    }

    /*
     * 新增模型
     */
    @RequestMapping(value = "AddScenesContent.action", method = { RequestMethod.POST })
    @ResponseBody
    public String AddScenesContent(HttpServletRequest request, HttpSession session) {
        String knowSubId = request.getParameter("treeId");
        String treeId = developSceneService.getSubId1(knowSubId);
        treeId= request.getParameter("treeId");
        Scenecontents Scenecontents = new Scenecontents();

        Scenecontents.setCustomName(request.getParameter("name"));
        Scenecontents.setCustomStyle(request.getParameter("cusstyle"));
        String nodeId = request.getParameter("KnowledgeId");

        Scenes scene = new Scenes();
        // String id=request.getParameter("Id2");
        String userId = session.getAttribute("ID").toString();
        scene.setUserKey(userId);
        Scenecontents.setUserKey(userId);
        String knowledgesId = developSceneService.AddScenesContent(Scenecontents, treeId, nodeId, scene);
        return knowledgesId;
    }
    /*
     * 新增模型
     */
    @RequestMapping(value = "AddScenesContentFile.action", method = { RequestMethod.POST })
    @ResponseBody
    public String AddScenesContentFile(@RequestParam("model")String model,HttpServletRequest request, HttpSession session,@RequestParam("file") MultipartFile[] file,
                                       @RequestParam("treeId1")String treeId1,@RequestParam("KnowledgeId")String KnowledgeId,
                                       @RequestParam("name")String name,@RequestParam("cusstyle")String cusstyle) {
        String knowSubId = treeId1;
        //String treeId = developSceneService.getSubId1(knowSubId);
        treeId1= treeId1;
        Scenecontents Scenecontents = new Scenecontents();
        System.out.println(model);
        List<FilModle> models =  jsonToList(model,FilModle.class);
        for (FilModle stu : models) {
            System.out.println(stu);
        }

        Scenecontents.setCustomName(name);
        Scenecontents.setCustomStyle(cusstyle);
        String nodeId = KnowledgeId;

        Scenes scene = new Scenes();
        // String id=request.getParameter("Id2");
        String userId = session.getAttribute("ID").toString();
        scene.setUserKey(userId);
        Scenecontents.setUserKey(userId);
        String knowledgesId = developSceneService.AddScenesContentFileModel(Scenecontents, treeId1, nodeId, scene,file,models);
        return knowledgesId;//knowledgesId;
    }
    /**
     * json 转 List<T>
     */
    public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
        @SuppressWarnings("unchecked")
        List<T> ts = (List<T>) JSONArray.parseArray(jsonString, clazz);
        return ts;
    }
    /*
	 * 新增模型
	 */
    @RequestMapping(value = "AddScenesContentZong.action", method = { RequestMethod.POST })
    @ResponseBody
    public String AddScenesContentZong(HttpServletRequest request, HttpSession session) {
        String knowSubId = request.getParameter("treeId");
        String treeId = developSceneService.getSubId1(knowSubId);

        Scenecontents Scenecontents = new Scenecontents();

        Scenecontents.setCustomName(request.getParameter("name"));
        Scenecontents.setCustomStyle(request.getParameter("cusstyle"));
        String nodeId = request.getParameter("treeId");

        Scenes Scene = new Scenes();
        // String id=request.getParameter("Id2");
        String userId = session.getAttribute("ID").toString();
        Scene.setUserKey(userId);
        Scenecontents.setUserKey(userId);
        String knowledgesId = developSceneService.AddScenesContent(Scenecontents, treeId, nodeId, Scene);
        return knowledgesId;
    }

    /**
     * 删除模型
     * @param request
     * @return
     */
    @RequestMapping(value="RemoveScene .action",method={RequestMethod.POST})
    @ResponseBody
    public Map<String,String> RemoveScene(HttpServletRequest request){
        Map<String,String> map = new HashMap<String,String>();
        String treeNodeId = null ;
        try{
            treeNodeId= request.getParameter("Id");
            String subjectId = request.getParameter("subjectId");
            developSceneService.removeScenes(treeNodeId,subjectId);

            /*删除模型分类*/
            developSceneService.delSceneContact(treeNodeId);
            /*end*/
        }catch(Exception e){
            e.printStackTrace();
            map.put("Value","删除成功");
            return map;
        }
        /*删除模型*/
        developSceneService.delScenes2(treeNodeId);
        /*end*/
        map.put("Key", "删除成功");
        return map;
    }

    /*
     * 获取场景内容表
     * */
    @RequestMapping("getParentScenes.action")
    @ResponseBody
    public List<Scenes> getParentScenes(HttpServletRequest request){
        String id = request.getParameter("id");
        List<Scenes> list = developSceneService.getParentScenes(id);
        return list;
    }

    /**
     * 获取场景list
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getScenesList.action",method={RequestMethod.POST})
    @ResponseBody
    public List<Scenes> getScenesList(HttpSession session,HttpServletRequest request,HttpServletResponse response,String Id){
        String userId=request.getParameter("userId");//session.getAttribute("ID").toString();
        List<Scenes> List=developSceneService.getScenesList(Id,userId);
        return List;
    }

    //主页场景目录的新增
    @RequestMapping(value="AllinsScenes.action",method={RequestMethod.POST})
    @ResponseBody
    public ZNodes AllinsScenes(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        Scenes scenes=new Scenes();
        String userId = session.getAttribute("ID").toString();
        scenes.setUserKey(userId);
        String id=UUID.randomUUID().toString();
        String content= request.getParameter("Content");
        if(content==null||content=="") {
            content="新建目录";
        }
        //科目树id
        String subjectTree_Id=request.getParameter("subid");
        String imageIcons="../../../Source/imgicon/tag_orange.png";
        String knowledgecontentId="00000000-0000-0000-0000-000000000000";
        String beforCondition="<root><beforesee></beforesee><userkey></userkey><grades></grades></root>";
        //上一节点id
        String preknowledge=developSceneService.lastScenesNodeIdInAll(subjectTree_Id,"",userId);//request.getParameter("lei");
				/*if(preknowledge==null||"".equals(preknowledge))
					throw new ServiceException("新增失败");*/
        //添加子科目目录
        if (request.getParameter("id")!="") {
            //父节点id
            String parentKnowledge=request.getParameter("id");

            scenes.setId(id);
            scenes.setContent(content);
            scenes.setParentScene(parentKnowledge);
            if (!preknowledge.isEmpty()) {
                scenes.setPreScene(preknowledge);
            }
            scenes.setSubjectTree_Id(subjectTree_Id);
            scenes.setImageIcons(imageIcons);
            scenes.setSceneContentId(knowledgecontentId);
            scenes.setBeforCondition(beforCondition);
            //向knowledge表新增数据
            Boolean i=developSceneService.inknowScenes(scenes);
            if (i==true) {
                ZNodes zNodes=new ZNodes();
                zNodes.setId(scenes.getId());
                zNodes.setpId(scenes.getParentScene());
                zNodes.setName(scenes.getContent());
                zNodes.setKnowledgecontentId(scenes.getSceneContentId());
                return zNodes;
            }
        }
        //添加科目目录
        else if (request.getParameter("id")=="") {
            //获取ROOT的ID
            String parentKnowledge=developSceneService.slRootScenes(subjectTree_Id);
            scenes.setId(id);
            scenes.setContent(content);
            scenes.setParentScene(parentKnowledge);
            /*if (!preknowledge.isEmpty()) {
                scenes.setPreScene(preknowledge);
            }*/
            if (StringUtils.isNotEmpty(parentKnowledge)) {
                scenes.setPreScene(preknowledge);
            }
            scenes.setSubjectTree_Id(subjectTree_Id);
            scenes.setImageIcons(imageIcons);
            scenes.setSceneContentId(knowledgecontentId);
            scenes.setBeforCondition(beforCondition);

            Boolean i=developSceneService.inknowScenes(scenes);
//
//            /*增加模型分类*/
//            Modelcontents modelContact =new Modelcontents();
//            modelContact.setId(id);
//            modelContact.setName(content);
//            modelContact.setParentId("7fb53e8f-baf1-4c1e-8868-bad634e81461");
//            mlsi.addModelContact(modelContact);
            /*end*/

            if (i==true) {
                ZNodes zNodes=new ZNodes();
                zNodes.setId(scenes.getId());
                zNodes.setpId("0");
                zNodes.setName(scenes.getContent());
                zNodes.setKnowledgecontentId(scenes.getSceneContentId());
                return zNodes;
            }
        }

        return null;
    }

    	//主页场景目录的新增
			@RequestMapping(value="insScenes.action",method={RequestMethod.POST})
			@ResponseBody
			public ZNodes insScenes(HttpServletRequest request,HttpServletResponse response,HttpSession session){
				Scenes scenes=new Scenes();
				String userId = session.getAttribute("ID").toString();
				scenes.setUserKey(userId);
				String id=UUID.randomUUID().toString();
				String content= request.getParameter("Content");
				if(content==null||content=="") {
					content="新建目录";
				}
				//科目树id
				String subjectTree_Id=request.getParameter("subid");
				String imageIcons="../../../Source/imgicon/tag_orange.png";
				String knowledgecontentId="00000000-0000-0000-0000-000000000000";
				String beforCondition="<root><beforesee></beforesee><userkey></userkey><grades></grades></root>";
				//上一节点id
				String preknowledge=request.getParameter("lei");
				/*if(preknowledge==null||"".equals(preknowledge))
					throw new ServiceException("新增失败");*/
				//添加子科目目录
				if (request.getParameter("id")!="") {
					//父节点id
					String parentKnowledge=request.getParameter("id");

					scenes.setId(id);
					scenes.setContent(content);
					scenes.setParentScene(parentKnowledge);
					if (!preknowledge.isEmpty()) {
						scenes.setPreScene(preknowledge);
					}
					scenes.setSubjectTree_Id(subjectTree_Id);
					scenes.setImageIcons(imageIcons);
					scenes.setSceneContentId(knowledgecontentId);
					scenes.setBeforCondition(beforCondition);
					//向knowledge表新增数据
					Boolean i=developSceneService.inknowScenes(scenes);
						if (i==true) {
							ZNodes zNodes=new ZNodes();
							zNodes.setId(scenes.getId());
							zNodes.setpId(scenes.getParentScene());
							zNodes.setName(scenes.getContent());
							zNodes.setKnowledgecontentId(scenes.getSceneContentId());
							return zNodes;
						}
				}
				//添加科目目录
				else if (request.getParameter("id")=="") {
					//获取ROOT的ID
					String parentKnowledge=developSceneService.slRootScenes(subjectTree_Id);
					scenes.setId(id);
					scenes.setContent(content);
					scenes.setParentScene(parentKnowledge);
					if (!preknowledge.isEmpty()) {
						scenes.setPreScene(preknowledge);
					}
					scenes.setSubjectTree_Id(subjectTree_Id);
					scenes.setImageIcons(imageIcons);
					scenes.setSceneContentId(knowledgecontentId);
					scenes.setBeforCondition(beforCondition);

					Boolean i=developSceneService.inknowScenes(scenes);


					if (i==true) {
						ZNodes zNodes=new ZNodes();
						zNodes.setId(scenes.getId());
						zNodes.setpId("0");
						zNodes.setName(scenes.getContent());
						zNodes.setKnowledgecontentId(scenes.getSceneContentId());
						return zNodes;
					}
				}

					return null;
			}

}
