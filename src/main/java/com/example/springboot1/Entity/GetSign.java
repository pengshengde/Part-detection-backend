package com.example.springboot1.Entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/*
 *  该类用于存放软件的注册信息，获取appid，时间戳和签名
 * */
@Data
public class GetSign {

    private String appid;

    private String timestamp;

    private String sign;

    @Override
    public String toString() {
        return "GetSign{" +
                "appid='" + appid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
