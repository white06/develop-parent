package com.tdu.develop.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class print {


    /**
     * 新增文件
     *
     * @param name
     * @param path
     * @throws IOException
     */
    public static void creatTxtFile(String name, String path) throws IOException {


        //文件对象
        File filename = new File(name);

        //判断是否存在文件夹
        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }
        //判断是否存在文件
        if (!filename.exists()) {


            filename.createNewFile();


        }

    }

    public static void writeTxtFile(String newStr, String filePath) throws IOException {


        //新建文本
        String filein = newStr;

        //打开流
        FileInputStream fis = null;

        InputStreamReader isr = null;

        BufferedReader br = null;

        FileOutputStream fos = null;

        PrintWriter pw = null;

        try {

            //创建文本对象
            File file = new File(filePath);


            fis = new FileInputStream(file);

            isr = new InputStreamReader(fis);

            br = new BufferedReader(isr);

            //写入流
            StringBuffer buf = new StringBuffer();

            //写入文本
            buf.append(filein);

            fos = new FileOutputStream(file);

            pw = new PrintWriter(fos);

            pw.write(buf.toString().toCharArray());

            pw.flush();


        } catch (IOException e1) {


            throw e1;

        } finally {

            if (pw != null) {

                pw.close();

            }

            if (fos != null) {

                fos.close();

            }

            if (br != null) {

                br.close();

            }

            if (isr != null) {

                isr.close();

            }

            if (fis != null) {

                fis.close();

            }

        }

    }


}


