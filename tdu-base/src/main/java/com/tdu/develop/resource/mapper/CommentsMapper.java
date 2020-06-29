package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Comments;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsMapper {

    /**
     *  新增评论
     * @param comments
     */
    public  void addComments(Comments comments);

    /**
     *  新增评论
     * @param comments
     */
    public  void insertComments(Comments comments);
}
