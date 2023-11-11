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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getPart_id() {
        return part_id;
    }

    public void setPart_id(String part_id) {
        this.part_id = part_id;
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
                "sign='" + sign + '\'' +
                ", image_id='" + image_id + '\'' +
                ", part_id='" + part_id + '\'' +
                ", image_base64='" + image_base64 + '\'' +
                '}';
    }
}
