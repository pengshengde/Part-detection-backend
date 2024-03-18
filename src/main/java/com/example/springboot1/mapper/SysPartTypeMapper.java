package com.example.springboot1.mapper;

import com.example.springboot1.entity.browser.quality.SysPartType;

import java.util.List;

/**
 * 系统访问零件类型(SysPartType)表数据库访问层
 */
public interface SysPartTypeMapper {

    /**
     * 根据条件查询零件的类型列表
     *  partTypeId、partTypeName、params.beginTime、 params.endTime
     * @param sysPartType   零件类型实体类
     * @return 零件类型列表
     */
    public List<SysPartType> selectPartTypeList(SysPartType sysPartType);

    /**
     * 根据零件类型名称查询零件类型Id
     * @param partTypeName 零件类型名称
     * @return  零件类型Id
     */
    public Long selectPartTypeIdByName(String partTypeName);
}
