package com.example.springboot1.entity.browser.quality;

import com.example.springboot1.common.browser.annotation.Excel;
import com.example.springboot1.common.browser.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;


/**
 * 图片的实体类
 */
public class SysImage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图片的id */
    @Excel(name = "图片编号", cellType = Excel.ColumnType.NUMERIC, prompt = "图片编号")
    private Long imageId;

    /** 图片的名称 */
    @Excel(name = "图片名称")
    private String imageName;

    /** 图片类型 */
    private String imageType;

    /** 零件类型ID */
    private Long partTypeId;

    @Excel(name = "零件类型")
    /** 零件类型名称 */
    private String partTypeName;

    /** 删除标志 0表示未被删除 2表示删除 */
    private String delFlag;

    /** 检测标志 0为未检测，1为已检测 */
    private String detectStatus;

    /** 检测时间 */
    @Excel(name = "检测时间", width = 30, dateFormat = "yyyy-MM-dd")
    private String detectTime;

    /** 原始图片url */
    @Excel(name = "检测图片url")
    private String originalImageUrl;

    /** 重命名后的图片名称 */
    @Excel(name = "图片保存名称")
    private String originalImageName;

    /** 检测后图片url */
    @Excel(name = "检测结果url")
    private String resultImageUrl;

    /** 重命名后检测图片名称 */
    @Excel(name = "结果保存名称")
    private String resultImageName;

    /** 零件图片对应的零件Id */
    private Long partId;

    @Excel(name = "图片检测结果")
    /** 零件图片的缺陷 */
    private String defectTypeName;

    /** 缺陷名称 */
    private String[] defectTypeNames;

    /**
     * 缺陷的集合
     */
    private List<SysDefectType> defectTypes;

    /** 检测结果ID集合 */
    private Long[] defectTypeIds;

    /** 缺陷Id */
    private Long defectTypeId;

    /** 校验用的签名 */
    private  String sign;

    /** 设备名称 */
    private String devName;


    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
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

    public String getDetectTime() {
        return detectTime;
    }

    public void setDetectTime(String detectTime) {
        this.detectTime = detectTime;
    }

    public String getOriginalImageUrl() {
        return originalImageUrl;
    }

    public void setOriginalImageUrl(String originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
    }

    public String getOriginalImageName() {
        return originalImageName;
    }

    public void setOriginalImageName(String originalImageName) {
        this.originalImageName = originalImageName;
    }

    public String getResultImageUrl() {
        return resultImageUrl;
    }

    public void setResultImageUrl(String resultImageUrl) {
        this.resultImageUrl = resultImageUrl;
    }

    public String getResultImageName() {
        return resultImageName;
    }

    public void setResultImageName(String resultImageName) {
        this.resultImageName = resultImageName;
    }


    public List<SysDefectType> getDefectTypes() {
        return defectTypes;
    }

    public void setDefectTypes(List<SysDefectType> defectTypes) {
        this.defectTypes = defectTypes;
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

    public String[] getDefectTypeNames() {
        return defectTypeNames;
    }

    public void setDefectTypeNames(String[] defectTypeNames) {
        this.defectTypeNames = defectTypeNames;
    }

    public String getDefectTypeName() {
        return defectTypeName;
    }

    public void setDefectTypeName(String defectTypeName) {
        this.defectTypeName = defectTypeName;
    }

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("imageId", getImageId())
                .append("imageName", getImageId())
                .append("imageType", getImageType())
                .append("partTypeId", getPartTypeId())
                .append("partTypeName", getPartTypeName())
                .append("detectStatus", getDetectStatus())
                .append("detectTime", getDetectTime())
                .append("delFlag", getDelFlag())
                .append("originalImageUrl", getOriginalImageUrl())
                .append("originalImageName", getOriginalImageName())
                .append("resultImageUrl", getResultImageUrl())
                .append("resultImageName", getResultImageName())
                .toString();
    }
}
