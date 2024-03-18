package com.example.springboot1.mapper;


import com.example.springboot1.entity.browser.common.SysUser;
import com.example.springboot1.entity.browser.system.SysLogininfor;
import com.example.springboot1.entity.browser.system.equipment.SysEquipment;
import com.example.springboot1.entity.browser.system.equipment.SysEquipmentStatus;
import com.example.springboot1.entity.browser.system.equipment.SysStatus;

import java.util.List;

/**
 * 系统设备状态情况信息 数据层
 * 
 * @author ruoyi
 */

public interface SysEquipmentStatusMapper
{

    /**
     * 根据条件分页查询设备状态列表
     *
     * @param sysEquipmentStatus 设备状态信息
     * @return 设备状态集合信息
     */
    public List<SysEquipmentStatus> selectEquipmentStatusList(SysEquipmentStatus sysEquipmentStatus);

    /**
     *  查询所有的设备信息
     * @param sysEquipment  设备信息
     * @return 查询设备信息列表
     */
    public List<SysEquipment> selectEquipmentList(SysEquipment sysEquipment);

    /**
     * 查询所有的状态信息
     * @param sysStatus
     * @return
     */
    public List<SysStatus> selectStatusList(SysStatus sysStatus);

    /**
     * 通过设备状态ID查询设备状态
     *
     * @param devStatusId 设备状态ID
     * @return   设备状态信息
     */
    public  SysEquipmentStatus selectEquipmentStatusById(Long devStatusId);

    /**
     *  新增设备状态
     *
     * @param sysEquipmentStatus 设备状态信息
     * @return 结果
     */
    public int insertEquipmentStatus(SysEquipmentStatus sysEquipmentStatus);

    /**
     * 通过设备状态ID删除设备状态
     *
     * @param devStatusId 设备状态ID
     * @return 删除结果
     */
    public int deleteEquipmentStatusById(Long devStatusId);

    /**
     * 批量删除设备状态信息
     *
     * @param devStatusIds 需要删除的数据ID
     * @return 删除结果
     */
    public int deleteEquipmentStatusByIds(Long[] devStatusIds);


    /**
     * 通过状态名称查询状态的id
     * @param statusName 状态名称
     * @return 状态id等信息
     */
    public SysStatus selectStatusIdByName(String statusName);

    /**
     * 通过设备名称查询设备的id
     * @param devName 设备名称
     * @return  设备id等信息
     */
    public Long selectEquipmentIdByName(String devName);

    /**
     *  检查是否存在某状态
     * @param statusName  状态名称
     * @return 1 表示存在 0 表示不存在
     */
    public int checkStatusNameExist(String statusName);

    /**
     * 检查是否存在某设备被
     * @param devName 设备名称
     * @return  1 表示存在 0 表示不存在
     */
    public int checkDevNameExist(String devName);

    /**
     * 修改设备状态信息
     * @param sysEquipmentStatus 设备状态信息
     * @return 修改结果
     */
    public int updateEquipmentStatus(SysEquipmentStatus sysEquipmentStatus);




}
