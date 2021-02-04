package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.CusAddress;
import com.tdu.develop.resource.pojo.CustomerInformation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2021-01-22-13:37
 */
@Repository
public interface CustomerInformationMapper {

   //查找用户信息
   public List<CustomerInformation> getCustomerId();




   public void delCusAddress(@Param("customerId")String customerId);
   //删除客户
   public void delCustomer(@Param("customerId")String customerId);

   //新增客户
   public void insertCustomer(CustomerInformation customerInformation);

   //新增客户
   public void createCustomer(CustomerInformation customerInformation);
   //根据id查找用户
   public CustomerInformation getCustomerforId(@Param("customerId")String customerId);

   public CusAddress getcusAddress(@Param("cusId")String cusId);

   public void inscusAddress(CusAddress cusAddress);
}
