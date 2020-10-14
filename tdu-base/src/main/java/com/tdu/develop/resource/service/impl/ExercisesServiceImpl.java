package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.ExercisesMapper;
import com.tdu.develop.resource.pojo.*;
import com.tdu.develop.resource.service.ExercisesService;
import com.tdu.develop.user.mapper.UsersMapper;
import com.tdu.develop.user.pojo.Subjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-27-17:36
 */
@Service
public class ExercisesServiceImpl implements ExercisesService {
    @Autowired
    ExercisesMapper ecMapper;

    @Autowired
    UsersMapper userMapper;


    /**
     * 根据知识点id获取全部题目
     */
    public List<Question> getQuestion(String knowId) {

        return ecMapper.getQuestion(knowId);
    }

    /**
     * 根据id获取内容id
     */
    @Override
    public String getKnowContentId(String knowId) {

        return ecMapper.getKnowContentId(knowId);
    }

    /**
     * 根据内容id获取知识点内容
     */
    @Override
    public Knowlegcontent getKnowContent(String contId) {

        return ecMapper.getKnowContent(contId);
    }

    /**
     * 根据知识点内容对象获取随机题目
     */
    @Override
    public List<Question> getRandomQuestion(Knowlegcontent knowlegcontent) {

        List<Question> randomQueList = new ArrayList<Question>();
        //获取存储的数据
        String xmlName = knowlegcontent.getNmae();
        //获取题目数量
        int preNum = xmlName.indexOf("<num>");
        int afeNum = xmlName.lastIndexOf("</num>");
        preNum = preNum + 5;
        //获取sub相关的属性
        String questionNum = xmlName.substring(preNum, afeNum);
        int preUrl = xmlName.indexOf("subjectid=");
        if (preUrl == -1) {
            preUrl = xmlName.indexOf("userShoucangId=");
        }
        int afeUrl = xmlName.lastIndexOf("</url>");
        preUrl = preUrl + 10;
        String subId = xmlName.substring(preUrl, afeUrl);
        if (questionNum.equals("收藏")) {

        }


        if (subId.equals("all")) {
            randomQueList = ecMapper.getAllSubQue(Integer.parseInt(questionNum));
        } else if (preUrl == 9) {
            preUrl = xmlName.indexOf("knowledgeId=") + 12;
            String knowId = xmlName.substring(preUrl, afeUrl);
            randomQueList = ecMapper.getAllKnowQue(Integer.parseInt(questionNum), knowId);
        } else {
            String subTreesId = ecMapper.getSubjectTreesId(subId);
            //存放题目
            List<Knowledges> knowList = ecMapper.getKnowAll(subTreesId);
            List<Question> queList = new ArrayList<Question>();
            for (int i = 0; i < knowList.size(); i++) {
                List<Question> queSoleList = new ArrayList<Question>();
                queSoleList = ecMapper.getQuestion(knowList.get(i).getId());
                if (queSoleList.size() != 0) {
                    for (int j = 0; j < queSoleList.size(); j++) {
                        queList.add(queSoleList.get(j));
                    }
                }

            }
            //题库的总题数
            int queCount = queList.size();
            int count = 0;
            List<String> result = new ArrayList<String>();
            if (Integer.parseInt(questionNum) > queCount) {
                questionNum = "" + queCount;
            }
            while (count < Integer.parseInt(questionNum)) {
                boolean chongfu = false;
                int randomQue = (int) (Math.random() * queCount);
                String randomNum = "" + randomQue;
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).equals(randomNum)) {
                        chongfu = true;
                    }
                }
                if (!chongfu) {
                    result.add(randomNum);
                    count++;
                }
            }
            for (int i = 0; i < result.size(); i++) {
                randomQueList.add(queList.get(Integer.parseInt(result.get(i))));
            }
        }
        return randomQueList;
    }

    /**
     * 根据知识点内容对象获取随机题目
     */
    @Override
    public List<QuestionPersonal> perQuestion(Knowlegcontent knowlegcontent, String type) {

        //获取存储的数据
        String xmlName = knowlegcontent.getNmae();
        //获取题目数量
        int preNum = xmlName.indexOf("<num>");
        int afeNum = xmlName.lastIndexOf("</num>");
        preNum = preNum + 5;
        //获取sub相关的属性
        String questionNum = xmlName.substring(preNum, afeNum);
        int preUrl = 0;
        int afeUrl = 0;
        if (type.equals("收藏")) {
            preUrl = xmlName.indexOf("userShoucangId=");
            afeUrl = xmlName.lastIndexOf("</url>");
            preUrl = preUrl + 15;
        } else {
            preUrl = xmlName.indexOf("userErrorId=");
            afeUrl = xmlName.lastIndexOf("</url>");
            preUrl += 12;
        }
        String subId = xmlName.substring(preUrl, afeUrl);
        return ecMapper.shoucangQuestion(questionNum, subId);
    }

    /**
     * 获取所有科目
     */
    @Override
    public List<Subjects> getAllSub() {

        return userMapper.getAllSub();
    }

    /**
     * 获取知识点
     */
    @Override
    public List<Knowledges> getKnowledge(String subId) {

        //获取知识树id
        String subTreesId = ecMapper.getSubjectTreesId(subId);
        List<Knowledges> knowList = ecMapper.getKnow(subTreesId);
        return knowList;
    }
    /**
     * 根据知识点获取全部内容
     */
