package com.tdu.develop.resource.service.impl;

import com.tdu.develop.models.mapper.DevelopModelMapper;
import com.tdu.develop.models.mapper.DevelopSceneMapper;
import com.tdu.develop.models.pojo.Models;
import com.tdu.develop.models.pojo.Scenes;
import com.tdu.develop.resource.mapper.CourseTreeMapper;
import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.SubjectTrees;
import com.tdu.develop.resource.service.CourseTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author 志阳
 * @create 2019-08-27-16:48
 */
@Service
public class CourseTreeServiceImpl implements CourseTreeService {


    public static final String ImageIcons = "../../../Source/imgicon/tag_orange.png";
    public static final String KnowledgesContentId = "00000000-0000-0000-0000-000000000000";
    public static final String BeforeCondition = "<root><beforesee></beforesee><userkey></userkey><grades></grades></root>";
    @Autowired
    public CourseTreeMapper courseTreeMapper;

    @Autowired
    public DevelopModelMapper developModelMapper;

    @Autowired
    public DevelopSceneMapper developSceneMapper;

    @Override
    public List<SubjectTrees> geSubjectTrees(String subjectKey) {

        List<SubjectTrees> list = courseTreeMapper.geSubjectTrees(subjectKey);
        return list;
    }

    @Override
    public void insertTree(SubjectTrees subjectTrees) {

        String id = UUID.randomUUID().toString();
        subjectTrees.setId(id);
        subjectTrees.setTreeNum(getTreeNumMax(subjectTrees));
        courseTreeMapper.insertTree(subjectTrees);
        if (subjectTrees.getId() != null) {

            Knowledges knowledges = new Knowledges();
            String rootId = UUID.randomUUID().toString();
            knowledges.setId(rootId);
            knowledges.setContent("Root");
            knowledges.setParentKnowledge(null);
            knowledges.setPreKnowledge(null);
            knowledges.setSubjectTree_Id(subjectTrees.getId());
            knowledges.setImageIcons(ImageIcons);
            knowledges.setKnowledgecontentId(KnowledgesContentId);
            knowledges.setBeforCondition(BeforeCondition);
            courseTreeMapper.insertRoot(knowledges);
        }
    }

    @Override
    public void insertTreeModel(SubjectTrees subjectTrees) {

        String id = UUID.randomUUID().toString();
        subjectTrees.setId(id);
        subjectTrees.setTreeNum(getTreeNumMax(subjectTrees));
        courseTreeMapper.insertTree(subjectTrees);
        if (subjectTrees.getId() != null) {

            Models knowledges = new Models();
            String rootId = UUID.randomUUID().toString();
            knowledges.setId(rootId);
            knowledges.setContent("Root");
            knowledges.setParentModel(null);
            knowledges.setPreModel(null);
            knowledges.setSubjectTree_Id(subjectTrees.getId());
            knowledges.setImageIcons(ImageIcons);
            knowledges.setModelContentId(KnowledgesContentId);
            knowledges.setBeforCondition(BeforeCondition);
            developModelMapper.AddNewModle(knowledges);
        }
    }

    @Override
    public void insertTreeScene(SubjectTrees subjectTrees) {

        String id = UUID.randomUUID().toString();
        subjectTrees.setId(id);
        subjectTrees.setTreeNum(getTreeNumMax(subjectTrees));
        courseTreeMapper.insertTree(subjectTrees);
        if (subjectTrees.getId() != null) {

            Scenes knowledges = new Scenes();
            String rootId = UUID.randomUUID().toString();
            knowledges.setId(rootId);
            knowledges.setContent("Root");
            knowledges.setParentScene(null);
            knowledges.setPreScene(null);
            knowledges.setSubjectTree_Id(subjectTrees.getId());
            knowledges.setImageIcons(ImageIcons);
            knowledges.setSceneContentId(KnowledgesContentId);
            knowledges.setBeforCondition(BeforeCondition);
            developSceneMapper.AddNewScene(knowledges);
        }
    }

    @Override
    public SubjectTrees getTreeSource(String treeId) {

        return courseTreeMapper.getTreeSource(treeId);
    }

    @Override
    public void updateTree(SubjectTrees subjectTrees) {

        courseTreeMapper.updateTree(subjectTrees);
    }

    @Override
    public void deleteTree(String treeid) {

        courseTreeMapper.deleteRoot(treeid);

        courseTreeMapper.deleteTree(treeid);
    }

    public int getTreeNumMax(SubjectTrees subjectTrees) {
        String subjectKey = subjectTrees.getSubjectKey();

        int i = courseTreeMapper.selTreeNumMax(subjectKey);
        if (i == -1)
            return 0;
        return i + 1;
    }

}
