package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.VocabularyMapper;
import com.tdu.develop.resource.pojo.Vocabulary;
import com.tdu.develop.resource.service.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VocabularyServiceImpl implements VocabularyService {

    @Autowired
    VocabularyMapper vocabularyMapper;

    @Override
    public List<Vocabulary> getVocabularyList(String knowledgeId, String type) {
        return vocabularyMapper.getVocabularyList(knowledgeId, type);
    }
}
