package com.tdu.develop.resource.service.impl;

import com.tdu.develop.resource.mapper.NavigationBarMapper;
import com.tdu.develop.resource.mapper.SubjectTreeMapper;
import com.tdu.develop.resource.pojo.Knowledges;
import com.tdu.develop.resource.pojo.NavigationBar;
import com.tdu.develop.resource.pojo.Navigation_other;
import com.tdu.develop.resource.pojo.SubjectTrees;
import com.tdu.develop.resource.service.NavigationBarService;
import com.tdu.develop.user.pojo.ZNodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author 志阳
 * @create 2019-08-27-17:51
 */
@Service
public class NavigationBarServiceImpl implements NavigationBarService {
    private final static String Null=null;

    @Autowired
    NavigationBarMapper navigationBarMapper;
    @Autowired
    SubjectTreeMapper std;
    /**
     * imageicom和icon的图片,节点边上的图标
     */
    public static final String image="../../../Source/imgicon/tag_orange.png";
    //查询并返回所有的导航栏信息
    public List<NavigationBar> seleNavigation(){
         
        List<NavigationBar> list=new ArrayList<>();
        list=navigationBarMapper.seleNavigation();
        return list;
    }

    public boolean upNavigation(SubjectTrees subjectTrees){
         
        navigationBarMapper.upSubjecttree(subjectTrees);
        return true;
    }

    /**
     * 新增导航栏的功能，因为有级别和排序，所以采用了比较慢的多次查询，查询出新增的这个数据的上一级这个属性的值
     * @param navigationBar
     * @return
     */
    public boolean inNavigation(NavigationBar navigationBar){
         
        if (navigationBar != null) {
            String pid=navigationBar.getColumnPid();
            int pidL=navigationBarMapper.seleRootL(pid);
            if (pidL>0) {
                String upperId=navigationBarMapper.seleUpper(pid).getId();
                for (int i = 1; i < pidL; i++) {
                    upperId=navigationBarMapper.selePid(pid, upperId).getId();
                }
                navigationBar.setColumnUpper(upperId);
                navigationBarMapper.inNavigation(navigationBar);
            } else {
                navigationBarMapper.inNavigation(navigationBar);
            }
            return true;
        }
        return false;
    }

    /**
     * 使用递归的方法，让导航栏有序地被查出，然后有序排列，方便前端进行使用
     * @return
     */
    //递归父部分
    public List<NavigationBar> selePid(){
         
        List<NavigationBar> listLevel1=new ArrayList<>();
        String RootId=navigationBarMapper.seleRoot();

        int rootIdL=navigationBarMapper.seleRootL(RootId).intValue();
        if (rootIdL>0) {

            NavigationBar navigationBar=navigationBarMapper.seleUpper(RootId);
            listLevel1.add(navigationBar);

            int pidL=navigationBarMapper.seleRootL(navigationBar.getId()).intValue();
            if (pidL>0) {
                listLevel1.addAll(this.seleLevel2(navigationBar.getId(),pidL));
            }

            for(int i=1;i<rootIdL;i++){
                NavigationBar navigationBar2=new NavigationBar();
                navigationBar2=navigationBarMapper.selePid(RootId, navigationBar.getId());
                listLevel1.add(navigationBar2);
                int pidL2=navigationBarMapper.seleRootL(navigationBar2.getId()).intValue();
                if (pidL2>0) {
                    listLevel1.addAll(this.seleLevel2(navigationBar2.getId(), pidL2));
                }
                navigationBar=navigationBar2;
            }
        }
        return listLevel1;
    }
    //递归子部分
    public List<NavigationBar> seleLevel2(String rootId,int pidL){
        List<NavigationBar> list=new ArrayList<>();
        NavigationBar navigationBar=new NavigationBar();
        navigationBar=navigationBarMapper.seleUpper(rootId);
        list.add(navigationBar);
        for(int i=1;i<pidL;i++){
            NavigationBar navigationBar2=new NavigationBar();
            navigationBar2=navigationBarMapper.selePid(rootId, navigationBar.getId());
            list.add(navigationBar2);
            navigationBar=navigationBar2;
        }
        return list;
    }

