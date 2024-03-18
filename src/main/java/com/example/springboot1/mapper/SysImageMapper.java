package com.example.springboot1.mapper;

import com.example.springboot1.entity.browser.quality.SysImage;

import java.util.List;

/**
 *  缺陷检测图片的mapper接口
 */
public interface SysImageMapper
{

    /**
     *  查询缺陷检测图片列表
     *  imageId,imageName,partTypeId、partTypeName、params.beginTime、params.endTime
     * @param image 图片的信息
     * @return  图片的信息
     */
    public List<SysImage> selectImageList(SysImage image);

    /**
     * 插入检测图片的信息
     *
     * @param image 图片的信息
     * @return 新增结果
     */
    public int insertImage(SysImage image);

    /**
     * 通过图片名称寻找Id
     * @param imageName
     * @return
     */
    public Long selectIdByImageName(String  imageName);

    /**
     * 通过图片的ID查询图片的详细信息
     * @param imageId
     * @return
     */
    public SysImage selectImageByImageId(Long imageId);

    /**
     * 校验图片名称是否唯一
     * @param imageName
     * @return
     */
    public SysImage checkImageNameUnique(String imageName);

    /**
     * 根据图片的id删除图片
     * @param imageId
     * @return
     */
    public int deleteImageById(Long imageId);

    /**
     * 根据条件分页查询零件的图片
     * @param image
     * @return
     */
    public List<SysImage> selectAllocatedImageList(SysImage image);

    public List<SysImage> selectUnallocatedImageList(SysImage image);
}
