package com.example.springboot1.mapper;

import com.example.springboot1.entity.browser.quality.SysPart;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统零件访问情况
 */
public interface SysPartMapper
{
    /**
     * 根据条件分页查询零件列表
     * partId、partName、partTypeId、partTypeName、detectResult、params.beginTime、params.endTime
     * @param sysPart  零件信息
     * @return  零件集合信息
     */
    public List<SysPart> selectPartList(SysPart sysPart);

    public List<SysPart> selectPartList1(SysPart sysPart);

    /**
     * 插入零件信息
     * @param sysPart
     * @return
     */
    public int insertPart(SysPart sysPart);

    /**
     * 检查零件名称是否唯一
     *
     * @param partName
     * @return
     */
    public SysPart checkPartNameUnique(String partName);


    /**
     * 更具零件的名称查询零件的ID
     * @param partName
     * @return
     */
    public Long selectPartIdByName(String partName);

    /**
     * 根据零件批次的名称查询所有的零件ID集合
     *
     * @param batchName
     * @return
     */
    public List<Long> selectPartIdsByBatchName(String batchName);

    /**
     * 通过零件ID查询零件信息
     *
     * @param partId 零件Id
     * @return
     */
    public SysPart selectPartByPartId(Long partId);

    /**
     * 更新零件信息
     * 检测无关信息
     * partTypeId、partName、updateBy、remark
     * 检测相关信息
     * detectTime、detectStatus、
     * @param part 零件的更新实体类
     * @return 更新结果
     */
    public int updatePart(SysPart part);

    /**
     * 通过零件的Id删除零件的信息
     * @param partId
     * @return
     */
    public int deletePartByPartId(Long partId);

    /**
     * 根据条件分页查询零件批次的零件
     */
    public List<SysPart> selectAllocatedPartList(SysPart sysPart);

    /**
     * 根据条件分页查询未分配零件的列表
     */
    public List<SysPart> selectUnallocatedPartList(SysPart sysPart);


}
