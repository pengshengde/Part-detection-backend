package com.example.springboot1.service;

import com.example.springboot1.entity.browser.quality.SysBatch;
import com.example.springboot1.entity.browser.quality.SysBatchPart;

import java.util.List;

/**
 * 零件批次表(SysBatch)的服务接口
 */
public interface ISysBatchService {

    /**
     * 根据条件查询未被检测的零件批次
     *
     * @param sysBatch
     * @return
     */
    public List<SysBatch> selectBatchUndetectedList(SysBatch sysBatch);


    /**
     *  根据条件查询已被检测的零件批次
     * @param sysBatch
     * @return
     */
    public List<SysBatch> selectBatchDetectedList(SysBatch sysBatch);

    /**
     * 根据批次Id查询零件批次信息
     * @param batchId
     * @return
     */
    public SysBatch selectBatchById(Long batchId);

    /**
     *  插入零件批次信息
     * @param sysBatch
     * @return
     */
    public int insertBatch(SysBatch sysBatch);

    /**
     * 更新零件批次信息
     * @param sysBatch
     * @return
     */
    public int updateBatch(SysBatch sysBatch);

    /**
     * 检验零件批次是否唯一
     *
     * @param sysBatch
     * @return
     */
    public boolean checkBatchNameUnique(SysBatch sysBatch);

    /**
     * 通过批次Id删除零件批次信息
     * @param batchId
     * @return
     */
    public  int deleteBatchById(Long batchId);

    /**
     * 通过批次Id批量删除零件批次信息
     * @param batchIds
     * @return
     */
    public int deleteBatchByIds(Long[] batchIds);

    /**
     * 通过批次Id查询零件Id
     * @param batchId
     * @return
     */
    public List<Long> selectPartIdsByBatchId(Long batchId);

    /**
     * 批量选择零件加入批次
     * @param batchId
     * @param partIds
     * @return
     */
    public int insertAuthParts(Long batchId, Long[] partIds);

    /**
     * 删除批次与零件的对应关系
     * @param batchPart
     * @return
     */
    public int deleteAuthPart(SysBatchPart batchPart);

    /**
     * 批量删除批次与零件的对应关系
     * @param batchId
     * @param partIds
     * @return
     */
    public int deleteAuthParts(Long batchId, Long[] partIds);
}
