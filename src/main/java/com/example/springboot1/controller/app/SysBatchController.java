package com.example.springboot1.controller.app;

import com.example.springboot1.common.browser.core.controller.BaseController;
import com.example.springboot1.common.browser.core.domain.AjaxResult;
import com.example.springboot1.common.browser.core.page.TableDataInfo;
import com.example.springboot1.entity.browser.quality.SysBatch;
import com.example.springboot1.entity.browser.quality.SysPart;
import com.example.springboot1.service.ISysBatchService;
import com.example.springboot1.service.ISysPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  批次的控制类
 *  未检测零件批次的查询接口
 *  已检测零件批次的查询接口
 */
@RestController
@RequestMapping("/api/sysBatch")
public class SysBatchController extends BaseController
{
    @Autowired
    private ISysBatchService batchService;

    @Autowired
    private ISysPartService partService;

    /**
     * 查询未检测零件批次的接口
     */
    @GetMapping("/list1")
    public TableDataInfo list1(SysBatch sysBatch)
    {
        startPage();
        List<SysBatch> list = batchService.selectBatchUndetectedList(sysBatch);
        return getDataTable(list);
    }

    /**
     *  查询已检测零件的批次接口
     */
    @GetMapping("/list2")
    public TableDataInfo list2(SysBatch sysBatch)
    {
        startPage();
        List<SysBatch> list = batchService.selectBatchDetectedList(sysBatch);
        return getDataTable(list);
    }

    /**
     *  新增零件批次
     * @param batch
     * @return
     */
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

    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysBatch batch)
    {
        // 如果批次信息不为空
        if (batch.getBatchName() != null)
        {
            // 先检查批次信息是否唯一
            if (!batchService.checkBatchNameUnique(batch))
            {
                return error("新增零件批次'" + batch.getBatchName() + "'失败，零件批次已存在");
            }
        }

        int rows = batchService.updateBatch(batch);

        return toAjax(rows);
    }

}
