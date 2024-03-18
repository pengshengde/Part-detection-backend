package com.example.springboot1.service;

import com.example.springboot1.entity.browser.quality.SysBatch;
import com.example.springboot1.entity.browser.quality.SysImage;
import com.example.springboot1.entity.browser.quality.SysPart;
import com.example.springboot1.entity.browser.quality.SysPartImage;

import java.util.List;

/**
 * 单个零件的(SysPart)的服务接口
 */
public interface ISysPartService {

    /**
     * 根据条件查询零件
     * @param sysPart
     * @return
     */
    public List<SysPart> selectPartList(SysPart sysPart);


    public List<SysPart> selectPartIds(SysPart sysPart);


    public int statisticPart(SysPart part);

    /**
     * 通过零件的Id查询零件信息
     * @param partId
     * @return
     */
    public SysPart selectPartById(Long partId);

    /**
     * 根据零件批次查询零件ID集合
     * @param batch 零件批次
     * @return
     */
    public Long[] selectPartIdsByName(SysBatch batch);

    /**
     * 插入零件信息
     *
     * @param sysPart
     * @return
     */
    public int insertPart(SysPart sysPart);


    /**
     * 新增批次与零件关联信息
     * @param batch
     * @return
     */
    public int insertBatchPart(SysBatch batch);

    /**
     *  更新零件信息
     * @param sysPart
     * @return
     */
    public int updatePart(SysPart sysPart);

    /**
     * 通过零件名称查看零件Id
     * @param partName
     * @return
     */
    public Long selectPartIdByName(String partName);

    /**
     * 校验零件名称是否唯一
     *
     * @param sysPart
     * @return
     */
    public boolean checkPartNameUnique(SysPart sysPart);

    /**
     * 批量删除零件
     * @param partIds
     * @return
     */
    public int deletePartByIds(Long[] partIds);

    /**
     * 删除零件的相关信息
     * @param part
     * @return
     */
    public int deletePart(SysPart part);

    /**
     * 根据条件分页查询零件批次零件
     */
    public List<SysPart> selectAllocatedPartList(SysPart part);

    /**
     * 根据条件分页查询未分配零件批次零件
     */
    public List<SysPart> selectUnallocatedPartList(SysPart part);

    /**
     *  批量新增零件图片关联信息
     * @param partId
     * @param imageIds
     * @return
     */
    public int insertAuthImages(Long partId, Long[] imageIds);

    /**
     * 删除零件图片关联信息
     * @param partImage
     * @return
     */
    public int deleteAuthImage(SysPartImage partImage);

    /**
     * 批量删除零件与图片关联信息
     * @param partId
     * @param imageIds
     * @return
     */
    public int deleteAuthImages(Long partId, Long[] imageIds);

}
