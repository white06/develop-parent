package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.*;
import com.tdu.develop.resource.pojo.*;
import com.tdu.develop.resource.service.BusinessService;
import com.tdu.develop.resource.service.CustomerService;
import com.tdu.develop.user.mapper.UsersMapper;
import com.tdu.develop.user.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author 志阳
 * @create 2021-01-22-13:00
 */
@Service
public class BusinessServiceImpl implements BusinessService {
    @Autowired
    BusinessUserMapper businessUserMapper;
    @Autowired
    BusinessMapper businessMapper;
    @Autowired
    CustomerInformationMapper customerInformationMapper;
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    FollowMapper followMapper;

    public List<Map<String,Object>> getBusinessInfo(String userId){
        List<Map<String,Object>>  returnlist = new ArrayList<Map<String,Object>>();
        List<BusinessUser> businessUserList=new ArrayList<BusinessUser>();
        businessUserList=businessUserMapper.getBusinessForUserId(userId);
        for(int i=0;i<businessUserList.size();i++){
        //    String businessId=businessMapper.getBusinessId(businessUserList.get(i).getBusinessId());
            Business business=businessMapper.getBusiness(businessUserList.get(i).getBusinessId());
            Map<String,Object> busMap=new HashMap<String,Object>();
           //关联客户需要另找，从客户表
            CustomerInformation customerInformation=customerInformationMapper.getCustomerforId(business.getCustomerId());
            //关联用户id
            Users users=usersMapper.GetNowUser(business.getBusinessId());
            //跟进id
            Follow follow=followMapper.getLastFollow(business.getFollowId(),business.getId());




            busMap.put("id",business.getId());
            busMap.put("name",business.getName());
            //客户信息
            busMap.put("customer",customerInformation);
            busMap.put("businessUser",users);
            busMap.put("follow",follow);
            busMap.put("budget",business.getBudget());
            busMap.put("billTime",business.getBillTime());
            busMap.put("phase",business.getPhase());
            busMap.put("possibility",business.getPossibility());
            busMap.put("number",business.getNumber());
            busMap.put("founder",business.getFounder());
            busMap.put("collaboration",business.getCollaboration());
            busMap.put("creationTime",business.getCreationTime());
            busMap.put("beizhu",business.getBeizhu());
            returnlist.add(busMap);
        }
        return returnlist;
    }


