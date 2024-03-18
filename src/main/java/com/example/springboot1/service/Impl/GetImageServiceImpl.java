package com.example.springboot1.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot1.entity.client.ImageResult;
import com.example.springboot1.controller.GetImageUrlController;
import com.example.springboot1.mapper.GetImageMapper;
import com.example.springboot1.service.GetImageService;
import com.example.springboot1.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetImageServiceImpl extends ServiceImpl<GetImageMapper,ImageResult> implements GetImageService {

    @Autowired
    private GetImageMapper getImageMapper;

    @Autowired
    private HttpUtils httpUtils;

    @Autowired
    GetImageUrlController getImageUrlController;

    @Override
    public boolean saveImage(String image_id, String part_id, String original_image_url) {

        return getImageMapper.saveImage(image_id, part_id, original_image_url);
    }

    @Override
    public boolean saveImageDetectionResult(String image_id, String defect_type, String processed_image_url,
                                            String additional_info, String detect_time){
        return getImageMapper.saveImageDetectionResult(image_id,defect_type,processed_image_url,additional_info,detect_time);
    }

    @Override
    public ImageResult getImageDetectionResult(String image_id){
        return  getImageMapper.getImageDetectionResult(image_id);
    }

    @Override
    public Integer getImageCount(String image_id){
        return getImageMapper.getImageCount(image_id);
    }


}
