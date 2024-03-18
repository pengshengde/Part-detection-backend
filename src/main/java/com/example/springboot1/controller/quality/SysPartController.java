package com.example.springboot1.controller.quality;

import com.example.springboot1.common.browser.annotation.Log;
import com.example.springboot1.common.browser.core.controller.BaseController;
import com.example.springboot1.common.browser.core.domain.AjaxResult;
import com.example.springboot1.common.browser.core.page.TableDataInfo;
import com.example.springboot1.common.browser.enums.BusinessType;
import com.example.springboot1.common.browser.utils.StringUtils;
import com.example.springboot1.common.browser.utils.poi.ExcelUtil;
import com.example.springboot1.entity.browser.quality.SysDefectType;
import com.example.springboot1.entity.browser.quality.SysImage;
import com.example.springboot1.entity.browser.quality.SysPart;
import com.example.springboot1.entity.browser.quality.SysPartImage;
import com.example.springboot1.service.ISysImageService;
import com.example.springboot1.service.ISysPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 零件的控制层
 */
@RestController
@RequestMapping("/quality/part")
public class SysPartController extends BaseController {

    @Autowired
    private ISysPartService partService;

    @Autowired
    private ISysImageService imageService;

    /**
     * 查询零件列表
     */
    @PreAuthorize("@ss.hasPermi('quality:part:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysPart part)
    {
        startPage();
        List<SysPart> list = partService.selectPartIds(part);
        return getDataTable(list);
    }


    /**
     * 导出所有零件的信息
     */
    @Log(title = "零件", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('quality:part:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysPart part)
    {
        List<SysPart> list = partService.selectPartList(part);
        ExcelUtil<SysPart> util = new ExcelUtil<SysPart>(SysPart.class);
        util.exportExcel(response, list, "零件数据");
    }

    /**
     * 根据零件的Id查询零件的详细信息
     */
    @PreAuthorize("@ss.hasPermi('quality:part:query')")
    @GetMapping("/{partId}")
    public AjaxResult getInfo(@PathVariable Long partId)
    {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(partId))
        {
            SysPart part = partService.selectPartById(partId);
            ajax.put(AjaxResult.DATA_TAG, part);
            ajax.put("imageIds", part.getImages().stream().map(SysImage::getImageId).collect(Collectors.toList()));
        }
        return ajax;
    }

    /**
     * 修改零件的信息
     */
    @PreAuthorize("@ss.hasPermi('quality:part:edit')")
    @Log(title = "零件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysPart part)
    {
        // 先检查零件的信息是否唯一
        if (!partService.checkPartNameUnique(part))
        {
            return error("新增零件’" +  part.getPartName() + "‘失败，零件名称已存在");
        }
        part.setUpdateBy(getUsername());
        return toAjax(partService.updatePart(part));
    }

    /**
     * 零件的检测结果修改
     */
    @PutMapping("/changeResult")
    public AjaxResult changeResult(@RequestBody SysPart part)
    {
        part.setUpdateBy(getUsername());
        return toAjax(partService.updatePart(part));
    }

    /**
     * 删除零件信息
     */
    @PreAuthorize("@ss.hasPermi('quality:part:remove')")
    @Log(title = "零件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{partIds}")
    public AjaxResult remove(@PathVariable Long[] partIds)
    {
        return toAjax(partService.deletePartByIds(partIds));
    }

    /**
     * 查询某零件的所有图片
     */
    @PreAuthorize("@ss.hasPermi('quality:part:list')")
    @GetMapping("/authImage/allocatedList")
    public TableDataInfo allocatedList(SysImage image)
    {
        startPage();
        List<SysImage> list = imageService.selectAllocatedImageList(image);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('quality:part:list')")
    @GetMapping("/authImage/unallocatedList")
    public TableDataInfo unallocatedList(SysImage image)
    {
        startPage();
        List<SysImage> list = imageService.selectUnallocatedImageList(image);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('quality:part:edit')")
    @Log(title = "分配图片", businessType = BusinessType.GRANT)
    @PutMapping("/authImage/selectAll")
    public AjaxResult selectAuthImageAll(Long partId, Long[] imageIds)
    {
        return toAjax(partService.insertAuthImages(partId, imageIds));
    }

    @PreAuthorize("@ss.hasPermi('quality:part:edit')")
    @Log(title = "分配图片", businessType = BusinessType.GRANT)
    @PutMapping("/authImage/cancel")
    public AjaxResult cancelAuthImage(SysPartImage partImage)
    {
        return toAjax(partService.deleteAuthImage(partImage));
    }

    @PreAuthorize("@ss.hasPermi('quality:part:edit')")
    @Log(title = "分配图片", businessType = BusinessType.GRANT)
    @PutMapping("/authImage/cancelAll")
    public AjaxResult cancelAuthImageAll(Long partId, Long[] imageIds)
    {
        return toAjax(partService.deleteAuthImages(partId, imageIds));
    }

    @Log(title = "图片导出", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('quality:part:export')")
    @PostMapping("/authImage/export")
    public void exportAuthImage(HttpServletResponse response, SysImage image){
        List<SysImage> list = imageService.selectAllocatedImageList(image);
        ExcelUtil<SysImage> util = new ExcelUtil<SysImage>(SysImage.class);
        util.exportExcel(response, list, "零件图片列表");
    }

    @PreAuthorize("@ss.hasPermi('quality:part:query')")
    @GetMapping("/authImage/{imageId}")
    public AjaxResult getAuthImage(@PathVariable Long imageId)
    {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(imageId))
        {
            SysImage image = imageService.selectImageById(imageId);
            ajax.put(AjaxResult.DATA_TAG, image);
        }
        return ajax;
    }




}

/*==>  Preparing: SELECT count(0) FROM sys_part_data p INNER JOIN sys_part_type pt ON p.part_type_id = pt.part_type_id LEFT JOIN sys_part_image pi ON p.part_id = pi.part_id LEFT JOIN sys_image i ON i.image_id = pi.image_id LEFT JOIN sys_part_defect_type pdt ON p.part_id = pdt.part_id LEFT JOIN sys_defect_type dt ON dt.defect_type_id = pdt.defect_type_id WHERE p.del_flag = '0' AND p.detect_status = '1'
 ==>  Preparing: SELECT count(0) FROM sys_part_data p INNER JOIN sys_part_type pt ON p.part_type_id = pt.part_type_id LEFT JOIN sys_part_image pi ON p.part_id = pi.part_id LEFT JOIN sys_image i ON i.image_id = pi.image_id LEFT JOIN sys_part_defect_type pdt ON p.part_id = pdt.part_id LEFT JOIN sys_defect_type dt ON dt.defect_type_id = pdt.defect_type_id WHERE p.del_flag = '0' AND p.detect_status = '1'

 * */
