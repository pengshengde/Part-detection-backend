package com.example.springboot1.Entity;


import lombok.Data;

@Data
public class GetImage {

    private String sign;
    private String image_id;
    private String image_base64;

     // 构造函数、Getter 和 Setter 方法

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getImage_base64() {
        return image_base64;
    }

    public void setImage_base64(String image_base64) {
        this.image_base64 = image_base64;
    }

    @Override
    public String toString() {
        return "GetImage{" +
                ", image_id='" + image_id + '\'' +
                ", image_base64='" + image_base64 + '\'' +
                '}';
    }
}
