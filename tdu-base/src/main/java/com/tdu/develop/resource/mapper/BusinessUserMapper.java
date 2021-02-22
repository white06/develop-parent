package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Business;
import com.tdu.develop.resource.pojo.BusinessUser;
import com.tdu.develop.resource.pojo.Follow;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2021-01-22-13:37
 */
@Repository
public interface BusinessUserMapper {

   //查询所有商机
   public List<BusinessUser> getBusinessUser(@Param("businessId")String businessId);

   //查询个人所有商机
   public List<BusinessUser> getBusinessForUserId(@Param("UserId")String UserId);
   //新增跟单
   public void insertBusiness(BusinessUser businessUse);

   //删除跟单记录
   public void delBusiness(@Param("businessUserId")String businessUserId);

   //新增客户
   public void modifyCustomer(BusinessUser businessUse);
}
