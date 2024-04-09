package com.example.springboot1.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot1.entity.client.ImageInfo;
import com.example.springboot1.config.LocalConfig;
import com.example.springboot1.mapper.ImageUrlMapper;
import com.example.springboot1.service.GetImageUrlService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class GetImageUrlServiceImpl extends ServiceImpl<ImageUrlMapper,ImageInfo> implements GetImageUrlService {

    public ImageInfo uploadImage(MultipartFile file, String imageTag) {
        String fileName = file.getOriginalFilename();
        String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String fileNamePrefix = fileName.substring(0, fileName.lastIndexOf("."));

        LocalDateTime localDateTime = LocalDateTime.now();
        Integer Year = localDateTime.getYear();
        Integer Month = localDateTime.getMonthValue();

        long timestamp = System.currentTimeMillis();                       // 获取当前的时间戳
        String formattedDateTime = LocalConfig.GetNowTime();                // 获取当前的时间

        String sepa = File.separator;

        String baseFilePath = LocalConfig.getUploadFilePath();
        String filePath = baseFilePath + sepa + imageTag + sepa + Year + sepa + Month;
        createDirectory(filePath);

        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        String newFileName = timestamp + "-" + uuidString + "." + fileNameSuffix;
        File targetFile = new File(filePath + sepa + newFileName);

        try {
            //将上传文件写到服务器上指定的文件
            file.transferTo(targetFile);
            String saveUrl =LocalConfig.getBaseurl() + imageTag + "/" +  Year + "/" + Month + "/" + newFileName;

            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setNewImageName(newFileName);
            imageInfo.setImageName(fileName);
            imageInfo.setImageUrl(saveUrl);
            imageInfo.setImageCreateTime(formattedDateTime);
            imageInfo.setImageType(fileNameSuffix);
            imageInfo.setImageTag(imageTag);


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
