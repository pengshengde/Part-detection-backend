package com.example.springboot1.service;

import com.example.springboot1.entity.browser.quality.SysDefectType;

import java.util.List;

/**
 * 缺陷类型 服务层
 */
public interface ISysDefectTypeService {

    /**
     * 查询缺陷类型列表
     * @param defectType
     * @return
     */
    public List<SysDefectType> selectDefectTypeList(SysDefectType defectType);

    /**
     * 通过缺陷类型ID查询
     * @param defectTypeId
     * @return
     */
    public SysDefectType selectDefectTypeById(Long defectTypeId);

    /**
     *  新增缺陷类型
     * @param defectType
     * @return
     */
    public int insertDefectType(SysDefectType defectType);

    /**
     * 更新缺陷的信息
     * @param defectType
     * @return
     */
    public int updateDefectType(SysDefectType defectType);

    /**
     * 删除缺陷类型对象
     * @param defectTypeIds
     * @return
     */
    public int deleteDefectTypeByIds(Long[] defectTypeIds);

    /**
     *  删除缺陷类型信息
     * @param defectTypeId
     * @return
     */
    public int deleteDefectTypeById(Long defectTypeId);

    /**
     * 校验缺陷类型的操作是否被允许
     * @param defectType
     */
    public void checkDefectTypeAllowed(SysDefectType defectType);

    /**
     * 校验缺陷名称是否唯一
     * @param defectType
     * @return
     */
    public boolean checkDefectTypeNameUnique(SysDefectType defectType);

    /**
     * 校验缺陷描述是否唯一
     * @param defectType
     * @return
     */
    public boolean checkDefectDescription(SysDefectType defectType);


}
