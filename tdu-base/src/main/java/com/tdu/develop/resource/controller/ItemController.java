package com.tdu.develop.resource.controller;

import com.tdu.develop.resource.pojo.CusAddress;
import com.tdu.develop.resource.pojo.CustomerInformation;
import com.tdu.develop.resource.pojo.Linshi;
import com.tdu.develop.resource.pojo.blue;
import com.tdu.develop.resource.service.CustomerService;
import com.tdu.develop.resource.service.ItemService;
import com.tdu.develop.resource.service.impl.CustomerServiceImpl;
import com.tdu.develop.resource.service.impl.ItemServiceImpl;
import com.tdu.develop.user.pojo.CellObject;
import com.tdu.develop.util.ExcelClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 志阳
 * @create 2020-09-10-10:24
 */
@CrossOrigin
@Controller
@RequestMapping(value = "ItemController")
public class ItemController {
    @Autowired
    ItemService itemService = new ItemServiceImpl();

    @Autowired
    CustomerService customerService = new CustomerServiceImpl();
    /**
     * 福建农林大学 -永恒之蓝使用
     *
     * @param request
     * @param session
     * @throws Exception
     */

    @RequestMapping(value = "yuxi.action", method = {RequestMethod.POST})
    @ResponseBody
    public void yuxi(HttpServletRequest request, HttpSession session) throws Exception {
        String userId = request.getParameter("userId");
        String yuxi = request.getParameter("yuxi");

        itemService.yuxi(yuxi, userId);

    }

    @RequestMapping(value = "getyuxi.action", method = {RequestMethod.POST})
    @ResponseBody
    public String getyuxi(HttpServletRequest request, HttpSession session) throws Exception {
        String userId = request.getParameter("userId");

        return itemService.getyuxi(userId);

    }

    @RequestMapping(value = "getblue.action", method = {RequestMethod.POST})
    @ResponseBody
    public blue getblue(HttpServletRequest request, HttpSession session) throws Exception {
        String userId = request.getParameter("userId");

        return itemService.getblue(userId);

    }

    @RequestMapping(value = "tijiao.action", method = {RequestMethod.POST})
    @ResponseBody
    public void tijiao(HttpServletRequest request, HttpSession session) throws Exception {
        String userId = request.getParameter("userId");
        String caozuo = request.getParameter("caozuo");
        itemService.tijiaocaozuo(userId, caozuo);

    }

    @RequestMapping(value = "tijiaobaogao.action", method = {RequestMethod.POST})
    @ResponseBody
    public void tijiaobaogao(HttpServletRequest request, HttpSession session) throws Exception {
        String userId = request.getParameter("userId");
        String baogao = request.getParameter("baogao");
        itemService.tijiaobaogao(userId, baogao);

    }

    @RequestMapping(value = "get.action", method = {RequestMethod.POST})
    @ResponseBody
    public blue get(HttpServletRequest request, HttpSession session) throws Exception {
        String userId = request.getParameter("userId");

        return itemService.get(userId);

    }


    /**
     * crm部分
     */


    /**
     * 获取客户信息表
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getCustomerforId.action", method = {RequestMethod.POST})
    @ResponseBody
    public CustomerInformation getCustomerforId(HttpServletRequest request, HttpSession session) throws Exception {
        String customerId = request.getParameter("customerId");
        System.out.println("返回+值="+customerService.getCustomerforId(customerId));
        return customerService.getCustomerforId(customerId);

    }



    /**
     * 获取客户地址
     * @param request
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "getAddressforId.action", method = {RequestMethod.POST})
    @ResponseBody
    public CusAddress getAddressforId(HttpServletRequest request, HttpSession session) throws Exception {
        String customerId = request.getParameter("customerId");
        return customerService.getAddressforId(customerId);

    }


    @RequestMapping(value = "getCustomers.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Map<String,Object>> getCustomers(HttpServletRequest request, HttpSession session) throws Exception {

        return customerService.getCustomerInfo();

    }

    /**
     * 删除客户信息
     * @param request
     * @param session
     * @throws Exception
     */
    @RequestMapping(value = "delCustomer.action", method = {RequestMethod.POST})
    @ResponseBody
    public void delCustomer(HttpServletRequest request, HttpSession session) throws Exception {
        String customerId = request.getParameter("customerId");
        customerService.delCusAddress(customerId);
        customerService.delCustomer(customerId);
    }

    @RequestMapping(value = "insertCustomer.action", method = {RequestMethod.POST})
    @ResponseBody
    public void insertCustomer(HttpServletRequest request, HttpSession session) throws Exception {
        String id = UUID.randomUUID().toString();
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String roleName = request.getParameter("roleName");
        String roleJob = request.getParameter("roleJob");
        String rolePhone = request.getParameter("rolePhone");
        String itemCount = request.getParameter("itemCount");
        System.out.println(id+","+name+","+address+","+roleName+","+roleJob+","+rolePhone+","+itemCount);
        CustomerInformation customerInformation=new CustomerInformation();
        customerInformation.setId(id);
        customerInformation.setName(name);
        customerInformation.setAddress(address);
        customerInformation.setRoleName(roleName);
        customerInformation.setRoleJob(roleJob);
        customerInformation.setRolePhone(rolePhone);
        customerInformation.setItemCount(itemCount);
        String shengid = UUID.randomUUID().toString();
        String sheng=request.getParameter("sheng");
        String shi=request.getParameter("shi");

        CusAddress cusAddress=new CusAddress();
        cusAddress.setId(shengid);
        cusAddress.setCusId(id);
        cusAddress.setSheng(sheng);
        cusAddress.setShi(shi);




        customerService.insertCustomer(customerInformation,cusAddress);
    }

