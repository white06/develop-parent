package com.tdu.develop.resource.service;

import com.tdu.develop.resource.pojo.HtmlPage;


public interface HtmlPageService {

    public  void insertHtmlPage(HtmlPage htmlPage);

    public  HtmlPage getHtmlPage(String userKey, String name);

}
