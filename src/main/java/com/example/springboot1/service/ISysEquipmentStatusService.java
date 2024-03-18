package com.example.springboot1.service;

import com.example.springboot1.entity.browser.common.SysUser;
import com.example.springboot1.entity.browser.system.equipment.SysEquipment;
import com.example.springboot1.entity.browser.system.equipment.SysEquipmentStatus;
import com.example.springboot1.entity.browser.system.equipment.SysStatus;

import java.util.List;

/**
 *  设备状态表(SysEquipmentStatus)表服务接口
 */
public interface ISysEquipmentStatusService {
    /**
     * 根据条件分页查询设备状态列表
     *
     * @param sysEquipmentStatus 设备状态信息
     * @return 设备状态集合信息
     */
    public List<SysEquipmentStatus> selectEquipmentStatusList(SysEquipmentStatus sysEquipmentStatus);

    /**
     * 根据条件分页查询设备列表
     *
     * @param sysEquipment 设备列表信息
     * @return 设备集合信息
     */
    public List<SysEquipment> selectEquipmentList(SysEquipment sysEquipment);

    /**
     * 根据条件查询状态列表
     * @param sysStatus
     * @return
     */
    public List<SysStatus> selectStatusList(SysStatus sysStatus);

    /**
     * 根据设备状态ID查询设备状态信息
     *
     * @param devStatusId 设备状态ID
     * @return 设备状态信息
     */
    public SysEquipmentStatus selectEquipmentStatusById(Long devStatusId);

    /**
     * 根据设备状态ID删除设备状态信息
     *
     * @param devStatusId 设备状态ID
     * @return  删除结果1
     */
    public int deleteEquipmentStatusById(Long devStatusId);

    /**
     * 根据设备状态ID集合删除设备状态信息
     *
     * @param devStatusIds 设备状态ID集合
     * @return 删除结果
     */
    public int deleteEquipmentStatusByIds(Long[] devStatusIds);

    /**
     * 新增设备状态
     * @param sysEquipmentStatus 设备状态信息
     * @return 新增结果
     */
    public int insertEquipmentStatus(SysEquipmentStatus sysEquipmentStatus);

    public int updateEquipmentStatus(SysEquipmentStatus sysEquipmentStatus);

}
