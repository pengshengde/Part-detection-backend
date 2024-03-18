package com.example.springboot1.mapper;

import com.example.springboot1.entity.browser.quality.SysPartImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 零件与图片的关联表
 */
public interface SysPartImageMapper
{
    /**
     * 通过零件Id查询包含的零件图片数量
     * @param partId
     * @return
     */
    public int countPartImageByPartId(String partId);


    /**
     * 批量插入零件与其图片的关联关系
     * @param list
     * @return
     */
    public int batchInsertPartImage(List<SysPartImage> list);


    /**
     * 插零件与其图片的关联关系
     * @param partImage
     * @return
     */
    public int insertPartImage(SysPartImage partImage);

    /**
     * 通过图片Id删除零件与其图片的关联关系
     * @param imageId
     * @return
     */
    public int deletePartImageByImageId(Long imageId);

    /**
     * 删除零件与图片的关联
     * @param partImage
     * @return
     */
    public int deletePartImageInfo(SysPartImage partImage);

    /**
     * 批量删除零件与图片关联信息
     * @param partId
     * @param imageId
     * @return
     */
    public int deletePartImageInfos(@Param("partId") Long partId, @Param("imageIds") Long[] imageId);

    /**
     * 通过图片Id找到零件Id
     * @param partId
     * @return
     */
    public List<Long> selectImageIdsByPartId(Long partId);

    /**
     *  通过零件Id找到图片Ids
     * @param partId
     * @return
     */
    public SysPartImage selectPartImageByImageId(Long partId);

    /**
     * 根据零件id 删除零件与图片关联信息
     * @param partId
     * @return
     */
    public int deletePartImageByPartId(Long partId);

}
