package com.example.springboot1.entity.browser.quality;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 零件与缺陷关联表
 */
public class SysPartDefectType {

    /** 零件ID */
    private Long partId;

    /** 缺陷类型ID */
    private Long defectTypeId;

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
    }

    public Long getDefectTypeId() {
        return defectTypeId;
    }

    public void setDefectTypeId(Long defectTypeId) {
        this.defectTypeId = defectTypeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("partId", getPartId())
                .append("defectTypeId", getDefectTypeId())
                .toString();
    }
}
