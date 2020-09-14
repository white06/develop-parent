package com.tdu.develop.resource.controller;

import com.tdu.develop.resource.pojo.Comments;
import com.tdu.develop.resource.service.CommentsService;
import com.tdu.develop.resource.service.impl.CommentsServiceImpl;
import com.tdu.develop.user.pojo.Users;
import com.tdu.develop.user.service.UsersService;
import com.tdu.develop.user.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value="CommentsController")
public class CommentsController {

    @Resource
    public CommentsService commentsService = new CommentsServiceImpl();

    @Autowired
    UsersService usersService = new UserServiceImpl();



    @RequestMapping(value="getUsersment.action",method={RequestMethod.POST})
    @ResponseBody
    public List<Comments> getUsersment(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String nandu = request.getParameter("nandu");
        String page = request.getParameter("page");
        String type = request.getParameter("type");
        String toUser = request.getParameter("toUser");
        List<Comments> commentsList= commentsService.getUsersment(nandu,page,type,toUser);
        if(commentsList.size()>0){
            Users user = null;
            for (Comments com: commentsList) {
                user = usersService.GetNowUser(com.getStuKey());
                if(user!=null){
                    com.setUsers(user);
                }
            }
            return commentsList;
        }
        return null;
    }


    @RequestMapping(value="getAssessment.action",method={RequestMethod.POST})
    @ResponseBody
    public Comments getAssessment(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String stuKey = request.getParameter("stuKey");
        Comments comments = commentsService.getAssessment(stuKey);
        if(comments!=null){
            return comments;
        }
       return null;
    }


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
        comments.setNandu(request.getParameter("nandu"));
        comments.setPage(request.getParameter("page"));
        comments.setContent(request.getParameter("content"));
        comments.setToUser(request.getParameter("toUser"));
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = df.format(d);
        comments.setCreatData(dateNowStr);
            commentsService.addComments(comments);
    }

}
