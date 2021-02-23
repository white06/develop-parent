package com.tdu.develop.resource.controller;

import com.tdu.develop.resource.pojo.CustomerInformation;
import com.tdu.develop.resource.pojo.Follow;
import com.tdu.develop.resource.pojo.blue;
import com.tdu.develop.resource.service.BusinessService;
import com.tdu.develop.resource.service.CustomerService;
import com.tdu.develop.resource.service.ItemService;
import com.tdu.develop.resource.service.impl.BusinessServiceImpl;
import com.tdu.develop.resource.service.impl.CustomerServiceImpl;
import com.tdu.develop.resource.service.impl.ItemServiceImpl;
import com.tdu.develop.util.ExcelClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 志阳
 * @create 2021-01-28-10:24
 */
@CrossOrigin
@Controller
@RequestMapping(value = "BusinessController")
public class BusinessController {


    @Autowired
    BusinessService businessService = new BusinessServiceImpl();


    /**
     *
     *
     * @param request
     * @param session
     * @throws Exception
     */

    @RequestMapping(value = "getBusiness.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Map<String,Object>> getBusiness(HttpServletRequest request, HttpSession session) throws Exception {
        //String userId = session.getAttribute("ID").toString();
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        String userId="4d272f66-9dac-4b87-a2a1-22b6e5910779";
        if(businessService.getBusinessInfo(userId)!=null){
            list=businessService.getBusinessInfo(userId);
        }
        return list;
    }

    /**
     *
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getBusinessForid.action", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> getBusinessForid(HttpServletRequest request, HttpSession session) throws Exception {
        //String userId = session.getAttribute("ID").toString();
        String busId=request.getParameter("busId");
   //     String userId="4d272f66-9dac-4b87-a2a1-22b6e5910779";
        return businessService.getBusinessForid(busId);
    }
    /**
     * 修改商机
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "modifyBusiness.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean modifyBusiness(HttpServletRequest request, HttpSession session) throws Exception {
     //   String userId = (String) session.getAttribute("ID");
        String userId="4d272f66-9dac-4b87-a2a1-22b6e5910779";
        Map<String,Object> busMap= new HashMap<String,Object>();
        busMap.put("id", request.getParameter("id"));
        busMap.put("name",request.getParameter("name"));
        //客户信息
        busMap.put("customerId",request.getParameter("customerId"));
        busMap.put("businessId",userId);
//        //跟进进度添加
//
//        busMap.put("followId",request.getParameter("followId"));


        busMap.put("budget",request.getParameter("budget"));
        busMap.put("billTime",request.getParameter("billTime"));
        busMap.put("phase",request.getParameter("phase"));
        busMap.put("possibility",request.getParameter("possibility"));
        busMap.put("number",request.getParameter("number"));
        //默认先用创建用户
        busMap.put("founder",userId);
        busMap.put("collaboration",request.getParameter("collaboration"));
        busMap.put("creationTime",request.getParameter("creationTime"));
        busMap.put("beizhu",request.getParameter("beizhu"));
        return businessService.modifyBusiness(busMap);
    }

    /**
     * 新增商机
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "insBusiness.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean insBusiness(HttpServletRequest request, HttpSession session) throws Exception{
    //    String userId = (String) session.getAttribute("ID");
        String userId="4d272f66-9dac-4b87-a2a1-22b6e5910779";
        String busId=UUID.randomUUID().toString();
        String ROLEname=(String) session.getAttribute("Name");
        Map<String,Object> busMap= new HashMap<String,Object>();
        busMap.put("id", busId);
        busMap.put("name",request.getParameter("name"));
        //客户信息
        busMap.put("customerId",request.getParameter("customerId"));
        busMap.put("businessId",userId);

        //跟进信息
        busMap.put("followId",UUID.randomUUID().toString());
        busMap.put("lastTime",request.getParameter("lastTime"));
        busMap.put("content",request.getParameter("content"));

        busMap.put("ROLEname",ROLEname);

        busMap.put("type",request.getParameter("type"));



        busMap.put("budget",request.getParameter("budget"));
        busMap.put("billTime",request.getParameter("billTime"));
        busMap.put("phase",request.getParameter("phase"));
        busMap.put("possibility",request.getParameter("possibility"));
        busMap.put("number",request.getParameter("number"));
        busMap.put("founder",request.getParameter("founder"));
       // busMap.put("collaboration",request.getParameter("collaboration"));
        busMap.put("collaboration",userId);
        busMap.put("creationTime",request.getParameter("creationTime"));
        busMap.put("beizhu",request.getParameter("beizhu"));
        return businessService.insBusiness(busMap);
    }

    /**
     * 删除商机
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "deleBussiness.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean deleBussiness(HttpServletRequest request, HttpSession session) throws Exception{
        String busId=request.getParameter("id");
        businessService.deleBusiness(busId);
        return true;
    }
    @RequestMapping(value = "getFollow.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Follow> getFollow(HttpServletRequest request, HttpSession session) throws Exception{
            String busId=request.getParameter("busId");
        List<Follow> followList=new ArrayList<Follow>();
        followList=businessService.getFollow(busId);
                return  followList;
    }


    @RequestMapping(value = "insFollow.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean insFollow(HttpServletRequest request, HttpSession session) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
        String followId=UUID.randomUUID().toString();
        Follow follow=new Follow();
        follow.setId(followId);
        follow.setContent(request.getParameter("content"));
        if(request.getParameter("lastTime")!=null){
            try {
                follow.setLastTime(format.parse(request.getParameter("lastTime")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        follow.setType(request.getParameter("type"));
        follow.setBusinessId(request.getParameter("businessId"));
        follow.setUserId((String) session.getAttribute("ID"));
        follow.setUserName((String) session.getAttribute("NAME"));
        return businessService.insFollow(follow);

    }

    @RequestMapping(value = "modifyFollow.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean modifyFollow(HttpServletRequest request, HttpSession session) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
        Follow follow=new Follow();
        follow.setId(request.getParameter("id"));
        follow.setContent(request.getParameter("content"));
        if(request.getParameter("lastTime")!=null){
            try {
                follow.setLastTime(format.parse(request.getParameter("lastTime")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        follow.setType(request.getParameter("type"));
        follow.setBusinessId(request.getParameter("businessId"));
        return businessService.modifyFollow(follow);

    }

    @RequestMapping(value = "deleFollow.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean deleFollow(HttpServletRequest request, HttpSession session) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
       String followId=request.getParameter("id");
        return businessService.deltFollow(followId);

    }
}
