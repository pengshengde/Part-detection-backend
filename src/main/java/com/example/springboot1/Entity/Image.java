package com.example.springboot1.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/*
*  该类用于存放图片信息，用于想前端软件传递信息的实体类
* */
@Getter
@Setter
@Data
@TableName("sys_image")
public class Image {

    private Integer id;

    private String image_id;      // 图片编号

    private String part_id;      // 零件编号

    private String defect_type;   // 含有的缺陷类别

    private String detect_time;    // 图片检测完成时间



}
