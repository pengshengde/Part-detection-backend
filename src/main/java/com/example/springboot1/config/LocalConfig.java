package com.example.springboot1.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class LocalConfig {

    @Value("${serverInfo.uploadpath}")
    private String uploadFilePath;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxfilesize;

    @Value("${serverInfo.baseurl}")
    private String baseurl;

    @Value("${python.url}")
    private String pythonurl;

    public Long getMaxFileSize(){
        String str = maxfilesize.substring(0,maxfilesize.length()-2);
        Long maxSize = 1024 * 1024 * Long.parseLong(str);
        return maxSize;
    }


    public String ContentTypeToExtension(String contentType){

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

    public  String GetNowTime(){
        // 读取当前时间
        LocalDateTime currentTime = LocalDateTime.now();
        // 定义日期时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化当前时间
        String nowTime = currentTime.format(formatter);

        return nowTime;
    }




}
