package com.tdu.develop.resource.controller;

import com.tdu.develop.resource.pojo.Comments;
import com.tdu.develop.resource.service.CommentsService;
import com.tdu.develop.resource.service.impl.CommentsServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@CrossOrigin
@Controller
@RequestMapping(value="CommentsController")
public class CommentsController {

    @Resource
    public CommentsService commentsService = new CommentsServiceImpl();

    @RequestMapping(value="addComments.action",method={RequestMethod.POST})
    @ResponseBody
    public void addComments(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        Comments comments = new Comments();
        comments.setId(UUID.randomUUID().toString());
        comments.setIntegrity(request.getParameter("integrity"));
        comments.setStandard(request.getParameter("standard"));
        comments.setGrammar(request.getParameter("grammar"));
        comments.setFluency( request.getParameter("fluency"));
        comments.setIntonation(request.getParameter("intonation"));
        comments.setReaction(request.getParameter("reaction"));
        comments.setOverall(request.getParameter("overall"));
        comments.setType(request.getParameter("type"));
        comments.setStuKey(request.getParameter("stuKey"));
        if(comments!=null){
            commentsService.addComments(comments);
        }
    }

}
