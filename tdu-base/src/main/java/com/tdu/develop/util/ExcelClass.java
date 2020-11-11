package com.tdu.develop.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The function of the class is to operate the Excel
 * date:2018-1-19
 *
 * @author tgy
 */
public class ExcelClass {
    private static final String Null = null;

    public List<Map<String, Object>> getExcelContent(String excelURL) {
        List<Map<String, Object>> excelList;
        String fileEnd = excelURL.substring(excelURL.lastIndexOf(".") + 1).toLowerCase();
        if (fileEnd.equals("xls")) {
            excelList = getExcel2003(excelURL);
        } else {
            excelList = getExcel2007(excelURL);
        }
        return excelList;
    }

    /**
     * This method is used for the getExcel2003(.xls)
     *
     * @param excelURL
     * @return
     */
    private List<Map<String, Object>> getExcel2003(String excelURL) {
        try {
            //文件路径处理
            File file = new File(excelURL);
            InputStream InputStream = new FileInputStream(file);
            //建一个List用于存放所有的表格数据
            List<Map<String, Object>> excelList = new ArrayList<Map<String, Object>>();
            //打开Excel
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(InputStream);
            int workbookNum = hssfWorkbook.getNumberOfSheets();
            //选择工作簿
            for (int sheetNum = 0; sheetNum < workbookNum; sheetNum++) {
                if (hssfWorkbook.getSheetAt(sheetNum) == null) {
                    continue;
                } else {
                    //建一个工作簿的Map对象
                    Map<String, Object> sheetMap = new HashMap<String, Object>();
                    List<Map<String, Object>> cellList = new ArrayList<Map<String, Object>>();
                    //获取工作簿名
                    String sheetName = hssfWorkbook.getSheetName(sheetNum);
                    //获取当前工作簿的内容
                    HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(sheetNum);
                    int hssfSheetNum = hssfSheet.getPhysicalNumberOfRows();
                    //获得头部行，也就是属性行,并且得到属性的数目
                    HSSFRow headRow = hssfSheet.getRow(0);
                    if (hssfSheet.getRow(0) == null) {
                        //hssfWorkbook.getSheetAt(sheetNum)无法判断工作簿是否为空，所以需要使用头文件判断
                        continue;
                    } else {
                        int heardRowNum = headRow.getPhysicalNumberOfCells();
                        //得到工作簿的行数，并且循环，以获取每一行的数据
                        for (int rowNum = 1; rowNum <= hssfSheetNum; rowNum++) {
                            //判断空行
                            if (hssfSheet.getRow(rowNum) == null) {
                                continue;
                            } else {
                                //得到当前行的所有数据
                                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                                //建一个每行的Map对象
                                Map<String, Object> cellMap = new HashMap<String, Object>();
                                //按属性行的列数循环，得出当前行的所有数据，并且保存
                                for (int cellNum = 0; cellNum < heardRowNum; cellNum++) {
                                    //得到当前列的数据
                                    HSSFCell headCell = headRow.getCell(cellNum);
                                    HSSFCell hssfCell = hssfRow.getCell(cellNum);
                                    //判断空列,空列无法使用cell的方法，必须直接用row.getCell判断
                                    if (hssfRow.getCell(cellNum) == null) {
                                        cellMap.put(headCell.getStringCellValue(), Null);
                                    } else {
                                        //判断数据的类型
                                        switch (hssfCell.getCellType()) {
                                            case 0://数字，int类型
                                                cellMap.put(headCell.getStringCellValue(), hssfCell.getNumericCellValue());
                                                break;
                                            case 1://String类型
                                                cellMap.put(headCell.getStringCellValue(), hssfCell.getStringCellValue());
                                                break;
                                            case 2://公式，存成长字符串
                                                cellMap.put(headCell.getStringCellValue(), hssfCell.getRichStringCellValue());
                                                break;
                                            case 3://空
                                                cellMap.put(headCell.getStringCellValue(), Null);
                                                break;
                                            case 4://布尔类型
                                                cellMap.put(headCell.getStringCellValue(), hssfCell.getBooleanCellValue());
                                                break;
                                            case 5://错误
                                                continue;
                                        }
                                    }
                                    continue;
                                }
                                cellList.add(cellMap);
                                continue;
                            }
                        }
                        sheetMap.put("sheetName", sheetName);
                        sheetMap.put("sheetContent", cellList);
                        excelList.add(sheetMap);
                    }
                }
            }
            if (InputStream != null) {
                InputStream.close();
            }
            return excelList;
        } catch (FileNotFoundException e) {
            System.out.println("Can't to find the file");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Can't to read the file.Check version of the file");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used for the getExcel2007(.xlsx)
     *
     * @param excelURL
     * @return
     */
    private List<Map<String, Object>> getExcel2007(String excelURL) {
        try {
            //获取文件
            File file = new File(excelURL);
            InputStream inputStream = new FileInputStream(file);
            //获取Excel内容
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            int workbookNum = xssfWorkbook.getNumberOfSheets();
            List<Map<String, Object>> excelList = new ArrayList<Map<String, Object>>();
            //选择工作簿
            for (int sheetNum = 0; sheetNum < workbookNum; sheetNum++) {
                if (xssfWorkbook.getSheetAt(sheetNum) == null) {
                    continue;
                } else {
                    //获取工作簿
                    XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(sheetNum);
                    int xssfSheetNum = xssfSheet.getLastRowNum();
                    //获取当前工作簿名
                    String sheetName = xssfSheet.getSheetName();
                    //获取属性行
                    XSSFRow headRow = xssfSheet.getRow(0);
                    if (xssfSheet.getRow(0) == null) {
                        continue;
                    } else {
                        int headRowNum = headRow.getPhysicalNumberOfCells();
                        //建立容器
                        Map<String, Object> sheetMap = new HashMap<String, Object>();
                        List<Map<String, Object>> cellList = new ArrayList<Map<String, Object>>();
                        //选择行
                        for (int rowNum = 1; rowNum <= xssfSheetNum; rowNum++) {
                            //空行跳过
                            if (xssfSheet.getRow(rowNum) == null) {
                                continue;
                            } else {
                                Map<String, Object> cellMap = new HashMap<String, Object>();
                                //获取当前行
                                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                                //选择列
                                for (int cellNum = 0; cellNum < headRowNum; cellNum++) {
                                    //获取列数据
                                    XSSFCell xssfCell = xssfRow.getCell(cellNum);
                                    XSSFCell headCell = headRow.getCell(cellNum);
                                    //空列判断，空列无法使用cell方法，只能使用row.getCell
                                    if (xssfRow.getCell(cellNum) == null) {
                                        cellMap.put(headCell.getStringCellValue(), Null);
                                    } else {
                                        switch (xssfCell.getCellType()) {
                                            case 0://数字，int类型，后面接收到是double
                                                cellMap.put(headCell.getStringCellValue(), xssfCell.getNumericCellValue());
                                                break;
                                            case 1://String类型
                                                cellMap.put(headCell.getStringCellValue(), xssfCell.getStringCellValue());
                                                break;
                                            case 2://公式，存成长字符串
                                                cellMap.put(headCell.getStringCellValue(), xssfCell.getRichStringCellValue());
                                                break;
                                            case 3://空
                                                cellMap.put(headCell.getStringCellValue(), Null);
                                                break;
                                            case 4://布尔类型
                                                cellMap.put(headCell.getStringCellValue(), xssfCell.getBooleanCellValue());
                                                break;
                                            case 5://错误
                                                continue;
                                        }
                                    }
                                }
                                cellList.add(cellMap);
                            }
                        }
                        sheetMap.put("sheetContent", cellList);
                        sheetMap.put("sheetName", sheetName);
                        excelList.add(sheetMap);
                    }
                }
            }
            if (inputStream != null) {
                inputStream.close();
            }
            return excelList;
        } catch (FileNotFoundException e) {
            System.out.println("Can't to find the file");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Can't to read the file.Check version of the file");
            e.printStackTrace();
        }
        return null;
    }
}