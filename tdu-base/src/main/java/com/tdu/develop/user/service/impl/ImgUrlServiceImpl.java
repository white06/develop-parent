package com.tdu.develop.user.service.impl;

import com.tdu.develop.resource.pojo.ImgURL;
import com.tdu.develop.user.mapper.ImgUrlMapper;
import com.tdu.develop.user.service.ImgUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-30-14:16
 */
@Service
public class ImgUrlServiceImpl implements ImgUrlService {

    @Autowired
    ImgUrlMapper imgUrlMapper;

    /**
     * 后面的方法都是用于处理页面图片
     */
    public List<ImgURL> seleImgURL() {

        List<ImgURL> list = imgUrlMapper.seleImgURL();
        return list;
    }

    public boolean upImgURL(String imgURL, String id) {

        imgUrlMapper.upImgURL(imgURL, id);
        return true;
    }

    public String seleLoginBackground() {

        String loginBackground;
        try {
            loginBackground = imgUrlMapper.seleLoginBackground();
        } catch (Exception e) {
            loginBackground = imgUrlMapper.seleLoginBackgroundAgain();
        }
        return loginBackground;
    }

    public String seleSchoolNameImg() {

        String schoolNameImg = imgUrlMapper.seleSchoolNameImg();
        return schoolNameImg;
    }

    public String seleSchoolLogo1() {

        String schoolLogo1 = imgUrlMapper.seleSchoolLogo1();
        return schoolLogo1;
    }

    public String seleSchoolLogo2() {

        String schoolLogo2 = imgUrlMapper.seleSchoolLogo2();
        return schoolLogo2;
    }
}
