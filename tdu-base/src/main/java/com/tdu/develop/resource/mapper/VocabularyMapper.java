package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Vocabulary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VocabularyMapper {

    public List<Vocabulary> getVocabularyList(@Param("knowledgeId") String knowledgeId, @Param("type") String type);
}
