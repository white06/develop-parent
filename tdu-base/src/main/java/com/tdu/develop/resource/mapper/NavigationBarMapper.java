package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.NavigationBar;
import com.tdu.develop.resource.pojo.Navigation_other;
import com.tdu.develop.resource.pojo.SubjectTrees;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-28-8:54
 */
@Repository
public interface NavigationBarMapper {
    public List<NavigationBar> seleNavigation();

    public void upNavigation(NavigationBar navigationBar);

    public void inNavigation(NavigationBar navigationBar);

    public List<NavigationBar> seleLevel1();

    public NavigationBar seleUpper(String pid);

    public NavigationBar selePid(@Param("pid") String pid, @Param("id") String id);

    public String seleRoot();

    public Integer seleRootL(String pid);

    public void delNav(String id);

    public void delNavMore(String id);

    public String seleGetUpper(String id);

    public String seleSetUpper(String id);

    public void upUpper(@Param("columnUpper") String columnUpper, @Param("id") String id);

    public void upsmoe(@Param("upper") String columnUpper, @Param("pid") String columnPid, @Param("id") String id);

    /*************************************************************************************************/


    public String seleRootBysubject(String subjectId);

    /**
     * 查询根节点下的子节点数
     *
     * @param subjectId
     * @return
     */
    public int seleCount(String subjectId);

    /**
     * 查询根节点下第一个大节点的数据
     * navigationbar表
     *
     * @param subjectId
     * @return
     */
    public Navigation_other selefirstNavigationBar(String subjectId);

    /**
     * 获取大节点下的子节点数
     *
     * @param id 大节点id
     * @return 子节点数
     */
    public int seleChildNodes(String id);

    /**
     * 获取大节点下的第一个子节点数
     *
     * @param id 大节点id
     * @return
     */
    public Navigation_other selefirstChild(String id);

    /**
     * 查询科目下的科目树
     * subjecttrees表
     *
     * @param subjectId 科目id
     * @return
     */
    public List<SubjectTrees> getSubjecttrees(String subjectId);

    /**
     * 查询
     *
     * @param subjectId
     * @return
     */
    public Navigation_other getCount(String subjectId);

    /**
     * 查询科目下的第一个科目树节点
     * subjecttrees表
     *
     * @param subjectId
     * @return
     */
    public SubjectTrees getSubjecttree(String subjectId);

    /**
     * 查询treeid的下一个节点
     *
     * @param subjectId
     * @return
     */
    public SubjectTrees getSubjecttreeOther(String subjectId);

    /**
     * 根据父节点id查询子节点的个数
     *
     * @param treeId
     * @return
     */
    public int getSubjecttreeCount(String treeId);

    /**
     * 根据父节点id查询第一个子节点
     *
     * @param treeId
     * @return
     */
    public SubjectTrees selfirstChild(String treeId);

    /**
     * 根据上节点id查询下一节点
     *
     * @param upperId
     * @return
     */
    public SubjectTrees selOtherChild(String upperId);

    /**
     * 更新科目树
     *
     * @param subjectTrees
     */
    public void upSubjecttree(SubjectTrees subjectTrees);

    /**
     * 根据id删除数据
     *
     * @param id
     */
    public void delSubjectTree(String id);

    /**
     * 查询上节点id
     *
     * @param id
     * @return
     */
    public String selUpId(String id);

    /**
     * 更新下节点id
     *
     * @param id
     * @param onId
     */
    public void upsubjecttreeOfUpper(@Param("id") String id, @Param("onId") String onId);

    /**
     * 查询所有的子节点id
     *
     * @param parentId
     * @return
     */
    public List<String> seleAllChilds(String parentId);

    /**
     * 查询最后一个树节点的id
     *
     * @param subjectId
     * @return
     */
    public String seleLastTreeId(String subjectId);

    /**
     * 插入数据
     *
     * @param sTrees
     */
    public void insSubjecttrees(SubjectTrees sTrees);

    /**
     * 查询id
     *
     * @param treeId
     * @return
     */
    public String querytreeId(String treeId);

    /**
     * @param subjectTrees
     */
    public void insertTrees(SubjectTrees subjectTrees);

    /**
     * 插入root
     *
     * @param knowledges
     */
    public void insertKnowledges(Knowledges knowledges);

    /**
     * 删除相应knowledges表数据
     *
     * @param id
     */
    public void delKnowledges(String id);
}
