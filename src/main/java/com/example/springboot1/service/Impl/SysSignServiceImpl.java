package com.example.springboot1.service.Impl;

import com.example.springboot1.common.browser.utils.StringUtils;
import com.example.springboot1.common.browser.utils.ip.checkIpLocation;
import com.example.springboot1.common.client.SignUtil;
import com.example.springboot1.entity.browser.quality.SysSign;
import com.example.springboot1.entity.browser.system.equipment.SysEquipment;
import com.example.springboot1.mapper.SysEquipmentStatusMapper;
import com.example.springboot1.mapper.SysSignMapper;
import com.example.springboot1.service.ISysSignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.example.springboot1.common.client.SignUtil.generateUUID;

@Service
public class SysSignServiceImpl implements ISysSignService {

    private static final Logger log = LoggerFactory.getLogger(SysSignServiceImpl.class);

    @Autowired
    private SysSignMapper signMapper;

    @Autowired
    private SysEquipmentStatusMapper equipmentStatusMapper;


    /**
     * appId的列表展示
     * @param sysSign
     * @return
     */
    @Override
    public List<SysSign> selectSignList(SysSign sysSign)
    {
        return signMapper.selectSignList(sysSign);
    }

    /**
     * 通过Id刷选appId
     * @param signId
     * @return
     */
    @Override
    public SysSign selectAppIdById(Long signId)
    {
        return signMapper.selectAppIdById(signId);
    }


    /**
     * 检查appId是否唯一
     * @param sign
     * @return
     */
    @Override
    public boolean checkAppIdUnique(SysSign sign)
    {
        Long signId = StringUtils.isNull(sign.getSignId()) ? -1L : sign.getSignId();
        SysSign info = signMapper.checkAppIdUnique(sign.getAppId());
        if (StringUtils.isNotNull(info) && info.getSignId().longValue() != signId.longValue())
        {
            return  false;
        }
        return  true;
    }

    /**
     *  新增保存appId
     * @param sign
     * @return
     */
    @Override
    @Transactional
    public int insertAppId(SysSign sign)
    {
        if (sign.getDevName() != null && sign.getDevName().length() > 0)
        {
            Long devId = equipmentStatusMapper.selectEquipmentIdByName(sign.getDevName());
            sign.setDevId(devId);
        }
        int rows = signMapper.insertAppId(sign);

        return rows;
    }


    /**
     * 检查appId是否存在
     * @param sign
     * @return
     */
    @Override
    public boolean checkAppIdExists(SysSign sign)
    {
        if (sign.getAppId() == null || sign.getAppId().length() == 0)
        {
            return false;
        }
        if (sign.getDevName() != null && sign.getDevName().length() > 0)
        {
            sign.setDevId(equipmentStatusMapper.selectEquipmentIdByName(sign.getDevName()));
        }

        return  signMapper.checkAppIdExists(sign) > 0;
    }

    /**
     * 进行时间校验，鉴定时间与注册时间不能超过五分钟
     * @param sign
     * @return
     */
    @Override
    public boolean checkTimestamp(SysSign sign)
    {
        Long lastTimeStamp = signMapper.selectAppIdLastUsedTime(sign);
        Long nowTimeStamp = Long.valueOf(sign.getTimestamp());
        int seconds = (int) ((nowTimeStamp - lastTimeStamp)/1000);

        // 定义时间不能超过五分钟
        if (Math.abs(seconds) > 300)
        {
            return false;
        }

        return true;
    }

    /**
     * 鉴定签名是否正确
     * @param sysSign
     * @return
     */
    @Override
    public boolean judgeSign(SysSign sysSign)
    {
        String appSecret = signMapper.selectAppSecret(sysSign);

        // 2.生成签名
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("appId", sysSign.getAppId());
        sortedMap.put("timestamp", sysSign.getTimestamp());
        System.out.println("签名："+ SignUtil.createSign(sortedMap, appSecret));

        //3.组装参数，
        SortedMap<String, String> sortedMap12 = new TreeMap<>();
        sortedMap12.put("appId", sysSign.getAppId());
        sortedMap12.put("timestamp", sysSign.getTimestamp());
        sortedMap12.put("sign", sysSign.getSign());

        //4.校验签名
        return SignUtil.isCorrectSign(sortedMap12, appSecret);
    }

