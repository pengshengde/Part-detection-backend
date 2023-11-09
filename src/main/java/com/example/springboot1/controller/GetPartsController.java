package com.example.springboot1.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot1.Entity.Image;
import com.example.springboot1.Entity.Parts;
import com.example.springboot1.common.Result;
import com.example.springboot1.service.Impl.GetAppSecretServiceImpl;
import com.example.springboot1.service.Impl.PartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parts")
public class GetPartsController {


    @Autowired
    PartServiceImpl partServiceImpl;

    @Autowired
    GetAppSecretServiceImpl getAppSecretServiceImpl;

    @GetMapping
    public Result findPage(@RequestParam String sign,
                           @RequestParam Integer page_number,
                           @RequestParam Integer page_size,
                           @RequestParam (defaultValue = "") String part_id,
                           @RequestParam (defaultValue = "") String defect_type,
                           @RequestParam (defaultValue = "") String detect_time){

        if (getAppSecretServiceImpl.existsSign(sign)){
            QueryWrapper<Parts> queryWrapper =new QueryWrapper<>();
            if (!"".equals(part_id)) {
                queryWrapper.like("part_id", part_id);
            }
            if (!"".equals(defect_type)) {
                queryWrapper.like("defect_type", defect_type);
            }
            if (!"".equals(detect_time)) {
                queryWrapper.like("detect_time", detect_time);
            }
            Page<Parts> page = partServiceImpl.page(new Page<>(page_number,page_size), queryWrapper);

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

//    @PostMapping
//    public Result addPart(@RequestParam String part_id,
//                          @RequestParam List<String> defect_type){
//        Parts parts = new Parts();
//        parts.setPart_id(part_id);
//        parts.setDefect_type(defect_type);
//        partServiceImpl.save(parts);
//
//        return  Result.success();
//    }

    @PostMapping
    public Result addPart(@RequestBody Parts parts){

        partServiceImpl.save(parts);
        return  Result.success("已成功保存零件信息",null);
    }

    @GetMapping("/{part_id}")
    public Result getOnePart(@PathVariable String part_id,
                             @RequestParam String sign){

        Parts parts;
        List<Parts> getParts = new ArrayList<>();

        if (getAppSecretServiceImpl.existsSign(sign)){
            parts = partServiceImpl.getById(part_id);
            if (!(parts.getPart_id()==null)){
                getParts.add(parts);
                return Result.successGetData(getParts);
            }else {
                return Result.errorGetData();
            }
        }else {
            return Result.errorSignJudge();
        }
    }

    @DeleteMapping ("/{part_id}")
    public Result deleteImage(@RequestParam String sign,
                              @PathVariable String part_id,
                              @RequestParam Integer page_number,
                              @RequestParam Integer page_size
                              ){

        QueryWrapper<Parts> queryWrapper =new QueryWrapper<>();
        if (!"".equals(part_id)) {
            queryWrapper.eq("part_id", part_id);
        }


        if (getAppSecretServiceImpl.existsSign(sign)){
            boolean delete = partServiceImpl.remove(queryWrapper);
            if (delete){
                List<Page> getDeleteImageResult = new ArrayList<>();
                Page<Parts> page = partServiceImpl.page(new Page<>(page_number,page_size));
                getDeleteImageResult.add(page);

                return Result.successDelete(getDeleteImageResult);
            }else {
                return Result.errorDelete();
            }
        }else {
            return Result.errorSignJudge();
        }
    }



}