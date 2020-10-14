package com.tdu.develop.user.service.impl;

import com.tdu.develop.common.exception.ServiceException;
import com.tdu.develop.resource.mapper.SubjectTreeMapper;
import com.tdu.develop.user.mapper.AuthMapper;
import com.tdu.develop.user.mapper.ClassMapper;
import com.tdu.develop.user.mapper.SubjectMapper;
import com.tdu.develop.user.mapper.UsersMapper;
import com.tdu.develop.user.pojo.*;
import com.tdu.develop.user.service.SubjectService;
import com.tdu.develop.util.OnlineUtil;
import com.tdu.develop.util.PropertiesUtil;
import com.tdu.develop.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * @author 志阳
 * @create 2019-08-13-11:39
 */
@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    SubjectMapper subjectMapper;
    @Autowired
    UsersMapper usersMapper;
    @Autowired
    AuthMapper authMapper;
    @Autowired
    SubjectTreeMapper subjectTreeMapper;
    @Autowired
    ClassMapper classMapper;

    public List<Subjects> getSubjectIdList2(String id) {

        return subjectMapper.getSubjectIdList2(id);
    }

    public List<Subjects> getSubjectIdList(String id) {

        return subjectMapper.getSubjectIdList(id);
    }

    public List<Subjects> getSubjectIdList_develop(String id) {

        return subjectMapper.getSubjectIdList_develop(id);
    }

    public List<Subjects> getSubjectIdList_resource(String id) {

        return subjectMapper.getSubjectIdList_resource(id);
    }


    public Subjects getSubject(String id) {

        return subjectMapper.getSubject(id);
    }

    @Override
    public List<Subjects> seleSub(Users users) {

        List<Subjects> list = new ArrayList<Subjects>();
        list = subjectMapper.seleSub(users);

        return list;
    }

    /**
     * 查询所有的科目背景图片和logo数据
     *
     * @return
     */
    public List<SubjectBackgrounds> querySubjectInfos() {
        List<SubjectBackgrounds> list = subjectMapper.querySubjectBackgrounds();
        return list;
    }

    /**
     * 保存上传的图片，并将图片名保存到数据库中
     *
     * @param background 背景图片文件
     * @param logo       logo图片文件
     * @param id         sujectbackgrounds主键（注意此主键并非java生成，由数据库自动生成，类型为int）
     * @throws IOException
     */
    public void editSubjectBackground(MultipartFile background, MultipartFile logo, int id) throws IOException {

        byte[] flash = new byte[1024];
        int len = 0;
        int len1 = 0;
        //背景图片名
        String backgroundName = background.getOriginalFilename();
        //logo图片名
        String logoName = background.getOriginalFilename();
        //判断文件是否为空
        if (StringUtils.isNotBlank(backgroundName)) {
            //将文件重命名
            backgroundName = imgRename(backgroundName);
            System.err.println(backgroundName);
            //获取背景存储路径
            String backgroundUrl = PropertiesUtil.getPropertiesInfo("function", "bgUrl");
            System.err.println(backgroundUrl);
            //创建文件输入流
            InputStream iStream = background.getInputStream();
            //创建文件缓冲字节输入流
            BufferedInputStream bufferedInputStream = new BufferedInputStream(iStream);
//			创建文件写入的路径
            File writeFile = new File(backgroundUrl, backgroundName);
            BufferedOutputStream bOutputStream = new BufferedOutputStream(new FileOutputStream(writeFile));
            //写入背景图片
            while (-1 != (len = bufferedInputStream.read(flash))) {
                bOutputStream.write(flash, 0, len);
            }
            bOutputStream.close();
            bufferedInputStream.close();
        }
        if (StringUtils.isNotBlank(logoName)) {
            System.err.println(2);
            logoName = imgRename(logoName);
            String logoUrl = PropertiesUtil.getPropertiesInfo("function", "topImg");
            InputStream iStream2 = logo.getInputStream();
            //读取图片文件
            BufferedInputStream bufferedInputStream2 = new BufferedInputStream(iStream2);
            //写入图片文件
            File wFile = new File(logoUrl, logoName);
            //System.out.println(logoUrl+logoName);
            BufferedOutputStream bOutputStream2 = new BufferedOutputStream(new FileOutputStream(wFile));
            while (-1 != (len1 = bufferedInputStream2.read(flash))) {
                System.err.println("logo");
                bOutputStream2.write(flash, 0, len1);
            }
            bOutputStream2.close();
            bufferedInputStream2.close();
        }
        //将路径写入表中
        SubjectBackgrounds sb = new SubjectBackgrounds();
        sb.setId(id);
        sb.setBackgrouppic(backgroundName);
        sb.setSubjectlogo(logoName);
        subjectMapper.updateSubjectBackgrounds(sb);
    }

    /**
     * 将原图片名换成新的图片名
     * 新图片名为当前时间的毫秒值
     *
     * @param imgname
     * @return
     */
    public String imgRename(String imgname) {
        //获取当前时间的毫秒值
        long time = new Date().getTime();
        //切除原图片名保留后缀
        String suffix = imgname.substring(imgname.lastIndexOf("."));
        //拼接为新的图片名
        String newImgname = time + suffix;
        return newImgname;
    }

    /**
     * 查询后台顶部图片路径,
     *
     * @return
     */
    public String gettopimg() {

        String imgurl = subjectMapper.queryLogo();
        String url = "";
        String sux = "../img/ico/";
        url = sux + imgurl;
        return url;
    }

    /**
     * 查询背景图片
     *
     * @return
     */
    public String loadgroundImg() {

        String imgurl = subjectMapper.queryBackground();
        String url = "";
        String sux = "img/";
        url = sux + imgurl;
        return url;
    }

    @Override
    public void changeSelectedSubject(String subjectId, String userId) {

        subjectMapper.alterstatus(userId);
        int i = subjectMapper.updateStatus(subjectId, userId);
        if (i != 1) throw new ServiceException("切换科目失败");

    }

    /**
     * 移动端，根据用户Id获取科目
     */
    @Override
    public String getSubId(String userId) {

        return subjectMapper.getSubId(userId);
    }

    /**
     * 获取知识点nmae
     *
     * @param knowledgecontentId
     * @return
     */
    public String getKnowNmae(String knowledgecontentId) {
        return subjectMapper.getKnowNmae(knowledgecontentId);
    }

    public String getSubIdOne(String userId) {

        List<String> list = subjectMapper.getSubIdOne(userId);
        String subId = "";
        if (list != null) {
            subId = list.get(0);
        }
        return subId;
    }


    public String getSubIdOne_develop(String userId) {

        List<String> list = subjectMapper.getSubIdOne_develop(userId);
        String subId = "";
        if (list != null) {
            subId = list.get(0);
        }
        return subId;
    }

    /**
     * 获取科目信息
     *
     * @return
     */
    public Map<String, Object> getMajors() {
        //最首层数据
        Map<String, Object> firstMap = new HashMap<String, Object>();
        List<Major> mList = subjectMapper.getAllMajor();
        for (int i = 0; i < mList.size(); i++) {
            List<Subjects> sList = subjectMapper.getSubjects(mList.get(i).Id);
            //中间层数据
            Map<String, Object> midMap = new HashMap<String, Object>();
            //最底层数据
            Map<String, Object> lastMap = new HashMap<String, Object>();
            for (int j = 0; j < sList.size(); j++) {
                //底层每一个数据
                Map<String, Object> everyMap = new HashMap<String, Object>();
                everyMap.put("id", sList.get(j).getId());
                everyMap.put("ischecked", true);
                everyMap.put("pid", sList.get(j).getMajor_Id());
                everyMap.put("text", sList.get(j).getSubjectName());
                lastMap.put(String.valueOf(j), everyMap);
            }
            midMap.put("children", lastMap);
            midMap.put("id", mList.get(i).getId());
            midMap.put("ischecked", true);
            midMap.put("text", mList.get(i).getMajorName());
            firstMap.put(String.valueOf(i), midMap);
        }

        return firstMap;
    }

    /**
     * 获取班级信息（选修）
     *
     * @return
     */
    public List<Classes> getClasses() {
        return subjectMapper.getClasses();
    }

    /**
     * 获取班级学生信息
     *
     * @param classId
     * @return
     */
    public List<OnlineUtil> getClassUsersOnLine(String classId) {
        List<OnlineUtil> userL = new ArrayList<OnlineUtil>();
        List<String> idList = new ArrayList<String>();
        idList = subjectMapper.getUserId(classId);
        for (int i = 0; i < idList.size(); i++) {
            if (subjectMapper.getClassUsers(idList.get(i)) != null) {
                userL.add(subjectMapper.getClassUsersOnLine(idList.get(i)));
            }
        }
        return userL;
    }

    @Override
    public List<ClassUsers> getClassUsersList(String userKey) {
        return classMapper.getClassUsersList(userKey);
    }

    @Override
    public List<String> selClassUsers(String classId) {
        return classMapper.selClassUsers(classId);
    }

    /**
     * 获取班级学生信息
     *
     * @param classId
     * @return
     */
    public List<Users> getClassUsers(String classId) {
        List<Users> userL = new ArrayList<Users>();
        List<String> idList = new ArrayList<String>();
        idList = subjectMapper.getUserId(classId);
        for (int i = 0; i < idList.size(); i++) {
            if (subjectMapper.getClassUsers(idList.get(i)) != null) {
                userL.add(subjectMapper.getClassUsers(idList.get(i)));
            }
        }
        /*for(int i=0;i<userL.size();i++){
            for(int j=i;j<userL.size();j++){
                BigInteger a = new BigInteger(userL.get(i).getUserName());
                BigInteger b = new BigInteger(userL.get(j).getUserName());
                if(a.compareTo(b)==1){
                    Users users=userL.get(i);
                    userL.set(i, userL.get(j));
                    userL.set(j, users);
                }
            }
        }*/
        return userL;
    }

    /**
     * 加载首页菜单栏
     *
     * @param userId
     * @return
     */
    public List<MenuTrees> loadMenus(String userId) {
        //获取科目id
        List<MenuTrees> list = null;
        String subjectId = subjectMapper.getSubjectKey(userId);
        //查询用户的角色
        String roleId = usersMapper.queryUserRoles(userId);
        //查询用户的角色权限
        List<String> listRoles = null;
        if (subjectId == null) {
            String initSubjectId = subjectMapper.seleinitSubject(userId);
            listRoles = authMapper.queryRoleAuths(roleId, subjectId);
            list = getTrees(initSubjectId, listRoles);
        } else {
            listRoles = authMapper.queryRoleAuths(roleId, subjectId);
            list = getTrees(subjectId, listRoles);
        }
        return list;
    }

    /**
     * 获取树形结构的数据
     *
     * @param subjectId
     * @param listRoles
     * @return
     */
    public List<MenuTrees> getTrees(String subjectId, List<String> listRoles) {
        List<MenuTrees> list = new ArrayList<>();
        //查询科目下的大节点科目树
        int count = subjectTreeMapper.querysubjectTrees(subjectId);
        if (count > 0) {
            //获取所有的子节点
            List<MenuTrees> listMenu = subjectTreeMapper.queryAllTrees(subjectId);
            //遍历所有的子节点
            for (MenuTrees menuTrees : listMenu) {
                //判断元素是否还有子节点
//                menuTrees = recursionTree(menuTrees,listRoles);
//                if(menuTrees!=null) {
//                    if(menuTrees.getChildren()!=null) {
//                        menuTrees.setState("open");
//                    }
                list.add(menuTrees);
//                }
            }
        }
        return list;
    }

    /**
     * 递归查询树,与用户角色权限无关
     *
     * @param menuTrees
     * @return
     */
    public MenuTrees recursionTree(MenuTrees menuTrees) {
        //获取子节点数
        int count = subjectTreeMapper.querysubjectTree(menuTrees.getId());
        if (count > 0) {
            menuTrees.setState("closed");
            List<MenuTrees> list = new ArrayList<>();
            //获取第一个子节点
            MenuTrees menuTrees2 = subjectTreeMapper.queryFirstChild(menuTrees.getId());
            //判断是否还有子节点
            MenuTrees mt = this.recursionTree(menuTrees2);
            list.add(mt);
            String upperId = menuTrees2.getId();
            //遍历其他子节点
            for (int i = 1; i < count; i++) {
                //获取其他子节点
                MenuTrees mTrees = subjectTreeMapper.seleotherTree(upperId);
                upperId = mTrees.getId();
                //判断是否还有子节点
                MenuTrees mt1 = this.recursionTree(mTrees);

                list.add(mt1);
            }
            menuTrees.setChildren(list);
        }
        return menuTrees;
    }

    /**
     * 判断是否有子节点，没有则返回原对象，
     * 有则添加属性state值为“closed”
     * 并将子节点封装到children属性中
     *
     * @param listRoles
     */
    public MenuTrees recursionTree(MenuTrees menuTrees, List<String> listRoles) {
        //获取子节点数
        int count = subjectTreeMapper.querysubjectTrees(menuTrees.getId());
        //判断是否有子节点
        if (count > 0) {
            List<MenuTrees> list = new ArrayList<>();
            //查询父节点下所有的子节点数据
            List<MenuTrees> mTrees = subjectTreeMapper.queryAllChildTrees(menuTrees.getId());
            for (MenuTrees mt : mTrees) {
                MenuTrees trees = this.recursionTree(mt, listRoles);
                if (trees != null)
                    list.add(trees);
            }
            if (list != null) {
                menuTrees.setChildren(list);
                menuTrees.setState("closed");
            }
            return menuTrees;
        } else {
            //判断此类型是否有权限加载
            if (!listRoles.contains(menuTrees.getId())) {
                return null;
            }
        }

        return menuTrees;
    }
}
