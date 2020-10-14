package com.tdu.develop.user.service.impl;

import com.tdu.develop.user.mapper.AuthMapper;
import com.tdu.develop.user.pojo.Auth;
import com.tdu.develop.user.pojo.AuthDatas;
import com.tdu.develop.user.pojo.ExtAuthDatas;
import com.tdu.develop.user.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-26-15:37
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthMapper authMapper;

    public List<AuthDatas> GetAuths() {
        List<Auth> AuthList = new ArrayList<Auth>();
        List<AuthDatas> items = new ArrayList<AuthDatas>();
        AuthList = authMapper.AuthList();

        for (Auth auth : AuthList) {

            if (auth.getChildren() != null) {
                AuthDatas temp = new AuthDatas(auth.getId(), "", auth.getPageName(), auth.getNum(), auth.getParametersType(), new ArrayList<ExtAuthDatas>());
                if (auth.getChildren() == "isParent") {

                } else {

                    String[] childrenid = auth.getChildren().split(";");

                    for (String schildrenid : childrenid) {
                        List<Auth> AuthList2 = new ArrayList<Auth>();
                        AuthList2 = authMapper.AuthInfo(schildrenid);
                        if (AuthList2 != null) {
                            for (Auth auth2 : AuthList2) {
                                ExtAuthDatas a = new ExtAuthDatas();
                                a.setId(auth2.getId());
                                a.setLink(auth2.getLink());
                                a.setPageName(auth2.getPageName());
                                a.setNum(auth2.getNum());
                                a.setParametersType(auth2.getParametersType());
                                temp.children.add(a);
                            }
                        }

                    }

                }
                items.add(temp);
            }
        }


        return items;
    }

    public List<Auth> AuthInfo(String authId) {
        return authMapper.AuthInfo(authId);
    }

    public void DeleteInfo(String authId) {
        authMapper.DeleteInfo(authId);
    }

    public void AddAuth(Auth auth) {
        authMapper.AddAuth(auth);
        addRole(auth.getId());
    }

    public void UpdateAuth(Auth auth) {
        authMapper.UpdateAuth(auth);
    }

    /**
     * 根据新增页面的id来设置权限
     * 默认权限为管理员
     *
     * @param ruthId
     */
    public void addRole(String authId) {
        authMapper.addRoleByAuthId(authId);
    }
}
