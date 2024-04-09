package com.example.springboot1.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "local")
public class LocalConfig {

    @Value("${local:uploadFilePath")
    private static String uploadFilePath = "/home/image";

    @Value("${local:baseurl}")
    private static String baseurl = "http://172.20.138.208/image";

    @Value("${local:pythonurl}")
    private static String pythonurl = "http://172.20.138.208:5000/api/mmdetection/predict";

    public static String getUploadFilePath() {
        return uploadFilePath;
    }

    public static void setUploadFilePath(String uploadFilePath) {
        LocalConfig.uploadFilePath = uploadFilePath;
    }


    public static String getBaseurl() {
        return baseurl;
    }

    public static void setBaseurl(String baseurl) {
        LocalConfig.baseurl = baseurl;
    }

    public static String getPythonurl() {
        return pythonurl;
    }

    public static void setPythonurl(String pythonurl) {
        LocalConfig.pythonurl = pythonurl;
    }


    public static String ContentTypeToExtension(String contentType){

        Map<String, String> mimeTypeToExtension = new HashMap<>();
        mimeTypeToExtension.put("image/jpeg", ".jpg");
        mimeTypeToExtension.put("image/png", ".png");
        mimeTypeToExtension.put("image/gif", ".gif");
        mimeTypeToExtension.put("image/bmp", ".bmp");
        mimeTypeToExtension.put("image/tiff", ".tiff");
        mimeTypeToExtension.put("image/webp", ".webp");
        mimeTypeToExtension.put("image/x-icon", ".ico");

        String fileExtension = mimeTypeToExtension.get(contentType);
        if (fileExtension != null) {
            System.out.println("File Extension: " + fileExtension);
        } else {
            System.out.println("Unsupported ContentType");
        }
        return fileExtension;
    }

    public static  String GetNowTime(){
        // 读取当前时间
        LocalDateTime currentTime = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化当前时间
        String nowTime = currentTime.format(formatter);

        return nowTime;
    }




}
