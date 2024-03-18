package com.example.springboot1.service.Impl;

import com.example.springboot1.entity.browser.common.SysUser;
import com.example.springboot1.entity.browser.system.equipment.SysEquipment;
import com.example.springboot1.entity.browser.system.equipment.SysEquipmentStatus;
import com.example.springboot1.entity.browser.system.equipment.SysStatus;
import com.example.springboot1.mapper.SysEquipmentStatusMapper;
import com.example.springboot1.service.ISysEquipmentStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysEquipmentStatusServiceImpl implements ISysEquipmentStatusService
{
    private static final Logger log = LoggerFactory.getLogger(SysEquipmentStatusServiceImpl.class);

    @Autowired
    private SysEquipmentStatusMapper equipmentStatusMapper;


    /**
     * 根据条件分页查询设备状态列表
     *
     * @param sysEquipmentStatus 设备状态信息
     * @return 设备状态信息集合信息
     */
    @Override
    public List<SysEquipmentStatus> selectEquipmentStatusList(SysEquipmentStatus sysEquipmentStatus)
    {
        return equipmentStatusMapper.selectEquipmentStatusList(sysEquipmentStatus);
    }

    /**
     * 根据条件分页查询设备列表
     * @param sysEquipment 设备列表信息
     * @return
     */
    @Override
    public List<SysEquipment> selectEquipmentList(SysEquipment sysEquipment)
    {
        return equipmentStatusMapper.selectEquipmentList(sysEquipment);
    }

    /**
     * 根据条件分页查询状态列表
     * @param sysStatus
     * @return
     */
    @Override
    public List<SysStatus>  selectStatusList(SysStatus sysStatus)
    {
        return equipmentStatusMapper.selectStatusList(sysStatus);
    }


    /**
     * 通过设备状态ID查询设备状态
     *
     * @param devStatusId 设备状态ID
     * @return  设备状态对象信息
     */
    @Override
    public SysEquipmentStatus selectEquipmentStatusById(Long devStatusId)
    {
        return equipmentStatusMapper.selectEquipmentStatusById(devStatusId);
    }

    /**
     * 通过设备状态ID删除设备状态
     *
     * @param devStatusId  设备状态ID
     * @return  删除结果
     */
    @Override
    @Transactional
    public int deleteEquipmentStatusById(Long devStatusId)
    {
        return equipmentStatusMapper.deleteEquipmentStatusById(devStatusId);
    }

    /**
     * 批量删除设备状态信息
     *
     * @param devStatusIds 需要删除的数据ID
     * @return  删除结果
     */
    @Override
    @Transactional
    public int deleteEquipmentStatusByIds(Long[] devStatusIds)
    {
        return equipmentStatusMapper.deleteEquipmentStatusByIds(devStatusIds);
    }

    /**
     *  新增保存设备状态信息
     * @param sysEquipmentStatus
     * @return
     */
    @Override
    @Transactional
    public int insertEquipmentStatus(SysEquipmentStatus sysEquipmentStatus)
    {
        // 通过 devName 查询 devId
        String devName = sysEquipmentStatus.getDevName();
        int a = equipmentStatusMapper.checkDevNameExist(devName);
        // 通过 statusName 查询 statusId
        String statusName = sysEquipmentStatus.getStatusName();
        int b = equipmentStatusMapper.checkStatusNameExist(statusName);

        if (a == 0 || b == 0)
        {
            return 0;
        }

        Long devId = equipmentStatusMapper.selectEquipmentIdByName(devName);
        Long statusId = equipmentStatusMapper.selectStatusIdByName(statusName).getStatusId();

        sysEquipmentStatus.setDevId(devId);
        sysEquipmentStatus.setStatusId(statusId);

        return equipmentStatusMapper.insertEquipmentStatus(sysEquipmentStatus);
    }


    @Override
    @Transactional
    public int updateEquipmentStatus(SysEquipmentStatus sysEquipmentStatus)
    {
        String devName = sysEquipmentStatus.getDevName();
        String statusName = sysEquipmentStatus.getStatusName();

        if((devName != null && devName != "")||(statusName != null &&  statusName != ""))
        {
            // 通过 devName 查询 devId
            int a = equipmentStatusMapper.checkDevNameExist(devName);
            // 通过 statusName 查询 statusId
            int b = equipmentStatusMapper.checkStatusNameExist(statusName);

            if(a == 0 || b == 0)
            {
                return 0;
            }

            Long devId = equipmentStatusMapper.selectEquipmentIdByName(devName);
            Long statusId = equipmentStatusMapper.selectStatusIdByName(statusName).getStatusId();

            sysEquipmentStatus.setDevId(devId);
            sysEquipmentStatus.setStatusId(statusId);
        }

        return equipmentStatusMapper.updateEquipmentStatus(sysEquipmentStatus);
    }

}
