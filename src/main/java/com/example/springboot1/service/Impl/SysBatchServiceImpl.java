package com.example.springboot1.service.Impl;

import com.example.springboot1.common.browser.utils.StringUtils;
import com.example.springboot1.entity.browser.quality.SysBatch;
import com.example.springboot1.entity.browser.quality.SysBatchPart;
import com.example.springboot1.entity.browser.quality.SysPart;
import com.example.springboot1.mapper.*;
import com.example.springboot1.service.ISysBatchService;
import com.example.springboot1.service.ISysPartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysBatchServiceImpl implements ISysBatchService {

    private static final Logger log = LoggerFactory.getLogger(SysBatchServiceImpl.class);

    @Autowired
    private SysBatchMapper batchMapper;

    @Autowired
    private SysPartTypeMapper partTypeMapper;

    @Autowired
    private SysPartMapper partMapper;

    @Autowired
    private SysBatchPartMapper batchPartMapper;

    @Autowired
    private SysEquipmentStatusMapper equipmentStatusMapper;

    private ISysPartService sysPartService;

    /**
     * 查询未被检测的零件批次
     * @param sysBatch 零件批次实体类
     * @return  未被检测的零件批次集合
     */
    @Override
    public List<SysBatch> selectBatchUndetectedList(SysBatch sysBatch)
    {
        return batchMapper.selectBatchUndetectedList(sysBatch);
    }


    /**
     * 查询被检测的零件批次
     * @param sysBatch  零件批次实体类
     * @return  已检测的零件批次集合
     */
    @Override
    public List<SysBatch> selectBatchDetectedList(SysBatch sysBatch)
    {
        return batchMapper.selectBatchDetectedList(sysBatch);
    }

    /**
     * 通过批次Id查询批次的信息
     * @param batchId
     * @return
     */
    @Override
    public SysBatch selectBatchById(Long batchId)
    {
        return batchMapper.selectBatchById(batchId);
    }

    /**
     * 插入零件的批次信息
     * @param sysBatch 零件批次的实体类
     * @return  零件批次信息插入结果
     */
    @Override
    @Transactional
    public int insertBatch(SysBatch sysBatch)
    {
        // 根据检查设备名称查询设备Id
        Long devId = equipmentStatusMapper.selectEquipmentIdByName(sysBatch.getDevName());
        sysBatch.setDevId(devId);

        // 根据零件类型名称查询零件类型Id
        Long partTypeId = partTypeMapper.selectPartTypeIdByName(sysBatch.getPartTypeName());
        sysBatch.setPartTypeId(partTypeId);

         sysBatch.setDetectStatus("0");

         int rows = batchMapper.insertBatch(sysBatch);

        return rows;
    }

    /**
     * 更新零件的批次信息
     */
    @Override
    @Transactional
    public int updateBatch(SysBatch batch)
    {
        // 检测是否需要更新相关的零件信息
        if (batch.getBatchName() != null || batch.getPartTypeName() != null )
        {
            // 根据batchId查询到与之关联的所有零件Ids
            List<Long> partIdList = batchPartMapper.selectBatchPartByBatchId(batch.getBatchId());
            Long[] partIds = partIdList.toArray(new Long[partIdList.size()]);

            for (Long partId : partIds)
            {
                SysPart part = partMapper.selectPartByPartId(partId);
                if (batch.getBatchName() != null)
                {
                    String partName = part.getPartName();
                    part.setPartName(batch.getBatchName() + partName.substring(partName.length()-2));
                }

                if (batch.getPartTypeName() != null)
                {
                    // 根据零件类型名称查询零件类型Id
                    Long partTypeId = partTypeMapper.selectPartTypeIdByName(batch.getPartTypeName());
                    part.setPartTypeId(partTypeId);
                    batch.setPartTypeId(partTypeId);
                }

                partMapper.updatePart(part);
            }
        }


        return batchMapper.updateBatch(batch);
    }

    /**
     * 校验零件批次名称是否唯一
     *
     * @param batch
     * @return
     */
    @Override
    public boolean checkBatchNameUnique(SysBatch batch)
    {
        Long batchId = StringUtils.isNull(batch.getBatchId()) ? -1L : batch.getBatchId();
        SysBatch info = batchMapper.checkBatchNameUnique(batch.getBatchName());
        if (StringUtils.isNotNull(info) && info.getBatchId().longValue() != batchId.longValue())
        {
            return false;
        }
        return true;
    }


    /**
     * 删除批次信息
     * @param batchId
     * @return
     */
    @Override
    public int deleteBatchById(Long batchId)
    {
        return batchMapper.deleteBatchById(batchId);
    }

    /**
     * 批量删除批次信息
     * @param batchIds
     * @return
     */
    @Override
    @Transactional
    public int deleteBatchByIds(Long[] batchIds)
    {
        return batchMapper.deleteBatchByIds(batchIds);
    }

    @Override
    public List<Long> selectPartIdsByBatchId(Long batchId)
    {
        return batchPartMapper.selectPartIdsByBatchId(batchId);
    }

    /**
     * 批量选择零件加入批次
     */
    @Override
    @Transactional
    public int insertAuthParts(Long batchId, Long[] partIds)
    {
        // 新增批次与零件管理
        List<SysBatchPart> list = new ArrayList<SysBatchPart>();
        for (Long partId : partIds)
        {
            SysBatchPart bp = new SysBatchPart();
            bp.setBatchId(batchId);
            bp.setPartId(partId);
            list.add(bp);
        }
        batchPartMapper.batchInsertBatchPart(list);

        return updateBatchInfo(batchId);
    }

    /**
     * 删除批次与零件的对应关系
     * @param batchPart
     * @return
     */
    @Override
    @Transactional
    public int deleteAuthPart(SysBatchPart batchPart)
    {
        batchPartMapper.deleteBatchPartInfo(batchPart);
        return updateBatchInfo(batchPart.getBatchId());
    }

    /**
     * 批量删除批次与零件的对应关系
     * @param batchId
     * @param partIds
     * @return
     */
    @Override
    @Transactional
    public int deleteAuthParts(Long batchId, Long[] partIds)
    {
        batchPartMapper.deleteBatchPartInfos(batchId, partIds);
        return updateBatchInfo(batchId);
    }

    /**
     * 在新增或者减少零件时，更新零件的合格数量，不合格数量，以及合格率，总数量
     */
    public int updateBatchInfo(Long batchId)
    {
        SysBatch batch = batchMapper.selectBatchById(batchId);
        // 通过batchId查询所有零件的Ids
        List<Long> partIds = batchPartMapper.selectPartIdsByBatchId(batchId);

        batch.setQuantity((long) partIds.size());
        Long qualifiedQuantity = 0L;
        Long unqualifiedQuantity = 0L;

        for (Long partId : partIds) {
            SysPart part = partMapper.selectPartByPartId(partId);
            if ("合格".equals(part.getDetectResult())) {
                qualifiedQuantity++;
            } else if ("不合格".equals(part.getDetectResult())) {
                unqualifiedQuantity++;
            }
        }

        batch.setQualifiedQuantity(qualifiedQuantity);
        batch.setUnqualifiedQuantity(unqualifiedQuantity);

        // 计算合格率
        if (batch.getQuantity() != 0) {
            double qualifiedRate = (double) qualifiedQuantity / batch.getQuantity();
            DecimalFormat decimalFormat = new DecimalFormat("0.000000");
            String formattedRate = decimalFormat.format(qualifiedRate);
            batch.setQualifiedRate(formattedRate);
        } else {
            batch.setQualifiedRate("0");
        }

        return batchMapper.updateBatch(batch);
    }



}