    /**
     * 使用递归的方法，让导航栏有序地被查出，然后有序排列，方便前端进行使用
     * @param subjectId
     * @return
     */
    //递归父部分
    public List<ZNodes> selePid_other(String subjectId){
         
        List<SubjectTrees> listLevel1=new ArrayList<>();
        List<ZNodes> listZnodes = new ArrayList<>();
        //查询科目下的科目树
        List<SubjectTrees> list = navigationBarMapper.getSubjecttrees(subjectId);
        //System.out.println("133:"+list);
        if(list.size()>0) {
            //查询第一个大节点
            SubjectTrees subjectTrees = navigationBarMapper.getSubjecttree(subjectId);
            if(subjectTrees!=null)
                listLevel1.add(subjectTrees);
            //判断大节点下是否还有子节点
            String upperId = subjectTrees.getId();
            List<SubjectTrees> list2 = getChildTrees(upperId);
            if(list2!=null)
                listLevel1.addAll(list2);
            //遍历查询剩余大节点
            for(int i=1;i<list.size();i++) {
                SubjectTrees tree = navigationBarMapper.getSubjecttreeOther(upperId);
                upperId=tree.getId();
                listLevel1.add(tree);
                //判断大节点下是否还有子节点
                List<SubjectTrees> list3 = getChildTrees(upperId);
                if(list3!=null)
                    listLevel1.addAll(list3);
            }
        }
        for(SubjectTrees sTrees:listLevel1) {
            ZNodes zNodes = new ZNodes();
            zNodes.setId(sTrees.getId());
            zNodes.setIcon(sTrees.getIcon());
            zNodes.setName(sTrees.getTreeName());
            zNodes.setpId((sTrees.getColumnPid()==null?"0":sTrees.getColumnPid()));
            zNodes.setColumnLink(sTrees.getColumnLink());
            listZnodes.add(zNodes);
        }
		/*//查询根id
		String RootId=navigationBarMapper.seleRootBysubject(subjectId);
		//统计根节点下的子节点数
		int rootIdL=navigationBarMapper.seleRootL(RootId).intValue();
		if (rootIdL>0) {
			//查询根节点下的第一个子节点
			NavigationBar navigationBar=navigationBarMapper.seleUpper(RootId);
			Navigation_other navigation_other=this.getOther(navigationBar);
			navigation_other.setpId("0");
			listLevel1.add(navigation_other);
			//查询第一个子节点下的子节点数
			int pidL=navigationBarMapper.seleRootL(navigationBar.getId()).intValue();
			if (pidL>0) {
				listLevel1.addAll(this.seleLevel2_other(navigationBar.getId(),pidL));
			}
			
			for(int i=1;i<rootIdL;i++){
				NavigationBar navigationBar2=new NavigationBar();
				//查询其他子节点的数据
				navigationBar2=navigationBarMapper.selePid(RootId, navigationBar.getId());
				Navigation_other navigation_other2=this.getOther(navigationBar2);
				navigation_other2.setpId("0");
				listLevel1.add(navigation_other2);
				//查询其他子节点的子节点数
				int pidL2=navigationBarMapper.seleRootL(navigationBar2.getId()).intValue();
				//判断该子节点下面是否还有其他子节点
				if (pidL2>0) {
					listLevel1.addAll(this.seleLevel2_other(navigationBar2.getId(), pidL2));
				}
				//navigationBar=navigationBar2;
			}
		}*/
        return listZnodes;
    }
    /**
     * 获取子树
     * @return
     */
    public List<SubjectTrees> getChildTrees(String treeId){
        List<SubjectTrees> list = new ArrayList<>();
        //获取子节点的个数
        int count = navigationBarMapper.getSubjecttreeCount(treeId);
        if(count>0) {
            //获取第一个大节点
            SubjectTrees subjectTrees = navigationBarMapper.selfirstChild(treeId);
            list.add(subjectTrees);
            //判断下面是否还有子树
            List<SubjectTrees> list2 = this.getChildTrees(subjectTrees.getId());
            if(list2!=null)
                list.addAll(list2);
            String upperId = subjectTrees.getId();
            for(int i=1;i<count;i++) {
                SubjectTrees subjectTrees2 = navigationBarMapper.selOtherChild(upperId);
                list.add(subjectTrees2);
                upperId=subjectTrees2.getId();
                List<SubjectTrees> list3 = this.getChildTrees(upperId);
                if(list3!=null)
                    list.addAll(list3);
            }
        }
        return list;
    }
    public List<ZNodes> seleknowledges(String treeid) {
        if(treeid==null)
            return null;
         
        ZNodes zNodes = new ZNodes();
        Knowledges knowledges = new Knowledges();
        List<ZNodes> list = new ArrayList<ZNodes>();
        String rootId = getSubjectRootId(treeid);
        if(null==rootId) {
            return null;
        }
        //统计资源树下大节点数
        int sub = std.seleNum(treeid, rootId);
        if(sub!=0){
            //获取第一个大节点的数据
            knowledges = std.seleFirst(treeid, rootId);
            zNodes.setId(knowledges.getId());
            zNodes.setKnowledgecontentId(knowledges.getKnowledgecontentId());
            zNodes.setName(knowledges.getContent());
            zNodes.setpId(treeid);
            zNodes.setIcon(image);
            zNodes.setImageicon(image);
            list.add(zNodes);
            String preId = knowledges.getId();
            if(std.childNum(preId)!=0){
                list.addAll(this.seleChild(preId));
            }
            //获取其他大节点的数据
            for(int i=1;i<sub;i++){
                ZNodes zNodes2 = new ZNodes();
                Knowledges knowledges2 = std.seleOther(preId, treeid, rootId);
                zNodes2.setId(knowledges2.getId());
                zNodes2.setKnowledgecontentId(knowledges2.getKnowledgecontentId());
                zNodes2.setName(knowledges2.getContent());
                zNodes2.setpId(treeid);
                zNodes2.setImageicon(knowledges2.getImageIcons());
                zNodes2.setIcon(knowledges2.getImageIcons());
                list.add(zNodes2);
                preId = knowledges2.getId();
                if(std.childNum(preId)!=0){
                    list.addAll(this.seleChild(preId));
                }
            }
        }
        return list;
    }
    public synchronized List<ZNodes> seleChild(String id){
         
        List<ZNodes> list = new ArrayList<ZNodes>();
        Knowledges ck = new Knowledges();
        ZNodes zn = new ZNodes();
        //统计大节点下子节点数
        int cnum = std.childNum(id);
        if(cnum!=0){
            //获取大节点下第一个子节点
            ck = std.childFirst(id);
            zn.setId(ck.getId());
            zn.setKnowledgecontentId(ck.getKnowledgecontentId());
            zn.setName(ck.getContent());
            zn.setpId(ck.getParentKnowledge());
            zn.setIcon(ck.getImageIcons());
            zn.setImageicon(ck.getImageIcons());
            list.add(zn);
            if(std.childNum(ck.getId())!=0){
                list.addAll(this.seleChild(ck.getId()));
            }
            //获取第一个子节点的id
            String preId = ck.getId();
            for(int i=1;i<cnum;i++){
                ZNodes zn1 = new ZNodes();
                Knowledges cko = std.childOther(preId, id);
                zn1.setId(cko.getId());
                zn1.setKnowledgecontentId(cko.getKnowledgecontentId());
                zn1.setName(cko.getContent());
                zn1.setpId(cko.getParentKnowledge());
                zn1.setIcon(cko.getImageIcons());
                zn1.setImageicon(cko.getImageIcons());
                list.add(zn1);
                preId = cko.getId();
                if(std.childNum(preId)!=0){
                    list.addAll(this.seleChild(cko.getId()));
                }
            }
        }
        return list;
    }
    public String getSubjectRootId(String subjectTree_Id) {
         
        //System.out.println("科目树id："+subjectTree_Id);
        String string = std.getSubjectRootId(subjectTree_Id);
        return string;
    }
    //递归子部分
    /**
     *
     * @param rootId	节点id
     * @param pidL 该节点下的子节点数
     * @return
     */
    public List<Navigation_other> seleLevel2_other(String rootId, int pidL){
        List<Navigation_other> list=new ArrayList<>();
        NavigationBar navigationBar=new NavigationBar();
        //查询该节点id下的第一个子节点数据
        navigationBar=navigationBarMapper.seleUpper(rootId);
        Navigation_other navigation_other=new Navigation_other();
        navigation_other=this.getOther(navigationBar);
        list.add(navigation_other);
        //查询该节点下的子节点数
        int childCount = navigationBarMapper.seleChildNodes(navigation_other.getId());
        //判断该子节点下是否还有子节点
        if(childCount>0) {
            list.addAll(this.seleLevel2_other(navigation_other.getId(), childCount));
        }
        for(int i=1;i<pidL;i++){
            NavigationBar navigationBar2=new NavigationBar();
            Navigation_other navigation_other2=new Navigation_other();
            //查询该节点id下的其他子节点id
            navigationBar2=navigationBarMapper.selePid(rootId, navigationBar.getId());
            navigation_other2=this.getOther(navigationBar2);
            list.add(navigation_other2);
            int childNum = navigationBarMapper.seleChildNodes(navigation_other2.getId());
            if(childNum>0) {
                list.addAll(this.seleLevel2_other(navigation_other2.getId(), childNum));
            }
            //navigationBar=navigationBar2;

        }
        return list;
    }
    /**
     * 将navigation对象转换成navigation_other对象
     * @param navigationBar
     * @return
     */
    public Navigation_other getOther(NavigationBar navigationBar){
        Navigation_other navigation_other=new Navigation_other();
        navigation_other.setId(navigationBar.getId());
        navigation_other.setColumnLevel(navigationBar.getColumnLevel());
        navigation_other.setColumnLink(navigationBar.getColumnLink());
        navigation_other.setColumnPicture(navigationBar.getColumnPicture());
        navigation_other.setColumnUpper(navigationBar.getColumnUpper());
        navigation_other.setName(navigationBar.getColumnName());
        navigation_other.setpId(navigationBar.getColumnPid());
        navigation_other.setUserrole(navigationBar.getUserrole());
        navigation_other.setColumnPid(navigationBar.getColumnPid());
        return navigation_other;
    }

