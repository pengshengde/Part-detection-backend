package com.example.springboot1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot1.entity.client.ImageResult;
import org.apache.ibatis.annotations.*;

public interface GetImageMapper extends BaseMapper<ImageResult> {

    @Insert("Insert INTO sys_Images (image_id,part_id, original_image_url) values (#{image_id},#{part_id},#{original_image_url})")
    boolean saveImage(@Param("image_id") String image_id,@Param("part_id") String part_id, @Param("original_image_url")String original_image_url);

    @Select("select COUNT(*) from sys_Images where image_id = #{image_id}")
    Integer  getImageCount(@Param("image_id") String image_id);


    @Update("update sys_Images set defect_type = #{defect_type}, processed_image_url = #{processed_image_url}," +
            "additional_info = #{additional_info}, detect_time = #{detect_time} where image_id = #{image_id}")
    boolean saveImageDetectionResult(@Param("image_id") String image_id,@Param("defect_type") String defect_type,
                                 @Param("processed_image_url") String processed_image_url,@Param("additional_info") String additional_info,
                                 @Param("detect_time") String detect_time);

    @Select("SELECT * FROM sys_Images WHERE image_id=#{image_id}")
    ImageResult getImageDetectionResult(@Param("image_id") String image_id);


}
