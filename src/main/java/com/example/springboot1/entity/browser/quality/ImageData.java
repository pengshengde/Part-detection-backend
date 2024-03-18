package com.example.springboot1.entity.browser.quality;

import com.example.springboot1.common.browser.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 包含图片数据的实体类
 */
public class ImageData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String partName;

    private String imageName;

    private String imageBase64;

    private String sign;

    private String devName;

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
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
                .append("partName", getPartName())
                .append("imageName", getImageName())
                .append("imageBase64",getImageBase64())
                .append("sign", getSign())
                .append("devName", getDevName())
                .toString();
    }
}
