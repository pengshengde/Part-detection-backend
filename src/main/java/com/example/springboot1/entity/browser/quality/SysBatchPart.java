package com.example.springboot1.entity.browser.quality;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 批次与零件的关联  sys_batch_part
 */
public class SysBatchPart
{
    /** 零件的批次ID */
    private Long batchId;

    /** 零件的ID  */
    private Long partId;

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
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
                .append("partId", getPartId())
                .toString();
    }
}
