package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Comments;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentsMapper {

    /**
     *  新增评论
     * @param comments
     */
    public  void addComments(Comments comments);
    /*
    * 获取自我评价
    * */
    public Comments getAssessment(@Param("stuKey")String stuKey);

    List<Comments> getUsersment(@Param("nandu")String nandu,@Param("page")String page,@Param("type")String type,@Param("toUser")String toUser);
}