    //查询所有的父级(level==1)的导航栏
    public List<NavigationBar> seleLevel1(){
         
        List<NavigationBar> listLevel1=new ArrayList<>();
        listLevel1=navigationBarMapper.seleLevel1();
        return listLevel1;
    }

    //删除功能
    public boolean delNav(String id){
         
        //查询该id的子节点数
        int count = navigationBarMapper.seleChildNodes(id);
        if(count>0) {
            //删除所有的子节点
            deleteTrees(id);
        }
        //获取该id的上节点id
        String onId = navigationBarMapper.selUpId(id);
        navigationBarMapper.delSubjectTree(id);
        //删除knowledges表
        navigationBarMapper.delKnowledges(id);
        //查询下一节点
        SubjectTrees latterTree = navigationBarMapper.selOtherChild(id);
        if(latterTree!=null) {
            //修改下节点的上节点id
            navigationBarMapper.upsubjecttreeOfUpper(latterTree.getId(),onId);
        }
        return true;
    }
    /**
     * 递归删除树
     * @param parentId
     */
    public void deleteTrees(String parentId) {
        //查询子节点数
        int count = navigationBarMapper.seleChildNodes(parentId);
        if(count<=0)
            return;
        //查询所有的子节点id
        List<String> treesId = navigationBarMapper.seleAllChilds(parentId);
        for(String treeId :treesId) {
            //删除子节点数据
            navigationBarMapper.delSubjectTree(treeId);
            //删除knowledges表
            navigationBarMapper.delKnowledges(treeId);
            //判断是否还有子节点
            this.deleteTrees(treeId);
        }
    }

