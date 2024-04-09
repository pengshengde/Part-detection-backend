package com.example.springboot1.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.example.springboot1.common.client.DefectType;
import com.example.springboot1.entity.client.Image;
import com.example.springboot1.entity.client.ImageResult;
import com.example.springboot1.entity.client.MmdectionResult;
import com.example.springboot1.entity.client.Parts;
import com.example.springboot1.common.client.Result;
import com.example.springboot1.config.LocalConfig;
import com.example.springboot1.service.Impl.GetAppSecretServiceImpl;
import com.example.springboot1.service.Impl.GetImageServiceImpl;
import com.example.springboot1.service.Impl.ImageServiceImpl;
import com.example.springboot1.service.Impl.PartServiceImpl;
import com.example.springboot1.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/image1")
public class GetImageController {

    @Autowired
    private GetImageServiceImpl getImageServiceImpl;

    @Autowired
    HttpUtils httpUtils;

    @Autowired
    private GetAppSecretServiceImpl getAppSecretServiceImpl;

    @Autowired
    private GetImageUrlController getImageUrlController;

    @Autowired
    private ImageServiceImpl imageServiceImpl;

    @Autowired
    private PartServiceImpl partServiceImpl;

    private String image_id;
    private String image_base64;
    private String sign;
    private String part_id;

    // 辅助方法：将拼音缺陷转换为中文缺陷
    private String convertToChinese(String pinyinDefect) {
        for (DefectType defectType : DefectType.values()) {
            if (defectType.getPinyinDefect().equals(pinyinDefect)) {
                return defectType.getChineseDefect();
            }
        }
        return pinyinDefect; // 如果找不到对应的中文缺陷，返回原始拼音缺陷
    }


