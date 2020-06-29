package com.tdu.develop.resource.service;


import com.tdu.develop.resource.pojo.Link;

import java.util.List;
import java.util.Map;

/**
 * @author 志阳
 * @create 2020-06-10-14:39
 */
public interface LinkService {

    Map<String,String> save(Link link, String jiami, String yanzheng, String leixing,String knowIdmi)  throws Exception;

    String restoreUrl(String url);

    Link getLink(String url);

    List<Link> getuserLink(String userId);

}
