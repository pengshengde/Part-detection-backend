package com.example.springboot1.Entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName("sys_image_url")
public class ImageInfo {

    @TableId()
    private Integer id;

    @TableField("image_name")
    private String imageName;

    @TableField("image_type")
    private String imageType;

    @TableField("image_url")
    private String imageUrl;

    @TableField("new_image_name")
    private String newImageName;

    @TableField("image_create_time")
    private String imageCreateTime;


}
