package com.tdu.develop.models.service;

import com.tdu.develop.models.pojo.Voicecontents;
import com.tdu.develop.models.pojo.Voices;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-16-11:12
 */
public interface DevelopVoicesService {
    public List<Voices> getVoicesFis(String subTreeId);
    public List<Voices> getVoicesSecond(String parentKnowledge);
    public String getKnowledgecontentsName(String knowledge_Id);
    Voicecontents getVoiceContentName(String id);
}
