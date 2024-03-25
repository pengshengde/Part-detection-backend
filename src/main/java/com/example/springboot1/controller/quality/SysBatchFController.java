package com.example.springboot1.controller.quality;

import com.example.springboot1.common.browser.annotation.Log;
import com.example.springboot1.common.browser.core.controller.BaseController;
import com.example.springboot1.common.browser.core.domain.AjaxResult;
import com.example.springboot1.common.browser.core.page.TableDataInfo;
import com.example.springboot1.common.browser.enums.BusinessType;
import com.example.springboot1.common.browser.utils.StringUtils;
import com.example.springboot1.common.browser.utils.poi.ExcelUtil;
import com.example.springboot1.entity.browser.quality.SysBatch;
import com.example.springboot1.entity.browser.quality.SysPart;
import com.example.springboot1.service.ISysBatchService;
import com.example.springboot1.service.ISysPartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 未检测零件批次的控制层
 */
@RestController
@RequestMapping("/quality/batchF")
public class SysBatchFController extends BaseController {

    @Autowired
    private ISysBatchService batchService;

    @Autowired
    private ISysPartService partService;

    /**
     * 查询未检测零件的批次接口
     */
    @ApiOperation(value = "未检测批次查询")
    @PreAuthorize("@ss.hasPermi('quality:batchF:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysBatch batch)
    {
        startPage();
        List<SysBatch> list = batchService.selectBatchUndetectedList(batch);
        return getDataTable(list);
    }

    /**
     * 导出未检测零件批次的信息
     * @param response
     * @param batch
     */
    @ApiOperation(value = "未检测批次导出")
    @Log(title = "未检测零件批次", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('quality:batchF:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysBatch batch)
    {
        List<SysBatch> list = batchService.selectBatchUndetectedList(batch);
        ExcelUtil<SysBatch> util = new ExcelUtil<SysBatch>(SysBatch.class);
        util.exportExcel(response, list, "未检测零件批次的数据");
    }

    /**
     * 根据批次的Id查询批次的详细信息
     * @param batchId
     * @return
     */
    @ApiOperation(value = "未检测批次详细查询")
    @PreAuthorize("@ss.hasPermi('quality:batchF:query')")
    @GetMapping("/{batchId}")
    public AjaxResult getInfo(@PathVariable Long batchId)
    {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(batchId))
        {
            SysBatch batch = batchService.selectBatchById(batchId);
            ajax.put(AjaxResult.DATA_TAG, batch);
            ajax.put("partIds",batch.getParts().stream().map(SysPart::getPartId).collect(Collectors.toList()));
        }
        return ajax;
    }

    /**
     * 未检测零件批次的新增接口
     */
    @ApiOperation(value = "未检测批次新增")
    @PreAuthorize("@ss.hasPermi('quality:batchF:add')")
    @Log(title = "未检测零件批次", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysBatch batch)
    {
        // 先检查批次信息是否唯一
        if (!batchService.checkBatchNameUnique(batch))
        {
            return error("新增零件批次'" + batch.getBatchName() + "'失败，零件批次已存在");
        }
        // 获取批次的零件个数
        Long quantity = batch.getQuantity();
        int rows =  batchService.insertBatch(batch);

        for (int i = 1; i < quantity + 1 ; i++)
        {
            // 创建零件
            SysPart part = new SysPart();
            // 获取零件类型
            part.setPartTypeName(batch.getPartTypeName());
            // 获取零件名称
            part.setPartName(String.format("%s%02d", batch.getBatchName(), i));
            // 检测零件是否唯一
            if (!partService.checkPartNameUnique(part))
            {
                return error("新增零件'" + part.getPartName() + "'失败，零件已存在");
            }
            // 新增零件
            partService.insertPart(part);
        }

        // 新增批次与零件之间的关系
        int row1 = partService.insertBatchPart(batch);

        return toAjax(rows * row1);
    }

    /**
     * 修改零件批次信息
     * @param batch
     * @return
     */
    @ApiOperation(value = "未检测批次修改")
    @PreAuthorize("@ss.hasPermi('quality:batchF:edit')")
    @Log(title = "未检测零件批次", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysBatch batch)
    {
        // 先检查批次信息是否唯一
        if (!batchService.checkBatchNameUnique(batch))
        {
            return error("新增零件批次'" + batch.getBatchName() + "'失败，零件批次已存在");
        }
        batch.setUpdateBy(getUsername());
        return toAjax(batchService.updateBatch(batch));
    }


    @ApiOperation(value = "未检测批次删除")
    @PreAuthorize("@ss.hasPermi('quality:batchF:remove')")
    @Log(title = "未检测零件批次", businessType = BusinessType.DELETE)
    @DeleteMapping("/{batchIds}")
    public AjaxResult remove(@PathVariable Long[] batchIds)
    {
        for (Long batchId : batchIds)
        {
            // 先通过零件批次Id找到零件的Ids
            List<Long> partIds = batchService.selectPartIdsByBatchId(batchId);
            for (Long partId : partIds)
            {
                SysPart part = new SysPart();
                part.setPartId(partId);
                partService.deletePart(part);
            }
        }
        return toAjax(batchService.deleteBatchByIds(batchIds));
    }


}
