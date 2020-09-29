package com.tdu.develop.resource.controller;

import com.tdu.develop.models.pojo.Scenecontents;
import com.tdu.develop.models.pojo.Scenes;
import com.tdu.develop.models.service.DevelopSceneService;
import com.tdu.develop.models.service.impl.DevelopSceneServiceImpl;
import com.tdu.develop.resource.pojo.Comments;
import com.tdu.develop.resource.pojo.Evaluation;
import com.tdu.develop.resource.service.CommentsService;
import com.tdu.develop.resource.service.EvaluationService;
import com.tdu.develop.resource.service.impl.CommentsServiceImpl;
import com.tdu.develop.resource.service.impl.EvaluationServiceImpl;
import org.apache.velocity.runtime.directive.Parse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@Controller
@RequestMapping(value="EvaluationController")
public class EvaluationController {

    @Resource
    public EvaluationService evaluationService = new EvaluationServiceImpl();

    @Resource
    public DevelopSceneService developSceneService = new DevelopSceneServiceImpl();

    @RequestMapping(value="addEvaluation.action",method={RequestMethod.POST})
    @ResponseBody
    public void addEvaluation(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        Evaluation evaluation = new Evaluation();
        evaluation.setId(UUID.randomUUID().toString());
        evaluation.setClarity(request.getParameter("clarity"));
        evaluation.setFullness(request.getParameter("fullness"));
        evaluation.setArticulation(request.getParameter("articulation"));
        evaluation.setKindness( request.getParameter("kindness"));
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = df.format(d);
        evaluation.setCreatData(dateNowStr);
        evaluation.setUserKey(request.getParameter("userKey"));
        evaluation.setSceneKey(request.getParameter("sceneKey"));
        if(evaluation!=null){
            evaluationService.addEvaluation(evaluation);
        }
    }

    @RequestMapping(value="getScenecontentsInfos.action",method={RequestMethod.POST})
    @ResponseBody
    public Scenecontents getScenecontentsInfos(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String sceneId =  request.getParameter("sceneId");
        Scenecontents scenecontents= developSceneService.getScenecontentsInfos(sceneId);
        return  scenecontents;
    }
    @RequestMapping(value="getOneByTimen.action",method={RequestMethod.POST})
    @ResponseBody
    public Evaluation getOneByTimen(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String userKey =  request.getParameter("userKey");
        String sceneKey =  request.getParameter("sceneKey");
        Evaluation evaluation =    evaluationService.getOneByTimen(userKey,sceneKey);
        return  evaluation;
    }

    @RequestMapping(value="getRanking.action",method={RequestMethod.POST})
    @ResponseBody
    public List<Scenes> getRanking(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        List<Scenes> scenesList =  null;
        scenesList = developSceneService.getScenesByRank();
        if(scenesList.size()>0){
            for (int i = 0; i < scenesList.size(); i++) {
                String count=evaluationService.getRanking(scenesList.get(i).getUserKey(),scenesList.get(i).getSceneContentId());
                System.out.println("  -----  count :"+count);
                if(count!=null){
                    scenesList.get(i).setCheckDel(Integer.parseInt(count));
                }
            }
        }
        return  scenesList;
    }
}
