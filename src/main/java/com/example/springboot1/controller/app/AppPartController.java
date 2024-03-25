package com.example.springboot1.controller.app;

import com.example.springboot1.common.browser.constant.HttpStatus;
import com.example.springboot1.common.browser.core.controller.BaseController;
import com.example.springboot1.common.browser.core.domain.AjaxResult;
import com.example.springboot1.common.browser.core.page.TableDataInfo;
import com.example.springboot1.entity.browser.quality.SysDefectType;
import com.example.springboot1.entity.browser.quality.SysPart;
import com.example.springboot1.entity.browser.quality.SysSign;
import com.example.springboot1.service.ISysSignService;

import com.example.springboot1.service.ISysPartService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;

/**
 *  零件的Controller
 *  零件的查询接口
 */

@RestController
@RequestMapping("/app/part")
public class AppPartController extends BaseController
{
    @Autowired
    private ISysPartService partService;

    @Autowired
    private ISysSignService signService;


    @GetMapping("/list")
    @ApiOperation(value = "零件查询接口", notes = "根据传入信息返回零件信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sign", value = "签名信息", defaultValue = "null",required = true),
            @ApiImplicitParam(name = "partName", value = "零件名称", defaultValue = "null"),
            @ApiImplicitParam(name = "partName", value = "零件名称", defaultValue = "null"),
            @ApiImplicitParam(name = "partTypeName", value = "零件类型", defaultValue = "null"),
            @ApiImplicitParam(name = "detectResult", value = "检测结果", defaultValue = "null"),
            @ApiImplicitParam(name = "params[beginTime]", value = "检测时间范围", defaultValue = "null"),
            @ApiImplicitParam(name = "params[endTime]", value = "检测时间范围", defaultValue = "null")

    })

    public TableDataInfo list(SysPart sysPart)
    {
        if (!signService.checkSign(sysPart.getSign())){
            return TableDataInfo.error(HttpStatus.UNAUTHORIZED, "鉴权失败，请检查sign是否过期");
        }
        // 查询新增时间
        startPage();
        List<SysPart> list = partService.selectPartIds(sysPart);

        if (list.isEmpty()){
            return TableDataInfo.error(HttpStatus.NO_CONTENT, "没有查询到数据");
        }
        return getDataTable(list);
    }

    /**
     * 新增零件信息
     * @param sysPart
     * @return
     */
    @ApiOperation(value = "零件新增接口", notes = "新增零件信息")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysPart sysPart)
    {

        // 先检查零件名称是否唯一
        if (!partService.checkPartNameUnique(sysPart))
        {
            return error("新增零件'" + sysPart.getPartName() + "'失败，零件名称已存在");
        }

        return toAjax(partService.insertPart(sysPart));
    }

    /**
     * 删除零件
     * @param part
     * @return
     */
    @ApiOperation(value = "零件删除接口", notes = "根据零件的Id删除零件信息")
    @DeleteMapping
    public AjaxResult delete(@Validated SysPart part)
    {
        // 进行签名鉴权操作
        if (!signService.checkSign(part.getSign()))
        {
            return error("鉴权信息sign:'" + part.getSign() + "'错误，鉴权签名未通过");
        }

        // 删除零件的相关信息
        return toAjax(partService.deletePart(part));
    }
}
