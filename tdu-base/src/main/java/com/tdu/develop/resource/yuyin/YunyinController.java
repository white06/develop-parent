package com.tdu.develop.resource.yuyin;

import com.tdu.develop.resource.pojo.Interpret;
import com.tdu.develop.resource.pojo.InterpretResult;
import com.tdu.develop.resource.pojo.InterpretScore;
import com.tdu.develop.resource.service.InterpretResultService;
import com.tdu.develop.resource.service.InterpretService;
import com.tdu.develop.resource.service.impl.InterpretResultServiceImpl;
import com.tdu.develop.resource.service.impl.InterpretServiceImpl;
import com.tdu.develop.resource.yuyin.common.ConnUtil;
import com.tdu.develop.resource.yuyin.util.kouyi;
import com.tdu.develop.util.DemoException;
import com.tdu.develop.util.TokenHolder;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 志阳
 * @create 2019-08-27-17:18
 */
@CrossOrigin
@Controller
@RequestMapping(value = "YunyinController")
public class YunyinController {

    @Autowired
    InterpretService interpretService = new InterpretServiceImpl();

    @Autowired
    InterpretResultService itnterpretResultService = new InterpretResultServiceImpl();

    private final boolean METHOD_RAW = false; // 默认以json方式上传音频文件

    //  填写网页上申请的appkey 如 $apiKey="g8eBUMSokVB1BHGmgxxxxxx"
    private final String APP_KEY = "b2r8vqjcKGGZ59G07C2eFGzH";

    // 填写网页上申请的APP SECRET 如 $SECRET_KEY="94dc99566550d87f8fa8ece112xxxxx"
    private final String SECRET_KEY = "iBD0YvTlokK1BeGNHSni2XGygA3Xohte";

    // 需要识别的文件
    private final String FILENAME = "16k.pcm";

    // 文件格式, 支持pcm/wav/amr 格式，极速版额外支持m4a 格式
    private final String FORMAT = FILENAME.substring(FILENAME.length() - 3);


    private String CUID = "1234567JAVA";

    // 采样率固定值
    private final int RATE = 16000;

    private String URL;

    private int DEV_PID;

    //private int LM_ID;//测试自训练平台需要打开此注释

    private String SCOPE;

    //  普通版 参数
    {
        URL = "http://vop.baidu.com/server_api"; // 可以改为https
       /* 1537	普通话(纯中文识别)	输入法模型	有标点	支持自定义词库
        1737	英语		无标点	不支持自定义词库
        1637	粤语		有标点	不支持自定义词库
        1837	四川话		有标点	不支持自定义词库
        1936	普通话远场	远场模型	有标点	不支持*/
        DEV_PID = 1737;
        SCOPE = "audio_voice_assistant_get";
    }

    // 自训练平台 参数
    /*{
        //自训练平台模型上线后，您会看见 第二步：“”获取专属模型参数pid:8001，modelid:1234”，按照这个信息获取 dev_pid=8001，lm_id=1234
        DEV_PID = 8001;
        LM_ID = 1234；
    }*/

    /* 极速版 参数
    {
        URL =   "http://vop.baidu.com/pro_api"; // 可以改为https
        DEV_PID = 80001;
        SCOPE = "brain_enhanced_asr";
    }
    */

    /* 忽略scope检查，非常旧的应用可能没有
    {
        SCOPE = null;
    }
    */
// 发音人选择, 基础音库：0为度小美，1为度小宇，3为度逍遥，4为度丫丫，
    // 精品音库：5为度小娇，103为度米朵，106为度博文，110为度小童，111为度小萌，默认为度小美
    private final int per = 4;
    // 语速，取值0-15，默认为5中语速
    private final int spd = 5;
    // 音调，取值0-15，默认为5中语调
    private final int pit = 5;
    // 音量，取值0-9，默认为5中音量
    private final int vol = 5;

    // 下载的文件格式, 3：mp3(default) 4： pcm-16k 5： pcm-8k 6. wav
    private final int aue = 6;

    public final String url = "http://tsn.baidu.com/text2audio"; // 可以使用https

    private String cuid = "1234567JAVA";

    // 下载的文件格式, 3：mp3(default) 4： pcm-16k 5： pcm-8k 6. wav
    private String getFormat(int aue) {
        String[] formats = {"mp3", "pcm", "pcm", "wav"};
        return formats[aue - 6];
    }

