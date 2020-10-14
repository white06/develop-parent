package com.tdu.develop.resource.controller;

import com.tdu.develop.common.exception.JsonResult;
import com.tdu.develop.resource.pojo.Word;
import com.tdu.develop.resource.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author 志阳
 * @create 2019-11-12-11:53
 */
@CrossOrigin
@Controller
@RequestMapping(value = "sectionController")
public class sectionController {
    @Autowired
    public SectionService ss;

    @RequestMapping("queryWordInfos.action")
    @ResponseBody
    public Map<String, Object> queryWordInfos(String page, String level) {
        Map<String, Object> map = ss.queryWordInfos(page, level);
        return map;
    }

    @RequestMapping("loadcollectQueBySection.action")
    @ResponseBody
    public List<Map<String, Object>> loadcollectQueBySection(String knowId, HttpSession session) {
        String userId = (String) session.getAttribute("ID");
        List<Map<String, Object>> list = ss.loadcollectQueBySection(knowId, userId);
        return list;
    }

    @RequestMapping("collectAllQue.action")
    @ResponseBody
    public List<Map<String, Object>> collectAllQue() {
        List<Map<String, Object>> list = ss.collectAllQue();
        return list;
    }

    @RequestMapping("removeQue.action")
    @ResponseBody
    public JsonResult removeQue(String queId) {
        ss.removeQue(queId);
        return new JsonResult();
    }

    @RequestMapping("errorAllQue.action")
    @ResponseBody
    public List<Map<String, Object>> errorAllQue(HttpSession session) {
        String userId = (String) session.getAttribute("ID");
        List<Map<String, Object>> list = ss.errorAllQue(userId);
        return list;
    }

    /**
     * 加载章节错题
     *
     * @param knowId
     * @return
     */
    @RequestMapping("loadErrorQueBySection.action")
    @ResponseBody
    public List<Map<String, Object>> loadErrorQueBySection(String knowId, HttpSession session) {
        String userId = (String) session.getAttribute("ID");
        List<Map<String, Object>> list = ss.loadErrorQueBySection(knowId, userId);
        return list;
    }

    @RequestMapping("loadChildNodes.action")
    @ResponseBody
    public List<Map<String, Object>> loadChildNodes(String id) {
        List<Map<String, Object>> list = ss.loadChildNodes(id);
        return list;
    }

    @RequestMapping("doLoadSections.action")
    @ResponseBody
    public List<Map<String, Object>> doLoadSections(String errorTreeId) {
        List<Map<String, Object>> list = ss.doLoadSections(errorTreeId);
        return list;
    }

    /**
     * 删除单词
     *
     * @return
     */
    @RequestMapping("doDeleteWord.action")
    @ResponseBody
    public JsonResult doDeleteWord(String id) {
        ss.doDeleteWord(id);
        return new JsonResult();
    }

    /**
     * 修改单词信息
     *
     * @param word
     * @return
     */
    @RequestMapping("doUpdateWord.action")
    @ResponseBody
    public JsonResult doUpdateWord(Word word) {
        System.out.println(word.toString());
        ss.doUpdateWord(word);
        return new JsonResult();
    }

    /**
     * 根据科目id加载所有的章节
     *
     * @param subjectId
     * @return
     */
    @RequestMapping("loadSections.action")
    @ResponseBody
    public List<Map<String, Object>> loadSections(String subjectId) {
        List<Map<String, Object>> list = ss.loadSections();
        return list;
    }

    @RequestMapping("doSaveWord.action")
    @ResponseBody
    public JsonResult doSaveWord(Word word) {
        ss.doSaveWord(word);
        System.out.println(word);
        return new JsonResult();
    }

    /**
     * 加载章节树
     *
     * @return
     */
    @RequestMapping("loadSectionTree.action")
    @ResponseBody
    public List<Map<String, Object>> loadSectionTree(String subjectId) {
        List<Map<String, Object>> list = ss.loadSectionsTree(subjectId);
        return list;
    }

    /**
     * 查询章节下的所有单词
     *
     * @param sectionId
     * @return
     */
    @RequestMapping("doLoadWords.action")
    @ResponseBody
    public Map<String, Object> doLoadWords(String sectionId, String page, String rows) {
        System.out.println(page + "," + rows);
        Map<String, Object> map = ss.doLoadWords(sectionId, Integer.parseInt(page), Integer.parseInt(rows));
        return map;
    }
}
