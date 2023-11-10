package com.example.springboot1.controller;

import com.alibaba.fastjson.JSON;
import com.example.springboot1.Entity.GetAppSecret;
import com.example.springboot1.common.Result;
import com.example.springboot1.common.SignUtil;
import com.example.springboot1.service.Impl.GetAppSecretServiceImpl;
import com.example.springboot1.service.Impl.GetAppSignServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.example.springboot1.common.SignUtil.generateUUID;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/register")
public class GetAppSecretController{
    @Autowired
    private GetAppSecretServiceImpl getAppSecretServiceImpl;

    @Autowired
    private GetAppSignServiceImpl getAppSignServiceImpl;

    private String appId;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "注册ID")
    })
    @ApiOperation(value = "权限注册",notes = "获取appId，返回appSecret")
    @GetMapping("/appSecret")
    public Result getSecret(@RequestParam String appId) throws JsonProcessingException {

        this.appId = appId;

        boolean IsExists = getAppSecretServiceImpl.existsAppId(appId);

        List<GetAppSecret> getAppSecretList = new ArrayList<>();
        String jsonString = null;

        if (IsExists){
            getAppSecretList.add(getAppSecretServiceImpl.getSecret(appId));
        }else {
            // 生成uuid
            String appSecret = generateUUID();
            getAppSecretServiceImpl.saveAppSecret(appId,appSecret);
            getAppSecretList.add(getAppSecretServiceImpl.getSecret(appId));
        }

       /* try {
            // 创建 ObjectMapper 对象
            ObjectMapper mapper = new ObjectMapper();

            // 将对象转换为 JSON 字符串
            jsonString = mapper.writeValueAsString(getAppSecretList);

            System.out.println(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }*/

        return Result.successGetAppSecret(getAppSecretList);

    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "注册ID"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳"),
            @ApiImplicitParam(name = "sign", value = "签名，由appId、timestamp、appSecret生成")

    })
    @ApiOperation(value = "权限鉴定")
    @GetMapping("/judgeSign")
    public Result getSign(@RequestParam("appId") String appId, @RequestParam("timestamp")String timestamp,
                           @RequestParam("sign") String sign){

        if (!sign.isEmpty()){
            String appSecret = getAppSecretServiceImpl.getSecret(appId).getAppSecret();

            //1.校验时间戳
            long requestTime = Long.valueOf(timestamp);
            /*        long requestTime = new Date().getTime();*/
            System.out.println(requestTime);
            System.out.println(timestamp);
            System.out.println(sign);
            // 时间查过20秒，则认为接口为重复调用，返回错误信息
            long nowTime = new Date().getTime();
            int seconds = (int) ((nowTime - requestTime)/1000);
            if(Math.abs(seconds) > 86400) {
                return Result.errorSignTimeOut();     // 返回访问过期
            }else {
                // 2.生成签名
                SortedMap<String, String> sortedMap = new TreeMap<>();
                sortedMap.put("appId", appId);
                sortedMap.put("timestamp", timestamp);
                System.out.println("签名："+ SignUtil.createSign(sortedMap, appSecret));

                //3.组装参数，
                SortedMap<String, String> sortedMap12 = new TreeMap<>();
                sortedMap12.put("appId", appId);
                sortedMap12.put("timestamp", timestamp);
                sortedMap12.put("sign", sign);

                //4.校验签名
                Boolean flag = SignUtil.isCorrectSign(sortedMap12, appSecret);
                if(flag){
                    System.out.println("签名验证通过");
                    getAppSignServiceImpl.saveSign(appId,timestamp,appSecret,sign);
                    getAppSignServiceImpl.updateSign(appId,sign);
                    return Result.successSignJudge();
                }else {
                    System.out.println("签名验证未通过");
                    return Result.errorSignJudge();
                }
            }
        }else {
            return Result.errorSignIsnull();
        }
    }
}