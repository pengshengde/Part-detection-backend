package com.example.springboot1.entity.browser.system.equipment;

import com.example.springboot1.common.browser.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SysEquipment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 设备id */
    private  Long devId;

    /** 设备名称 */
    private String devName;

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("devId",getDevId())
                .append("devName",getDevName())
                .toString();
    }
}
