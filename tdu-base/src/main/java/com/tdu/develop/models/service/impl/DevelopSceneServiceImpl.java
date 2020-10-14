package com.tdu.develop.models.service.impl;

import com.tdu.develop.models.mapper.DevelopSceneMapper;
import com.tdu.develop.models.pojo.Scenecontents;
import com.tdu.develop.models.pojo.Scenes;
import com.tdu.develop.models.service.DevelopSceneService;
import com.tdu.develop.resource.mapper.SubjectTreeMapper;
import com.tdu.develop.user.mapper.SubjectMapper;
import com.tdu.develop.user.mapper.UsersMapper;
import com.tdu.develop.util.EncodingDecodingUtil;
import com.tdu.develop.util.FilModle;
import com.tdu.develop.util.print;
import com.tdu.develop.util.zip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @author 志阳
 * @create 2019-08-15-15:08
 */
@Service
public class DevelopSceneServiceImpl implements DevelopSceneService {
    @Autowired
    DevelopSceneMapper developSceneMapper;
    @Autowired
    SubjectMapper subjectMapper;
    @Autowired
    SubjectTreeMapper subjectTreeMapper;
    @Autowired
    UsersMapper usersMapper;

    public static final String SceneImage = "../../../Source/imgicon/仿真.png";
    public static final String SceneType = "场景";
    //图标
    public static final String ImageIcon = "../../../Source/imgicon/tag_orange.png";


    @Override
    public List<Scenes> getScenesByRank() {
        return developSceneMapper.getScenesByRank();
    }

    @Override
    public String getSceneId(String sceneContentId) {
        return developSceneMapper.getSceneId(sceneContentId);
    }

    public void addScenecontents(Scenecontents scenecontents) {
        developSceneMapper.addScenesModel(scenecontents);
    }

    public void addScenes(Scenes scenes) {
        developSceneMapper.addLastScenesNode(scenes);
    }


    public String getFirstSceneId(String rootId, String userId) {
        // TODO Auto-generated method stub
        return developSceneMapper.getFirstSceneId(rootId, userId);
    }

    /**
     * 获取模型rootId的 subtreeid
     */
    public String getSubjectScenesRootId(String treeId) {

        return subjectTreeMapper.getSubjectScenesRootId2(treeId);
    }

    /**
     * 获取rootId
     *
     * @param treeId
     * @return
     */
    public String gettScenesRootId(String treeId) {

        return developSceneMapper.getSubjectScenesRootId(treeId);
    }


    /**
     * knowledgecontents的type属性
     */
    public static final String simulateType = "仿真";


    /**
     * 通过模型Id获取contents内容
     */
    public Scenecontents getScenecontentsInfos(String id) {
        return developSceneMapper.getScenecontentsInfos(id);
    }

    /**
     * 删除模型(含文件夹)
     *
     * @param id
     * @return
     */
    public int delscenes(String id, String time) {

        return developSceneMapper.delScenes(id, time);
    }

    /**
     * 删除模型contents
     *
     * @param id
     * @return
     */
    public int delsceneContets(String id, String time) {

        return developSceneMapper.delSceneContets(id, time);
    }

    /**
     * 获取模型信息
     *
     * @param modelId
     * @return
     */
    public List<Scenes> getScenesscontents(String modelId, String userId) {
        // TODO 获取模型信息数据

        List<Scenes> modelList = developSceneMapper.getScenecontents(modelId, userId);
        return modelList;
    }


    public boolean updateSceneName(String id, String name) {
        int count1 = developSceneMapper.updateSceneName(id, name);
        int count2 = developSceneMapper.updateSceneContentName(id, name);
        boolean flag = false;
        if (count1 != 0 && count2 != 0) {
            flag = true;
        }
        return flag;
    }


    public List<Scenes> getFirstScene(String subUpId, String rootId, String userId) {
        // TODO 获取首层目录

        return developSceneMapper.getFisScenes(subUpId, rootId, userId);
    }


    public List<Scenes> getSubScene(String parentId, String subUpId, String userId) {
        // TODO 获取第二层目录

        return developSceneMapper.getSubScenes(parentId, subUpId, userId);
    }


    public void addSubScenes(String parentId, String content, String subUpId, String userId) {
        // TODO 添加模型参数

        String id = UUID.randomUUID().toString();
        String preScene = lastScenesNodeId(subUpId, parentId, userId);
        developSceneMapper.addSubScenes(id, content, parentId, preScene, subUpId, userId);
    }


    public String getSubId(String subId, String treeName) {
        // TODO Auto-generated method stub

        return subjectTreeMapper.getSubId(subId, treeName);
    }


