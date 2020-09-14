package com.tdu.develop.models.mapper;

import com.tdu.develop.models.pojo.Voicecontents;
import com.tdu.develop.models.pojo.Voices;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-16-11:13
 */
@Repository
public interface DevelopVoicesMapper {
    /**
     * 获取Voices首节点信息
     * @return
     */
    public List<Voices> getVoicesFis(@Param("subTreeId") String subTreeId);

    public List<Voices> getVoicesSecond(String ParentVoice);
    /**
     * 获取仿真模型name信息
     * @param knowledge_Id
     * @return
     */
    public String getKnowledgecontentsName(String knowledge_Id);
    public Voicecontents getVoiceContentName(@Param("id") String id);
}
