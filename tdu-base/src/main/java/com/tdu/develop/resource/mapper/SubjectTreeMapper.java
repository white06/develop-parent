package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.*;
import com.tdu.develop.user.pojo.MenuTrees;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-14-14:52
 */
@Repository
public interface SubjectTreeMapper {
    /**
     * 获取课程
     * @param majorId
     * @return
     */
    public List<SubjectTrees> getTT(@Param("majorId") String majorId);

    public String getKnowledgeUrl(String knowledgecontentId);

    public String getSubjectModelsRootId2(@Param("SubjectKey") String SubjectKey);

    public void AddNewSubjectTree(SubjectTrees subjectTrees);

    public List<SubjectTrees> GetSubjectTree(String SubjectKey);

    public List<Knowledges> GetSubjectTreePage(String SubjectKey);

    public String getSubjectRootId(@Param("subjectTree_Id") String subjectTree_Id);



    public List<SubjectTrees> getSourceList(String subjectkey);
    public List<Knowledges> subjectChange(String treeType);
    /**
     * 统计资源树下的大节点数
     */
    public int seleNum(@Param("id") String id, @Param("rootId") String rootId);

    /**
     * 统计资源树下的大节点数
     */
    public int seleNum2(@Param("id") String id, @Param("rootId") String rootId, @Param("userKey") String userKey);

    /**
     * 统计资源树下的大节点数（模型）
     */
    public int seleModelNum(@Param("id") String id, @Param("rootId") String rootId, @Param("userId") String userId);
    /**
     * 获取第一个大节点的数据
     * @return
     */
    public Knowledges seleFirst(@Param("treeId") String id, @Param("rootId") String rootId);
    /**
     * 获取第一个大节点的数据
     * @return
     */
    public Knowledges seleFirst2(@Param("treeId") String id, @Param("rootId") String rootId, @Param("userKey") String userKey);
    /**
     * 根据id查询目录点信息
     * @param knowId
     * @return
     */
    public Knowledges seleKnow(@Param("knowId") String knowId);
    /**
     * 获取模型数据第一个大节点的数据
     * @return
     */
    public RoleKnowledges seleModelFirst(@Param("treeId") String id, @Param("rootId") String rootId, @Param("userId") String userId);
    /**
     * 获取后面大节点的数据
     * @param preId
     * @param treeId
     * @param rootId
     * @return
     */
    public Knowledges seleOther(@Param("preId") String preId, @Param("treeId") String treeId, @Param("rootId") String rootId);

    /**
     * 获取后面大节点的数据
     * @param preId
     * @param treeId
     * @param rootId
     * @return
     */
    public Knowledges seleOther2(@Param("preId") String preId, @Param("treeId") String treeId, @Param("rootId") String rootId, @Param("userKey") String userKey);
    /**
     * 获取模型后面大节点的数据
     * @param preId
     * @param treeId
     * @param rootId
     * @return
     */
    public RoleKnowledges seleModelOther(@Param("preId") String preId, @Param("treeId") String treeId, @Param("rootId") String rootId, @Param("userId") String userId);
    /**
     * 统计大节点下子节点数
     * @param id
     * @return
     */
    public int childNum(String id);
    /**
     * 获取大节点下第一个子节点的数据
     * @param parentId
     * @return
     */
    public Knowledges childFirst(String parentId);
    /**
     * 获取大节点下其他子节点的数据
     * @param preId
     * @param parentId
     * @return
     */
    public Knowledges childOther(@Param("preId") String preId, @Param("parentId") String parentId);
    /**
     * 获取类型
     * @param contentid
     * @return
     */
    public String getType(String contentid);
    /**
     * 根据id获取对象参数
     * @param id
     * @return
     */
    public Knowlegcontent getSimulateParams(String id);
    /**
     * 获取最大的order
     * @return
     */
    public int getMaxOrder();
    /**
     * 根据id删除数据
     * @param knowledgecontentId
     */
    public void updateknow(@Param("content")String content,@Param("knowledgecontentId")String knowledgecontentId);
    /**
     * 保存参数
     * @param kt
     */
    public int saveSimulateModel(Knowlegcontent kt);
    /**
     * 根据id删除数据
     * @param sourceId
     */
    public void deleteSimulateModel(String sourceId);
    /**
     * 获取该节点下所有的子类
     * @param treeNodeId
     * @return
     */
    public List<Knowledges> getAllSubclass(String treeNodeId);
    /**
     * 根据knowledgeId获取数据
     * @param knowledgeId
     * @return
     */
    public Knowlegcontent getFileContent(String knowledgeId);
    /**
     * 根据id获取树id
     * @param knowledge_Id
     * @return
     */
    public String getTreeId(String knowledge_Id);
    /**
     * 根据Id删除数据
     * @param id
     */
    public int deleteKnowledges(String id);
    /**
     * 根据当前节点id获取上一节点id
     * @param currentNodeId
     * @return
     */
    public String getPreNodeId(String currentNodeId);
    /**
     * 根据当前节点id获取下一节点id
     * @param deletedNodeId
     * @return
     */
    public String getNextNodeId(String deletedNodeId);
    /**
     * 修改节点的上一节点id
     * @param preId
     * @param nextId
     */
    public void alterNextPreNodeId(@Param("preId") String preId, @Param("nextId") String nextId);

    /**
     * 从  subjecttrees 表 获取rootId
     * @param SubjectKey
     * @return
     */
    public String getSubjectScenesRootId2(@Param("SubjectKey") String SubjectKey);

