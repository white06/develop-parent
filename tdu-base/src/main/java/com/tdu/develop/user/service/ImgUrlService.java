package com.tdu.develop.user.service;

import com.tdu.develop.resource.pojo.ImgURL;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-30-14:16
 */
public interface ImgUrlService {
    public List<ImgURL> seleImgURL();

    public boolean upImgURL(String imgURL, String id);

    public String seleLoginBackground();

    public String seleSchoolNameImg();

    public String seleSchoolLogo1();

    public String seleSchoolLogo2();
}
