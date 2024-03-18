package com.example.springboot1.controller.app;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot1.common.browser.constant.HttpStatus;
import com.example.springboot1.common.browser.core.controller.BaseController;
import com.example.springboot1.common.browser.core.domain.AjaxResult;
import com.example.springboot1.common.browser.core.page.TableDataInfo;
import com.example.springboot1.config.LocalConfig;
import com.example.springboot1.entity.browser.quality.ImageData;
import com.example.springboot1.entity.browser.quality.SysImage;
import com.example.springboot1.entity.browser.quality.SysPart;
import com.example.springboot1.entity.browser.quality.SysSign;
import com.example.springboot1.entity.client.ImageInfo;
import com.example.springboot1.entity.client.MmdectionResult;
import com.example.springboot1.service.ISysImageService;
import com.example.springboot1.service.ISysPartService;
import com.example.springboot1.service.ISysSignService;
import com.example.springboot1.utils.HttpUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 检测图片的控制类
 * 已检测图片的查询接口
 */
@RestController
@RequestMapping("/api/image")
public class SysImageController extends BaseController {

    @Autowired
    private ISysImageService imageService;

    @Autowired
    private LocalConfig localConfig;

    @Autowired
    private ISysPartService partService;

    @Autowired
    private ISysSignService signService;


    @GetMapping("/list")
    @ApiOperation("图片信息查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sign", value = "签名信息", defaultValue = "null",required = true),
            @ApiImplicitParam(name = "partId", value = "零件编号", defaultValue = "null"),
            @ApiImplicitParam(name = "imageName", value = "图片名称", defaultValue = "null"),
            @ApiImplicitParam(name = "partTypeName", value = "零件类型", defaultValue = "null"),
            @ApiImplicitParam(name = "params[beginTime]", value = "检测时间范围", defaultValue = "null"),
            @ApiImplicitParam(name = "params[endTime]", value = "检测时间范围", defaultValue = "null")

    })
    public TableDataInfo list(SysImage sysImage)
    {
        // 先检查零件的鉴权信息
        if (!signService.checkSign(sysImage.getSign())){
            return TableDataInfo.error(HttpStatus.UNAUTHORIZED, "鉴权失败，请检查sign是否过期");
        }
        startPage();
        List<SysImage> list = imageService.selectImageList(sysImage);

        // 如果没有查询到数据
        if (list.isEmpty()){
            return TableDataInfo.error(HttpStatus.NO_CONTENT, "没有查询到数据");
        }
        return  getDataTable(list);
    }

    @PostMapping
    public TableDataInfo add(@Validated @RequestBody ImageData imageData)
    {

        if (!signService.checkSign(imageData.getSign()))
        {
            return TableDataInfo.error(HttpStatus.UNAUTHORIZED, "鉴权信息sign:'" + imageData.getSign() + "'错误，鉴权签名未通过");
        }

        // 获取零件的Id，并检查零件信息是否存在
        Long partId = partService.selectPartIdByName(imageData.getPartName());
        if (partId == null || partId == 0)
        {
            return TableDataInfo.error(HttpStatus.NOT_FOUND, "零件'" + imageData.getPartName() + "'不存在，请先注册零件批次或者零件信息");
        }

        // 获取图片的名称，并检查图片信息是否存在
        SysImage image = new SysImage();
        String imageName = imageData.getPartName() + imageData.getImageName();

        image.setPartId(partId);
        image.setImageName(imageName);

        if (!imageService.checkImageNameUnique(image)){
            return TableDataInfo.error(HttpStatus.CONFLICT, "图片'" + image.getImageName() + "'已存在，请勿重复添加");
        }

        // 上传图片
        MultipartFile fileOriginal = HttpUtils.base64ToMultipartFile(imageName, imageData.getImageBase64());
        Map<String,String> mapOriginal = imageService.uploadImage(fileOriginal, "original");

        image.setOriginalImageUrl(mapOriginal.get("imageUrl"));
        image.setOriginalImageName(mapOriginal.get("imageRename"));

        String dectionResult = HttpUtils.pythonPost(localConfig.getPythonurl(),imageName, imageData.getImageBase64());   // 将上传的到的图片post到python接口
        MmdectionResult mmdectionResult = HttpUtils.postResultToEntity(dectionResult);                                  // 得到python接口返回的检测结果

        MultipartFile fileResult = HttpUtils.base64ToMultipartFile(imageName,mmdectionResult.getImageResultBase64());   // 将获得的base64转化成file文件
        Map<String,String> mapResult = imageService.uploadImage(fileResult,"result");                       // 获得检测完图片的Url,保存在文件夹的/result

        image.setResultImageUrl(mapResult.get("imageUrl"));
        image.setResultImageName(mapResult.get("imageRename"));

        // 批量增加零件图片与缺陷类型信息 || 增加零件图片正常信息
        List<String> defectTypeList = mmdectionResult.getDetect_type();
        String[] defectTypeArray = defectTypeList.toArray(new String[defectTypeList.size()]);
        image.setDefectTypeNames(defectTypeArray);

        imageService.insertImage(image);

        startPage();
        List<SysImage> list = imageService.selectImageList(image);

        return getDataTable(list);
    }


    @DeleteMapping
    public AjaxResult delete(SysImage image)
    {
        // 进行签名鉴权操作
        if (!signService.checkSign(image.getSign()))
        {
            return error("鉴权信息sign:'" + image.getSign() + "'错误，鉴权签名未通过");
        }

        return toAjax(imageService.deleteImage(image));
    }




}
