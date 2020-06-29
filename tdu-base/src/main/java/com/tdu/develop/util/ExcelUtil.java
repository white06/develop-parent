package com.tdu.develop.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.tdu.develop.common.exception.ServiceException;
import com.tdu.develop.user.pojo.Member;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelUtil {
	/**
	 * 将excel文件中的数据解析成Map<sheetName,List<Member>>的形式,
	 * 此方法用于将批量导入用户信息
	 * @param file 上传文件
	 * @return map
	 * @throws ParseException 
	 */
	public Map<String,List<Member>> importExcel(MultipartFile file) throws ParseException{
		Map<String,List<Member>> map = new HashMap<String,List<Member>>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			List<Member> list = new ArrayList<Member>();
			//创建文件读取字节流
			InputStream is = file.getInputStream();
			//获取文件名加后缀
			String fileName = file.getOriginalFilename();
			//获取文件后缀
			String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
			if(!"xls".equals(suffix) && !"xlsx".equals(suffix))
				throw new ServiceException("文件格式不对");
			Workbook workbook = new HSSFWorkbook(is);
			//获取总表数
			int sheets = workbook.getNumberOfSheets();
			//遍历表
			for(int i=0;i<sheets;i++){
				Sheet sheet = workbook.getSheetAt(i);
				String sheetName = sheet.getSheetName();
				//获取总行数
				int rows = getPracticalRows(sheet);
				if(rows <= 0)
					continue;
				//遍历行
				for(int j=1;j<rows;j++){
					//获取指定行
					Row row = sheet.getRow(j);
					Member member = new Member();
					member.setId(UUID.randomUUID().toString());
					member.setName(typeConversion(row.getCell(0)));
					member.setSex(typeConversion(row.getCell(1)));
					member.setBirthDate(new java.sql.Date(sdf.parse(typeConversion(row.getCell(2))).getTime()));
					member.setMobilePhoneNum(typeConversion(row.getCell(3)));
					member.setQqNum(typeConversion(row.getCell(4)));
					member.setEmail(typeConversion(row.getCell(5)));
					member.setFamilyAddress(typeConversion(row.getCell(6)));
					member.setFamilyPhoneNum(typeConversion(row.getCell(7)));
					member.setUserName(typeConversion(row.getCell(8)));
					member.setPassword(typeConversion(row.getCell(9)));
					member.setCreateDate(new java.sql.Date(new Date().getTime()));
					list.add(member);
				}
				map.put(sheetName, list);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	/**
	 * 根据cell的类型获取相应的值
	 * @param cell
	 * @return
	 */
	public String typeConversion(Cell cell) {
		if(cellJudge(cell)==0) {
			return "";
		}
		if(cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		}else if(cell.getCellType()==cell.CELL_TYPE_NUMERIC) {
			String cellvalue="";
			if(HSSFDateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				Date dt = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
				cellvalue = dateFormat.format(dt);
			}else {
				DecimalFormat df = new DecimalFormat("0");
				cellvalue = df.format(cell.getNumericCellValue());
			}
			return cellvalue;
		}else {
			return String.valueOf(cell.getStringCellValue());
		}
	}
	/**
	 * 获取实际的行数
	 * @param sheet
	 * @return
	 */
	public int getPracticalRows(Sheet sheet) {
		int begin = sheet.getFirstRowNum();
		int end = sheet.getLastRowNum();
		int totalRows = 0;
		for(int i=begin;i<=end;i++) {
			if(null==sheet.getRow(i)) {
				continue;
			}
			totalRows++;
		}
		return totalRows;
	}
	/**
	 * 判断单元格是否为空
	 * @param cell
	 * @return
	 */
	public int cellJudge(Cell cell) {
		if(null == cell||cell.getCellType()==HSSFCell.CELL_TYPE_BLANK||cell.equals("")) {
			return 0;
		}
		return 1;
	}
	
}
