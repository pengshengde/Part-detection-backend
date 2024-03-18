package com.example.springboot1.entity.browser.quality;

import com.example.springboot1.common.browser.annotation.Excel;
import com.example.springboot1.common.browser.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SysDefectType extends BaseEntity {

    /**
     * 缺陷类型的实体类
     */
    private static final long serialVersionUID = 1L;

    /** 缺陷类型ID */
    @Excel(name = "缺陷类型序号", cellType = Excel.ColumnType.NUMERIC)
    private Long defectTypeId;

    /** 缺陷类型名称 */
    @Excel(name = "缺陷类型名称")
    private String defectTypeName;


    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;


    /** 缺陷描述 */
    @Excel(name = "缺陷描述")
    private String defectDescription;

    public SysDefectType()
    {

    }
    public SysDefectType(Long defectTypeId)
    {
        this.defectTypeId = defectTypeId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.defectTypeId);
    }

    public static boolean isAdmin(Long defectTypeId)
    {
        return defectTypeId != null && 1L == defectTypeId;
    }

    public Long getDefectTypeId() {
        return defectTypeId;
    }

    public void setDefectTypeId(Long defectTypeId) {
        this.defectTypeId = defectTypeId;
    }

    public String getDefectTypeName() {
        return defectTypeName;
    }

    public void setDefectTypeName(String defectTypeName) {
        this.defectTypeName = defectTypeName;
    }

    public String getDefectDescription() {
        return defectDescription;
    }

    public void setDefectDescription(String defectDescription) {
        this.defectDescription = defectDescription;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("defectTypeId",getDefectTypeId())
                .append("defectTypeName",getDefectTypeName())
                .append("defectDescription",getDefectDescription())
                .append("delFlag",getDelFlag())
                .toString();
    }
}
