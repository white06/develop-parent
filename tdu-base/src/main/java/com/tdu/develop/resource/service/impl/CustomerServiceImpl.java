package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.CustomerInformationMapper;
import com.tdu.develop.resource.mapper.ItemMapper;
import com.tdu.develop.resource.pojo.CusAddress;
import com.tdu.develop.resource.pojo.CustomerInformation;
import com.tdu.develop.resource.pojo.Item;
import com.tdu.develop.resource.pojo.blue;
import com.tdu.develop.resource.service.CustomerService;
import com.tdu.develop.resource.service.ItemService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 志阳
 * @create 2021-01-22-13:00
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    CustomerInformationMapper customerInformationMapper;


    /**
     * 获取学校客户信息
     * @return
     */
    public List<Map<String,Object>> getCustomerInfo() {
        //存放信息Map
        List<Map<String,Object>>  returnlist = new ArrayList<Map<String,Object>>();
        List<CustomerInformation>    CustomerList=customerInformationMapper.getCustomerId();
        CustomerInformation customerInformation1;
        List<Item>    itemList;
        if(CustomerList!=null){
            for(int i=0;i<CustomerList.size();i++){
                CusAddress cusAddress=new CusAddress();
                cusAddress = customerInformationMapper.getcusAddress(CustomerList.get(i).getId());
                Map<String,Object> cusMap=new HashMap<String,Object>();
                cusMap.put("id",CustomerList.get(i).getId());
                cusMap.put("name",CustomerList.get(i).getName());
                cusMap.put("address",CustomerList.get(i).getAddress());
                cusMap.put("roleName",CustomerList.get(i).getRoleName());
                cusMap.put("roleJob",CustomerList.get(i).getRoleJob());
                cusMap.put("rolePhone",CustomerList.get(i).getRolePhone());
                cusMap.put("itemCount",CustomerList.get(i).getItemCount());
                cusMap.put("sheng",cusAddress.getSheng());
                cusMap.put("shi",cusAddress.getShi());
                cusMap.put("shengid",cusAddress.getId());
                itemList = itemMapper.getItemInfo(CustomerList.get(i).getId());
                   List<String> itemArryList=new ArrayList<String>();
                for(int j=0;j<itemList.size();j++){
                    itemArryList.add(itemList.get(j).getItemName());
                }
                cusMap.put("item",itemArryList);
                returnlist.add(cusMap);
            }
        }
            return returnlist;
    }

    public CusAddress getAddressforId(String customerId){
        if(customerId!=null){
            return customerInformationMapper.getcusAddress(customerId);
        }else{
            return null;
        }
    }


    public void delCustomer(String customerId){
        if(customerId!=null){
            customerInformationMapper.delCustomer(customerId);
        }
    }


    public void delCusAddress(String customerId){
        if(customerId!=null){
            customerInformationMapper.delCusAddress(customerId);
        }
    }


    public void insertCustomer(CustomerInformation customerInformation ,CusAddress cusAddress){
        if(cusAddress!=null){
            customerInformationMapper.inscusAddress(cusAddress);
        }
        if(customerInformation!=null){
            customerInformationMapper.insertCustomer(customerInformation);
        }
    }

    public void createCustomer(CustomerInformation customerInformation, CusAddress cusAddress ){
        if(cusAddress!=null){
            customerInformationMapper.inscusAddress(cusAddress);
        }
        if(customerInformation!=null){
            customerInformationMapper.createCustomer(customerInformation);
        }
    }

    public CustomerInformation getCustomerforId(String customerId){
        if(customerId!=null){
           return customerInformationMapper.getCustomerforId(customerId);
        }else{
            return null;
        }
    }

    public String allInsertCustomer(  List<CustomerInformation> objectList){
        if(objectList!=null){
            for(int i=0;i<objectList.size();i++){
                customerInformationMapper.insertCustomer(objectList.get(i));
            }
            return "0";
        }else{
            return "1";
        }
    }

    public boolean addAddress(List<CusAddress> cusAddressList){
        if(cusAddressList!=null){
            for(int i=0;i<cusAddressList.size();i++){
                customerInformationMapper.inscusAddress(cusAddressList.get(i));
            }
        }
      return true;
    }
}
