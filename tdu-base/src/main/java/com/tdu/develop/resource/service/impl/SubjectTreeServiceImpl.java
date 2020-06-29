package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.SubjectTreeMapper;
import com.tdu.develop.resource.pojo.*;
import com.tdu.develop.resource.service.SubjectTreeService;
import com.tdu.develop.user.pojo.ZNodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * @author 志阳
 * @create 2019-08-14-14:51
 */
@Service
public class SubjectTreeServiceImpl implements SubjectTreeService {

    @Autowired
    SubjectTreeMapper subjectTreeMapper;


    public Map<String, String> saveSimulateModel(Knowlegcontent kt) {
        Map<String, String> map = new HashMap<String, String>();
        kt.setType(simulateType);
        kt.setImageContentIcons(simulateimage);
        int i = subjectTreeMapper.saveSimulateModel(kt);
        subjectTreeMapper.updateknow(kt.getCustomname(),kt.getId());
        if(i>0){
            map.put("Key", "Value");
            map.put("Value", "保存成功");
        }else{
            map.put("fail", "保存失败！");
        }
        return map;
    }
    /**
     * 学生成绩
     * @param rId
     * @param userId
     * @return
     */
    public StutotalScores getStudentScoreNan(String rId,String userId){
        //String tId=subjectTreeMapper.getTreeId(rId);
        //String pId=subjectTreeMapper.getKnow(tId);
        //StuQueInfors  stuQueInfors= subjectTreeMapper.getstuq(pId, userId);
        StutotalScores  stutotalScores= subjectTreeMapper.getstuqNan(rId, userId);
        return stutotalScores;
    }

    public List<ZNodes> seleKnowledgesNan(String treeid) {
        ZNodes zNodes = new ZNodes();
        Knowledges knowledges = new Knowledges();
        List<ZNodes> list = new ArrayList<ZNodes>();
        String rootId = getSubjectRootId(treeid);
        //统计资源树下大节点数
        int sub = subjectTreeMapper.seleNum(treeid, rootId);
        if(sub!=0){
            //获取第一个大节点的数据
            knowledges = subjectTreeMapper.seleFirst(treeid, rootId);
            zNodes.setId(knowledges.getId());
            zNodes.setKnowledgecontentId(knowledges.getKnowledgecontentId());
            zNodes.setName(knowledges.getContent());
            zNodes.setpId("0");
            zNodes.setIcon(image);
            zNodes.setImageicon(image);
            list.add(zNodes);
            String preId = knowledges.getId();
            if(subjectTreeMapper.childNum(preId)!=0){
                list.addAll(this.seleChild(preId));
            }
            //获取其他大节点的数据

            for(int i=1;i<sub;i++){
                ZNodes zNodes2 = new ZNodes();
                Knowledges knowledges2 = subjectTreeMapper.seleOther(preId, treeid, rootId);
                zNodes2.setId(knowledges2.getId());
                zNodes2.setKnowledgecontentId(knowledges2.getKnowledgecontentId());
                zNodes2.setName(knowledges2.getContent());
                zNodes2.setpId("0");
                zNodes2.setImageicon(knowledges2.getImageIcons());
                zNodes2.setIcon(knowledges2.getImageIcons());
                list.add(zNodes2);
                preId = knowledges2.getId();
                if(subjectTreeMapper.childNum(preId)!=0){
                    list.addAll(this.seleChild(preId));
                }
            }
        }
        return list;
    }



    public List<SubjectTrees> getUsersSub(String majorId){
        return subjectTreeMapper.getTT(majorId);
    }

    public String getKnowledgesUrl(String Id) {
        return subjectTreeMapper.getKnowledgeUrl(Id);
    }

    public void AddNewSubjectTree(SubjectTrees subjectTrees) {
        subjectTreeMapper.AddNewSubjectTree(subjectTrees);
    }

    public List<SubjectTrees> GetSubjectTree(String SubjectKey) {
        List<SubjectTrees> list=new ArrayList<SubjectTrees>();
        list=subjectTreeMapper.GetSubjectTree(SubjectKey);
        return list;
    }

