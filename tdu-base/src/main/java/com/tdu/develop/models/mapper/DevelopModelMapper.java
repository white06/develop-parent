package com.tdu.develop.models.mapper;

import com.tdu.develop.models.pojo.Modelcontents;
import com.tdu.develop.models.pojo.Models;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-15-11:42
 */
@Repository
public interface DevelopModelMapper {

    public void AddNewModle(Models knowledges);


    public String getFirstModelId(@Param("ParentModel") String ParentModel, @Param("userKey") String userKey);


    public int delmodels(@Param("Id") String Id, @Param("DelTime") String DelTime);
    public int delmodelContets(@Param("Id") String Id, @Param("DelTime") String DelTime);

    /**
     * 更改模型表名称
     */
    public int updateModelName(@Param("ModelContentId") String ModelContentId, @Param("Content") String Content);
    /**
     * 更改模型content表名称
     */
    public int updateModelContentName(@Param("Id") String Id, @Param("CustomName") String CustomName);

    /**
     * 获取model首目录（写死）
     * @return
     */
    public List<Models> getFisModels(@Param("subUpId") String subUpId, @Param("parentModel") String parentModel, @Param("userKey") String userKey);
    /**
     * 获取
     * @param parentId
     * @return
     */
    public List<Models> getSubModels(@Param("parentId") String parentId, @Param("subUpId") String subUpId, @Param("userKey") String userKey);
    /**
     * 添加子节点
     * @param Id
     * @param content
     * @param parentModel
     */
    public void addSubModels(@Param("Id") String Id, @Param("content") String content,
                             @Param("parentModel") String parentModel, @Param("preModel") String preModel,
                             @Param("subUpId") String subUpId, @Param("userKey") String userKey);
    /**
     * 获取subId
     * @param subId
     * @param treeName
     * @return
     */
    public String getSubId(@Param("subId") String subId, @Param("treeName") String treeName);
    /**
     * 修改文件名
     * @param Id
     * @param content
     */
    public void upFirModelTree(@Param("Id") String Id, @Param("content") String content);
    /**
     * 删除model
     * @param Id
     */
    public void deleteModel(@Param("Id") String Id);
    /**
     * 添加父节点id
     * @param Id
     * @param content
     * @param parentId
     * @param subId
     */
    public void addFisModels(@Param("Id") String Id, @Param("content") String content, @Param("parentId") String parentId,
                             @Param("subId") String subId, @Param("userKey") String userKey);


