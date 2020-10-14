package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.KnowledgesMapper;
import com.tdu.develop.resource.mapper.KnowlegcontentMapper;
import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.Knowlegcontent;
import com.tdu.develop.resource.service.KnowledgesService;
import com.tdu.develop.user.mapper.DepartmentMapper;
import com.tdu.develop.user.mapper.UsersMapper;
import com.tdu.develop.user.pojo.ZNodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 志阳
 * @create 2019-08-15-10:02
 */
@Service
public class KnowledgesServiceImpl implements KnowledgesService {
    private static final String Null = null;
    @Autowired
    KnowlegcontentMapper knowlegcontentMapper;
    @Autowired
    KnowledgesMapper knowledgesMapper;
    @Autowired
    DepartmentMapper departmentMapper;
    @Autowired
    UsersMapper usersMapper;

    @Override
    public List<Knowledges> selectAll(String id) {
        return knowledgesMapper.selectAll(id);
    }

    @Override
    public List<Knowledges> getKnowledgesFis(String subTreeId) {

        return knowledgesMapper.getKnowledgesFis(subTreeId);
    }

    /**
     * 查询科目树下的所有内容
     */
    @Override
    public List<ZNodes> seleknowledges(String id) {


        List<ZNodes> list = new ArrayList<ZNodes>();
        //统计科目树下的大节点数量
        int subLength = knowledgesMapper.seleLength(id);

        //获取第一个大节点
        if (subLength > 1) {
            Knowledges knowledges = new Knowledges();
            ZNodes zNodes = new ZNodes();
            //获取根id
            String rid = knowledgesMapper.seleRoot(id);
            //统计根目录下的子节点
            int subL = knowledgesMapper.seleL(rid);
            //获取根目录下的第一个子节点数据
            knowledges = knowledgesMapper.seleFirst(rid);

            zNodes.setId(knowledges.getId());
            zNodes.setName(knowledges.getContent());
            zNodes.setpId("0");
            zNodes.setKnowledgecontentId(knowledges.getKnowledgecontentId());
            zNodes.setIcon(knowledges.getImageIcons());
            list.add(zNodes);
            //判断该节点下是否还有子节点
            if (knowledgesMapper.seleLengthS(knowledges.getId()) != 0) {
                list.addAll(this.selechild(knowledges.getId()));
            }
            //循环获取所有的大节点
            for (int i = 0; i < subL - 1; i++) {
                knowledges = knowledgesMapper.selesecond(knowledges.getId());
                ZNodes sZNodess = new ZNodes();

                sZNodess.setId(knowledges.getId());
                sZNodess.setName(knowledges.getContent());
                sZNodess.setpId("0");
                sZNodess.setKnowledgecontentId(knowledges.getKnowledgecontentId());
                sZNodess.setIcon(knowledges.getImageIcons());
                list.add(sZNodess);
                if (knowledgesMapper.seleLengthS(knowledges.getId()) != 0) {
                    list.addAll(this.selechild(knowledges.getId()));
                }
            }

            return list;
        }
        return null;
    }

