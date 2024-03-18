package com.example.springboot1.controller.app;


import com.example.springboot1.common.browser.core.controller.BaseController;
import com.example.springboot1.common.browser.core.domain.AjaxResult;
import com.example.springboot1.entity.browser.quality.SysSign;
import com.example.springboot1.service.ISysSignService;
import com.example.springboot1.service.Impl.SysSignServiceImpl;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 鉴权注册信息
 */
@RestController
@RequestMapping("/api/sign")
public class SysSignController extends BaseController
{
    @Autowired
    private ISysSignService signService;

    /**
     *  新增鉴权注册信息
     * @param sign
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "注册ID")
    })
    @ApiOperation(value = "权限注册",notes = "获取appId，返回appSecret")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysSign sign)
    {
        if (!signService.checkAppIdUnique(sign))
        {
            return error("新增鉴权注册信息appId:'" + sign.getAppId() + "'失败，appId已存在");
        }

        return toAjax(signService.insertAppId(sign));
    }

    @GetMapping("/register")
    public AjaxResult register(@Validated SysSign sign)
    {
        if (!signService.checkAppIdExists(sign))
        {
            return error("鉴权信息appId:'" + sign.getAppId() + "'不存在，请输入正确的appId");
        }
        String appSecret = signService.updateAppSecret(sign);

        return AjaxResult.success("注册成功",appSecret);
    }


    @GetMapping("/judge")
    public AjaxResult judge(@Validated SysSign sysSign)
    {
        if (sysSign.getAppId() == null || sysSign.getTimestamp() == null || sysSign.getSign() == null )
        {
            return error("鉴权信息缺失，appId:‘"  + sysSign.getAppId() + "'timestamp:'" + sysSign.getTimestamp() + "'sign:'" + sysSign.getSign() + "'");
        }

        if (!signService.checkAppIdExists(sysSign))
        {
            return error("鉴权信息appId'" + sysSign.getAppId() + "'不存在，请输入正确的appId");
        }

/*        if (!signService.checkTimestamp(sysSign))
        {
            return error("注册时间已过期，请重新进行注册");
        }*/

        if (!signService.judgeSign(sysSign))
        {
            return error("鉴权信息sign:'" + sysSign.getSign() + "'错误，鉴权签名未通过");
        }

        return toAjax(signService.updateSign(sysSign));
    }


    @GetMapping("/check")
    public AjaxResult check(@Validated @RequestBody SysSign sysSign)
    {
        if (sysSign.getSign() == null || sysSign.getSign().length() == 0)
        {
            return error("鉴权信息sign:'" + sysSign.getSign() + "'错误，鉴权签名未通过");
        }

        if (!signService.checkSign(sysSign))
        {
            return error("鉴权信息sign:'" + sysSign.getSign() + "'错误，鉴权签名未通过");
        }

        return AjaxResult.success();
    }

}