    /**
     * getImage输入是一个实体类的形式
     */
    @PostMapping                                 // app上传图片的接口，新增图片信息
    public Result addOneImage(@RequestBody Map<String, String> request
                           /*@RequestBody GetImage getImage*/){

        String sign = request.get("sign");
        String image_id = request.get("image_id");
        String image_base64 = request.get("image_base64");
        String part_id = request.get("part_id");

//        String sign = getImage.getSign();
//        String image_id = getImage.getImage_id();
//        String image_base64 = getImage.getImage_base64();
//        String part_id = getImage.getPart_id();

        image_id = part_id + image_id;

        List<ImageResult> getImageResultList = new ArrayList<>();                // imageResult实体类的列表

        if (getAppSecretServiceImpl.existsSign(sign)){

            boolean saveImage = true;
            if (!SqlHelper.retBool(getImageServiceImpl.getImageCount(image_id))){    // 检查image_id的信息是否存在
                MultipartFile file = httpUtils.base64ToMultipartFile(image_id, image_base64);
                String original_image_url = getImageUrlController.uploadImageInner(file,"original");
                saveImage = getImageServiceImpl.saveImage(image_id, part_id, original_image_url);
            }

            if (saveImage){
                ImageResult imageResult = new ImageResult();

                String nowTime = LocalConfig.GetNowTime();                                 // 获取当前时间

                String dectionResult = httpUtils.pythonPost(LocalConfig.getPythonurl(),image_id,image_base64);   // 将上传的到的图片post到python接口
                MmdectionResult mmdectionResult = httpUtils.postResultToEntity(dectionResult);                   // 得到python接口返回的检测结果
                System.out.println(mmdectionResult.toString());

                MultipartFile file = httpUtils.base64ToMultipartFile(image_id,mmdectionResult.getImageResultBase64());   // 将获得的base64转化成file文件
                String image_url = getImageUrlController.uploadImageInner(file,"result");                       // 获得检测完图片的Url,保存在文件夹的/result
                System.out.println(image_url);


                List<String> defect_type = mmdectionResult.getDetect_type();
                List<String> defect_type_chinese = new ArrayList<>();

                if (defect_type.size()==0){
                    imageResult.setDefect_type("正常");
                }else {
                    for (String pinyinDefect : defect_type) {
                        String chineseDefect = convertToChinese(pinyinDefect);
                        defect_type_chinese.add(chineseDefect);
                    }
                    imageResult.setDefect_type(String.join(", ",defect_type_chinese));
                }

                imageResult.setImage_id(image_id);
                imageResult.setDetect_time(nowTime);
                imageResult.setProcessed_image_url(image_url);
                imageResult.setAdditional_info("1");

                getImageServiceImpl.saveImageDetectionResult(imageResult.getImage_id(),imageResult.getDefect_type(),
                        imageResult.getProcessed_image_url(),imageResult.getAdditional_info(),imageResult.getDetect_time());

                getImageResultList.add(imageResult);

                QueryWrapper queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("part_id",part_id);

                // 检查零件信息是否存在
                if (SqlHelper.retBool(partServiceImpl.count(queryWrapper))){        // 如果存在
                    Parts existParts = partServiceImpl.getOne(queryWrapper);        // 获取该零件保存的缺陷列表
                    List<String> existDefectType = existParts.getDefect_type();

                    Set<String> set = new HashSet<>();
                    set.addAll(defect_type_chinese);
                    set.addAll(existDefectType);
                    List<String> mergedList = new ArrayList<>(set);                 // 确保合并后的列表中没有重复元素，

                    existParts.setDefect_type(mergedList);                          // 将新的列表保存在数据库中
                    existParts.setPart_id(part_id);


                    boolean savePartResult = partServiceImpl.saveOrUpdate(existParts, queryWrapper);

                    if (!savePartResult){
                        return Result.errorSaveImage();                            // 若保存零件失败，返回失败结果
                    }
                }else {
                    Parts newParts = new Parts();                                  // 如果不存在，保存零件信息
                    newParts.setPart_id(part_id);
                    newParts.setDefect_type(defect_type_chinese);
                    newParts.setDetect_time(nowTime);
                    boolean savePartResult = partServiceImpl.save(newParts);

                    if (!savePartResult){
                        return Result.errorSaveImage();
                    }
                }


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
                return Result.successGetData(getPage);
            }else {
                return Result.errorGetData();
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

        ImageResult imageResult;
        List<ImageResult> getImageResult = new ArrayList<>();

        if (getAppSecretServiceImpl.existsSign(sign)){
            imageResult = getImageServiceImpl.getImageDetectionResult(image_id);
            if (!imageResult.isEmpty()){
                getImageResult.add(imageResult);
                return Result.successGetData(getImageResult);
            }else {
                return Result.errorGetData();
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
                              @RequestParam(defaultValue = "1") Integer page_number,
                              @RequestParam(defaultValue = "10") Integer page_size,
                              @RequestParam String part_id
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
            // 删除前检查图片的某种缺陷是否删除干净
            Image image = imageServiceImpl.getOne(queryWrapper);
            boolean delete = imageServiceImpl.remove(queryWrapper);                             // 删掉该图片的信息

            // 下面对删除所有图片的零件进行删除
            QueryWrapper<Image> queryWrapper1 =new QueryWrapper<>();
            queryWrapper1.eq("part_id", part_id);                                        // 查询该零件的图片信息
            if(imageServiceImpl.count(queryWrapper1) == 0){                                     // 若检测到所有图片已经删除，则删除该零件信息
                partServiceImpl.remove(new QueryWrapper<Parts>().eq("part_id", part_id));
            } else {                                                                            // 如果删除图片为最后一张，则不需要更新零件缺陷
                String ImageDefectType = image.getDefect_type();                                    // 获取该图片的缺陷

                String[] values =  ImageDefectType.split(",");                                // 将该图片存在的缺陷转化为字典
                for(String value : values){                                                         // 对该字典中存在的缺陷进行遍历
                    queryWrapper.like("defect_type", value);
                    if (imageServiceImpl.count(queryWrapper)==0){                                   // 检查剩余缺陷图片中是否存在该缺陷，若不存在则需要更新零件的缺陷信息
                        QueryWrapper<Parts> queryWrapperPart = new QueryWrapper<Parts>();           // 找到该图片的零件信息
                        queryWrapperPart.eq("part_id",part_id);
                        List<String> PartDefectType =  partServiceImpl.getOne(queryWrapperPart).getDefect_type();   // 获得该零件的缺陷类型
                        PartDefectType.remove(value);                                                // 去掉上面已删除的图片缺陷类型

                        Integer id = partServiceImpl.getOne(queryWrapperPart).getId();

                        Parts updatePart = new Parts();
                        updatePart.setId(id);
                        updatePart.setDefect_type(PartDefectType);
                        updatePart.setPart_id(part_id);


                        /*UpdateWrapper<Parts> updateWrapper = new UpdateWrapper<Parts>();             //  更新该零件的缺陷类型
                        updateWrapper.eq("part_id",part_id);
                        updateWrapper.set("defect_type",PartDefectType);*/
                        partServiceImpl.saveOrUpdate(updatePart);
                    }
                }
            }




            List<Page> getDeleteImageResult = new ArrayList<>();

            Page<Image> page = imageServiceImpl.page(new Page<>(page_number,page_size),queryWrapper1);
            getDeleteImageResult.add(page);

            return Result.successDelete(getDeleteImageResult);
        }else {
            return Result.errorSignJudge();
        }
    }

    @GetMapping("/parts")
    public Result getPartsImage(@RequestParam String sign,
                                @RequestParam String part_id,
                                @RequestParam(defaultValue = "1") Integer page_number,
                                @RequestParam(defaultValue = "10") Integer page_size){

        if (getAppSecretServiceImpl.existsSign(sign)){
            QueryWrapper<Image> queryWrapper =new QueryWrapper<>();
            queryWrapper.eq("part_id", part_id);

            Page<Image> page = imageServiceImpl.page(new Page<>(page_number,page_size),queryWrapper);

            List<Page> getPage = new ArrayList<>();
            getPage.add(page);

            if (!page.getRecords().isEmpty()){
                return Result.successGetData(getPage);
            }else {
                return Result.errorGetData();
            }
        }else {
            return Result.errorSignJudge();
        }
    }

}
