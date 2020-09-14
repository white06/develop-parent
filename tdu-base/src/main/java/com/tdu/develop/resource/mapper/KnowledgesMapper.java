package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.Knowlegcontent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-15-10:03
 */
@Repository
public interface KnowledgesMapper {
    /**
     * 获取首节点信息
     * @return
     */
    public List<Knowledges> getKnowledgesFis(@Param("subTreeId") String subTreeId);
    public Knowledges getKnow(String id);

    public String seleRoot(String id);

    public Knowledges seleFirst(String id);

    public Knowledges selesecond(@Param("pid") String pid);

    public int seleLength(String id);

    public int seleL(String id);

    public Knowledges selefirstS(@Param("pid") String pid);

    public int seleLengthS(@Param("pid") String pid);

    public List<Knowledges> selectAll(String id);

    public void inknow(Knowledges knowledges);//添加功能

    public void deTree(String id);

    public List<String> slChild(String id);

    public void deChild(String id);

    public String slSelf(String id);

    public void upNext(@Param("id") String id, @Param("pid") String pid);

    public void uprandom(@Param("id") String id, @Param("name") String name);

    public void upknow(@Param("id") String id, @Param("pid") String pid, @Param("fatherId") String fatherId);

    public void upTheNext(@Param("id") String id, @Param("pid") String pid);

    public String seleSubTree(String id);

    public void updateContent(@Param("knowledgeId") String knowledgeId, @Param("content") String content);

    /**
     * 获取次节点信息
     * @param parentKnowledge
     * @return
     */
    public List<Knowledges> getKnowledgesSecond(@Param("parentKnowledge") String parentKnowledge, @Param("userKey") String userKey);


    /**
     * 获取知识点type
     * @param knowledgecontentId
     * @return
     */
    public String getKnowType(@Param("knowledgecontentId") String knowledgecontentId);
    /**
     * 获取知识点nmae
     * @param knowledgecontentId
     * @return
     */
    public String getKnowNmae(@Param("knowledgecontentId") String knowledgecontentId);

    //仿真成绩
    public List<Knowledges> getSubjectsTree(@Param("SubjectKey") String SubjectKey);

    public String getSubjectId(String id);
/**
     * 通过知识点id获取知识内容
     * @param knowId
     * @return
     */
    public Knowlegcontent getContent(@Param("knowId") String knowId);
}
