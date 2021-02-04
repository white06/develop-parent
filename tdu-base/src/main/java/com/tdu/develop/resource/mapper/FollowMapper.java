package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.CustomerInformation;
import com.tdu.develop.resource.pojo.Follow;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2021-01-22-13:37
 */
@Repository
public interface FollowMapper {
   //查询跟单
   public Follow getLastFollow(@Param("businessId")String businessId,@Param("busId")String busId);

   //查询所有跟单
   public List<Follow> getFollow(@Param("businessId")String businessId);

   //查询是都存在某一跟单记录
   //新增跟单
   public void insertFollow(Follow follow);

   //删除跟单记录
   public void delFollow(@Param("businessId")String businessId);
   //删除跟单记录(整个商机）
   public void delFollowforBus(@Param("businessId")String businessId);

   //新增客户
   public void modifyFollow(Follow follow);
}
