package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.AddKnowledgeMapper;
import com.tdu.develop.resource.mapper.SubjectTreeMapper;
import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.Knowlegcontent;
import com.tdu.develop.resource.service.AddKnowledgeService;
import com.tdu.develop.util.EncodingDecodingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author 志阳
 * @create 2019-08-26-16:49
 */
@Service
public class AddKnowledgeServiceImpl implements AddKnowledgeService {
    public static final String PhotoType = "图片";
    public static final String VideoType = "video";
    public static final String DocumentType = "Office";
    public static final String PDFType = "PDF";
    public static final String FlashType = "flash";
    public static final String OSGJSType = "压缩包";
    public static final String SimulateType = "仿真";
    public static final String QuesType = "题库";
    public static final String ExamType = "考试";
    public static final String customType = "自定义";
    public static final String HtmlType = "html";

    public static final String ModelType = "模型";
    public static final String ViewType = "场景";
    public static final String ChartletType = "贴图";
    public static final String VoiceType = "声音";

    public static final String PhotoImage = "../../../Source/imgicon/仿真.png";
    public static final String videoImage = "../../../Source/imgicon/仿真.png";
    public static final String DocumentImage = "../../../Source/imgicon/仿真.png";
    public static final String PDFImage = "../../../Source/imgicon/仿真.png";
    public static final String FlashImage = "../../../Source/imgicon/仿真.png";
    public static final String OSGJSImage = "../../../Source/imgicon/仿真.png";
    public static final String SimulateImage = "../../../Source/imgicon/仿真.png";
    public static final String QuesImage = "../../../Source/imgicon/仿真.png";
    public static final String customImage = "../../../Source/imgicon/仿真.png";
    public static final String HtmlImage = "../../../Source/imgicon/仿真.png";

    public static final String ModelImage = "../../../Source/imgicon/仿真.png";
    public static final String ViewImage = "../../../Source/imgicon/仿真.png";

    //图标
    public static final String ImageIcon = "../../../Source/imgicon/tag_orange.png";
    /**
     * 上传文件保存的路径
     */
    public static final String filePath = "";

    @Autowired
    public AddKnowledgeMapper addKnowledgeMapper;
    @Autowired
    public SubjectTreeMapper subjectTreeMapper;


    //复制方法
    public static void copy(String src, String des, String id) throws Exception {
        //初始化文件复制
        File file1 = new File(src);
        //把文件里面内容放进数组
        File[] fs = file1.listFiles();
        //初始化文件粘贴
        File file2 = new File(des);
        //判断是否有这个文件有不管没有创建
        if (!file2.exists()) {
            boolean str = file2.mkdirs();
            System.out.println(str);
        }
        //遍历文件及文件夹
        for (File f : fs) {
            if (f.isFile()) {
                System.out.println(f.getPath());
                //文件后缀名
                String suffix = f.getName().substring(f.getName().lastIndexOf(".") + 1);
                //||suffix.equals("jpg")||suffix.equals("png")暂时不改贴图的.因为模型或场景中均有图片.
                if (suffix.equals("exm") || suffix.equals("EXM") || suffix.equals("tdc") || suffix.equals("tdb")
                        || suffix.equals("mp3") || suffix.equals("mp4")) {
                    //文件
                    fileCopy(f.getPath(), des + "\\" + id + "." + suffix); //调用文件拷贝的方法
                    System.out.println(des + "\\" + id + "." + suffix);
                } else {
                    //文件
                    fileCopy(f.getPath(), des + "\\" + f.getName()); //调用文件拷贝的方法
                }
            } else if (f.isDirectory()) {
                //文件夹
                copy(f.getPath(), des + "\\" + f.getName(), id);//继续调用复制方法      递归的地方,自己调用自己的方法,就可以复制文件夹的文件夹了
            }
        }

    }

