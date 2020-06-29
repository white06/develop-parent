package com.tdu.develop.user.controller;

import com.tdu.develop.user.pojo.Auth;
import com.tdu.develop.user.pojo.AuthDatas;
import com.tdu.develop.user.service.AuthService;
import com.tdu.develop.user.service.impl.AuthServiceImpl;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author 志阳
 * @create 2019-08-26-15:23
 */
@CrossOrigin
@Controller
@RequestMapping(value="AuthController")
public class AuthController {
    @Autowired
    AuthService authService=new AuthServiceImpl();
    public static String Null=null;

    @RequestMapping(value="GetAuths.action",method={RequestMethod.POST})
    @ResponseBody
    public List<AuthDatas> GetAuths(HttpServletRequest request){

        List<AuthDatas> returnJson=authService.GetAuths();

        return returnJson;
    }

    @RequestMapping(value="GetNewId.action")
    @ResponseBody
    public void GetNewId(HttpServletRequest request,HttpServletResponse response){
        String id= UUID.randomUUID().toString();
        try {
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print("{\"Key\":true,\"Value\":\""+id+"\"}");
        } catch (Exception e) {
        }


    }

    @SuppressWarnings("rawtypes")
    @RequestMapping(value="SubmitDatas.action")
    @ResponseBody
    public void SubmitDatas(HttpServletRequest request, HttpServletResponse response){

        String CustomXml=request.getParameter("xml");
        String deleteddata=request.getParameter("deleteddata");

        if (!deleteddata.equals("")) {
            String[] deleteidarray = deleteddata.split(";");
            for (String item : deleteidarray) {
                authService.DeleteInfo(item);
            }
        }

        try {
            Document doc = DocumentHelper.parseText(CustomXml);
            int num = 0;
            Element rootElt = doc.getRootElement();

            for(Iterator item = rootElt.elementIterator(); item.hasNext();) {
                num++;
                Element element=(Element)item.next();

                String authid=element.elementText("id").toString();
                List<Auth> a=new ArrayList<Auth>();
                a=authService.AuthInfo(authid);

                if(a.isEmpty()) {

                    Auth newAuth=new Auth();
                    newAuth.setId(authid);
                    newAuth.setPageName(element.elementText("pagename").toString());
                    newAuth.setChildren(element.elementText("children").toString()==""?Null:element.elementText("children").toString());
                    newAuth.setLink(element.elementText("children").toString()==""?element.elementText("link").toString():Null);
                    newAuth.setParametersType(Integer.valueOf(element.elementText("ParametersType").toString()));
                    newAuth.setNum(num);
                    authService.AddAuth(newAuth);

                }else {
                    Auth newAuth2=new Auth();
                    newAuth2.setId(authid);
                    newAuth2.setPageName(element.elementText("pagename").toString());
                    newAuth2.setChildren(element.elementText("children").toString()==""?Null:element.elementText("children").toString());
                    newAuth2.setLink(element.elementText("children").toString()==""?element.elementText("link").toString():Null);
                    newAuth2.setParametersType(Integer.valueOf(element.elementText("ParametersType").toString()));
                    newAuth2.setNum(num);

                    authService.UpdateAuth(newAuth2);

                }
            }



        } catch (DocumentException e1) {
            e1.printStackTrace();
        }


    }
}
