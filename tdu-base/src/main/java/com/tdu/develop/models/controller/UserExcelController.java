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
import java.io.File;
import java.io.IOException;
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
                              @RequestParam("file") MultipartFile file) {
        Date date = new Date();
        // 获取日期时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String getDate = simpleDateFormat.format(date);
        // 获取服务器路径(tomcat)
        String filePath = request.getServletContext().getRealPath("/");
        String itemPath = "../../workspaces/diesel/temp/";
        // 文件名称
        // String fileName=file.getOriginalFilename();
        String fileName = request.getParameter("filename");
        //System.out.println(fileName);
        String modelinfo = request.getParameter("modelinfo");
        //System.out.println(modelinfo);
        logger.info(" fileName :" + fileName);
        logger.info(" modelinfo :" + modelinfo);

        // 固定服务器路径
        //String testUrl="D:/working/TDuClub/TDu/Data/3D/";

        // 公司服务器路径 D:\working\TDuClub\TDu\Data\3D
        String testUrl = "/www/wwwroot/tdu.tduvr.club/Data/3D/";

        // 测试路径
        //   String testUrl="D:/testup/";
        String[] nameArray = fileName.split("\\|");
        String[] modelArray = modelinfo.split("\\|");

        if (nameArray.length != 5 || modelArray.length != 5) {
            logger.debug("文件名或modelinfo拆分后长度未达到5！");
            throw new RuntimeException("文件名或modelinfo拆分后长度未达到5！");
        }

        String[] gethouzhui = fileName.split("\\.");
        String str = gethouzhui[(gethouzhui.length - 1)];
        //System.out.println("str   :" + str);
        logger.info(" str :" + str);
        // System.out.println("nameArray[4] :"+nameArray[4]);
        // System.out.println("modelArray[4] :"+modelArray[4]);
        if (nameArray.length > 3) {
            // 用户id
            String name = nameArray[0];
            //System.out.println("用户id  :" + nameArray[0]);
            // 模型或者场景id
            String modelOrView = nameArray[1];
            //System.out.println("模型或者场景id  :" + nameArray[1]);
            // 文件名
            String UPfileName = nameArray[2];
            //System.out.println("文件名  :" + nameArray[2]);
            // model和view路径获取
            String UPfileName1 = nameArray[3];
            //System.out.println("model和view路径获取  :" + nameArray[3]);
            String userId = (String) session.getAttribute("ID");
            //System.out.println("nameArray[4]  :" + nameArray[4]);
            String[] fileArray = nameArray[4].split("\\.");

            // 获取文件名称
            String LatFileName = UPfileName + "." + str; // str;

            //System.out.println("UPfileName  :" + UPfileName);
            //System.out.println("LatFileName  :" + LatFileName);

            //System.out.println("fileArray  :" + fileArray);
            //System.out.println("fileArray[0]  :" + fileArray[0]);
            //System.out.println("LatFileName   :" + LatFileName);

            //System.out.println("UPfileName1  ------- :" + UPfileName1);

            // 查询文件夹路径是否存在
            String trueUrl = "";
            if (fileArray[0].equals("null")) {
                trueUrl = testUrl + UPfileName1 + "/" + name + "/" + modelOrView + "/";
            } else {
                trueUrl = testUrl + UPfileName1 + "/" + name + "/" + modelOrView + "/" + fileArray[0] + "/";
            }

            // String studentId=usersServiceImp.selRole();

            // 文件路径
            String fileUrlPath = trueUrl + LatFileName;
            logger.info(" fileUrlPath :" + fileUrlPath);
            // String studentId=usersServiceImp.selRole();
            if (!new File(trueUrl).exists()) {
                logger.info(" fileUrlPath :" + fileUrlPath);
                logger.info(" new File(trueUrl).exists() :" + new File(trueUrl).exists());
                new File(trueUrl).mkdirs();
            }
            try {
                String id = modelArray[0];
                String Name = modelArray[1];// session.getAttribute("ID").toString();
                String userkey = modelArray[2];
                String ContactKey = modelArray[3];
                String subtreeId = modelArray[4];

                if (str.equals("tdc") || str.equals("tdb") || str.equals("exm") || str.equals("EXM")) {
                    LatFileName = modelOrView + "." + str;
                    fileUrlPath = trueUrl + "/" + LatFileName;
                }
                logger.info(" fileUrlPath :" + fileUrlPath);
                // 添加文件
                file.transferTo(new File(fileUrlPath));
                /* 模型和Knowleg 以及Knowlegcontent 同步添加 */
                if (UPfileName1.equals("Model")) {
                    Models model = new Models();
                    model.setId(id);
                    model.setName(Name);
                    model.setType("模型");
                    model.setUserKey(userkey);
                    model.setPhotoName("preview.jpg");
                    Modelcontents modelcontents = new Modelcontents();
                    // knowlegcontent.setNmae(Name);
                    modelcontents.setCustomName(Name);

                    if (str.equals("tdc") || str.equals("tdb")) {
                        modelcontents.setNmae(LatFileName);
                    } else {
                        modelcontents.setNmae(Name);
                    }
                    modelcontents.setCustomStyle("default");
                    modelcontents.setId(id);
                    modelcontents.setType("模型");
                    modelcontents.setUserKey(userkey);
                    modelcontents.setUrl(testUrl);
                    modelcontents.setPhotoName("preview.jpg");
                    String nodeId = ContactKey;
                    if (developModelService.getModelContentName(id) == null) {
                        developModelService.AddModelsContent2(modelcontents, subtreeId, nodeId);
                        //ads.AddModelsContent2(modelcontents, ModelsSubId, nodeId);
                    }
                    if (str.equals("tdc") || str.equals("tdb")) {
                        developModelService.updateModelscontentCustomName(id, LatFileName, "preview.jpg");
                        // msi.updateModelFileName(id,LatFileName);
                    }
                } else if (UPfileName1.equals("Scene")) {
					/*String id = modelArray[0];
					String Name = modelArray[1];// session.getAttribute("ID").toString();
					String userkey = modelArray[2];
					String ContactKey = modelArray[3];
					String subtreeId = modelArray[4];*/

                    Models model = new Models();
                    // model.setContactKey("7fb53e8f-baf1-4c1e-8868-bad634e81461");
                    model.setId(modelOrView);
                    // 目前用文件名
                    model.setName(UPfileName);

                    model.setType("场景");
                    model.setUserKey(name);
                    model.setPhotoName("preview.jpg");
                    Scenecontents scenecontents = new Scenecontents();
                    // knowlegcontent.setNmae(Name);
                    scenecontents.setCustomName(Name);

                    if (str.equals("EXM") || str.equals("exm")) {
                        scenecontents.setNmae(LatFileName);
                    } else {
                        scenecontents.setNmae(UPfileName);
                    }
                    scenecontents.setCustomStyle("default");
                    scenecontents.setId(modelOrView);
                    scenecontents.setType("场景");
                    scenecontents.setUserKey(name);
                    scenecontents.setUrl(testUrl);
                    // scenecontents.setPhotoName("preview.jpg");
                    String nodeId = ContactKey;
                    // String nodeId="2cb52724-a23b-42ad-9f80-d537af993c9e";
                    if (developSceneService.getSceneContentName(modelOrView) == null) {
                        developSceneService.AddScenesContent2(scenecontents, subtreeId, nodeId);
                        //ads.AddScenesContent2(scenecontents, ScenesSubId, nodeId);
                    }
                    if (str.equals("EXM") || str.equals("exm")) {
                        developSceneService.updateScenescontentCustomName(modelOrView, LatFileName, null);
                        // adsi.updateScenescontentCustomName(modelOrView,LatFileName,"preview.jpg");
                        // msi.updateModelFileName(modelOrView,LatFileName);
                    }
                }

                // ExcelClass excelClass=new ExcelClass();
                // List<Map<String, Object>> sheetArray=excelClass.getExcelContent(fileWay);
                // if (new File(fileWay).exists()) {
                // new File(fileWay).delete();
                // }
                // List<String> errList=new ArrayList<>();
                // for (Map<String, Object> sheetMap : sheetArray) {
                // List<CellObject> objectList=new ArrayList<CellObject>();
                // String sheetName=sheetMap.get("sheetName").toString();
                // String classId=usersServiceImp.seleExcelClassId(sheetName);
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
                // errList.addAll(usersServiceImp.insUserList(objectList));
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
        } else {

        }
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
