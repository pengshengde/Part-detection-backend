package com.example.springboot1.entity.browser.quality;

import com.example.springboot1.common.browser.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SysPartType extends BaseEntity {
    /**
     * 部件类型
     */

    private static final long serialVersionUID = 1L;

    /** 零件类型ID */
    private Long  partTypeId;

    /** 零件类型名称 */
    private String partTypeName;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("partTypeId",getPartTypeId())
                .append("partTypeName",getPartTypeName())
                .toString();
    }
}
