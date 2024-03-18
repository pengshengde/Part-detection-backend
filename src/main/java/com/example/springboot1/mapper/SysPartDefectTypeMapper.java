package com.example.springboot1.mapper;

import com.example.springboot1.entity.browser.quality.SysPartDefectType;

import java.util.List;

/**
 * 零件与缺陷类型信息的数据层
 */
public interface SysPartDefectTypeMapper {

    /**
     * 通过零件Id检测是否该零件下有某种缺陷类型
     * @param PartDefectType
     * @return
     */
    public int countPartDefectType(SysPartDefectType PartDefectType);

    /**
     * 批量插入零件的缺陷信息
     * @param list
     * @return
     */
    public int batchInsertPartDefectType(List<SysPartDefectType> list);

    /**
     * 单独增加零件与缺陷类型的关联表
     * 仅用于正常的情况下
     * @param PartDefectType
     * @return
     */
    public int insertPartDefectType(SysPartDefectType PartDefectType);

    /**
     * 删除零件与缺陷类型关联信息
     *
     * @param PartDefectType
     * @return
     */
    public int deletePartDefectTypeInfo(SysPartDefectType PartDefectType);

    /**
     * 删除零件与缺陷类型关联信息
     * @param partId
     * @return
     */
    public int deletePartDefectTypeByPartId(Long partId);

    /**
     * 单个删除零件与缺陷类型关联信息
     * @param defectTypeId
     * @return
     */
    public int deletePartDefectTypeByDefectTypeId(Long defectTypeId);

    /**
     * 批量删除零件与缺陷类型关联信息
     * @param defectTypeIds
     * @return
     */
    public int deletePartDefectTypeByDefectTypeIds(Long[] defectTypeIds);

    /**
     * 通过零件的Id查询缺陷
     * @param partId
     * @return
     */
    public  List<Long> selectDefectTypeByPartId(Long partId);

}