    /**
     * 递归对所有的子节点进行查询
     *
     * @return
     */
    public synchronized List<ZNodes> selechild(String id) {
        //切换数据库

        //创建集合
        List<ZNodes> list = new ArrayList<ZNodes>();
        //统计父节点id是该id的数目
        int subLengthS = knowledgesMapper.seleLengthS(id);

        Knowledges knowledges = new Knowledges();
        ZNodes sZNodes = new ZNodes();

        //获取大节点下面的第一个小节点
        knowledges = knowledgesMapper.selefirstS(id);

        sZNodes.setId(knowledges.getId());
        sZNodes.setName(knowledges.getContent());
        sZNodes.setpId(knowledges.getParentKnowledge());
        sZNodes.setKnowledgecontentId(knowledges.getKnowledgecontentId());
        sZNodes.setIcon(knowledges.getImageIcons());
        list.add(sZNodes);
        if (knowledgesMapper.seleLengthS(knowledges.getId()) != 0) {
            list.addAll(this.selechild(knowledges.getId()));
        }
        //循环获取大节点下面的所有小节点
        for (int i = 0; i < subLengthS - 1; i++) {
            knowledges = knowledgesMapper.selesecond(knowledges.getId());
            ZNodes sZNodess = new ZNodes();
            sZNodess.setId(knowledges.getId());
            sZNodess.setName(knowledges.getContent());
            sZNodess.setpId(knowledges.getParentKnowledge());
            sZNodess.setKnowledgecontentId(knowledges.getKnowledgecontentId());
            sZNodess.setIcon(knowledges.getImageIcons());
            list.add(sZNodess);
            if (knowledgesMapper.seleLengthS(knowledges.getId()) != 0) {
                list.addAll(this.selechild(knowledges.getId()));
            }
        }
        return list;
    }

    /**
     * 新增功能
     */
    @Override
    public Boolean inknow(Knowledges knowledges) {

        knowledgesMapper.inknow(knowledges);
        return true;
    }

    /**
     * 删除功能
     *
     * @return
     */
    public Boolean deTree(String id, String filePath) {

        Knowlegcontent knowlegcontent = new Knowlegcontent();

        //查询自身的上一节点，然后给下一节点更新
        String preKnowledge = knowledgesMapper.slSelf(id);
        //更新下一个节点的对应数据
        knowledgesMapper.upNext(id, preKnowledge);
        //对所有子节点的处理
        List<String> childId = new ArrayList<>();
        childId = this.slChild(id);
        for (String string : childId) {
            //list中的每一个id都去knowlegcontent表中做一次查询
            knowlegcontent = knowlegcontentMapper.seleOne(string);
            if (knowlegcontent != null) {
                if (this.filedel(filePath, knowlegcontent.getNmae())) {
                    knowlegcontentMapper.deSel(string);
                    knowlegcontent = null;
                }
            }
            knowledgesMapper.deTree(string);
        }
        //再把本体去knowlegcontent做一次查询
        knowlegcontent = knowlegcontentMapper.seleOne(id);
        if (knowlegcontent != null) {
            if (this.filedel(filePath, knowlegcontent.getNmae())) {
                knowlegcontentMapper.deSel(id);
            }
        }
        //最后才能删除本体
        knowledgesMapper.deTree(id);

        return true;
    }

    /**
     * 递归查询需要删除的子节点
     *
     * @return
     */
    public List<String> slChild(String id) {

        List<String> childId = new ArrayList<>();
        List<String> childsId = new ArrayList<>();
        //查询主节点下面的子目录
        childId = knowledgesMapper.slChild(id);
        if (childId != null) {
            for (String stringId : childId) {
                //开始递归，查询出所有目录
                childsId.addAll(this.slChild(stringId));
            }
        }
        //查询结束，将所有的数据放到一个list中
        childId.addAll(childsId);
        return childId;
    }

    /**
     * 编辑目录名
     *
     * @param name
     */
    public void upRandom(String id, String name) {

        knowledgesMapper.uprandom(id, name);
    }

    /**
     * Root查询
     *
     * @return
     */
    public String slRoot(String id) {

        String rid = knowledgesMapper.seleRoot(id);
        return rid;
    }

    /**
     * 删除在系统盘符的指定文件
     */
    public Boolean filedel(String filePath, String nmae) {
        StringBuffer stringBuffer = new StringBuffer();
        String end = nmae.substring(nmae.indexOf(".") + 1);
        String wenjianjia;
        if (end.equals("pptx") || end.equals("ppt") || end.equals("docx") || end.equals("doc")) {
            wenjianjia = "word";
        } else if (end.equals("zip") || end.equals("rar")) {
            wenjianjia = "source";
        } else {
            wenjianjia = "video";
        }
        //需要删除文件的绝对路径拼接
        stringBuffer.append(filePath).append(wenjianjia).append(nmae);
        File file = new File(stringBuffer.toString());
        if (file.exists()) {
            //如果文件存在，删除
            file.delete();
        }
        return true;
    }

