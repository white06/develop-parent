package com.tdu.develop.resource.controller;

import com.tdu.develop.models.mapper.DevelopSceneMapper;
import com.tdu.develop.resource.pojo.Link;
import com.tdu.develop.resource.service.LinkService;
import com.tdu.develop.util.Base64Util;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 志阳
 * @create 2020-06-10-14:54
 */


@CrossOrigin
@Controller
@RequestMapping(value="LinkController")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @Autowired
    private DevelopSceneMapper developSceneMapper;

    /**
     * 生成短链接
     *
     * @param
     * @return Caron
     */
    @RequestMapping("/api")
    @ResponseBody
    public Map<String, String> save(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        String url = request.getParameter("link");
        String jiami = request.getParameter("jiami");
        String fenxiang = request.getParameter("fenxiang");
        String leixing = request.getParameter("leixing");
        String[] url1=url.split("&");
        String[] url2=url1[2].split("=");
        String knowIdmi=url2[1];
        String sceneId=Base64Util.decode(knowIdmi);
        if (url == null || "".equals(url)) {
            return null;
        }
        if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("tduvr://")) {
            Link link = new Link();
            link.setLongUrl(url);
            return linkService.save(link, jiami, fenxiang, leixing,sceneId);

        } else {
            map.put("erro", "网址必须以http或https开头");
            return map;
        }

    }

    @RequestMapping("/getapi")
    @ResponseBody
    public Link getLink(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getParameter("url");
        return linkService.getLink(url);
    }

    /**
     * 301跳转
     *
     * @param url
     * @return
     */
    @RequestMapping("/{url}")
    public String restoreUrl(@PathVariable("url") String url, Map<String, Object> map) throws Exception {
        Link link = linkService.getLink("https://www.tduvr.club/vr" + url);
        String restoreUrl = linkService.restoreUrl("https://www.tduvr.club/vr" + url);
        String jiami = "";
        if (link.getJiami() != null) {
            jiami = Base64Util.encode(link.getJiami());
        }

        String yanzheng = "";
        if (link.getYanzheng() != null) {
            yanzheng = Base64Util.encode(link.getYanzheng());
        }
        String baseurl = Base64Util.encode(restoreUrl);
        if (link.getJiami() != null) {


            map.put("jiami", jiami);
            map.put("restoreUrl", restoreUrl);
            map.put("need", link.getYanzheng());
            return "redirect:" + "https://www.tduvr.club/tdu/yanzheng/login-2.html";
        } else if (link.getYanzheng() != null) {
            if (link.getYanzheng().equals("1")) {
                map.put("restoreUrl", restoreUrl);
                return "redirect:" + "https://www.tduvr.club/tdu/yanzheng/login.html";
            }
            return "redirect:https://www.tduvr.club";
        } else {


//            if (restoreUrl != null && !"".equals(restoreUrl)) {
            if (restoreUrl != null) {
                return "redirect:" + restoreUrl;
            } else {
                return "redirect:https://www.tduvr.club";
//            return  "forward:/404.html";    //如果要访问本地html，@RequestMapping("/{url}")前面必须加一层路径/aa/{url}
            }

        }
    }

    @RequestMapping("getlink.action")
    @ResponseBody
    public List<Link> getlink(HttpServletRequest request, HttpSession session) throws Exception{
        String userId= session.getAttribute("ID").toString();

        return linkService.getuserLink(userId);
    }
}
