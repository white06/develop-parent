package com.tdu.develop.resource.service.impl;

import com.tdu.develop.models.mapper.DevelopSceneMapper;
import com.tdu.develop.resource.mapper.LinkMapper;
import com.tdu.develop.resource.mapper.LinshiMapper;
import com.tdu.develop.resource.pojo.Link;
import com.tdu.develop.resource.pojo.Linshi;
import com.tdu.develop.resource.service.LinkService;
import com.tdu.develop.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 志阳
 * @create 2020-06-10-14:41
 */
@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkMapper linkMapper;
    @Autowired
    private LinshiMapper linshiMapper;

    @Autowired
    private DevelopSceneMapper developSceneMapper;

    /**
     * 转化url
     * 查询数据库是否已经存在 如果存在就返回数据库对应的短链接，如果不存在就查询一条新纪录并返回新的短链接
     *
     * @param link
     * @return
     */
    @Override
    public Map<String, String> save(Link link, String jiami, String yanzheng, String leixing, String knowIdmi) throws Exception {
        String Id = UUID.randomUUID().toString();
        link.setId(Id);
        String shortUrl = "https://www.tduvr.club/vr";
        String longUrl = link.getLongUrl();
        String name = developSceneMapper.getSceneName(knowIdmi);
        System.out.println(longUrl);
        //      Link link1 = linkMapper.findByLongUrl(longUrl);
        //获取当前日期加一个月
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d = new Date();
        String dateNowStr = sdf.format(d);
        Date sd1 = sdf.parse(dateNowStr);
        link.setSharetime(sd1);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(sd1);
        rightNow.add(Calendar.MONTH, 1);
        Date sd2 = rightNow.getTime();
        link.setTime(sd2);

        int count = 0;
        link.setSharecount(count);
        link.setName(name);
        String mima = this.generateWord();
        if (jiami.equals("true")) {


            link.setJiami(mima);
        }
        if (yanzheng.equals("true")) {
            link.setYanzheng("1");
        }

        //    if(link1 == null) {
        shortUrl += this.gererateShortUrl(longUrl + dateNowStr);
        link.setShortUrl(shortUrl);
        linkMapper.insert(link);
        //    }else{
        //          shortUrl = link1.getShortUrl();
        //      }

        Map<String, String> map = new HashMap<String, String>();
        map.put("url", shortUrl);
        map.put("mima", mima);
        return map;
    }

    @Override
    public String restoreUrl(String url) {
        return linkMapper.findByShortUrl(url);
    }

    private String generateWord() {
        String[] beforeShuffle = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i",
                "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(5, 9);
        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        String sLongUrl = "http://474515923.qzone.qq.com"; //长链接
        LinkServiceImpl link = new LinkServiceImpl();
        String result = link.gererateShortUrl(sLongUrl);
        // 打印出结果
        System.out.println("短链接为： " + result);
    }

    /**
     * 将长链接转换为短链接
     *
     * @param url
     * @return
     */
    public String gererateShortUrl(String url) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "caron";
        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"

        };
        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = MD5Util.MD5(key + url);
        String hex = sMD5EncryptResult;

//        String[] resUrl = new String[4];
//        for ( int i = 0; i < 4; i++) {

        // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
        String sTempSubString = hex.substring(2 * 8, 2 * 8 + 8);    //固定取第三组

        // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
        long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
        String outChars = "";
        for (int j = 0; j < 6; j++) {
            // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
            long index = 0x0000003D & lHexLong;
            // 把取得的字符相加
            outChars += chars[(int) index];
            // 每次循环按位右移 5 位
            lHexLong = lHexLong >> 5;
        }
        // 把字符串存入对应索引的输出数组
//            resUrl[i] = outChars;
//        }
        return outChars;
    }

    public Link getLink(String url) {
        return linkMapper.findLink(url);
    }

    public List<Link> getuserLink(String userId) {
        return linkMapper.getlink(userId);
    }

    public void ins(Linshi linshi) {
        linshiMapper.insert(linshi);
    }

    public Linshi get(String userId) {
        return linshiMapper.get(userId);
    }
}
