package com.tdu.develop.user.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesMapper {
    /**
     * 根据权限名查询权限id
     * roles表
     * @param string	权限名roleName
     * @return
     */
    String selIdByRoleName(String string);
    /**
     * 建立用户与权限之间的关系
     * userroles表
     * @param id	用户id
     * @param roleId	权限id
     */
    void insUsersRoles(@Param("id") String id, @Param("roleId") String roleId);

}