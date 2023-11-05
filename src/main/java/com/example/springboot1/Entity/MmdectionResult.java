package com.example.springboot1.Entity;

import lombok.Data;

import java.util.List;


/**
 * 从python接口获得数据的实体类，存放图片的检测结果信息
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
}