    public List<Knowledges> GetSubjectTreePage(String SubjectKey) {
        List<Knowledges> list=new ArrayList<Knowledges>();
        list=subjectTreeMapper.GetSubjectTreePage(SubjectKey);
        return list;
    }

    public List<ZNodes> seleknowledges(String treeid, String userId) {
        ZNodes zNodes = new ZNodes();
        Knowledges knowledges = new Knowledges();
        List<ZNodes> list = new ArrayList<ZNodes>();
        String rootId = getSubjectRootId(treeid);
        //统计资源树下大节点数
        int sub = subjectTreeMapper.seleNum2(treeid, rootId,userId);
        if(sub!=0){
            //获取第一个大节点的数据
            knowledges = subjectTreeMapper.seleFirst2(treeid, rootId,userId);
            zNodes.setId(knowledges.getId());
            zNodes.setKnowledgecontentId(knowledges.getKnowledgecontentId());
            zNodes.setName(knowledges.getContent());
            zNodes.setpId("0");
            zNodes.setIcon(image);
            zNodes.setImageicon(image);
            list.add(zNodes);
            String preId = knowledges.getId();
            if(subjectTreeMapper.childNum(preId)!=0){
                list.addAll(this.seleChild(preId));
            }
            //获取其他大节点的数据

            for(int i=1;i<sub;i++){
                ZNodes zNodes2 = new ZNodes();
                Knowledges knowledges2 = subjectTreeMapper.seleOther2(preId, treeid, rootId,userId);
                if(knowledges2!=null){
                    zNodes2.setId(knowledges2.getId());
                    zNodes2.setKnowledgecontentId(knowledges2.getKnowledgecontentId());
                    zNodes2.setName(knowledges2.getContent());
                    zNodes2.setpId("0");
                    zNodes2.setImageicon(knowledges2.getImageIcons());
                    zNodes2.setIcon(knowledges2.getImageIcons());
                    list.add(zNodes2);
                    preId = knowledges2.getId();
                    if(subjectTreeMapper.childNum(preId)!=0){
                        list.addAll(this.seleChild(preId));
                    }
                }
            }
        }
        return list;
    }

    public String getSubjectRootId(String subjectTree_Id) {
        return subjectTreeMapper.getSubjectRootId(subjectTree_Id);
    }




