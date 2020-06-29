//package com.tdu.develop.user.controller;
//
//import com.baidu.aip.util.Base64Util;
//import com.tdu.develop.util.ConnUtil;
//import com.tdu.develop.util.DemoException;
//import com.tdu.develop.util.TokenHolder;
//import org.json.JSONObject;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//@CrossOrigin
//@Controller
//@RequestMapping(value="AsrMain")
//public class AsrMain {
//
//    private final boolean METHOD_RAW = false; // 默认以json方式上传音频文件
//
//    //  填写网页上申请的appkey 如 $apiKey="g8eBUMSokVB1BHGmgxxxxxx"
//    private final String APP_KEY = "AWbnYAFxHcUXGllGmAiYHU76";
//
//    // 填写网页上申请的APP SECRET 如 $SECRET_KEY="94dc99566550d87f8fa8ece112xxxxx"
//    private final String SECRET_KEY = "RFjbFDq8CrpCOOOhVk6CiUdIUyQtFPAK";
//
//    // 需要识别的文件
//    private final String FILENAME = "16k.pcm";
//
//    // 文件格式, 支持pcm/wav/amr 格式，极速版额外支持m4a 格式
//    private final String FORMAT = FILENAME.substring(FILENAME.length() - 3);
//
//
//    private String CUID = "1234567JAVA";
//
//    // 采样率固定值
//    private final int RATE = 16000;
//
//    private String URL;
//
//    private int DEV_PID;
//
//    //private int LM_ID;//测试自训练平台需要打开此注释
//
//    private String SCOPE;
//
//    //  普通版 参数
//    {
//        URL = "http://vop.baidu.com/server_api"; // 可以改为https
//        //  1537 表示识别普通话，使用输入法模型。 其它语种参见文档
//        DEV_PID = 1537;
//        SCOPE = "audio_voice_assistant_get";
//    }
//
//@RequestMapping("getApi")
//public String run() throws IOException, DemoException {
//    TokenHolder holder = new TokenHolder(APP_KEY, SECRET_KEY, SCOPE);
//    holder.resfresh();
//    String token = holder.getToken();
//    String result = null;
//    if (METHOD_RAW) {
//        result = runRawPostMethod(token);
//    } else {
//        result = runJsonPostMethod(token);
//    }
//    return result;
//
//}
//    private String base64Encode(byte[] content) {
//        /**
//         Base64.Encoder encoder = Base64.getEncoder(); // JDK 1.8  推荐方法
//         String str = encoder.encodeToString(content);
//         **/
//
//        String chars = Base64Util.encode(content); // 1.7 及以下，不推荐，请自行跟换相关库
//        String str = new String(chars);
//
//        return str;
//    }
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
//        String result = ConnUtil.getResponseString(conn);
//
//
//        params.put("speech", "base64Encode(getFileContent(FILENAME))");
//        System.out.println("url is : " + URL);
//        System.out.println("params is :" + params.toString());
//
//
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
//        String result = ConnUtil.getResponseString(conn);
//        return result;
//    }
//    private byte[] getFileContent(String filename) throws DemoException, IOException {
//        File file = new File(filename);
//        if (!file.canRead()) {
//            System.err.println("文件不存在或者不可读: " + file.getAbsolutePath());
//            throw new DemoException("file cannot read: " + file.getAbsolutePath());
//        }
//        FileInputStream is = null;
//        try {
//            is = new FileInputStream(file);
//            return ConnUtil.getInputStreamContent(is);
//        } finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
//}
