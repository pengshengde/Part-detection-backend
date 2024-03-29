package com.example.springboot1.entity.client;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;


/**
 * 将从图床相关的数据存放在数据库的实体类，包括检测图片的名称，类型，图片url，新的图像名字，图像创建时间，图像标签（原始图片/检测后的图片）
 * */
@Data
@ToString
@TableName("sys_image_url")
public class ImageInfo {

    @TableId(value = "id", type = IdType.AUTO)
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

    @TableField("image_tag")
    private String imageTag;

}