    /**
     * imageicom和icon的图片,节点边上的图标
     */
    public static final String image="../../../Source/imgicon/tag_orange.png";
    /**
     * knowledgecontents的type属性
     */
    public static final String simulateType="仿真";
    /**
     * knowledgecontents的imagecontentIcons属性
     */
    public static final String simulateimage="../../../Source/imgicon/仿真.png";
    public List<Knowledges> subjectChange(String selectValue) {
         
        return subjectTreeMapper.subjectChange(selectValue);
    }
    public List<SubjectTrees> getSourceList(String subjectKey) {
         
        return subjectTreeMapper.getSourceList(subjectKey);
    }
    public synchronized List<ZNodes> seleChild(String id){
         
        List<ZNodes> list = new ArrayList<ZNodes>();
        Knowledges ck = new Knowledges();
        ZNodes zn = new ZNodes();
        //统计大节点下子节点数
        int cnum = subjectTreeMapper.childNum(id);
        if(cnum!=0){
            //获取大节点下第一个子节点
            ck = subjectTreeMapper.childFirst(id);
            zn.setId(ck.getId());
            zn.setKnowledgecontentId(ck.getKnowledgecontentId());
            zn.setName(ck.getContent());
            zn.setpId(ck.getParentKnowledge());
            zn.setIcon(ck.getImageIcons());
            zn.setImageicon(ck.getImageIcons());
            list.add(zn);
            if(subjectTreeMapper.childNum(ck.getId())!=0){
                list.addAll(this.seleChild(ck.getId()));
            }
            //获取第一个子节点的id
            String preId = ck.getId();
            for(int i=1;i<cnum;i++){
                ZNodes zn1 = new ZNodes();
                Knowledges cko = subjectTreeMapper.childOther(preId, id);
                if(cko!=null){
                    zn1.setId(cko.getId());
                    zn1.setKnowledgecontentId(cko.getKnowledgecontentId());
                    zn1.setName(cko.getContent());
                    zn1.setpId(cko.getParentKnowledge());
                    zn1.setIcon(cko.getImageIcons());
                    zn1.setImageicon(cko.getImageIcons());
                    list.add(zn1);
                    preId = cko.getId();
                    if(subjectTreeMapper.childNum(preId)!=0){
                        list.addAll(this.seleChild(cko.getId()));
                    }
                }
            }
        }
        return list;
    }
    public List<ZNodes> selenext(String preId, String treeid, String rootId){
        List<ZNodes> zNodes = new ArrayList<ZNodes>();
        ZNodes zNodes2=new ZNodes();
        Knowledges knowledges = subjectTreeMapper.seleOther(preId, treeid, rootId);
        zNodes2.setId(knowledges.getId());
        zNodes2.setKnowledgecontentId(knowledges.getKnowledgecontentId());
        zNodes2.setName(knowledges.getContent());
        zNodes2.setpId("0");
        zNodes.add(zNodes2);
        preId = knowledges.getId();
        if(subjectTreeMapper.childNum(preId)!=0){
            zNodes.addAll(this.seleChild(preId));
        }

        return zNodes;
    }
    public List<SubjectTrees> treeChange(String subjectId){
        
        List<SubjectTrees> list = subjectTreeMapper.getSourceList(subjectId);
        return list;
    }
    public String getType(String contentid){
        
        String str = subjectTreeMapper.getType(contentid);
        return str;
    }
    public Knowlegcontent getSimulateParams(String id) {
         
        return subjectTreeMapper.getSimulateParams(id);
    }
    public void createAppointedFile(String filePath,String joString) throws IOException{
         
        File file = new File(filePath);
        if(!file.exists()){
            file.mkdirs();
        }
        String treeId = filePath.substring(filePath.lastIndexOf(File.separator)+1);
        File appointedFile = new File(file,(treeId+".js"));
        if(!appointedFile.exists()){
            appointedFile.createNewFile();
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(appointedFile), "utf-8"));
        bw.write(joString);
        bw.flush();
        bw.close();
    }
    public void remove(String treeNodeId, String subjectId) {
         
        List<Knowledges> list = new ArrayList<Knowledges>();
        Knowledges knowledges = new Knowledges();
        //1.找到该节点的所有子类
        list = subjectTreeMapper.getAllSubclass(treeNodeId);
        //System.out.println(2);
        //判断是否存在子文件或子目录
        if(list.size()>0){
            //System.out.println(4);
            for(int i=0;i<list.size();i++){
                knowledges = list.get(i);
                String id = knowledges.getId();
                //判断该子类是内容还是目录
                String result = judgeType(id);
                //System.out.println(result);
                //	if("content".equals(result)){
                //对内容的处理
                deleteFile(id,subjectId);
                //	}else if("directory".equals(result)){
                //对目录的处理
                deleteDirectory(id, subjectId);
                //	}
            }
            deleteDirectory(treeNodeId,subjectId);
        }else{
            //System.out.println(5);
//			String result = judgeType(treeNodeId);
            //		if("content".equals(result)){
            //对内容的处理
            knowledges=subjectTreeMapper.seleKnow(treeNodeId);
            if(!knowledges.getKnowledgecontentId().equals("00000000-0000-0000-0000-000000000000"))
                deleteFile(treeNodeId,subjectId);
            //		}else if("directory".equals(result)){
            //对目录的处理
            deleteDirectory(treeNodeId, subjectId);
            //		}
        }
        //System.out.println(3);
    }
    /**
     * 对内容的处理
     * @param knowledgeId  Id (Knowledges)
     * @param subjectId
     */
    public void deleteFile(String knowledgeId,String subjectId){
         
        Knowlegcontent knowlegcontent =  subjectTreeMapper.getFileContent(knowledgeId);
        //判断内容的类型进行删除
        if(simulateType.equals(knowlegcontent.getType())||AddKnowledgeServiceImpl.HtmlType.equals(knowlegcontent.getType())||
                AddKnowledgeServiceImpl.QuesType.equals(knowlegcontent.getType())||AddKnowledgeServiceImpl.customType.equals(knowlegcontent.getType())){
            //删除内容
            subjectTreeMapper.deleteSimulateModel(knowlegcontent.getId());
            alterNextPreNodeId(knowlegcontent.getKnowledge_Id());
            //删除节点文件
            int i = subjectTreeMapper.deleteKnowledges(knowlegcontent.getId());
        }else{
            //根据id获取树id
            String treeId = subjectTreeMapper.getTreeId(knowlegcontent.getKnowledge_Id());
            String path = AddKnowledgeServiceImpl.filePath+File.separator+subjectId+File.separator+treeId+File.separator+knowlegcontent.getType()+File.separator+knowlegcontent.getId();
            File file = new File(path);
            //删除内容文件
            file.delete();
            //删除内容
            subjectTreeMapper.deleteSimulateModel(knowlegcontent.getId());
            alterNextPreNodeId(knowlegcontent.getKnowledge_Id());
            //删除节点文件
            int i = subjectTreeMapper.deleteKnowledges(knowlegcontent.getId());
            //System.out.println("8:"+knowlegcontent.getKnowledge_Id());
        }

    }
    /**
     * 判断该子类是内容还是目录
     * @param knowledgeId
     * @return "content" 内容
     * 			"directory" 目录
     */
    public String judgeType(String knowledgeId){
        Knowlegcontent knowlegcontent =  subjectTreeMapper.getFileContent(knowledgeId);
        String result;
        if(knowlegcontent!=null){
            //System.out.println("是内容");
            result = "content";
        }else{
            //System.out.println("是目录");
            result = "directory";
        }
        return result;
    }
    /**
     * 对目录的处理
     * @param id 当前knowledges的Id
     * @param subjectId
     */
    public void deleteDirectory(String id,String subjectId){
         
        //判断该目录是否有子类
        if(subjectTreeMapper.childNum(id)>0){
            //遍历所有的子类
            for(int i=0;i<subjectTreeMapper.childNum(id);i++){
                //获取子类对象
                Knowledges knowledges = subjectTreeMapper.getAllSubclass(id).get(i);
                //判断子类是内容还是目录
                String result = judgeType(knowledges.getId());
                if("content".equals(result)){
                    alterNextPreNodeId(knowledges.getId());
                    deleteFile(knowledges.getId(), subjectId);
                }else{
                    deleteDirectory(knowledges.getId(), subjectId);
                }
            }
        }else{
            alterNextPreNodeId(id);
            //删除目录
            int i = subjectTreeMapper.deleteKnowledges(id);
        }
    }
    /**
     * 根据删除节点id查询下一节点id并
     * 修改下一节点的上一节点id
     * @param deletedNodeId 当前节点id
     */
    public void alterNextPreNodeId(String deletedNodeId){
         
        //System.out.println("9:"+deletedNodeId);
        //获取下一节点
        String nextId = subjectTreeMapper.getNextNodeId(deletedNodeId);
        //System.out.println("6:"+nextId);
        //判断是否有下一节点
        if(nextId!=null){
            //获取上一节点id
            String preId = subjectTreeMapper.getPreNodeId(deletedNodeId);
            //修改下一节点的上一节点id
            subjectTreeMapper.alterNextPreNodeId(preId,nextId);
        }
    }
    public Knowlegcontent getParams(String id) {
         
        return subjectTreeMapper.getSimulateParams(id);
    }
    public Map<String, String> saveCustomModel(Knowlegcontent kt) {
         
        Map<String, String> map = new HashMap<String, String>();
        kt.setType(AddKnowledgeServiceImpl.customType);
        kt.setImageContentIcons(AddKnowledgeServiceImpl.customImage);
        int i = subjectTreeMapper.saveSimulateModel(kt);
        if(i>0){
            map.put("Key", "Value");
            map.put("Value", "保存成功");
        }else{
            map.put("fail", "保存失败！");
        }
        return map;
    }

    /**
     * 获取知识点nmae
     * @param knowledgecontentId
     * @return
     */
    public String getKnowNmae(String knowledgecontentId){
        return subjectTreeMapper.getKnowNmae(knowledgecontentId);
    }

    /**
     * 根据subid获取跟我学知识点（首级）
     * @param subId
     * @return
     */
    public List<Knowledges> getPractice(String subId){
        String stId=subjectTreeMapper.getPractice(subId);
        //获取首级目录信息
        String rootId = subjectTreeMapper.getRoot(stId);
        return subjectTreeMapper.getTeacherF(rootId);
    }

    /**
     * 根据subid获取来闯关知识点（首级）
     * @param subId
     * @return
     */
    public List<Knowledges> getGo(String subId){
        String stId=subjectTreeMapper.getGoId(subId);
        //获取首级目录信息
        String rootId = subjectTreeMapper.getRoot(stId);
        return subjectTreeMapper.getTeacherF(rootId);
    }

    /**
     * 根据subid获取跟我做知识点（首级）
     * @param subId
     * @return
     */
    public List<Knowledges> getDo(String subId){
        String stId=subjectTreeMapper.getDoId(subId);
        //获取首级目录信息
        String rootId = subjectTreeMapper.getRoot(stId);
        return subjectTreeMapper.getTeacherF(rootId);
    }

    /**
     * 根据subid获取Office知识点（首级）
     * @param subId
     * @return
     */
    public List<Knowledges> getOffice(String subId){
        String stId=subjectTreeMapper.getTeacherId(subId);
        //获取首级目录信息
        System.out.println("stId = [" + stId + "]");
        String rootId = subjectTreeMapper.getRoot(stId);
        System.out.println("rootId = [" + rootId + "]");
        return subjectTreeMapper.getTeacherF(rootId);
    }

    /**
     * 获取off内容
     * @param oId
     * @return
     */
    public Knowlegcontent getKc(String oId){
        return subjectTreeMapper.getKc(oId);
    }

    /**
     * 根据did获取考试Id
     * @param dId
     * @return
     */
    public String getKcId(String dId){
        String kcId=subjectTreeMapper.getkciId(dId);
        return kcId;
    }

    /**
     * 根据did获取考信息
     * @param dId
     * @return
     */
    public Exams getExam(String dId){
        return subjectTreeMapper.getExam(dId);
    }

    /**
     * 根据exmaId获取题目信息
     * @param examId
     * @return
     */
    public Map<String,Object> getQuestion(String examId){
        Map<String,Object> qMap=new HashMap<String, Object>();
        List<QuestionPagerContents> pList=subjectTreeMapper.getAll(examId);
        List<QuestionPagerContents> danxuanList=new ArrayList<QuestionPagerContents>();
        List<QuestionPagerContents> duoxuanList=new ArrayList<QuestionPagerContents>();
        List<QuestionPagerContents> panduanList=new ArrayList<QuestionPagerContents>();
        List<QuestionPagerContents> wendaList=new ArrayList<QuestionPagerContents>();
        List<QuestionPagerContents> fangzhenList=new ArrayList<QuestionPagerContents>();
        for(int i=0;i<pList.size();i++){
            if(pList.get(i).getQuestionType().equals("单选题")){
                danxuanList.add(pList.get(i));
            }else if(pList.get(i).getQuestionType().equals("多选题")){
                duoxuanList.add(pList.get(i));
            }else if(pList.get(i).getQuestionType().equals("判断题")){
                panduanList.add(pList.get(i));
            }else if(pList.get(i).getQuestionType().equals("问答题")){
                wendaList.add(pList.get(i));
            }else if(pList.get(i).getQuestionType().equals("3D仿真实验题")){
                fangzhenList.add(pList.get(i));
            }
        }
        if(danxuanList!=null){
            qMap.put("单选题", danxuanList);
        }
        if(duoxuanList!=null){
            qMap.put("多选题", duoxuanList);
        }
        if(panduanList!=null){
            qMap.put("判断题", panduanList);
        }
        if(wendaList!=null){
            qMap.put("问答题", wendaList);
        }
        if(fangzhenList!=null){
            qMap.put("3D仿真实验题", fangzhenList);
        }
        return qMap;
    }

    public String getQueKey(String examId){
        return subjectTreeMapper.getQueKey(examId);
    }

    /**
     * 获取考核成绩
     * @param examId
     * @param userId
     * @return
     */
    public StuQueInfors getkaohe(String examId,String userId){
        return subjectTreeMapper.getkaohe(examId, userId);
    }

    /**
     * 学生成绩
     * @param rId
     * @param userId
     * @return
     */
    public StuQueInfors getStudentScore(String rId,String userId){
        //String tId=subjectTreeMapper.getTreeId(rId);
        //String pId=subjectTreeMapper.getKnow(tId);
        //StuQueInfors  stuQueInfors= subjectTreeMapper.getstuq(pId, userId);
        StuQueInfors  stuQueInfors= subjectTreeMapper.getstuq(rId, userId);
        return stuQueInfors;
    }

    /**
     *
     * @param dId
     * @param userId
     * @param questionKey
     * @return
     */
    public Integer getStuScore(String dId,String userId,String questionKey){
        return subjectTreeMapper.getStuScore(dId,userId,questionKey);
    }


    public boolean getScores(String scroe,int getscroe,String totalscroe,String dId,String questionKey,String userId,Integer getStuScore){
        boolean panduan = false;
        StutotalScores stutotalScores=subjectTreeMapper.getstuto(dId, userId);
        StuQueInfors stuQueInfors=subjectTreeMapper.getstuin(dId, questionKey,userId);
        System.out.println(getStuScore);
        if(getStuScore==null){
            getStuScore=0;
        }
        if(getStuScore<getscroe){
            if(stutotalScores!=null){
                subjectTreeMapper.updatastu(getscroe, stutotalScores.getId());
                panduan=true;
            }else{
                String stuId= UUID.randomUUID().toString();
                subjectTreeMapper.repalceStu(stuId, dId, getscroe, userId);
                panduan=true;
            }
            if(stuQueInfors!=null){
                subjectTreeMapper.updataIn(getscroe,stuQueInfors.getId(),scroe);
                panduan=true;
            }else{
                String infosId=UUID.randomUUID().toString();
                subjectTreeMapper.repalceStuIn(infosId, dId, getscroe, scroe, questionKey, userId);
                panduan=true;
            }
        }
        return panduan;
    }

    @Override
    public List<Knowledges> queryKnowledgeContents(String subjecttreeId) {
        List<Knowledges> list = new ArrayList<>();
        //List<Knowlegcontent> list = std.queryKnowledgeContents(subjecttreeId);
        //查询所有的大节点数
        int count = subjectTreeMapper.selCounts_infor(subjecttreeId);
        if(count>0) {
            //查询第一个大节点
            Knowledges knowledge = subjectTreeMapper.queryFirstNode(subjecttreeId);
            String parentId = knowledge.getId();
            list.add(knowledge);
            for(int i=1;i<count;i++) {
                Knowledges ke = subjectTreeMapper.queryOtherNode(parentId);
                parentId = ke.getId();
                list.add(ke);
            }
        }
        return list;
    }
    @Override
    public List<Knowledges> queryAllKnowledges(String treeId) {
        //查询所有的子节点数据
//		List<Knowledges> list = std.queryAllKnowledges(treeId);
        List<Knowledges> list = new ArrayList<>();
        int count = subjectTreeMapper.queryCounts(treeId);
        Knowledges ks = subjectTreeMapper.queryfirstChild(treeId);
        if(ks!=null) {
            String preId = ks.getId();
            list.add(ks);
            if (count > 0) {
                for (int i = 1; i < count; i++) {
                    Knowledges ke = subjectTreeMapper.queryOtherNode(preId);
                    preId = ke.getId();
                    list.add(ke);
                }
            }
        }
        return list;
    }

    public String GetSubjectRootId(String treetype) {
        return subjectTreeMapper.GetSubjectRootId(treetype);
    }

    public  List<Knowledges> getContentKnowledges(String subjectId, String sarchStr){
        return  subjectTreeMapper.getContentKnowledges(subjectId,sarchStr);
    }
}
