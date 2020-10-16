package com.tdu.develop;

import java.io.*;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class test2 {

    public static void main(String[] args) throws DocumentException, IOException {
        SAXReader sax = new SAXReader();//创建一个SAXReader对象
        File xmlFile = new File("D:\\working\\TDuClub\\TDu\\Data\\3D\\Scene\\149b527b-248a-4aa1-825a-115240d96f82\\cab33af0-c920-4bc6-bfd5-f4818737b5f6\\test.xml");//根据指定的路径创建file对象
        Document document = sax.read(xmlFile);//获取document对象,如果文档无节点，则会抛出Exception提前结束
        Element root = document.getRootElement();//获取根节点
        //this.getNodes(root);//从根节点开始遍历所有节点
        editAttribute(root, "user");//对指定名字的节点进行属性的添加删除修改
        saveDocument(document, xmlFile);//把改变的内存中的document真正保存到指定的文件中
    }

    /**
     * 对指定的节点属性进行删除、添加、修改
     *
     * @author chenleixing
     */
    public static void editAttribute(Element root, String nodeName) {

        //获取指定名字的节点，无此节点的会报NullPointerException,时间问题不做此情况的判断与处理了
        List<Element> nodes = root.elements("Node");
        for (Element node : nodes) {
            Element childNode = node.element("File");
            Attribute attrUser = childNode.attribute("UserID");//获取此节点的指定属性
            attrUser.setValue("更改");//更改此属性值

            Attribute attrModel = childNode.attribute("ModelID");//获取此节点的指定属性
            attrModel.setValue("更改");//更改此属性值
            childNode.setText("11111");
        }
    }

    /**
     * 对指定的节点添加子节点和对象的文本内容
     *
     * @author chenleixing
     */
    public static void addNode(Element node, String nodeName, String content) {
        Element newNode = node.addElement(nodeName);//对指定的节点node新增子节点,名为nodeName
        newNode.setText(content);//对新增的节点添加文本内容content
    }


    /**
     * 把改变的domcument对象保存到指定的xml文件中
     *
     * @throws IOException
     * @author chenleixing
     */
    public static void saveDocument(Document document, File xmlFile) throws IOException {
        Writer osWrite = new OutputStreamWriter(new FileOutputStream(xmlFile));//创建输出流
        OutputFormat format = OutputFormat.createPrettyPrint();  //获取输出的指定格式
        format.setEncoding("UTF-8");//设置编码 ，确保解析的xml为UTF-8格式
        XMLWriter writer = new XMLWriter(osWrite, format);//XMLWriter 指定输出文件以及格式
        writer.write(document);//把document写入xmlFile指定的文件(可以为被解析的文件或者新创建的文件)
        writer.flush();
        writer.close();

    }

}