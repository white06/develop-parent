package com.tdu.develop.models.service.impl;

import com.tdu.develop.models.mapper.DevelopModelMapper;
import com.tdu.develop.models.mapper.DevelopSceneMapper;
import com.tdu.develop.models.pojo.Modelcontents;
import com.tdu.develop.models.pojo.Models;
import com.tdu.develop.models.pojo.Scenes;
import com.tdu.develop.models.service.DevelopModelService;
import com.tdu.develop.resource.mapper.SubjectTreeMapper;
import com.tdu.develop.user.mapper.SubjectMapper;
import com.tdu.develop.user.mapper.UsersMapper;
import com.tdu.develop.util.EncodingDecodingUtil;
import com.tdu.develop.util.print;
import com.tdu.develop.util.zip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author 志阳
 * @create 2019-08-15-11:38
 */
@Service
public class DevelopModelServiceImpl implements DevelopModelService {
    @Autowired
    DevelopModelMapper developModelMapper;
    @Autowired
    DevelopSceneMapper developSceneMapper;
    @Autowired
    SubjectMapper subjectMapper;

    @Autowired
    SubjectTreeMapper subjectTreeMapper;

    @Autowired
    UsersMapper usersMapper;

    public static final String ModelImage = "../../../Source/imgicon/仿真.png";
    public static final String ModelType = "模型";
    //图标
    public static final String ImageIcon = "../../../Source/imgicon/tag_orange.png";


    public String getFirstModelId(String rootId, String userId) {

        return developModelMapper.getFirstModelId(rootId, userId);
    }

    /**
     * 获取模型rootId的 subtreeid
     */
    public String getSubjectModelsRootId(String treeId) {

        return subjectTreeMapper.getSubjectModelsRootId2(treeId);
    }

    /**
     * 获取rootId
     *
     * @param treeId
     * @return
     */
    public String gettModelsRootId(String treeId) {

        return developModelMapper.getSubjectModelsRootId(treeId);
    }

    /**
     * knowledgecontents的type属性
     */
    public static final String simulateType = "仿真";

    /**
     * 通过模型Id获取contents内容
     */
    public Modelcontents getModelcontentsInfos(String id) {
        return developModelMapper.getModelcontentsInfos(id);
    }

    /**
     * 删除模型(含文件夹)
     *
     * @param id
     * @return
     */
    public int delmodels(String id, String time) {

        return developModelMapper.delmodels(id, time);
    }

    /**
     * 删除模型contents
     *
     * @param id
     * @return
     */
    public int delmodelContets(String id, String time) {

        return developModelMapper.delmodelContets(id, time);
    }

    /**
     * 获取模型信息
     *
     * @param modelId
     * @return
     */
    public List<Models> getModelscontents(String modelId, String userId) {
        // TODO 获取模型信息数据

        List<Models> modelList = developModelMapper.getModelcontents(modelId, userId);
        return modelList;
    }

    public boolean updateModelName(String id, String name) {
        int count1 = developModelMapper.updateModelName(id, name);
        int count2 = developModelMapper.updateModelContentName(id, name);
        boolean flag = false;
        if (count1 != 0 && count2 != 0) {
            flag = true;
        }
        return flag;
    }


    public List<Models> getFirstModel(String subUpId, String rootId, String userId) {
        // TODO 获取首层目录

        return developModelMapper.getFisModels(subUpId, rootId, userId);
    }


    public List<Models> getSubModel(String parentId, String subUpId, String userId) {
        // TODO 获取第二层目录

        return developModelMapper.getSubModels(parentId, subUpId, userId);
    }


    public void addSubModels(String parentId, String content, String subUpId, String userId) {
        // TODO 添加模型参数

        String id = UUID.randomUUID().toString();
        String preModel = lastModelsNodeId(subUpId, parentId, userId);
        developModelMapper.addSubModels(id, content, parentId, preModel, subUpId, userId);
    }


    public String getSubId(String subId, String treeName) {
        // TODO Auto-generated method stub

        return subjectTreeMapper.getSubId(subId, treeName);
    }


    public void upFirModelTree(String id, String name) {
        // TODO Auto-generated method stub

        developModelMapper.upFirModelTree(id, name);
    }


    public void deleModel(String Id) {
        // TODO Auto-generated method stub

        developModelMapper.deleteModel(Id);
    }


    public void addFisModel(String Id, String subId, String content, String userId) {
        // TODO Auto-generated method stub

        String rooyId = developModelMapper.getRootId(subId);
        String preModelId = lastModelsNodeId(subId, rooyId, userId);
        developModelMapper.addFisModels2(Id, content, rooyId, preModelId, subId, userId);
        //developModelMapper.addFisModels(Id, content, rooyId, subId);
    }