    public String seleRoot(String subjectId){
         
        return navigationBarMapper.seleRootBysubject(subjectId);
    }

    //添加
    public boolean inNavigation2(NavigationBar navigationBar){
         
        navigationBarMapper.inNavigation(navigationBar);
        return true;
    }

    //拖动修改
    public boolean upNext(String id,String pid){
         
        if (pid.isEmpty()) {
            navigationBarMapper.upUpper(Null,id);
        } else {
            navigationBarMapper.upUpper(pid,id);
        }
        return true;
    }

    //修改一部分数据
    public void upsome(String id,String upper,String pid){
         
        if (upper==null||upper.isEmpty()) {
            navigationBarMapper.upsmoe(Null, pid, id);
        } else {
            navigationBarMapper.upsmoe(upper, pid, id);
        }
    }

    /**************************************************************************************************************/


    /**
     * 新建按钮功能
     * @param subjectId
     * @return
     */
    public ZNodes insTrees(String subjectId) {
         
        SubjectTrees sTrees = new SubjectTrees();
        sTrees.setId(UUID.randomUUID().toString());
        sTrees.setTreeName("新建目录");
        sTrees.setSubjectKey(subjectId);
        //获取最后节点的id
        String lastTreeId = navigationBarMapper.seleLastTreeId(subjectId);
        sTrees.setColumnUpper(lastTreeId);
        sTrees.setStatus("1");
        sTrees.setTreeNum(0);
        navigationBarMapper.insSubjecttrees(sTrees);
        //给knowledge表加root
        Knowledges knowledges = new Knowledges();
        knowledges.setId(UUID.randomUUID().toString());
        knowledges.setContent("Root");
        knowledges.setImageIcons("../../../Source/imgicon/tag_orange.png");
        knowledges.setKnowledgecontentId("00000000-0000-0000-0000-000000000000");
        knowledges.setSubjectTree_Id(sTrees.getId());
        knowledges.setBeforCondition("<root><beforesee></beforesee><userkey></userkey><grades></grades></root>");
        navigationBarMapper.insertKnowledges(knowledges);
        ZNodes zNodes = new ZNodes();
        zNodes.setId(sTrees.getId());
        zNodes.setName(sTrees.getTreeName());
        zNodes.setIcon(null);
        zNodes.setpId(sTrees.getColumnPid());
        return zNodes ;
    }
    /**
     *
     * @param subjectId
     * @param treeId
     * @return
     */
    public ZNodes insertTree(String subjectId, String treeId) {
         
        SubjectTrees subjectTrees = new SubjectTrees();
        subjectTrees.setId(UUID.randomUUID().toString());
        subjectTrees.setTreeName("新建文件");
        subjectTrees.setSubjectKey(subjectId);
        subjectTrees.setTreeNum(0);
        subjectTrees.setStyle("1");
        subjectTrees.setStatus("1");
        subjectTrees.setColumnPid(treeId);
        //查询最后的upper
        String id = navigationBarMapper.querytreeId(treeId);
        if(id!=null)
            subjectTrees.setColumnUpper(id);
        navigationBarMapper.insertTrees(subjectTrees);
        ZNodes zNodes = new ZNodes();
        zNodes.setId(subjectTrees.getId());
        zNodes.setName(subjectTrees.getTreeName());
        zNodes.setIcon(subjectTrees.getIcon());
        zNodes.setpId(subjectTrees.getColumnPid());
        return zNodes;
    }
}
