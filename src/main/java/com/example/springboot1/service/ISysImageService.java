package com.example.springboot1.service;

import com.example.springboot1.entity.browser.quality.SysImage;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 图片的业务层
 */
public interface ISysImageService
{
    /**
     * 根据条件查询检测图片数据
     *
     * @param sysImage
     * @return
     */
    public List<SysImage> selectImageList(SysImage sysImage);

    /**
     * 通过零件Id查询图片详细
     * @param imageId
     * @return
     */
    public SysImage selectImageById(Long imageId);

    /**
     * 检查零件名称是否已经存在
     * @param image
     * @return
     */
    public boolean checkImageNameUnique(SysImage image);

    /**
     * 新增图片信息 与 零件图片缺陷信息
     *
     * @param image
     * @return
     */
    public int insertImage(SysImage image);

    /**
     * 将图片写入图床，获得图片的url
     * @param file
     * @param imageTag
     * @return
     */
    public Map<String, String> uploadImage(MultipartFile file, String imageTag);


    /**
     * 删除图片
     * @param image
     * @return
     */
    public int deleteImage(SysImage image);

    /**
     * 根据条件分页查询零件的图片
     */
    public List<SysImage> selectAllocatedImageList(SysImage image);

    /**
     * 根据条件分页查询未被分配的图片
     */
    public List<SysImage> selectUnallocatedImageList(SysImage image);
}
