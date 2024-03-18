package com.example.springboot1.controller.quality;

import com.example.springboot1.common.browser.annotation.Log;
import com.example.springboot1.common.browser.core.controller.BaseController;
import com.example.springboot1.common.browser.core.domain.AjaxResult;
import com.example.springboot1.common.browser.core.page.TableDataInfo;
import com.example.springboot1.common.browser.enums.BusinessType;
import com.example.springboot1.common.browser.utils.poi.ExcelUtil;
import com.example.springboot1.entity.browser.quality.SysDefectType;
import com.example.springboot1.service.ISysDefectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 缺陷信息
 */
@RestController
@RequestMapping("/quality/defect")
public class SysDefectTypeController extends BaseController
{
    @Autowired
    private ISysDefectTypeService defectTypeService;


    /**
     * 查询零件缺陷信息
     * @param defectType
     * @return
     */
    @PreAuthorize("@ss.hasPermi('quatity:defect:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDefectType defectType)
    {
        startPage();
        List<SysDefectType> list = defectTypeService.selectDefectTypeList(defectType);
        return getDataTable(list);
    }

    /**
     * 导出零件缺陷信息
     * @param response
     * @param defectType
     */
    @Log(title = "零件缺陷类型", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('quatity:defect:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDefectType defectType)
    {
        List<SysDefectType> list = defectTypeService.selectDefectTypeList(defectType);
        ExcelUtil<SysDefectType> util = new ExcelUtil<SysDefectType>(SysDefectType.class);
        util.exportExcel(response, list, "缺陷信息数据");
    }

    /**
     * 根据缺陷类型ID获取详细信息
     * @param defectTypeId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('quatity:defect:query')")
    @GetMapping(value = "/{defectTypeId}")
    public AjaxResult getInfo(@PathVariable Long defectTypeId)
    {
        return success(defectTypeService.selectDefectTypeById(defectTypeId));
    }


    /**
     * 零件缺陷的新增接口
     * @param defectType
     * @return
     */
    @PreAuthorize("@ss.hasPermi('quatity:defect:add')")
    @Log(title = "零件缺陷类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysDefectType defectType)
    {
        if (!defectTypeService.checkDefectTypeNameUnique(defectType))
        {
            return AjaxResult.error("新增零件缺陷类型'" + defectType.getDefectTypeName() + "'失败，零件缺陷类型名称已存在");
        }
        if (!defectTypeService.checkDefectDescription(defectType))
        {
            return AjaxResult.error("新增零件缺陷描述'" + defectType.getDefectTypeName() + "'失败，零件缺陷描述已存在");
        }
        defectType.setCreateBy(getUsername());
        return toAjax(defectTypeService.insertDefectType(defectType));
    }

    /**
     * 修改零件缺陷类型接口
     * @param defectType
     * @return
     */
    @PreAuthorize("@ss.hasPermi('quatity:defect:edit')")
    @Log(title = "零件缺陷类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDefectType defectType)
    {
        // 检查操作是否被允许
        defectTypeService.checkDefectTypeAllowed(defectType);
        if (!defectTypeService.checkDefectTypeNameUnique(defectType))
        {
            return AjaxResult.error("新增零件缺陷类型'" + defectType.getDefectTypeName() + "'失败，零件缺陷类型名称已存在");
        }
        if (!defectTypeService.checkDefectDescription(defectType))
        {
            return AjaxResult.error("新增零件缺陷描述'" + defectType.getDefectTypeName() + "'失败，零件缺陷描述已存在");
        }
        defectType.setUpdateBy(getUsername());
        return toAjax(defectTypeService.updateDefectType(defectType));
    }

    /**
     * 删除缺陷类型
     * @param defectTypeIds
     * @return
     */
    @PreAuthorize("@ss.hasPermi('system:defectType:remove')")
    @Log(title = "零件缺陷类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{defectTypeIds}")
    public AjaxResult remove(@PathVariable Long[] defectTypeIds)
    {
        return toAjax(defectTypeService.deleteDefectTypeByIds(defectTypeIds));
    }

}
