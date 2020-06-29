package com.tdu.develop.models.mapper;

import com.tdu.develop.models.pojo.Chartletcontents;
import com.tdu.develop.models.pojo.Chartlets;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-16-17:18
 */
@Repository
public interface DevelopChartletMapper {
    /**
     * 获取Chartlets首节点信息
     * @return
     */
    public List<Chartlets> getChartletsFis(@Param("subTreeId") String subTreeId);

    public List<Chartlets> getChartletsSecond(String ParentChartlet);

    public Chartletcontents getChartletContentName(@Param("id") String id);

    public int delChartletcontents(Chartletcontents chartletcontents);

    public int delChartlets(Chartlets chartlets);
}
