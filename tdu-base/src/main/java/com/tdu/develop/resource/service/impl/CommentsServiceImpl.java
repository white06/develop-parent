package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.CommentsMapper;
import com.tdu.develop.resource.pojo.Comments;
import com.tdu.develop.resource.service.CommentsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {


    @Resource
    CommentsMapper commentsMapper;

    @Override
    public void addComments(Comments comments) {
        System.out.println(comments);
        if (comments != null) {
            commentsMapper.addComments(comments);
        }
    }

    @Override
    public Comments getAssessment(String stuKey) {
        Comments comments = commentsMapper.getAssessment(stuKey);
        if (comments != null) {
            return comments;
        }
        return null;
    }

    @Override
    public List<Comments> getUsersment(String nandu, String page, String type, String toUser) {
        return commentsMapper.getUsersment(nandu, page, type, toUser);
    }
}
