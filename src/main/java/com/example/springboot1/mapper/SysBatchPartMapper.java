package com.example.springboot1.mapper;

import com.example.springboot1.entity.browser.quality.SysBatchPart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 批次与零件的关联表
 */
public interface SysBatchPartMapper
{
    /**
     * 通过批次Id查询包含的零件数量
     *
     * @param batchId
     * @return
     */
    public int countBatchPartByBatchId(String batchId);

    /**
     * 根据批次Id查找所有相关的零件Ids
     *
     * @param batchId  批次的Id
     * @return 零件的Id集合
     */
    public List<Long> selectBatchPartByBatchId(Long batchId);


    /**
     * 根据零件的Id查找对应的批次Id
     *
     * @param partId 零件Id
     * @return 一个批次Id
     */
    public Long selectBartPartByPartId(Long partId);

    /**
     * 通过零件批次的Id寻找所有零件的Id
     * @param batchId
     * @return
     */
    public List<Long> selectPartIdsByBatchId(Long batchId);

    /**
     * 批量增加批次与零件的对应关系
     * @param batchPartList
     * @return
     */
    public int batchInsertBatchPart(List<SysBatchPart> batchPartList);

    /**
     * 通过批次Id删除所有与之对应的批次、零件关联信息
     * 用于删除批次时
     * @param batchId 要删除的批次Id
     * @return  删除结果
     */
    public int deleteBatchPartByBatchId(Long batchId);

    /**
     *  通过零件Id删除所有与之对应的批次、零件关联信息
     *  用于删除某零件时
     * @param partId
     * @return
     */
    public int deleteBatchPartByPartId(Long partId);

    /**
     * 通过零件id 批量删除批次与零件的对应关系
     *
     * @param partIds 要删除零件Id的集合
     * @return 删除结果
     */
    public int deleteBatchPart(Long[] partIds);

    /**
     *  删除批次与零件的对应关系
     * @param batchPart
     * @return
     */
    public int deleteBatchPartInfo(SysBatchPart batchPart);

    /**
     * 批量删除批次与零件的对应关系
     * @param batchId
     * @param partIds
     * @return
     */
    public int deleteBatchPartInfos(@Param("batchId") Long batchId, @Param("partIds") Long[] partIds);
}
