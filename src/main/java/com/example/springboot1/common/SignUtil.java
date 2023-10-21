package com.example.springboot1.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

/**
 * @datetime 2022-11-02 上午11:00
 * @desc 接口校验工具类
 *  生成有序map,签名，验签
 *  通过appId、timestamp、appSecret做签名
 * @menu
 */
@Slf4j
public class SignUtil {

    /**
     * 生成签名sign
     * 加密前：appId=wx123456789&timestamp=1583332804914&key=7214fefff0cf47d7950cb2fc3b5d670a
     * 加密后：E2B30D3A5DA59959FA98236944A7D9CA
     */
    public static String createSign(SortedMap<String, String> params, String key){
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> es =  params.entrySet();
        Iterator<Map.Entry<String,String>> it =  es.iterator();
        //生成
        while (it.hasNext()){
            Map.Entry<String,String> entry = it.next();
            String k = entry.getKey();
            String v = entry.getValue();
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)){
                sb.append(k+"="+v+"&");
            }
        }
        sb.append("appSecret=").append(key);
        System.out.println(sb.toString());
        String sign = MD532(sb.toString()).toUpperCase();
        return sign;
    }

    /**
     * 校验签名
     */
    public static Boolean isCorrectSign(SortedMap<String, String> params, String key){
        String sign = createSign(params,key);
        String requestSign = params.get("sign").toUpperCase();
        log.info("通过用户发送数据获取新签名：{}", sign);
        return requestSign.equals(sign);
    }

    /**
     * md5常用工具类
     */
    public static String MD5(String data){
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte [] array = md5.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString().toUpperCase();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String MD532(String data){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] inputBytes = data.getBytes("UTF-8");
            byte[] hashBytes = md.digest(inputBytes);

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成uuid
     */
    public static String generateUUID(){
        String uuid = UUID.randomUUID().toString().replaceAll("-","").substring(0,32);
        return uuid;
    }

    public static void main(String[] args) {
        //第一步：生成uuid，用作appSecret
//        System.out.println(SignUtil.generateUUID());

        //第二步：用户端发起请求，生成签名后发送请求
        String appSecret = "7214fefff0cf47d7950cb2fc3b5d670a";
        String appId = "wx123456789";
        String timestamp = "1583332804914";
        //生成签名
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("appId", appId);
        sortedMap.put("timestamp", timestamp);
        System.out.println("签名："+SignUtil.createSign(sortedMap, appSecret));

        //第三步：校验签名
        //1.校验时间戳
        long requestTime = Long.valueOf(timestamp);
        // 时间查过20秒，则认为接口为重复调用，返回错误信息
        long nowTime = new Date().getTime();
        int seconds = (int) ((nowTime - requestTime)/1000);
        if(Math.abs(seconds) > 86400) {
            System.out.println("访问已过期，请检查服务器时间！");
            return;
        }
        //2.组装参数，
        SortedMap<String, String> sortedMap12 = new TreeMap<>();
        sortedMap12.put("appId", appId);
        sortedMap12.put("timestamp", timestamp);
        sortedMap12.put("sign", "");

        //3.校验签名
        Boolean flag = SignUtil.isCorrectSign(sortedMap12, appSecret);
        if(flag){
            System.out.println("签名验证通过");
        }else {
            System.out.println("签名验证未通过");
        }
    }
}