    public void upFirSceneTree(String id, String name) {
        // TODO Auto-generated method stub

        developSceneMapper.upFirSceneTree(id, name);
    }


    public void deleScene(String Id) {
        // TODO Auto-generated method stub

        developSceneMapper.deleteScene(Id);
    }


    public void addFisScene(String Id, String subId, String content, String userId) {
        // TODO Auto-generated method stub

        String rooyId = developSceneMapper.getRootId(subId);
        String preModelId = lastScenesNodeId(subId, rooyId, userId);
        developSceneMapper.addFisScenes2(Id, content, rooyId, preModelId, subId, userId);
        //developSceneMapper.addFisScenes(Id, content, rooyId, subId);
    }


    public List<Scenecontents> getScenecontents(String SceneId, String userId) {
        // TODO 获取模型信息数据

        List<Scenes> SceneList = developSceneMapper.getScenecontents(SceneId, userId);
        List<Scenecontents> ScenecontentsList = new ArrayList<Scenecontents>();

        List<Scenecontents> getKsList = new ArrayList<Scenecontents>();

        for (int i = 0; i < SceneList.size(); i++) {
            Scenecontents scene = developSceneMapper.getScenecontentsInfos(SceneList.get(i).getId());
            if (scene != null) {
                ScenecontentsList.add(scene);
            }
        }
        for (Scenecontents scenecontents : ScenecontentsList) {
            if (scenecontents.getCheckDel() == 0) {
                getKsList.add(scenecontents);
            }
        }
        return getKsList;
    }


    public String getSubId1(String SceneId) {
        // TODO 获取模型subiD


        return developSceneMapper.getSubId1(SceneId);
    }

    /*
     * 新增主页模型
     * */

    public String AddScenesContent(Scenecontents Scenecontents, String treeId, String nodeId, Scenes scenes) {

        //System.out.println(" treeId："+treeId+" nodeId："+nodeId);
        addLastScenesNode(treeId, nodeId, Scenecontents.getUserKey());

        String id = UUID.randomUUID().toString();

        Scenecontents.setNmae(id + ".tdc");

        Scenecontents.setId(id);
        Scenecontents.setIntroduce(null);
        Scenecontents.setImageContentIcons(SceneImage);
        Scenecontents.setScene_Id(lastScenesNodeId(treeId, nodeId, Scenecontents.getUserKey()));
        Scenecontents.setType(SceneType);
        Scenecontents.setOrder(developSceneMapper.getScenesMaxOrder() + 1);
        Scenecontents.setUrl("D:\\working\\TDuClub\\TDu\\Data\\3D");
        developSceneMapper.addScenesModel(Scenecontents);
        developSceneMapper.alterSceneContent_Id(Scenecontents);
        System.out.println(Scenecontents.getScene_Id());
        String Name = Scenecontents.getNmae();


        /*源文件路径  D:\wamp\www\develop\QZ\SceneExample
         * 目標文件路徑  D:\working\TDuClub\TDu\Data\3D\Scene
         * 服务器路径   D:\\working\\TDuClub\\develop\\QZ\\SceneExample
         * */


        //服務器路徑
        String path = "\\home\\working\\https://www.tduvr.club\\tdu\\sceneExample";
        //String path="D:\\wamp\\www\\newTdu\\sceneExample";

        //服務器路徑
        String mubiao = "\\home\\working\\tdu.tduvr.club\\Data\\3D\\Scene\\" + Scenecontents.getUserKey() + "\\" + id;
        //String mubiao="D:\\working\\TDuClub\\TDu\\Data\\3D\\Scene\\"+Scenecontents.getUserKey()+"\\"+id;

        /*String path="D:\\working\\TDuClub\\develop\\QZ\\sceneExample";
        String mubiao="D:\\working\\TDuClub\\TDu\\Data\\3D\\Scene\\"+Scenecontents.getUserKey()+"\\"+id;*/

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

        //String ContactKey=request.getParameter("SceneOrView");
        //String type=request.getParameter("type");
        //暂无
        //Scene.setContactKey(ContactKey);
		/*Scene.setId(id);
		Scene.setName(Name);
		Scene.setType("模型");
		//C:\\xampp\\htdocs4\\TDu\\Data
		Scene.setUrl("D:\\working\\TDuClub\\TDu\\Data\\3D");
		Scene.setContent(Scenecontents.getScene_Id());
		if(nodeId==""){
			Scene.setContactKey("fc2b74a1-e131-41b7-9787-c950f72e8626");
		}else{
			Scene.setContactKey(nodeId);
		}
		addScene(Scene);*/

        return Scenecontents.getId();
    }

