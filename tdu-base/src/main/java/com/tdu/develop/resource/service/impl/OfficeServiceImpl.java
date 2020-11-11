package com.tdu.develop.resource.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import com.tdu.develop.resource.mapper.StuQueInforsMapper;
import com.tdu.develop.resource.mapper.SubjectTreeMapper;
import com.tdu.develop.resource.pojo.Exams;
import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.StuQueInfors;
import com.tdu.develop.resource.pojo.StutotalScoresForYXY;
import com.tdu.develop.resource.service.OfficeService;
import com.tdu.develop.resource.service.OfficeService;
import com.tdu.develop.user.mapper.DepartmentMapper;
import com.tdu.develop.user.pojo.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tdu.develop.util.ExcelUtil;
import com.tdu.develop.user.mapper.OfficeMapper;
import com.tdu.develop.user.mapper.RolesMapper;
import com.tdu.develop.user.mapper.UsersMapper;

@Service
public class OfficeServiceImpl implements OfficeService {

    @Autowired
    private OfficeMapper officeMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private RolesMapper rolesMapper;
    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    StuQueInforsMapper stuQueInforsMapper;

    @Autowired
    SubjectTreeMapper subjectTreeMapper;

    /**
     * 根据用户ld集合获取集合内所有的用户信息
     */
    public List<Member> getUsersInfos2(List<String> usersId) {
        List<Member> lm = new ArrayList<Member>();
        for (String str : usersId) {
            Member member = officeMapper.getUsersInfos2(str);
            if (member == null) {
                continue;
            }
            lm.add(member);
        }

        for (int i = 0; i < lm.size(); i++) {
            for (int j = i; j < lm.size(); j++) {
                BigInteger a = new BigInteger(lm.get(i).getUserName());
                BigInteger b = new BigInteger(lm.get(j).getUserName());
                if (a.compareTo(b) == 1) {
                    Member member = lm.get(i);
                    lm.set(i, lm.get(j));
                    lm.set(j, member);
                }
            }
        }
        return lm;
    }

    /**
     * 获取所有的学生信息
     *
     * @param students
     * @return
     */
    public List<Map<String, List<Member>>> getMemberOfAll2(String students) {
        if ("false".equals(students)) {
            return null;
        }
        //获取所有的学生
        //获取所有的学生id
        List<String> stuIds = officeMapper.getAllNurseStudents();
        List<Member> lm = getUsersScores(stuIds);
        Map<String, List<Member>> map = new HashMap<String, List<Member>>();
        map.put("护理仿真总成绩", lm);
        List<Map<String, List<Member>>> list = new ArrayList<Map<String, List<Member>>>();
        list.add(map);
        return list;
    }

    /**
     * 根据用户ld集合获取集合内所有的用户信息
     */
    public List<Member> getUsersScores(List<String> usersId) {
        List<Member> lm = new ArrayList<Member>();
        for (String str : usersId) {
            Member member = officeMapper.getUsersInfos2(str);
            if (member == null) {
                continue;
            }
            lm.add(member);
        }
        for (int i = 0; i < lm.size(); i++) {
            for (int j = i; j < lm.size(); j++) {
                BigInteger a = new BigInteger(lm.get(i).getUserName());
                BigInteger b = new BigInteger(lm.get(j).getUserName());
                if (a.compareTo(b) == 1) {
                    Member member = lm.get(i);
                    lm.set(i, lm.get(j));
                    lm.set(j, member);
                }
            }
        }
        return lm;
    }

    public List<HSSFWorkbook> exportAll(String classes, String students) {
        List<HSSFWorkbook> listAll = new ArrayList<>();
        //对按班级导出的处理
        List<Map<String, List<Member>>> lces = getMemberByClasses2(classes);
        HSSFWorkbook hwb1 = exportExcel2(lces);
        //对直接导出所有学生进行处理
        List<Map<String, List<Member>>> lces1 = getMemberOfAll2(students);
        HSSFWorkbook hwb2 = exportExcel2(lces1);
		/*//对老师信息的导出处理
		List<Map<String,List<Member>>> loes2 = getTeachers(teachers);
		HSSFWorkbook hwb3 = exportExcel(loes2);
		//对管理员信息的导出处理
		List<Map<String,List<Member>>> loes3 = getadminsOfAll(admins);
		HSSFWorkbook hwb4 = exportExcel(loes3);*/
        if (null != hwb1)
            listAll.add(hwb1);
        if (null != hwb2)
            listAll.add(hwb2);
		/*if(null != hwb3)
			listAll.add(hwb3);
		if(null != hwb4)
			listAll.add(hwb4);*/
        return listAll;
    }

