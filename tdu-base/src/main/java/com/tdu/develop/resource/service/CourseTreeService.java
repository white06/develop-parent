package com.tdu.develop.resource.service;

import com.tdu.develop.resource.pojo.SubjectTrees;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-27-16:48
 */
public interface CourseTreeService {
    public List<SubjectTrees> geSubjectTrees(String subjectKey);

    public void insertTree(SubjectTrees subjectTrees);

    public SubjectTrees getTreeSource(String treeId);

    public void updateTree(SubjectTrees subjectTrees);


    public void deleteTree(String treeid);

    public int getTreeNumMax(SubjectTrees subjectTrees);

    void insertTreeModel(SubjectTrees subjectTrees);

    void insertTreeScene(SubjectTrees subjectTrees);
}
