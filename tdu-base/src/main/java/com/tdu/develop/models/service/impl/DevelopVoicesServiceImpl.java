package com.tdu.develop.models.service.impl;

import com.tdu.develop.models.mapper.DevelopVoicesMapper;
import com.tdu.develop.models.pojo.Voicecontents;
import com.tdu.develop.models.pojo.Voices;
import com.tdu.develop.models.service.DevelopVoicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-16-11:12
 */
@Service
public class DevelopVoicesServiceImpl implements DevelopVoicesService {
    @Autowired
    DevelopVoicesMapper developVoicesMapper;
    /**
     * 获取首节点
     */
    public List<Voices> getVoicesFis(String subTreeId) {

        return developVoicesMapper.getVoicesFis(subTreeId);
    }

    /**
     * 获取次节点
     */
    @Override
    public List<Voices> getVoicesSecond(String parentKnowledge) {
        List<Voices> ksList=developVoicesMapper.getVoicesSecond(parentKnowledge);
        return ksList;
    }
    /**
     * 获取仿真名
     */
    @Override
    public String getKnowledgecontentsName(String knowledge_Id) {

        return developVoicesMapper.getKnowledgecontentsName(knowledge_Id);
    }

    /**
     * 获取声音内容表
     */
    @Override
    public Voicecontents getVoiceContentName(String id) {
        Voicecontents scenecontents = developVoicesMapper.getVoiceContentName(id);
        return scenecontents;
    }


}
