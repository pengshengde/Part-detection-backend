package com.example.springboot1.entity.browser.system.equipment;

import com.example.springboot1.common.browser.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SysStatus extends BaseEntity {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

    /** 状态的id */
    private Long statusId;

    /** 状态名称 */
    private String statusName;

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("statusId",getStatusId())
                .append("statusName",getStatusName())
                .toString();
    }
}