    public boolean upNext(String id, String pid) {

        if (pid.isEmpty()) {
            knowledgesMapper.upTheNext(id, Null);
        } else {
            knowledgesMapper.upTheNext(id, pid);
        }

        return true;
    }

    public void upknow(String id, String pid, String fatherId) {

        if (pid.isEmpty()) {
            pid = Null;
        }
        if (fatherId.isEmpty()) {
            String fdId = "0675d573-77ac-4a4b-a718-1f07295412a0";
            knowledgesMapper.upknow(id, pid, fdId);
        } else {
            knowledgesMapper.upknow(id, pid, fatherId);
        }

    }

    @Override
    public void updateContent(String knowledgeId, String content) {

        knowledgesMapper.updateContent(knowledgeId, content);
    }

    /**
     * 获取次节点
     */
    @Override
    public List<Knowledges> getKnowledgesSecond(String parentKnowledge, String userId) {
        List<Knowledges> ksList = knowledgesMapper.getKnowledgesSecond(parentKnowledge, userId);
        List<Knowlegcontent> kcList;
        String username;
        for (int i = 0; i < ksList.size(); i++) {
            username = usersMapper.getUserName(userId);
            ksList.get(i).setName(username);
        }
        return ksList;
    }

    /**
     * 获取次节点(新）
     */
    @Override
    public List<Knowlegcontent> getKnowledgesSecond2(String parentKnowledge, String userId) {
        List<Knowledges> ksList = knowledgesMapper.getKnowledgesSecond(parentKnowledge, userId);
        List<Knowlegcontent> kcList = new ArrayList<>();
        String username;
        for (int i = 0; i < ksList.size(); i++) {
            kcList.add(knowledgesMapper.getContent(ksList.get(i).getId()));
        }
        return kcList;
    }

    public Knowlegcontent getContent(String knowId) {
        return knowledgesMapper.getContent(knowId);
    }

    @Override
    public Knowledges getKnow(String knowledgeId) {
        return knowledgesMapper.getKnow(knowledgeId);
    }

    /**
     * 获取知识点类型
     *
     * @param knowledgecontentId
     * @return
     */
    public String getKnowType(String knowledgecontentId) {
        return knowledgesMapper.getKnowType(knowledgecontentId);
    }

    /**
     * 获取知识点nmae
     *
     * @param knowledgecontentId
     * @return
     */
    public String getKnowNmae(String knowledgecontentId) {
        return knowledgesMapper.getKnowNmae(knowledgecontentId);
    }

    /**
     * 通过给的科目id查询出其下的所有东西，最终得到所有的资源
     *
     * @param id
     * @return
     */
    public List<Knowlegcontent> SubjcetTree(String id) {//科目下是理论结构的科目树
        List<Knowlegcontent> list = knowlegcontentMapper.seleAll(id);
        return list;
    }

    //所有子树去一一对应content表，将所有的资源得到
    //给id得到content表中的资源
    public Knowlegcontent seleKContent(String id) {
        Knowlegcontent knowlegcontent = new Knowlegcontent();
        knowlegcontent = knowlegcontentMapper.seleOne(id);
        return knowlegcontent;
    }

    //编辑修改content的名字
    public void upcontent(String name, String id) {
        knowlegcontentMapper.upcontent(name, id);
    }

    public List<Knowledges> getSubjectsTree(String string) {
        // TODO Auto-generated method stub
        return knowledgesMapper.getSubjectsTree(string);
    }

    public String getSubjectName(String Id) {
        String subjectId = knowledgesMapper.getSubjectId(Id);
        String name = departmentMapper.getSubjectName(subjectId);
        return name;
    }
}