    public List<Modelcontents> getModelcontents(String modelId, String userId) {
        // TODO 获取模型信息数据

        List<Models> modelList = developModelMapper.getModelcontents(modelId, userId);
        List<Modelcontents> modelcontentsList = new ArrayList<Modelcontents>();

        List<Modelcontents> getKsList = new ArrayList<Modelcontents>();

        for (int i = 0; i < modelList.size(); i++) {
            Modelcontents mod = developModelMapper.getModelcontentsInfos(modelList.get(i).getId());
            if (mod != null) {
                modelcontentsList.add(mod);
            }
        }

        for (Modelcontents modelcontents : modelcontentsList) {
            if (modelcontents.getCheckDel() == 0) {
                getKsList.add(modelcontents);
            }
        }
        return getKsList;
    }


    public String getSubId1(String modelId) {
        // TODO 获取模型subiD


        return developModelMapper.getSubId1(modelId);
    }

    /*
     * 新增主页模型
     * */

    public String AddModelsContent(Modelcontents modelcontents, String treeId, String nodeId, Models model) {
        //System.out.println(" treeId："+treeId+" nodeId："+nodeId);
        addLastModelsNode(treeId, nodeId, modelcontents.getUserKey());

        String id = UUID.randomUUID().toString();

        modelcontents.setNmae(id + ".tdb");

        modelcontents.setId(id);
        modelcontents.setIntroduce(null);
        modelcontents.setImageContentIcons(ModelImage);
        modelcontents.setModel_Id(lastModelsNodeId(treeId, nodeId, modelcontents.getUserKey()));
        modelcontents.setType(ModelType);
        modelcontents.setOrder(developModelMapper.getModelsMaxOrder() + 1);
        modelcontents.setUrl("D:\\working\\TDuClub\\TDu\\Data\\3D");
        developModelMapper.addModelsModel(modelcontents);
        developModelMapper.alterModelContent_Id(modelcontents);
        System.out.println(modelcontents.getModel_Id());
        String Name = modelcontents.getNmae();


        /*源文件路径  D:\wamp\www\develop\QZ\modelExample
         * 目標文件路徑  D:\working\TDuClub\TDu\Data\3D\Model
         * 服务器路径   D:\\working\\TDuClub\\develop\\QZ\\modelExample
         * */

        //源文件路徑

        //服務器路徑
        String path = "\\home\\working\\https://www.tduvr.club\\tdu\\modelExample";
        //String path="D:\\wamp\\www\\newTdu\\modelExample";

        //服務器路徑
        String mubiao = "\\home\\working\\tdu.tduvr.club\\Data\\3D\\Model\\" + modelcontents.getUserKey() + "\\" + id;
        //String mubiao="D:\\working\\TDuClub\\TDu\\Data\\3D\\Model\\"+modelcontents.getUserKey()+"\\"+id;

        File files = new File(mubiao);
        if (!files.exists()) {
            boolean str = files.mkdirs();
            System.out.println(str);
        }
        try {
            copy(path, mubiao, id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //String ContactKey=request.getParameter("modelOrView");
        //String type=request.getParameter("type");
        //暂无
        //model.setContactKey(ContactKey);
		/*model.setId(id);
		model.setName(Name);
		model.setType("模型");
		//C:\\xampp\\htdocs4\\TDu\\Data
		model.setUrl("D:\\working\\TDuClub\\TDu\\Data\\3D");
		model.setContent(modelcontents.getModel_Id());
		if(nodeId==""){
			model.setContactKey("fc2b74a1-e131-41b7-9787-c950f72e8626");
		}else{
			model.setContactKey(nodeId);
		}
		addModel(model);*/

        return modelcontents.getId();
    }

    /*
     * 新增主页模型  New  文件夹上传
     * */

    public String AddModelsContentFile(Modelcontents modelcontents, String treeId, String nodeId, Models model, MultipartFile[] file) {
        //System.out.println(" treeId："+treeId+" nodeId："+nodeId);
        addLastModelsNode(treeId, nodeId, modelcontents.getUserKey());

        String id = UUID.randomUUID().toString();

        modelcontents.setNmae(id + ".tdb");

        modelcontents.setId(id);
        modelcontents.setIntroduce(null);
        modelcontents.setImageContentIcons(ModelImage);
        modelcontents.setModel_Id(lastModelsNodeId(treeId, nodeId, modelcontents.getUserKey()));
        modelcontents.setType(ModelType);
        modelcontents.setOrder(developModelMapper.getModelsMaxOrder() + 1);
        modelcontents.setUrl("D:\\working\\TDuClub\\TDu\\Data\\3D");
        developModelMapper.addModelsModel(modelcontents);
        developModelMapper.alterModelContent_Id(modelcontents);
        System.out.println(modelcontents.getModel_Id());
        String Name = modelcontents.getNmae();

        //源文件路徑

        //服務器路徑
        //String path="\\home\\working\\https://www.tduvr.club\\tdu\\modelExample";
        //String path="D:\\wamp\\www\\newTdu\\modelExample";

        //服務器路徑
        //String mubiao="\\home\\working\\tdu.tduvr.club\\Data\\3D\\Model\\"+modelcontents.getUserKey()+"\\"+id;
        //String mubiao="D:\\working\\TDuClub\\TDu\\Data\\3D\\Model\\"+modelcontents.getUserKey()+"\\"+id;
        //新
        //  String mubiao="D:/wamp/www/Data/3D/Model/"+modelcontents.getUserKey()+"/"+id;
        String mubiao = "/www/wwwroot/tdu.tduvr.club/Data/3D/Model/" + modelcontents.getUserKey() + "/" + id;


        for (MultipartFile f : file) {
            File file1;
            String name = "";
            try {
                System.out.println(f.getOriginalFilename() + "   iii         --------");
                String path = f.getOriginalFilename();
                String suffix = path.substring(path.lastIndexOf("."));
                //sence  /  model ID
                String pp = id;
                String realPath = pp;

                if (suffix.equals(".tdb") || suffix.equals(".tdc")) {
                    modelcontents.setNmae(id + suffix);
                }

                System.out.println(" path :" + path);
                if (suffix.equals(".tdb") || suffix.equals(".tdc") || suffix.equals(".EXM") || suffix.equals(".exm") || suffix.equals(".glb") || suffix.equals(".GLB")) {
                    System.out.println(" suffix :" + suffix);
                    realPath = pp + "/" + "" + id + "" + suffix;
                    System.out.println("  realPath  :" + realPath);
                } else {
                    String[] strArr = path.split("/");
                    System.out.println(strArr.length); //这里输出3
                    for (int i = 0; i < strArr.length; ++i) {
                        System.out.println(strArr[i]);//这里输出a b c
                        if (i != 0) {
                            realPath = realPath + "/" + strArr[i];
                        }
                    }
                    System.out.println("  realPath  :" + realPath);
                }
                updatePdf(f, realPath, modelcontents.getUserKey(), id);
                System.out.println("sssss   ");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        String name = "";
        List<String> nList = new ArrayList<String>();
        List<String> fList = new ArrayList<String>();
        Map<String, List<String>> versionMap = setVersion(mubiao, name, nList, fList);
        List<String> nameList = versionMap.get("nameList");
        List<String> fileList = versionMap.get("fileList");
        try {
            setVersionFile(nameList, fileList, mubiao);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelcontents.getId();
    }


    public void updatePdf(MultipartFile mf, String realPath, String userKey, String modelId) throws IOException {
        InputStream is = mf.getInputStream();
        StringBuffer fileBuf = new StringBuffer();

        String[] strArr = realPath.split("/");
        String wenjianPath = "";
        System.out.println(strArr.length); //这里输出3
        for (int i = 0; i < strArr.length; ++i) {
            System.out.println(strArr[i]);//这里输出a b c
            if (i != (strArr.length - 1)) {
                wenjianPath = wenjianPath + "/" + strArr[i];
            }
        }
        //String filePar = "D:/working/TDuClub/TDu/Data/3D/Model/"+userKey+wenjianPath;// 文件夹路径
        //String filePar = "\\home\\working\\tdu.tduvr.club\\Data\\3D\\Model\\"+userKey+wenjianPath;// 文件夹路径

        //新
        //  String filePar = "D:/wamp/www/Data/3D/Model/"+userKey+wenjianPath;
        String filePar = "/www/wwwroot/tdu.tduvr.club/Data/3D/Model/" + userKey + wenjianPath;

        /*File myPath = new File( filePar );
        if ( !myPath.exists()){//若此目录不存在，则创建之
            myPath.setWritable(true,false);
            boolean mkdirs = myPath.mkdirs();
            System.out.println("创建目录返回结果："+mkdirs);
            System.out.println("创建文件夹路径为："+ filePar);
        }*/
        if (!new File(filePar).exists()) {
            System.out.println(" fileUrlPath :" + filePar);
            System.out.println(" new File(trueUrl).exists() :" + new File(filePar).exists());
            boolean mkdirs = new File(filePar).mkdirs();
            System.out.println("创建目录返回结果：" + mkdirs);
            System.out.println("创建文件夹路径为：" + filePar);
        }

        // 文件夹路径存在的情况下

        //String filename = "D:/working/TDuClub/TDu/Data/3D/Model/"+""+userKey+""+"/"+realPath;;// 文件名

        //新
        //  String filename = "D:/wamp/www/Data/3D/Model/"+""+userKey+""+"/"+realPath;;// 文件名
        String filename = "/www/wwwroot/tdu.tduvr.club/Data/3D/Model/" + "" + userKey + "" + "/" + realPath;
        ;// 文件名

        //String filename = "\\home\\working\\tdu.tduvr.club\\Data\\3D\\Model\\"+""+userKey+""+"\\"+realPath;;// 文件名
//String name = "D:/working/TDuClub/TDu/Data/3D/Model/"+""+userKey+""+"/"+realPath;
        //新
        //String name = "D:/wamp/www/Data/3D/Model/"+""+userKey+""+"/"+realPath;
        String name = "/www/wwwroot/tdu.tduvr.club/Data/3D/Model/" + "" + userKey + "" + "/" + realPath;
        String creatFileName = unicodeToUtf8(name);
        File writeFile = new File(creatFileName);
        //File writeFile=new File("\\home\\working\\tdu.tduvr.club\\Data\\3D\\Model\\"+""+userKey+""+"\\"+realPath);
        System.out.println(" filename : " + filename);
        System.out.println(Charset.defaultCharset());
        try {
            FileWriter fw = new FileWriter(filename, true);// filePar + "\\" + filename,true
            BufferedInputStream bis = new BufferedInputStream(is);
            // BufferedReader bReader  = new BufferedReader (new InputStreamReader(is,"UTF-8"));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(writeFile));
            //BufferedWriter bWriter = new BufferedWriter(new OutputStreamWriter (new FileOutputStream (filename), "UTF-8"));

            /*// 一行一行的写
            String strLine = null;
            while ((strLine = bReader.readLine()) != null) {
                bWriter.flush();
                bWriter.write(strLine);
                // 记得换行
                bWriter.newLine();
            }*/
            byte[] flash = new byte[1024];
            //String str = new String(flash, StandardCharsets.UTF_8);
            //flash = str.getBytes(StandardCharsets.UTF_8);
            int len = 0;
            while (-1 != (len = bis.read(flash))) {
                bos.write(flash, 0, len);
            }
            //bReader.close();
            bos.flush();
            bis.close();
            bos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String unicodeToUtf8(String s) throws UnsupportedEncodingException {
        return new String(s.getBytes("utf-8"), "utf-8");
    }

    /**
     * 添加最后一个节点
     *
     * @param treeId
     * @param clickNodeId
     */
    public void addLastModelsNode(String treeId, String clickNodeId, String userId) {

        Models models = new Models();
        String NodeId = UUID.randomUUID().toString();
        models.setId(NodeId);
        models.setContent("新建目录");
        models.setParentModel(parentModelsNodeId(clickNodeId, treeId));
        models.setSubjectTree_Id(treeId);
        models.setImageIcons(ImageIcon);
        models.setModelContentId("00000000-0000-0000-0000-000000000000");
        models.setBeforCondition("<root><beforesee></beforesee><userkey></userkey><grades></grades></root>");
        models.setPreModel(lastModelsNodeId(treeId, clickNodeId, userId));
        models.setUserKey(userId);
        developModelMapper.addLastModelsNode(models);
    }

    /**
     * 设置父节点的id
     *
     * @param clickNodeId 该点击节点的id
     * @param treeId      树id
     * @return 父节点id
     */
    public String parentModelsNodeId(String clickNodeId, String treeId) {

        if (clickNodeId.length() > 0) {
            return clickNodeId;
        }
        return developModelMapper.getSubjectModelsRootId(treeId);
    }

    /**
     * 根据树id
     * 获取最后一个节点的id
     *
     * @return
     */
    public String lastModelsNodeId(String treeId, String clickNodeId, String userId) {

        //获取父节点id
        String rootId = parentModelsNodeId(clickNodeId, treeId);
        //获取大节点数
        int nodeNum = developModelMapper.seleModelsNum(treeId, rootId, userId);
        //找到第一个大节点
        Models models = developModelMapper.seleModelsFirst(treeId, rootId, userId);
        if (models != null) {
            String firstId = models.getId();
            for (int i = 1; i < nodeNum; i++) {
                String nextId = developModelMapper.getModelsNextNodeId(firstId);
                firstId = nextId;
            }
            return firstId;
        }
        return null;
    }

    //复制方法
    public void copy(String src, String des, String id) throws Exception {
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
                if (suffix.equals("EXM") || suffix.equals("tdc") || suffix.equals("tdb")) {
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
    public void fileCopy(String src, String des) throws Exception {
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

    /**
     * 删除模型
     *
     * @param treeNodeId
     * @param subjectId
     */
    public void removeModels(String treeNodeId, String subjectId) {

        List<Models> list = new ArrayList<Models>();
        Models models = new Models();
        //1.找到该节点的所有子类
        list = developModelMapper.getAllModelsclass(treeNodeId);
        //判断是否存在子文件或子目录
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                models = list.get(i);
                String id = models.getId();
                //判断该子类是内容还是目录
                String result = judgeModelsType(id);
                //对内容的处理
                deleteModelFile(id, subjectId);
                //对目录的处理
                deleteModelsDirectory(id, subjectId);
            }
            deleteModelsDirectory(treeNodeId, subjectId);
        } else {
            //对内容的处理
            models = developModelMapper.seleModelsKnow(treeNodeId);
            if (!models.getModelContentId().equals("00000000-0000-0000-0000-000000000000"))
                deleteModelFile(treeNodeId, subjectId);
            //对目录的处理
            deleteModelsDirectory(treeNodeId, subjectId);
        }
    }

    /**
     * 判断该子类是内容还是目录
     *
     * @param knowledgeId
     * @return "content" 内容
     * "directory" 目录
     */
    public String judgeModelsType(String knowledgeId) {
        Modelcontents modelcontents = developModelMapper.getFileModelsContent(knowledgeId);
        String result;
        if (modelcontents != null) {
            result = "content";
        } else {
            result = "directory";
        }
        return result;
    }

    /**
     * 对内容的处理
     *
     * @param knowledgeId Id (Knowledges)
     * @param subjectId
     */
    public void deleteModelFile(String knowledgeId, String subjectId) {

//        Modelcontents modelcontents =  developModelMapper.getFileModelsContent(knowledgeId);
//        //判断内容的类型进行删除
//        if(simulateType.equals(modelcontents.getType())||AddKnowledgeServiceImpl.HtmlType.equals(modelcontents.getType())||
//                AddKnowledgeServiceImpl.QuesType.equals(modelcontents.getType())||AddKnowledgeServiceImpl.customType.equals(modelcontents.getType())){
//            //删除内容
//            developModelMapper.deleteSimulateModels(modelcontents.getId());
//            alterModelsNextPreNodeId(modelcontents.getModel_Id());
//            //删除节点文件
//            int i = developModelMapper.deleteModelsKnowledges(modelcontents.getId());
//        }else{
//            //根据id获取树id
//            String treeId = developModelMapper.getModelsTreeId(modelcontents.getModel_Id());
//            String path = AddKnowledgeServiceImpl.filePath+ File.separator+subjectId+File.separator+treeId+File.separator+modelcontents.getType()+File.separator+modelcontents.getId();
//            File file = new File(path);
//            //删除内容文件
//            file.delete();
//            //删除内容
//            developModelMapper.deleteSimulateModels(modelcontents.getId());
//            alterModelsNextPreNodeId(modelcontents.getModel_Id());
//            //删除节点文件
//            int i = developModelMapper.deleteModelsKnowledges(modelcontents.getId());
//        }

    }

    /**
     * 根据删除节点id查询下一节点id并
     * 修改下一节点的上一节点id
     *
     * @param deletedNodeId 当前节点id
     */
    public void alterModelsNextPreNodeId(String deletedNodeId) {

        //获取下一节点
        String nextId = developModelMapper.getModelsNextNodeId(deletedNodeId);
        //判断是否有下一节点
        if (nextId != null) {
            //获取上一节点id
            String preId = developModelMapper.getModelsPreNodeId(deletedNodeId);
            //修改下一节点的上一节点id
            developModelMapper.alterModelsNextPreNodeId(preId, nextId);
        }
    }

    /**
     * 对目录的处理
     *
     * @param id        当前knowledges的Id
     * @param subjectId
     */
    public void deleteModelsDirectory(String id, String subjectId) {

        //判断该目录是否有子类
        if (developModelMapper.childModelsNum(id) > 0) {
            //遍历所有的子类
            for (int i = 0; i < developModelMapper.childModelsNum(id); i++) {
                //获取子类对象
                Models models = developModelMapper.getAllModelsclass(id).get(i);
                //判断子类是内容还是目录
                String result = judgeModelsType(models.getId());
                if ("content".equals(result)) {
                    alterModelsNextPreNodeId(models.getId());
                    deleteModelFile(models.getId(), subjectId);
                } else {
                    deleteModelsDirectory(models.getId(), subjectId);
                }
            }
        } else {
            alterModelsNextPreNodeId(id);
            //删除目录
            int i = developModelMapper.deleteModelsKnowledges(id);
        }
    }

    /*
     * 删除模型分类
     * */
    public int delModelContact(String id) {

        int count = developModelMapper.delModelContact(id);
        return count;
    }

    /*
     * 知识树删除模型/场景
     * */
    public int delModels2(String Id) {

        //modelMapper.addModel(model);
        return developModelMapper.delModels2(Id);
    }


    public List<Object> getTreeInfos(String subId, String content, String userId) {

        String rootId = "";
        if (content.equals("模型库")) {
            rootId = developModelMapper.getRootId(subId);
        } else if (content.equals("场景库")) {
            rootId = developSceneMapper.getRootId(subId);
        }

        List<Object> allList = new ArrayList<Object>();
        List<Object> adminList = new ArrayList<Object>();
        List<Object> roleList = new ArrayList<Object>();
        // TODO 获取首页树数据
        if (userId.equals("4d272f66-9dac-4b87-a2a1-22b6e5910779")) {
            adminList = getTreeInfosAdmin(subId, content, userId, rootId);
            for (int i = 0; i < adminList.size(); i++) {
                allList.add(adminList.get(i));
            }

        } else {
            adminList = getTreeInfosAdmin(subId, content, "4d272f66-9dac-4b87-a2a1-22b6e5910779", rootId);
            roleList = getTreeInfosAdmin(subId, content, userId, rootId);
            for (int i = 0; i < adminList.size(); i++) {
                Iterator it_d = ((Map) adminList.get(i)).entrySet().iterator();

                while (it_d.hasNext()) {
                    Entry entry_d = (Entry) it_d.next();
                    Object key = entry_d.getKey();
                    Object value = entry_d.getValue();
                    value = value.toString().split("-")[0];
                    if (key.equals("name") && value.equals("我的模型库")) {
                        ((Map) adminList.get(i)).put(key, "公共模型库");
                        allList.add(adminList.get(i));
                    } else if (key.equals("name") && value.equals("我的场景库")) {
                        ((Map) adminList.get(i)).put(key, "公共场景库");
                        allList.add(adminList.get(i));
                    }
                }
                //allList.add(adminList.get(i));
            }
            for (int i = 0; i < roleList.size(); i++) {
                allList.add(roleList.get(i));
            }
        }
        return allList;
    }

    public List<Object> getTreeInfosAdmin(String subId, String content, String userId, String rootId) {
        // TODO 获取管理员树数据
        //第一层
        List<Object> returnList = null;
        if (content.equals("模型库")) {
            returnList = seleModelsForP(rootId, userId);
        } else if (content.equals("场景库")) {
            returnList = seleScenesForP(rootId, userId);
        }


        return returnList;
    }

    public List<Object> seleScenesForP(String parentId, String userId) {
        // TODO 封装一层的数据
        List<Object> allList = new ArrayList<Object>();
        List<Object> secondList = new ArrayList<Object>();
        List<Scenes> mList = developSceneMapper.seleScenesForP(parentId, userId);
        for (int i = 0; i < mList.size(); i++) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("id", mList.get(i).getId());
            resultMap.put("name", mList.get(i).getContent());
            if (mList.get(i).getSceneContentId().equals("00000000-0000-0000-0000-000000000000")) {
                secondList = seleScenesForP(mList.get(i).getId(), userId);
                resultMap.put("children", secondList);
                resultMap.put("isParent", true);
            } else {
                resultMap.put("isParent", false);
                //resultMap.put("icon", "img/短隧道.png");
            }
            allList.add(i, resultMap);

        }
        return allList;
    }


    public List<Object> seleModelsForP(String parentId, String userId) {
        // TODO 封装一层的数据
        List<Object> allList = new ArrayList<Object>();
        List<Object> secondList = new ArrayList<Object>();
        List<Models> mList = developModelMapper.seleModelsForP(parentId, userId);
        for (int i = 0; i < mList.size(); i++) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("id", mList.get(i).getId());
            resultMap.put("name", mList.get(i).getContent());
            if (mList.get(i).getModelContentId().equals("00000000-0000-0000-0000-000000000000")) {
                secondList = seleModelsForP(mList.get(i).getId(), userId);
                resultMap.put("children", secondList);
                resultMap.put("isParent", true);
            } else {
                resultMap.put("isParent", false);
                //resultMap.put("icon", "img/花.png");
            }
            allList.add(i, resultMap);

        }
        return allList;
    }

    public Models getModelsparentId(String id) {
        return developModelMapper.getModelsparentId(id);
    }

    /**
     * 获取首节点
     */
    public List<Models> getModelsFis(String subTreeId) {

        return developModelMapper.getModelsFis(subTreeId);
    }

    @Override
    public List<Models> getModelsSecondAll(String parentKnowledge, String subjectId) {
        List<Models> ksList = new ArrayList<Models>();
        List<String> userList = subjectMapper.getUserIdForAll(subjectId);
        String userId;
        String userName;
        for (int j = 0; j < userList.size(); j++) {
            List<Models> itemList = developModelMapper.getModelsSecond(parentKnowledge, userList.get(j));
            for (int k = 0; k < itemList.size(); k++) {
                userId = itemList.get(k).getUserKey();
                userName = usersMapper.getUserName(userId);
                itemList.get(k).setName(userName);
                ksList.add(itemList.get(k));
            }
        }

        return ksList;
    }

    /**
     * 获取模型内容表
     */
    @Override
    public Modelcontents getModelContentName(String id) {
        Modelcontents modelcontents = developModelMapper.getModelContentName(id);
        return modelcontents;
    }

    /**
     * 获取知识点类型
     *
     * @param knowledgecontentId
     * @return
     */
    public String getModelsType(String knowledgecontentId) {
        return developModelMapper.getModelsType(knowledgecontentId);
    }

    /**
     * 获取次节点
     */
    public List<Models> getModelsSecond(String parentKnowledge, String userId) {
        List<Models> ksList = developModelMapper.getModelsSecond(parentKnowledge, userId);
        for (int i = 0; i < ksList.size(); i++) {
            Modelcontents modelcontents = developModelMapper.getModelcontentsInfos2(ksList.get(i).getModelContentId());
            if (modelcontents != null) {
                String userName = usersMapper.getUserName(userId);
                ksList.get(i).setType(modelcontents.getType());
                ksList.get(i).setName(userName);
            } else {
                String userName = usersMapper.getUserName(userId);
                ksList.get(i).setName(userName);
            }
        }

        List<Models> getKsList = new ArrayList<Models>();
        List<Models> getKsList2 = new ArrayList<Models>();

        String preId = "";
        for (int i = 0; i < ksList.size(); ) {
            if (preId == "") {
                for (int j = 0; j < ksList.size(); j++) {
                    if (ksList.get(j).getPreModel() == null) {
                        getKsList.add(ksList.get(j));
                        preId = ksList.get(j).getId();
                        i++;
                    }
                }
            } else if (preId != null) {
                for (int j = 0; j < ksList.size(); j++) {
                    if (ksList.get(j).getPreModel() != null) {
                        if (ksList.get(j).getPreModel().equals(preId)) {
                            getKsList.add(ksList.get(j));
                            preId = ksList.get(j).getId();
                            i++;
                        }
                    }
                }
            }
        }
        for (Models models : getKsList) {
            if (models.getCheckDel() == 0) {
                getKsList2.add(models);
            }
        }

        return getKsList2;

        //return ksList;
    }

    @Override
    public List<Models> getModelByAllUsers(String name, String rootId) {
        List<Models> list = developModelMapper.getModelByAllUsers(name, rootId);
        return list;
    }

    /*
     * 刪除模型 更改 checkdel  deltime 字段
     * */
    @Override
    public int delModelcontents(Modelcontents modelcontents) {
        return developModelMapper.delModelcontents(modelcontents);
    }

    @Override
    public int delModels(Models models) {
        return developModelMapper.delModels(models);
    }

    @Override
    public List<Models> getModelByUsers(String name, String rootId, String userId) {
        List<Models> list = developModelMapper.getModelByTeamUserModels(name, rootId, userId);
        return list;
    }

    /**
     * 根据父Id获取子节点
     */
    public List<Models> getParentModels(String id) {
        List<Models> list = developModelMapper.getParentModels(id);


        return list;
    }
//
//    /*
//     * view导航
//     * */
//    public List<Modelcontents> getModelContacts2(){
//        List<Modelcontents> list = developModelMapper.getModelContacts2();
//        return list;
//    }
//    /*
//     * 展示View
//     * */
//    public List<Models> getModelListMapper2(String Id,String userId){
//        List<Models> lsit =developModelMapper.getModelListMapper2(Id,userId);
//        return lsit;
//    }


    public List<Models> getModelsList(String id, String userkey) {
        List<Models> list = developModelMapper.getModelsList(id, userkey);
        Modelcontents modelcontents = new Modelcontents();
        for (Models models : list) {
            modelcontents = getModelcontentsInfos(models.getId());
            models.setFileName(modelcontents.getNmae());
            models.setDelTime(modelcontents.getDelTime());
        }

        return list;
    }

    /*
     * 展示模型
     * */
    public List<Models> getModelListMapper(String Id, String userId) {
        List<Models> lsit = developModelMapper.getModelListMapper(Id, userId);
        return lsit;
    }

    public int updateModelFileName(String Id, String FileName) {
        //modelMapper.addModel(model);
        return developModelMapper.updateModelFileName(Id, FileName);
    }

    /*
     * 编辑模型/场景
     * */
    public int updateModel(Models model) {
        //modelMapper.addModel(model);
        return developModelMapper.updateModel(model);
    }

    /*
     * 获取模型/场景
     * */
    public Models getModel(String Id) {
        //modelMapper.addModel(model);
        return developModelMapper.getModel(Id);
    }

    /*
     * 新增主页模型 (上传)
     * */
    @Override
    public String AddModelsContent2(Modelcontents modelcontents, String treeId, String nodeId) {
        //System.out.println(" treeId："+treeId+" nodeId："+nodeId);
        addLastModelsNode(treeId, nodeId, modelcontents.getUserKey());

        //String id = UUID.randomUUID().toString();
        //modelcontents.setId(id);
        modelcontents.setIntroduce(null);
        modelcontents.setImageContentIcons(ModelImage);
        modelcontents.setModel_Id(lastModelsNodeId(treeId, nodeId, modelcontents.getUserKey()));
        modelcontents.setType(ModelType);
        modelcontents.setOrder(developModelMapper.getModelsMaxOrder() + 1);
        developModelMapper.addModelsModel(modelcontents);
        developModelMapper.alterModelContent_Id(modelcontents);
        System.out.println(modelcontents.getModel_Id());
        String Name = modelcontents.getNmae();
        //String ContactKey=request.getParameter("modelOrView");
        //String type=request.getParameter("type");
        //暂无
        //model.setContactKey(ContactKey);
		/*model.setId(id);
		model.setName(Name);
		model.setType("模型");
		//C:\\xampp\\htdocs4\\TDu\\Data
		model.setUrl("D:\\working\\htdocs4\\TDu\\Data");
		model.setContent(modelcontents.getModel_Id());
		if(nodeId==""){
			model.setContactKey("fc2b74a1-e131-41b7-9787-c950f72e8626");
		}else{
			model.setContactKey(nodeId);
		}
		addModel(model);*/

        return modelcontents.getId();
    }

    /*
     * 修改模型的名字
     * */
    public int updateModelscontentCustomName(String id, String CustomName, String PhotoName) {
        int count2 = developModelMapper.updateModelsContent(id, CustomName, PhotoName);
        return count2;
    }

    @Override
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

    @Override
    public void setVersionFile(List<String> nameList, List<String> fileList, String path) throws Exception {
        // TODO Auto-generated method stub
        //内容编辑
        String xmlText = "<?xml version=\"1.0\"?>\n<root>\n";
        for (int i = 0; i < nameList.size(); i++) {
            xmlText += "<file md5=\"" + nameList.get(i) + "\">" + fileList.get(i) + "</file>\n";
        }
        xmlText += "</root>";

        String filenameTemp = path + "/version.xml";
        System.out.println("  filenameTemp :" + filenameTemp);
        print.creatTxtFile(filenameTemp, path);
        print.writeTxtFile(xmlText, filenameTemp);
        zip.compress(filenameTemp, path + "/version.zip");
        File file = new File(filenameTemp);
        if (file.isFile()) {
            file.delete();
        }
        setVersionTxt(path, "version.zip");
    }

    public void setVersionTxt(String path, String name) throws Exception {
        // TODO Auto-generated method stub
        String md5 = EncodingDecodingUtil.MD5Encode(name, "utf8");
        String filenameTemp = path + "/version.txt";
        System.out.println(" filenameTemp :" + filenameTemp);
        print.creatTxtFile(filenameTemp, path);
        print.writeTxtFile(md5, filenameTemp);
    }

    public String lastModelsNodeIdInAll(String treeId, String clickNodeId, String userKey) {
        //获取父节点id
        String rootId = parentModelsNodeId(clickNodeId, treeId);
        //获取大节点数
        int nodeNum = developModelMapper.seleModelsNum2(treeId, rootId, userKey);
        //找到第一个大节点
        Models models = developModelMapper.seleModelsFirst2(treeId, rootId, userKey);
        if (models != null) {
            String firstId = models.getId();
            for (int i = 1; i < nodeNum; i++) {
                String nextId = developModelMapper.getModelsNextNodeId(firstId);
                firstId = nextId;
            }
            return firstId;
        }
        return null;
    }

    /**
     * 新增功能
     */
    public Boolean inknowModels(Models knowledges) {
        developModelMapper.inknowModels(knowledges);
        return true;
    }

    /**
     * Root查询
     *
     * @return
     */
    public String slRootModels(String id) {
        String rid = developModelMapper.seleRootModels(id);
        return rid;
    }

    public List<Models> getContentModels(String subjectId, String sarchStr) {
        return developModelMapper.getContentModels(subjectId, sarchStr);
    }
}
