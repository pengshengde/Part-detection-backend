package com.example.springboot1.mapper;

import com.example.springboot1.entity.browser.quality.SysBatch;

import java.util.List;

/**
 *  系统批次访问情况
 */
public interface SysBatchMapper
{
    /**
     * 根据条件分页查询未被检测的批次列表
     * batchId、batchName、partTypeId、partTypeName、quantity、productionLine、devId、devName、params.beginTime、params.endTime
     * @param sysBatch  批次信息
     * @return  批次信息集合
     */
    public List<SysBatch> selectBatchUndetectedList(SysBatch sysBatch);

    /**
     * 根据条件分页查询检测的批次列表
     * batchId、batchName、partTypeId、partTypeName、quantity、devId、devName、params.beginTime、params.endTime
     * @param sysBatch 批次信息
     * @return 批次信息集合
     */
    public List<SysBatch> selectBatchDetectedList(SysBatch sysBatch);

    /**
     * 根据批次的Id查询零件批次的详细信息
     * @param batchId
     * @return
     */
    public SysBatch selectBatchById(Long batchId);

    /**
     *  新增批次信息
     *  包含信息：
     *  batchName、partTypeId、quantity、productionLine、devId、expectedStartTime、expectedEndTime、createBy、remark
     * @param sysBatch  新增信息的批次实体类
     * @return  插入结果
     */
    public int insertBatch(SysBatch sysBatch);

    /**
     * 检验零件批次是否唯一
     *
     * @param batchName
     * @return
     */
    public SysBatch checkBatchNameUnique(String  batchName);

    /**
     * 更新批次信息
     * 检测无关信息更新
     * batchName、partTypeId、productionLine、devId、expectedStartTime、expectedEndTime、updateBy、remark
     * @param batch  批次信息
     * 检测相关信息更新
     * detectStatus、detectStartTime、detectEndTime、qualifiedQuantity、unqualifiedQuantity、qualifiedRate、
     * @return  更新结果
     */
    public int updateBatch(SysBatch batch);

    /**
     * 通过Id删除
     * @param batchId
     * @return
     */
    public int deleteBatchById(Long batchId);

    /**
     * 根据Ids批量删除零件批次信息
     * @param batchId
     * @return
     */
    public int deleteBatchByIds(Long[] batchId);
}
