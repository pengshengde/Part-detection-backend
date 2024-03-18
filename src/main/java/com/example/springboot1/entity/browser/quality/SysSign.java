package com.example.springboot1.entity.browser.quality;

import com.example.springboot1.common.browser.annotation.Excel;
import com.example.springboot1.common.browser.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SysSign extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 签名主键 */
    @Excel(name = "签名序号", cellType = Excel.ColumnType.NUMERIC)
    private Long signId;


    /** 使用签名的设备Id */
    private Long devId;

    @Excel(name = "使用签名的设备名称")
    /** 使用签名的设备名称 */
    private String devName;

    @Excel(name = "AppId")
    /** 提前定义好的appId, 一般由服务器提前定义 */
    private String appId;

    /** 鉴权注册时服务器生成的UUID */
    private String appSecret;

    @Excel(name = "签名")
    /** 鉴权要用到的签名 */
    private String sign;

    @Excel(name = "签名使用次数")
    /** 使用签名的数量 */
    private Integer signUsageCount;

    @Excel(name = "签名过期时间")
    /** 表示签名过期的时间 */
    private String signExpireTime;

    /** 上次使用appId注册的时间 */
    private Long appIdLastUsedTime;

    @Excel(name = "使用签名的IP地址")
    /** 鉴权注册的IP地址 */
    private String ipaddr;

    @Excel(name = "使用签名的登录地点")
    /** 鉴权注册的登录地点 */
    private String loginLocation;

    /** 鉴权注册的时间戳 */
    private String timestamp;


    public Long getSignId() {
        return signId;
    }

    public void setSignId(Long signId) {
        this.signId = signId;
    }

    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getSignUsageCount() {
        return signUsageCount;
    }

    public void setSignUsageCount(Integer signUsageCount) {
        this.signUsageCount = signUsageCount;
    }

    public String getSignExpireTime() {
        return signExpireTime;
    }

    public void setSignExpireTime(String signExpireTime) {
        this.signExpireTime = signExpireTime;
    }

    public Long getAppIdLastUsedTime() {
        return appIdLastUsedTime;
    }

    public void setAppIdLastUsedTime(Long appIdLastUsedTime) {
        this.appIdLastUsedTime = appIdLastUsedTime;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getLoginLocation() {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("signId", getSignId())
                .append("devId", getDevId())
                .append("devName", getDevName())
                .append("appId", getAppId())
                .append("appSecret", getAppSecret())
                .append("sign",getSign())
                .append("signUsageCount", getSignUsageCount())
                .append("signExpireTime", getSignExpireTime())
                .append("appIdLastUsedTime", getAppIdLastUsedTime())
                .append("ipaddr", getIpaddr())
                .append("loginLocation", getLoginLocation())
                .toString();
    }
}