    /*
    xmlForEditor
    * */
    public String AddScenesContentFileModel(Scenecontents Scenecontents, String treeId, String nodeId, Scenes scenes) {

        //System.out.println(" treeId："+treeId+" nodeId："+nodeId);
        addLastScenesNode(treeId, nodeId, Scenecontents.getUserKey());
        //String id = UUID.randomUUID().toString();
        Scenecontents.setNmae(Scenecontents.getId() + ".EXM");
        //Scenecontents.setId(id);
        Scenecontents.setIntroduce(null);
        Scenecontents.setImageContentIcons(SceneImage);
        Scenecontents.setScene_Id(lastScenesNodeId(treeId, nodeId, Scenecontents.getUserKey()));
        Scenecontents.setType(SceneType);
        Scenecontents.setOrder(developSceneMapper.getScenesMaxOrder() + 1);
        Scenecontents.setUrl("D:\\working\\TDuClub\\TDu\\Data\\3D");
        developSceneMapper.addScenesModel(Scenecontents);
        developSceneMapper.alterSceneContent_Id(Scenecontents);
        System.out.println(Scenecontents.getScene_Id());
        return Scenecontents.getScene_Id();
    }

    /*
     * 新增主页模型
     * */
    public String AddScenesContentFileModel(Scenecontents Scenecontents, String treeId, String nodeId, Scenes scenes, MultipartFile[] file, List<FilModle> models) {

        //System.out.println(" treeId："+treeId+" nodeId："+nodeId);
        addLastScenesNode(treeId, nodeId, Scenecontents.getUserKey());

        String id = UUID.randomUUID().toString();

        Scenecontents.setNmae(id + ".EXM");

        Scenecontents.setId(id);
        Scenecontents.setIntroduce(null);
        Scenecontents.setImageContentIcons(SceneImage);
        Scenecontents.setScene_Id(lastScenesNodeId(treeId, nodeId, Scenecontents.getUserKey()));
        Scenecontents.setType(SceneType);
        Scenecontents.setOrder(developSceneMapper.getScenesMaxOrder() + 1);
        Scenecontents.setUrl("D:\\working\\TDuClub\\TDu\\Data\\3D");
        developSceneMapper.addScenesModel(Scenecontents);
        developSceneMapper.alterSceneContent_Id(Scenecontents);
        System.out.println(Scenecontents.getScene_Id());
        String Name = Scenecontents.getNmae();

        //服務器路徑
        //String mubiao="\\home\\working\\tdu.tduvr.club\\Data\\3D\\Scene\\"+Scenecontents.getUserKey()+"\\"+id;
        //String mubiao="D:\\working\\TDuClub\\TDu\\Data\\3D\\Scene\\"+Scenecontents.getUserKey()+"\\"+id;
        //新
        //String mubiao="D:/wamp/www/Data/3D/Scene/"+Scenecontents.getUserKey()+"/"+id;
        String mubiao = "/www/wwwroot/tdu.tduvr.club/Data/3D/Scene/" + Scenecontents.getUserKey() + "/" + id;
        for (MultipartFile f : file) {
            File file1;
            String name = "";
            String suffix = "";
            try {
                System.out.println(f.getOriginalFilename() + "   iii         --------");
                String path = f.getOriginalFilename();
                suffix = path.substring(path.lastIndexOf("."));
                //sence  /  model ID
                String pp = id;
                String realPath = pp;
                System.out.println("  path : " + path);

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

                updatePdf(f, realPath, Scenecontents.getUserKey(), id);
                if (suffix.equals(".EXM") || suffix.equals(".exm")) {
                    // 文件夹路径存在的情况下
                    //String filename = "D:/wamp/www/Data/3D/Scene/"+""+Scenecontents.getUserKey()+""+"/"+realPath;;// 文件名
                    String filename = "/www/wwwroot/tdu.tduvr.club/Data/3D/Scene/" + "" + Scenecontents.getUserKey() + "" + "/" + realPath;
                    ;// 文件名
                    if (models.size() > 0) {
                        saveDocument(filename, models);//把改变的内存中的document真正保存到指定的文件中
                    }
                }
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

        return Scenecontents.getId();
    }

    /**
     * 把改变的domcument对象保存到指定的xml文件中
     *
     * @throws IOException
     * @author chenleixing
     */
    public static void saveDocument(String filename, List<FilModle> models) throws IOException {
        int count = 0;
        DocumentBuilderFactory a = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder b = a.newDocumentBuilder();
            Document document = b.parse(filename);
            NodeList booklist = document.getElementsByTagName("Node");

            for (int i = 0; i < booklist.getLength(); ++i) {
                Node book = booklist.item(i);
                NamedNodeMap bookmap = book.getAttributes();

                for (int j = 0; j < bookmap.getLength(); ++j) {
                    Node node = bookmap.item(j);
                    System.out.print(node.getNodeName() + ":");
                    System.out.println(node.getNodeValue());
                }

                NodeList childlist = book.getChildNodes();

                for (int t = 0; t < childlist.getLength(); ++t) {
                    if (childlist.item(t).getNodeType() == 1 && childlist.item(t).getNodeName().equals("File")) {
                        System.out.print(childlist.item(t).getNodeName() + ":");
                        if (count < models.size()) {
                            childlist.item(t).setTextContent(((FilModle) models.get(count)).getTxt());
                        }

                        System.out.println(childlist.item(t).getTextContent());
                        NamedNodeMap childBookmap = childlist.item(t).getAttributes();
                        if (childBookmap.getLength() <= 2) {
                            Node node = childlist.item(t);
                            Element eNode = (Element) node;
                            if (count < models.size()) {
                                eNode.setAttribute("ModelID", ((FilModle) models.get(count)).getModelID());
                            }

                            if (count < models.size()) {
                                eNode.setAttribute("UserID", ((FilModle) models.get(count)).getUserID());
                            }

                            ++count;
                        } else {
                            for (int j = 0; j < childBookmap.getLength(); ++j) {
                                Node node = childBookmap.item(j);
                                if (node.getNodeName().equals("ModelID") && count < models.size()) {
                                    node.setNodeValue(((FilModle) models.get(count)).getModelID());
                                }

                                if (node.getNodeName().equals("UserID") && count < models.size()) {
                                    node.setNodeValue(((FilModle) models.get(count)).getUserID());
                                }

                                System.out.print(node.getNodeName() + ":");
                                System.out.println(node.getNodeValue());
                            }

                            ++count;
                        }
                    }
                }
            }

            save(document, filename);
        } catch (ParserConfigurationException | SAXException | TransformerException var15) {
            var15.printStackTrace();
        }
    }

    public static void save(Document document, String filename) throws TransformerException {
        Source xmlSource = new DOMSource(document);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        Result result = new StreamResult(new File(filename));
        transformer.transform(xmlSource, result);
    }

    /*
     * 新增主页模型
     * */
    public String AddScenesContentFile(Scenecontents Scenecontents, String treeId, String nodeId, Scenes scenes, MultipartFile[] file) {

        //System.out.println(" treeId："+treeId+" nodeId："+nodeId);
        addLastScenesNode(treeId, nodeId, Scenecontents.getUserKey());

        String id = UUID.randomUUID().toString();

        Scenecontents.setNmae(id + ".tdc");

        Scenecontents.setId(id);
        Scenecontents.setIntroduce(null);
        Scenecontents.setImageContentIcons(SceneImage);
        Scenecontents.setScene_Id(lastScenesNodeId(treeId, nodeId, Scenecontents.getUserKey()));
        Scenecontents.setType(SceneType);
        Scenecontents.setOrder(developSceneMapper.getScenesMaxOrder() + 1);
        Scenecontents.setUrl("D:\\working\\TDuClub\\TDu\\Data\\3D");
        developSceneMapper.addScenesModel(Scenecontents);
        developSceneMapper.alterSceneContent_Id(Scenecontents);
        System.out.println(Scenecontents.getScene_Id());
        String Name = Scenecontents.getNmae();

        //服務器路徑
        //String mubiao="\\home\\working\\tdu.tduvr.club\\Data\\3D\\Scene\\"+Scenecontents.getUserKey()+"\\"+id;
        String mubiao = "D:\\working\\TDuClub\\TDu\\Data\\3D\\Scene\\" + Scenecontents.getUserKey() + "\\" + id;

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
                System.out.println("  path : " + path);

                if (suffix.equals(".tdb") || suffix.equals(".tdc") || suffix.equals(".EXM") || suffix.equals(".exm") || suffix.equals(".glb") || suffix.equals(".GLB")) {
                    System.out.println(" suffix :" + suffix);
                    realPath = pp + "\\" + "" + id + "" + suffix;
                    System.out.println("  realPath  :" + realPath);
                } else {
                    String[] strArr = path.split("/");
                    System.out.println(strArr.length); //这里输出3
                    for (int i = 0; i < strArr.length; ++i) {
                        System.out.println(strArr[i]);//这里输出a b c
                        if (i != 0) {
                            realPath = realPath + "\\" + strArr[i];
                        }
                    }
                    System.out.println("  realPath  :" + realPath);
                }
                updatePdf(f, realPath, Scenecontents.getUserKey(), id);
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

        return Scenecontents.getId();
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

    public void setVersionFile(List<String> nameList, List<String> fileList, String path) throws Exception {
        // TODO Auto-generated method stub
        //内容编辑
        String xmlText = "<?xml version=\"1.0\"?>\n<root>\n";
        for (int i = 0; i < nameList.size(); i++) {
            xmlText += "<file md5=\"" + nameList.get(i) + "\">" + fileList.get(i) + "</file>\n";
        }
        xmlText += "</root>";

        String filenameTemp = path + "/version.xml";
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
        print.creatTxtFile(filenameTemp, path);
        print.writeTxtFile(md5, filenameTemp);
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

        //String filePar = "D:/working/TDuClub/TDu/Data/3D/Scene/"+userKey+wenjianPath;// 文件夹路径
        //String filePar = "\\home\\working\\tdu.tduvr.club\\Data\\3D\\Scene\\"+userKey+wenjianPath;// 文件夹路径
        //新
        // String filePar = "D:/wamp/www/Data/3D/Scene/"+userKey+wenjianPath;
        String filePar = "/www/wwwroot/tdu.tduvr.club/Data/3D/Scene/" + userKey + wenjianPath;// 文件夹路径
       /* File myPath = new File( filePar );
        if ( !myPath.exists()){//若此目录不存在，则创建之
            myPath.mkdirs();
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
        // String filename = "D:/wamp/www/Data/3D/Scene/"+""+userKey+""+"/"+realPath;;// 文件名
        String filename = "/www/wwwroot/tdu.tduvr.club/Data/3D/Scene/" + "" + userKey + "" + "/" + realPath;
        ;// 文件名

        System.out.println(filename);
        //String name  = "D:/wamp/www/Data/3D/Scene/"+""+userKey+""+"/"+realPath;
        //String name  = " /www/wwwroot/tdu.tduvr.club/Data/3D/Scene/"+""+userKey+""+"/"+realPath;
        //File writeFile=new File(" /www/wwwroot/tdu.tduvr.club/Data/3D/Scene/"+""+userKey+""+"/"+realPath);
        //String creatFileName  = unicodeToUtf8(name);
        //File writeFile=new File(creatFileName);


        String name = "/www/wwwroot/tdu.tduvr.club/Data/3D/Scene/" + "" + userKey + "" + "/" + realPath;
        String creatFileName = unicodeToUtf8(name);
        File writeFile = new File(creatFileName);
        System.out.println(Charset.defaultCharset());
        try {
            //FileWriter fw = new FileWriter(filename,true);// filePar + "\\" + filename,true
            //BufferedInputStream bis = new BufferedInputStream(is);
            //BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(writeFile));


            FileWriter fw = new FileWriter(filename, true);// filePar + "\\" + filename,true
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(writeFile));


            byte[] flash = new byte[1024];
            int len = 0;
            while (-1 != (len = bis.read(flash))) {
                bos.write(flash, 0, len);
            }
            System.out.println(bos.toString());
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
    public void addLastScenesNode(String treeId, String clickNodeId, String userId) {

        Scenes Scenes = new Scenes();
        String NodeId = UUID.randomUUID().toString();
        Scenes.setId(NodeId);
        Scenes.setContent("新建目录");
        Scenes.setParentScene(parentScenesNodeId(clickNodeId, treeId));
        Scenes.setSubjectTree_Id(treeId);
        Scenes.setImageIcons(ImageIcon);
        Scenes.setSceneContentId("00000000-0000-0000-0000-000000000000");
        Scenes.setBeforCondition("<root><beforesee></beforesee><userkey></userkey><grades></grades></root>");
        Scenes.setPreScene(lastScenesNodeId(treeId, clickNodeId, userId));
        Scenes.setUserKey(userId);
        developSceneMapper.addLastScenesNode(Scenes);
    }

    /**
     * 设置父节点的id
     *
     * @param clickNodeId 该点击节点的id
     * @param treeId      树id
     * @return 父节点id
     */
    public String parentScenesNodeId(String clickNodeId, String treeId) {

        if (clickNodeId.length() > 0) {
            return clickNodeId;
        }
        return developSceneMapper.getSubjectScenesRootId(treeId);
    }

    /**
     * 根据树id
     * 获取最后一个节点的id
     *
     * @return
     */
    public String lastScenesNodeId(String treeId, String clickNodeId, String userId) {

        //获取父节点id
        String rootId = parentScenesNodeId(clickNodeId, treeId);
        //获取大节点数
        int nodeNum = developSceneMapper.seleScenesNum(treeId, rootId, userId);
        //找到第一个大节点
        Scenes Scenes = developSceneMapper.seleScenesFirst(treeId, rootId, userId);
        if (Scenes != null) {
            String firstId = Scenes.getId();
            for (int i = 1; i < nodeNum; i++) {
                String nextId = developSceneMapper.getScenesNextNodeId(firstId);
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
    public void removeScenes(String treeNodeId, String subjectId) {

        List<Scenes> list = new ArrayList<Scenes>();
        Scenes Scenes = new Scenes();
        //1.找到该节点的所有子类
        list = developSceneMapper.getAllScenesclass(treeNodeId);
        //判断是否存在子文件或子目录
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Scenes = list.get(i);
                String id = Scenes.getId();
                //判断该子类是内容还是目录
                String result = judgeScenesType(id);
                //对内容的处理
                deleteSceneFile(id, subjectId);
                //对目录的处理
                deleteScenesDirectory(id, subjectId);
            }
            deleteScenesDirectory(treeNodeId, subjectId);
        } else {
            //对内容的处理
            Scenes = developSceneMapper.seleScenesKnow(treeNodeId);
            if (!Scenes.getSceneContentId().equals("00000000-0000-0000-0000-000000000000"))
                deleteSceneFile(treeNodeId, subjectId);
            //对目录的处理
            deleteScenesDirectory(treeNodeId, subjectId);
        }
    }

    /**
     * 判断该子类是内容还是目录
     *
     * @param knowledgeId
     * @return "content" 内容
     * "directory" 目录
     */
    public String judgeScenesType(String knowledgeId) {
        Scenecontents Scenecontents = developSceneMapper.getFileScenesContent(knowledgeId);
        String result;
        if (Scenecontents != null) {
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
    public void deleteSceneFile(String knowledgeId, String subjectId) {

//        Scenecontents Scenecontents =  developSceneMapper.getFileScenesContent(knowledgeId);
//        //判断内容的类型进行删除
//        if(simulateType.equals(Scenecontents.getType())||AddKnowledgeServiceImpl.HtmlType.equals(Scenecontents.getType())||
//                AddKnowledgeServiceImpl.QuesType.equals(Scenecontents.getType())||AddKnowledgeServiceImpl.customType.equals(Scenecontents.getType())){
//            //删除内容
//            developSceneMapper.deleteSimulateScenes(Scenecontents.getId());
//            alterScenesNextPreNodeId(Scenecontents.getScene_Id());
//            //删除节点文件
//            int i = developSceneMapper.deleteScenesKnowledges(Scenecontents.getId());
//        }else{
//            //根据id获取树id
//            String treeId = developSceneMapper.getScenesTreeId(Scenecontents.getScene_Id());
//            String path = AddKnowledgeServiceImpl.filePath+ File.separator+subjectId+File.separator+treeId+File.separator+Scenecontents.getType()+File.separator+Scenecontents.getId();
//            File file = new File(path);
//            //删除内容文件
//            file.delete();
//            //删除内容
//            developSceneMapper.deleteSimulateScenes(Scenecontents.getId());
//            alterScenesNextPreNodeId(Scenecontents.getScene_Id());
//            //删除节点文件
//            int i = developSceneMapper.deleteScenesKnowledges(Scenecontents.getId());
//        }

    }

    /**
     * 根据删除节点id查询下一节点id并
     * 修改下一节点的上一节点id
     *
     * @param deletedNodeId 当前节点id
     */
    public void alterScenesNextPreNodeId(String deletedNodeId) {

        //获取下一节点
        String nextId = developSceneMapper.getScenesNextNodeId(deletedNodeId);
        //判断是否有下一节点
        if (nextId != null) {
            //获取上一节点id
            String preId = developSceneMapper.getScenesPreNodeId(deletedNodeId);
            //修改下一节点的上一节点id
            developSceneMapper.alterScenesNextPreNodeId(preId, nextId);
        }
    }

    /**
     * 对目录的处理
     *
     * @param id        当前knowledges的Id
     * @param subjectId
     */
    public void deleteScenesDirectory(String id, String subjectId) {

        //判断该目录是否有子类
        if (developSceneMapper.childScenesNum(id) > 0) {
            //遍历所有的子类
            for (int i = 0; i < developSceneMapper.childScenesNum(id); i++) {
                //获取子类对象
                Scenes Scenes = developSceneMapper.getAllScenesclass(id).get(i);
                //判断子类是内容还是目录
                String result = judgeScenesType(Scenes.getId());
                if ("content".equals(result)) {
                    alterScenesNextPreNodeId(Scenes.getId());
                    deleteSceneFile(Scenes.getId(), subjectId);
                } else {
                    deleteScenesDirectory(Scenes.getId(), subjectId);
                }
            }
        } else {
            alterScenesNextPreNodeId(id);
            //删除目录
            int i = developSceneMapper.deleteScenesKnowledges(id);
        }
    }

    /*
     * 删除模型分类
     * */
    public int delSceneContact(String id) {

        int count = developSceneMapper.delSceneContact(id);
        return count;
    }

    /*
     * 知识树删除模型/场景
     * */
    public int delScenes2(String Id) {

        //SceneMapper.addScene(Scene);
        return developSceneMapper.delScenes2(Id);
    }


    public Scenes getScenesparentId(String id) {
        return developSceneMapper.getScenesparentId(id);
    }

    public List<Scenes> getSceneByUsers(String name, String rootId, String userId) {
        List<Scenes> list = developSceneMapper.getSceneByTeamUserScenes(name, rootId, userId);
        return list;
    }

    @Override
    public List<Scenes> getSceneByAllUsers(String name, String rootId) {
        List<Scenes> list = developSceneMapper.getSceneByAllUsers(name, rootId);
        return list;
    }

    /**
     * 获取场景内容表
     */
    @Override
    public Scenecontents getSceneContentName(String id) {
        Scenecontents scenecontents = developSceneMapper.getSceneContentName(id);
        return scenecontents;
    }

    /**
     * 获取首节点
     */
    public List<Scenes> getScenesFis(String subTreeId) {

        return developSceneMapper.getScenesFis(subTreeId);
    }

    @Override
    public List<Scenes> getScenesSecondAll(String parentKnowledge, String subjectId) {
        List<Scenes> ksList = new ArrayList<Scenes>();
        List<String> userList = subjectMapper.getUserIdForAll(subjectId);
        String username;
        String userId;
        for (int j = 0; j < userList.size(); j++) {
            List<Scenes> itemList = developSceneMapper.getScenesSecond(parentKnowledge, userList.get(j));
            itemList = getScneesList(itemList);
            for (int k = 0; k < itemList.size(); k++) {
                userId = itemList.get(k).getUserKey();
                username = usersMapper.getUserName(userId);
                itemList.get(k).setName(username);
                ksList.add(itemList.get(k));

            }
        }


        return ksList;
    }

    //排序
    public List<Scenes> getScneesList(List<Scenes> ksList) {
        List<Scenes> getKsList = new ArrayList<Scenes>();
        String preId = "";
        for (int i = 0; i < ksList.size(); ) {
            if (preId == "") {
                for (int j = 0; j < ksList.size(); j++) {
                    if (ksList.get(j).getPreScene() == null) {
                        getKsList.add(ksList.get(j));
                        preId = ksList.get(j).getId();
                        i++;
                    }
                }
            } else if (preId != null) {
                for (int j = 0; j < ksList.size(); j++) {
                    if (ksList.get(j).getPreScene() != null) {
                        if (ksList.get(j).getPreScene().equals(preId)) {
                            getKsList.add(ksList.get(j));
                            preId = ksList.get(j).getId();
                            i++;
                        }
                    }
                }
            }
        }
        return getKsList;
    }

    /**
     * 获取次节点
     */
    @Override
    public List<Scenes> getScenesSecond(String parentKnowledge, String userId) {
        List<Scenes> ksList = developSceneMapper.getScenesSecond(parentKnowledge, userId);
        for (int i = 0; i < ksList.size(); i++) {
            Scenecontents scenecontents = developSceneMapper.getScenecontentsInfos(ksList.get(i).getSceneContentId());
            if (scenecontents != null) {
                String userName = usersMapper.getUserName(userId);
                //ksList.get(i).setType(scenecontents.getType());
                ksList.get(i).setName(userName);
            } else {
                String userName = usersMapper.getUserName(userId);
                ksList.get(i).setName(userName);
            }
        }

        List<Scenes> getKsList = new ArrayList<Scenes>();
        List<Scenes> getKsList2 = new ArrayList<Scenes>();
        String preId = "";
        for (int i = 0; i < ksList.size(); ) {
            if (preId == "") {
                for (int j = 0; j < ksList.size(); j++) {
                    if (ksList.get(j).getPreScene() == null) {
                        getKsList.add(ksList.get(j));
                        preId = ksList.get(j).getId();
                        i++;
                    }
                }
            } else if (preId != null) {
                for (int j = 0; j < ksList.size(); j++) {
                    if (ksList.get(j).getPreScene() != null) {
                        if (ksList.get(j).getPreScene().equals(preId)) {
                            getKsList.add(ksList.get(j));
                            preId = ksList.get(j).getId();
                            i++;
                        }
                    }
                }
            }
        }
        for (Scenes scenes : getKsList) {
            if (scenes.getCheckDel() == 0) {
                getKsList2.add(scenes);
            }
        }


        return getKsList2;

        // return ksList;
    }

    public String getScenesType(String knowledgecontentId) {
        return developSceneMapper.getScenesType(knowledgecontentId);
    }

    @Override
    public int delScenecontents(Scenecontents scenecontents) {
        return developSceneMapper.delScenecontents(scenecontents);
    }

    @Override
    public int delScenes(Scenes scenes) {
        return developSceneMapper.delScenes_1(scenes);
    }

    /**
     * 根据父Id获取子节点
     */
    public List<Scenes> getParentScenes(String id) {
        List<Scenes> list = developSceneMapper.getParentScenes(id);


        return list;
    }

    public List<Scenes> getScenesList(String id, String userId) {
        List<Scenes> list = developSceneMapper.getScenesList(id, userId);


        return list;
    }

    /*
     * 新增主页场景(上传)
     * */
    @Override
    public String AddScenesContent2(Scenecontents scenecontents, String treeId, String nodeId) {
        //System.out.println(" treeId："+treeId+" nodeId："+nodeId);
        addLastScenesNode(treeId, nodeId, scenecontents.getUserKey());

        //String id = UUID.randomUUID().toString();
        //scenecontents.setId(id);
        scenecontents.setIntroduce(null);
        scenecontents.setImageContentIcons(SceneImage);
        scenecontents.setScene_Id(lastScenesNodeId(treeId, nodeId, scenecontents.getUserKey()));
        scenecontents.setType(SceneType);
        scenecontents.setOrder(developSceneMapper.getScenesMaxOrder() + 1);
        developSceneMapper.addScenesModel(scenecontents);
        developSceneMapper.alterSceneContent_Id(scenecontents);
        System.out.println(scenecontents.getScene_Id());
        String Name = scenecontents.getNmae();
        //String ContactKey=request.getParameter("modelOrView");
        //String type=request.getParameter("type");
        //暂无
        //model.setContactKey(ContactKey);
		/*scene.setId(id);
		scene.setName(Name);
		scene.setType("场景");
		//C:\\xampp\\htdocs4\\TDu\\Data
		scene.setUrl("D:\\working\\htdocs4\\TDu\\Data");
		scene.setContent(scenecontents.getScene_Id());
		if(nodeId==""){
			scene.setContactKey("7fb53e8f-baf1-4c1e-8868-bad634e81461");
		}else{
			scene.setContactKey(nodeId);
		}
		addScene(scene);*/

        return scenecontents.getId();
    }

    /*
     * 修改场景的名字
     * */
    public int updateScenescontentCustomName(String id, String CustomName, String PhotoName) {
        int count2 = developSceneMapper.updateScenesContent(id, CustomName, PhotoName);
        return count2;
    }

    public String lastScenesNodeIdInAll(String treeId, String clickNodeId, String userId) {
        //获取父节点id
        String rootId = parentScenesNodeId(clickNodeId, treeId);
        //获取大节点数
        int nodeNum = developSceneMapper.seleScenesNum2(treeId, rootId, userId);
        //找到第一个大节点
        Scenes scenes = developSceneMapper.seleScenesFirst2(treeId, rootId, userId);
        if (scenes != null) {
            String firstId = scenes.getId();
            for (int i = 1; i < nodeNum; i++) {
                String nextId = developSceneMapper.getScenesNextNodeId(firstId);
                firstId = nextId;
            }
            return firstId;
        }
        return null;
    }

    public Boolean inknowScenes(Scenes knowledges) {
        developSceneMapper.inknowScenes(knowledges);
        return true;
    }

    public String slRootScenes(String id) {
        String rid = developSceneMapper.seleRootScenes(id);
        return rid;
    }

    public List<Scenes> getContentScenes(String subjectId, String sarchStr) {
        return developSceneMapper.getContentScenes(subjectId, sarchStr);
    }

    @Override
    public void updateScenesContentFile(String userkey, String id, MultipartFile[] file) {
        //服務器路徑
        String mubiao = "/www/wwwroot/tdu.tduvr.club/Data/3D/Scene/" + userkey + "/" + id;
        // String mubiao = "D:\\working\\TDuClub\\TDu\\Data\\3D\\Scene\\" + userkey + "\\" + id;

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
                System.out.println("  path : " + path);

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
                updatePdf(f, realPath, userkey, id);
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
    }

    @Override
    public Scenes getScenes(String sceneId) {
        return developSceneMapper.seleScenesKnow(sceneId);
    }
}
