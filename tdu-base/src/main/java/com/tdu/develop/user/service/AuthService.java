package com.tdu.develop.user.service;

import com.tdu.develop.user.pojo.Auth;
import com.tdu.develop.user.pojo.AuthDatas;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-26-15:37
 */
public interface AuthService {
    public List<AuthDatas> GetAuths();

    public List<Auth> AuthInfo(String authId) ;
    public void DeleteInfo(String authId) ;
    public void AddAuth(Auth auth) ;
    public void UpdateAuth(Auth auth) ;
    /**
     * 根据新增页面的id来设置权限
     * 默认权限为管理员
     * @param ruthId
     */
    public void addRole(String authId);
}