    /**
     * 文件复制的具体方法
     */
    private static void fileCopy(String src, String des) throws Exception {
        //io流固定格式
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(des));
        int i = -1;//记录获取长度
        byte[] bt = new byte[2014];//缓冲区
        while ((i = bis.read(bt)) != -1) {
            bos.write(bt, 0, i);
        }
        bis.close();
        bos.close();
        //关闭流
    }


    /*
     * 根据修改来修改模型或者场景以及知识点和知识点内容的名字
     * */
    public int updateKnowledgetConName(String id, String name) {

        int count = addKnowledgeMapper.updateKnowledgeContent4(id, name);
        return count;
    }

    @Override
    public Knowlegcontent getKnowlegContentName(String id) {

        Knowlegcontent knowlegcontent = addKnowledgeMapper.getKnowlegContentName(id);
        return knowlegcontent;
    }

    /**
     * 添加最后一个节点
     */
    public void addLastNode2(String treeId, String clickNodeId, String userKey) {

        Knowledges knowledges = new Knowledges();
        String NodeId = UUID.randomUUID().toString();
        knowledges.setId(NodeId);
        knowledges.setContent("新建目录");
        knowledges.setParentKnowledge(parentNodeId(clickNodeId, treeId));
        knowledges.setSubjectTree_Id(treeId);
        knowledges.setImageIcons(ImageIcon);
        knowledges.setKnowledgecontentId("00000000-0000-0000-0000-000000000000");
        knowledges.setBeforCondition("<root><beforesee></beforesee><userkey></userkey><grades></grades></root>");
        knowledges.setPreKnowledge(lastNodeId(treeId, clickNodeId));
        knowledges.setUserKey(userKey);
        knowledges.setCheckDel("0");
        addKnowledgeMapper.addLastNode2(knowledges);
    }

    @Override
    public String addUploading(Knowlegcontent knowlegcontent, String contentType, String fileName, String treeId, String clickNodeId, MultipartFile file) throws IOException {

        //末尾添加节点
        addLastNode2(treeId, clickNodeId, knowlegcontent.getUserKey());
        //截取最后一个.结尾的后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String Id = UUID.randomUUID().toString();
        //拼接nmae
        String nmae = Id + suffix;
        //设置属性
        knowlegcontent.setNmae(nmae);
        knowlegcontent.setId(Id);
        knowlegcontent.setKnowledge_Id(lastNodeId(treeId, clickNodeId));
        knowlegcontent.setOrder(addKnowledgeMapper.getMaxOrder() + 1);
        knowlegcontent.setCheckDel("0");
        String subjectId = addKnowledgeMapper.getSubjectId(treeId);
        if ("addPhoto".equals(contentType)) {
            knowlegcontent.setType(PhotoType);
            knowlegcontent.setImageContentIcons(PhotoImage);
        } else if ("addVideo".equals(contentType)) {
            knowlegcontent.setType(VideoType);
            knowlegcontent.setImageContentIcons(videoImage);
        } else if ("addDocument".equals(contentType)) {
            knowlegcontent.setType(DocumentType);
            knowlegcontent.setImageContentIcons(DocumentImage);
        } else if ("addPDF".equals(contentType)) {
            knowlegcontent.setType(PDFType);
            knowlegcontent.setImageContentIcons(PDFImage);
        } else if ("addFlash".equals(contentType)) {
            knowlegcontent.setType(FlashType);
            knowlegcontent.setImageContentIcons(FlashImage);
        } else if ("addOSGJS".equals(contentType)) {
            knowlegcontent.setType(OSGJSType);
            knowlegcontent.setImageContentIcons(OSGJSImage);
        } else {
            //System.out.println("类型未匹配!");
        }
        //上传数据
        addKnowledgeMapper.addUploading(knowlegcontent);
        addKnowledgeMapper.alterKnowledgeContent_Id(knowlegcontent);
        saveUploadFile(file, subjectId, treeId, filePath, knowlegcontent);
        String result = "ok";
        return result;
    }

    /**
     * 添加最后一个节点
     */
    public void addLastNode(String treeId, String clickNodeId, String userKey) {

        Knowledges knowledges = new Knowledges();
        String NodeId = UUID.randomUUID().toString();
        knowledges.setId(NodeId);
        knowledges.setContent("新建目录");
        knowledges.setParentKnowledge(parentNodeId(clickNodeId, treeId));
        knowledges.setSubjectTree_Id(treeId);
        knowledges.setImageIcons(ImageIcon);
        knowledges.setKnowledgecontentId("00000000-0000-0000-0000-000000000000");
        knowledges.setBeforCondition("<root><beforesee></beforesee><userkey></userkey><grades></grades></root>");
        knowledges.setPreKnowledge(lastNodeId(treeId, clickNodeId));
        knowledges.setUserKey(userKey);
        addKnowledgeMapper.addLastNode(knowledges);
    }

    /**
     * 根据树id
     * 获取最后一个节点的id
     *
     * @return
     */
    public String lastNodeId(String treeId, String clickNodeId) {

        //获取父节点id
        String rootId = parentNodeId(clickNodeId, treeId);
        //获取大节点数
        int nodeNum = subjectTreeMapper.seleNum(treeId, rootId);
        //找到第一个大节点
        Knowledges knowledges = subjectTreeMapper.seleFirst(treeId, rootId);
        if (knowledges != null) {
            String firstId = knowledges.getId();
            for (int i = 1; i < nodeNum; i++) {
                String nextId = addKnowledgeMapper.getNextNodeId(firstId);
                firstId = nextId;
            }
            return firstId;
        }
        return null;
    }

    /**
     * 设置父节点的id
     *
     * @param clickNodeId 该点击节点的id
     * @param treeId      树id
     * @return 父节点id
     */
    public String parentNodeId(String clickNodeId, String treeId) {

        if (clickNodeId.length() > 0) {
            return clickNodeId;
        }
        return subjectTreeMapper.getSubjectRootId(treeId);
    }

    /**
     * 判断该id是否为知识点id
     *
     * @param nodeId 节点id
     * @return
     */
    @Override
    public String isKnowledgeContent(String nodeId) {

        String isNull = addKnowledgeMapper.isKnowledgeContent(nodeId);
        if (isNull == null) {
            return "succeed";
        }
        return "ok";
    }

    /**
     * 添加仿真模型
     */
    @Override
    public String addSimulateModel(Knowlegcontent knowledgecontent, String treeId, String nodeId) {

        addLastNode(treeId, nodeId, knowledgecontent.getUserKey());
        knowledgecontent.setId(UUID.randomUUID().toString());
        knowledgecontent.setIntroduce(null);
        knowledgecontent.setImageContentIcons(SimulateImage);
        knowledgecontent.setKnowledge_Id(lastNodeId(treeId, nodeId));
        //knowledgecontent.setType(SimulateType);
        knowledgecontent.setOrder(addKnowledgeMapper.getMaxOrder() + 1);
        addKnowledgeMapper.alterKnowledgeContent_Id(knowledgecontent);
        addKnowledgeMapper.addSimulateModel(knowledgecontent);
        return knowledgecontent.getId();
    }

    /**
     * 添加题库模型
     */
    @Override
    public String addQuesModel(Knowlegcontent knowlegcontent, String treeId, String nodeId) {

        addLastNode(treeId, nodeId, knowlegcontent.getUserKey());
        knowlegcontent.setId(UUID.randomUUID().toString());
        knowlegcontent.setIntroduce(null);
        knowlegcontent.setImageContentIcons(QuesImage);
        knowlegcontent.setKnowledge_Id(lastNodeId(treeId, nodeId));
        knowlegcontent.setType(QuesType);
        knowlegcontent.setOrder(addKnowledgeMapper.getMaxOrder() + 1);
        addKnowledgeMapper.addQuesModel(knowlegcontent);
        addKnowledgeMapper.alterKnowledgeContent_Id(knowlegcontent);
        return knowlegcontent.getId();
    }

    @Override
    public String addCustomModel(Knowlegcontent knowlegcontent, String treeId, String nodeId) {

        addLastNode(treeId, nodeId, knowlegcontent.getUserKey());
        knowlegcontent.setId(UUID.randomUUID().toString());
        knowlegcontent.setIntroduce(null);
        knowlegcontent.setImageContentIcons(customImage);
        knowlegcontent.setKnowledge_Id(lastNodeId(treeId, nodeId));
        knowlegcontent.setType(customType);
        knowlegcontent.setOrder(addKnowledgeMapper.getMaxOrder() + 1);
        addKnowledgeMapper.addCustomModel(knowlegcontent);
        addKnowledgeMapper.alterKnowledgeContent_Id(knowlegcontent);
        return knowlegcontent.getId();
    }


    public void saveUploadFile(MultipartFile mf, String subjectId, String treeId, String filePath, Knowlegcontent knowlegcontent) throws IOException {

        //File file = new File(filePath+File.separator+knowlegcontent.getType());
        File file = new File(File.separator + knowlegcontent.getType());
        InputStream is = mf.getInputStream();
//		if(!file.exists()){
//			//创建多级目录
//			file.mkdirs();
//		}

        //在指定目录下添加文件
        File writeFile = new File("");
        if (knowlegcontent.getType().equals("PDF")) {
            //本机路径  wamp  www
            // writeFile = new File("D:/wamp/www/newTdu/PDF/"+knowlegcontent.getNmae());
            //服务器路径
            writeFile = new File("/www/wwwroot/file.tduvr.club/pdf/" + knowlegcontent.getNmae());
        } else if (knowlegcontent.getType().equals("video")) {
            //本机路径
            //	writeFile = new File("D:/wamp/www/newTdu/video/"+knowlegcontent.getNmae());
            //服务器路径
            writeFile = new File("/www/wwwroot/file.tduvr.club/video/" + knowlegcontent.getNmae());
        } else if (knowlegcontent.getType().equals(DocumentType)) {
            //本机路径
            //writeFile = new File("D:/wamp/www/newTdu//office/"+knowlegcontent.getNmae());
            //服务器路径
            writeFile = new File("/www/wwwroot/file.tduvr.club/office/" + knowlegcontent.getNmae());
        }
        if (!writeFile.exists()) {
            //创建指定文件
            writeFile.createNewFile();
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
//				BufferedReader br=new BufferedReader(new InputStreamReader(is,"utf-8"));
//				PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(writeFile),"utf-8"));
//				String line = null;
//				while((line=br.readLine())!=null){
//					pw.write(line);
//				}
//				pw.flush();
//				br.close();
//				pw.close();
        }
    }

    @Override
    public String addHtmlEditorContent(Knowlegcontent knowlegcontent, String htmlcontent, String treeId, String nodeId) {

        addLastNode(treeId, nodeId, knowlegcontent.getUserKey());
        knowlegcontent.setId(UUID.randomUUID().toString());
        knowlegcontent.setType(HtmlType);
        knowlegcontent.setKnowledge_Id(lastNodeId(treeId, nodeId));
        knowlegcontent.setImageContentIcons(HtmlImage);
        knowlegcontent.setOrder(addKnowledgeMapper.getMaxOrder() + 1);
        knowlegcontent.setNmae(htmlcontent);
        addKnowledgeMapper.addHtmlEditorContent(knowlegcontent);
        addKnowledgeMapper.alterKnowledgeContent_Id(knowlegcontent);
        return knowlegcontent.getId();
    }

    @Override
    public String getKnowIdbycontentId(String contentId) {

        String knowId = addKnowledgeMapper.getKnowIdbycontentId(contentId);
        return knowId;
    }

    /**
     * 编辑pdf上传
     */
    public String editorLoading(Knowlegcontent knowlegcontent, String contentType, String fileName, String treeId, String clickNodeId, MultipartFile file) throws IOException {

        //截取最后一个.结尾的后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //获取改变前的属性
        String preNmae = addKnowledgeMapper.getKnowNmae(clickNodeId);
        //拼接nmae
        String nmae = UUID.randomUUID().toString() + suffix;
        //设置属性
        knowlegcontent.setNmae(nmae);
        knowlegcontent.setId(UUID.randomUUID().toString());
        knowlegcontent.setKnowledge_Id(lastNodeId(treeId, clickNodeId));
        knowlegcontent.setOrder(addKnowledgeMapper.getMaxOrder() + 1);
        String subjectId = addKnowledgeMapper.getSubjectId(treeId);
        //更改数据库名字

        addKnowledgeMapper.updataName(nmae, clickNodeId);


        if ("addPhoto".equals(contentType)) {
            knowlegcontent.setType(PhotoType);
            knowlegcontent.setImageContentIcons(PhotoImage);
        } else if ("addVideo".equals(contentType)) {
            knowlegcontent.setType(VideoType);
            knowlegcontent.setImageContentIcons(videoImage);
        } else if ("addDocument".equals(contentType)) {
            knowlegcontent.setType(DocumentType);
            knowlegcontent.setImageContentIcons(DocumentImage);
        } else if ("addPDF".equals(contentType)) {
            knowlegcontent.setType(PDFType);
            knowlegcontent.setImageContentIcons(PDFImage);
        } else if ("addFlash".equals(contentType)) {
            knowlegcontent.setType(FlashType);
            knowlegcontent.setImageContentIcons(FlashImage);
        } else if ("addOSGJS".equals(contentType)) {
            knowlegcontent.setType(OSGJSType);
            knowlegcontent.setImageContentIcons(OSGJSImage);
        } else {
            //System.out.println("类型未匹配!");
        }
        //上传数据
        updatePdf(file, subjectId, treeId, filePath, knowlegcontent, preNmae);
        String result = "ok";
        return result;
    }

    @Override
    public void updatePdf(MultipartFile mf, String subjectId, String treeId, String filePath,
                          Knowlegcontent knowlegcontent, String preNmae) throws IOException {

        //File file = new File(filePath+File.separator+knowlegcontent.getType());
        InputStream is = mf.getInputStream();
        File delFile = new File("");
        File writeFile = new File("");
        if (knowlegcontent.getType().equals("PDF")) {
            delFile = new File("D:/working/TDuClub/develop/QZ/PDF/" + preNmae);
            writeFile = new File("D:/working/TDuClub/develop/QZ/PDF/" + knowlegcontent.getNmae());
            //服务器路径
//			delFile = new File("C:/xampp/htdocs/diesel/QZ/PDF/"+preNmae);
//			writeFile = new File("C:/xampp/htdocs/diesel/QZ/PDF/"+knowlegcontent.getNmae());
        } else if (knowlegcontent.getType().equals("video")) {
            delFile = new File("D:/working/TDuClub/develop/QZ/video/" + preNmae);
            writeFile = new File("D:/working/TDuClub/develop/QZ/video/" + knowlegcontent.getNmae());
            //服务器路径
//			delFile = new File("D:/xampp/htdocs/diesel/QZ/video/"+preNmae);
//			writeFile = new File("C:/xampp/htdocs/diesel/QZ/video/"+knowlegcontent.getNmae());
        } else if (knowlegcontent.getType().equals(DocumentType)) {
            delFile = new File("D:/working/TDuClub/develop/QZ/office/" + preNmae);//D:/workspaces/diesel/QZ/office/
            writeFile = new File("D:/working/TDuClub/develop/QZ/office/" + knowlegcontent.getNmae());//D:\wamp\www\diesel\QZ\office
            //服务器路径
//			delFile = new File("C:/xampp/htdocs/diesel/QZ/office/"+preNmae);
//			writeFile = new File("C:/xampp/htdocs/diesel/QZ/office/"+knowlegcontent.getNmae());
        }
        if (delFile.isFile()) {
            delFile.delete();
        }
        //在指定目录下添加文件
        if (!writeFile.exists()) {
            //创建指定文件
            writeFile.createNewFile();
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
        }
    }

    @Override
    public String addExamModel(Knowlegcontent knowlegcontent, String treeId, String nodeId) {

        addLastNode(treeId, nodeId, knowlegcontent.getUserKey());
        knowlegcontent.setId(UUID.randomUUID().toString());
        knowlegcontent.setIntroduce(null);
        knowlegcontent.setImageContentIcons(QuesImage);
        knowlegcontent.setKnowledge_Id(lastNodeId(treeId, nodeId));
        knowlegcontent.setType(ExamType);
        knowlegcontent.setOrder(addKnowledgeMapper.getMaxOrder() + 1);
        addKnowledgeMapper.addQuesModel(knowlegcontent);
        addKnowledgeMapper.alterKnowledgeContent_Id(knowlegcontent);
        return knowlegcontent.getId();
    }

    @Override
    public Knowlegcontent getExamName(String examId) {


        return addKnowledgeMapper.getExamName(examId);
    }


    public Map<String, List<String>> setVersion(String path, String name, List<String> nameList, List<String> fileList) {
        // TODO Auto-generated method stub
        Map<String, List<String>> versionMap = new HashMap<String, List<String>>();
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null != files) {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        setVersion(file2.getAbsolutePath(), file2.getName(), nameList, fileList);
                    } else {
                        if (name == null || name == "") {
                            if (file2.getName().equals("version.zip") || file2.getName().equals("version.txt")) {

                            } else {
                                fileList.add(file2.getName());
                                nameList.add(EncodingDecodingUtil.MD5Encode(file2.getName(), "utf8"));
                            }
                        } else {
                            fileList.add(name + "\\" + file2.getName());
                            nameList.add(EncodingDecodingUtil.MD5Encode(name + "\\" + file2.getName(), "utf8"));
                        }
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        versionMap.put("fileList", fileList);
        versionMap.put("nameList", nameList);
        return versionMap;
    }


}
