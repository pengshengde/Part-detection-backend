package com.example.springboot1.mapper;

import com.example.springboot1.entity.browser.quality.SysSign;

import java.util.List;

public interface SysSignMapper {

    /**
     * 查询appId或者签名信息列表
     * @param sysSign
     * @return
     */
    public List<SysSign> selectSignList(SysSign sysSign);

    /**
     * 新增appId,用来给软件端进行鉴权注册
     * @param sysSign
     * @return
     */
    public int insertAppId(SysSign sysSign);

    /**
     * 校验appId是否唯一
     * @param appId
     * @return
     */
    public  SysSign checkAppIdUnique(String appId);

    /**
     * 检查软件进行权限注册用的appId是否存在
     * @param sign
     * @return
     */
    public int checkAppIdExists(SysSign sign);

    /**
     * 更新appId,devName,remark
     * @param sign
     * @return
     */
    public int updateAppId(SysSign sign);

    /**
     *  更新appSecret,用来给软件端进行鉴权注册
     * @param sign
     * @return
     */
    public int  updateAppSecret(SysSign sign);

    /**
     * 将注册时间取出
     * @param sign
     * @return
     */
    public Long selectAppIdLastUsedTime(SysSign sign);

    /**
     * 通过Id查询详细信息
     * @param signId
     * @return
     */
    public SysSign selectAppIdById(Long signId);

    /**
     * 将指定appId的注册的appSecret取出
     * @param sign
     * @return
     */
    public String selectAppSecret(SysSign sign);

    /**
     * 更新签名等相关信息
     * @param sign
     * @return
     */
    public int updateSign(SysSign sign);

    /**
     * 校验签名
     * @param sign
     * @return
     */
    public int selectSign(SysSign sign);

    /**
     * 更新签名记录
     * @param sign
     * @return
     */
    public int updateSignRecord(SysSign sign);

    /**
     * 校验签名的过期时间
     * @param sign
     * @return
     */
    public Long selectSignExpireTime(SysSign sign);

    /**
     * 通过Id删除签名信息
     * @param signId
     * @return
     */
    public int deleteSignById(Long signId);

    /**
     * 通过Ids 批量删除签名信息
     * @param signIds
     * @return
     */
    public int deleteSignByIds(Long[] signIds);
}
