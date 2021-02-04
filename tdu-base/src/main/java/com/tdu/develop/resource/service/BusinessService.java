package com.tdu.develop.resource.service;


import com.tdu.develop.resource.pojo.CustomerInformation;
import com.tdu.develop.resource.pojo.Follow;

import java.util.List;
import java.util.Map;

/**
 * @author 志阳
 * @create 2021-01-22-14:39
 */
public interface BusinessService {
     public List<Map<String,Object>> getBusinessInfo(String userId);

     public Map<String,Object> getBusinessForid(String busId);

     public boolean modifyBusiness(Map<String,Object> busMap);

     public boolean insBusiness(Map<String,Object> busMap);

     public boolean deleBusiness(String busId);

     public boolean insFollow(Follow follow);


     public boolean modifyFollow(Follow follow);

     public boolean deltFollow(String followId);

     public List<Follow> getFollow(String busId);
//
//     public boolean panduanFollow(String businessId,String lastTime,String userId);
}
