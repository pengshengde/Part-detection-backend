package com.example.springboot1.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Data
public class LocalConfig {

    @Value("${serverInfo.uploadpath}")
    private String uploadFilePath;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxfilesize;

    @Value("${serverInfo.baseurl}")
    private String baseurl;

    public Long getMaxFileSize(){
        String str = maxfilesize.substring(0,maxfilesize.length()-2);
        Long maxSize = 1024 * 1024 * Long.parseLong(str);
        return maxSize;
    }



}