    /**
     * 获取subId
     * @param subId
     * @param treeName
     * @return
     */
    public String getSubId(@Param("subId") String subId, @Param("treeName") String treeName);

    /**
     * 获取知识点nmae
     * @param knowledgecontentId
     * @return
     */
    public String getKnowNmae(@Param("knowledgecontentId") String knowledgecontentId);

    /**
     * 获取跟我学Id
     * @param subId
     * @return
     */
    public String getPractice(@Param("subId") String subId);

    /**
     * 获取教学资源的rootId
     * @param stId
     * @return
     */
    public String getRoot(@Param("stId") String stId);

    /**
     * 根据rootid获取教学资源首节点信息
     * @param rootId
     * @return
     */
    public List<Knowledges> getTeacherF(@Param("rootId") String rootId);

    /**
     * 获取来闯关Id
     * @param subId
     * @return
     */
    public String getGoId(@Param("subId") String subId);

    /**
     * 获取Id
     * @param subId
     * @return
     */
    public String getDoId(@Param("subId") String subId);

    /**
     * 根绝subId获取教学资源id
     * @param subId
     * @return
     */
    public String getTeacherId(@Param("subId") String subId);

    /**
     * 根据officeid获取节点内容
     * @param oId
     * @return
     */
    public Knowlegcontent getKc(@Param("oId") String oId);

    /**
     * 获取知识点内容Id
     * @param dId
     * @return
     */
    public String getkciId(@Param("dId") String dId);

    /**
     * 获取考试信息
     * @param dId
     * @return
     */
    public Exams getExam(@Param("dId") String dId);

    /**
     * 获取考试题目信息
     * @param examId
     * @return
     */
    public List<QuestionPagerContents> getAll(@Param("examId") String examId);

    public String getQueKey(@Param("examId") String examId);

    /**
     * 获取学生考核成绩
     * @param examId
     * @return
     */
    public StuQueInfors getkaohe(@Param("examId") String examId, @Param("userId") String userId);

    /**
     * 获取仿真Id
     * @param tId
     * @return
     */
    public String getKnow(@Param("tId") String tId);

    /**
     * 获取成绩信息模型
     * @param pId
     * @param userId
     * @return
     */
    public StuQueInfors getstuq(@Param("pId") String pId, @Param("userId") String userId);

    /**
     * 获取班级学生成绩
     * @param dId
     * @param userId
     * @param questionKey
     * @return
     */
    public Integer getStuScore(@Param("dId") String dId, @Param("userId") String userId, @Param("questionKey") String questionKey);

    /**
     * 查询是否已经有成绩了
     * @param dId
     * @param userId
     * @return
     */
    public StutotalScores getstuto(@Param("dId") String dId, @Param("userId") String userId);

    /**
     * 查询是否已经有成绩了
     * @param dId
     * @param userId
     * @return
     */
    public StutotalScores getstuqNan(@Param("pId") String dId, @Param("userId") String userId);

    /**
     * 获取infos的信息
     * @param dId
     * @param questionKey
     * @return
     */
    public StuQueInfors getstuin(@Param("dId") String dId, @Param("questionKey") String questionKey, @Param("userId") String userId);

    /**
     * 修改成绩
     * @param getscroe
     * @param id
     */
    public void updatastu(@Param("getscroe") int getscroe, @Param("id") String id);

    /**
     * 添加成绩
     * @param stuId
     * @param dId
     * @param getscroe
     * @param userId
     */
    public void repalceStu(@Param("stuId") String stuId, @Param("dId") String dId, @Param("getscroe") int getscroe, @Param("userId") String userId);

    /**
     * 修改成绩信息表
     * @param getscroe
     * @param id
     */
    public void updataIn(@Param("getscroe") int getscroe, @Param("id") String id, @Param("scroe") String scroe);

    /**
     * 添加成绩信息表
     * @param infosId
     * @param dId
     * @param getscroe
     * @param scroe
     * @param questionKey
     * @param userId
     */
    public void repalceStuIn(@Param("infosId") String infosId, @Param("dId") String dId, @Param("getscroe") int getscroe, @Param("scroe") String scroe, @Param("questionKey") String questionKey, @Param("userId") String userId);

    /**
     * 根据科目id查询科目树大节点数
     * @param subjectId
     * @return
     */
    public int querysubjectTrees(String subjectId);

    /**
     * 查询科目树下所有的子节点
     * @param subjectId
     * @return
     */
    public List<MenuTrees> queryAllTrees(String subjectId);

    /**
     * 获取子节点数
     * @param id
     * @return
     */
    public int querysubjectTree(String id);

    /**
     * 获取第一个子节点
     * @param id
     * @return
     */
    public MenuTrees queryFirstChild(String id);
    /**
     * 查询科目下的大节点
     * @param upperId
     * @return
     */
    public MenuTrees seleotherTree(String upperId);
    /**
     * 查询父元素下所有子节点
     * @param id
     * @return
     */
    public List<MenuTrees> queryAllChildTrees(String id);

    public int selCounts_infor(String subjecttreeId);

    public Knowledges queryFirstNode(String subjecttreeId);

    /**
     *
     * @param parentId
     * @return
     */
    public Knowledges queryOtherNode(String parentId);

    /**
     *
     * @param treeId
     * @return
     */
    public int queryCounts(String treeId);

    public Knowledges queryfirstChild(String treeId);

    public String GetSubjectRootId(String treetype);

    List<Knowledges> getContentKnowledges(@Param("subjectId") String subjectId, @Param("sarchStr") String sarchStr);
}
