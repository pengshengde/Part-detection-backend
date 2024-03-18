package com.example.springboot1.service.Impl;

import com.example.springboot1.entity.client.GetAppSecret;
import com.example.springboot1.mapper.GetAppSecretMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GetAppSecretServiceImpl implements GetAppSecretMapper{

    @Autowired
    private GetAppSecretMapper getAppSecretMapper;

    @Override
    public GetAppSecret getSecret(String appId) {
        return getAppSecretMapper.getSecret(appId);
    }

    @Override
    public int saveAppSecret(String appId,String appSecret){
        return getAppSecretMapper.saveAppSecret(appId,appSecret);
    }

    @Override
    public  boolean existsAppId(String appId){
        return getAppSecretMapper.existsAppId(appId);
    }

    @Override
    public boolean existsSign(String sign){
        return getAppSecretMapper.existsSign(sign);
    }

}
