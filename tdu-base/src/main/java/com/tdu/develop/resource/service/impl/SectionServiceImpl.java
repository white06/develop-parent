package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.SectionMapper;
import com.tdu.develop.resource.pojo.Word;
import com.tdu.develop.resource.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 志阳
 * @create 2019-11-12-14:05
 */
@Service
public class SectionServiceImpl  implements SectionService {
    @Autowired
    private SectionMapper sectionMapper;
    /**
     * 根据科目id加载所有的章节
     */
    @Override
    public List<Map<String, Object>> loadSections() {
        List<Map<String,Object>> list = sectionMapper.getAllSections();
        return list;
    }
    @Override
    public void doSaveWord(Word word) {
        sectionMapper.insertWord(word, UUID.randomUUID().toString());
    }
    @Override
    public List<Map<String,Object>> loadSectionsTree(String subjectId) {
        //查询所有的大节点
        List<Map<String,Object>> list = sectionMapper.queryAllSections(subjectId);
        for(Map<String,Object> mt : list) {
            List<Map<String,Object>> listChild = sectionMapper.selAllChilds((String) mt.get("id"));
            if(listChild!=null&&listChild.size()>0) {
                mt.put("state", "closed");
                mt.put("children",listChild);
            }
        }
        return list;
    }
    @Override
    public Map<String,Object> doLoadWords(String sectionId,Integer page,Integer rows) {
        Map<String,Object> map = new HashMap<>();
        //计算起始位置
        int startIndex = (page-1)*rows;
        List<Map<String,Object>> list = sectionMapper.selAllWords(sectionId,startIndex,rows);
        int count = sectionMapper.setAllCount(sectionId);
        map.put("total", count);
        map.put("rows", list);
        return map;
    }
    @Override
    public void doUpdateWord(Word word) {
        sectionMapper.doUpdateWord(word);
    }
    @Override
    public void doDeleteWord(String id) {
        sectionMapper.deleteWord(id);
    }
    @Override
    public List<Map<String, Object>> doLoadSections(String errorTreeId) {
        List<Map<String,Object>> list = new ArrayList<>();
        int count = sectionMapper.selCount(errorTreeId);
        if(count>0) {
            Map<String,Object> firstSection = sectionMapper.selFirstSection(errorTreeId);
            String preId = (String) firstSection.get("Id");
            list.add(firstSection);
            for(int i=1;i<count;i++) {
                Map<String,Object> map = sectionMapper.selOtherSection(preId);
                preId = (String)map.get("Id");
                list.add(map);
            }
        }
        return list;
    }
    @Override
    public List<Map<String, Object>> loadChildNodes(String id) {
        List<Map<String,Object>> list = new ArrayList<>();
        int count = sectionMapper.selCounts(id);
        if(count>0) {
            Map<String,Object> fChild = sectionMapper.selFirstChild(id);
            list.add(fChild);
            String preId = (String) fChild.get("Id");
            for(int i=1;i<count;i++) {
                Map<String,Object> map = sectionMapper.selOtherSection(preId);
                preId = (String)map.get("Id");
                list.add(map);
            }
        }

        return list;
    }
    @Override
    public List<Map<String, Object>> loadErrorQueBySection(String knowId,String userId) {
        List<Map<String,Object>> list = sectionMapper.loadErrorQueBySection(knowId,userId);
        return list;
    }
    @Override
    public List<Map<String, Object>> errorAllQue(String userId) {
        List<Map<String,Object>> list = sectionMapper.errorAllQue(userId);
        return list;
    }
    @Override
    public void removeQue(String queId) {
        sectionMapper.deleteQue(queId);
    }
    @Override
    public List<Map<String, Object>> collectAllQue() {
        List<Map<String,Object>> list = sectionMapper.selAllCollect();
        return list;
    }
    @Override
    public List<Map<String, Object>> loadcollectQueBySection(String knowId, String userId) {
        List<Map<String,Object>> list = sectionMapper.loadcollectQueBySection(knowId,userId);
        return list;
    }
    @Override
    public Map<String, Object> queryWordInfos(String page, String level) {
        Map<String,Object> map = sectionMapper.queryWordInfos(Integer.parseInt(page)-1,level);
        return map;
    }
}
