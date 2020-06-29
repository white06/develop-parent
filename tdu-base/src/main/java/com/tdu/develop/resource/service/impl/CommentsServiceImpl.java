package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.CommentsMapper;
import com.tdu.develop.resource.mapper.InterpretMapper;
import com.tdu.develop.resource.pojo.Comments;
import com.tdu.develop.resource.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CommentsServiceImpl implements CommentsService {


    @Resource
    CommentsMapper commentsMapper;

    @Override
    public void addComments(Comments comments) {
        System.out.println(comments);
        if(comments != null) {
            commentsMapper.addComments(comments);
        }
    }
}
