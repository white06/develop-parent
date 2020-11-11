package com.tdu.develop.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


public class PropertiesUtil {
    /**
     * 根据参数名在指定的properties配置
     * 文件中找到相应的信息,
     * 注意该配置文件位置必须是src文件夹的子文件
     *
     * @param propertiesName 配置文件名不要加配置文件名后缀
     * @param infoName       参数名
     * @throws IOException
     * @return 对应参数名的信息
     */
    public static String getPropertiesInfo(String propertiesName, String infoName) throws IOException {
        String prefix = "../../../";
        String suffix = ".properties";
        String url = prefix + propertiesName + suffix;
        InputStream inStream = PropertiesUtil.class.getResourceAsStream(url);
        Properties prop = new Properties();
        prop.load(inStream);
        String info = prop.getProperty(infoName);
        return info;
    }

    /**
     * 将properties文件中的字符串转换为list集合，
     * 该字符串会按照 , 分隔
     *
     * @param proString 字符串
     * @return list
     */
    public static List<String> getList(String proString) {
        String[] str = proString.split(",");
        List<String> list = Arrays.asList(str);
        return list;
    }
}
