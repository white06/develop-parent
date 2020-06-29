package com.tdu.develop.models.mapper;

import com.tdu.develop.models.pojo.Scenecontents;
import com.tdu.develop.models.pojo.Scenes;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-15-15:12
 */
@Repository
public interface DevelopSceneMapper {

    public void AddNewScene(Scenes knowledges);

    public String getFirstSceneId(@Param("ParentScene") String ParentScene, @Param("userKey") String userKey);

//    /**
//     * 从  subjecttrees 表 获取rootId
//     * @param subjectTree_Id
//     * @return
//     */
//    public String getSubjectScenesRootId2(@Param("SubjectKey")String SubjectKey);

    /**
     * 删除模型(含文件夹)和contents
     * @param id
     * @return
     */
    public int delScenes(@Param("Id") String Id, @Param("DelTime") String DelTime);

    public int delSceneContets(@Param("Id") String Id, @Param("DelTime") String DelTime);


    /**
     * 更改模型表名称
     */
    public int updateSceneName(@Param("SceneContentId") String ModelContentId, @Param("Content") String Content);
    /**
     * 更改模型content表名称
     */
    public int updateSceneContentName(@Param("Id") String Id, @Param("CustomName") String CustomName);

    /**
     * 获取model首目录（写死）
     * @return
     */
    public List<Scenes> getFisScenes(@Param("subUpId") String subUpId, @Param("parentScene") String parentScene, @Param("userKey") String userKey);
    /**
     * 获取
     * @param parentId
     * @return
     */
    public List<Scenes> getSubScenes(@Param("parentId") String parentId, @Param("subUpId") String subUpId, @Param("userKey") String userKey);
    /**
     * 添加子节点
     * @param Id
     * @param content
     * @param parentModel
     */
    public void addSubScenes(@Param("Id") String Id, @Param("content") String content,
                             @Param("parentScene") String parentModel, @Param("preScene") String preScene,
                             @Param("subUpId") String subUpId, @Param("userKey") String userKey);
//    /**
//     * 获取subId
//     * @param subId
//     * @param treeName
//     * @return
//     */
//    public String getSubId(@Param("subId")String subId,@Param("treeName")String treeName);
    /**
     * 修改文件名
     * @param Id
     * @param content
     */
    public void upFirSceneTree(@Param("Id") String Id, @Param("content") String content);
    /**
     * 删除model
     * @param Id
     */
    public void deleteScene(@Param("Id") String Id);
    /**
     * 添加父节点id
     * @param Id
     * @param content
     * @param parentId
     * @param subId
     */
    public void addFisScenes(@Param("Id") String Id, @Param("content") String content, @Param("parentId") String parentId,
                             @Param("subId") String subId, @Param("userKey") String userKey);
    /**
     * 添加父节点id
     * @param Id
     * @param content
     * @param parentId
     * @param subId
     */
    public void addFisScenes2(@Param("Id") String Id, @Param("content") String content, @Param("parentId") String parentId, @Param("PreScene") String PreScene,
                              @Param("subId") String subId, @Param("userKey") String userKey);
    /**
     * 获取rootId
     * @param subId
     * @return
     */
    public String getRootId(@Param("subId") String subId);
    /**
     * 获取模型信息
     * @param modelId
     * @return
     */
    public List<Scenes> getScenecontents(@Param("sceneId") String modelId, @Param("userKey") String userKey);
    /**
     * 获取模型信息
     * @param modelId
     * @return
     */
    public Scenecontents getScenecontentsInfos(@Param("sceneId") String modelId);
    /**
     * 获取模型subId
     * @param modelId
     * @return
     */
    public String getSubId1(@Param("sceneId") String modelId);
    /**
     * 获取模型最大下标
     * @return
     */
    public int getScenesMaxOrder();

