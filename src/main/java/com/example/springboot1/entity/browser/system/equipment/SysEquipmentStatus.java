package com.example.springboot1.entity.browser.system.equipment;

import com.example.springboot1.common.browser.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
/**
 * 设备状态  sys_devstatus
 */

public class SysEquipmentStatus extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 设备状态的id */
    private Long devStatusId;

    /** 设备的id */
    private Long devId;

    /** 设备名称 */
    private  String devName;

    /** 状态id */
    private Long statusId;

    /** 状态名称 */
    private String statusName;

    /** 设备状态的起始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startTime;

    /** 设备状态的结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endTime;

    /** 决定设备状态，若为1则表示为起始时间中，否则为0 */
    private Integer timeStatus;


    public Long getDevStatusId() {
        return devStatusId;
    }

    public void setDevStatusId(Long devStatusId) {
        this.devStatusId = devStatusId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
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

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
    public Integer getTimeStatus() {
        return timeStatus;
    }

    public void setTimeStatus(Integer timeStatus) {
        this.timeStatus = timeStatus;
    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("devStatusId", getDevStatusId())
                .append("devId",getDevId())
                .append("devName",getDevName())
                .append("statusId",getStatusId())
                .append("statusName",getStatusName())
                .append("startTime",getStartTime())
                .append("endTime",getEndTime())
                .append("timeStatus",getTimeStatus())
                .toString();
    }
}
