package com.tdu.develop.models.controller;

import com.tdu.develop.models.pojo.Voicecontents;
import com.tdu.develop.models.pojo.Voices;
import com.tdu.develop.models.service.DevelopVoicesService;
import com.tdu.develop.models.service.impl.DevelopVoicesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-16-11:11
 */
@CrossOrigin
@Controller
@RequestMapping(value = "DevelopVoicesController")
public class DevelopVoicesController {
    @Autowired
    DevelopVoicesService developVoicesService = new DevelopVoicesServiceImpl();

    /**
     * 得到Voices数据首节点信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getVoicesFis.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Voices> getVoicesFis(HttpServletRequest request, HttpServletResponse response) {
        String subTreeId = request.getParameter("subTreeId");
        List<Voices> fList = developVoicesService.getVoicesFis(subTreeId);
        return fList;
    }

    /**
     * 得到第二节点信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getVoicesSecond.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Voices> getVoicesSecond(HttpServletRequest request, HttpServletResponse response) {
        String fId = request.getParameter("KnowledgesFisId");
        List<Voices> sList = developVoicesService.getVoicesSecond(fId);
        return sList;
    }

    /**
     * 获取name 信息
     *
     * @param request
     * @param response
     * @return
     */

    @RequestMapping(value = "getKnowledgecontentsName.action", method = {RequestMethod.POST})
    @ResponseBody
    public String getKnowledgecontentsName(HttpServletRequest request, HttpServletResponse response) {
        String index_id = request.getParameter("knowledge_Id");
        return developVoicesService.getKnowledgecontentsName(index_id);
    }

    @RequestMapping("getVoicesContentName.action")
    @ResponseBody
    public Voicecontents getVoicesContentName(HttpServletRequest request) {
        String id = request.getParameter("id");
        Voicecontents scenecontents = developVoicesService.getVoiceContentName(id);
        return scenecontents;
    }
}
