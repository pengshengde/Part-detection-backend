package com.example.springboot1.entity.browser.quality;

import com.example.springboot1.common.browser.annotation.Excel;
import com.example.springboot1.common.browser.core.domain.BaseEntity;
import com.example.springboot1.entity.client.Parts;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 批次对象 sys_batch
 */
public class SysBatch extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 零件批次编号 */
    @Excel(name = "零件批次编号", cellType = Excel.ColumnType.NUMERIC, prompt = "批次编号" )
    private Long  batchId;

    /** 零件批次名称 */
    @Excel(name = "零件批次名称")
    private String batchName;

    /** 零件的名称编号 */
    private Long  partTypeId;

    @Excel(name = "零件名称")
    private String partTypeName;

    /** 零件数量 */
    @Excel(name = "零件数量")
    private Long quantity;

    /** 生产产线 */
    @Excel(name = "生产产线")
    private String productionLine;

    /** 生产设备id */
    private Long devId;

    /** 生产设备名称 */
    @Excel(name = "生产设备名称")
    private String devName;

    @Excel(name = "预计检测开始时间")
    /** 预计开始检测时间 */
    private String expectedStartTime;

    @Excel(name = "预计检测结束")
    /** 预计结束检测时间 */
    private String expectedEndTime;

    @Excel(name = "检测状态")
    /** 检测状态 0表示未检测 1表示已检测 */
    private String detectStatus;

    /** 实际开始检测时间 */
    @Excel(name = "检测开始时间")
    private String detectStartTime;

    /** 实际检测结束时间 */
    @Excel(name = "检测结束时间")
    private String detectEndTime;

    /** 检测合格数量 */
    @Excel(name = "合格数量")
    private Long qualifiedQuantity;

    /** 检测不合格数量 */
    @Excel(name = "不合格数量")
    private Long unqualifiedQuantity;

    /** 批次零件检测合格率 */
    @Excel(name = "合格率")
    private String qualifiedRate;

    /** 零件对象 */
    private List<SysPart> parts;

    /** 零件组 */
    private Long[] partIds;

    /** 零件Id */
    private Long partId;

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Long getPartTypeId() {
        return partTypeId;
    }

    public void setPartTypeId(Long partTypeId) {
        this.partTypeId = partTypeId;
    }

    public String getPartTypeName() {
        return partTypeName;
    }

    public void setPartTypeName(String partTypeName) {
        this.partTypeName = partTypeName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getProductionLine() {
        return productionLine;
    }

    public void setProductionLine(String productionLine) {
        this.productionLine = productionLine;
    }

    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getExpectedStartTime() {
        return expectedStartTime;
    }

    public void setExpectedStartTime(String expectedStartTime) {
        this.expectedStartTime = expectedStartTime;
    }

    public String getExpectedEndTime() {
        return expectedEndTime;
    }

    public void setExpectedEndTime(String expectedEndTime) {
        this.expectedEndTime = expectedEndTime;
    }

    public String getDetectStatus() {
        return detectStatus;
    }

    public void setDetectStatus(String detectStatus) {
        this.detectStatus = detectStatus;
    }

    public String getDetectStartTime() {
        return detectStartTime;
    }

    public void setDetectStartTime(String detectStartTime) {
        this.detectStartTime = detectStartTime;
    }

    public String getDetectEndTime() {
        return detectEndTime;
    }

    public void setDetectEndTime(String detectEndTime) {
        this.detectEndTime = detectEndTime;
    }

    public Long getQualifiedQuantity() {
        return qualifiedQuantity;
    }

    public void setQualifiedQuantity(Long qualifiedQuantity) {
        this.qualifiedQuantity = qualifiedQuantity;
    }

    public Long getUnqualifiedQuantity() {
        return unqualifiedQuantity;
    }

    public void setUnqualifiedQuantity(Long unqualifiedQuantity) {
        this.unqualifiedQuantity = unqualifiedQuantity;
    }

    public String getQualifiedRate() {
        return qualifiedRate;
    }

    public void setQualifiedRate(String qualifiedRate) {
        this.qualifiedRate = qualifiedRate;
    }

    public List<SysPart> getParts() {
        return parts;
    }

    public void setParts(List<SysPart> parts) {
        this.parts = parts;
    }

    public Long[] getPartIds() {
        return partIds;
    }

    public void setPartIds(Long[] partIds) {
        this.partIds = partIds;
    }

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("batchId", getBatchId())
                .append("batchName", getBatchName())
                .append("partTypeId", getPartTypeId())
                .append("partTypeName", getPartTypeName())
                .append("quantity", getQuantity())
                .append("productionLine", getProductionLine())
                .append("devId", getDevId())
                .append("devName", getDevName())
                .append("expectedStartTime", getExpectedStartTime())
                .append("expectedEndTime", getExpectedEndTime())
                .append("detectStatus", getDetectStatus())
                .append("detectStartTime", getDetectStartTime())
                .append("detectEndTime", getDetectEndTime())
                .append("qualifiedQuantity", getQualifiedQuantity())
                .append("unqualifiedQuantity", getUnqualifiedQuantity())
                .append("qualifiedRate", getQualifiedRate())
                .toString();
    }


}
