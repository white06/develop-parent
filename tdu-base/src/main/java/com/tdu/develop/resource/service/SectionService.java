package com.tdu.develop.resource.service;

import com.tdu.develop.resource.pojo.Word;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 志阳
 * @create 2019-11-12-14:05
 */
public interface SectionService {
    /**
     * 根据科目id加载所有的章节
     */
    
    public List<Map<String, Object>> loadSections() ;
    
    public void doSaveWord(Word word) ;
    
    public List<Map<String,Object>> loadSectionsTree(String subjectId) ;
    
    public Map<String,Object> doLoadWords(String sectionId, Integer page, Integer rows) ;
    
    public void doUpdateWord(Word word) ;
    
    public void doDeleteWord(String id) ;
    
    public List<Map<String, Object>> doLoadSections(String errorTreeId) ;
    
    public List<Map<String, Object>> loadChildNodes(String id) ;
    
    public List<Map<String, Object>> loadErrorQueBySection(String knowId, String userId) ;
    
    public List<Map<String, Object>> errorAllQue(String userId) ;
    
    public void removeQue(String queId) ;

    public List<Map<String, Object>> collectAllQue() ;

    public List<Map<String, Object>> loadcollectQueBySection(String knowId, String userId) ;
    
    public Map<String, Object> queryWordInfos(String page, String level) ;
}
