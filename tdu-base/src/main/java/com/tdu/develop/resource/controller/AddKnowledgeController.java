package com.tdu.develop.resource.controller;

import com.tdu.develop.resource.pojo.HtmlPage;
import com.tdu.develop.resource.pojo.Knowlegcontent;
import com.tdu.develop.resource.service.AddKnowledgeService;
import com.tdu.develop.resource.service.HtmlPageService;
import com.tdu.develop.resource.service.impl.AddKnowledgeServiceImpl;
import com.tdu.develop.resource.service.impl.HtmlPageServiceImpl;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author 志阳
 * @create 2019-08-27-17:18
 */
@CrossOrigin
@Controller
@RequestMapping(value = "AddKnowledgeController")
public class AddKnowledgeController {

    @Autowired
    public AddKnowledgeService addKnowledgeService = new AddKnowledgeServiceImpl();

    @Autowired
    public HtmlPageService htmlPageService = new HtmlPageServiceImpl();

    /*
     *  添加报告
     * */
    @RequestMapping(value = "insertPageForJY.action", method = RequestMethod.POST)
    @ResponseBody
    public void insertPageForJY(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        HtmlPage htmlPage = new HtmlPage();
        String userKey = (String) session.getAttribute("ID");
        //String userKey = request.getParameter("userkey");
        String htmlStr = request.getParameter("htmlstr");
        String name = request.getParameter("name");
        String input_1 = request.getParameter("input_1");
        String input_2 = request.getParameter("input_2");
        String input_3 = request.getParameter("input_3");
        String input_4 = request.getParameter("input_4");
        String input_5 = request.getParameter("input_5");
        String input_6 = request.getParameter("input_6");
        String input_7 = request.getParameter("input_7");
        String input_8 = request.getParameter("input_8");
        String input_9 = request.getParameter("input_9");
        String input_10 = request.getParameter("input_10");
        String input_11 = request.getParameter("input_11");
        String input_12 = request.getParameter("input_12");
        String input_13 = request.getParameter("input_13");
        String input_14 = request.getParameter("input_14");
        String input_15 = request.getParameter("input_15");
        String input_16 = request.getParameter("input_16");
        String input_17 = request.getParameter("input_17");

        htmlPage.setInput_1(input_1);
        htmlPage.setInput_2(input_2);
        htmlPage.setInput_3(input_3);
        htmlPage.setInput_4(input_4);
        htmlPage.setInput_5(input_5);
        htmlPage.setInput_6(input_6);
        htmlPage.setInput_7(input_7);
        htmlPage.setInput_8(input_8);
        htmlPage.setInput_9(input_9);
        htmlPage.setInput_10(input_10);
        htmlPage.setInput_11(input_11);
        htmlPage.setInput_12(input_12);
        htmlPage.setInput_13(input_13);
        htmlPage.setInput_14(input_14);
        htmlPage.setInput_15(input_15);
        htmlPage.setInput_16(input_16);
        htmlPage.setInput_17(input_17);

        String textarea_1 = request.getParameter("textarea_1");
        String textarea_2 = request.getParameter("textarea_2");
        String textarea_3 = request.getParameter("textarea_3");
        String textarea_4 = request.getParameter("textarea_4");
        String textarea_5 = request.getParameter("textarea_5");
        String textarea_6 = request.getParameter("textarea_6");
        String textarea_7 = request.getParameter("textarea_7");
        String textarea_8 = request.getParameter("textarea_8");
        String textarea_9 = request.getParameter("textarea_9");
        htmlPage.setTextarea_1(textarea_1);
        htmlPage.setTextarea_2(textarea_2);
        htmlPage.setTextarea_3(textarea_3);
        htmlPage.setTextarea_4(textarea_4);
        htmlPage.setTextarea_5(textarea_5);
        htmlPage.setTextarea_6(textarea_6);
        htmlPage.setTextarea_7(textarea_7);
        htmlPage.setTextarea_8(textarea_8);
        htmlPage.setTextarea_9(textarea_9);
        String select_1 = request.getParameter("select_1");
        String select_2 = request.getParameter("select_2");
        String select_3 = request.getParameter("select_3");
        String select_4 = request.getParameter("select_4");
        String select_5 = request.getParameter("select_5");

        htmlPage.setSelect_1(select_1);
        htmlPage.setSelect_2(select_2);
        htmlPage.setSelect_3(select_3);
        htmlPage.setSelect_4(select_4);
        htmlPage.setSelect_5(select_5);
        htmlPage.setSelect_6("暂无");
        htmlPage.setSelect_7("暂无");
        htmlPage.setId(UUID.randomUUID().toString());
        htmlPage.setUserkey(userKey);
        htmlPage.setHtmlstr(htmlStr);
        htmlPage.setName(name);
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = df.format(d);
        htmlPage.setCreattime(dateNowStr);
        htmlPageService.insertHtmlPage(htmlPage);
    }

