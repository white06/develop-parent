package com.tdu.develop.resource.mapper;

import com.tdu.develop.resource.pojo.StuQueInfors;
import com.tdu.develop.resource.pojo.StutotalScores;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-27-9:33
 */
@Repository
public interface StuQueInforsMapper {
    public void insStuQueInfors(StuQueInfors stuQueInfors);

    public String selStuQueInfors(@Param("quesId") String quesId, @Param("stuId") String stuId, @Param("pageId") String pageId);

    public void upStuQueInfors(StuQueInfors stuQueInfors);

    public Integer seleChecked(@Param("pagerKey") String pagerKey, @Param("stuKey") String stuKey);

    public List<Double> seleQuesScore(@Param("pagerKey") String pagerKey, @Param("stuKey") String stuKey);

    public void insTotalScore(StutotalScores stutotalScores);

    public List<StutotalScores> seleStuScores(String pageId);

    public Integer seleAllScore(@Param("pagerKey") String pagerKey, @Param("stuKey") String stuKey);

    public List<StuQueInfors> seleAnswer(@Param("pagerKey") String pagerKey, @Param("stuKey") String stuKey);

    public int updateStutotalScores(StutotalScores sts);

    public int insertStutotalScores(StutotalScores sts);

    public int updatequescore(StuQueInfors sqif);

    public int insertquescore(StuQueInfors sqif);

    public List<StutotalScores> GetUserExams();

    public List<StutotalScores> GetUserExams2(String Id);

    public Integer seleChecked2(@Param("pagerKey") String pagerKey, @Param("stuKey") String stuKey, @Param("KnowledgeId") String KnowledgeId);

    public void insTotalScore2(StutotalScores stutotalScores);
}
