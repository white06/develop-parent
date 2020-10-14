package com.tdu.develop.controller;

import com.tdu.develop.models.service.DevelopModelService;
import com.tdu.develop.models.service.impl.DevelopModelServiceImpl;
import com.tdu.develop.resource.pojo.Knowlegcontent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 志阳
 * @create 2019-08-02-16:10
 */
@Controller
public class HelloController {

    @Autowired
    DevelopModelService developModelService = new DevelopModelServiceImpl();

    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "Hello world";
    }

    @RequestMapping("/success")
    public String success(Map<String, Object> map) {
        map.put("hello", "你好");
        return "tdu/test";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public void upload(@RequestParam("treeId") String treeId, @RequestParam("KnowledgeId") String KnowledgeId, @RequestParam("name") String name2, @RequestParam("file") MultipartFile[] file) {

        System.out.println(" treeId :" + treeId);
        System.out.println(" KnowledgeId :" + KnowledgeId);
        System.out.println(" name2 :" + name2);
        System.out.println(" file :" + file);
        for (MultipartFile f : file) {

            File file1;
            String name = "";
            try {
                /*if (f instanceof CommonsMultipartFile) {
                    //转换成这个对象，然后我们需要通过里面的FileItem来获得相对路径
                    CommonsMultipartFile f2 = (CommonsMultipartFile) f;
                    name = f2.getFileItem().getName();
                    System.out.println(name + "        ---------相对路径");

                    //file1 = new File(getProjectPath() + "/" + name);
                    file1 = new File("/" + name);
                    file1.mkdirs();
                    file1.createNewFile();
                    f.transferTo(file1);
                }*/

                System.out.println(f.getOriginalFilename() + "   iii         --------");
                String path = f.getOriginalFilename();
                String suffix = path.substring(path.lastIndexOf("."));
                //sence  /  model ID
                String pp = "123456789";
                String realPath = pp;
                if (suffix.equals(".tdb") || suffix.equals(".tdc")) {
                    System.out.println(" suffix :" + suffix);
                    realPath = pp + "/1345678" + suffix;
                    System.out.println("  realPath  :" + realPath);
                } else {
                    String[] strArr = path.split("\\/");
                    System.out.println(strArr.length); //这里输出3
                    for (int i = 0; i < strArr.length; ++i) {
                        System.out.println(strArr[i]);//这里输出a b c
                        if (i != 0) {
                            realPath = realPath + "/" + strArr[i];
                        }
                    }
                    System.out.println("  realPath  :" + realPath);
                }
                updatePdf(f, realPath);
                System.out.println("sssss   ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        try {
            setVersion("D:/working/TDuClub/TDu/Data/3D/Model/4d272f66-9dac-4b87-a2a1-22b6e5910779/123456789");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePdf(MultipartFile mf, String realPath) throws IOException {

        //File file = new File(filePath+File.separator+knowlegcontent.getType());
        InputStream is = mf.getInputStream();
        /*File delFile=new File("");
        File writeFile=new File("");
        delFile = new File("D:/working/TDuClub/TDu/Data/3D/Model/"+"4d272f66-9dac-4b87-a2a1-22b6e5910779/"+realPath);
        writeFile = new File("D:/working/TDuClub/TDu/Data/3D/Model/"+"4d272f66-9dac-4b87-a2a1-22b6e5910779/"+realPath);
        System.out.println("D:/working/TDuClub/TDu/Data/3D/Model/"+"4d272f66-9dac-4b87-a2a1-22b6e5910779/"+realPath);
        if (delFile.isFile()){
            delFile.delete();
        }

        if(!writeFile.exists()){//如果文件夹不存在
            writeFile.mkdir();//创建文件夹
        }
        //在指定目录下添加文件
        if(!writeFile.exists()){
            //创建指定文件
            writeFile.createNewFile();
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
        }*/
        StringBuffer fileBuf = new StringBuffer();

        String[] strArr = realPath.split("\\/");
        String wenjianPath = "";
        System.out.println(strArr.length); //这里输出3
        for (int i = 0; i < strArr.length; ++i) {
            System.out.println(strArr[i]);//这里输出a b c
            if (i != (strArr.length - 1)) {
                wenjianPath = wenjianPath + "/" + strArr[i];
            }
        }
        String filePar = "D:/working/TDuClub/TDu/Data/3D/Model/" + "4d272f66-9dac-4b87-a2a1-22b6e5910779" + wenjianPath;// 文件夹路径
        File myPath = new File(filePar);
        if (!myPath.exists()) {//若此目录不存在，则创建之
            myPath.mkdir();
            System.out.println("创建文件夹路径为：" + filePar);
        }
        // 文件夹路径存在的情况下
        String filename = "D:/working/TDuClub/TDu/Data/3D/Model/" + "4d272f66-9dac-4b87-a2a1-22b6e5910779/" + realPath;
        ;// 文件名

        File writeFile = new File("D:/working/TDuClub/TDu/Data/3D/Model/" + "4d272f66-9dac-4b87-a2a1-22b6e5910779/" + realPath);

        try {
            FileWriter fw = new FileWriter(filename, true);// filePar + "\\" + filename,true
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(writeFile));
            byte[] flash = new byte[1024];
            int len = 0;
            while (-1 != (len = bis.read(flash))) {
                bos.write(flash, 0, len);
            }
            bos.flush();
            bis.close();
            bos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public void setVersion(String path) throws Exception {
        //只需关注路径
        //String path="D:\\wamp\\www\\TDu\\Data\\3D\\Model\\4d272f66-9dac-4b87-a2a1-22b6e5910779\\f540168f-1a32-bc3b-6b36-3d9b399034c1";//"D:\\working\\TDuClub\\TDu\\Data\\3D\\Model\\4d272f66-9dac-4b87-a2a1-22b6e5910779\\a3a673d6-a69e-42b0-9c42-e8eaa507746c";
        String name = "";
        System.out.println("  path :" + path);
        List<String> nList = new ArrayList<String>();
        List<String> fList = new ArrayList<String>();
        Map<String, List<String>> versionMap = developModelService.setVersion(path, name, nList, fList);
        List<String> nameList = versionMap.get("nameList");
        List<String> fileList = versionMap.get("fileList");
        developModelService.setVersionFile(nameList, fileList, path);
    }

}
