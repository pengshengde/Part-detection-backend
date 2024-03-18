package com.example.springboot1.service.Impl;

import com.example.springboot1.common.browser.exception.ServiceException;
import com.example.springboot1.common.browser.utils.StringUtils;
import com.example.springboot1.entity.browser.quality.SysDefectType;
import com.example.springboot1.mapper.SysDefectTypeMapper;
import com.example.springboot1.mapper.SysImageDefectTypeMapper;
import com.example.springboot1.mapper.SysPartDefectTypeMapper;
import com.example.springboot1.service.ISysDefectTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *  缺陷类型表(SysDefectType)表服务实现类
 */
@Service
public class SysDefectTypeServiceImpl implements ISysDefectTypeService {

    @Autowired
    private SysDefectTypeMapper defectTypeMapper;

    @Autowired
    private SysPartDefectTypeMapper partDefectTypeMapper;

    @Autowired
    private SysImageDefectTypeMapper imageDefectTypeMapper;

    /**
     * 查询缺陷类型的列表
     * @param defectType
     * @return
     */
    @Override
    public List<SysDefectType> selectDefectTypeList(SysDefectType defectType)
    {
        return defectTypeMapper.selectDefectTypeList(defectType);
    }

    /**
     * 通过缺陷类型Id查询
     * @param defectTypeId
     * @return
     */
    @Override
    public SysDefectType selectDefectTypeById(Long defectTypeId)
    {
        return defectTypeMapper.selectDefectTypeById(defectTypeId);
    }

    /**
     * 新增缺陷类型
     * @param defectType
     * @return
     */
    @Override
    @Transactional
    public int insertDefectType(SysDefectType defectType)
    {
        return defectTypeMapper.insertDefectType(defectType);
    }


    /**
     * 更新缺陷类型的信息
     * @param defectType
     * @return
     */
    @Override
    @Transactional
    public int updateDefectType(SysDefectType defectType)
    {
        return defectTypeMapper.updateDefectType(defectType);
    }

    /**
     * 删除缺陷类型对象
     * @param defectTypeIds
     * @return
     */
    @Override
    @Transactional
    public int deleteDefectTypeByIds(Long[] defectTypeIds)
    {
        for (Long defectTypeId : defectTypeIds)
        {
            checkDefectTypeAllowed(new SysDefectType(defectTypeId));
        }
        // 删除缺陷与零件关联
        partDefectTypeMapper.deletePartDefectTypeByDefectTypeIds(defectTypeIds);
        // 删除缺陷与图片关联
        imageDefectTypeMapper.deleteImageDefectTypeByDefectTypeIds(defectTypeIds);

        // 删除缺陷信息
        return defectTypeMapper.deleteDefectTypeByIds(defectTypeIds);
    }


    @Override
    @Transactional
    public int deleteDefectTypeById(Long defectTypeId)
    {
        checkDefectTypeAllowed(new SysDefectType(defectTypeId));
        // 删除缺陷与零件关联
        partDefectTypeMapper.deletePartDefectTypeByDefectTypeId(defectTypeId);
        // 删除缺陷与图片关联
        imageDefectTypeMapper.deleteImageDefectTypeByDefectTypeId(defectTypeId);

        return defectTypeMapper.deleteDefectTypeById(defectTypeId);
    }

    /**
     * 校验缺陷类型是否允许操作
     * @param defectType
     */
    @Override
    public void checkDefectTypeAllowed(SysDefectType defectType)
    {
        if (StringUtils.isNotNull(defectType.getDefectTypeId()) && defectType.isAdmin())
        {
            throw new ServiceException("不允许操作'正常'的类别");
        }
    }

    /**
     * 校验缺陷名称是否唯一
     * @param defectType
     * @return
     */
    @Override
    public boolean checkDefectTypeNameUnique(SysDefectType defectType)
    {
        Long defectTypeId = StringUtils.isNull(defectType.getDefectTypeId()) ? -1L : defectType.getDefectTypeId();
        SysDefectType info = defectTypeMapper.checkDefectTypeNameUnique(defectType.getDefectTypeName());
        if (StringUtils.isNotNull(info) && info.getDefectTypeId().longValue() != defectTypeId.longValue())
        {
            return false;
        }
        return true;
    }

    /**
     * 校验缺陷描述是否唯一
     * @param defectType
     * @return
     */
    @Override
    public boolean checkDefectDescription(SysDefectType defectType)
    {
        Long defectTypeId = StringUtils.isNull(defectType.getDefectTypeId()) ? -1L : defectType.getDefectTypeId();
        SysDefectType info = defectTypeMapper.checkDefectTypeNameUnique(defectType.getDefectDescription());
        if (StringUtils.isNotNull(info) && info.getDefectTypeId().longValue() != defectTypeId.longValue())
        {
            return false;
        }
        return true;
    }
}