    public Map<String,Object> getBusinessForid(String busId){
        Business business=businessMapper.getBusiness(busId);
        Map<String,Object> busMap=new HashMap<String,Object>();
        //关联客户需要另找，从客户表
        CustomerInformation customerInformation=customerInformationMapper.getCustomerforId(business.getCustomerId());
        //关联用户id
        Users users=usersMapper.GetNowUser(business.getBusinessId());
        //跟进id
        Follow follow=followMapper.getLastFollow(business.getFollowId(),business.getId());




        busMap.put("id",business.getId());
        busMap.put("name",business.getName());
        //客户信息
        busMap.put("customer",customerInformation);
        busMap.put("businessUser",users);
        busMap.put("follow",follow);
        busMap.put("budget",business.getBudget());
        busMap.put("billTime",business.getBillTime());
        busMap.put("phase",business.getPhase());
        busMap.put("possibility",business.getPossibility());
        busMap.put("number",business.getNumber());
        busMap.put("founder",business.getFounder());
        busMap.put("collaboration",business.getCollaboration());
        busMap.put("creationTime",business.getCreationTime());
        busMap.put("beizhu",business.getBeizhu());
        return busMap;
    }
    /**
     * 修改商机
     * @param busMap
     * @return
     */
    public boolean modifyBusiness(Map<String,Object> busMap){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
        String busId="";
        if(busMap.get("id")!=null){
            busId=busMap.get("id").toString();
        }
        Business business=businessMapper.getBusiness(busId);
        business.setName(busMap.get("name").toString());
        business.setBudget(busMap.get("budget").toString());
        if(busMap.get("billTime").toString()!=null){
            try {
                business.setBillTime(format.parse(busMap.get("billTime").toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        business.setCustomerId(busMap.get("customerId").toString());
        business.setBusinessId(busMap.get("businessId").toString());
        business.setPhase(busMap.get("phase").toString());
        business.setPossibility(busMap.get("possibility").toString());
        business.setNumber(busMap.get("number").toString());
        business.setFounder(busMap.get("founder").toString());
        if(busMap.get("creationTime").toString()!=null){
            try {
                business.setCreationTime(format.parse(busMap.get("creationTime").toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        business.setBeizhu(busMap.get("beizhu").toString());

        businessMapper.modifyBusiness(business);
        return true;
    }


    /**
     * 新增商机
     * @param busMap
     * @return
     */
    public boolean insBusiness(Map<String,Object> busMap){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
        Follow follow=new Follow();
        if(busMap.get("followId")!=null){
            follow.setId(busMap.get("followId").toString());
            if(busMap.get("lastTime").toString()!=null){
                try {
                    follow.setLastTime(format.parse(busMap.get("lastTime").toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            follow.setType(busMap.get("type").toString());
            follow.setContent(busMap.get("content").toString());
            follow.setBusinessId(busMap.get("id").toString());
            follow.setUserName(busMap.get("ROLEname").toString());
        }

        followMapper.insertFollow(follow);


        String busId="";


        if(busMap.get("id")!=null){
            busId=busMap.get("id").toString();
        }
        Business business=new Business();
        business.setId(busId);
        business.setName(busMap.get("name").toString());
        business.setBudget(busMap.get("budget").toString());
        if(busMap.get("billTime").toString()!=null){
            try {
                business.setBillTime(format.parse(busMap.get("billTime").toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        business.setFollowId(busMap.get("followId").toString());
        business.setCustomerId(busMap.get("customerId").toString());
        business.setBusinessId(busMap.get("businessId").toString());
        business.setPhase(busMap.get("phase").toString());
        business.setPossibility(busMap.get("possibility").toString());
        business.setNumber(busMap.get("number").toString());
        business.setCollaboration(busMap.get("collaboration").toString());
        business.setFounder(busMap.get("founder").toString());
        if(busMap.get("creationTime").toString()!=null){
            try {
                business.setCreationTime(format.parse(busMap.get("creationTime").toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        business.setBeizhu(busMap.get("beizhu").toString());
        BusinessUser businessUser=new BusinessUser();
        businessUser.setId(UUID.randomUUID().toString());
        businessUser.setUserId(busMap.get("businessId").toString());
        businessUser.setBusinessId(busMap.get("id").toString());
        businessUserMapper.modifyCustomer(businessUser);


        businessMapper.modifyBusiness(business);

        return true;
    }


    public boolean deleBusiness(String busId){
        followMapper.delFollowforBus(busId);
        businessMapper.delBusiness(busId);
        businessUserMapper.delBusiness(busId);
        return true;
    }

    public boolean insFollow(Follow follow){
        followMapper.insertFollow(follow);
        return true;
    }

    public boolean modifyFollow(Follow follow){
        followMapper.modifyFollow(follow);
        return true;
    }

    public boolean deltFollow(String followId){
        followMapper.delFollow(followId);
        return true;
    }
    public List<Follow> getFollow(String busId){
        List<Follow> followsList=new ArrayList<Follow>();
        if(busId!=null){
            followsList=followMapper.getFollow(busId);
        }
        return followsList;
    }


    /**
     * 判断跟进记录是否存在
     */
//    public boolean panduanFollow(String businessId,String lastTime,String userId){
//        List<BusinessUser> getBusinessUser=businessUserMapper.getBusinessForUserId(userId);
//        for(int i=0;i<getBusinessUser.size();i++){
//            BusinessUser businessUser=followMapper.getFollow()
//        }
//    }
}