    /*
     *  添加报告
     * */
    @RequestMapping(value = "insertPage.action", method = RequestMethod.POST)
    @ResponseBody
    public void insertPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HtmlPage htmlPage = new HtmlPage();
        String userKey = request.getParameter("userkey");
        String htmlStr = request.getParameter("htmlstr");
        String name = request.getParameter("name");
        String input_1 = request.getParameter("input_1");
        String input_2 = request.getParameter("input_2");
        String input_3 = request.getParameter("input_3");
        String input_4 = request.getParameter("input_4");
        String input_5 = request.getParameter("input_5");
        String input_6 = request.getParameter("input_6");
        String input_7 = request.getParameter("input_7");
        String input_8 = request.getParameter("input_8");
        String input_9 = request.getParameter("input_9");
        String input_10 = request.getParameter("input_10");
        String input_11 = request.getParameter("input_11");
        String input_12 = request.getParameter("input_12");
        String input_13 = request.getParameter("input_13");
        String input_14 = request.getParameter("input_14");
        String input_15 = request.getParameter("input_15");
        String input_16 = request.getParameter("input_16");
        String input_17 = request.getParameter("input_17");

        htmlPage.setInput_1(input_1);
        htmlPage.setInput_2(input_2);
        htmlPage.setInput_3(input_3);
        htmlPage.setInput_4(input_4);
        htmlPage.setInput_5(input_5);
        htmlPage.setInput_6(input_6);
        htmlPage.setInput_7(input_7);
        htmlPage.setInput_8(input_8);
        htmlPage.setInput_9(input_9);
        htmlPage.setInput_10(input_10);
        htmlPage.setInput_11(input_11);
        htmlPage.setInput_12(input_12);
        htmlPage.setInput_13(input_13);
        htmlPage.setInput_14(input_14);
        htmlPage.setInput_15(input_15);
        htmlPage.setInput_16(input_16);
        htmlPage.setInput_17(input_17);

        String textarea_1 = request.getParameter("textarea_1");
        String textarea_2 = request.getParameter("textarea_2");
        String textarea_3 = request.getParameter("textarea_3");
        String textarea_4 = request.getParameter("textarea_4");
        String textarea_5 = request.getParameter("textarea_5");
        String textarea_6 = request.getParameter("textarea_6");
        String textarea_7 = request.getParameter("textarea_7");
        String textarea_8 = request.getParameter("textarea_8");
        String textarea_9 = request.getParameter("textarea_9");
        htmlPage.setTextarea_1(textarea_1);
        htmlPage.setTextarea_2(textarea_2);
        htmlPage.setTextarea_3(textarea_3);
        htmlPage.setTextarea_4(textarea_4);
        htmlPage.setTextarea_5(textarea_5);
        htmlPage.setTextarea_6(textarea_6);
        htmlPage.setTextarea_7(textarea_7);
        htmlPage.setTextarea_8(textarea_8);
        htmlPage.setTextarea_9(textarea_9);
        String select_1 = request.getParameter("select_1");
        String select_2 = request.getParameter("select_2");
        String select_3 = request.getParameter("select_3");
        String select_4 = request.getParameter("select_4");
        String select_5 = request.getParameter("select_5");

