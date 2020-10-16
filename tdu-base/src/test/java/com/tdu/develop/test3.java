package com.tdu.develop;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;


public class test3 {

    public static void main(String[] args) {
        //创建DocumentBuilderFactory对象
        DocumentBuilderFactory a = DocumentBuilderFactory.newInstance();
        try {
            //创建DocumentBuilder对象
            DocumentBuilder b = a.newDocumentBuilder();
            String path = "D:\\working\\TDuClub\\TDu\\Data\\3D\\Scene\\149b527b-248a-4aa1-825a-115240d96f82\\7a68d420-cf81-45d7-90ec-c1b75eeaa710\\7a68d420-cf81-45d7-90ec-c1b75eeaa710.EXM";
            //通过DocumentBuilder对象的parse方法返回一个Document对象
            Document document = b.parse(path);
            //通过Document对象的getElementsByTagName()返根节点的一个list集合
            NodeList booklist = document.getElementsByTagName("Node");
            for (int i = 0; i < booklist.getLength(); i++) {
                //循环遍历获取每一个book
                Node book = booklist.item(i);
                //通过Node对象的getAttributes()方法获取全的属性值
                NamedNodeMap bookmap = book.getAttributes();
                //循环遍每一个book的属性值
                for (int j = 0; j < bookmap.getLength(); j++) {
                    Node node = bookmap.item(j);
                    //通过Node对象的getNodeName()和getNodeValue()方法获取属性名和属性值
                    System.out.print(node.getNodeName() + ":");
                    System.out.println(node.getNodeValue());
                }
                //解析book节点的子节点
                NodeList childlist = book.getChildNodes();
                for (int t = 0; t < childlist.getLength(); t++) {
                    //区分出text类型的node以及element类型的node
                    if (childlist.item(t).getNodeType() == Node.ELEMENT_NODE) {
                        if (childlist.item(t).getNodeName().equals("File")) {
                            System.out.print(childlist.item(t).getNodeName() + ":");
                            childlist.item(t).setTextContent("11111");
                            System.out.println(childlist.item(t).getTextContent());
                            NamedNodeMap childBookmap = childlist.item(t).getAttributes();
                            if (childBookmap.getLength() <= 2) {
                                Node node = childlist.item(t);
                                Element eNode = (Element) node;
                                eNode.setAttribute("ModelID", "222");
                                eNode.setAttribute("UserID", "333");
                            } else {
                                for (int j = 0; j < childBookmap.getLength(); j++) {
                                    Node node = childBookmap.item(j);
                                    //通过Node对象的getNodeName()和getNodeValue()方法获取属性名和属性值
                                    if (node.getNodeName().equals("ModelID")) {
                                        node.setNodeValue("222");
                                    }
                                    if (node.getNodeName().equals("UserID")) {
                                        node.setNodeValue("333");
                                    }
                                    System.out.print(node.getNodeName() + ":");
                                    System.out.println(node.getNodeValue());
                                }

                            }
                        }
                    }
                }

            }
            System.out.println(document);
            save(document, path);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据节点参数查询节点
     *
     * @param express 节点路径
     * @param source  搜索节点源
     * @return 查询到的第一个节点
     */
    public static Node selectSingleNode(String express, Element source) {
        Node result = null;
        //创建XPath工厂
        XPathFactory xPathFactory = XPathFactory.newInstance();
        //创建XPath对象
        XPath xpath = xPathFactory.newXPath();
        try {
            result = (Node) xpath.evaluate(express, source, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
            return null;
        }
        return result;
    }


    public static void save(Document document, String path) throws TransformerException {
        Source xmlSource = new DOMSource(document);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        Result result = new StreamResult(new File(path));
        transformer.transform(xmlSource, result);
    }
}