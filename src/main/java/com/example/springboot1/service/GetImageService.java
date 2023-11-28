package com.example.springboot1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springboot1.Entity.ImageResult;

import java.util.List;

public interface GetImageService extends IService<ImageResult> {
    boolean saveImage(String image_id, String part_id, String original_image_url);

    boolean saveImageDetectionResult(String image_id, String defect_type, String processed_image_url,
                                     String additional_info, String detect_time);

    ImageResult getImageDetectionResult(String image_id);

    Integer getImageCount(String image_id);
}
