package com.example.springboot1.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.springboot1.entity.client.MmdectionResult;
import com.example.springboot1.config.LocalConfig;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class HttpUtils {

    public static LocalConfig localConfig = new LocalConfig();
    /**
     * @param url 调用接口
     * @param image_id
     * @param image_base64   查询条件,包括image_id和image_base64
     * @return String
     */

    public static String pythonPost(String url, String image_id, String image_base64) {

        JSONObject jsonObject = new JSONObject();
        String imageId = JSONObject.toJSONString(image_id);
        String imageBase64 = JSONObject.toJSONString(image_base64);
        jsonObject.put("image_id", imageId);
        jsonObject.put("image", imageBase64);


        HttpResponse res = HttpRequest.post(url).connectionTimeout(90000).timeout(90000)
                .contentType("application/json", "UTF-8").bodyText(jsonObject.toString())
                .send();
        res.charset("utf-8");
        return res.bodyText();
    }

    public static MmdectionResult postResultToEntity(String bodyText){
        JSONObject jsonObject = JSONObject.parseObject(bodyText);
        JSONArray jsonArray = jsonObject.getJSONArray("class_info");

        String imageDetect = jsonObject.getString("success");
        String imageResultBase64 = jsonObject.getString("encoded_image");
        String imageResultSavePath = jsonObject.getString("image_save_path");


        List<String> detect_type = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            String value = jsonArray.getString(i);
            detect_type.add(value);
        }

        MmdectionResult mmdectionResult = new MmdectionResult();
        mmdectionResult.setImageDetect(imageDetect);
        mmdectionResult.setImageResultBase64(imageResultBase64);
        mmdectionResult.setImageResultSavePath(imageResultSavePath);
        mmdectionResult.setDetect_type(detect_type);

        return mmdectionResult;
    }

    public static MultipartFile base64ToMultipartFile(String image_id, String base64){

        String contentType;
        byte[] bytes;
        String imageName;
        if (base64.contains(",")){
            String[] parts = base64.split(",");
            contentType = parts[0].split(";")[0].split(":")[1];
            bytes = Base64.getDecoder().decode(parts[1]);

            String fileExtension = localConfig.ContentTypeToExtension(contentType);
            imageName = image_id + fileExtension;
        }else {
            contentType = "image/jpeg";
            bytes = Base64.getDecoder().decode(base64);
            imageName = image_id + ".jpg";
        }

        return new MockMultipartFile("file", imageName, contentType, bytes);
    }
}
