package com.tdu.develop.models.controller;

import com.tdu.develop.models.pojo.Modelcontents;
import com.tdu.develop.models.pojo.Models;
import com.tdu.develop.models.pojo.Scenecontents;
import com.tdu.develop.models.service.DevelopModelService;
import com.tdu.develop.models.service.DevelopSceneService;
import com.tdu.develop.models.service.impl.DevelopModelServiceImpl;
import com.tdu.develop.models.service.impl.DevelopSceneServiceImpl;
import com.tdu.develop.resource.pojo.Question;
import com.tdu.develop.resource.service.AddKnowledgeService;
import com.tdu.develop.resource.service.ExamService;
import com.tdu.develop.resource.service.impl.AddKnowledgeServiceImpl;
import com.tdu.develop.resource.service.impl.ExamServiceImpl;
import com.tdu.develop.user.pojo.CellObject;
import com.tdu.develop.user.service.UsersService;
import com.tdu.develop.user.service.impl.UserServiceImpl;
import com.tdu.develop.util.ExcelClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 志阳
 * @create 2019-09-04-17:18
 */
@CrossOrigin
@Controller
@RequestMapping(value = "UserExcelController")
public class UserExcelController {
    @Autowired
    UsersService usersService = new UserServiceImpl();

    @Autowired
    ExamService examService = new ExamServiceImpl();

    @Autowired
    DevelopModelService developModelService = new DevelopModelServiceImpl();

    @Autowired
    DevelopSceneService developSceneService = new DevelopSceneServiceImpl();


    public static final String ModelsSubId = "36c8c7c2-8aaf-44c9-b6c8-53e0e3fa3a68";

    public static final String ScenesSubId = "a1411a5a-668c-4eb3-906e-c80e491b5093";

    private static final Logger logger = LogManager.getLogger(UserExcelController.class);


    // 用户信息批量导入
    @RequestMapping(value = "getFile.action", method = {RequestMethod.POST})
    @ResponseBody
    public void getFile(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam("file") MultipartFile file) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String getDate = simpleDateFormat.format(date);

        Date endDay = new Date();

        //创建Calendar实例
        Calendar cal = Calendar.getInstance();
        cal.setTime(endDay);   //设置当前时间
        cal.add(Calendar.MONTH, 1);
        String dateEndStr = simpleDateFormat.format(cal.getTime());

