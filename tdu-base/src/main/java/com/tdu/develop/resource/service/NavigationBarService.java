package com.tdu.develop.resource.service;

import com.tdu.develop.resource.pojo.NavigationBar;
import com.tdu.develop.resource.pojo.Navigation_other;
import com.tdu.develop.resource.pojo.SubjectTrees;
import com.tdu.develop.user.pojo.ZNodes;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-27-17:51
 */
public interface NavigationBarService {

    //查询并返回所有的导航栏信息
    public List<NavigationBar> seleNavigation();

    public boolean upNavigation(SubjectTrees subjectTrees);

    /**
     * 新增导航栏的功能，因为有级别和排序，所以采用了比较慢的多次查询，查询出新增的这个数据的上一级这个属性的值
     *
     * @param navigationBar
     * @return
     */
    public boolean inNavigation(NavigationBar navigationBar);

    /**
     * 使用递归的方法，让导航栏有序地被查出，然后有序排列，方便前端进行使用
     *
     * @return
     */
    //递归父部分
    public List<NavigationBar> selePid();

    //递归子部分
    public List<NavigationBar> seleLevel2(String rootId, int pidL);

    /**
     * 使用递归的方法，让导航栏有序地被查出，然后有序排列，方便前端进行使用
     *
     * @param subjectId
     * @return
     */
    //递归父部分
    public List<ZNodes> selePid_other(String subjectId);

    /**
     * 获取子树
     *
     * @return
     */
    public List<SubjectTrees> getChildTrees(String treeId);

    public List<ZNodes> seleknowledges(String treeid);

    public List<ZNodes> seleChild(String id);

    public String getSubjectRootId(String subjectTree_Id);
    //递归子部分

    /**
     * @param rootId 节点id
     * @param pidL   该节点下的子节点数
     * @return
     */
    public List<Navigation_other> seleLevel2_other(String rootId, int pidL);

    /**
     * 将navigation对象转换成navigation_other对象
     *
     * @param navigationBar
     * @return
     */
    public Navigation_other getOther(NavigationBar navigationBar);

    //查询所有的父级(level==1)的导航栏
    public List<NavigationBar> seleLevel1();

    //删除功能
    public boolean delNav(String id);

    /**
     * 递归删除树
     *
     * @param parentId
     */
    public void deleteTrees(String parentId);

    public String seleRoot(String subjectId);

    //添加
    public boolean inNavigation2(NavigationBar navigationBar);

    //拖动修改
    public boolean upNext(String id, String pid);

    //修改一部分数据
    public void upsome(String id, String upper, String pid);

    /**************************************************************************************************************/


    /**
     * 新建按钮功能
     *
     * @param subjectId
     * @return
     */
    public ZNodes insTrees(String subjectId);

    public ZNodes insertTree(String subjectId, String treeId);
}
