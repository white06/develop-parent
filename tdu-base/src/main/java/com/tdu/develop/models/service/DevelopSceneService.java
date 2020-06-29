package com.tdu.develop.models.service;

import com.tdu.develop.models.pojo.Scenecontents;
import com.tdu.develop.models.pojo.Scenes;
import com.tdu.develop.resource.pojo.Knowlegcontent;
import com.tdu.develop.util.FilModle;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-15-15:06
 */
public interface DevelopSceneService {
    public String getFirstSceneId(String rootId, String userId) ;

    /**
     *
     * 获取模型rootId的 subtreeid
     */
    public String getSubjectScenesRootId(String treeId);
    /**
     * 获取rootId
     * @param treeId
     * @return
     */
    public String gettScenesRootId(String treeId);





    /**
     * 通过模型Id获取contents内容
     */
    public Scenecontents getScenecontentsInfos(String id) ;
    public int delscenes(String id, String time) ;
    /**
     * 删除模型contents
     * @param id
     * @return
     */
    public int delsceneContets(String id, String time) ;
    /**
     * 获取模型信息
     * @param modelId
     * @return
     */
    public List<Scenes> getScenesscontents(String modelId, String userId);



    public boolean updateSceneName(String id, String name) ;



    public List<Scenes> getFirstScene(String subUpId, String rootId, String userId) ;



    public List<Scenes> getSubScene(String parentId, String subUpId, String userId) ;



    public void addSubScenes(String parentId, String content, String subUpId, String userId) ;



    public String getSubId(String subId, String treeName) ;



    public void upFirSceneTree(String id, String name) ;



    public void deleScene(String Id) ;



    public void addFisScene(String Id, String subId, String content, String userId) ;



    public List<Scenecontents> getScenecontents(String SceneId, String userId) ;



    public String getSubId1(String SceneId) ;

    /*
     * 新增主页模型
     * */

    public String AddScenesContent(Scenecontents Scenecontents, String treeId, String nodeId, Scenes scenes) ;

    /**
     * 添加最后一个节点
     * @param treeId
     * @param clickNodeId
     */
    public void addLastScenesNode(String treeId, String clickNodeId, String userId);

    /**
     * 设置父节点的id
     * @param clickNodeId 该点击节点的id
     * @param treeId	树id
     * @return 父节点id
     */
    public String parentScenesNodeId(String clickNodeId, String treeId);

    /**
     * 根据树id
     * 获取最后一个节点的id
     * @return
     */
    public String lastScenesNodeId(String treeId, String clickNodeId, String userId);

    //复制方法
    public  void copy(String src, String des, String id) throws Exception ;

    /**
     * 文件复制的具体方法
     */
    public  void fileCopy(String src, String des) throws Exception ;
    /**
     * 删除模型
     * @param treeNodeId
     * @param subjectId
     */
    public void removeScenes(String treeNodeId, String subjectId) ;
    /**
     * 判断该子类是内容还是目录
     * @param knowledgeId
     * @return "content" 内容
     * 			"directory" 目录
     */
    public String judgeScenesType(String knowledgeId);

    /**
     * 对内容的处理
     * @param knowledgeId  Id (Knowledges)
     * @param subjectId
     */
    public void deleteSceneFile(String knowledgeId, String subjectId);
    /**
     * 根据删除节点id查询下一节点id并
     * 修改下一节点的上一节点id
     * @param deletedNodeId 当前节点id
     */
    public void alterScenesNextPreNodeId(String deletedNodeId);
    /**
     * 对目录的处理
     * @param id 当前knowledges的Id
     * @param subjectId
     */
    public void deleteScenesDirectory(String id, String subjectId);
    /*
     * 删除模型分类
     * */
    public int delSceneContact(String id);
    /*
     * 知识树删除模型/场景
     * */
    public int delScenes2(String Id);


    public Scenes getScenesparentId(String id);

    public List<Scenes> getSceneByUsers(String name, String rootId, String userId);

    /**
     * 模糊搜索当前科目下所有学生场景
     * @param name
     * @param rootId
     * @return
     */
    public List<Scenes> getSceneByAllUsers(String name, String rootId);

    public Scenecontents getSceneContentName(String id);

    /**
     * 获取 Scenes 首节点信息
     * @return
     */
    public List<Scenes> getScenesFis(String subTreeId);

    /**
     * 获取所有人的场景
     * @param fId
     * @param subjectId
     * @return
     */
    public List<Scenes> getScenesSecondAll(String fId, String subjectId);
    /**
     * 获取次节点信息
     * @param parentKnowledge
     * @return
     */
    public List<Scenes> getScenesSecond(String parentKnowledge, String userId);

    public String getScenesType(String knowledgecontentId);
    public int delScenecontents(Scenecontents scenecontents);

    public int delScenes(Scenes scenes);

    /**
     * 根据父Id获取子节点
     */
    public List<Scenes> getParentScenes(String id) ;
   public List<Scenes> getScenesList(String id,String userId) ;

    /*
     * 新增主页场景(上传)
     * */
    public String AddScenesContent2(Scenecontents scenecontents, String treeId, String nodeId) ;

    /*
     * 修改场景的名字
     * */
    public int updateScenescontentCustomName(String id,String CustomName,String PhotoName) ;

    public String lastScenesNodeIdInAll(String treeId,String clickNodeId,String userId);

    public Boolean inknowScenes(Scenes knowledges) ;

    public String slRootScenes(String id);

    String AddScenesContentFile(Scenecontents scenecontents, String treeId1, String nodeId, Scenes scene, MultipartFile[] file);

    String AddScenesContentFileModel(Scenecontents scenecontents, String treeId1, String nodeId, Scenes scene, MultipartFile[] file, List<FilModle> models);

    List<Scenes> getContentScenes(String subjectId, String sarchStr);

    void updateScenesContentFile(String userkey, String knoContentId, MultipartFile[] file);


    Scenes getScenes(String sceneId);
}
