package com.example.springboot1.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot1.entity.client.ImageInfo;
import com.example.springboot1.common.client.Result;
import com.example.springboot1.config.LocalConfig;
import com.example.springboot1.service.Impl.GetImageUrlServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/geturl")
public class GetImageUrlController {

    @Autowired
    GetImageUrlServiceImpl getImageUrlServiceImpl;

    @Autowired
    LocalConfig localConfig;

    @PostMapping
    public Result uploadImage(MultipartFile file){

        String imageName = file.getOriginalFilename();
        QueryWrapper<ImageInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("image_name",imageName);
        if (getImageUrlServiceImpl.getOne(queryWrapper)!=null){
            return Result.urlIsExist(getImageUrlServiceImpl.getOne(queryWrapper).getImageUrl());
        }

        String nextFilePath = "url";

        ImageInfo imageInfo = getImageUrlServiceImpl.uploadImage(file,nextFilePath);
        getImageUrlServiceImpl.save(imageInfo);
        String imageUrl = imageInfo.getImageUrl();
        return Result.success(imageUrl);
    }

    public String uploadImageInner(MultipartFile file,  String imageTag){

        String imageName = file.getOriginalFilename();
        QueryWrapper<ImageInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("image_name",imageName);
        queryWrapper.eq("image_tag",imageTag);
        if (getImageUrlServiceImpl.getOne(queryWrapper)!=null){
            return getImageUrlServiceImpl.getOne(queryWrapper).getImageUrl();
        }else {
            ImageInfo imageInfo = getImageUrlServiceImpl.uploadImage(file,imageTag);
            getImageUrlServiceImpl.save(imageInfo);
            String imageUrl = imageInfo.getImageUrl();
            return imageUrl;
        }
    }

}
