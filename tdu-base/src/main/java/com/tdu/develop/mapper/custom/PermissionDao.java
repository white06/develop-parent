package com.tdu.develop.mapper.custom;


import com.tdu.develop.model.auto.TsysPremission;

import java.util.List;

public interface  PermissionDao {
	/**
	 * 查询全部权限 
	 * @return
	 */
	public List<TsysPremission> findAll();
	 
	 /**
	  * 根据用户id查询出用户的所有权限
	  * @param userId
	  * @return
	  */
	 public List<TsysPremission> findByAdminUserId(String userId);
	 
	 /**
	  * 根据角色id查询权限
	  * @param roleid
	  * @return
	  */
	 public List<TsysPremission> queryRoleId(String roleid);
	 
	 
	 
	 
}
