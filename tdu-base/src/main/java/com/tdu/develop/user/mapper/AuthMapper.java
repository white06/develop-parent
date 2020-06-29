package com.tdu.develop.user.mapper;

import com.tdu.develop.user.pojo.Auth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-26-15:37
 */
@Repository
public interface AuthMapper {
    public List<Auth> AuthList();
    public List<Auth> AuthInfo(String authId);
    public void DeleteInfo(String authId);
    public void AddAuth(Auth auth);
    public void UpdateAuth(Auth auth);
    public void addRoleByAuthId(String authId);

    public void addRoleAuth(@Param("authId") String authId, @Param("roldId") String roldId);

    public int queryCount(@Param("authId") String authId, @Param("roldId") String roldId);

    public void delRoleAuth(@Param("authId") String authId, @Param("roldId") String roldId);

    /**
     * 查询角色权限
     * @param roleId
     * @param subjectId
     * @return
     */
    public List<String> queryRoleAuths(@Param("roleId") String roleId, @Param("subjectId") String subjectId);
}