    /**
     * 根据班级获取所有学生信息
     *
     * @return 返回所有的班级和学生信息
     * @param    classes    值为“true”或“false” 表示是否按班级获取学生信息
     */
    public List<Map<String, List<Member>>> getMemberByClasses2(String classes) {
        if ("false".equals(classes)) {
            return null;
        }
        //获取所有的班级
        List<Classes> list = officeMapper.getNurseClasses();
        List<Map<String, List<Member>>> listAll = new ArrayList<Map<String, List<Member>>>();
        //遍历所有的班级
        for (Classes cl : list) {
            //根据班级id找到所在的所有学生信息
            List<Member> lm = getStudentsInfos2(cl.getId());
            if (lm == null) {
                continue;
            }
            Map<String, List<Member>> map = new HashMap<String, List<Member>>();
            map.put(cl.getClassName(), lm);
            listAll.add(map);
        }
        return listAll;
    }

    /**
     * 获取班级内所有学生的信息
     *
     * @param classId
     * @return 返回班级内所有的学生信息
     */
    public List<Member> getStudentsInfos2(String classId) {
        //班级全部学生id
        List<String> list = officeMapper.getUsersId2(classId);
        if (list.size() == 0) {
            return null;
        }
        List<Member> lm = getUsersInfos2(list);
        return lm;
    }

