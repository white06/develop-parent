package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.QuestionPagerMapper;
import com.tdu.develop.resource.pojo.Question;
import com.tdu.develop.resource.pojo.QuestionPagerContents;
import com.tdu.develop.resource.pojo.QuestionPagers;
import com.tdu.develop.resource.service.QuestionPagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 志阳
 * @create 2019-08-27-14:55
 */
@Service
public class QuestionPagerServiceImpl implements QuestionPagerService {
    @Autowired
    QuestionPagerMapper questionPagerMapper;

    public Boolean DelPagerStoAndeAnswer(String questionPager_Id){
        Boolean falg =false;
        if(questionPagerMapper.DelPagerStoAndeAnswer(questionPager_Id)!=0){
            if(questionPagerMapper.DelPagerchengji(questionPager_Id)!=0){
                falg=true;
            }

        }
        return falg;
    }

    public Boolean DelQuesPagerContent(String questionPager_Id,String questionKey){
        Boolean falg =false;
        if(questionPagerMapper.DelQuesPagerContent(questionPager_Id, questionKey)==1){
            falg=true;
        }
        return falg;
    }

    public int updatePager(String testName,String id) {
        //System.out.println("testName:"+testName+"-id:"+id);
        
		/*QuestionPagers pt = new QuestionPagers();
		pt.setId(UUID.randomUUID().toString());
		pt.setName(testName);
		pt.setUseAble(1);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		pt.setCreateData(sdf.format(date));*/
		/*Users.cut();
		String userName = examMapper.getUserName(sessionId);
		pt.(userName);
		pt.setCiteTime(0);*/

        return questionPagerMapper.updateQuestionPagers(testName, id);
        //return pt.getId();
    }

    public List<QuestionPagers> GetQuestionPagers(/*int page,int rowCount*/) {
        
        return questionPagerMapper.GetQuestionPagers(/*page,rowCount*/);
    }

    public String submitTest(String testName,String sessionId,String subjectKey) {
        //System.out.println("testName:"+testName+"-sessionId:"+sessionId+"-subjectKey:"+subjectKey);
        
        QuestionPagers pt = new QuestionPagers();
        pt.setId(UUID.randomUUID().toString());
        pt.setName(testName);
        pt.setUseAble(1);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        pt.setCreateData(sdf.format(date));
        pt.setSubjectKey(subjectKey);
		/*Users.cut();
		String userName = examMapper.getUserName(sessionId);
		pt.(userName);
		pt.setCiteTime(0);*/

        questionPagerMapper.insertQuestionPagers(pt);
        return pt.getId();
    }

    public String submitTestPaper(String id,String testId,String questionType,int order,int score) {
        //System.out.println("id:"+id+"-testId:"+testId+"-questionType:"+questionType+"-order:"+order+"-score:"+score);
        
        Question question = new Question();
        String result = null;
        int i = 0;
        question.setFenshu(score);
        question = questionPagerMapper.selectAll(id);
        QuestionPagerContents ptc = new QuestionPagerContents();
        ptc.setId(UUID.randomUUID().toString());
        ptc.setQuestionKey(question.getId());
        ptc.setQuestionContent(question.getContent());
        ptc.setQuestionIndex(order);
        ptc.setQuestionPager_Id(testId);
        ptc.setQuestionScore(question.getFenshu());
        ptc.setQuestionType(question.getType());
        i = questionPagerMapper.insertQuestionPagerContents(ptc);
        if(i>0){
            result = "true";
        }else{
            result = "false";
        }
        return result;
    }

    /**
     * 删除数据以及试卷内的试题
     * @param id
     * @return
     */
    public Boolean delPager(String id){
        
        Boolean flag=false;
        if(questionPagerMapper.delPageCont(id)==true&&questionPagerMapper.delPager(id)==true){
            flag =true;
        }
        return flag;
    }

}
