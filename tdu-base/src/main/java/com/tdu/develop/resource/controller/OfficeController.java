package com.tdu.develop.resource.controller;



import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tdu.develop.common.exception.JsonResult;
import com.tdu.develop.resource.service.OfficeService;
import com.tdu.develop.resource.service.impl.OfficeServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("office")
public class OfficeController {
	@Resource
	OfficeService os;
	
	@Resource
	OfficeServiceImpl officeServiceImpl;
	/**
	 * 进度条刷新，数据从session当中取
	 *
	 * @param request
	 * @return String
	 */
	/*@ResponseBody
	@RequestMapping(params = "method=flushProgress")
	public String flushProgress(HttpServletRequest request) {
		HashMap<String, Object> map = null;
		try {
			map = new HashMap<String, Object>();
			Object percent = request.getSession().getAttribute("percent");
	        //加上空判断是为了防止第一次读取session时，出现NULL
			if (null == percent || "".equals(percent)) {
				map.put("percent", 0);
			} else {
				map.put("percent", percent);
				request.getSession().removeAttribute("percent");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JSON.toJSONString(map);
	}*/
	
	/**
	 * 导出excel文件
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("exportExcelAll.action")
	@ResponseBody
	public JsonResult exportExcelAll(HttpServletRequest request, HttpServletResponse response) throws IOException{
		/*String fileName = "导出文件";
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ new String((fileName).getBytes("GB2312"),"iso8859-1")+".xls"+"\"");
		//response.addHeader("Content-Length", "C://导出文件.xls");
		String classes = request.getParameter("classes");
		String students = request.getParameter("students");
		String teachers = request.getParameter("teacheres");
		String admins = request.getParameter("admines");
		System.out.println(classes+":"+students+":"+teachers+":"+admins);
		OutputStream outputStream = response.getOutputStream();
		List<HSSFWorkbook> list=os.exportInfos(classes,students,teachers,admins);
		for(HSSFWorkbook hwb:list) {
			hwb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		}
		return new JsonResult();*/
		String fileName = "护理仿真总成绩";
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ new String((fileName).getBytes("GB2312"),"iso8859-1")+".xls"+"\"");
		//response.addHeader("Content-Length", "C://导出文件.xls");
		String classes = request.getParameter("classes");
		String students = request.getParameter("students");
		String index = request.getParameter("index");
		OutputStream outputStream = response.getOutputStream();
		//ServletOutputStream outputStream = response.getOutputStream();
		List<HSSFWorkbook> list=officeServiceImpl.exportAll(classes,students);
		for(HSSFWorkbook hwb:list) {
			hwb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		}
		return new JsonResult();
	}
	/**
	 * 导出excel文件
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("exportExcelByClass.action")
	@ResponseBody
	public JsonResult exportExcelByClass(HttpServletRequest request,HttpServletResponse response) throws IOException{
		/*String fileName = "导出文件";
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ new String((fileName).getBytes("GB2312"),"iso8859-1")+".xls"+"\"");
		//response.addHeader("Content-Length", "C://导出文件.xls");
		String classes = request.getParameter("classes");
		String students = request.getParameter("students");
		String teachers = request.getParameter("teacheres");
		String admins = request.getParameter("admines");
		System.out.println(classes+":"+students+":"+teachers+":"+admins);
		OutputStream outputStream = response.getOutputStream();
		List<HSSFWorkbook> list=os.exportInfos(classes,students,teachers,admins);
		for(HSSFWorkbook hwb:list) {
			hwb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		}
		return new JsonResult();*/
		String fileName = "选修总成绩20172018";
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ new String((fileName).getBytes("GB2312"),"iso8859-1")+".xls"+"\"");
		//response.addHeader("Content-Length", "C://导出文件.xls");
		String classes = request.getParameter("classes");
		String students = request.getParameter("students");
		String index = request.getParameter("index");
		System.out.println(classes+":"+students);
		OutputStream outputStream = response.getOutputStream();
		List<HSSFWorkbook> list=officeServiceImpl.exportAll(classes,students);
		for(HSSFWorkbook hwb:list) {
			hwb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		}
		//response.getWriter().print("<script>layer.close("+index+");</script>");
		return new JsonResult();
	}
	
	
	/**
	 * 导出excel文件
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("exportExcelInfo.action")
	@ResponseBody
	public JsonResult exportExcelInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		/*String fileName = "导出文件";
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ new String((fileName).getBytes("GB2312"),"iso8859-1")+".xls"+"\"");
		//response.addHeader("Content-Length", "C://导出文件.xls");
		String classes = request.getParameter("classes");
		String students = request.getParameter("students");
		String teachers = request.getParameter("teacheres");
		String admins = request.getParameter("admines");
		System.out.println(classes+":"+students+":"+teachers+":"+admins);
		OutputStream outputStream = response.getOutputStream();
		List<HSSFWorkbook> list=os.exportInfos(classes,students,teachers,admins);
		for(HSSFWorkbook hwb:list) {
			hwb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		}
		return new JsonResult();*/
		String fileName = "导出文件";
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment; filename=\""
				+ new String((fileName).getBytes("GB2312"),"iso8859-1")+".xls"+"\"");
		//response.addHeader("Content-Length", "C://导出文件.xls");
		String classes = request.getParameter("classes");
		String students = request.getParameter("students");
		String teachers = request.getParameter("teacheres");
		String admins = request.getParameter("admines");
		System.out.println(classes+":"+students+":"+teachers+":"+admins);
		OutputStream outputStream = response.getOutputStream();
		List<HSSFWorkbook> list=os.exportInfos(classes,students,teachers,admins);
		for(HSSFWorkbook hwb:list) {
			hwb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		}
		return new JsonResult();
	}
	/**
	 * 导入excel文件
	 * @param file
	 * @param request
	 * @return
	 */
	@RequestMapping("importExcelInfos.action")
	@ResponseBody
	public JsonResult importExcelInfos(@RequestParam("excelFile") MultipartFile file,HttpServletRequest request){
		String filePath = request.getSession().getServletContext().getRealPath("/")+file.getOriginalFilename();
		os.importExcelInfos(file);
		return new JsonResult();
	}
}
