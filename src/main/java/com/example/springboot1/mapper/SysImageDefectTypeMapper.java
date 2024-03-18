package com.example.springboot1.mapper;

import com.example.springboot1.entity.browser.quality.SysImageDefectType;

import java.util.List;

/**
 * 图片与缺陷类型的关联表 数据层
 */
public interface SysImageDefectTypeMapper
{
    /**
     * 批量增加图片与缺陷类型的关联表
     * @param ImageDefectTypeList 零件与缺陷类型id的列表
     * @return
     */
    public int batchInsertImageDefectType(List<SysImageDefectType> ImageDefectTypeList);

    /**
     * 单独增加图片与缺陷类型的关联表
     * 仅用于正常的情况下
     * @param ImageDefectType
     * @return
     */
    public int insertImageDefectType(SysImageDefectType ImageDefectType);

    /**
     * 通过imageId查询图片存在缺陷的数量
     * @param imageId
     * @return
     */
    public int countImageDefectTypeByImageId(Long imageId);


    /**
     * 根据图片Id删除缺陷与零件关联信息
     * @param imageId
     * @return
     */
    public int deleteImageDefectTypeByImageId(Long imageId);

    /**
     * 检查图片是否含有某种缺陷
     * @param imageDefectType
     * @return
     */
    public int countImageDefectType(SysImageDefectType imageDefectType);

    /**
     * 通过图片Id查询缺陷类型Id
     * @param imageId
     * @return
     */
    public List<Long> selectDefectTypeByImageId(Long  imageId);

    /**
     * 删除零件与图片的关联信息
     * @param defectTypeId
     * @return
     */
    public int deleteImageDefectTypeByDefectTypeId(Long defectTypeId);

    /**
     * 删除零件与图片的关联信息
     * @param defectTypeIds
     * @return
     */
    public int deleteImageDefectTypeByDefectTypeIds(Long[] defectTypeIds);
}
