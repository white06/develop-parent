package com.tdu.develop.user.controller;

import com.tdu.develop.resource.pojo.ImgURL;
import com.tdu.develop.user.service.ImgUrlService;
import com.tdu.develop.user.service.SeduService;
import com.tdu.develop.user.service.impl.ImgUrlServiceImpl;
import com.tdu.develop.user.service.impl.SeduServiceImpl;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * @author 志阳
 * @create 2019-08-30-14:15
 */
@CrossOrigin
@Controller
@RequestMapping(value = "SeduController")
public class SeduController {
    @Autowired
    SeduService seduService = new SeduServiceImpl();

    @RequestMapping(value = "getCount.action", method = {RequestMethod.POST})
    @ResponseBody
    public Integer getCount(HttpServletRequest request, HttpServletResponse response) {

        return seduService.getCount();
    }

    @RequestMapping(value = "addCount.action", method = {RequestMethod.POST})
    @ResponseBody
    public void addCount(HttpServletRequest request, HttpServletResponse response) {
        Integer count = Integer.valueOf(request.getParameter("count"));
        seduService.addCount(count);
    }

}
