package com.example.springboot1.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot1.Entity.GetAppSecret;
import com.example.springboot1.Entity.GetImage;
import com.example.springboot1.Entity.Image;
import com.example.springboot1.Entity.ImageResult;
import com.example.springboot1.common.Result;
import com.example.springboot1.service.IImageService;
import com.example.springboot1.service.Impl.GetAppSecretServiceImpl;
import com.example.springboot1.service.Impl.GetImageServiceImpl;
import com.example.springboot1.service.Impl.ImageServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/image")
public class GetImageController {

    @Autowired
    private GetImageServiceImpl getImageServiceImpl;

    @Autowired
    private GetAppSecretServiceImpl getAppSecretServiceImpl;

    @Autowired
//    @Qualifier("imageServiceImpl")
//    private IImageService imageService;

    private ImageServiceImpl imageServiceImpl;
    private String image_id;
    private String image_base64;
    private String sign;


    /**
     * getImage输入是一个实体类的形式
     */
    @PostMapping                                 // app上传图片的接口，新增图片信息
    public Result addOneImage(/*@RequestParam String sign,
                           @RequestParam String image_id,
                           @RequestParam String image_base64*/
                            @RequestBody GetImage getImage){

        sign = getImage.getSign();
        image_id = getImage.getImage_id();
        image_base64 = getImage.getImage_base64();

        List<ImageResult> getImageResultList = new ArrayList<>();                // imageResult实体类的列表

        if (getAppSecretServiceImpl.existsSign(sign)){
            boolean saveImage = getImageServiceImpl.saveImage(image_id,image_base64);
            if (saveImage){
                ImageResult imageResult = new ImageResult();

                // 读取当前时间
                LocalDateTime currentTime = LocalDateTime.now();
                // 定义日期时间格式
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                // 格式化当前时间
                String nowTime = currentTime.format(formatter);

                imageResult.setImage_id(image_id);
                imageResult.setDefect_type("过切");
                imageResult.setDetect_time(nowTime);
                imageResult.setProcessed_image_url("https://pic.imgdb.cn/item/6513f2ecc458853aef42061f.jpg");
                imageResult.setAdditional_info("1");

                getImageServiceImpl.saveImageDetectionResult(imageResult.getImage_id(),imageResult.getDefect_type(),
                        imageResult.getProcessed_image_url(),imageResult.getAdditional_info(),imageResult.getDetect_time());


                getImageResultList.add(imageResult);
                return Result.successSaveImage(getImageResultList);
            }else{
                return Result.errorSaveImage();
            }
        }else {
            return Result.errorSignJudge();
        }
    }

/*    @PostMapping
    public Result addListImage(@RequestBody List<Image> images){

        return Result.success();
    }*/

    @GetMapping
    public Result findPage(@RequestParam String sign,
                           @RequestParam Integer page_number,
                           @RequestParam Integer page_size,
                           @RequestParam (defaultValue = "") String image_id,
                           @RequestParam (defaultValue = "") String defect_type,
                           @RequestParam (defaultValue = "") String detect_time){

        if (getAppSecretServiceImpl.existsSign(sign)){
            QueryWrapper<Image> queryWrapper =new QueryWrapper<>();
            if (!"".equals(image_id)) {
                queryWrapper.like("image_id", image_id);
            }
            if (!"".equals(defect_type)) {
                queryWrapper.like("defect_type", defect_type);
            }
            if (!"".equals(detect_time)) {
                queryWrapper.like("detect_time", detect_time);
            }
            Page<Image> page = imageServiceImpl.page(new Page<>(page_number,page_size), queryWrapper);

            List<Page> getPage = new ArrayList<>();
            getPage.add(page);

            System.out.println(page);

            if (!page.getRecords().isEmpty()){
                return Result.successGetImage(getPage);
            }else {
                return Result.errorGetImage();
            }

        }else {
            return Result.errorSignJudge();
        }
    }

    @GetMapping("/{image_id}")
    public Result getOneImage(@PathVariable String image_id,
                              @RequestParam String sign
                             /* @RequestParam String defect_type,
                              @RequestParam String detect_time*/){

        ImageResult imageResult = new ImageResult();
        List<ImageResult> getImageResult = new ArrayList<>();

        if (getAppSecretServiceImpl.existsSign(sign)){
            imageResult = getImageServiceImpl.getImageDetectionResult(image_id);
            if (!imageResult.isEmpty()){
                getImageResult.add(imageResult);
                return Result.successGetImage(getImageResult);
            }else {
                return Result.errorGetImage();
            }
        }else {
            return Result.errorSignJudge();
        }
    }

/*    @PutMapping
    public Result updateImage(@RequestBody ImageResult imageResult){

        sign = imageResult.getSign();
        image_id = imageResult.getImage_id();

    }*/

    @DeleteMapping ("/{image_id}")
    public Result deleteImage(@RequestParam String sign,
                              @PathVariable String image_id,
                              @RequestParam Integer page_number,
                              @RequestParam Integer page_size
                              /*@RequestParam (defaultValue = "") String defect_type,
                              @RequestParam (defaultValue = "") String detect_time*/){

        QueryWrapper<Image> queryWrapper =new QueryWrapper<>();
        if (!"".equals(image_id)) {
            queryWrapper.eq("image_id", image_id);
        }
//        if (!"".equals(defect_type)) {
//            queryWrapper.like("defect_type", defect_type);
//        }
//        if (!"".equals(detect_time)) {
//            queryWrapper.like("detect_time", detect_time);
//        }

        if (getAppSecretServiceImpl.existsSign(sign)){
            boolean delete = imageServiceImpl.remove(queryWrapper);
            if (delete){
                List<Page> getDeleteImageResult = new ArrayList<>();
                Page<Image> page = imageServiceImpl.page(new Page<>(page_number,page_size));
                getDeleteImageResult.add(page);

                return Result.successDeleteImage(getDeleteImageResult);
            }else {
                return Result.errorDeleteImage();
            }
        }else {
            return Result.errorSignJudge();
        }
    }

}
