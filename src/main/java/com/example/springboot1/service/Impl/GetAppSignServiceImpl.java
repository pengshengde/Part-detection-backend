package com.example.springboot1.service.Impl;


import com.example.springboot1.Entity.GetAppSign;
import com.example.springboot1.mapper.GetAppSignMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetAppSignServiceImpl implements GetAppSignMapper {

    @Autowired
    private GetAppSignMapper getAppSignMapper;

    @Override
    public int saveSign(String appId, String timestamp, String appSecret, String sign) {
        return getAppSignMapper.saveSign(appId,timestamp,appSecret,sign);
    }

    @Override
    public boolean updateSign(String appId, String sign){
        return getAppSignMapper.updateSign(appId,sign);
    }
}
