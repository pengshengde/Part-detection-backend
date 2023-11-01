package com.example.springboot1.service;

import com.example.springboot1.Entity.ImageInfo;
import com.example.springboot1.config.LocalConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class GetImageUrlService {

    @Autowired
    private LocalConfig localConfig;

    public ImageInfo uploadImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String fileNamePrefix = fileName.substring(0, fileName.lastIndexOf("."));

        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = formatter.format(localDateTime);
        Integer Year = localDateTime.getYear();
        Integer Month = localDateTime.getMonthValue();
        long timestamp = System.currentTimeMillis();

        String sepa = File.separator;

        String baseFilePath = localConfig.getUploadFilePath();
        String filePath = baseFilePath + sepa + Year + sepa + Month;
        createDirectory(filePath);

        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        String newFileName = timestamp + "-" + uuidString + "." + fileNameSuffix;
        File targetFile = new File(filePath + sepa + newFileName);

        try {
            //将上传文件写到服务器上指定的文件
            file.transferTo(targetFile);
            String saveUrl =localConfig.getBaseurl()+ Year + "/" + Month + "/" + newFileName;
            ImageInfo imageInfo = new ImageInfo(newFileName,fileNameSuffix,saveUrl,fileName,formattedDateTime);
            return imageInfo;
        } catch (IOException e) {
            e.printStackTrace();
            return new ImageInfo();
        }
    }

    public void createDirectory(String directoryPath){
        File targetFile = new File(directoryPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
    }

}