        String filePath = request.getServletContext().getRealPath("/");
        String fileName = file.getOriginalFilename();
        String fileWay = filePath + fileName;
        String studentId = usersService.selRole();
        try {
            file.transferTo(new File(fileWay));
            ExcelClass excelClass = new ExcelClass();
            List<Map<String, Object>> sheetArray = excelClass.getExcelContent(fileWay);
            if (new File(fileWay).exists()) {
                new File(fileWay).delete();
            }
            List<String> errList = new ArrayList<>();
            for (Map<String, Object> sheetMap : sheetArray) {
                List<CellObject> objectList = new ArrayList<CellObject>();
                String sheetName = sheetMap.get("sheetName").toString();
                String classId = usersService.seleExcelClassId(sheetName);
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> sheetList = (List<Map<String, Object>>) sheetMap.get("sheetContent");
                response.setCharacterEncoding("UTF-8");
                for (Map<String, Object> cellMap : sheetList) {
                    CellObject cellObject = new CellObject();
                    // System.out.println("姓名:"+cellMap.get("姓名")+"用戶名:"+cellMap.get("用户名")+"密碼:"+cellMap.get("密码")+"性別:"+cellMap.get("性别")+"出生日期:"+cellMap.get("出生日期"));
                    if (cellMap.get("姓名") == null || cellMap.get("用户名") == null || cellMap.get("密码") == null
                            || cellMap.get("性别") == null) {
                        response.getWriter().print("含有空列，请检查！用户表单不允许有空列！");
                        return;
                    }
                    cellObject.setName(cellMap.get("姓名").toString());
                    cellObject.setUserName(cellMap.get("用户名").toString());
                    cellObject.setPassWord(cellMap.get("密码").toString());
                    cellObject.setSex(cellMap.get("性别").toString());
                    cellObject.setId(UUID.randomUUID().toString());
                    cellObject.setRoleId(studentId);
                    cellObject.setClassId(classId);
                    cellObject.setCreateDate(getDate);
                    cellObject.setIdol(dateEndStr);
                    objectList.add(cellObject);
                }
                errList.addAll(usersService.insUserList(objectList));
            }
            response.setCharacterEncoding("UTF-8");
            if (errList.size() == 0) {
                response.getWriter().print("完全成功");
                return;
            } else {
                String errMessage = "导入成功，以下账号已存在:";
                for (String string : errList) {
                    errMessage += string + ",";
                }
                errMessage += "无法导入";
                response.getWriter().print(errMessage);
                return;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 试题信息批量导入
    @RequestMapping(value = "getQuestion.action", method = {RequestMethod.POST})
    @ResponseBody
    public void getQuestion2(HttpServletRequest request, HttpServletResponse response,
                             @RequestParam("file") MultipartFile file, @RequestParam("knowId") String knowId) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String getDate = simpleDateFormat.format(date);
        String filePath = request.getServletContext().getRealPath("/");
        String fileName = file.getOriginalFilename();
        String fileWay = filePath + fileName;
        String daan = "";
        String daan_duoxuan = "";
        try {
            file.transferTo(new File(fileWay));
            ExcelClass excelClass = new ExcelClass();
            List<Map<String, Object>> sheetArray = excelClass.getExcelContent(fileWay);
            if (new File(fileWay).exists()) {
                new File(fileWay).delete();
            }
            for (Map<String, Object> sheetMap : sheetArray) {
                List<Question> objectList = new ArrayList<Question>();
                // 开始读取
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> sheetList = (List<Map<String, Object>>) sheetMap.get("sheetContent");
                for (Map<String, Object> cellMap : sheetList) {
                    Question question = new Question();
                    double fenshu1 = Double.valueOf(cellMap.get("分数").toString());
                    int fenshu = (int) fenshu1;
                    String tigan = cellMap.get("题干").toString();
                    String type = cellMap.get("类型").toString();
                    String nandu = cellMap.get("难度").toString();
                    String jiexi = cellMap.get("解析").toString();
                    if (type.equals("单选题")) {
                        daan = cellMap.get("答案1").toString();
                    } else if (type.equals("多选题")) {
                        daan_duoxuan = cellMap.get("答案1").toString();
                        for (int i = 0; i < daan_duoxuan.length(); i++) {
                            if (i == (daan_duoxuan.length() - 1)) {
                                daan = daan + daan_duoxuan.charAt(i);
                            } else {
                                daan = daan + daan_duoxuan.charAt(i) + "______";
                            }
                        }
                    } else if (type.equals("判断题")) {
                        daan = cellMap.get("答案1").toString();
                        if (daan.equals("正确")) {
                            daan = "A";
                        } else if (daan.equals("错误")) {
                            daan = "B";
                        }
                    } else if (type.equals("填空题")) {
                        if (cellMap.get("答案1") != null) {
                            daan = cellMap.get("答案1").toString();
                        }
                        if (cellMap.get("答案2") != null) {
                            daan = daan + "______" + cellMap.get("答案2").toString();
                        }
                        if (cellMap.get("答案3") != null) {
                            daan = daan + "______" + cellMap.get("答案3").toString();
                        }
                        if (cellMap.get("答案4") != null) {
                            daan = daan + "______" + cellMap.get("答案4").toString();
                        }
                    }
                    // String daan=cellMap.get("答案（填空题多个答案用6个下划线分隔）").toString();
                    String content = "<智能题><难度>" + nandu + "</难度><类型>" + type + "</类型><题干><文字>" + tigan
                            + "</文字><图片></图片></题干><选项列表>";
                    if (type.equals("单选题") || type.equals("多选题") || type.equals("选择题")) {
                        String option = cellMap.get("选项A").toString();
                        System.out.println(cellMap.get("选项B") + "--" + cellMap.get("选项C") + "--" + cellMap.get("题干"));
                        option = option + ";" + cellMap.get("选项B").toString() + ";" + cellMap.get("选项C").toString()
                                + ";" + cellMap.get("选项D").toString();
                        if (cellMap.get("选项E") != null) {
                            option = option + ";" + cellMap.get("选项E").toString();
                        }
                        if (cellMap.get("选项F") != null) {
                            option = option + ";" + cellMap.get("选项F").toString();
                        }
                        if (option != null) {
                            String[] xuanxiang = option.split(";");
                            for (int i = 0; i < xuanxiang.length; i++) {
                                /*content += "<选项" + (i + 1) + "><图片></图片><文字><![CDATA[" + xuanxiang[i] + "]]></文字><选项"
                                        + (i + 1) + ">";*/
                                if (i == 0) {
                                    content += "<选项A><图片></图片><文字>" + xuanxiang[i] + "</文字></选项A>";
                                } else if (i == 1) {
                                    content += "<选项B><图片></图片><文字>" + xuanxiang[i] + "</文字></选项B>";
                                } else if (i == 2) {
                                    content += "<选项C><图片></图片><文字>" + xuanxiang[i] + "</文字></选项C>";
                                } else if (i == 3) {
                                    content += "<选项D><图片></图片><文字>" + xuanxiang[i] + "</文字></选项D>";
                                } else if (i == 4) {
                                    content += "<选项E><图片></图片><文字>" + xuanxiang[i] + "</文字></选项E>";
                                } else if (i == 5) {
                                    content += "<选项F><图片></图片><文字>" + xuanxiang[i] + "</文字></选项F>";
                                }
                            }
                        } // 此处加返回
                    }
                    content += "</选项列表><答案>" + daan + "</答案><解析>" + jiexi + "</解析></智能题>";

                    question.setId(UUID.randomUUID().toString());
                    question.setContent(content);
                    question.setKnowledgeId(knowId);
                    question.setTime(getDate);
                    question.setTitle(tigan);
                    question.setType(type);
                    question.setQuestionImg(nandu);
                    question.setFenshu(fenshu);
                    // question.setJiexi(jiexi);
                    objectList.add(question);
                }
                examService.insQuertion(objectList);
            }
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print("完全成功");
            return;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*// 试题信息批量导入
    @RequestMapping(value = "getQuestion2.action", method = { RequestMethod.POST })
    @ResponseBody
    public void getQuestion(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam("file") MultipartFile file, @RequestParam("knowId") String knowId) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String getDate = simpleDateFormat.format(date);
        String filePath = request.getServletContext().getRealPath("/");
        String fileName = file.getOriginalFilename();
        String fileWay = filePath + fileName;
        String daan = "";
        String daan_duoxuan = "";
        try {
            file.transferTo(new File(fileWay));
            ExcelClass excelClass = new ExcelClass();
            List<Map<String, Object>> sheetArray = excelClass.getExcelContent(fileWay);
            if (new File(fileWay).exists()) {
                new File(fileWay).delete();
            }
            for (Map<String, Object> sheetMap : sheetArray) {
                List<Question> objectList = new ArrayList<Question>();
                // 开始读取
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> sheetList = (List<Map<String, Object>>) sheetMap.get("sheetContent");
                for (Map<String, Object> cellMap : sheetList) {
                    Question question = new Question();
                    double fenshu1 = Double.valueOf(cellMap.get("分数").toString());
                    int fenshu = (int) fenshu1;
                    String tigan = cellMap.get("题干").toString();
                    String type = cellMap.get("类型").toString();
                    String nandu = cellMap.get("难度").toString();
                    if (type.equals("单选题")) {
                        daan = cellMap.get("答案1").toString();
                    } else if (type.equals("多选题")) {
                        daan_duoxuan = cellMap.get("答案1").toString();
                        for (int i = 0; i < daan_duoxuan.length(); i++) {
                            if (i == (daan_duoxuan.length() - 1)) {
                                daan = daan + daan_duoxuan.charAt(i);
                            } else {
                                daan = daan + daan_duoxuan.charAt(i) + "______";
                            }
                        }
                    } else if (type.equals("判断题")) {
                        daan = cellMap.get("答案1").toString();
                        if (daan.equals("正确")) {
                            daan = "A";
                        } else if (daan.equals("错误")) {
                            daan = "B";
                        }
                    } else if (type.equals("填空题")) {
                        if (cellMap.get("答案1") != null) {
                            daan = cellMap.get("答案1").toString();
                        }
                        if (cellMap.get("答案2") != null) {
                            daan = daan + "______" + cellMap.get("答案2").toString();
                        }
                        if (cellMap.get("答案3") != null) {
                            daan = daan + "______" + cellMap.get("答案3").toString();
                        }
                        if (cellMap.get("答案4") != null) {
                            daan = daan + "______" + cellMap.get("答案4").toString();
                        }
                    }
                    // String daan=cellMap.get("答案（填空题多个答案用6个下划线分隔）").toString();
                    String content = "<智能题><难度>" + nandu + "</难度><类型>" + type + "</类型><题干><文字>" + tigan
                            + "</文字><图片></图片></题干>";
                    if (type.equals("单选题") || type.equals("多选题") || type.equals("选择题")) {
                        String option = cellMap.get("选项A").toString();
                        option = option + ";" + cellMap.get("选项B").toString() + ";" + cellMap.get("选项C").toString()
                                + ";" + cellMap.get("选项D").toString();
                        if (cellMap.get("选项E") != null) {
                            option = option + ";" + cellMap.get("选项E").toString();
                        }
                        if (cellMap.get("选项F") != null) {
                            option = option + ";" + cellMap.get("选项F").toString();
                        }
                        if (option != null) {
                            String[] xuanxiang = option.split(";");
                            for (int i = 0; i < xuanxiang.length; i++) {
                                content += "<选项" + i + "><图片></图片><文字>" + xuanxiang[i] + "</文字><选项" + i + ">";
                            }
                        } // 此处加返回
                    }
                    content += "<答案>" + daan + "</答案><解析></解析></智能题>";

                    question.setId(UUID.randomUUID().toString());
                    question.setContent(content);
                    question.setKnowledgeId(knowId);
                    question.setTime(getDate);
                    question.setTitle(tigan);
                    question.setType(type);
                    question.setQuestionImg(nandu);
                    question.setFenshu(fenshu);
                    objectList.add(question);
                }
                examService.insQuertion(objectList);
            }
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print("完全成功");
            return;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 编辑器模型上传
     *
     * @param request
     * @param response
     * @param file
     */

    @RequestMapping(value = "getFileForModel.action", method = {RequestMethod.POST})
    @ResponseBody
    public void getFileForModel(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam("file") MultipartFile file) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String getDate = simpleDateFormat.format(date);
        String filePath = request.getServletContext().getRealPath("/");
        String itemPath = "../../workspaces/diesel/temp/";
        String fileName = file.getOriginalFilename();
        String fileWay = itemPath + fileName;
        // String studentId=usersService.selRole();
        if (!new File(fileWay).exists()) {
            new File(fileWay).mkdirs();
        }
        try {
            // 添加文件
            file.transferTo(new File(fileWay));
            // ExcelClass excelClass=new ExcelClass();
            // List<Map<String, Object>> sheetArray=excelClass.getExcelContent(fileWay);
            // if (new File(fileWay).exists()) {
            // new File(fileWay).delete();
            // }
            // List<String> errList=new ArrayList<>();
            // for (Map<String, Object> sheetMap : sheetArray) {
            // List<CellObject> objectList=new ArrayList<CellObject>();
            // String sheetName=sheetMap.get("sheetName").toString();
            // String classId=usersService.seleExcelClassId(sheetName);
            // @SuppressWarnings("unchecked")
            // List<Map<String, Object>> sheetList=(List<Map<String, Object>>)
            // sheetMap.get("sheetContent");
            // response.setCharacterEncoding("UTF-8");
            // for (Map<String, Object> cellMap : sheetList) {
            // CellObject cellObject=new CellObject();
            // if
            // (cellMap.get("姓名")==null||cellMap.get("用户名")==null||cellMap.get("密码")==null||cellMap.get("性别")==null)
            // {
            // response.getWriter().print("含有空列，请检查！用户表单不允许有空列！");
            // return;
            // }
            // cellObject.setName(cellMap.get("姓名").toString());
            // cellObject.setUserName(cellMap.get("用户名").toString());
            // cellObject.setPassWord(cellMap.get("密码").toString());
            // cellObject.setSex(cellMap.get("性别").toString());
            // cellObject.setId(UUID.randomUUID().toString());
            // cellObject.setRoleId(studentId);
            // cellObject.setClassId(classId);
            // cellObject.setCreateDate(getDate);
            // objectList.add(cellObject);
            // }
            // errList.addAll(usersService.insUserList(objectList));
            // }
            // response.setCharacterEncoding("UTF-8");
            // if (errList.size()==0) {
            // response.getWriter().print("完全成功");
            // return;
            // } else {
            // String errMessage="导入成功，以下账号已存在:";
            // for (String string : errList) {
            // errMessage+=string+",";
            // }
            // errMessage+="无法导入";
            // response.getWriter().print(errMessage);
            // return;
            // }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 编辑器开发者平台上传功能
     *
     * @param request
     * @param response
     * @param file
     */
    @RequestMapping(value = "getFileForTdu.action", method = {RequestMethod.POST})
    @ResponseBody
    public void getFileForTdu(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                              @RequestParam("file") MultipartFile[] file,@RequestParam("modelinfo") String modelinfo) {
        Date date = new Date();
        // 获取日期时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String getDate = simpleDateFormat.format(date);
        // 获取服务器路径(tomcat)
        String filePath = request.getServletContext().getRealPath("/");
        String itemPath = "../../workspaces/diesel/temp/";
        logger.info(" modelinfo :" + modelinfo);

        // 固定服务器路径
        //String testUrl="D:/working/TDuClub/TDu/Data/3D/";

        // 公司服务器路径 D:\working\TDuClub\TDu\Data\3D
        String testUrl = "/www/wwwroot/tdu.tduvr.club/Data/3D/Model";

        String[] modelArray = modelinfo.split("\\|");

        if ( modelArray.length != 5) {
            logger.debug("文件名或modelinfo拆分后长度未达到5！");
            throw new RuntimeException("文件名或modelinfo拆分后长度未达到5！");
        }

        String id = modelArray[0];
        String Name = modelArray[1];// session.getAttribute("ID").toString();
        String userkey = modelArray[2];
        String ContactKey = modelArray[3];
        String subtreeId = modelArray[4];

        String LatFileName = "";

            try {
                for (MultipartFile f:file) {
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
                            LatFileName = id + suffix;
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
                        //f.transferTo(new File(realPath));
                        updatePdf(f, realPath, userkey);
                        System.out.println("sssss   ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                /* 模型和Knowleg 以及Knowlegcontent 同步添加 */

                    Modelcontents modelcontents = new Modelcontents();
                    // knowlegcontent.setNmae(Name);
                    modelcontents.setCustomName(Name);
                    modelcontents.setNmae(LatFileName);
                    modelcontents.setCustomStyle("default");
                    modelcontents.setId(id);
                    modelcontents.setType("模型");
                    modelcontents.setUserKey(userkey);
                    modelcontents.setUrl(testUrl);
                    modelcontents.setPhotoName("preview.jpg");
                    //subjectID
                    String nodeId = ContactKey;
                    if (developModelService.getModelContentName(id) == null) {
                        developModelService.AddModelsContent2(modelcontents, subtreeId, nodeId);
                        //ads.AddModelsContent2(modelcontents, ModelsSubId, nodeId);
                    }

                        developModelService.updateModelscontentCustomName(id, LatFileName, "preview.jpg");
                        // msi.updateModelFileName(id,LatFileName);

            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
    }

    public void updatePdf(MultipartFile mf, String realPath, String userKey) throws IOException {
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
        String filePar = "D:/wamp/www/Data/3D/Model/"+userKey+wenjianPath;
        //String filePar = "/www/wwwroot/tdu.tduvr.club/Data/3D/Model/" + userKey + wenjianPath;

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
        String filename = "D:/wamp/www/Data/3D/Model/"+""+userKey+""+"/"+realPath;;// 文件名
        //String filename = "/www/wwwroot/tdu.tduvr.club/Data/3D/Model/" + "" + userKey + "" + "/" + realPath;
        ;// 文件名

        //String filename = "\\home\\working\\tdu.tduvr.club\\Data\\3D\\Model\\"+""+userKey+""+"\\"+realPath;;// 文件名
//String name = "D:/working/TDuClub/TDu/Data/3D/Model/"+""+userKey+""+"/"+realPath;
        //新
        String name = "D:/wamp/www/Data/3D/Model/"+""+userKey+""+"/"+realPath;
        //String name = "/www/wwwroot/tdu.tduvr.club/Data/3D/Model/" + "" + userKey + "" + "/" + realPath;
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



    @RequestMapping(value = "setVersion.action", method = {RequestMethod.GET})
    @ResponseBody
    public void setVersion(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //只需关注路径
        //String path="D:\\wamp\\www\\TDu\\Data\\3D\\Model\\4d272f66-9dac-4b87-a2a1-22b6e5910779\\f540168f-1a32-bc3b-6b36-3d9b399034c1";//"D:\\working\\TDuClub\\TDu\\Data\\3D\\Model\\4d272f66-9dac-4b87-a2a1-22b6e5910779\\a3a673d6-a69e-42b0-9c42-e8eaa507746c";
        String path = request.getParameter("LocalPath");
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
