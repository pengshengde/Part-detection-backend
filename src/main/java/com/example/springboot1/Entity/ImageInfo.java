package com.example.springboot1.Entity;


import lombok.Data;

@Data
public class ImageInfo {

    public ImageInfo(){}
    public ImageInfo(String fileName, String fileType, String fileUrl, String oldFileName, String fileCreateDate){
        this.fileName = fileName;
        this.fileType= fileType;
        this.fileUrl = fileUrl;
        this.originFileName = oldFileName;
        this.fileCreateDate = fileCreateDate;
    }
    public String fileName;
    public String fileType;
    public String fileUrl;
    public String originFileName;
    public String fileCreateDate;


}
