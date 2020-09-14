package com.tdu.develop.resource.service;

import com.tdu.develop.resource.pojo.Vocabulary;

import java.util.List;

public interface VocabularyService {

    public List<Vocabulary> getVocabularyList(String knowledgeId, String type);
}
