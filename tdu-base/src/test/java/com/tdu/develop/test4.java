package com.tdu.develop;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.dom4j.DocumentException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class test4 {
    public static void main(String[] args) throws DocumentException, IOException {
        System.out.println(System.getProperty("file.encoding"));
       // test();
        System.out.println("Default Charset=" + Charset.defaultCharset());
        System.out.println("file.encoding=" + System.getProperty("file.encoding"));
        System.out.println("Default Charset=" + Charset.defaultCharset());
        System.out.println("Default Charset in Use=" + getDefaultCharSet());

        //upload();
       test();
    }

    public static void upload() throws  FileNotFoundException {
        File file = new File("D:\\wamp\\www\\Data\\3D\\Model\\149b527b-248a-4aa1-825a-115240d96f82\\4a26d358-5544-4e8e-a7bd-556a9de7a8e0\\images\\水面.png");
        //InputStreamReader is = new InputStreamReader(System.in,"UTF-8"); // 读取
        URL url = test4.class.getResource("D:\\wamp\\www\\Data\\3D\\Model\\149b527b-248a-4aa1-825a-115240d96f82\\4a26d358-5544-4e8e-a7bd-556a9de7a8e0\\images\\水面.png");
        InputStream is = new FileInputStream(file);
        System.out.println("  System.in ："+System.in);
        File creatFile = new File("D:\\working\\load\\水面.png");
        try {
            FileWriter fw = new FileWriter("D:\\wamp\\www\\Data\\3D\\Model\\149b527b-248a-4aa1-825a-115240d96f82\\4a26d358-5544-4e8e-a7bd-556a9de7a8e0\\images\\水面.png",true);// filePar + "\\" + filename,true
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(creatFile));
            byte[] flash = new byte[1024];
            int len = 0;
            while(-1 != (len = bis.read(flash))){
                bos.write(flash,0,len);
            }
            bos.flush();
            bis.close();
            bos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void downLoad(HttpServletResponse response){
        try (
                FileInputStream inputStream = new FileInputStream(new File("D:\\working\\load\\test.png"));
                ServletOutputStream outputStream = response.getOutputStream();
        ) {
            // 声明响应类型
            response.setContentType("application/x-download");
            // 下载的文件名称
            response.addHeader("Content-Disposition", "attachment;filename=水面.png");
            IOUtils.copy(inputStream, outputStream);
            //ServletUtils.toJson(request,response);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getDefaultCharSet() {
        OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream());
        String enc = writer.getEncoding();
        return enc;
    }
    private static void test() {
        String dir = "D:\\working\\load\\";
        String fileName = "水面.png";
//		String fileName = "D:/fbb/myWorkSpace_DW07/common_glass.log";
        try {
            String encoding = codeString(dir,fileName);
            System.out.println("encoding:"+encoding);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 判断文件的编码格式
     * @param fileName :file
     * @return 文件编码格式
     * @throws Exception
     */
    public static String codeString(String dir,String fileName) throws Exception{

        File file = new File(dir+fileName);
        if(file==null || !file.exists()){
            System.out.println("文件不存在..."+file.getAbsolutePath());
            return null;
        }

        BufferedInputStream bin = new BufferedInputStream( new FileInputStream(file));
        int p = (bin.read() << 8) + bin.read();
        String code = null;
        //其中的 0xefbb、0xfffe、0xfeff、0x5c75这些都是这个文件的前面两个字节的16进制数
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            case 0x5c75:
                code = "ANSI|ASCII" ;
                break ;
            default:
                code = "GBK";
        }

        return code;
    }
}
