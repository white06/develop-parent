package com.tdu.develop.resource.controller;

import com.tdu.develop.resource.pojo.NavigationBar;
import com.tdu.develop.resource.pojo.SubjectTrees;
import com.tdu.develop.resource.service.NavigationBarService;
import com.tdu.develop.resource.service.impl.NavigationBarServiceImpl;
import com.tdu.develop.user.pojo.ZNodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 志阳
 * @create 2019-08-14-9:11
 */
@CrossOrigin
@Controller
@RequestMapping(value="NavigationBarController")
public class NavigationBarController {
        @Autowired
        NavigationBarService navigationBarService=new NavigationBarServiceImpl();

        @RequestMapping(value="seleNavigation.action")
        @ResponseBody
        public List<NavigationBar> seleNavigation(){
            List<NavigationBar> list=new ArrayList<>();
            list= navigationBarService.selePid();
            return list;
        }

        @RequestMapping(value="seleNavigation2.action")
        @ResponseBody
        public List<ZNodes> seleNavigation2(String subjectId){
            List<ZNodes> list=new ArrayList<>();
            list= navigationBarService.selePid_other(subjectId);
            return list;
        }

        @RequestMapping(value="addNavigation.action",method={RequestMethod.POST})
        public void addNavigation(HttpServletRequest request,HttpServletResponse response) throws IOException {
            NavigationBar navigationBar=new NavigationBar();
            navigationBar.setId(UUID.randomUUID().toString());
            navigationBar.setColumnName(request.getParameter("columnName"));
            navigationBar.setColumnLink(request.getParameter("columnLink"));
            navigationBar.setColumnPicture(request.getParameter("columnPicture"));
            navigationBar.setColumnLevel(Integer.valueOf(request.getParameter("columnLevel")));
            navigationBar.setColumnPid(request.getParameter("columnPid"));
            navigationBar.setUserrole(Integer.valueOf(request.getParameter("userrole")));
            if ( navigationBarService.inNavigation(navigationBar)) {
                response.getWriter().print("ture");
            }else {
                response.getWriter().print("err");
            }
        }

        @RequestMapping(value="upNavigation.action",method={RequestMethod.POST})
        public void upNavigation(HttpServletRequest request,HttpServletResponse response) throws IOException{
            SubjectTrees subjectTrees = new SubjectTrees();
            subjectTrees.setId(request.getParameter("id"));
            subjectTrees.setTreeName(request.getParameter("columnName"));
            subjectTrees.setColumnLink(request.getParameter("columnLink"));
            subjectTrees.setIcon(request.getParameter("columnPicture"));
            subjectTrees.setSubjectKey(request.getParameter("subjectId"));
            if ( navigationBarService.upNavigation(subjectTrees)) {
                response.getWriter().print("ture");
            }else {
                response.getWriter().print("err");
            }
        }

        @RequestMapping(value="seleLevel1.action",method={RequestMethod.POST})
        @ResponseBody
        public List<NavigationBar> seleLevel1(){
            List<NavigationBar> list = new ArrayList<>();
            list= navigationBarService.seleLevel1();
            return list;
        }

        @RequestMapping(value="delNav.action",method={RequestMethod.POST})
        public void delNav(HttpServletRequest request,HttpServletResponse response) throws IOException{
            if( navigationBarService.delNav(request.getParameter("id"))){
                response.getWriter().print("ture");
            }else {
                response.getWriter().print("err");
            }
        }



        //目录的新增
        @RequestMapping(value="ins.action",method={RequestMethod.POST})
        @ResponseBody
        public ZNodes ins(HttpServletRequest request, HttpServletResponse response){
            String subjectId = request.getParameter("subjectId");
            ZNodes subjectTrees =  navigationBarService.insTrees(subjectId);
		/*NavigationBar navigationBar=new NavigationBar();
		String id=UUID.randomUUID().toString();
		String name="新建目录";
		String uppper=request.getParameter("lei");
		String subjectId = request.getParameter("subjectId");
		navigationBar.setId(id);
		navigationBar.setColumnName(name);
		navigationBar.setUserrole(0);
		navigationBar.setSubjectId(subjectId);
		if (!uppper.isEmpty()) {
			navigationBar.setColumnUpper(uppper);
		}
		if (request.getParameter("id")!="") {//添加子科目目录
			navigationBar.setColumnLevel(2);
			navigationBar.setColumnPid(request.getParameter("id"));
		}else if (request.getParameter("id")=="") {//添加科目目录
			navigationBar.setColumnLevel(1);
			navigationBar.setColumnPid( navigationBarService.seleRoot(subjectId));
		}
		if ( navigationBarService.inNavigation2(navigationBar)) {
			Navigation_other navigation_other=new Navigation_other();
			navigation_other.setId(id);
			navigation_other.setName(name);
			if(request.getParameter("id")==""){
				navigation_other.setpId("0");
			}else{
				navigation_other.setpId(navigationBar.getColumnPid());
			}
			navigation_other.setColumnPid(navigationBar.getColumnPid());
			navigation_other.setColumnLevel(navigationBar.getColumnLevel());
			navigation_other.setUserrole(navigationBar.getUserrole());
			navigation_other.setColumnUpper(navigationBar.getColumnUpper());
			return navigation_other;
		}*/
            return subjectTrees;
        }
        /**
         * 树结构插入
         * @return
         */
        @RequestMapping("insertTree.action")
        @ResponseBody
        public ZNodes insertTree(HttpServletRequest request) {
            String subjectId = request.getParameter("subjectId");
            String treeId = request.getParameter("id");
            ZNodes zNodes =  navigationBarService.insertTree(subjectId,treeId);
            return zNodes;
        }

        //拖动功能的实现
        @RequestMapping(value="drop.action",method={RequestMethod.POST})
        public void drop(HttpServletRequest request, HttpServletResponse response){
            String nextId=request.getParameter("nextId");//有可能为空
            String nextprevknowledgeId=request.getParameter("nextprevknowledgeId");//有可能为空
            String prevId=request.getParameter("prevId");//有可能为空
            String prevprevknowledgeId=request.getParameter("prevprevknowledgeId");//有可能为空
            String selfId=request.getParameter("selfId");
            String selfprevknowledgeId=request.getParameter("selfprevknowledgeId");//有可能为空
            String selfparentknowledgeId=request.getParameter("selfparentknowledgeId");//有可能为空
            if (!nextId.isEmpty()) {
                navigationBarService.upNext(nextId, nextprevknowledgeId);
            }
            if (!prevId.isEmpty()) {
                navigationBarService.upNext(prevId, prevprevknowledgeId);
            }
            navigationBarService.upsome(selfId, selfprevknowledgeId, selfparentknowledgeId);
        }
}