    @Override
    @Transactional
    public int updateAppId(SysSign sign)
    {
        if (sign.getDevName() != null && sign.getDevName().length() > 0)
        {
            sign.setDevId(equipmentStatusMapper.selectEquipmentIdByName(sign.getDevName()));
        }
        return  signMapper.updateAppId(sign);
    }

    /**
     * 更新签名等相关信息
     * @param sign
     * @return
     */
    @Override
    @Transactional
    public int updateSign(SysSign sign)
    {
        sign.setAppIdLastUsedTime(Long.valueOf(sign.getTimestamp()));
        if(sign.getIpaddr()!=null && sign.getIpaddr().length()>0){
            sign.setLoginLocation(checkIpLocation.isInternalIP(sign.getIpaddr()));
        }
        int rows = signMapper.updateSign(sign);
        return rows;
    }

    @Override
    @Transactional
    public String updateAppSecret(SysSign sign)
    {
        String appSecret = SignUtil.generateUUID();
        sign.setAppSecret(appSecret);

        int rows = signMapper.updateAppSecret(sign);
        if (rows > 0)
        {
            return appSecret;
        }

        return null;
    }

    /**
     * 通过Id删除签名信息
     * @param signId
     * @return
     */
    @Override
    @Transactional
        public int deleteSignById(Long signId)
    {
        return signMapper.deleteSignById(signId);
    }

    @Override
    @Transactional
    public int deleteSignByIds(Long[] signIds)
    {
        return signMapper.deleteSignByIds(signIds);
    }


    /**
     * 校验签名，并更新sign的使用记录
     * @param sign
     * @return
     */
    @Override
    @Transactional
    public boolean checkSign(SysSign sign)
    {
        if (sign.getSign() == null || sign.getSign().isEmpty())
        {
            return false;
        }

        // 如果存在设备名，则转换为设备Id
        if (sign.getDevName() != null && !sign.getDevName().isEmpty())
        {
            sign.setDevId(equipmentStatusMapper.selectEquipmentIdByName(sign.getDevName()));
        }

        // 检查sign是否存在
        if (signMapper.selectSign(sign) == 0)
        {
            return false;
        }

        // 检查sign是否过期
        if (signMapper.selectSignExpireTime(sign) >= System.currentTimeMillis())
        {
            return false;
        }

        // 更新sign的记录
        signMapper.updateSignRecord(sign);

        return true;
    }

    /**
     * 校验签名，并更新sign的使用记录
     * @param sign
     * @return
     */
    @Override
    @Transactional
    public boolean checkSign(String sign){
        SysSign sysSign = new SysSign();
        sysSign.setSign(sign);
        if (sysSign.getSign() == null || sysSign.getSign().isEmpty())
        {
            return false;
        }

        // 检查sign是否存在
        if (signMapper.selectSign(sysSign) == 0)
        {
            return false;
        }

        // 检查sign是否过期
        if (signMapper.selectSignExpireTime(sysSign) >= System.currentTimeMillis())
        {
            return false;
        }

        // 更新sign的记录
        signMapper.updateSignRecord(sysSign);

        return true;
    }


    @Override
    public boolean checkSignIsUsing(Long signId)
    {
        SysSign sign = signMapper.selectAppIdById(signId);

        if (sign.getSign() == null || sign.getAppSecret() == null)
        {
            return false;
        }

        // 检查签名的截止时间是否过期
        if (sign.getSignExpireTime() != null && signMapper.selectSignExpireTime(sign) >= System.currentTimeMillis())
        {
            // 表示签名正在使用中
            return true;
        }

        return false;
    }

}
