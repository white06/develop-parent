package com.tdu.develop.resource.controller;

import com.tdu.develop.resource.pojo.Knowlegcontent;
import com.tdu.develop.resource.pojo.SubjectTrees;
import com.tdu.develop.resource.service.CourseTreeService;
import com.tdu.develop.resource.service.impl.CourseTreeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

import static com.tdu.develop.resource.service.impl.AddKnowledgeServiceImpl.DocumentType;

/**
 * @author 志阳
 * @create 2019-08-27-16:46
 */
@CrossOrigin
@Controller
@RequestMapping(value="CourseTreeController")
public class CourseTreeController {
    @Resource
    public CourseTreeService courseTreeService=new CourseTreeServiceImpl();
    @RequestMapping("getSubjectTrees.action")
    @ResponseBody
    public List<SubjectTrees> getSubjectTrees(String subjectKey){
        List<SubjectTrees> list = courseTreeService.geSubjectTrees(subjectKey);
        return list;
    }
    @RequestMapping("submitTree.action")
    public void submitTree(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        SubjectTrees subjectTrees = new SubjectTrees();
        subjectTrees.setTreeName(request.getParameter("subjectTree"));
        subjectTrees.setStatus(request.getParameter("treeStatus"));
        String style = request.getParameter("treeStyle");
        subjectTrees.setStyle(style);
        subjectTrees.setSubjectKey(request.getParameter("subjectId"));

        /*
        * 0-模型库 1-场景库 2-题库 3-考试 4-资源类
        * */
        if(style.equals("0")){
            courseTreeService.insertTreeModel(subjectTrees);
        }else  if(style.equals("1")){
            courseTreeService.insertTreeScene(subjectTrees);
        }else{
            courseTreeService.insertTree(subjectTrees);
        }
        try {
            response.getWriter().println("js");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("getTreeSourse.action")
    @ResponseBody
    public SubjectTrees editTree(String treeid){
        return courseTreeService.getTreeSource(treeid);
    }


    @RequestMapping("updateTreeNoFile.action")
    public void updateTreeNoFile(HttpServletRequest request, HttpServletResponse response){
        SubjectTrees subjectTrees = new SubjectTrees();
        subjectTrees.setTreeName(request.getParameter("subjectTree"));
        subjectTrees.setStatus(request.getParameter("treeStatus"));
        subjectTrees.setStyle(request.getParameter("treeStyle"));
        subjectTrees.setId(request.getParameter("treeId"));
        subjectTrees.setIcon(request.getParameter("iconName"));
        courseTreeService.updateTree(subjectTrees);
        try {
            response.getWriter().println("js");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("updateTree.action")
    public void updateTree(@RequestParam("file") MultipartFile file, @RequestParam("subjectTree") String subjectTree,
                           @RequestParam("treeStyle") String treeStyle,@RequestParam("treeStatus") String treeStatus,
                           @RequestParam("treeId") String treeId,@RequestParam("iconName") String iconName,
                           HttpServletRequest request, HttpServletResponse response){
        SubjectTrees subjectTrees = new SubjectTrees();
        subjectTrees.setTreeName(subjectTree);
        subjectTrees.setStatus(treeStatus);
        subjectTrees.setStyle(treeStyle);
        subjectTrees.setId(treeId);
        String[] str = null;
        String newIcon = "";
        if(!iconName.equals("")&&iconName!=null&&!iconName.equals("null")){
            str =  iconName.split("/");
            for (int i = 0; i <str.length ; i++) {
                if(i==0){
                    newIcon=str[i];
                }
                if(i==1){
                    newIcon =newIcon+ "/"+str[i];
                }
                if(i==str.length-1){
                    str[i]=file.getOriginalFilename();
                    newIcon =newIcon+ "/"+str[i];
                }
            }
            subjectTrees.setIcon(newIcon);
        }else {
            newIcon="img/ico/"+file.getOriginalFilename();
            subjectTrees.setIcon(newIcon);
        }
        try {
            updatePdf(file,newIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        courseTreeService.updateTree(subjectTrees);
        try {
            response.getWriter().println("js");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updatePdf(MultipartFile mf,String newIcon) throws IOException {

        //File file = new File(filePath+File.separator+knowlegcontent.getType());
        InputStream is = mf.getInputStream();
        File delFile=new File("");
        File writeFile=new File("");
        System.out.println(newIcon);
        //delFile = new File("D:/wamp/www/develop/QZ/PDF/"+preNmae);

        //writeFile = new File("D:/wamp/www/newTdu/"+newIcon);
        //服务器String testUrl = "/www/wwwroot/tdu.tduvr.club/Data/3D/";
        writeFile = new File("/www/wwwroot/computer.tduvr.org/newTdu/"+newIcon);

        if (delFile.isFile()){
            delFile.delete();
        }
        System.out.println(writeFile.exists());
        //在指定目录下添加文件
        if(!writeFile.exists()){
            //创建指定文件
            boolean flag =  writeFile.createNewFile();
            System.out.println(flag);
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(writeFile));
            byte[] flash = new byte[1024];
            int len = 0;
            while(-1 != (len = bis.read(flash))){
                bos.write(flash,0,len);
            }
            bos.flush();
            bis.close();
            bos.close();
        }
    }



    @RequestMapping("deleteTree.action")
    public void deleteTree(String treeid,HttpServletResponse response){
        courseTreeService.deleteTree(treeid);
        try {
            response.getWriter().println("js");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
