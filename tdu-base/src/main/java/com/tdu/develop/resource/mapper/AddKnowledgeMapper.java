package com.tdu.develop.resource.mapper;

import com.tdu.develop.models.pojo.Modelcontents;
import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.Knowlegcontent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 志阳
 * @create 2019-08-26-16:51
 */
@Repository
public interface AddKnowledgeMapper {


    public void addUploading(Knowlegcontent knowlegcontent);

    public int getMaxOrder();


    /**
     * 主页添加模型
     *
     * @param modelcontents
     */
    public void addModlesModel(Modelcontents modelcontents);

    /**
     * 在模型末尾添加节点
     *
     * @param modelcontents
     */
    public void addLastNode1(Modelcontents modelcontents);


    /**
     * 查找knowlegcontent名字
     *
     * @param id
     */
    public Knowlegcontent getKnowlegContentName(@Param("id") String id);

    /**
     * 更改knowlege名字
     *
     * @param knowlegcontentId
     */
    public int updateKnowledge(@Param("knowledgecontentId") String knowlegcontentId, @Param("content") String name);

    /**
     * 更改knowlegcontent名字
     *
     * @param knowlegcontentId
     */
    public int updateKnowledgeContent(@Param("id") String knowlegcontentId, @Param("name") String name, @Param("customname") String customname);

    /**
     * 更改knowlegcontent名字
     *
     * @param knowlegcontentId
     */
    public int updateKnowledgeContent2(@Param("id") String knowlegcontentId, @Param("name") String name);


    /**
     * 更改knowlegcontent名字
     *
     * @param knowlegcontent
     */
    public int updateKnowledgeContent3(@Param("id") String id, @Param("customname") String customname);

    /**
     * 更改knowlegcontent名字
     *
     * @param knowlegcontent
     */
    public int updateKnowledgeContent4(@Param("knowledge_Id") String knowledge_Id, @Param("customname") String customname);

    /**
     * 添加模型
     *
     * @param knowlegcontent
     */
    public void addModleModel(Knowlegcontent knowlegcontent);

    /**
     * 添加场景
     *
     * @param knowlegcontent
     */
    public void addViewModel(Knowlegcontent knowlegcontent);

    /**
     * 在末尾添加节点
     *
     * @param knowledges
     */
    public void addLastNode(Knowledges knowledges);

    /**
     * 在末尾添加节点
     *
     * @param knowledges
     */
    public void addLastNode2(Knowledges knowledges);

    /**
     * 获取下一个节点id
     *
     * @param firstId
     * @return
     */
    public String getNextNodeId(String firstId);

    /**
     * 用konwledegcontents表中的数据修改knowledges表中的
     * knowledgeContentId达到绑定数据的作用
     *
     * @param id
     * @param knowledge_Id
     */
    public void alterKnowledgeContent_Id(Knowlegcontent knowlegcontent);

    /**
     * 用于判断是否是知识点
     *
     * @param nodeId 被点击的节点id
     * @return type
     */
    public String isKnowledgeContent(String nodeId);

    /**
     * 添加仿真模型
     *
     * @param knowledgecontent
     */
    public void addSimulateModel(Knowlegcontent knowledgecontent);

    /**
     * 添加题库模型
     *
     * @param knowlegcontent
     */
    public void addQuesModel(Knowlegcontent knowlegcontent);

    /**
     * 添加自定义模型
     *
     * @param knowlegcontent
     */
    public void addCustomModel(Knowlegcontent knowlegcontent);

    /**
     * 根据树id获取对应的科目id
     *
     * @param treeId
     * @return
     */
    public String getSubjectId(String treeId);

    /**
     * 添加html模型
     *
     * @param knowlegcontent
     */
    public void addHtmlEditorContent(Knowlegcontent knowlegcontent);

    /**
     * 根据contentId获取knowledgeId
     *
     * @param contentId
     * @return
     */
    public String getKnowIdbycontentId(String contentId);

    /**
     * 根据点击id修改上传文件名
     *
     * @param name
     * @param knowId
     */
    public void updataName(@Param("name") String name, @Param("knowId") String knowId);

    /**
     * 获取知识点名称
     *
     * @param knowId
     * @return
     */
    public String getKnowNmae(@Param("knowId") String knowId);

    /**
     * 获取考试名称
     *
     * @param examId
     * @return
     */
    public Knowlegcontent getExamName(@Param("examId") String examId);

    public void AddNewKnowledge(Knowledges knowledges);
}
