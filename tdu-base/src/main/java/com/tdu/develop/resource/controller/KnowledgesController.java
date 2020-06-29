package com.tdu.develop.resource.controller;

import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.Knowlegcontent;
import com.tdu.develop.resource.service.KnowledgesService;
import com.tdu.develop.resource.service.impl.KnowledgesServiceImpl;
import com.tdu.develop.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-15-9:57
 */
@CrossOrigin
@Controller
@RequestMapping(value="KnowledgesController")
public class KnowledgesController {

    @Autowired
    KnowledgesService knowledgesService=new KnowledgesServiceImpl();



    /**
     * 得到第二节点信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getKnowledgesForPage.action",method={RequestMethod.POST})
    @ResponseBody
    public HashMap<String, List> getKnowledgesForPage(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        String subjectTree_Id=request.getParameter("id");
        List<Knowledges> sList=knowledgesService.selectAll(subjectTree_Id);
        List<Knowlegcontent> kcList=new ArrayList<>();
        HashMap<String, List> resultMap = new HashMap<String, List>();
        resultMap.put("knowList", sList);
        Knowlegcontent knowlegcontent = null;
        for (int i = 0; i < sList.size(); i++) {
            if(sList.get(i).getKnowledgecontentId()!="00000000-0000-0000-0000-000000000000"){
                knowlegcontent = knowledgesService.getContent(sList.get(i).getId());
                if(knowlegcontent!=null){
                    kcList.add(knowlegcontent);
                }
            }
        }
        resultMap.put("knowContList", kcList);
        return resultMap;
    }


    /**
     * 得到数据首节点信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getLink.action", method={RequestMethod.POST})
    @ResponseBody
    public String getLink(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String knowledgeId=request.getParameter("knowledgeId");
        knowledgeId= Base64Util.decode(knowledgeId);
        //  1-VR：TDuVREngine    2-编辑器 : TDuVRDirector  3-WEBGl :TDuSimEngine 4-仿真 :TDuSimEngine
        String mark=request.getParameter("mark");
        mark= Base64Util.decode(mark);
        //  type=1 练习   type=2 考核
        String type=request.getParameter("type");
        type= Base64Util.decode(type);

        Knowledges knowledges = knowledgesService.getKnow(knowledgeId);
        Knowlegcontent  knowlegcontent= knowledgesService.getContent(knowledges.getId());

        String userId=(String) session.getAttribute("ID");

        String link = getLink(type,mark,knowledges,knowlegcontent);
        link = link + "KnowledgeID=" + knowledges.getKnowledgecontentId() + "&OperateID=" + userId + "&";
        return link;
    }

    public  String getLink(String type,String mark, Knowledges knowledges,Knowlegcontent  knowlegcontent){
        String str="tduvr://command=open&App=TDuSimEngine&" +
                "Scene=79c6c061-baae-46c7-8de2-ee28d37d5613/c86b27a8-3a34-b198-ab93-01664af51d29/c86b27a8-3a34-b198-ab93-01664af51d29.EXM&" +
                "UserID=79c6c061-baae-46c7-8de2-ee28d37d5613&" +
                "SceneOrModelID=c86b27a8-3a34-b198-ab93-01664af51d29&OpMode=TDuPractice&";
        str=knowlegcontent.getNmae();
        String[] strArr = str.split("\\&");
        String link="";
        System.out.println(strArr.length); //这里输出3
        for (int i = 0; i < strArr.length; ++i){
            System.out.println(strArr[i]);//这里输出a b c
            if(i==1){
                String[] strArri1= strArr[i].split("\\=");
                if(mark.equals("1")){
                    strArri1[1]="TDuVREngine";
                }else if(mark.equals("2")){
                    strArri1[1]="TDuVRDirector";
                }else if(mark.equals("3")){
                    strArri1[1]="TDuSimEngine";
                }else if(mark.equals("4")){
                    strArri1[1]="TDuSimEngine";
                }
                strArr[i]=strArri1[0]+"="+strArri1[1];
            }
            if(i==2){
                String[] strArri2= strArr[i].split("\\=");
                String[] strArri22= strArri2[1].split("\\/");
                String[] strArri23= strArri22[2].split("\\.");
                strArri22[0]=strArri22[0];
                strArri22[1]=strArri22[1];
                strArri23[0]=strArri23[0];
                /*strArri22[0]="*1";
                strArri22[1]="*2";
                strArri23[0]="*1";*/
                System.out.println(1);
                strArr[i]=strArri2[0]+"="+strArri22[0]+"/"+strArri22[1]+"/"+strArri23[0]+"."+strArri23[1];
            }
            if(i==3){
                String[] strArri1= strArr[i].split("\\=");
                //strArri1[1]="UserID";
                //strArri1[1]=knowledges.getUserKey();
                strArr[i]=strArri1[0]+"="+strArri1[1];
            }
            if(i==4){
                String[] strArri1= strArr[i].split("\\=");
                strArri1[1]=strArri1[1];
                //strArri1[1]="SceneOrModelID";
                strArr[i]=strArri1[0]+"="+strArri1[1];
            }
            if(i==5){
                String[] strArri1= strArr[i].split("\\=");
                if(type.equals("1")){
                    strArri1[1]="TDuPractice";
                }else if(type.equals("2")){
                    strArri1[1]="TDuTest";
                }
                //strArri1[1]="OpMode";
                strArr[i]=strArri1[0]+"="+strArri1[1];
            }
            link=link+strArr[i]+"&";
        }
        return link;
    }

    /**
     * 得到数据首节点信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getKnowledgesFis.action", method={RequestMethod.POST})
    @ResponseBody
    public List<Knowledges> getKnowledgesFis(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String subTreeId=request.getParameter("subTreeId");
        //String userId=(String) session.getAttribute("ID");
        List<Knowledges> fList=knowledgesService.getKnowledgesFis(subTreeId);
        return fList;
    }

    /**
     * 得到第二节点信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getKnowledgesSecond.action",method={RequestMethod.POST})
    @ResponseBody
    public List<Knowledges> getKnowledgesSecond(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        String fId=request.getParameter("KnowledgesFisId");
        String userId=(String) session.getAttribute("ID");
        if(userId==null){
            userId=request.getParameter("userId");
        }
        List<Knowledges> sList=knowledgesService.getKnowledgesSecond(fId,userId);
        return sList;
    }

    /**
     * 得到第二节点信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getKnowledgesSecondNew.action",method={RequestMethod.POST})
    @ResponseBody
    public HashMap<String, List> getKnowledgesSecondNew(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        String fId=request.getParameter("KnowledgesFisId");
        String userId=(String) session.getAttribute("ID");
        if(userId==null){
            userId=request.getParameter("userId");
        }
        List<Knowledges> sList=knowledgesService.getKnowledgesSecond(fId,userId);
        List<Knowlegcontent> kcList=new ArrayList<>();
        HashMap<String, List> resultMap = new HashMap<String, List>();
        resultMap.put("knowList", sList);
        Knowlegcontent knowlegcontent = null;
        for (int i = 0; i < sList.size(); i++) {
            if(sList.get(i).getKnowledgecontentId()!="00000000-0000-0000-0000-000000000000"){
                knowlegcontent = knowledgesService.getContent(sList.get(i).getId());
                if(knowlegcontent!=null){
                    kcList.add(knowlegcontent);
                }
            }
        }
        resultMap.put("knowContList", kcList);
        return resultMap;
    }

    /**
     * 得到第二节点信息
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getKnowledgesSecond2.action",method={RequestMethod.POST})
    @ResponseBody
    public List<Knowlegcontent> getKnowledgesSecond2(HttpServletRequest request,HttpServletResponse response,HttpSession session){
        String fId=request.getParameter("KnowledgesFisId");
        String userId=(String) session.getAttribute("ID");
        if(userId==null){
            userId=request.getParameter("userId");
        }
        List<Knowlegcontent> sList=knowledgesService.getKnowledgesSecond2(fId,userId);
        return sList;
    }
    /**
     * 获取知识点的TYPE
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="getKnowType.action",method={RequestMethod.POST})
    @ResponseBody
    public void getKnowType(HttpServletRequest request,HttpServletResponse response){
        String knowledgecontentId=request.getParameter("knowledgecontentId");
        String type=knowledgesService.getKnowType(knowledgecontentId);
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.getWriter().print(type);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 获取nmae
     * @param request
     * @param response
     */
    @RequestMapping(value="getKnowNmae.action",method={RequestMethod.POST})
    @ResponseBody
    public void getKnowNmae(HttpServletRequest request,HttpServletResponse response){
        String knowledgecontentId=request.getParameter("knowledgecontentId");
        String type=knowledgesService.getKnowNmae(knowledgecontentId);
        response.setContentType("text/html;charset=UTF-8");
        try {
            response.getWriter().print(type);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //得到用户之下的科目
    @RequestMapping(value="getSubjectsTree.action")
    @ResponseBody
    public List<Knowledges> getSubjectsTree(HttpSession session,HttpServletRequest request,HttpServletResponse response){
        //users.setId(session.getAttribute("ID").toString());
			/*HashMap<Integer, HashMap<String, List<Subjects>>> majorMap= new HashMap<Integer, HashMap<String, List<Subjects>>>();
			HashMap<String, List<Subjects>> subjectMap = new HashMap<String, List<Subjects>>();
			String College_Id = asi.getCollege_Id(users.getId());
			System.out.println("College_Id:"+College_Id);
			List<Major> returnJson=asi.MajorListByCollege_Id(College_Id);
			for (int i=0;i<returnJson.size();i++) {
				//System.out.println(returnJson.get(i).getId()+"----"+returnJson.get(i).getMajorName()+"--------"+i);
				List<Subjects> subList =asi.getMajorSubjects(returnJson.get(i).getId());
				//System.out.println(subList);
				for (int j = 0; j <subList.size(); j++) {
					//System.out.println(subList.get(j).getSubjectName()+"-----"+j);
					subjectMap.put(returnJson.get(i).getMajorName()+","+returnJson.get(i).getId(), subList);
					//System.out.println(subjectMap);
				}

				majorMap.put(i, subjectMap);

			}*/
        //majorMap.put(0, subjectMap);
			/*subList=subjectServiceImp.seleSub(users);*/
        //System.out.println(majorMap);
        //String userkey=(String) session.getAttribute("ID");
        String userkey=request.getParameter("userkey");
        List<Knowledges> returnJson=knowledgesService.getSubjectsTree(userkey);
        for (Knowledges kno:returnJson) {
            String name = knowledgesService.getSubjectName(kno.getSubjectTree_Id());
            kno.setName(name);
        }
        return returnJson;
    }
}