        htmlPage.setSelect_1(select_1);
        htmlPage.setSelect_2(select_2);
        htmlPage.setSelect_3(select_3);
        htmlPage.setSelect_4(select_4);
        htmlPage.setSelect_5(select_5);
        htmlPage.setSelect_6("暂无");
        htmlPage.setSelect_7("暂无");
        htmlPage.setId(UUID.randomUUID().toString());
        htmlPage.setUserkey(userKey);
        htmlPage.setHtmlstr(htmlStr);
        htmlPage.setName(name);
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = df.format(d);
        htmlPage.setCreattime(dateNowStr);
        htmlPageService.insertHtmlPage(htmlPage);
    }

    /*
     *  查询报告
     * */
    @RequestMapping(value = "getPageForJY.action", method = RequestMethod.POST)
    @ResponseBody
    public HtmlPage getPageForJY(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        //String userKey = request.getParameter("userkey");
        String userKey = (String) session.getAttribute("ID");
        String name = request.getParameter("name");
        HtmlPage htmlPage = htmlPageService.getHtmlPage(userKey, name);
        return htmlPage;
    }

    /*
     *  查询报告
     * */
    @RequestMapping(value = "getPage.action", method = RequestMethod.POST)
    @ResponseBody
    public HtmlPage getPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userKey = request.getParameter("userkey");
        String name = request.getParameter("name");
        HtmlPage htmlPage = htmlPageService.getHtmlPage(userKey, name);
        return htmlPage;
    }


    /**
     * 删除本地临时文件
     *
     * @param file
     */
    public static void delteTempFile(File file) {
        if (file != null) {
            File del = new File(file.toURI());
            del.delete();
        }
    }

    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {
        InputStream ins = null;
        String path = "/www/wwwroot/tdu.tduvr.club/Data/pic/";
        //String path = "D:\\wamp\\www\\Data\\pic\\";
        if (!new File(path).exists()) {
            System.out.println(" fileUrlPath :" + path);
            System.out.println(" new File(trueUrl).exists() :" + new File(path).exists());
            boolean mkdirs = new File(path).mkdirs();
            System.out.println("创建目录返回结果：" + mkdirs);
            System.out.println("创建文件夹路径为：" + path);
        }

        File writeFile = new File(path + file.getOriginalFilename());
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            ins = file.getInputStream();
            try {
                BufferedInputStream bis = new BufferedInputStream(ins);
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
            /*OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();*/
            } catch (Exception e) {
                e.printStackTrace();
            }
            ins.close();
        }
        return writeFile;
    }

    /**
     * @param file      源文件(图片)
     * @param waterFile 水印文件(图片)
     * @param x         距离右下角的X偏移量
     * @param y         距离右下角的Y偏移量
     * @param alpha     透明度, 选择值从0.0~1.0: 完全透明~完全不透明
     * @return BufferedImage
     * @throws IOException
     * @Title: 构造图片
     * @Description: 生成水印并返回java.awt.image.BufferedImage
     */
    public BufferedImage watermark(File file, File waterFile, int x, int y, float alpha) throws IOException {
        // 获取底图
        BufferedImage buffImg = ImageIO.read(file);
        // 获取层图
        BufferedImage waterImg = ImageIO.read(waterFile);
        // 创建Graphics2D对象，用在底图对象上绘图
        Graphics2D g2d = buffImg.createGraphics();
        int waterImgWidth = waterImg.getWidth();// 获取层图的宽度
        int waterImgHeight = waterImg.getHeight();// 获取层图的高度
        // 在图形和图像中实现混合和透明效果
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        // 绘制
        g2d.drawImage(waterImg, x, y, waterImgWidth, waterImgHeight, null);
        g2d.dispose();// 释放图形上下文使用的系统资源
        return buffImg;
    }

    /**
     * 输出水印图片
     *
     * @param buffImg  图像加水印之后的BufferedImage对象
     * @param savePath 图像加水印之后的保存路径
     */
    private void generateWaterFile(BufferedImage buffImg, String savePath) {
        int temp = savePath.lastIndexOf(".") + 1;
        try {
            ImageIO.write(buffImg, savePath.substring(temp), new File(savePath));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /*
     * 色度键控图片下载
     * */
    @RequestMapping(value = "downloadImage.action", method = RequestMethod.POST)
    @ResponseBody
    public void downloadImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String imgPath = request.getParameter("imgPath");
        System.out.println(" imgPath : " + imgPath);
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(new File(imgPath)));
        //3下载名称
        String filename = "";
        filename = System.currentTimeMillis() + ".png";
        //4设置下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //5设置文件contentType类型
        response.setContentType("multipart/form-data");
        //6输出流
        BufferedOutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        IOUtils.copy(inputStream, outputStream);
        int len = 0;
        while ((len = inputStream.read()) != -1) {
            outputStream.write(len);
        }
        outputStream.close();
        inputStream.close();
    }


    /*
     * 色度键控处理图片以及合成
     * */
    @RequestMapping(value = "testAdd.action", method = RequestMethod.POST)
    @ResponseBody
    public String testAdd(@RequestParam("userKey")String userKey,@RequestParam("file0") MultipartFile file1, @RequestParam("file1") MultipartFile file2, @RequestParam("mubucolor") String color, HttpServletRequest request) throws Exception {

        File newFile1 = multipartFileToFile(file1);
        File newFile2 = multipartFileToFile(file2);

        System.out.println(" color------ :" + color);

        int[] rgb = new int[3];
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(newFile2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int width = bi.getWidth();
        int height = bi.getHeight();
        int minx = bi.getMinX();
        int miny = bi.getMinY();
        ImageIcon imageIcon = new ImageIcon(bi);
        int w = imageIcon.getIconWidth();
        int h = imageIcon.getIconHeight();
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
        g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon.getImageObserver());
        int alpha = 0;
        System.out.println("正在处理：" + newFile2.getName());
//         黄色 R-B>60 G-B>60
//         红色 R-G>60 R-B>60
//         绿色 G-R>30 G-B>30
//         蓝色 B-G>60 B-R>60
//         黑色 R<40 G<40 B<40
//         白色 R>230 G>230 B>230
        for (int i = bufferedImage.getMinX(); i < w; i++) {
            for (int j = bufferedImage.getMinY(); j < h; j++) {
                int pixel = bufferedImage.getRGB(i, j);
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                //绿色   0 128 0
                if (color.equals("green")) {
                    if (rgb[1] - rgb[0] > 30 && rgb[1] - rgb[2] > 30) {
                        bufferedImage.setRGB(i, j, 0x00ffffff);
                    }
                }
                //蓝色   0 0 255
                if (color.equals("blue")) {
                    if (rgb[2] - rgb[1] > 60 && rgb[2] - rgb[0] > 60) {
                        bufferedImage.setRGB(i, j, 0x00ffffff);
                    }
                }
                //红色  255 0 0
                if (color.equals("red")) {
                    if (rgb[0] - rgb[1] > 60 && rgb[0] - rgb[2] > 60) {
                        bufferedImage.setRGB(i, j, 0x00ffffff);
                    }
                }
                //黄色 255 255 0
                if (color.equals("yellow")) {
                    if (rgb[0] - rgb[2] > 60 && rgb[1] - rgb[2] > 60) {
                        bufferedImage.setRGB(i, j, 0x00ffffff);
                    }
                }
                //白色 255 255 255
                if (color.equals("white")) {
                    if (rgb[0] > 230 && rgb[1] > 230 && rgb[2] > 230) {
                        bufferedImage.setRGB(i, j, 0x00ffffff);
                    }
                }
                //黑色 0 0 0
                if (color.equals("black")) {
                    if (rgb[0] < 40 && rgb[1] < 40 && rgb[2] < 40) {
                        bufferedImage.setRGB(i, j, 0x00ffffff);
                    }
                }
            }

        }

        System.out.println("\t处理完毕：" + newFile2.getName());
        System.out.println();
        g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
        File f = null;
        try {
            // 创建临时文件
            f = File.createTempFile("tmp", ".png", new File("/www/wwwroot/tdu.tduvr.club/Data/pic/"));
            // 输出绝对路径
            System.out.println("File path: " + f.getAbsolutePath());
        } catch (Exception e) {
            // 如果有错误输出内容
            e.printStackTrace();
        }
        FileOutputStream ops = new FileOutputStream(f);//new File("C:\\Users\\TDU\\Desktop\\tu\\a.png")
        ImageIO.write(bufferedImage, "png", ops);
        ops.flush();
        ops.close();

        //String UUid = UUID.randomUUID().toString();
        // 公司服务器路径
        String saveFilePath = "/www/wwwroot/tdu.tduvr.club/Data/pic/";
        //String saveFilePath = "D:\\wamp\\www\\Data\\pic\\";
        //saveFilePath = saveFilePath + UUid + ".png";
        saveFilePath = saveFilePath + userKey + ".png";
        // 构建叠加层
        BufferedImage buffImg = watermark(newFile1, f, 0, 0, 1.0f);
        // 输出水印图片
        generateWaterFile(buffImg, saveFilePath);

        // 终止后删除临时文件
        f.delete();
        newFile1.delete();
        newFile2.delete();
        f.deleteOnExit();
        // 公司服务器路径
        String result = "https://tdu.tduvr.club/Data/pic/" + userKey + ".png";
        //String result="http://192.168.0.55/Data/pic/"+userKey+".png";
        return result;
    }


    @RequestMapping("addUploading.action")
    @ResponseBody
    public String addUploading(@RequestParam("file") MultipartFile file, @RequestParam("customname") String customName, @RequestParam("introduce") String introduce, @RequestParam("customstyle") String customStyle, @RequestParam("subjectTreeId") String treeId,
                               @RequestParam("brosweFile") String fileName, @RequestParam("type") String contentType, @RequestParam("treeNodeId") String clickNodeId, HttpSession session) {
        String string;
        String userId = session.getAttribute("ID").toString();
        try {
            Knowlegcontent knowlegcontent = new Knowlegcontent();
            knowlegcontent.setCustomname(customName);
            knowlegcontent.setIntroduce(introduce);
            knowlegcontent.setCustomstyle(customStyle);
            knowlegcontent.setUserKey(userId);
            string = addKnowledgeService.addUploading(knowlegcontent, contentType, fileName, treeId, clickNodeId, file);
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            String result = "fail";
            return result;
        }
    }

    @RequestMapping("editorLoading.action")
    @ResponseBody
    public String editorLoading(@RequestParam("file") MultipartFile file, @RequestParam("customname") String customName, @RequestParam("introduce") String introduce, @RequestParam("customstyle") String customStyle, @RequestParam("subjectTreeId") String treeId,
                                @RequestParam("brosweFile") String fileName, @RequestParam("type") String contentType, @RequestParam("treeNodeId") String clickNodeId) {
        String string;
        try {
            Knowlegcontent knowlegcontent = new Knowlegcontent();
            knowlegcontent.setCustomname(customName);
            knowlegcontent.setIntroduce(introduce);
            knowlegcontent.setCustomstyle(customStyle);
            string = addKnowledgeService.editorLoading(knowlegcontent, contentType, fileName, treeId, clickNodeId, file);
            return string;
        } catch (Exception e) {
            e.printStackTrace();
            String result = "fail";
            return result;
        }
    }

    @RequestMapping("IsKnowledgeContent.action")
    @ResponseBody
    public String isKnowledgeContent(HttpServletRequest request) {
        String nodeId = request.getParameter("id");
        String result = addKnowledgeService.isKnowledgeContent(nodeId);
        return result;
    }

    @RequestMapping("AddSimulateModel.action")
    @ResponseBody
    public String addSimulateModel(HttpServletRequest request, HttpSession session) {
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String userId = session.getAttribute("ID").toString();

        Knowlegcontent knowledgecontent = new Knowlegcontent();
        knowledgecontent.setNmae(request.getParameter("simulate"));
        knowledgecontent.setCustomname(request.getParameter("name"));
        knowledgecontent.setCustomstyle("default");//
        knowledgecontent.setType(request.getParameter("simulType"));
        knowledgecontent.setUserKey(userId);
        String nodeId = request.getParameter("KnowledgeId");
        String treeId = request.getParameter("treeId");
        String knowledgesId = addKnowledgeService.addSimulateModel(knowledgecontent, treeId, nodeId);
        return knowledgesId;
    }

    @RequestMapping("AddQuesModel.action")
    @ResponseBody
    public String addQuesModel(HttpServletRequest request, HttpSession session) {
        String userId = session.getAttribute("ID").toString();
        Knowlegcontent knowlegcontent = new Knowlegcontent();
        knowlegcontent.setNmae(request.getParameter("errorques"));
        knowlegcontent.setCustomname(request.getParameter("name"));
        knowlegcontent.setCustomstyle(request.getParameter("cusstyle"));
        knowlegcontent.setUserKey(userId);
        String nodeId = request.getParameter("KnowledgeId");
        String treeId = request.getParameter("treeId");
        String knowledgesId = addKnowledgeService.addQuesModel(knowlegcontent, treeId, nodeId);
        return knowledgesId;
    }

    /**
     * 添加考试页面
     *
     * @param request
     * @return
     */
    @RequestMapping("addExamModel.action")
    @ResponseBody
    public String addExamModel(HttpServletRequest request, HttpSession session) {
        String userId = session.getAttribute("ID").toString();
        Knowlegcontent knowlegcontent = new Knowlegcontent();
        knowlegcontent.setNmae(request.getParameter("examId"));
        knowlegcontent.setCustomname(request.getParameter("name"));
        knowlegcontent.setCustomstyle(request.getParameter("cusstyle"));
        String nodeId = request.getParameter("KnowledgeId");
        String treeId = request.getParameter("treeId");
        knowlegcontent.setUserKey(userId);
        String knowledgesId = addKnowledgeService.addExamModel(knowlegcontent, treeId, nodeId);
        return knowledgesId;
    }

    @RequestMapping("AddCustomModel.action")
    @ResponseBody
    public String addCustomModel(HttpServletRequest request, HttpSession session) {
        String userId = session.getAttribute("ID").toString();
        Knowlegcontent knowlegcontent = new Knowlegcontent();
        knowlegcontent.setNmae(request.getParameter("errorques"));
        knowlegcontent.setCustomname(request.getParameter("name"));
        knowlegcontent.setCustomstyle(request.getParameter("cusstyle"));
        knowlegcontent.setUserKey(userId);
        String nodeId = request.getParameter("KnowledgeId");
        String treeId = request.getParameter("treeId");
        String knowledgesId = addKnowledgeService.addCustomModel(knowlegcontent, treeId, nodeId);
        return knowledgesId;
    }

    @RequestMapping("AddHtmlEditorContent.action")
    @ResponseBody
    public String addHtmlEditorContent(HttpServletRequest request) {
        Knowlegcontent knowlegcontent = new Knowlegcontent();
        knowlegcontent.setIntroduce(request.getParameter("introduce"));
        knowlegcontent.setCustomname(request.getParameter("name"));
        knowlegcontent.setCustomstyle(request.getParameter("cusstyle"));
        String nodeId = request.getParameter("KnowledgeId");
        String treeId = request.getParameter("treeId");
        String htmlcontent = request.getParameter("htmlcontent");
        String knowledgesId = addKnowledgeService.addHtmlEditorContent(knowlegcontent, htmlcontent, treeId, nodeId);
        return knowledgesId;
    }

    @RequestMapping("getknowidbycontentid.action")
    @ResponseBody
    public String getknowidbycontentid(HttpServletRequest request) {
        String contentId = request.getParameter("id");
        String knowId = addKnowledgeService.getKnowIdbycontentId(contentId);
        return knowId;
    }

    /**
     * 查询考试名称
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getExamName.action", method = {RequestMethod.POST})
    @ResponseBody
    public Knowlegcontent getExamName(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String examId = request.getParameter("examId");
        return addKnowledgeService.getExamName(examId);
    }


}
