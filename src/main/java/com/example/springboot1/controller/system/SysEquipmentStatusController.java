package com.example.springboot1.controller.system;

import com.example.springboot1.common.browser.core.controller.BaseController;
import com.example.springboot1.common.browser.core.domain.AjaxResult;
import com.example.springboot1.common.browser.core.page.TableDataInfo;
import com.example.springboot1.common.browser.utils.DateUtils;
import com.example.springboot1.entity.browser.system.equipment.SysEquipment;
import com.example.springboot1.entity.browser.system.equipment.SysEquipmentStatus;
import com.example.springboot1.entity.browser.system.equipment.SysStatus;
import com.example.springboot1.service.ISysEquipmentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *  设备状态 信息操作处理
 */
@RestController
@RequestMapping("/api/sysEquipmentStatus")
public class SysEquipmentStatusController extends BaseController
{
    @Autowired
    private ISysEquipmentStatusService equipmentStatusService;

    @GetMapping("/list")
    public TableDataInfo list(SysEquipmentStatus sysEquipmentStatus)
    {
        startPage();
        List<SysEquipmentStatus> list = equipmentStatusService.selectEquipmentStatusList(sysEquipmentStatus);
        return getDataTable(list);
    }

    @GetMapping("/equipmentList")
    public TableDataInfo equipmentList(SysEquipment sysEquipment)
    {
        startPage();
        List<SysEquipment> list = equipmentStatusService.selectEquipmentList(sysEquipment);
        return getDataTable(list);
    }

    @GetMapping("/statusList")
    public TableDataInfo statusList(SysStatus sysStatus)
    {
        startPage();
        List<SysStatus> list = equipmentStatusService.selectStatusList(sysStatus);
        return getDataTable(list);
    }


    /**
     *  新增保存 设备状态
     */
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysEquipmentStatus sysEquipmentStatus)
    {
        return toAjax(equipmentStatusService.insertEquipmentStatus(sysEquipmentStatus));
    }

    /**
     *  批量删除 设备状态
     */
    @DeleteMapping("/{equipmentStatusIds}")
    public AjaxResult remove(@PathVariable Long[] equipmentStatusIds)
    {
        return toAjax(equipmentStatusService.deleteEquipmentStatusByIds(equipmentStatusIds));
    }

    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysEquipmentStatus sysEquipmentStatus)
    {
        return toAjax(equipmentStatusService.updateEquipmentStatus(sysEquipmentStatus));
    }

}
