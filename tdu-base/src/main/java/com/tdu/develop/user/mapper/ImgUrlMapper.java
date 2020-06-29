package com.tdu.develop.user.mapper;

import com.tdu.develop.resource.pojo.ImgURL;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-30-14:17
 */
@Repository
public interface ImgUrlMapper {

    public List<ImgURL> seleImgURL();

    public void upImgURL(@Param("imgURL") String imgURL, @Param("id") String id);

    public String seleLoginBackground() throws Exception;

    public String seleLoginBackgroundAgain();

    public String seleSchoolNameImg();

    public String seleSchoolLogo1();

    public String seleSchoolLogo2();

}
