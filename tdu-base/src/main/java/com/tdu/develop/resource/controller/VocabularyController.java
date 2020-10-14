package com.tdu.develop.resource.controller;

import com.tdu.develop.resource.pojo.Vocabulary;
import com.tdu.develop.resource.service.VocabularyService;
import com.tdu.develop.resource.service.impl.VocabularyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping(value = "VocabularyController")
public class VocabularyController {

    @Autowired
    VocabularyService vocabularyService = new VocabularyServiceImpl();


    /**
     * 得到第二节点信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getVocabularyList.action", method = {RequestMethod.POST})
    @ResponseBody
    public List<Vocabulary> getVocabularyList(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String knowledgeId = request.getParameter("knowledgeId");
        String type = request.getParameter("type");
        knowledgeId = "1";
        type = "词汇";
        List<Vocabulary> vocabularyList = vocabularyService.getVocabularyList(knowledgeId, type);
        return vocabularyList;
    }

}
