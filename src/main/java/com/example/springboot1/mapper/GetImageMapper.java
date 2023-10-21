package com.example.springboot1.mapper;

import com.example.springboot1.Entity.GetAppSecret;
import com.example.springboot1.Entity.GetImage;
import com.example.springboot1.Entity.Image;
import com.example.springboot1.Entity.ImageResult;
import org.apache.ibatis.annotations.*;

public interface GetImageMapper {

    @Insert("Insert INTO sys_Image (image_id, image_base64) values (#{image_id},#{image_base64})")
    boolean saveImage(@Param("image_id") String image_id, @Param("image_base64")String image_base64);

    @Update("update sys_Image set defect_type = #{defect_type}, processed_image_url = #{processed_image_url}," +
            "additional_info = #{additional_info}, detect_time = #{detect_time} where image_id = #{image_id}")
    boolean saveImageDetectionResult(@Param("image_id") String image_id,@Param("defect_type") String defect_type,
                                 @Param("processed_image_url") String processed_image_url,@Param("additional_info") String additional_info,
                                 @Param("detect_time") String detect_time);

    @Select("SELECT * FROM sys_Image WHERE image_id=#{image_id}")
    ImageResult getImageDetectionResult(@Param("image_id") String image_id);


}
