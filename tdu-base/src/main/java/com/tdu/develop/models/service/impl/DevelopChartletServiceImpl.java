package com.tdu.develop.models.service.impl;

import com.tdu.develop.models.mapper.DevelopChartletMapper;
import com.tdu.develop.models.pojo.Chartletcontents;
import com.tdu.develop.models.pojo.Chartlets;
import com.tdu.develop.models.service.DevelopChartletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-16-17:17
 */
@Service
public class DevelopChartletServiceImpl implements DevelopChartletService {

    @Autowired
    DevelopChartletMapper developChartletMapper;
    /**
     * 获取首节点
     */
    public List<Chartlets> getChartletsFis(String subTreeId) {

        return developChartletMapper.getChartletsFis(subTreeId);
    }

    /**
     * 获取次节点
     */
    @Override
    public List<Chartlets> getChartletsSecond(String parentKnowledge) {
        List<Chartlets> ksList=developChartletMapper.getChartletsSecond(parentKnowledge);
        return ksList;
    }

    /**
     * 获取贴图内容表
     */
    @Override
    public Chartletcontents getChartletContentName(String id) {
        Chartletcontents scenecontents = developChartletMapper.getChartletContentName(id);
        return scenecontents;
    }
    @Override
    public int delChartletcontents(Chartletcontents chartletcontents) {
        return developChartletMapper.delChartletcontents(chartletcontents);
    }

    @Override
    public int delChartlets(Chartlets chartlets) {
        return developChartletMapper.delChartlets(chartlets);
    }
}
