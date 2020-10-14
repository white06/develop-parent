package com.tdu.develop.user.service.impl;

import com.tdu.develop.user.mapper.MajorMapper;
import com.tdu.develop.user.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-19-9:46
 */
@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    MajorMapper majorMapper;

    public List<String> getMajorName(String userId) {
        // TODO Auto-generated method stub
        return majorMapper.getMajorName(userId);
    }
}