    /**
     * 添加模型
     * @param knowlegcontent
     */
    public void addScenesModel(Scenecontents knowlegcontent);
    /**
     * 用konwledegcontents表中的数据修改knowledges表中的
     * knowledgeContentId达到绑定数据的作用
     * @param id
     * @param knowledge_Id
     */
    public void alterSceneContent_Id(Scenecontents knowlegcontent);
    /**
     * 在末尾添加节点
     * @param knowledges
     */
    public void addLastScenesNode(Scenes models);
    /**
     * 获取rootId
     * @param subjectTree_Id
     * @return
     */
    public String getSubjectScenesRootId(@Param("subjectTree_Id") String subjectTree_Id);
    /**
     * 搜索modelNum
     * @param id
     * @param rootId
     * @return
     */
    public int seleScenesNum(@Param("id") String id, @Param("rootId") String rootId, @Param("userKey") String userKey);
    /**
     * 查询第一个模型信息
     * @param id
     * @param rootId
     * @return
     */
    public Scenes seleScenesFirst(@Param("treeId") String id, @Param("rootId") String rootId, @Param("userKey") String userKey);
    /**
     * 获取下一个NodeId
     * @param firstId
     * @return
     */
    public String getScenesNextNodeId(@Param("firstId") String firstId);

    /**
     * 获取该节点下所有的子类
     * @param treeNodeId
     * @return
     */
    public List<Scenes> getAllScenesclass(@Param("treeNodeId") String treeNodeId);
    /**
     * 根据knowledgeId获取数据
     * @param knowledgeId
     * @return
     */
    public Scenecontents getFileScenesContent(@Param("knowledgeId") String knowledgeId);
    /**
     * 根据id删除数据
     * @param sourceId
     */
    public void deleteSimulateScenes(@Param("sourceId") String sourceId);
    /**
     * 根据当前节点id获取上一节点id
     * @param currentNodeId
     * @return
     */
    public String getScenesPreNodeId(@Param("currentNodeId") String currentNodeId);
    /**
     * 修改节点的上一节点id
     * @param preId
     * @param nextId
     */
    public void alterScenesNextPreNodeId(@Param("preId") String preId, @Param("nextId") String nextId);
    /**
     * 根据Id删除数据
     * @param id
     */
    public int deleteScenesKnowledges(@Param("id") String id);
    /**
     * 根据id获取树id
     * @param knowledge_Id
     * @return
     */
    public String getScenesTreeId(@Param("knowledge_Id") String knowledge_Id);
    /**
     * 统计大节点下子节点数
     * @param id
     * @return
     */
    public int childScenesNum(@Param("id") String id);
    /**
     * 根据id查询目录点信息
     * @param knowId
     * @return
     */
    public Scenes seleScenesKnow(@Param("knowId") String knowId);
    /**
     * 删除
     * @param Id
     * @return
     */
    public int delSceneContact(@Param("Id") String Id);
    /**
     * 删除
     * @param Content
     * @return
     */
    public int delScenes2(@Param("Content") String Content);

    /**
     * 获取某一层数据根据parentId
     * @param parentId
     * @return
     */
    public List<Scenes> seleScenesForP(@Param("parentId") String parentId, @Param("userKey") String userKey);

    public Scenes getScenesparentId(String Id);

    public List<Scenes> getSceneByTeamUserScenes(@Param("Content") String Content, @Param("SubjectTree_Id") String SubjectTree_Id, @Param("userKey") String userKey);

    public List<Scenes> getSceneByAllUsers(@Param("Content") String Content, @Param("SubjectTree_Id") String SubjectTree_Id);

    public Scenecontents getSceneContentName(@Param("id") String id);

    /**
     * 获取Scenes首节点信息
     * @return
     */
    public List<Scenes> getScenesFis(@Param("subTreeId") String subTreeId);

    public List<Scenes> getScenesSecond(@Param("ParentScene") String ParentScene, @Param("userKey") String userKey);

    public String getScenesType(@Param("SceneContentId") String SceneContentId);

    public int delScenecontents(Scenecontents scenecontents);

    public int delScenes_1(Scenes scenes);

    public List<Scenes> getParentScenes(String Id);

//    /**
//     * 获取场景list
//     * @param id
//     * @return
//     */
    public List<Scenes> getScenesList(@Param("Id")String Id,@Param("userKey")String userKey);
    public int updateScenesContent(@Param("id")String knowlegcontentId,@Param("name")String name,@Param("PhotoName")String PhotoName);

    public int seleScenesNum2(@Param("id") String id,@Param("rootId") String rootId,@Param("userKey") String userKey);

    public Scenes seleScenesFirst2(@Param("treeId")String id,@Param("rootId")String rootId,@Param("userKey") String userKey);

    public void inknowScenes(Scenes knowledges);//添加功能
    public String seleRootScenes(String id);

    List<Scenes> getContentScenes(@Param("subjectId")String subjectId,@Param("sarchStr")String sarchStr);

    public String getSceneName(@Param("sceneId")String sceneId);
}
