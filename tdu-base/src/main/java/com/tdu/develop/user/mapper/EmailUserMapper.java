package com.tdu.develop.user.mapper;

import com.tdu.develop.user.pojo.EmailUser;
import com.tdu.develop.user.pojo.Users;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 志阳
 * @create 2020-02-26-16:13
 */
@Repository
public interface EmailUserMapper {
    /**
     * 邮件
     */
    public void addEmail(@Param("id") String id, @Param("username") String username, @Param("email") String email, @Param("password") String password,
                         @Param("state") String state, @Param("code") String code, @Param("telephone") String telephone, @Param("ziyuan") String ziyuan);

    public Users seleUser(@Param("username") String username);

    public EmailUser getEmailUser(@Param("id") String id);

}
