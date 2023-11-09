package com.example.springboot1.Entity;


import lombok.Data;

/*
 * 获取图片,从软件前端获取图片的数据
 */

@Data
public class GetImage {

    private String sign;
    private String image_id;
    private String part_id;
    private String image_base64;

}
