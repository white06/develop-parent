package com.tdu.develop.models.service;

import com.tdu.develop.models.pojo.Modelcontents;
import com.tdu.develop.models.pojo.Models;
import com.tdu.develop.models.pojo.Scenecontents;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author 志阳
 * @create 2019-08-15-11:37
 */
public interface DevelopModelService {


    public String getFirstModelId(String rootId, String userId) ;

    /**
     *
     * 获取模型rootId的 subtreeid
     */
    public String getSubjectModelsRootId(String treeId);
    /**
     * 获取rootId
     * @param treeId
     * @return
     */
    public String gettModelsRootId(String treeId);

    /**
     * knowledgecontents的type属性
     */
    public static final String simulateType="仿真";

    /**
     * 通过模型Id获取contents内容
     */
    public Modelcontents getModelcontentsInfos(String id) ;

    /**
     * 删除模型(含文件夹)
     * @param id
     * @return
     */
    public int delmodels(String id, String time) ;
    /**
     * 删除模型contents
     * @param id
     * @return
     */
    public int delmodelContets(String id, String time) ;
    /**
     * 获取模型信息
     * @param modelId
     * @return
     */
    public List<Models> getModelscontents(String modelId, String userId);

    public boolean updateModelName(String id, String name) ;


    public List<Models> getFirstModel(String subUpId, String rootId, String userId) ;


    public List<Models> getSubModel(String parentId, String subUpId, String userId) ;



    public void addSubModels(String parentId, String content, String subUpId, String userId) ;



    public String getSubId(String subId, String treeName) ;



    public void upFirModelTree(String id, String name) ;



    public void deleModel(String Id) ;



    public void addFisModel(String Id, String subId, String content, String userId) ;


    public List<Modelcontents> getModelcontents(String modelId, String userId) ;



    public String getSubId1(String modelId) ;

    /*
     * 新增主页模型
     * */

    public String AddModelsContent(Modelcontents modelcontents, String treeId, String nodeId, Models model) ;

    /**
     * 添加最后一个节点
     * @param treeId
     * @param clickNodeId
     */
    public void addLastModelsNode(String treeId, String clickNodeId, String userId);

    /**
     * 设置父节点的id
     * @param clickNodeId 该点击节点的id
     * @param treeId	树id
     * @return 父节点id
     */
    public String parentModelsNodeId(String clickNodeId, String treeId);

    /**
     * 根据树id
     * 获取最后一个节点的id
     * @return
     */
    public String lastModelsNodeId(String treeId, String clickNodeId,String userId);

    //复制方法
    public  void copy(String src, String des, String id) throws Exception ;

    /**
     * 文件复制的具体方法
     */
    void fileCopy(String src, String des) throws Exception ;
    /**
     * 删除模型
     * @param treeNodeId
     * @param subjectId
     */
    public void removeModels(String treeNodeId, String subjectId) ;
/**
 * 判断该子类是内容还是目录
 * @param knowledgeId
 * @return "content" 内容
 * 			"directory" 目录
 */
public String judgeModelsType(String knowledgeId);

/**
 * 对内容的处理
 * @param knowledgeId  Id (Knowledges)
 * @param subjectId
 */
public void deleteModelFile(String knowledgeId, String subjectId);
/**
 * 根据删除节点id查询下一节点id并
 * 修改下一节点的上一节点id
 * @param deletedNodeId 当前节点id
 */
public void alterModelsNextPreNodeId(String deletedNodeId);
/**
 * 对目录的处理
 * @param id 当前knowledges的Id
 * @param subjectId
 */
public void deleteModelsDirectory(String id, String subjectId);
/*
 * 删除模型分类
 * */
public int delModelContact(String id);
/*
 * 知识树删除模型/场景
 * */
public int delModels2(String Id);


public List<Object> getTreeInfos(String subId, String content, String userId) ;

public List<Object> getTreeInfosAdmin(String subId, String content, String userId, String rootId);

public List<Object> seleScenesForP(String parentId, String userId) ;


public List<Object> seleModelsForP(String parentId, String userId) ;

public Models getModelsparentId(String id);

    public List<Models> getModelsFis(String subTreeId);
    public List<Models> getModelsSecondAll(String parentKnowledge, String subjectId);
    Modelcontents getModelContentName(String id);

    public String getModelsType(String knowledgecontentId);

    public List<Models> getModelsSecond(String parentKnowledge, String userId);

    /**
     * 模糊搜索当前科目下所有学生模型
     * @param name
     * @param rootId
     * @return
     */
    public List<Models> getModelByAllUsers(String name, String rootId);
    public int delModelcontents(Modelcontents modelcontents);

    public int delModels(Models models);
    public List<Models> getModelByUsers(String name, String rootId, String userId);
    public List<Models> getParentModels(String id);

//    public List<Modelcontents> getModelContacts2();
//
//    public List<Models> getModelListMapper2(String Id,String userId);

    public List<Models> getModelsList(String id,String userkey);

    /*
     * 展示模型
     * */
    public List<Models> getModelListMapper(String Id,String userId);

    public int updateModelFileName(String Id, String FileName);

    /*
     * 编辑模型/场景
     * */
    public int updateModel(Models model);

    /*
     * 获取模型/场景
     * */
    public Models getModel(String Id);

    /*上传
     * */
    String AddModelsContent2(Modelcontents modelcontents, String treeId, String nodeId);

    /*
     * 修改模型的名字
     * */
    public int updateModelscontentCustomName(String id,String CustomName,String PhotoName) ;

    /**
     * 生成版本文件内容
     * @param path
     */
    public Map<String,List<String>> setVersion(String path, String name, List<String> nameList, List<String> fileList);

    /**
     * 生成版本文件
     * @param nameList
     */
    public void setVersionFile(List<String> nameList,List<String> fileList,String path) throws Exception ;

    public String lastModelsNodeIdInAll(String treeId,String clickNodeId,String userKey);

    /**
     * 新增功能
     */
    public Boolean inknowModels(Models knowledges) ;

    /**
     * Root查询
     * @return
     */
    public String slRootModels(String id);

    String AddModelsContentFile(Modelcontents modelcontents, String treeId, String nodeId, Models model, MultipartFile[] file);

    List<Models> getContentModels(String subjectId, String sarchStr);
}
