package com.tdu.develop.resource.service;

import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.Knowlegcontent;
import com.tdu.develop.user.pojo.ZNodes;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-15-9:59
 */
public interface KnowledgesService {

    public List<Knowledges> selectAll(String id);

    /**
     * 获取首节点
     */
    public List<Knowledges> getKnowledgesFis(String subTreeId);

    public List<ZNodes> seleknowledges(String age);

    public Boolean inknow(Knowledges knowledges);

    public void updateContent(String id, String newname);
    public List<Knowledges> getKnowledgesSecond(String parentKnowledge, String userId);
    public String getKnowType(String knowledgecontentId);
    public String getKnowNmae(String knowledgecontentId);

    public List<Knowlegcontent> SubjcetTree(String id);
    public Knowlegcontent seleKContent(String id);

    public void upcontent(String name, String id);

    public List<Knowledges> getSubjectsTree(String string);

    public String getSubjectName(String Id);

    public List<Knowlegcontent> getKnowledgesSecond2(String parentKnowledge, String userId);

    public Knowlegcontent getContent(String knowId);

    Knowledges getKnow(String knowledgeId);
}
