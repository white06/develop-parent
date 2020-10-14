package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.Word;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 志阳
 * @create 2019-11-12-14:33
 */
@Repository
public interface SectionMapper {
    List<Map<String, Object>> getAllSections();

    void insertWord(@Param("words") Word word, @Param("id") String id);

    List<Map<String, Object>> queryAllSections(String subjectId);

    List<Map<String, Object>> selAllChilds(String id);

    List<Map<String, Object>> selAllWords(@Param("sectionId") String sectionId, @Param("startIndex") int startIndex, @Param("rows") Integer rows);

    void doUpdateWord(Word word);

    void deleteWord(String id);

    int setAllCount(String sectionId);

    int selCount(String errorTreeId);

    Map<String, Object> selFirstSection(String errorTreeId);

    Map<String, Object> selOtherSection(String preId);

    Map<String, Object> selFirstChild(String id);

    int selCounts(String parentId);

    List<Map<String, Object>> loadErrorQueBySection(@Param("knowId") String knowId, @Param("userId") String userId);

    List<Map<String, Object>> errorAllQue(String userId);

    void deleteQue(String queId);

    List<Map<String, Object>> selAllCollect();

    List<Map<String, Object>> loadcollectQueBySection(@Param("knowId") String knowId, @Param("userId") String userId);

    Map<String, Object> queryWordInfos(@Param("page") int page, @Param("level") String level);
}