    @RequestMapping(value = "createCustomer.action", method = {RequestMethod.POST})
    @ResponseBody
    public void createCustomer(HttpServletRequest request, HttpSession session) throws Exception {
        String customerId = request.getParameter("customerId");
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String roleName = request.getParameter("roleName");
        String roleJob = request.getParameter("roleJob");
        String rolePhone = request.getParameter("rolePhone");
        String itemCount = request.getParameter("itemCount");
        System.out.println(customerId+","+name+","+address+","+roleName+","+roleJob+","+rolePhone+","+itemCount);
        CustomerInformation customerInformation=new CustomerInformation();
        customerInformation.setId(customerId);
        customerInformation.setName(name);
        customerInformation.setAddress(address);
        customerInformation.setRoleName(roleName);
        customerInformation.setRoleJob(roleJob);
        customerInformation.setRolePhone(rolePhone);
        customerInformation.setItemCount(itemCount);
        String shengid = request.getParameter("shengId");
        String sheng=request.getParameter("sheng");
        String shi=request.getParameter("shi");
        CusAddress cusAddress=new CusAddress();
        cusAddress.setId(shengid);
        cusAddress.setCusId(customerId);
        cusAddress.setSheng(sheng);
        cusAddress.setShi(shi);





        customerService.createCustomer(customerInformation,cusAddress);
    }



    // 用户信息批量导入
    @RequestMapping(value = "getFile.action", method = {RequestMethod.POST})
    @ResponseBody
    public void getFile(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam("file") MultipartFile file) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String getDate = simpleDateFormat.format(date);

        Date endDay = new Date();

        //创建Calendar实例
        Calendar cal = Calendar.getInstance();

        cal.setTime(endDay);   //设置当前时间
        cal.add(Calendar.MONTH, 1);
        String dateEndStr = simpleDateFormat.format(cal.getTime());

        String filePath = request.getServletContext().getRealPath("/");
        String fileName = file.getOriginalFilename();
        String fileWay = filePath + fileName;
        try {
            file.transferTo(new File(fileWay));
            ExcelClass excelClass = new ExcelClass();
            List<Map<String, Object>> sheetArray = excelClass.getExcelContent(fileWay);
            if (new File(fileWay).exists()) {
                new File(fileWay).delete();
            }
            List<String> errList = new ArrayList<>();
            for (Map<String, Object> sheetMap : sheetArray) {
                List<CustomerInformation> objectList = new ArrayList<CustomerInformation>();
                List<CusAddress> objectList2 = new ArrayList<CusAddress>();
                String sheetName = sheetMap.get("sheetName").toString();
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> sheetList = (List<Map<String, Object>>) sheetMap.get("sheetContent");
                response.setCharacterEncoding("UTF-8");
                for (Map<String, Object> cellMap : sheetList) {
                    CustomerInformation customerInformation = new CustomerInformation();
                    CusAddress cusAddress=new CusAddress();
                    // System.out.println("姓名:"+cellMap.get("姓名")+"用戶名:"+cellMap.get("用户名")+"密碼:"+cellMap.get("密码")+"性別:"+cellMap.get("性别")+"出生日期:"+cellMap.get("出生日期"));
                    if (cellMap.get("学校名称") == null || cellMap.get("学校地址") == null || cellMap.get("联系人") == null
                            || cellMap.get("职务") == null|| cellMap.get("联系人电话") == null|| cellMap.get("已合作项目数量") == null) {
                        response.getWriter().print("含有空列，请检查！用户表单不允许有空列！");
                        return;
                    }
                    String cusId=UUID.randomUUID().toString();
                    customerInformation.setId(cusId);
                    customerInformation.setName(cellMap.get("学校名称").toString());
                    customerInformation.setAddress(cellMap.get("学校地址").toString());
                    customerInformation.setRoleName(cellMap.get("联系人").toString());
                    customerInformation.setRoleJob(cellMap.get("职务").toString());
                    customerInformation.setRolePhone(cellMap.get("联系人电话").toString());
                    customerInformation.setItemCount(cellMap.get("已合作项目数量").toString());
                    cusAddress.setId(UUID.randomUUID().toString());
                    cusAddress.setSheng(cellMap.get("省").toString());
                    cusAddress.setShi(cellMap.get("市").toString());
                    cusAddress.setCusId(cusId);
                    objectList2.add(cusAddress);
                    objectList.add(customerInformation);
                }
                customerService.addAddress(objectList2);
                errList.add(customerService.allInsertCustomer(objectList));
            }
            response.setCharacterEncoding("UTF-8");
            if (errList.get(0) == "0") {
                response.getWriter().print("完全成功");
                return;
            } else {
                String errMessage = "导入成功，以下账号已存在:";
                for (String string : errList) {
                    errMessage += string + ",";
                }
                errMessage += "无法导入";
                response.getWriter().print(errMessage);
                return;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
