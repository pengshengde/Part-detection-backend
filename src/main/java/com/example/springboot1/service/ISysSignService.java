package com.example.springboot1.service;

import com.example.springboot1.entity.browser.quality.SysSign;

import java.util.List;

/**
 * 签名的业务层
 */
public interface ISysSignService {

    /**
     * 鉴权信息查询
     * @param sysSign
     * @return
     */
    public List<SysSign> selectSignList(SysSign sysSign);

    /**
     * 通过signId检索详细信息
     * @param signId
     * @return
     */
    public SysSign selectAppIdById(Long signId);

    /**
     * 新增鉴权appId
     * @param sign
     * @return
     */
    public int insertAppId(SysSign sign);

    /**
     *  校验appId是否唯一
     * @param sign
     * @return
     */
    public boolean checkAppIdUnique(SysSign sign);

    /**
     * 进行时间校验，鉴定时间与注册时间不能超过五分钟
     * @param sign
     * @return
     */
    public boolean checkTimestamp(SysSign sign);

    /**
     * 检查appId是否存在
     * @param sign
     * @return
     */
    public boolean checkAppIdExists(SysSign sign);

    /**
     * 更新appId和设备信息等
     * @param sign
     * @return
     */
    public int updateAppId(SysSign sign);

    /**
     * 生成并更新appSecret
     * @param sign
     * @return
     */
    public String updateAppSecret(SysSign sign);


    /**
     * 更新签名等相关信息
     * @param sign
     * @return
     */
    public int updateSign(SysSign sign);



    /**
     * 通过Id删除签名信息
     * @param signId
     * @return
     */
    public int deleteSignById(Long signId);

    /**
     * 通过ids批量删除签名信息
     * @param signIds
     * @return
     */
    public int deleteSignByIds(Long[] signIds);
    /**
     * 鉴定签名是否正确
     * @param sign
     * @return
     */
    public boolean judgeSign(SysSign sign);

    /**
     * 校验sign签名
     * @param sign
     * @return
     */
    public boolean checkSign(SysSign sign);

    /**
     * 校验sign签名
     * @param sign
     * @return
     */
    public boolean checkSign(String sign);

    /**
     * 检查签名是否正在使用
     * @param signId
     * @return
     */
    public boolean checkSignIsUsing(Long signId);

}
