package com.tdu.develop.models.service;

import com.tdu.develop.models.pojo.Chartletcontents;
import com.tdu.develop.models.pojo.Chartlets;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-16-17:16
 */
public interface DevelopChartletService {
    /**
     * 获取 Chartlets 首节点信息
     *
     * @return
     */
    public List<Chartlets> getChartletsFis(String subTreeId);

    /**
     * 获取次节点信息
     *
     * @param parentKnowledge
     * @return
     */
    public List<Chartlets> getChartletsSecond(String parentKnowledge);

    Chartletcontents getChartletContentName(String id);

    public int delChartletcontents(Chartletcontents chartletcontents);

    public int delChartlets(Chartlets chartlets);
}
