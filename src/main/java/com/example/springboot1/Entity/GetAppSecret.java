package com.example.springboot1.Entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;


@Data
@TableName("sys_AppSecret")
@AllArgsConstructor
@NoArgsConstructor
public class GetAppSecret {
    private String AppSecret;

    @Override
    public String toString() {
        return AppSecret;
    }

    public String getAppSecret() {
        return AppSecret;
    }

/*
    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getAppSecret() {
        return AppSecret;
    }

    public void setAppSecret(String AppSecret) {
        this.AppSecret = AppSecret;
    }

    @Override
    public String toString() {
        return "GetAppSecret{" +
                "AppSecret='" + AppSecret + '\'' +
                ", AppId=" + AppId +
                '}';
    }*/
}
