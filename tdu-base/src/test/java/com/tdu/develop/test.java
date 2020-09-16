package com.tdu.develop;

import org.dom4j.DocumentException;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.SortedMap;

public class test {
    public static void main(String[] args) throws DocumentException, IOException {
        System.out.println((4-3)*100/3);
        //System.out.println(System.getProperty("file.encoding"));
       // test();
        //System.out.println("Default Charset=" + Charset.defaultCharset());
        //System.out.println("file.encoding=" + System.getProperty("file.encoding"));
        //System.out.println("Default Charset=" + Charset.defaultCharset());
       // System.out.println("Default Charset in Use=" + getDefaultCharSet());
        //test();
    }
    private static String getDefaultCharSet() {
        OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream());
        String enc = writer.getEncoding();
        return enc;
    }
    private static void test() {
        String dir = "D:\\wamp\\www\\Data\\3D\\Model\\149b527b-248a-4aa1-825a-115240d96f82\\4a26d358-5544-4e8e-a7bd-556a9de7a8e0\\images\\";
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
