package com.example.springboot1.service.Impl;

import com.example.springboot1.common.browser.utils.StringUtils;
import com.example.springboot1.config.PageHelperConfigure;
import com.example.springboot1.entity.browser.quality.*;
import com.example.springboot1.mapper.*;
import com.example.springboot1.service.ISysPartService;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysPartServiceImpl implements ISysPartService {
    private static final Logger log = LoggerFactory.getLogger(SysPartServiceImpl.class);

    @Autowired
    private SysPartMapper partMapper;

    @Autowired
    private SysImageMapper imageMapper;

    @Autowired
    private SysPartTypeMapper partTypeMapper;

    @Autowired
    private SysBatchPartMapper batchPartMapper;

    @Autowired
    private SysPartImageMapper partImageMapper;

    @Autowired
    private SysImageDefectTypeMapper imageDefectTypeMapper;

    @Autowired
    private SysPartDefectTypeMapper partDefectTypeMapper;

    @Autowired
    private SysDefectTypeMapper defectTypeMapper;

    /**
     * 查询零件信息
     * @param part 零件信息实体类
     * @return  零件集合
     */
    @Override
    @Transactional
    public List<SysPart> selectPartList(SysPart part)
    {
        List<SysPart> list = partMapper.selectPartList(part);
        // 将零件的缺陷类型列表赋值给缺陷类型集合
        for (SysPart sysPart : list)
        {
            changeDefectTypeName(sysPart);
        }
        return list;
    }

    @Override
    @Transactional
    public List<SysPart> selectPartIds(SysPart part)
    {
        return partMapper.selectPartList1(part);
    }

    @Override
    public int statisticPart(SysPart part){
        return partMapper.selectPartList1(part).size();
    }


    /**
     * 通过零件的Id查询零件信息
     * @param partId
     * @return
     */
    @Override
    public SysPart selectPartById(Long partId)
    {
        SysPart part = partMapper.selectPartByPartId(partId);
        return changeDefectTypeName(part);
    }

    /**
     * 根据零件批次查询零件ID集合
     * @param batch 零件批次
     * @return
     */
    public Long[] selectPartIdsByName(SysBatch batch)
    {
        String batchName = batch.getBatchName();
        List<Long> partIdList =  partMapper.selectPartIdsByBatchName(batchName);
        Long[] partIds = partIdList.toArray(new Long[partIdList.size()]);
        return partIds;
    }


    /**
     * 根据零件名称查询零件ID
     * @param partName
     * @return
     */
    @Override
    public Long selectPartIdByName(String partName)
    {
        return  partMapper.selectPartIdByName(partName);
    }

    /**
     * 校验零件名称是否唯一
     * @param part
     * @return
     */
    @Override
    public boolean checkPartNameUnique(SysPart part){
        Long partId = StringUtils.isNull(part.getPartId()) ? -1L : part.getPartId();
        SysPart info = partMapper.checkPartNameUnique(part.getPartName());
        if (StringUtils.isNotNull(info) && info.getPartId().longValue() != partId.longValue())
        {
            return false;
        }
        return true;

    }

    /**
     * 插入零件信息
     * @param part
     * @return
     */
    @Override
    @Transactional
    public int insertPart(SysPart part)
    {
        // 根据零件类型名称查询零件类型Id
        Long partTypeId = partTypeMapper.selectPartTypeIdByName(part.getPartTypeName());
        part.setPartTypeId(partTypeId);
        // 零件检测字体设置为未检测
        part.setDetectStatus("0");
        // 设置删除标志为未删除
        part.setDelFlag("0");
        // 新增零件信息
        int rows =  partMapper.insertPart(part);
        return  rows;
    }



    /**
     * 新增零件批次与零件信息
     *
     * @param batch 批次对象
     */
    @Override
    @Transactional
    public int insertBatchPart(SysBatch batch)
    {
        // 查询零件ID组
        batch.setPartIds(this.selectPartIdsByName(batch));
        return insertBatchPart(batch.getBatchId(),batch.getPartIds());
    }



    /**
     * 新增零件批次与零件信息
     *
     * @param batchId  批次ID
     * @param partIds  零件ID组
     */
    public int insertBatchPart(Long batchId,Long[] partIds)
    {
        if (StringUtils.isNotNull(partIds))
        {
            // 新增零件批次与零件信息
            List<SysBatchPart> list = new ArrayList<SysBatchPart>(partIds.length);
            for (Long partId : partIds)
            {
                SysBatchPart batchPart = new SysBatchPart();
                batchPart.setBatchId(batchId);
                batchPart.setPartId(partId);
                list.add(batchPart);
            }
            return batchPartMapper.batchInsertBatchPart(list);
        }
        return 0;
    }

    @Override
    @Transactional
    public int updatePart(SysPart part)
    {
        // 如果零件类型修改
        if (part.getPartTypeName() != null )
        {
            // 根据零件类型名称查询零件类型Id
            Long partTypeId = partTypeMapper.selectPartTypeIdByName(part.getPartTypeName());
            part.setPartTypeId(partTypeId);
        }

        if (part.getDefectTypeNames() != null && part.getDefectTypeNames().length > 0)
        {
            // 先删除零件缺陷类型
            partDefectTypeMapper.deletePartDefectTypeByPartId(part.getPartId());
            // 新增零件缺陷类型
            List<SysPartDefectType> list = new ArrayList<SysPartDefectType>(part.getDefectTypeNames().length);

            for (String defectTypeName : part.getDefectTypeNames())
            {
                SysPartDefectType partDefectType = new SysPartDefectType();
                partDefectType.setPartId(part.getPartId());
                partDefectType.setDefectTypeId(defectTypeMapper.selectDefectTypeIdByDescription(defectTypeName));
                partDefectTypeMapper.insertPartDefectType(partDefectType);
            }
        }

        return partMapper.updatePart(part);

    }

    @Override
    @Transactional
    public int deletePartByIds(Long[] partIds)
    {
        SysPart  part = new SysPart();
        for (Long partId : partIds)
        {
            part.setPartId(partId);
            deletePart(part);
        }

        return partIds.length;
    }

    /**
     * 删除零件相关信息
     * @param part
     * @return
     */
    @Override
    @Transactional
    public int deletePart(SysPart part)
    {
        // 先检查零件的Id是否为空
        if (part.getPartId() == null)
        {
            return 0;
        }

        // 根据零件Id查询图片Ids
        List<Long> imageIdList = partImageMapper.selectImageIdsByPartId(part.getPartId());
        for (Long imageId : imageIdList)
        {
            // 删除图片与缺陷的关联表信息,删除图片的相关信息
            imageDefectTypeMapper.deleteImageDefectTypeByImageId(imageId);
            imageMapper.deleteImageById(imageId);
        }

        // 根据零件Id删除与缺陷的关联信息
        partDefectTypeMapper.deletePartDefectTypeByPartId(part.getPartId());

        // 根据零件Id删除与图片的关联信息
        partImageMapper.deletePartImageByPartId(part.getPartId());

        // 根据零件删除与批次的关联信息
        batchPartMapper.deleteBatchPartByPartId(part.getPartId());

        // 删除零件的信息
        int rows = partMapper.deletePartByPartId(part.getPartId());

        return rows;
    }

    /**
     * 根据条件分页查询零件批次零件
     */
    @Override
    public List<SysPart> selectAllocatedPartList(SysPart part)
    {
        return  partMapper.selectAllocatedPartList(part);
    }

    /**
     * 根据条件分页查询未被分配的零件
     */
    @Override
    public List<SysPart> selectUnallocatedPartList(SysPart part)
    {
        return partMapper.selectUnallocatedPartList(part);
    }

    @Override
    @Transactional
    public int insertAuthImages(Long partId, Long[] imageIds)
    {
        // 新建零件与图片的关联信息
        List<SysPartImage> list = new ArrayList<SysPartImage>();
        for (Long imageId : imageIds) {
            SysPartImage pi = new SysPartImage();
            pi.setPartId(partId);
            pi.setImageId(imageId);
            list.add(pi);
        }
        int rows = partImageMapper.batchInsertPartImage(list);
        updatePartDefectType(partId);
        return rows;
    }

    @Override
    @Transactional
    public int deleteAuthImage(SysPartImage partImage){
        int rows = partImageMapper.deletePartImageInfo(partImage);
        updatePartDefectType(partImage.getPartId());
        return rows;
    }

    @Override
    @Transactional
    public int deleteAuthImages(Long partId, Long[] imageIds)
    {
        int rows = partImageMapper.deletePartImageInfos(partId, imageIds);
        updatePartDefectType(partId);
        return rows;
    }

    @Transactional
    public void updatePartDefectType(Long partId)
    {
        // 先将零件下的所有图片的缺陷保存在集合里面
        // 先获得零件下所有图片Id
        List<Long> imageIds = partImageMapper.selectImageIdsByPartId(partId);

        List<Long> defectTypeIdsList = new ArrayList<>();
        for (Long imageId : imageIds)
        {
            List<Long> defectTypeIdsResult = imageDefectTypeMapper.selectDefectTypeByImageId(imageId);
            // 将查询到的 defectTypeIdsResult 添加到 defectTypeIdsList 中
            defectTypeIdsList.addAll(defectTypeIdsResult);
        }

        // 去除重复的缺陷类型Id
        Set<Long> uniqueDefectTypeIds = new HashSet<>(defectTypeIdsList);
        List<Long> mergedDefectTypeIds = new ArrayList<>(uniqueDefectTypeIds);

        if (mergedDefectTypeIds.contains(1L) && mergedDefectTypeIds.size() > 1) {
            mergedDefectTypeIds.remove(1L);
        }


        // 更新零件的缺陷类型
        // 新建零件与图片的关联信息
        List<SysPartDefectType> list = new ArrayList<SysPartDefectType>();
        for (Long defectTypeId : mergedDefectTypeIds) {
            SysPartDefectType sysPartDefectType = new SysPartDefectType();
            sysPartDefectType.setDefectTypeId(defectTypeId);
            sysPartDefectType.setPartId(partId);
            list.add(sysPartDefectType);
        }
        // 删除零件原有的缺陷类型关联信息
        partDefectTypeMapper.deletePartDefectTypeByPartId(partId);

        // 插入新的缺陷类型关联信息
        partDefectTypeMapper.batchInsertPartDefectType(list);

        // 更新零件的是否合格信息
        SysPart part = new SysPart();
        part.setPartId(partId);
        if (mergedDefectTypeIds.contains(1L) && mergedDefectTypeIds.size() == 1)
        {
            part.setDetectResult("合格");
        }else {
            part.setDetectResult("不合格");
        }
        partMapper.updatePart(part);
    }


    public SysPart changeDefectTypeName(SysPart part)
    {
        List<SysDefectType> defectTypes = part.getDefectTypes();
        if (defectTypes != null) {
            List<String> defectTypeNamesList = new ArrayList<>();
            for (SysDefectType defectType : defectTypes) {
                String defectTypeName = defectType.getDefectDescription();
                defectTypeNamesList.add(defectTypeName);
            }
            String[] defectTypeNamesArray = defectTypeNamesList.toArray(new String[0]);
            String defectTypeName = String.join(",", defectTypeNamesList);
            part.setDefectTypeNames(defectTypeNamesArray);
            part.setDefectTypeName(defectTypeName);
        }

        return part;
    }
}
