package com.tdu.develop.user.controller;

import com.tdu.develop.resource.pojo.ImgURL;
import com.tdu.develop.user.service.ImgUrlService;
import com.tdu.develop.user.service.impl.ImgUrlServiceImpl;
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
@RequestMapping(value = "ImgUrlController")
public class ImgUrlController {
    @Autowired
    ImgUrlService imgUrlService = new ImgUrlServiceImpl();

    @RequestMapping(value = "seleImgURL.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<ImgURL> seleImgURL() {
        List<ImgURL> list = imgUrlService.seleImgURL();
        return list;
    }

    @RequestMapping(value = "upImgURL.action", method = {RequestMethod.POST})
    public void upImgURL(HttpServletRequest request, HttpServletResponse response, @RequestParam("imgfile") MultipartFile file, @RequestParam("id") String id, @RequestParam("filePath") String filePath) throws IOException {
        if (!file.isEmpty()) {

            String fileURL = filePath;
            //得到文件名
            String fileName = file.getOriginalFilename();
            //得到文件后缀
            String fileEnd = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            //使用UUID创建文件名
            String uuid = UUID.randomUUID().toString();
            //使用StringBuffer创建文件的存储路径
            StringBuffer sbRealPath = new StringBuffer();
            sbRealPath.append(fileURL).append(uuid).append(".").append(fileEnd);
            StringBuffer sbname = new StringBuffer();
            sbname.append(uuid).append(".").append(fileEnd);

            File saveFile = new File(sbRealPath.toString());
            if (!saveFile.exists()) {
                InputStream is = file.getInputStream();
                saveFile.createNewFile();
                BufferedInputStream bis = new BufferedInputStream(is);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(saveFile));
                byte[] flash = new byte[1024];
                int len = 0;
                while (-1 != (len = bis.read(flash))) {
                    bos.write(flash, 0, len);
                }
                bos.flush();
                bis.close();
                bos.close();
            }


            String imgURL = "img/" + uuid + "." + fileEnd;

            if (imgUrlService.upImgURL(imgURL, id)) {
                response.getWriter().print("ture");
            }
            ;

        } else {
            response.getWriter().print("ture");
        }

    }

    @RequestMapping(value = "getimgUrl.action", method = {RequestMethod.POST})
    public void getimgUrl(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String key = request.getParameter("imgKey");
        String imgURL = null;
        if (key.equals("loginBackground")) {
            imgURL = imgUrlService.seleLoginBackground();
        } else if (key.equals("schoolNameImg")) {
            imgURL = imgUrlService.seleSchoolNameImg();
        } else if (key.equals("schoolLogo1")) {
            imgURL = imgUrlService.seleSchoolLogo1();
        } else if (key.equals("schoolLogo2")) {
            imgURL = imgUrlService.seleSchoolLogo2();
        }
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(imgURL);
    }
}
