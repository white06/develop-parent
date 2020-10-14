package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.SubjectTrees;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-27-16:49
 */
@Repository
public interface CourseTreeMapper {
    /**
     * 获取科目树的数据
     * subjecttrees表
     *
     * @return
     */
    public List<SubjectTrees> geSubjectTrees(String subjectKey);

    /**
     * 插入科目树
     * subjecttrees表
     *
     * @param subjectTrees
     */
    public void insertTree(SubjectTrees subjectTrees);

    /**
     * 根据树id获取树的数据
     *
     * @param treeId
     * @return
     */
    public SubjectTrees getTreeSource(String treeId);

    /**
     * 根据树id插入root
     * knowledges表
     *
     * @param id
     */
    public void insertRoot(Knowledges knowledges);

    /**
     * 更新树的数据
     *
     * @param subjectTrees
     */
    public void updateTree(SubjectTrees subjectTrees);

    /**
     * 根据id删除树的数据
     *
     * @param treeid
     */
    public void deleteTree(String treeid);

    /**
     * 根据id删除root
     *
     * @param treeid
     */
    public void deleteRoot(String treeid);

    /**
     * 根据subjectKey找到treeNum最大值
     * subjecttrees表
     *
     * @param subjectKey
     * @return
     */
    public int selTreeNumMax(String subjectKey);
}
