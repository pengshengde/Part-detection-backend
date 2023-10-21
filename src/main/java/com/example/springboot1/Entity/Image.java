package com.example.springboot1.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
@TableName("sys_image")
public class Image {

    private Integer id;

    private String image_id;      // 零件编号

    private String defect_type;   // 含有的缺陷类别

    private String detect_time;    // 零件检测完成时间


}