//    @Override
//    public List<Question> getAllQuestion(String knowId) {
//
//
//        return ecMapper.getAllQuestion(knowId);
//    }

    /**
     * 根据知识点内容对象获取随机题目（指定章节）
     */
    public List<Question> getAllQuestion(Knowlegcontent knowlegcontent, String chooseId) {
        List<Question> randomQueList = new ArrayList<Question>();
        //获取存储的数据
        String xmlName = knowlegcontent.getNmae();
        //获取题目数量
        int preNum = xmlName.indexOf("<num>");
        int afeNum = xmlName.lastIndexOf("</num>");
        preNum = preNum + 5;
        //获取sub相关的属性
        String questionNum = xmlName.substring(preNum, afeNum);
        int preUrl = xmlName.indexOf("subjectid=");
        if (preUrl == -1) {
            preUrl = xmlName.indexOf("userShoucangId=");
        }
        preUrl = preUrl + 10;
        randomQueList = ecMapper.getAllKnowQue(Integer.parseInt(questionNum), chooseId);
        return randomQueList;
    }


    /**
     * 根据subId获取首目录
     */
    @Override
    public List<Knowledges> getFirstRoot(String subId) {

        String subTreeId = ecMapper.getSubjectTreesId(subId);
        return ecMapper.getFirstRoot(subTreeId);
    }

    /**
     * 根据parentId获取知识点
     */
    @Override
    public List<Knowledges> getKnowledgeBy(String parentId) {

        //获取知识树id
        List<Knowledges> knowList = ecMapper.getKnowledgeBy(parentId);
        return knowList;
    }

    /**
     * 获取知识点
     */
    @Override
    public List<Object> getKnowledgeZtree(String subId) {

        //获取知识树id
        String subTreesId = ecMapper.getSubjectTreesId(subId);
        List<Knowledges> knowList = ecMapper.getKnow(subTreesId);
        List<Knowledges> knowZtreeList = new ArrayList<Knowledges>();
        List<Object> ztreeList = new ArrayList<Object>();
        List<Knowledges> rootList = ecMapper.getFirstRoot(subTreesId);
        String rootId = rootList.get(0).getId();
        for (int i = 0; i < knowList.size(); i++) {
            if (knowList.get(i).getParentKnowledge() != null) {
                if (knowList.get(i).getParentKnowledge().equals(rootId)) {
                    knowZtreeList.add(knowList.get(i));
                }
            }
        }
        for (int i = 0; i < knowZtreeList.size(); i++) {
            HashMap<String, Object> resultMap = new HashMap<String, Object>();
            List<Knowledges> secondList = new ArrayList<Knowledges>();
            resultMap.put("id", knowZtreeList.get(i).getId());
            resultMap.put("name", knowZtreeList.get(i).getContent());
            resultMap.put("parentKnowledge", knowZtreeList.get(i).getParentKnowledge());
            resultMap.put("preKnowledge", knowZtreeList.get(i).getPreKnowledge());
            resultMap.put("subjectTree_Id", knowZtreeList.get(i).getSubjectTree_Id());
            resultMap.put("imageIcons", knowZtreeList.get(i).getImageIcons());
            resultMap.put("knowledgecontentId", knowZtreeList.get(i).getKnowledgecontentId());
            resultMap.put("BeforCondition", knowZtreeList.get(i).getBeforCondition());
            for (int j = 0; j < knowList.size(); j++) {
                if (knowList.get(j).getParentKnowledge() != null) {
                    if (knowList.get(j).getParentKnowledge().equals(knowZtreeList.get(i).getId())) {
                        knowList.get(j).setName(knowList.get(j).getContent());
                        secondList.add(knowList.get(j));
                    }
                }
            }
            resultMap.put("isParent", true);
            resultMap.put("children", secondList);
            ztreeList.add(i, resultMap);
        }
        return ztreeList;
    }

    /**
     * 根据题目id以及类型添加个人题目
     */
    @Override
    public boolean addPersonal(String timuId, String useType, String userId) {
        Question question = ecMapper.getSingleQuestion(timuId);
        QuestionPersonal personal = new QuestionPersonal();
        personal.setId(question.getId());
        personal.setContent(question.getContent());
        personal.setFenshu(question.getFenshu());
        personal.setPersonalId(userId);
        personal.setQuestionImg(question.getQuestionImg());
        personal.setTime(question.getTime());
        personal.setTitle(question.getTitle());
        personal.setType(question.getType());
        personal.setUseType(useType);
        ecMapper.addPersonal(personal);
        return true;
    }

    /**
     * 根据个人题目id从个人题目表中删除
     */
    @Override
    public boolean deleteShoucang(String timuId) {
        boolean panduan = false;
        if (timuId != null || timuId == "") {
            panduan = true;
            ecMapper.deleteShoucang(timuId);
        }
        return panduan;
    }

    public boolean submit(String userId, String allscore, String examId, String id) {
        if (allscore != null) {
            ecMapper.submit(userId, allscore, examId, id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean addError(String timuId, String useType, String userId) {
        Question question = ecMapper.getSingleQuestion(timuId);
        QuestionPersonal personal = new QuestionPersonal();
        personal.setId(question.getId());
        personal.setContent(question.getContent());
        personal.setFenshu(question.getFenshu());
        personal.setPersonalId(userId);
        personal.setQuestionId(question.getKnowledgeId());
        personal.setQuestionImg(question.getQuestionImg());
        personal.setTime(question.getTime());
        personal.setTitle(question.getTitle());
        personal.setType(question.getType());
        personal.setUseType(useType);
        ecMapper.addPersonal(personal);
        return true;
    }

    @Override
    public List<Question> getAllQuestion(String knowId) {

        return ecMapper.getAllQuestion(knowId);
    }
}
