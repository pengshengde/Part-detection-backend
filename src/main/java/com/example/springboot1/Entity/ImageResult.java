package com.example.springboot1.Entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;


/**
 * 向服务器存放数据的实体类，存放图片的相关信息返回给软件
 * */
@Data
@ToString
@TableName("sys_image")
public class ImageResult {

//    private String sign;
    @TableField("image_id")
    private String image_id;      // 零件编号

    @TableField("defect_type")
    private String defect_type;   // 含有的缺陷类别             // 列表

    @TableField("detect_time")
    private String detect_time;    // 零件检测完成时间

    //    private String image_base64;    // 编码为base64的零件图像数据
    @TableField("original_image_url")
    private String original_image_url;

    @TableField("additional_info")
    private String additional_info;      // 零件缺陷检测完成的标签数据       // 字典

    @TableField("processed_image_url")
    private String processed_image_url;   // 图片检测后url连接

    public boolean isEmpty(){
        return image_id == null;
    }
}
