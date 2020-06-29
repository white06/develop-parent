package com.tdu.develop.models.controller;

import com.tdu.develop.models.pojo.Chartletcontents;
import com.tdu.develop.models.pojo.Chartlets;
import com.tdu.develop.models.service.DevelopChartletService;
import com.tdu.develop.models.service.impl.DevelopChartletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-16-17:12
 */
@CrossOrigin
@Controller
@RequestMapping(value="DevelopChartletController")
public class DevelopChartletController {

    @Autowired
    DevelopChartletService developChartletService=new DevelopChartletServiceImpl();
    /**
     * 得到Chartlets数据首节点信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getChartletsFis.action", method={RequestMethod.POST})
    @ResponseBody
    public List<Chartlets> getChartletsFis(HttpServletRequest request, HttpServletResponse response){
        String subTreeId=request.getParameter("subTreeId");
        List<Chartlets> fList=developChartletService.getChartletsFis(subTreeId);
        return fList;
    }

    /**
     * 得到第二节点信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getChartletsSecond.action",method={RequestMethod.POST})
    @ResponseBody
    public List<Chartlets> getChartletsSecond(HttpServletRequest request, HttpServletResponse response){
        String fId=request.getParameter("KnowledgesFisId");
        List<Chartlets> sList=developChartletService.getChartletsSecond(fId);
        return sList;
    }
    /*
     * 获取贴图内容表
     * */
    @RequestMapping("getChartletsContentName.action")
    @ResponseBody
    public Chartletcontents getChartletsContentName(HttpServletRequest request){
        String id = request.getParameter("id");
        Chartletcontents scenecontents = developChartletService.getChartletContentName(id);
        return scenecontents;
    }

    @RequestMapping(value="delChartletcontents.action",method={RequestMethod.POST})
    @ResponseBody
    public int delChartletcontents(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        String modelID=request.getParameter("Id");//session.getAttribute("ID").toString();
        Chartletcontents chartletcontents = new Chartletcontents();
        chartletcontents.setChartlet_Id(modelID);
        chartletcontents.setCheckDel(1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String timeFormat = sdf.format(date);
        chartletcontents.setDelTime(timeFormat);

        Chartlets chartlets = new Chartlets();
        chartlets.setId(modelID);
        chartlets.setCheckDel(1);
        chartlets.setDelTime(timeFormat);
        int count =developChartletService.delChartletcontents(chartletcontents);
        int count2 =developChartletService.delChartlets(chartlets);
        return (count+count2);
    }
}
