package com.tdu.develop.resource.service;

import java.util.List;

import com.tdu.develop.resource.pojo.Exams;
import com.tdu.develop.resource.pojo.Knowledges;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public interface OfficeService {

    List<HSSFWorkbook> exportInfos(String classes, String students, String teachers, String admins);

    List<HSSFWorkbook> exportInfoByCollege(String college);

    void importExcelInfos(MultipartFile file);

    List<HSSFWorkbook> daoByClass(List<Exams> examList);

    List<HSSFWorkbook> daoFangzhen(String depertId, List<Knowledges> knoList);
}
