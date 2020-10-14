package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.InterpretMapper;
import com.tdu.develop.resource.pojo.Interpret;
import com.tdu.develop.resource.service.InterpretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterpretServiceImpl implements InterpretService {

    @Autowired
    InterpretMapper interpretMapper;

    @Override
    public void addInterpret(Interpret interpret) {
        interpretMapper.addInterpret(interpret);
    }

    @Override
    public List<Interpret> getInterResultList(String nandu, String page, String userKey, String pageKey) {
        return interpretMapper.getInterResultList(nandu, page, userKey, pageKey);
    }
}
