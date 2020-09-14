package com.tdu.develop.util;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import com.alibaba.fastjson.JSONObject;

/**
 *
 * @Title:GetMessageCode
 * @Description:发送验证码
 * @author:ヅ零碎de記憶ぷ
 * @Date 2018年7月4日 下午9:27:04
 */
public class GetMessageCode {
    private static final String QUERY_PATH = "https://openapi.miaodiyun.com/distributor/sendSMS";
    private static final String ACCOUNT_SID = "8c63408028d31ba98b702d8cebab2c9a";
    private static final String AUTH_TOKEN = "62c8095ee8ece76e543b60bff61aaea0";

    // 根据相应的手机号发送验证码
    public static String getCode(String phone) {
        //验证码
        String rod = smsCode();
        //时间戳
        String timestamp = getTimestamp();
        //签名：MD5(ACCOUNT SID + AUTH TOKEN + timestamp)。共32位（小写）
        String sig = getMD5(ACCOUNT_SID, AUTH_TOKEN, timestamp);
        //  短信内容  smsContent
        String templateid = "249459";//"【联途旅游】尊敬的用户，您好，您的验证码为：" + rod + "，若非本人操作，请忽略此短信。";
        OutputStreamWriter out = null;
        BufferedReader br = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(QUERY_PATH);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);// 设置是否允许数据写入
            connection.setDoOutput(true);// 设置是否允许参数数据输出
            connection.setConnectTimeout(5000);// 设置链接响应时间
            connection.setReadTimeout(10000);// 设置参数读取时间
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            // 提交请求
            out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            String args = getQueryArgs(ACCOUNT_SID, templateid, phone, timestamp, sig,rod);
            out.write(args);
            out.flush();
            // 读取返回参数

            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String temp = "";
            while ((temp = br.readLine()) != null) {
                result.append(temp);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JSONObject json = JSONObject.parseObject(result.toString());
        String respCode = json.getString("respCode");
        String defaultRespCode = "00000";
        if (defaultRespCode.equals(respCode)) {
            return rod;
        } else {
            return defaultRespCode;
        }
    }

    // 定义一个请求参数拼接方法
    public static String getQueryArgs(String accountSid, String templateid, String to, String timestamp, String sig,
                                      String param) {
        return "accountSid=" + accountSid + "&to=" + to + "&templateid=" + templateid + "&"+"param=" + param
                + "&timestamp=" + timestamp + "&sig=" + sig;
    }

    // 获取时间戳
    public static String getTimestamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    // sing签名
    public static String getMD5(String sid, String token, String timestamp) {

        StringBuilder result = new StringBuilder();
        String source = sid + token + timestamp;
        // 获取某个类的实例
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // 要进行加密的东西
            byte[] bytes = digest.digest(source.getBytes());
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xff);
                if (hex.length() == 1) {
                    result.append("0" + hex);
                } else {
                    result.append(hex);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result.toString();
    }

    // 创建验证码
    public static String smsCode() {
        String random = (int) ((Math.random() * 9 + 1) * 100000) + "";
        return random;
    }
}