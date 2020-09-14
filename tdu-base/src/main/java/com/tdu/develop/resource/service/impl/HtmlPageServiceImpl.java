package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.HtmlPageMapper;
import com.tdu.develop.resource.pojo.HtmlPage;
import com.tdu.develop.resource.service.HtmlPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HtmlPageServiceImpl implements HtmlPageService {

    @Autowired
    HtmlPageMapper htmlPageMapper;

    @Override
    public void insertHtmlPage(HtmlPage htmlPage) {
        htmlPageMapper.insertHtmlPage(htmlPage);
    }

    @Override
    public HtmlPage getHtmlPage(String userKey,String name) {
        return htmlPageMapper.getHtmlPage(userKey,name);
    }
}
