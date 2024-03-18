package com.example.springboot1.entity.browser.quality;

import com.example.springboot1.common.browser.annotation.Excel;
import com.example.springboot1.common.browser.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class SysPart extends BaseEntity  {

    private static final long serialVersionUID = 1L;

    /** 零件ID */
    @Excel(name = "零件序号", cellType = Excel.ColumnType.NUMERIC, prompt = "零件编号")
    private Long partId;

    /** 零件类型ID */
    private Long partTypeId;

    /** 零件类型名称 */
    @Excel(name = "零件类型名称")
    private String partTypeName;

    /** 零件编号名称 */
    @Excel(name = "零件名称")
    private String partName;

    /** 零件缺陷检测时间 */
    @Excel(name = "检测时间", width = 30, dateFormat = "yyyy-MM-dd")
    private String detectTime;

    /** 生产设备名称 */
    private String devName;

    /** 检测标志（0代表存在 1代表已检测） */
    private String detectStatus;

    /** 零件缺陷检测结果 */
    @Excel(name = "检测结果")
    private String detectResult;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 图片对象 */
    private List<SysImage> images;

    /** 图片组 */
    private Long[] imageIds;


    /** 零件缺陷检测类型ID集合 */
    private Long[] defectTypeIds;

    /** 零件缺陷检测类型ID */
    private Long defectTypeId;

    /** 零件缺陷检测类型集合 */
    private List<SysDefectType> defectTypes;

    /** 零件缺陷检测类型名称 */
    @Excel(name = "零件缺陷")
    private String defectTypeName;

    /** 零件缺陷检测类型名称集合 */
    private String[]  defectTypeNames;

    private String sign;

    private Long batchId;

    public List<SysImage> getImages() {
        return images;
    }

    public void setImages(List<SysImage> images) {
        this.images = images;
    }

    public Long[] getImageIds() {
        return imageIds;
    }

    public void setImageIds(Long[] imageIds) {
        this.imageIds = imageIds;
    }

    public Long[] getDefectTypeIds() {
        return defectTypeIds;
    }

    public void setDefectTypeIds(Long[] defectTypeIds) {
        this.defectTypeIds = defectTypeIds;
    }

    public Long getDefectTypeId() {
        return defectTypeId;
    }

    public void setDefectTypeId(Long defectTypeId) {
        this.defectTypeId = defectTypeId;
    }

    public List<SysDefectType> getDefectTypes() {
        return defectTypes;
    }

    public void setDefectTypes(List<SysDefectType> defectTypes) {
        this.defectTypes = defectTypes;
    }

    public String getDefectTypeName() {
        return defectTypeName;
    }

    public void setDefectTypeName(String defectTypeName) {
        this.defectTypeName = defectTypeName;
    }

    public String[] getDefectTypeNames() {
        return defectTypeNames;
    }

    public void setDefectTypeNames(String[] defectTypeNames) {
        this.defectTypeNames = defectTypeNames;
    }

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
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

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getDetectTime() {
        return detectTime;
    }

    public void setDetectTime(String detectTime) {
        this.detectTime = detectTime;
    }

    public String getDetectResult() {
        return detectResult;
    }

    public void setDetectResult(String detectResult) {
        this.detectResult = detectResult;
    }

    public String getDetectStatus() {
        return detectStatus;
    }

    public void setDetectStatus(String detectStatus) {
        this.detectStatus = detectStatus;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }


    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("partId", getPartId())
                .append("partTypeId", getPartTypeId())
                .append("partTypeName", getPartTypeName())
                .append("devName", getDevName())
                .append("partName", getPartName())
                .append("detectTime", getDetectTime())
                .append("detectStatus", getDetectStatus())
                .append("detectResult", getDetectResult())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
