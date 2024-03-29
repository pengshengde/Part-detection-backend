package com.example.springboot1.service;

import com.example.springboot1.entity.client.ImageInfo;
import org.springframework.web.multipart.MultipartFile;

public interface GetImageUrlService {


    ImageInfo uploadImage(MultipartFile file, String nextFilePath);

    void createDirectory(String directoryPath);
}