    /**
     * 将用户信息导出到excel上并保存到桌面
     *
     * @param list 导出信息的集合
     */
    public HSSFWorkbook exportExcel2(List<Map<String, List<Member>>> list) {
        if (list == null) {
            return null;
        }
        List<Subjects> strList = officeMapper.getNurseSubject();

        for (int i = 0; i < strList.size(); i++) {
            if (strList.get(i).getSubjectName().equals("开发者平台") || strList.get(i).getSubjectName().equals("理论知识")) {
                strList.remove(i);
            }
        }
        for (int i = 0; i < strList.size(); i++) {
            if (strList.get(i).getSubjectName().equals("开发者平台") || strList.get(i).getSubjectName().equals("理论知识")) {
                strList.remove(i);
            }
        }

        DecimalFormat df = new DecimalFormat("######0.00");

        //创建一个webbook,对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        for (Map<String, List<Member>> map : list) {
            for (String key : map.keySet()) {
                //在webbook中添加一个sheet，对应Excel文件中的sheet
                HSSFSheet sheet = wb.createSheet(key);
                //在sheet中添加表头第0行
                HSSFRow row = sheet.createRow((int) 0);
                //创建单元格，并设置值表头，设置表头居中
                HSSFCellStyle style = wb.createCellStyle();
                //设置对其方式，居中对齐
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                HSSFCell cell = row.createCell(0);
                cell.setCellValue("学号");
                cell = row.createCell(1);
                cell.setCellValue("姓名");

                for (int i = 0; i < strList.size(); i++) {
                    cell = row.createCell((i + 2));
                    cell.setCellValue(strList.get(i).getSubjectName());
                        /*if(i!=strList.size()-1){
                            cell = row.createCell((i+2));
                            cell.setCellValue(strList.get(i).getSubjectName());
                        }else{
                            cell = row.createCell((i+2));
                            cell.setCellValue("平均分");
                        }*/
                }
                cell = row.createCell(strList.size() + 2);
                cell.setCellValue("平均分");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                //获取班级内的学生信息
                List<Member> value = map.get(key);
                for (int j = 0; j < value.size(); j++) {

                    row = sheet.createRow(j + 1);
                    Member member = value.get(j);
                    row.createCell(1).setCellValue(member.getName());
                    row.createCell(0).setCellValue(member.getUserName());

                    double count = 0.00;
                    for (int i = 0; i < strList.size(); i++) {
                        //String obj =officeMapper.getSubTreeId(strList.get(i).getId());
                        //String obj2 =officeMapper.getKnowledgeContentId(obj);
                        StuQueInfors stuQueInfors = new StuQueInfors();
                        //stuQueInfors.setPagerKey(obj2);
                        /*stuQueInfors.setSubjectKey(strList.get(i).getId());*/
                        stuQueInfors.setPagerKey(strList.get(i).getId());
                        stuQueInfors.setStuKey(member.getId());
                        String chengji = officeMapper.getNurseScore(stuQueInfors);
                        if (chengji == null) {
                            chengji = "0";
                        }

                        double cji = Double.parseDouble(chengji);

                        count += cji;

                            /*if(i!=strList.size()-1){
                                count+=cji;
                            }*/
                        //row.createCell((i+2)).setCellValue(chengji);

                        row.createCell((i + 2)).setCellValue(chengji);

                            /*if(i!=strList.size()-1){
                                row.createCell((i+2)).setCellValue(chengji);
                            }else{
                                row.createCell((i+2)).setCellValue(df.format(count/(strList.size()-1)));
                            }*/
                    }
                    row.createCell((strList.size() + 2)).setCellValue(df.format(count / (strList.size())));
                }
            }
        }
		/*//将文件存到指定位置
				try{
					//FileSystemView fsv = FileSystemView.getFileSystemView();
					//File com = fsv.getHomeDirectory();
					//桌面路径
					//String url = com.getAbsolutePath();
					//excel存放路径
					String url = PropertiesUtil.getPropertiesInfo("function", "excelUrl");
					LOGGER.info(url);
					String[] split = url.split("\\.");
					System.out.println("split:"+split.toString());
					LOGGER.info(split[0]);
					LOGGER.info(split[1]);
					//创建文件对象
					File file = new File(url);
					int i = 0;
					//判断桌面是否有此名字的文件，并做相应处理
					while(file.exists()){
						i++;
						String fileName = split[0]+i;
						url = fileName+"."+split[1];
						System.out.println("path:"+url);
						file = new File(url);
						LOGGER.info(url);;
					}
					//创建此抽象路径的文件
					File finalFile = new File(url);
					finalFile.createNewFile();
					//创建文件输出流
					OutputStream os = new FileOutputStream(url);
					//写入文件
					wb.write(os);
					os.close();
				}catch(Exception e){
					e.printStackTrace();
				}*/
        System.err.println(wb);
        //Classes.cut();
        return wb;
    }


    public List<String> quchong(List<String> list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    public List<HSSFWorkbook> daoFangzhen(String depertId, List<Knowledges> knoList) {
        List<HSSFWorkbook> listAll = new ArrayList<>();
        //对按班级导出的处理
        List<Map<String, List<Member>>> lces = daoFangzhen(depertId);
        HSSFWorkbook hwb1 = exportExcelFangzhen(lces, knoList);
        if (null != hwb1) {
            listAll.add(hwb1);
        }
        return listAll;
    }


    @Override
    public List<HSSFWorkbook> daoFangzhenByKnowledges(String classId, List<Knowledges> knoList,String startDate, String enddatetime) throws ParseException {
        List<HSSFWorkbook> listAll = new ArrayList<>();
        //对按Knowledges导出的处理
        List<Map<String, List<Member>>> lces = null;
        if(startDate.equals("")&&enddatetime.equals("")){
            lces  = daoFangzhenKnowledges(classId,knoList);
        }
        if(!startDate.equals("")&&!enddatetime.equals("")){
            lces  = daoFangzhenKnowledgesByDate(classId,knoList,startDate,enddatetime);
        }
        HSSFWorkbook hwb1 = exportExcelFangzhenKnowledges(lces);
        if (null != hwb1) {
            listAll.add(hwb1);
        }
        return listAll;
    }
    public List<Map<String, List<Member>>> daoFangzhenKnowledgesByDate(String classId,List<Knowledges> knoList,String startDate, String enddatetime ) throws ParseException {
        if (knoList.size() <= 0) {
            return null;
        }

        //获取系统时间，并且转码成数据库可读
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = df.format(d);
        List<Map<String, List<Member>>> listAll = new ArrayList<Map<String, List<Member>>>();
        //遍历所有的班级
        for(int j = 0 ;j<knoList.size();j++){
            //根据班级id找到所在的所有学生信息
            List<Member> lm = getStudentsInfos(classId);
            List<Member> memberList = new ArrayList<Member>();
            if (lm == null) {
                continue;
            }
            for(int i = 0 ;i<lm.size();i++){
                lm.get(i).setSex("0");
                lm.get(i).setMsn(dateNowStr);
                System.out.println(knoList.get(j).getKnowledgecontentId());
                System.out.println(lm.get(i).getId());
                List<StutotalScoresForYXY> list = subjectTreeMapper.getScoresForYXYStuByDate(knoList.get(j).getKnowledgecontentId(),lm.get(i).getId(),startDate,enddatetime);
                if(list.size()>0){
                    for (StutotalScoresForYXY stu:list) {
                        Member member =  new Member();
                        member.setId(lm.get(i).getId());
                        member.setName(lm.get(i).getName());
                        member.setUserName(lm.get(i).getUserName());
                        member.setMsn(stu.getEndDate());
                        member.setSex(Integer.toString(stu.getQuesScore()));
                        memberList.add(member);
                    }
                }
            }
            Map<String, List<Member>> map = new HashMap<String, List<Member>>();
            if(memberList.size()>0){
                for (Member member:memberList) {
                    lm.add(member);
                }
                map.put(knoList.get(j).getContent(), lm);
            }else{
                map.put(knoList.get(j).getContent(), lm);
            }
            listAll.add(map);
        }
        return listAll;
    }
    public HSSFWorkbook exportExcelFangzhenKnowledges(List<Map<String, List<Member>>> list) {
        if (list == null) {
            return null;
        }
        //List<Subjects> strList = officeMapper.getSubject();
        DecimalFormat df = new DecimalFormat("######0.00");
        //创建一个webbook,对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        for (Map<String, List<Member>> map : list) {
            for (String key : map.keySet()) {
                //在webbook中添加一个sheet，对应Excel文件中的sheet
                HSSFSheet sheet = wb.createSheet(key);
                //在sheet中添加表头第0行
                HSSFRow row = sheet.createRow((int) 0);
                //创建单元格，并设置值表头，设置表头居中
                HSSFCellStyle style = wb.createCellStyle();
                //设置对其方式，居中对齐
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                HSSFCell cell = row.createCell(0);
                cell.setCellValue("学号");
                cell = row.createCell(1);
                cell.setCellValue("姓名");
                cell = row.createCell(2);
                cell.setCellValue("成绩");
                cell = row.createCell(3);
                cell.setCellValue("日期");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                //获取班级内的学生信息
                List<Member> value = map.get(key);
                for (int j = 0; j < value.size(); j++) {
                    row = sheet.createRow(j + 1);
                    Member member = value.get(j);
                    row.createCell(0).setCellValue(member.getUserName());
                    row.createCell(1).setCellValue(member.getName());
                    row.createCell(2).setCellValue(member.getSex());
                    row.createCell(3).setCellValue(member.getMsn());
                }
            }
        }
        return wb;
    }

    public List<Map<String, List<Member>>> daoFangzhenKnowledges(String classId,List<Knowledges> knoList ) throws ParseException {
        if (knoList.size() <= 0) {
            return null;
        }

        //获取系统时间，并且转码成数据库可读
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = df.format(d);
        List<Map<String, List<Member>>> listAll = new ArrayList<Map<String, List<Member>>>();
        //遍历所有的班级
        for(int j = 0 ;j<knoList.size();j++){
            //根据班级id找到所在的所有学生信息
            List<Member> lm = getStudentsInfos(classId);
            List<Member> memberList = new ArrayList<Member>();
            if (lm == null) {
                continue;
            }
                for(int i = 0 ;i<lm.size();i++){
                    lm.get(i).setSex("0");
                    lm.get(i).setMsn(dateNowStr);
                    System.out.println(knoList.get(j).getKnowledgecontentId());
                    System.out.println(lm.get(i).getId());
                    List<StutotalScoresForYXY> list = subjectTreeMapper.getScoresForYXYStu(knoList.get(j).getKnowledgecontentId(),lm.get(i).getId());
                    if(list.size()>0){
                        for (StutotalScoresForYXY stu:list) {
                            Member member =  new Member();
                            member.setId(lm.get(i).getId());
                            member.setName(lm.get(i).getName());
                            member.setUserName(lm.get(i).getUserName());
                            member.setMsn(stu.getEndDate());
                            member.setSex(Integer.toString(stu.getQuesScore()));
                            memberList.add(member);
                        }
                    }
                }
            Map<String, List<Member>> map = new HashMap<String, List<Member>>();
            if(memberList.size()>0){
                for (Member member:memberList) {
                    lm.add(member);
                }
                map.put(knoList.get(j).getContent(), lm);
            }else{
                map.put(knoList.get(j).getContent(), lm);
            }
            listAll.add(map);
        }
        return listAll;
    }


    public HSSFWorkbook exportExcelFangzhen(List<Map<String, List<Member>>> list, List<Knowledges> strList) {
        if (list == null) {
            return null;
        }
        //List<Subjects> strList = officeMapper.getSubject();
        DecimalFormat df = new DecimalFormat("######0.00");
        //创建一个webbook,对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        for (Map<String, List<Member>> map : list) {
            for (String key : map.keySet()) {
                //在webbook中添加一个sheet，对应Excel文件中的sheet
                HSSFSheet sheet = wb.createSheet(key);
                //在sheet中添加表头第0行
                HSSFRow row = sheet.createRow((int) 0);
                //创建单元格，并设置值表头，设置表头居中
                HSSFCellStyle style = wb.createCellStyle();
                //设置对其方式，居中对齐
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                HSSFCell cell = row.createCell(0);
                cell.setCellValue("学号");
                cell = row.createCell(1);
                cell.setCellValue("姓名");
                for (int i = 0; i < strList.size(); i++) {
                    cell = row.createCell((i + 2));
                    cell.setCellValue(strList.get(i).getContent());
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                //获取班级内的学生信息
                List<Member> value = map.get(key);
                for (int j = 0; j < value.size(); j++) {

                    row = sheet.createRow(j + 1);
                    Member member = value.get(j);
                    row.createCell(1).setCellValue(member.getName());
                    row.createCell(0).setCellValue(member.getUserName());

                    double count = 0.00;
                    for (int i = 0; i < strList.size(); i++) {
                        //StuQueInfors.cut();
                        //String obj =officeMapper.getSubTreeId(strList.get(i).getId());
                        //String obj2 =officeMapper.getKnowledgeContentId(obj);

                        StuQueInfors stuQueInfors = new StuQueInfors();
                        stuQueInfors.setPagerKey(strList.get(i).getKnowledgecontentId());
                        stuQueInfors.setStuKey(member.getId());
                        Integer chengji = zongfen(strList.get(i).getKnowledgecontentId(), member.getId());
                        if (chengji == null) {
                            chengji = 0;
                        }

                        double cji = chengji;
                        if (i != strList.size() - 1) {
                            count += cji;
                        }
                        row.createCell((i + 2)).setCellValue(chengji);
                    }
                }
            }
        }
        return wb;
    }

    public List<Map<String, List<Member>>> daoFangzhen(String depertId) {
        if (depertId == null) {
            return null;
        }
        //获取所有的班级
        List<Classes> list = new ArrayList<>();

        List<String> majorIdList = usersMapper.getMajorId(depertId);
        list = usersMapper.getClassList(majorIdList);
        /*for (String majorKey:majorId) {
            Classes classes = usersMapper.getClassList(majorKey);
            if(classes!=null){
                list.add(classes);
            }
        }*/
        List<Map<String, List<Member>>> listAll = new ArrayList<Map<String, List<Member>>>();
        //遍历所有的班级
        for (Classes cl : list) {
            //根据班级id找到所在的所有学生信息
            List<Member> lm = getStudentsInfos(cl.getId());
            if (lm == null) {
                continue;
            }
            Map<String, List<Member>> map = new HashMap<String, List<Member>>();
            map.put(cl.getClassName(), lm);
            listAll.add(map);
        }
        return listAll;
    }

    public List<HSSFWorkbook> daoByClass(List<Exams> examList) {

        List<String> classList = new ArrayList<String>();

        for (int i = 0; i < examList.size(); i++) {
            String str = examList.get(i).getClassKey();
            boolean status = str.contains("______");
            if (status) {
                String[] strArr = str.split("\\______");
                for (int j = 0; j < strArr.length; ++j) {
                    classList.add(strArr[j]);
                }
            } else {
                classList.add(str);
            }
        }
        classList = quchong(classList);
        List<HSSFWorkbook> listAll = new ArrayList<>();
        //对按班级导出的处理
        List<Map<String, List<Member>>> lces = daoMemberByClasses(classList);
        HSSFWorkbook hwb1 = exportExcel2(lces, examList);
        if (null != hwb1)
            listAll.add(hwb1);
        return listAll;
    }

    /**
     * 将用户信息导出到excel上并保存到桌面
     *
     * @param list 导出信息的集合
     */
    public HSSFWorkbook exportExcel2(List<Map<String, List<Member>>> list, List<Exams> strList) {
        if (list == null) {
            return null;
        }
        //List<Subjects> strList = officeMapper.getSubject();

        DecimalFormat df = new DecimalFormat("######0.00");

        //创建一个webbook,对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        for (Map<String, List<Member>> map : list) {
            for (String key : map.keySet()) {
                //在webbook中添加一个sheet，对应Excel文件中的sheet
                HSSFSheet sheet = wb.createSheet(key);
                //在sheet中添加表头第0行
                HSSFRow row = sheet.createRow((int) 0);
                //创建单元格，并设置值表头，设置表头居中
                HSSFCellStyle style = wb.createCellStyle();
                //设置对其方式，居中对齐
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                HSSFCell cell = row.createCell(0);
                cell.setCellValue("学号");
                cell = row.createCell(1);
                cell.setCellValue("姓名");

                for (int i = 0; i < strList.size(); i++) {
                    cell = row.createCell((i + 2));
                    cell.setCellValue(strList.get(i).getName());
                   /* if(i!=strList.size()-1){
                        cell = row.createCell((i+2));
                        cell.setCellValue(strList.get(i).getName());
                    }else{
                        cell = row.createCell((i+2));
                        cell.setCellValue("平均分");
                    }*/
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                //获取班级内的学生信息
                List<Member> value = map.get(key);
                for (int j = 0; j < value.size(); j++) {

                    row = sheet.createRow(j + 1);
                    Member member = value.get(j);
                    row.createCell(1).setCellValue(member.getName());
                    row.createCell(0).setCellValue(member.getUserName());

                    double count = 0.00;
                    for (int i = 0; i < strList.size(); i++) {
                        //StuQueInfors.cut();
                        //String obj =officeMapper.getSubTreeId(strList.get(i).getId());
                        //String obj2 =officeMapper.getKnowledgeContentId(obj);

                        StuQueInfors stuQueInfors = new StuQueInfors();
                        //stuQueInfors.setPagerKey(obj2);
                        stuQueInfors.setPagerKey(strList.get(i).getExamPager());
                        stuQueInfors.setStuKey(member.getId());
                        Integer chengji = zongfen(strList.get(i).getExamPager(), member.getId());
                        if (chengji == null) {
                            chengji = 0;
                        }

                        double cji = chengji;
                        if (i != strList.size() - 1) {
                            count += cji;
                        }
                        //row.createCell((i+2)).setCellValue(chengji);
                        row.createCell((i + 2)).setCellValue(chengji);
                        /*if(i!=strList.size()-1){
                            row.createCell((i+2)).setCellValue(chengji);
                        }else{
                            row.createCell((i+2)).setCellValue(df.format(count/(strList.size()-1)));
                        }*/
                    }
                }
            }
        }
        System.err.println(wb);
        //Classes.cut();
        return wb;
    }

    public int zongfen(String pagerKey, String stuKey) {

        int zongfen = 0;
        List<Double> list = stuQueInforsMapper.seleQuesScore(pagerKey, stuKey);
        if (list != null) {
            for (Double double1 : list) {
                double d = double1.doubleValue();
                zongfen += (int) d;
            }
        }
        return zongfen;
    }

    public List<Map<String, List<Member>>> daoMemberByClasses(List<String> classList) {
        if (classList.size() < 1) {
            return null;
        }
        //获取所有的班级
        List<Classes> list = new ArrayList<>();
        for (String classKey : classList) {
            Classes classes = usersMapper.getClass(classKey);
            if (classes != null) {
                list.add(classes);
            }
        }
        List<Map<String, List<Member>>> listAll = new ArrayList<Map<String, List<Member>>>();
        //遍历所有的班级
        for (Classes cl : list) {
            //根据班级id找到所在的所有学生信息
            List<Member> lm = getStudentsInfos(cl.getId());
            if (lm == null) {
                continue;
            }
            Map<String, List<Member>> map = new HashMap<String, List<Member>>();
            map.put(cl.getClassName(), lm);
            listAll.add(map);
        }
        return listAll;
    }


    @Override
    public List<HSSFWorkbook> exportInfos(String classes, String students, String teachers, String admins) {
        List<HSSFWorkbook> listAll = new ArrayList<>();
        //对按班级导出的处理
        List<Map<String, List<Member>>> lces = getMemberByClasses(classes);
        HSSFWorkbook hwb1 = exportExcel(lces);
        //对直接导出所有学生进行处理
        List<Map<String, List<Member>>> lces1 = getMemberOfAll(students);
        HSSFWorkbook hwb2 = exportExcel(lces1);
        //对老师信息的导出处理
        List<Map<String, List<Member>>> loes2 = getTeachers(teachers);
        HSSFWorkbook hwb3 = exportExcel(loes2);
        //对管理员信息的导出处理
        List<Map<String, List<Member>>> loes3 = getadminsOfAll(admins);
        HSSFWorkbook hwb4 = exportExcel(loes3);
        if (null != hwb1)
            listAll.add(hwb1);
        if (null != hwb2)
            listAll.add(hwb2);
        if (null != hwb3)
            listAll.add(hwb3);
        if (null != hwb4)
            listAll.add(hwb4);
        return listAll;
    }

    @Override
    public List<HSSFWorkbook> exportInfoByCollege(String college) {
        List<HSSFWorkbook> listAll = new ArrayList<>();
        //对按學院导出的处理
        List<Map<String, List<Member>>> lces = getMemberByCollege(college);
        HSSFWorkbook hwb1 = exportExcel(lces);
        if (null != hwb1)
            listAll.add(hwb1);
        return listAll;
    }

    /**
     * 根据學院获取所有学生信息
     *
     * @return 返回所有的学院和学生信息
     * @param    college    值为“true”或“false”
     */
    public List<Map<String, List<Member>>> getMemberByCollege(String college) {

        //获取该学院
        List<Department> list = departmentMapper.DepartmentListById(college);
        List<Map<String, List<Member>>> listAll = new ArrayList<Map<String, List<Member>>>();
        //遍历所有的学院
        for (Department cl : list) {
            //根据班级id找到所在的所有学生信息
            List<Member> lm = getStudentsInfosByCollege(cl.getId());
            if (lm == null) {
                continue;
            }
            Map<String, List<Member>> map = new HashMap<String, List<Member>>();
            map.put(cl.getName(), lm);
            listAll.add(map);
        }
        return listAll;
    }

    /**
     * 获取学院内所有学生的信息
     *
     * @param collegeId
     * @return 返回学院内所有的学生信息
     */
    public List<Member> getStudentsInfosByCollege(String collegeId) {
        //班级全部学生id
        List<String> list = officeMapper.getUsersIdByCollege(collegeId);
        if (list.size() == 0) {
            return null;
        }
        List<Member> lm = getUsersInfos(list);
        return lm;
    }

    public List<Map<String, List<Member>>> getadminsOfAll(String admins) {
        if ("false".equals(admins)) {
            return null;
        }
        //获取所有的管理员id
        List<String> list = officeMapper.getAllAdmins();
        List<Member> lm = getUsersInfos(list);
        Map<String, List<Member>> map = new HashMap<String, List<Member>>();
        map.put("管理员信息表", lm);
        List<Map<String, List<Member>>> lf = new ArrayList<Map<String, List<Member>>>();
        lf.add(map);
        return lf;
    }

    /**
     * 将用户信息导出到excel上并保存到桌面
     *
     * @param list 导出信息的集合
     */
    public HSSFWorkbook exportExcel(List<Map<String, List<Member>>> list) {
        if (list == null) {
            return null;
        }
        //创建一个webbook,对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        for (Map<String, List<Member>> map : list) {
            for (String key : map.keySet()) {
                //在webbook中添加一个sheet，对应Excel文件中的sheet
                HSSFSheet sheet = wb.createSheet(key);
                //在sheet中添加表头第0行
                HSSFRow row = sheet.createRow((int) 0);
                //创建单元格，并设置值表头，设置表头居中
                HSSFCellStyle style = wb.createCellStyle();
                //设置对其方式，居中对齐
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                HSSFCell cell = row.createCell(0);
                cell.setCellValue("姓名");
                cell = row.createCell(1);
                cell.setCellValue("性别");
                cell = row.createCell(2);
                cell.setCellValue("出生日期");
                cell = row.createCell(3);
                cell.setCellValue("个人电话");
                cell = row.createCell(4);
                cell.setCellValue("QQ");
                cell = row.createCell(5);
                cell.setCellValue("邮箱");
                cell = row.createCell(6);
                cell.setCellValue("MSN");
                cell = row.createCell(7);
                cell.setCellValue("星座");
                cell = row.createCell(8);
                cell.setCellValue("家庭住址");
                cell = row.createCell(9);
                cell.setCellValue("家庭电话");
                cell = row.createCell(10);
                cell.setCellValue("账号");
                cell = row.createCell(11);
                cell.setCellValue("密码");
                cell = row.createCell(12);
                cell.setCellValue("创建日期");
                cell.setCellStyle(style);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                //获取班级内的学生信息
                List<Member> value = map.get(key);
                for (int j = 0; j < value.size(); j++) {
                    row = sheet.createRow(j + 1);
                    Member member = value.get(j);
                    row.createCell(1).setCellValue(member.getSex());
                    row.createCell(0).setCellValue(member.getName());
                    System.err.println(member.getBirthDate());
                    if (member.getBirthDate() == null)
                        row.createCell(2).setCellValue("");
                    else
                        row.createCell(2).setCellValue(sdf1.format(new Date(member.getBirthDate().getTime())));
                    row.createCell(3).setCellValue(member.getMobilePhoneNum());
                    row.createCell(4).setCellValue(member.getQqNum());
                    row.createCell(5).setCellValue(member.getEmail());
                    row.createCell(6).setCellValue(member.getMsn());
                    row.createCell(7).setCellValue(member.getConstellation());
                    row.createCell(8).setCellValue(member.getFamilyAddress());
                    row.createCell(9).setCellValue(member.getFamilyPhoneNum());
                    row.createCell(10).setCellValue(member.getUserName());
                    row.createCell(11).setCellValue(member.getPassword());
                    row.createCell(12).setCellValue(sdf.format(member.getCreateDate()));
                }
            }
        }
        System.err.println(wb);
        return wb;
    }

    /**
     * 根据班级获取所有学生信息
     *
     * @return 返回所有的班级和学生信息
     * @param    classes    值为“true”或“false” 表示是否按班级获取学生信息
     */
    public List<Map<String, List<Member>>> getMemberByClasses(String classes) {
        if ("false".equals(classes)) {
            return null;
        }
        //获取所有的班级
        List<Classes> list = officeMapper.getclasses();
        List<Map<String, List<Member>>> listAll = new ArrayList<Map<String, List<Member>>>();
        //遍历所有的班级
        for (Classes cl : list) {
            //根据班级id找到所在的所有学生信息
            List<Member> lm = getStudentsInfos(cl.getId());
            if (lm == null) {
                continue;
            }
            Map<String, List<Member>> map = new HashMap<String, List<Member>>();
            map.put(cl.getClassName(), lm);
            listAll.add(map);
        }
        return listAll;
    }

    /**
     * 获取班级内所有学生的信息
     *
     * @param classId
     * @return 返回班级内所有的学生信息
     */
    public List<Member> getStudentsInfos(String classId) {
        //班级全部学生id
        List<String> list = officeMapper.getUsersId(classId);
        if (list.size() == 0) {
            return null;
        }
        List<Member> lm = getUsersInfos(list);
        return lm;
    }

    /**
     * 获取所有的学生信息
     *
     * @param students
     * @return
     */
    public List<Map<String, List<Member>>> getMemberOfAll(String students) {
        if ("false".equals(students)) {
            return null;
        }
        //获取所有的学生
        //获取所有的学生id
        List<String> stuIds = officeMapper.getAllStudents();
        List<Member> lm = getUsersInfos(stuIds);
        Map<String, List<Member>> map = new HashMap<String, List<Member>>();
        map.put("学生信息表", lm);
        List<Map<String, List<Member>>> list = new ArrayList<Map<String, List<Member>>>();
        list.add(map);
        return list;
    }

    /**
     * 查询所有的老师信息
     *
     * @param teachers
     * @return
     */
    public List<Map<String, List<Member>>> getTeachers(String teachers) {
        if ("false".equals(teachers)) {
            return null;
        }
        //获取所有老师id
        List<String> teaIds = officeMapper.getAllTeachers();
        List<Member> lm = getUsersInfos(teaIds);
        Map<String, List<Member>> map = new HashMap<String, List<Member>>();
        List<Map<String, List<Member>>> list = new ArrayList<Map<String, List<Member>>>();
        map.put("老师信息表", lm);
        list.add(map);
        return list;
    }

    /**
     * 根据用户ld集合获取集合内所有的用户信息
     */
    public List<Member> getUsersInfos(List<String> usersId) {
        List<Member> lm = new ArrayList<Member>();
        for (String str : usersId) {
            Member member = officeMapper.getUsersInfos(str);
            if (member == null) {
                continue;
            }
            member.setId(str);
            lm.add(member);
        }
        return lm;
    }

    @Override
    public void importExcelInfos(MultipartFile file) {
        ExcelUtil excelUtil = new ExcelUtil();
        Map<String, List<Member>> map = null;
        try {
            map = excelUtil.importExcel(file);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //从数据库中获取所有的班级名
        List<Classes> classes = officeMapper.getclasses();
        //建立班级名与id之间的映射关系
        Map<String, String> map1 = new HashMap<String, String>();
        for (Classes cs : classes) {
            map1.put(cs.getClassName(), cs.getId());
        }
        Set<String> classNames = map1.keySet();
        //遍历班级名
        String classId = null;
        ClassUsers cu = new ClassUsers();
        for (String className : map.keySet()) {
            boolean key = false;
            if (!classNames.contains(className)) {
                Classes cl = new Classes();
                classId = UUID.randomUUID().toString();
                cl.setId(classId);
                cl.setClassName(className);
                officeMapper.createClass(cl);
                key = true;
            }
            List<Member> list = map.get(className);
            //遍历班级内所有的学生
            for (Member member : list) {
                //插入学生信息
                usersMapper.creUser(member);
                cu.setId(UUID.randomUUID().toString());
                cu.setUser(member.getId());
                if (key)
                    cu.setClassId(classId);
                else {
                    cu.setClassId(map1.get(className));
                }
                //在数据库中建立学生与班级的关联关系
                usersMapper.creClassUser(cu);
                //建立学生与权限之间的关系
                String roleId = rolesMapper.selIdByRoleName("student");
                rolesMapper.insUsersRoles(cu.getUser(), roleId);
            }
        }
    }
}