    public static File MultipartFileToFile(MultipartFile multiFile) {
        // 获取文件名
        String fileName = multiFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf("."));
        // 若需要防止生成的临时文件重复,可以在文件名后添加随机码

        try {
            File file = File.createTempFile(fileName, prefix);
            multiFile.transferTo(file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "getInterResultList.action")/**/
    @ResponseBody
    public List<Interpret> getInterResultList(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Interpret> interpretList=null;
        String nandu =  request.getParameter("nandu");
        String page = request.getParameter("page");
        String userKey = request.getParameter("userKey");
        String pageKey = request.getParameter("pageKey");
        interpretList =  interpretService.getInterResultList(nandu,page,userKey,pageKey);
        if(interpretList!=null) {
            return interpretList;
        }
          return null;
    }



    @RequestMapping(value = "getInterpretScore.action")/**/
    @ResponseBody
    public InterpretScore getInterpretScore(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String page =  request.getParameter("page");
        String nandu = request.getParameter("nandu");
        String userKey = request.getParameter("userKey");
        String pageKey = request.getParameter("pageKey");
        InterpretScore interpretScore = itnterpretResultService.getInterpretScore(userKey,nandu,page,pageKey);
        if(interpretScore!=null){
            return interpretScore;
        }
        return null;
    }



    @RequestMapping(value = "addInterpretScore.action")/**/
    @ResponseBody
    public String addInterpretScore(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String page =  request.getParameter("page");
        String nandu = request.getParameter("nandu");
        String userKey = request.getParameter("userKey");
        //陈述流畅度------score1
        //信息忠实度-------score2
        //逻辑连贯度-------score3
        //语言准确度------score4
        String score1 = request.getParameter("score1");
        String score2 = request.getParameter("score2");
        String score3 = request.getParameter("score3");
        String score4 = request.getParameter("score4");
        String zong = request.getParameter("zong");
        String pageKey = request.getParameter("pageKey");
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNowStr = df.format(d);
        InterpretScore interpretScore = new InterpretScore();
        String uuid = UUID.randomUUID().toString();
        interpretScore.setId(uuid);
        interpretScore.setFluency(score1);
        interpretScore.setFidelity(score2);
        interpretScore.setCoherence(score3);
        interpretScore.setAccuracy(score4);
        interpretScore.setZong(zong);
        interpretScore.setCreatData(dateNowStr);
        interpretScore.setUserKey(userKey);
        interpretScore.setNandu(nandu);
        interpretScore.setPage(Integer.parseInt(page));
        interpretScore.setPageKey(pageKey);
        if(userKey!=null){
            itnterpretResultService.addInterpretScore(interpretScore);
        }else
        {
            return"{result: userKey为空}";
        }

        return "{result: 成功}";
    }


    //@CrossOrigin(origins = {"https://tdu.tduvr.club/",})
    @RequestMapping(value = "yuyinNew.action", produces = "application/json; charset=utf-8")/**/
    @ResponseBody
    public String yuyinNew(@RequestParam("file") MultipartFile file, @RequestParam("userKey") String userKey
            , @RequestParam("number") String number, @RequestParam("pageKey") String pageKey
            , @RequestParam("obj") String str, @RequestParam("DEV_PID") String DEV_PID
            , @RequestParam("objStr") String objStr
            , @RequestParam("nandu") String nandu, @RequestParam("page") int page
            , HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception {

        byte[] fileByte = file.getBytes();
        net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(str); //str是字符串
        kouyi dif = (kouyi) net.sf.json.JSONObject.toBean(json, kouyi.class);

        String[] strarr = null;//dif.getText().split("");
        if (DEV_PID.equals("1737")) {
            strarr = dif.getText().split(" ");
        } else if (DEV_PID.equals("1537")) {
            strarr = dif.getText().split("");
        }
        int ms = strarr.length / 2;
// 将MultipartFile file转换成为File
        File f_file = MultipartFileToFile(file);
        //String path = f_file.getCanonicalPath();
        int second = 0;
        Encoder encoder = new Encoder();

        JSONObject jsonObject = null;

        try {
            MultimediaInfo m = encoder.getInfo(f_file);
            long ls = m.getDuration();
            second = (int) ls / 1000;
            System.out.println("此视频时长为:" + ls / 1000 + "秒！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //f_file.delete();
        int score1 = 0;
        if (ms >= second) {
            score1 = 100;
        } else {
            int count = (second - ms) / ms;
            if (count == 1) {
                score1 = 70;
            } else if (count == 2) {
                score1 = 59;
            } else if (count == 3) {
                score1 = 20;
            } else {
                score1 = 10;
            }
        }

        TokenHolder holder = new TokenHolder(APP_KEY, SECRET_KEY, SCOPE);
        holder.resfresh();
        String token = holder.getToken();
        String result = null;
        if (METHOD_RAW) {
            result = runRawPostMethodFor(token, fileByte, Integer.parseInt(DEV_PID));
        } else {
            result = runJsonPostMethodFor(token, fileByte, Integer.parseInt(DEV_PID));
        }

        jsonObject = new JSONObject(result);
        System.out.println("--------------------->>");
        System.out.println("  jsonObject : " + jsonObject);

        String err_msg = jsonObject.get("err_msg").toString();

        int score2 = 0;
        int score3 = 0;
        //  如果  result=-1   该语句中没有该关键词；   result1>result2>result3则为正确关键词顺序；
        int result1 = jsonObject.get("result").toString().indexOf(dif.getCi1());
        int result2 = jsonObject.get("result").toString().indexOf(dif.getCi2());
        int result3 = jsonObject.get("result").toString().indexOf(dif.getCi3());
        int result4 = -1;
        int result5 = -1;
        int result6 = -1;
        int result7 = -1;
        int result8 = -1;
        int result9 = -1;
        if (dif.getCi4() != null) {
            if (dif.getCi9() != null) {
                result4 = jsonObject.get("result").toString().indexOf(dif.getCi4());
                result5 = jsonObject.get("result").toString().indexOf(dif.getCi5());
                result6 = jsonObject.get("result").toString().indexOf(dif.getCi6());
                result7 = jsonObject.get("result").toString().indexOf(dif.getCi7());
                result8 = jsonObject.get("result").toString().indexOf(dif.getCi8());
                result9 = jsonObject.get("result").toString().indexOf(dif.getCi9());
                if (result1 != -1) {
                    score2 = score2 + 10;
                }
                if (result2 != -1) {
                    score2 = score2 + 10;
                }
                if (result3 != -1) {
                    score2 = score2 + 10;
                }
                if (result4 != -1) {
                    score2 = score2 + 10;
                }
                if (result5 != -1) {
                    score2 = score2 + 10;
                }
                if (result6 != -1) {
                    score2 = score2 + 10;
                }
                if (result7 != -1) {
                    score2 = score2 + 10;
                }
                if (result8 != -1) {
                    score2 = score2 + 10;
                }
                if (result9 != -1) {
                    score2 = score2 + 20;
                }
                if (result1 < result2 && result2 <result3&&result3< result4 &&result4 < result5 &&result5 < result6
                        &&result6 < result7 && result7 < result8 && result8 < result9 && result1 != -1
                        && result2 != -1 && result3 != -1 && result4 != -1 && result5 != -1
                        && result6 != -1 && result7 != -1 && result8 != -1 && result9 != -1) {
                    score3 = 100;
                }
            } else if(dif.getCi8() != null&&dif.getCi9() == null) {
                result4 = jsonObject.get("result").toString().indexOf(dif.getCi4());
                result5 = jsonObject.get("result").toString().indexOf(dif.getCi5());
                result6 = jsonObject.get("result").toString().indexOf(dif.getCi6());
                result7 = jsonObject.get("result").toString().indexOf(dif.getCi7());
                result8 = jsonObject.get("result").toString().indexOf(dif.getCi8());
                if (result1 != -1) {
                    score2 = score2 + 20;
                }
                if (result2 != -1) {
                    score2 = score2 + 20;
                }
                if (result3 != -1) {
                    score2 = score2 + 10;
                }
                if (result4 != -1) {
                    score2 = score2 + 10;
                }
                if (result5 != -1) {
                    score2 = score2 + 10;
                }
                if (result6 != -1) {
                    score2 = score2 + 10;
                }
                if (result7 != -1) {
                    score2 = score2 + 10;
                }
                if (result8 != -1) {
                    score2 = score2 + 10;
                }
                if (result1 < result2 && result2 < result3 && result3 < result4 && result4 < result5 && result5 < result6 &&
                        result6 < result7 && result7 < result8  && result1 != -1 && result2 != -1 && result3 != -1 && result4 != -1 && result5 != -1 && result6 != -1 && result7 != -1 && result8 != -1) {
                    score3 = 100;
                }
            } else if(dif.getCi7() != null&&dif.getCi8() == null) {
                result4 = jsonObject.get("result").toString().indexOf(dif.getCi4());
                result5 = jsonObject.get("result").toString().indexOf(dif.getCi5());
                result6 = jsonObject.get("result").toString().indexOf(dif.getCi6());
                result7 = jsonObject.get("result").toString().indexOf(dif.getCi7());
                if (result1 != -1) {
                    score2 = score2 + 20;
                }
                if (result2 != -1) {
                    score2 = score2 + 20;
                }
                if (result3 != -1) {
                    score2 = score2 + 20;
                }
                if (result4 != -1) {
                    score2 = score2 + 10;
                }
                if (result5 != -1) {
                    score2 = score2 + 10;
                }
                if (result6 != -1) {
                    score2 = score2 + 10;
                }
                if (result7 != -1) {
                    score2 = score2 + 10;
                }
                if (result1 < result2 && result2 < result3 && result3 < result4 && result4 < result5 && result5 < result6 &&
                        result6 < result7 && result1 != -1 && result2 != -1 && result3 != -1 && result4 != -1 && result5 != -1 && result6 != -1 && result7 != -1 ) {
                    score3 = 100;
                }
            } else if(dif.getCi6() != null&&dif.getCi7() == null) {
                result4 = jsonObject.get("result").toString().indexOf(dif.getCi4());
                result5 = jsonObject.get("result").toString().indexOf(dif.getCi5());
                result6 = jsonObject.get("result").toString().indexOf(dif.getCi6());
                if (result1 != -1) {
                    score2 = score2 + 20;
                }
                if (result2 != -1) {
                    score2 = score2 + 20;
                }
                if (result3 != -1) {
                    score2 = score2 + 20;
                }
                if (result4 != -1) {
                    score2 = score2 + 20;
                }
                if (result5 != -1) {
                    score2 = score2 + 10;
                }
                if (result6 != -1) {
                    score2 = score2 + 10;
                }
                if (result1 < result2 && result2 < result3 && result3 < result4 && result4 < result5 && result5 < result6
                        && result1 != -1 && result2 != -1 && result3 != -1 && result4 != -1 && result5 != -1 && result6 != -1  ) {
                    score3 = 100;
                }
            }else if(dif.getCi5() != null&&dif.getCi6() == null) {
                result4 = jsonObject.get("result").toString().indexOf(dif.getCi4());
                result5 = jsonObject.get("result").toString().indexOf(dif.getCi5());
                if (result1 != -1) {
                    score2 = score2 + 20;
                }
                if (result2 != -1) {
                    score2 = score2 + 20;
                }
                if (result3 != -1) {
                    score2 = score2 + 20;
                }
                if (result4 != -1) {
                    score2 = score2 + 20;
                }
                if (result5 != -1) {
                    score2 = score2 + 10;
                }
                if (result1 < result2 && result2 < result3 && result3 < result4 && result4 < result5
                        && result1 != -1 && result2 != -1 && result3 != -1 && result4 != -1 && result5 != -1) {
                    score3 = 100;
                }
            }else if(dif.getCi4() != null&&dif.getCi5() == null) {
                result4 = jsonObject.get("result").toString().indexOf(dif.getCi4());
                if (result1 != -1) {
                    score2 = score2 + 20;
                }
                if (result2 != -1) {
                    score2 = score2 + 20;
                }
                if (result3 != -1) {
                    score2 = score2 + 20;
                }
                if (result4 != -1) {
                    score2 = score2 + 20;
                }
                if (result1 < result2 && result2 < result3 && result3 < result4
                        && result1 != -1 && result2 != -1 && result3 != -1 && result4 != -1) {
                    score3 = 100;
                }
            }

        } else {
            if (result1 != -1) {
                score2 = score2 + 33;
            }
            if (result2 != -1) {
                score2 = score2 + 33;
            }
            if (result3 != -1) {
                score2 = score2 + 34;
            }
            if (result1 < result2 && result2 < result3 && result1 != -1 && result2 != -1 && result3 != -1) {
                score3 = 100;
            }
        }
        System.out.println(err_msg);
        System.out.println("--------------------->>");
        System.out.println("  err_msg  :" + err_msg);
        int shu = 0;
        int scroe4 = 0;
        int zong = 0;
        if (err_msg.equals("success.")) {
            String strResult = jsonObject.get("result").toString();
            System.out.println(strResult);
            System.out.println(dif.getText());
            for (int count = 0; count < strarr.length; count++) {
                if (strResult.indexOf(strarr[count]) != -1) {
                    shu = shu + 1;
                }
            }
            scroe4 = (shu * 100 / strarr.length);

            zong = (score1 + score2 + score3 + scroe4) / 4;

            //临时
//            zong=95;
//            score1=95;
//            score2=95;
//            score3=95;
//            scroe4=95;
//            jsonObject.put("result", dif.getText());

            jsonObject.append("zong", zong);
            jsonObject.append("score1", score1);
            jsonObject.append("score2", score2);
            jsonObject.append("score3", score3);
            jsonObject.append("score4", scroe4);
            String[] list = strResult.split("\"");
            System.out.println(list[1]);
            String text = list[1];



            String name = pageKey+"_"+nandu+"_"+page+"_"+number+"_"+userKey+".mp3";

            Interpret interpret = new Interpret();
            InterpretResult interpretResult = new InterpretResult();

            String uuid_result = UUID.randomUUID().toString();

            interpretResult.setId(uuid_result);
            interpretResult.setFluency(Integer.toString(score1));
            interpretResult.setFidelity(Integer.toString(score2));
            interpretResult.setCoherence(Integer.toString(score3));
            interpretResult.setAccuracy(Integer.toString(scroe4));
            Date d = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateNowStr = df.format(d);
            interpretResult.setCreatData(dateNowStr);
            interpretResult.setUserKey(userKey);
            interpretResult.setFileName(name);

            itnterpretResultService.addInterpretResult(interpretResult);



            String uuid = UUID.randomUUID().toString();
            interpret.setId(uuid);
            interpret.setName(name);
            interpret.setUserKey(userKey);
            interpret.setNumber(Integer.parseInt(number));
            interpret.setPageKey(pageKey);
            interpret.setPage(page);
            interpret.setNandu(nandu);
            interpret.setYuanju(dif.getText());
            interpret.setYiju(jsonObject.get("result").toString());
            interpret.setWenti(objStr);
            interpretService.addInterpret(interpret);

            String path = "/www/wwwroot/tdu.tduvr.club/Data/video/"+userKey+"/";
            //String path = "D:\\wamp\\www\\Data\\video\\"+userKey+"\\";

            InputStream is = null;
            //String path = "D:\\wamp\\www\\Data\\pic\\";
            if (!new File(path).exists()) {
                System.out.println(" fileUrlPath :"+path);
                System.out.println(" new File(trueUrl).exists() :"+new File(path).exists());
                boolean mkdirs =new File(path).mkdirs();
                System.out.println("创建目录返回结果："+mkdirs);
                System.out.println("创建文件夹路径为："+ path);
            }
            File writeFile=new File(path+name);
            if (file.equals("") || file.getSize() <= 0) {
                file = null;
            }else{
                //is = file.getInputStream();
                is = new FileInputStream(f_file);
            }
            //File relFile = new File(path+"uu.mp3");
            //InputStream is = file.getInputStream();
            if(!writeFile.exists()){
                //创建指定文件
                writeFile.createNewFile();
                BufferedInputStream bis = new BufferedInputStream(is);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(writeFile));
                byte[] flash = new byte[1024];
                int len = 0;
                while(-1 != (len = bis.read(flash))){
                    bos.write(flash,0,len);
                }
                bos.flush();
                bis.close();
                bos.close();
            }
        } else {
            jsonObject.append("zong", zong);
        }
        System.out.println((score1 + score2 + score3 + scroe4) / 4);
        return jsonObject.toString();
    }


    /*@RequestMapping(value = "yuyin.action", produces = "application/json; charset=utf-8")
    @ResponseBody
    public String yuyin(@RequestParam("file") MultipartFile file, HttpSession session,HttpServletRequest request)throws Exception{
        TokenHolder holder = new TokenHolder(APP_KEY, SECRET_KEY, SCOPE);
        holder.resfresh();
        String token = holder.getToken();
        String result = null;
        if (METHOD_RAW) {
            result = runRawPostMethodFor(token,file.getBytes());
        } else {
            result = runJsonPostMethodFor(token,file.getBytes());
        }
        JSONObject jsonObject = new JSONObject(result);
        System.out.println(jsonObject);

        String err_msg=jsonObject.get("err_msg").toString();
        System.out.println(err_msg);
        if(err_msg.equals("success.")){
            String strResult=jsonObject.get("result").toString();
            System.out.println(strResult);
            String[] list =strResult.split("\"");
            System.out.println(list[1]);
            String text =list[1];

            Interpret interpret = new Interpret();
            String uuid= UUID.randomUUID().toString();
            interpret.setId(uuid);
            interpret.setName(uuid + ".mp3");
            interpret.setUserKey("1");
            interpret.setNumber(1);
            interpret.setPageKey("1");
            interpretService.addInterpret(interpret);

            File relFile = new File(File.separator);
            InputStream is = file.getInputStream();
            File writeFile=new File("C:\\Users\\TDU\\Desktop\\"+uuid + ".mp3");
            if(!writeFile.exists()){
                //创建指定文件
                writeFile.createNewFile();
                BufferedInputStream bis = new BufferedInputStream(is);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(writeFile));
                byte[] flash = new byte[1024];
                int len = 0;
                while(-1 != (len = bis.read(flash))){
                    bos.write(flash,0,len);
                }
                bos.flush();
                bis.close();
                bos.close();
            }

            // 此处2次urlencode， 确保特殊字符被正确编码
            String params = "tex=" + ConnUtil.urlEncode(ConnUtil.urlEncode(text));
            params += "&per=" + per;
            params += "&spd=" + spd;
            params += "&pit=" + pit;
            params += "&vol=" + vol;
            params += "&cuid=" + cuid;
            params += "&tok=" + token;
            params += "&aue=" + aue;
            params += "&lan=zh&ctp=1";
            System.out.println(url + "?" + params); // 反馈请带上此url，浏览器上可以测试
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(5000);
            PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
            printWriter.write(params);
            printWriter.close();
            String contentType = conn.getContentType();
            if (contentType.contains("audio/")) {
                byte[] bytes = ConnUtil.getResponseBytes(conn);
                String format = getFormat(aue);

                File newFile = new File("C:\\Users\\TDU\\Desktop\\"+"result." + format); // 打开mp3文件即可播放
                System.out.println( newFile.getAbsolutePath());
                FileOutputStream os = new FileOutputStream(newFile);
                os.write(bytes);
                os.close();
                System.out.println("audio file write to " + newFile.getAbsolutePath());
            } else {
                System.err.println("ERROR: content-type= " + contentType);
                String res = ConnUtil.getResponseString(conn);
                System.err.println(res);
            }
        }
        File upfile=multipartFileToFile(file);
        FileWriter fo = new FileWriter(upfile);
        fo.write(result);
        fo.close();
        System.out.println("Result also wrote into " + upfile.getAbsolutePath());
        System.out.println(result);
        return result;
    }*/
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public String run() throws IOException, DemoException {
//        TokenHolder holder = new TokenHolder(APP_KEY, SECRET_KEY, SCOPE);
//        holder.resfresh();
//        String token = holder.getToken();
//        String result = null;
//        if (METHOD_RAW) {
//          //  result = runRawPostMethod(token);
//        } else {
//            result = runJsonPostMethod(token);
//        }
//        return result;
//    }

//    private String runRawPostMethod(String token) throws IOException, DemoException {
//        String url2 = URL + "?cuid=" + ConnUtil.urlEncode(CUID) + "&dev_pid=" + DEV_PID + "&token=" + token;
//        //测试自训练平台需要打开以下信息
//        //String url2 = URL + "?cuid=" + ConnUtil.urlEncode(CUID) + "&dev_pid=" + DEV_PID + "&lm_id="+ LM_ID + "&token=" + token;
//        String contentTypeStr = "audio/" + FORMAT + "; rate=" + RATE;
//        //System.out.println(url2);
//        byte[] content = getFileContent(FILENAME);
//        HttpURLConnection conn = (HttpURLConnection) new URL(url2).openConnection();
//        conn.setConnectTimeout(5000);
//        conn.setRequestProperty("Content-Type", contentTypeStr);
//        conn.setRequestMethod("POST");
//        conn.setDoOutput(true);
//        conn.getOutputStream().write(content);
//        conn.getOutputStream().close();
//        System.out.println("url is " + url2);
//        System.out.println("header is  " + "Content-Type :" + contentTypeStr);
//        String result="";
//        try {
//            result = ConnUtil.getResponseString(conn);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return result;
//    }

    private String runRawPostMethodFor(String token, byte[] name, int DEV_PID) throws IOException, DemoException {
        String url2 = URL + "?cuid=" + ConnUtil.urlEncode(CUID) + "&dev_pid=" + DEV_PID + "&token=" + token;
        //测试自训练平台需要打开以下信息
        //String url2 = URL + "?cuid=" + ConnUtil.urlEncode(CUID) + "&dev_pid=" + DEV_PID + "&lm_id="+ LM_ID + "&token=" + token;
        String contentTypeStr = "audio/" + FORMAT + "; rate=" + RATE + ";charset=utf-8";
        //System.out.println(url2);
        byte[] content = name;
        HttpURLConnection conn = (HttpURLConnection) new URL(url2).openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", contentTypeStr);
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.getOutputStream().write(content);
        conn.getOutputStream().close();
        System.out.println("url is " + url2);
        System.out.println("header is  " + "Content-Type :" + contentTypeStr);
        String result = "";
        try {
            result = ConnUtil.getResponseString(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

//    public String runJsonPostMethod(String token) throws DemoException, IOException {
//
//        byte[] content = getFileContent(FILENAME);
//        String speech = base64Encode(content);
//
//        JSONObject params = new JSONObject();
//        params.put("dev_pid", DEV_PID);
//        //params.put("lm_id",LM_ID);//测试自训练平台需要打开注释
//        params.put("format", FORMAT);
//        params.put("rate", RATE);
//        params.put("token", token);
//        params.put("cuid", CUID);
//        params.put("channel", "1");
//        params.put("len", content.length);
//        params.put("speech", speech);
//
//        // System.out.println(params.toString());
//        HttpURLConnection conn = (HttpURLConnection) new URL(URL).openConnection();
//        conn.setConnectTimeout(5000);
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
//        conn.setDoOutput(true);
//        conn.getOutputStream().write(params.toString().getBytes());
//        conn.getOutputStream().close();
//        String result="";
//        try {
//            result = ConnUtil.getResponseString(conn);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//
//        params.put("speech", "base64Encode(getFileContent(FILENAME))");
//        System.out.println("url is : " + URL);
//        System.out.println("params is :" + params.toString());
//
//
//        return result;
//    }


    public String runJsonPostMethodFor(String token, byte[] name, int DEV_PID) throws DemoException, IOException {

        byte[] content = name;
        String speech = base64Encode(content);

        JSONObject params = new JSONObject();

        params.put("dev_pid", DEV_PID);

        //params.put("dev_pid", DEV_PID);

        //params.put("lm_id",LM_ID);//测试自训练平台需要打开注释

        params.put("format", FORMAT);
        params.put("rate", RATE);
        params.put("token", token);
        params.put("cuid", CUID);
        params.put("channel", "1");
        params.put("len", content.length);
        params.put("speech", speech);

        // System.out.println(params.toString());
        System.out.println(1);
        HttpURLConnection conn = (HttpURLConnection) new URL(URL).openConnection();
        System.out.println(12);
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setDoOutput(true);
        conn.getOutputStream().write(params.toString().getBytes());
        conn.getOutputStream().close();
        String result = "";
        System.out.println(123);
        try {
            result = ConnUtil.getResponseString(conn);
            System.out.println(1234);
        } catch (Exception e) {
            e.printStackTrace();
        }


        params.put("speech", "base64Encode(getFileContent(FILENAME))");
        System.out.println("url is : " + URL);
        System.out.println("params is :" + params.toString());


        return result;
    }




  /*  private String getFileContent(String filename) throws DemoException, IOException {
        File file = new File(filename);
        if (!file.canRead()) {
            System.err.println("文件不存在或者不可读: " + file.getAbsolutePath());
            throw new DemoException("file cannot read: " + file.getAbsolutePath());
        }
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            return ConnUtil.getInputStreamContent(is);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }*/

    private String base64Encode(byte[] content) {
        /**
         Base64.Encoder encoder = Base64.getEncoder(); // JDK 1.8  推荐方法
         String str = encoder.encodeToString(content);
         **/

        char[] chars = Base64Util.encode(content); // 1.7 及以下，不推荐，请自行跟换相关库
        String str = new String(chars);

        return str;
    }


}
