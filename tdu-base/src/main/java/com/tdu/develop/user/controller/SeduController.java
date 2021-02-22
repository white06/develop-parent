package com.tdu.develop.user.controller;

import com.tdu.develop.resource.pojo.ImgURL;
import com.tdu.develop.user.pojo.SeduBanji;
import com.tdu.develop.user.pojo.Sedubaogao;
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
import javax.servlet.http.HttpSession;
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



    @RequestMapping(value = "getsuduinfo.action", method = {RequestMethod.POST})
    @ResponseBody
    public SeduBanji getsuduinfo(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String userId=session.getAttribute("ID").toString();
        SeduBanji seduBanji=new SeduBanji();
        if(seduService.getsuduinfo(userId)!=null){
            seduBanji=seduService.getsuduinfo(userId);
        }
        return seduBanji;
    }

    /**
     * 获取查询学生id
     * @param request
     * @param response
     * @param session
     * @return
     */


    @RequestMapping(value = "getsuduinfoAuth.action", method = {RequestMethod.POST})
    @ResponseBody
    public SeduBanji getsuduinfoAuth(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String userId=request.getParameter("userId");
        SeduBanji seduBanji=new SeduBanji();
        if(seduService.getsuduinfo(userId)!=null){
            seduBanji=seduService.getsuduinfo(userId);
        }
        return seduBanji;
    }


    @RequestMapping(value = "inssuduinfo.action", method = {RequestMethod.POST})
    @ResponseBody
    public void inssuduinfo(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String userId=session.getAttribute("ID").toString();
        String xuexiao=request.getParameter("xuexiao");
        String zhuanye=request.getParameter("zhuanye");
        String banji=request.getParameter("banji");
        String xuehao=request.getParameter("xuehao");
        SeduBanji seduBanji=new SeduBanji();
        seduBanji.setId(UUID.randomUUID().toString());
        seduBanji.setZhuanye(zhuanye);
        seduBanji.setBanji(banji);
        seduBanji.setUserId(userId);
        seduBanji.setXuehao(xuehao);
        seduBanji.setXuexiao(xuexiao);
        seduService.inssuduinfo(seduBanji);
    }

    @RequestMapping(value = "getCount.action", method = {RequestMethod.POST})
    @ResponseBody
    public String getCount(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        return seduService.getCount();
    }

    @RequestMapping(value = "addbaogao.action", method = {RequestMethod.POST})
    @ResponseBody
    public boolean addbaogao(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String riqi=request.getParameter("riqi");
        String wenti =request.getParameter("wenti");
        String id=UUID.randomUUID().toString();
        String userId =session.getAttribute("ID").toString();
        return seduService.addbaogao(userId,riqi,wenti,userId);
    }


    /*
     * 色度键控处理图片以及合成
     * */
    @RequestMapping(value = "testAdd1.action", method = RequestMethod.POST)
    @ResponseBody
    public String testAdd1(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpSession session) throws Exception {
        String userId=session.getAttribute("ID").toString();
        String fileName = file.getOriginalFilename();
        //截取最后一个.结尾的后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //File file = new File(filePath+File.separator+knowlegcontent.getType());
        InputStream is = file.getInputStream();

        //在指定目录下添加文件
        File writeFile = new File("");
        //本机路径  wamp  www
      //  writeFile = new File("D:/ceshi/"+userId+suffix);
        //服务器路径
             writeFile = new File("/www/wwwroot/file.tduvr.club/pp/" + userId + suffix);

        if (!writeFile.exists()) {
            //创建指定文件
            writeFile.createNewFile();
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(writeFile));
            byte[] flash = new byte[1024];
            int len = 0;
            while (-1 != (len = bis.read(flash))) {
                bos.write(flash, 0, len);
            }
            bos.flush();
            bis.close();
            bos.close();
        }
        return fileName;
    }

    @RequestMapping(value = "getbaogao.action", method = {RequestMethod.POST})
    @ResponseBody
    public Sedubaogao getbaogao(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String userId =session.getAttribute("ID").toString();
        return seduService.getbaogao(userId);
    }


    @RequestMapping(value = "getbaogaoAuth.action", method = {RequestMethod.POST})
    @ResponseBody
    public Sedubaogao getbaogaoAuth(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String userId =request.getParameter("userId");
        return seduService.getbaogao(userId);
    }



    @RequestMapping(value = "addCount.action", method = {RequestMethod.POST})
    @ResponseBody
    public void addCount(HttpServletRequest request, HttpServletResponse response) {
        Integer count = Integer.valueOf(request.getParameter("count"));
        seduService.addCount(count);
    }

}