    /**
     * 添加父节点id
     * @param Id
     * @param content
     * @param parentId
     * @param subId
     */
    public void addFisModels2(@Param("Id") String Id, @Param("content") String content, @Param("parentId") String parentId, @Param("PreModel") String PreModel,
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
    public List<Models> getModelcontents(@Param("modelId") String modelId, @Param("userKey") String userKey);
    /**
     * 获取模型信息
     * @param modelId
     * @return
     */
    public Modelcontents getModelcontentsInfos(@Param("modelId") String modelId);
    /**
     * 获取模型subId
     * @param modelId
     * @return
     */
    public String getSubId1(@Param("modelId") String modelId);
    /**
     * 获取模型最大下标
     * @return
     */
    public int getModelsMaxOrder();

    /**
     * 添加模型
     * @param knowlegcontent
     */
    public void addModelsModel(Modelcontents knowlegcontent);
    public void alterModelContent_Id(Modelcontents knowlegcontent);
    public void addLastModelsNode(Models models);
    /**
     * 获取rootId
     * @param subjectTree_Id
     * @return
     */
    public String getSubjectModelsRootId(@Param("subjectTree_Id") String subjectTree_Id);

    /**
     * 搜索modelNum
     * @param id
     * @param rootId
     * @return
     */
    public int seleModelsNum(@Param("id") String id, @Param("rootId") String rootId,@Param("userKey") String userKey);
    /**
     * 查询第一个模型信息
     * @param id
     * @param rootId
     * @return
     */
    public Models seleModelsFirst(@Param("treeId") String id, @Param("rootId") String rootId,@Param("userKey") String userKey);
    /**
     * 获取下一个NodeId
     * @param firstId
     * @return
     */
    public String getModelsNextNodeId(@Param("firstId") String firstId);

    /**
     * 获取该节点下所有的子类
     * @param treeNodeId
     * @return
     */
    public List<Models> getAllModelsclass(@Param("treeNodeId") String treeNodeId);
    /**
     * 根据knowledgeId获取数据
     * @param knowledgeId
     * @return
     */
    public Modelcontents getFileModelsContent(@Param("knowledgeId") String knowledgeId);
    /**
     * 根据id删除数据
     * @param sourceId
     */
    public void deleteSimulateModels(@Param("sourceId") String sourceId);
    /**
     * 根据当前节点id获取上一节点id
     * @param currentNodeId
     * @return
     */
    public String getModelsPreNodeId(@Param("currentNodeId") String currentNodeId);
    /**
     * 修改节点的上一节点id
     * @param preId
     * @param nextId
     */
    public void alterModelsNextPreNodeId(@Param("preId") String preId, @Param("nextId") String nextId);
    /**
     * 根据Id删除数据
     * @param id
     */
    public int deleteModelsKnowledges(@Param("id") String id);
    /**
     * 根据id获取树id
     * @param knowledge_Id
     * @return
     */
    public String getModelsTreeId(@Param("knowledge_Id") String knowledge_Id);
    /**
     * 统计大节点下子节点数
     * @param id
     * @return
     */
    public int childModelsNum(@Param("id") String id);
    /**
     * 根据id查询目录点信息
     * @param knowId
     * @return
     */
    public Models seleModelsKnow(@Param("knowId") String knowId);
    /**
     * 删除
     * @param Id
     * @return
     */
    public int delModelContact(@Param("Id") String Id);
    /**
     * 删除
     * @param Content
     * @return
     */
    public int delModels2(@Param("Content") String Content);
    /**
     * 获取某一层数据根据parentId
     * @param parentId
     * @return
     */
    public List<Models> seleModelsForP(@Param("parentId") String parentId, @Param("userKey") String userKey);

    public List<Models> getSubModelByTreeClick(@Param("ParentModel") String parentId);

    public Models getModelsByTreeClick(@Param("Id") String Id, @Param("Content") String Content);

    public Models getModelsparentId(String Id);

    /**
     * 获取 Models 首节点信息
     * @return
     */
    public List<Models> getModelsFis(String subTreeId);

    public List<Models> getModelsSecond(@Param("ParentModel") String ParentModel, @Param("userKey") String userKey);

    public Modelcontents getModelContentName(@Param("id") String id);

    public String getModelsType(@Param("ModelContentId") String ModelContentId);

    /**
     * 模糊搜索当前科目下所有学生模型
     * @param name
     * @param rootId
     * @return
     */
    public List<Models> getModelByAllUsers(@Param("Content") String Content, @Param("SubjectTree_Id") String SubjectTree_Id);

    public int delModelcontents(Modelcontents modelcontents);

    public int delModels(Models models);

    public List<Models> getModelByTeamUserModels(@Param("Content") String Content, @Param("SubjectTree_Id") String SubjectTree_Id, @Param("userKey") String userKey);

    /**
     * 根据父Id获取子节点
     */
    public List<Models> getParentModels(String Id);

//    public List<Modelcontents> getModelContacts2();
//
//    public List<Models> getModelListMapper2(@Param("ContactKey")String ContactKey,@Param("userKey") String userKey);

    /**
     * 获取模型list
     * @param id
     * @return
     */
    public List<Models> getModelsList(@Param("Id")String Id,@Param("userKey")String userKey);

    public List<Models> getModelListMapper(@Param("ContactKey")String ContactKey,@Param("userKey") String userKey);


    public int updateModelFileName(@Param("Id")String Id,@Param("FileName") String FileName);


    public int updateModel(Models model);

    public Models getModel(String Id);
    public int updateModelsContent(@Param("id")String knowlegcontentId,@Param("name")String name,@Param("PhotoName")String PhotoName);

    /**
     * 获取模型信息
     * @param modelId
     * @return
     */
    public Modelcontents getModelcontentsInfos2(@Param("id") String id);

    public int seleModelsNum2(@Param("id") String id,@Param("rootId") String rootId,@Param("userKey") String userKey);

    public Models seleModelsFirst2(@Param("treeId")String id,@Param("rootId")String rootId,@Param("userKey") String userKey);

    /**
     * 新增功能
     */
    public Boolean inknowModels(Models knowledges) ;

    public String seleRootModels(String id);

    List<Models> getContentModels(@Param("subjectId")String subjectId,@Param("sarchStr")String sarchStr);
}
