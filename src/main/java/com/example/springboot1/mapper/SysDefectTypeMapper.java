package com.example.springboot1.mapper;

import com.example.springboot1.entity.browser.quality.SysDefectType;

import java.util.List;

/**
 * 缺陷类型访问情况
 */
public interface SysDefectTypeMapper
{
    /**
     * 根据缺陷名称返回
     * @param defectTypeName
     * @return
     */
    public Long selectDefectTypeIdByName(String defectTypeName);

    /**
     * 根据缺陷描述返回
     * @param defectTypeDescription
     * @return
     */
    public Long selectDefectTypeIdByDescription(String defectTypeDescription);

    /**
     *  查询缺陷类型列表
     * @param sysDefectType
     * @return
     */
    public List<SysDefectType> selectDefectTypeList(SysDefectType sysDefectType);

    /**
     * 通过Id查询缺陷类型
     * @param defectTypeId
     * @return
     */
    public SysDefectType selectDefectTypeById(Long defectTypeId);

    /**
     * 插入缺陷类型
     * @param defectType
     * @return
     */
    public int insertDefectType(SysDefectType defectType);

    /**
     * 检测缺陷名称是否唯一
     * @param defectTypeName
     * @return
     */
    public SysDefectType checkDefectTypeNameUnique(String defectTypeName);

    /**
     * 检查缺陷描述是否唯一
     * @param defectDescription
     * @return
     */
    public SysDefectType checkDefectDescription(String defectDescription);

    /**
     * 更新缺陷类型信息
     * @param defectType
     * @return
     */
    public int updateDefectType(SysDefectType defectType);

    /**
     * 批量删除缺陷类型信息
     * @param defectTypeIds
     * @return
     */
    public int deleteDefectTypeByIds(Long[] defectTypeIds);

    /**
     * 删除缺陷类型信息
     * @param defectTypeId
     * @return
     */
    public int deleteDefectTypeById(Long defectTypeId);
}
