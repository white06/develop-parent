package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Business;
import com.tdu.develop.resource.pojo.Follow;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author 志阳
 * @create 2021-01-22-13:37
 */
@Repository
public interface BusinessMapper {

   //查询所有商机
   public Business getBusiness(@Param("businessId")String businessId);

   //新增跟单
   public void insertBusiness(Business business);

   //删除跟单记录
   public void delBusiness(@Param("businessId")String businessId);

   //新增客户
   public void modifyBusiness(Business business);

   public String getBusinessId(@Param("businessId")String businessId);
}
