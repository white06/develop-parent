package com.tdu.develop.resource.service;

import com.tdu.develop.resource.pojo.Comments;

import java.util.List;

public interface CommentsService {

    /**
     *  新增评论
     * @param comments
     */
    public  void addComments(Comments comments);

    /*
     * 获取自我评价
     * */
    public Comments getAssessment(String stuKey);

    List<Comments> getUsersment(String nandu, String page, String type, String toUser);
}
