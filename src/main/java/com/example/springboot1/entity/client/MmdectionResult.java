package com.example.springboot1.entity.client;

import lombok.Data;

import java.util.List;


/**
 * 从python接口获得数据的实体类，存放图片的检测结果信息，包括图片的base64，图片在服务器的保存路径，图片的缺陷类型
 * */
@Data
public class MmdectionResult {

    private List<String> detect_type;

    private String imageResultBase64;

    private String imageResultSavePath;

    private String imageDetect;

    public List<String> getDetect_type() {
        return detect_type;
    }

    public void setDetect_type(List<String> detect_type) {
        this.detect_type = detect_type;
    }

    public String getImageResultBase64() {
        return imageResultBase64;
    }

    public void setImageResultBase64(String imageResultBase64) {
        this.imageResultBase64 = imageResultBase64;
    }

    public String getImageResultSavePath() {
        return imageResultSavePath;
    }

    public void setImageResultSavePath(String imageResultSavePath) {
        this.imageResultSavePath = imageResultSavePath;
    }

    public String getImageDetect() {
        return imageDetect;
    }

    public void setImageDetect(String imageDetect) {
        this.imageDetect = imageDetect;
    }


}
