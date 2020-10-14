package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.HtmlPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HtmlPageMapper {

    public void insertHtmlPage(HtmlPage htmlPage);

    public HtmlPage getHtmlPage(@Param("userKey") String userKey, @Param("name") String name);
}
