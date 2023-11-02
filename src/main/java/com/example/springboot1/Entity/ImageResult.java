package com.example.springboot1.Entity;


import lombok.Data;
import lombok.ToString;


/**
 * 向服务器存放数据的实体类，存放图片的相关信息
 * */
@Data
@ToString
public class ImageResult {

//    private String sign;

    private String image_id;      // 零件编号

    private String defect_type;   // 含有的缺陷类别             // 列表

    private String detect_time;    // 零件检测完成时间

    //    private String image_base64;    // 编码为base64的零件图像数据

    private String additional_info;      // 零件缺陷检测完成的标签数据       // 字典

    private String processed_image_url;   // 图片url连接

    public boolean isEmpty(){
        return image_id == null;
    }
}
