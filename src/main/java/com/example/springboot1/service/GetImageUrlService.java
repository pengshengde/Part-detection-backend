package com.example.springboot1.service;

import com.example.springboot1.Entity.ImageInfo;
import org.springframework.web.multipart.MultipartFile;

public interface GetImageUrlService {


    ImageInfo uploadImage(MultipartFile file);
    void createDirectory(String directoryPath);
}
