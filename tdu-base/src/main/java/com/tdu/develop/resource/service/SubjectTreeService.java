package com.tdu.develop.resource.service;

import com.tdu.develop.resource.pojo.*;
import com.tdu.develop.user.pojo.ZNodes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 志阳
 * @create 2019-08-14-14:51
 */
public interface SubjectTreeService {

    public List<SubjectTrees> getUsersSub(String majorId);

    public String getKnowledgesUrl(String Id);

    public void AddNewSubjectTree(SubjectTrees subjectTrees);

    public List<SubjectTrees> GetSubjectTree(String SubjectKey);

    public List<ZNodes> seleknowledges(String treeid, String userId);

    public String getSubjectRootId(String subjectTree_Id);


    public static final String simulateimage = "../../../Source/imgicon/仿真.png";

    public List<Knowledges> subjectChange(String selectValue);

    public List<SubjectTrees> getSourceList(String subjectKey);

    public List<ZNodes> seleChild(String id);

    public List<ZNodes> selenext(String preId, String treeid, String rootId);

    public List<SubjectTrees> treeChange(String subjectId);

    String getType(String contentid);

    public Knowlegcontent getSimulateParams(String id);

    public void createAppointedFile(String filePath, String joString) throws IOException;

    public void remove(String treeNodeId, String subjectId);

    /**
     * 对内容的处理
     *
     * @param knowledgeId Id (Knowledges)
     * @param subjectId
     */
    public void deleteFile(String knowledgeId, String subjectId);

    /**
     * 判断该子类是内容还是目录
     *
     * @param knowledgeId
     * @return "content" 内容
     * "directory" 目录
     */
    public String judgeType(String knowledgeId);

    /**
     * 对目录的处理
     *
     * @param id        当前knowledges的Id
     * @param subjectId
     */
    public void deleteDirectory(String id, String subjectId);

    /**
     * 根据删除节点id查询下一节点id并
     * 修改下一节点的上一节点id
     *
     * @param deletedNodeId 当前节点id
     */
    public void alterNextPreNodeId(String deletedNodeId);

    public Knowlegcontent getParams(String id);

    public Map<String, String> saveCustomModel(Knowlegcontent kt);

    /**
     * 获取知识点nmae
     *
     * @param knowledgecontentId
     * @return
     */
    public String getKnowNmae(String knowledgecontentId);

    public List<Knowledges> getPractice(String subId);

    public List<Knowledges> getGo(String subId);

    public List<Knowledges> getDo(String subId);

    public List<Knowledges> getOffice(String subId);

    public Knowlegcontent getKc(String oId);

    public String getKcId(String dId);

    public Exams getExam(String dId);

    public Map<String, Object> getQuestion(String examId);

    public String getQueKey(String examId);

    public StuQueInfors getkaohe(String examId, String userId);

    public StuQueInfors getStudentScore(String rId, String userId);

    public List<Knowledges> queryKnowledgeContents(String subjecttreeId);

    public List<Knowledges> queryAllKnowledges(String treeId);


    public List<ZNodes> seleKnowledgesNan(String id);

    public StutotalScores getStudentScoreNan(String rId, String userId);


    public Map<String, String> saveSimulateModel(Knowlegcontent kt);

    public String GetSubjectRootId(String treetype);

    Integer getStuScore(String dId, String userId, String questionKey);

    boolean getScores(String score, int getscroe, String totalscroe, String dId, String questionKey, String userId, Integer getStuScore);

    List GetSubjectTreePage(String subjectKey);

    List<Knowledges> getContentKnowledges(String subjectId, String sarchStr);

    boolean getScoresForYXY(String score, int getscroe, String totalscroe, String dId, String dId1, String userId, String dateNowStr);

    List<StutotalScoresForYXY> getScoresForYXYByStu(String userKey, String subjectKey);

    List<StutotalScoresForYXY> getScoresForYXYByByDate(String classKey, String knoConentId, String startDate, String enddatetime);

    List<StutotalScoresForYXY> getScoresForYXYStu(String rId, String userId);

    List<Knowledges> GetSubjectTreePageForYX(String subjectKey);
}
