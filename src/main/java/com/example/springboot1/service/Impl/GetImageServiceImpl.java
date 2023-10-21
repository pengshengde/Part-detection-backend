package com.example.springboot1.service.Impl;

import com.example.springboot1.Entity.GetImage;
import com.example.springboot1.Entity.ImageResult;
import com.example.springboot1.mapper.GetImageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetImageServiceImpl implements GetImageMapper {

    @Autowired
    private GetImageMapper getImageMapper;

    @Override
    public boolean saveImage(String image_id, String image_base64) {
        return getImageMapper.saveImage(image_id, image_base64);
    }

    @Override
    public boolean saveImageDetectionResult(String image_id,String defect_type,String processed_image_url,
                                            String additional_info,String detect_time){
        return getImageMapper.saveImageDetectionResult(image_id,defect_type,processed_image_url,additional_info,detect_time);
    }

    @Override
    public ImageResult getImageDetectionResult(String image_id){
        return  getImageMapper.getImageDetectionResult(image_id);
    }



}
