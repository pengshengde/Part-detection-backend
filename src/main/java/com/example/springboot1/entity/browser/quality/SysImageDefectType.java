package com.example.springboot1.entity.browser.quality;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 图片与缺陷的关联  sys_image_defect_type
 */
public class SysImageDefectType {

    /** 图片id */
    private Long imageId;

    /** 缺陷检测Id */
    private Long defectTypeId;

    // getter and setter

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
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
                .append("imageId", getImageId())
                .append("defectTypeId", getDefectTypeId())
                .toString();
    }
}
