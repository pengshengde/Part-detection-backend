package com.example.springboot1.controller;

import com.example.springboot1.Entity.ImageInfo;
import com.example.springboot1.config.LocalConfig;
import com.example.springboot1.service.GetImageUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api")
public class GetImageUrlController {

    @Autowired
    GetImageUrlService getImageUrlService;

    @Autowired
    private LocalConfig localConfig;
    @PostMapping("/image")
    public String uploadImage(MultipartFile file){
        long size = (long) file.getSize();
        if (size > localConfig.getMaxFileSize()) {
            return "上传文件过大，请上传小于100MB大小的文件";
        }

        ImageInfo imageInfo = getImageUrlService.uploadImage(file);

        if (imageInfo.getFileUrl() == null){
            return "保存图片失败";
        }

        return imageInfo.getFileUrl();
    }
}
