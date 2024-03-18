package com.example.springboot1.controller.quality;

import com.example.springboot1.common.browser.annotation.Log;
import com.example.springboot1.common.browser.core.controller.BaseController;
import com.example.springboot1.common.browser.core.domain.AjaxResult;
import com.example.springboot1.common.browser.core.page.TableDataInfo;
import com.example.springboot1.common.browser.enums.BusinessType;
import com.example.springboot1.common.browser.utils.poi.ExcelUtil;
import com.example.springboot1.entity.browser.quality.SysDefectType;
import com.example.springboot1.entity.browser.quality.SysSign;
import com.example.springboot1.service.ISysSignService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/quality/sign")
public class SysAppIdController extends BaseController
{
    @Autowired
    private ISysSignService signService;

    /**
     * 获取鉴权信息列表
     * @param sign
     * @return
     */
    @PreAuthorize("@ss.hasPermi('quality:sign:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysSign sign)
    {
        startPage();
        List<SysSign> list = signService.selectSignList(sign);
        return getDataTable(list);
    }

    /**
     * 导出AppId等信息
     * @param response
     * @param sign
     */
    @Log(title = "鉴权注册信息", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('quatity:sign:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysSign sign)
    {
        List<SysSign> list = signService.selectSignList(sign);
        ExcelUtil<SysSign> util = new ExcelUtil<SysSign>(SysSign.class);
        util.exportExcel(response, list, "鉴权注册信息");
    }

    /**
     *  新增鉴权注册信息
     * @param sign
     * @return
     */
    @PreAuthorize("@ss.hasPermi('quality:sign:add')")
    @Log(title = "鉴权注册信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysSign sign)
    {
        if (!signService.checkAppIdUnique(sign))
        {
            return error("新增鉴权注册信息appId:'" + sign.getAppId() + "'失败，appId已存在");
        }

        return toAjax(signService.insertAppId(sign));
    }

    /**
     * 更新鉴权注册信息
     * @param sign
     * @return
     */
    @PreAuthorize("@ss.hasPermi('quality:sign:edit')")
    @Log(title = "鉴权注册信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysSign sign)
    {
        if (!signService.checkAppIdUnique(sign))
        {
            return error("新增鉴权注册信息appId:'" + sign.getAppId() + "'失败，appId已存在");
        }
        sign.setUpdateBy(getUsername());
        return toAjax(signService.updateAppId(sign));
    }

    /**
     * 获取具体的鉴权注册信息
     * @param signId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('quality:sign:query')")
    @GetMapping(value = "/{signId}")
    public AjaxResult getInfo(@PathVariable Long signId)
    {
        return success(signService.selectAppIdById(signId));
    }

    /**
     * 删除鉴权注册信息
     * @param signIds
     * @return
     */
    @PreAuthorize("@ss.hasPermi('quality:sign:remove')")
    @Log(title = "鉴权注册信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{signIds}")
    public AjaxResult remove(@PathVariable Long[] signIds)
    {
        for (Long signId : signIds)
        {
            if (signService.checkSignIsUsing(signId))
            {
                return  warn("删除鉴权注册信息失败, 该签名正在被使用");
            }
        }

        return toAjax(signService.deleteSignByIds(signIds));
    }

}
