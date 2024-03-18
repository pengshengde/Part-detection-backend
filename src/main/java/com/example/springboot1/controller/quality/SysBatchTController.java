package com.example.springboot1.controller.quality;

import com.example.springboot1.common.browser.annotation.Log;
import com.example.springboot1.common.browser.core.controller.BaseController;
import com.example.springboot1.common.browser.core.domain.AjaxResult;
import com.example.springboot1.common.browser.core.page.TableDataInfo;
import com.example.springboot1.common.browser.enums.BusinessType;
import com.example.springboot1.common.browser.utils.StringUtils;
import com.example.springboot1.common.browser.utils.poi.ExcelUtil;
import com.example.springboot1.entity.browser.common.SysRole;
import com.example.springboot1.entity.browser.quality.SysBatch;
import com.example.springboot1.entity.browser.quality.SysBatchPart;
import com.example.springboot1.entity.browser.quality.SysPart;
import com.example.springboot1.service.ISysBatchService;
import com.example.springboot1.service.ISysPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 已检测零件批次的控制层
 */
@RestController
@RequestMapping("/quality/batchT")
public class SysBatchTController extends BaseController {

    @Autowired
    private ISysBatchService batchService;

    @Autowired
    private ISysPartService partService;

    /**
     *  查询已检测零件的批次接口
     */
    @PreAuthorize("@ss.hasPermi('quality:batchT:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysBatch sysBatch)
    {
        startPage();
        List<SysBatch> list = batchService.selectBatchDetectedList(sysBatch);
        return getDataTable(list);
    }

    /**
     * 导出已检测零件批次的信息
     * @param response
     * @param batch
     */
    @Log(title = "已检测零件批次", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('quality:batchT:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysBatch batch)
    {
        List<SysBatch> list = batchService.selectBatchDetectedList(batch);
        ExcelUtil<SysBatch> util = new ExcelUtil<SysBatch>(SysBatch.class);
        util.exportExcel(response, list, "已检测零件批次的列表");
    }

    /**
     * 根据批次的Id查询批次的详细信息
     * @param batchId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('quality:batchT:query')")
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
     * 修改零件批次信息
     * @param batch
     * @return
     */
    @PreAuthorize("@ss.hasPermi('quality:batchT:edit')")
    @Log(title = "已检测零件批次", businessType = BusinessType.UPDATE)
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

    @PreAuthorize("@ss.hasPermi('quality:batchT:remove')")
    @Log(title = "已检测零件批次", businessType = BusinessType.DELETE)
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

    /**
     * 查询某零件批次的所有零件信息
     * @param part
     * @return
     */
    @PreAuthorize("@ss.hasPermi('quality:batchT:list')")
    @GetMapping("/authPart/allocatedList")
    public TableDataInfo allocatedList(SysPart part)
    {
        startPage();
        List<SysPart> list = partService.selectAllocatedPartList(part);
        return getDataTable(list);
    }

    /**
     * 查询未被分配的所有零件信息
     */
    @PreAuthorize("@ss.hasPermi('quality:batchT:list')")
    @GetMapping("/authPart/unallocatedList")
    public TableDataInfo unallocatedList(SysPart part)
    {
        startPage();
        List<SysPart> list = partService.selectUnallocatedPartList(part);
        return getDataTable(list);
    }

    /**
     * 导出已检测零件批次的信息
     * @param response
     * @param part
     */
    @Log(title = "零件导出", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('quality:batchT:export')")
    @PostMapping("/authPart/export")
    public void exportAuthPart(HttpServletResponse response, SysPart part)
    {
        List<SysPart> list = partService.selectAllocatedPartList(part);
        ExcelUtil<SysPart> util = new ExcelUtil<SysPart>(SysPart.class);
        util.exportExcel(response, list, "批次的零件列表");
    }

    /**
     * 批量选择零件
     * @param batchId
     * @param partIds
     * @return
     */
    @PreAuthorize("@ss.hasPermi('quality:batchT:edit')")
    @Log(title = "分配零件", businessType = BusinessType.GRANT)
    @PutMapping("/authPart/selectAll")
    public AjaxResult selectAuthPartAll(Long batchId, Long[] partIds)
    {
        return toAjax(batchService.insertAuthParts(batchId, partIds));
    }

    /**
     * 取消零件
     * @param batchPart
     * @return
     */
    @PreAuthorize("@ss.hasPermi('quality:batchT:edit')")
    @Log(title = "分配零件", businessType = BusinessType.GRANT)
    @PutMapping("/authPart/cancel")
    public AjaxResult cancelAuthPart(SysBatchPart batchPart)
    {
        return toAjax(batchService.deleteAuthPart(batchPart));
    }

    /**
     * 批量取消零件
     * @param batchId
     * @param partIds
     * @return
     */
    @PreAuthorize("@ss.hasPermi('quality:batchT:edit')")
    @Log(title = "分配零件", businessType = BusinessType.GRANT)
    @PutMapping("/authPart/cancelAll")
    public AjaxResult cancelAuthPartAll(Long batchId, Long[] partIds)
    {
        return toAjax(batchService.deleteAuthParts(batchId, partIds));
    }




}
