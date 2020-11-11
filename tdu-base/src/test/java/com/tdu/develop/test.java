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
        String st = get();
        System.out.println(st);
    }

    public  static String get() {
        String realPath = "C:/Users/TDU/Desktop/XMhunnitu/cccccc";
        //读取文件夹路径
        File file = new File(realPath);
        //判断是否存在
        if (file.exists() && file.isDirectory()) {
            try {
                delTempChild(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("文件夹不存在！");
        }
        return realPath;
    }
    public static void delTempChild(File file){
        if (file.isDirectory()) {
            String[] children = file.list();//获取文件夹下所有子文件夹
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                delTempChild(new File(file, children[i]));
            }
        }
        // 目录空了，进行删除
        file.delete();
    }
}
