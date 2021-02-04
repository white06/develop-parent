package com.tdu.develop.resource.service;


import com.tdu.develop.resource.pojo.CusAddress;
import com.tdu.develop.resource.pojo.CustomerInformation;
import com.tdu.develop.resource.pojo.blue;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 志阳
 * @create 2021-01-22-14:39
 */
public interface CustomerService {
    List<Map<String,Object>> getCustomerInfo();

    public void delCustomer(String customerId);

    public void delCusAddress(String customerId);

    public void insertCustomer(CustomerInformation customerInformation, CusAddress cusAddress);

    public void createCustomer(CustomerInformation customerInformation, CusAddress cusAddress);

    public CustomerInformation getCustomerforId(String customerId);

    public CusAddress getAddressforId(String customerId);

    public boolean addAddress(List<CusAddress> cusAddressList);


    public String allInsertCustomer(  List<CustomerInformation> objectList);
}
